import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CengGUI 
{	
	private JFrame frame;
	private JTable currentPaperTable;
	private JTable alreadyAddedPaperTable;
	
	private static final Object[] columnNames = new Object[]{"PaperId", "Journal", "Paper Name", "Author"};

	private ArrayList<CengPaper> currentPapers;
	private ArrayList<CengPaper> addedPapers;
		
	private JPanel mainModel;
	private JPanel secondModel;
		
	private ArrayList<GUILevel> paintedLevels;
	
	public static Integer orderN; // orderN = 2 * order + 1
	
	@SuppressWarnings("serial")
	static ArrayList<Color> definedColors = new ArrayList<Color>(){{
		
		// Pre-defined colors
		add(Color.black);
		//add(Color.white); Color of empty padding
		add(Color.red);
		//add(Color.green); Color of leaf background
		add(Color.blue);
		add(Color.cyan);
		add(Color.gray);
		//add(Color.lightGray); Color of internal background
		add(Color.magenta);
		add(Color.orange);
		add(Color.pink);
		add(Color.yellow);
		
		// Common colors
		add(new Color(128, 0, 0)); // Maroon
		add(new Color(0, 128, 0)); // Olive
		add(new Color(0, 0, 128)); // Navy
		add(new Color(0, 128, 128)); // Teal
		
		// Uncommon colors
		
		add(new Color(240,230,140)); // Khaki
		add(new Color(0,100,0)); // Dark green
		add(new Color(255,140,0)); // Dark orange
		add(new Color(47,79,79)); // Dark slate gray
		add(new Color(0,206,209)); // Dark turquoise
		add(new Color(188,143,143)); // Rosy brown
	}};

	public CengGUI(Integer options)
	{
		initialize(options);		
	}

	public void show()
	{
		frame.setVisible(true);
	}
	
	public void modelNeedsUpdate(Integer options, ScholarNode root1, ScholarNode root2) {
		//System.out.println("Updating model...");
		
		if(options%2==1) updateMainModel(root1);
		if(options>1) updateSecondModel(root2);
	}
	
	private void initialize(Integer options) {
		currentPapers = new ArrayList<CengPaper>();
		addedPapers = new ArrayList<CengPaper>();
		
		frame = new JFrame();
		frame.setSize(512, 384);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		if(options%2==1) addMainModel();
		if(options>1) addSecondModel();
		addCurrentTable(options);
		
		addAlreadyAddedTable();
		
        frame.pack();
	}

	private void addMainModel() {
		mainModel = new JPanel();
		mainModel.setLayout(new BoxLayout(mainModel, BoxLayout.Y_AXIS));
		
		frame.getContentPane().add(mainModel);		
	}
	
	private void addSecondModel() {
		secondModel = new JPanel();
		secondModel.setLayout(new BoxLayout(secondModel, BoxLayout.Y_AXIS));
		
		frame.getContentPane().add(secondModel);		
	}
	
	private void updateMainModel(ScholarNode root) {
		mainModel.removeAll();
		
		Queue<ScholarNode> queue = new LinkedList<ScholarNode>();
		
		queue.add(root);
		
		ArrayList<ScholarNode> allLevels = new ArrayList<ScholarNode>();
		
		root.level = 1;
		
		Integer maxLevel = root.level;
		
		while(queue.size() > 0) {
			ScholarNode currentNode = queue.poll();
			
			if(currentNode.getType() == ScholarNodeType.Internal) {
				ScholarNodePrimaryIndex castNode = (ScholarNodePrimaryIndex)currentNode;
				
				ArrayList<ScholarNode> allChildren = castNode.getAllChildren();
				
				for(ScholarNode node : allChildren) {
					node.level = currentNode.level + 1;
					maxLevel = node.level;
					queue.add(node);
				}
			}

			allLevels.add(currentNode);
		}
		
		if(paintedLevels != null) {
			paintedLevels.clear();
			paintedLevels = null;
		}
		
		paintedLevels = new ArrayList<GUILevel>();
		
		for(int i = 0; i < maxLevel; i++) {
			GUILevel newLevel = new GUILevel();
			
						
			for(ScholarNode node : allLevels)
			{
				if(node.level == i + 1) // Level starts with 1
				{						
					newLevel.addNode(node);
				}
			}
			
			mainModel.add(newLevel);
			paintedLevels.add(newLevel);
		}
		
		mainModel.revalidate();
		mainModel.repaint();
		
		frame.revalidate();
		frame.repaint();
		
		if(CengScholar.shouldPack()) {
			frame.pack();
		}
	}
	
	private void updateSecondModel(ScholarNode root) {
		secondModel.removeAll();
		
		Queue<ScholarNode> queue = new LinkedList<ScholarNode>();
		
		queue.add(root);
		
		ArrayList<ScholarNode> allLevels = new ArrayList<ScholarNode>();
		
		root.level = 1;
		
		Integer maxLevel = root.level;
		
		while(queue.size() > 0) {
			ScholarNode currentNode = queue.poll();
			
			if(currentNode.getType() == ScholarNodeType.Internal) {
				ScholarNodeSecondaryIndex castNode = (ScholarNodeSecondaryIndex)currentNode;
				
				ArrayList<ScholarNode> allChildren = castNode.getAllChildren();
				
				for(ScholarNode node : allChildren) {
					node.level = currentNode.level + 1;
					maxLevel = node.level;
					queue.add(node);
				}
			}

			allLevels.add(currentNode);
		}
		
		if(paintedLevels != null) {
			paintedLevels.clear();
			paintedLevels = null;
		}
		
		paintedLevels = new ArrayList<GUILevel>();
		
		for(int i = 0; i < maxLevel; i++) {
			GUILevel newLevel = new GUILevel();
			
						
			for(ScholarNode node : allLevels) {
				if(node.level == i + 1) // Level starts with 1
				{
					newLevel.addNode2(node);
				}
			}
			
			secondModel.add(newLevel);
			paintedLevels.add(newLevel);
		}
		
		secondModel.revalidate();
		secondModel.repaint();
		
		frame.revalidate();
		frame.repaint();
		
		if(CengScholar.shouldPack()) {
			frame.pack();
		}
	}
	
	private void addCurrentTable(Integer options) {
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
				
		DefaultTableModel currentPaperTableModel = new DefaultTableModel(columnNames, 0);

		ArrayList<CengPaper> inputPapers = ScholarParser.parsePapersFromFile(CengScholar.getFilenameToParse());
		
		for(CengPaper paper : inputPapers) {
			currentPaperTableModel.addRow(new Object[]{paper.paperId(), paper.journal(), paper.paperName(), paper.author()});
		}
		
		if(currentPapers.size() == 0) {
			currentPapers.addAll(inputPapers);
		}
		
		currentPaperTable = new JTable(currentPaperTableModel) {
			// Anon class
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
	        {
                return false;               	
	        };
		};
		
		modifyTable(currentPaperTable);
		
        final JButton addButton = new JButton("Add Selected Paper");
        
        addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				addSelectedPaper();
			}
		 });
        
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JButton print1Button = new JButton("Print Primary Tree");
        
        print1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("print1"); // Only for visual purposes
				CengScholar.printPrimary();
			}
        });
        
        print1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JButton print2Button = new JButton("Print Secondary Tree");
        
        print2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("print2"); // Only for visual purposes
				CengScholar.printSecondary();
			}
        });
        
        print2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if(options%2==1) tablePanel.add(print1Button);
        if(options>1) tablePanel.add(print2Button);
        tablePanel.add(addButton);
        tablePanel.add(currentPaperTable.getTableHeader());
		tablePanel.add(currentPaperTable);
	
		JScrollPane scrollPaneCurrentPapers = new JScrollPane(tablePanel);
		
		// Change width manually
        // scrollPaneCurrentPapers.getViewport().setPreferredSize(new Dimension(400, scrollPaneCurrentPapers.getViewport().getPreferredSize().height));

		frame.getContentPane().add(scrollPaneCurrentPapers);
	}
	
	void addAlreadyAddedTable() {
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		
		DefaultTableModel addedPaperTableModel = new DefaultTableModel(columnNames, 0);
		
		alreadyAddedPaperTable = new JTable(addedPaperTableModel) {
			// Anon class
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
	        {
                return false;               	
	        };
		};
		
		modifyTable(alreadyAddedPaperTable);

		final JButton searchButton = new JButton("Search Selected Paper");
		final JButton searchJournalButton = new JButton("Search Selected Papers Journal");

        searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				searchSelectedPaper();
			}
        });
		searchJournalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				searchSelectedJournal();
			}
		});
        
        searchButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        tablePanel.add(searchButton);
		searchJournalButton.setAlignmentX(Container.CENTER_ALIGNMENT);
		tablePanel.add(searchJournalButton);
        tablePanel.add(alreadyAddedPaperTable.getTableHeader());
		tablePanel.add(alreadyAddedPaperTable);
		
		JScrollPane scrollPaneAddedPapers = new JScrollPane(tablePanel);
		
		frame.getContentPane().add(scrollPaneAddedPapers);
	}
	
	private void addSelectedPaper() {
		if(currentPaperTable.getSelectedRow() == -1) {
			return;
		}
		
		CengPaper selectedPaper = getSelectedPaperFromTable(currentPaperTable);
		
		System.out.println("add|" + selectedPaper.fullName()); // Only for visual purposes
		
		CengScholar.addPaper(selectedPaper);
				
		currentPapers.remove(selectedPaper);
		
		addPaperToTable(selectedPaper, alreadyAddedPaperTable);
		
		addedPapers.add(selectedPaper);
		
		removeSelectionFromTable(currentPaperTable);
	}

	private void searchSelectedPaper() {
		if(alreadyAddedPaperTable.getSelectedRow() == -1) {
			return;
		}
		
		CengPaper selectedPaper = getSelectedPaperFromTable(alreadyAddedPaperTable);
		
		System.out.println("search1|" + selectedPaper.paperId()); // Only for visual purposes
		
		CengScholar.searchPaper(selectedPaper.paperId());
	}

	private void searchSelectedJournal() {
		if(alreadyAddedPaperTable.getSelectedRow() == -1) {
			return;
		}

		CengPaper selectedPaper = getSelectedPaperFromTable(alreadyAddedPaperTable);

		System.out.println("search2|" + selectedPaper.journal()); // Only for visual purposes

		CengScholar.searchJournal(selectedPaper.journal());
	}
	
	// Helpers
	
	private void removeSelectionFromTable(JTable table) {
		Integer selectedRowIndex = table.getSelectedRow();

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.removeRow(selectedRowIndex);		
	}
	
	private CengPaper getSelectedPaperFromTable(JTable table) {
		Integer selectedRowIndex = table.getSelectedRow();
		
		if(table.equals(currentPaperTable)) {
			return currentPapers.get(selectedRowIndex);
		}
		else {
			return addedPapers.get(selectedRowIndex);
		}
	}
	
	private void addPaperToTable(CengPaper paper, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.addRow(new Object[]{paper.paperId(), paper.journal(), paper.paperName(), paper.author()});
	}
	
	private void modifyTable(final JTable table) {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

    	if(tableModel.getRowCount() != 0) {
    		// Initialize table with first row selected.
        	table.addRowSelectionInterval(0, 0);
    	}
    	
		tableModel.addTableModelListener(new TableModelListener() {      
		    @Override
		    public void tableChanged(TableModelEvent e) 
		    {
		        SwingUtilities.invokeLater(new Runnable() 
		        {
		            @Override
		            public void run()
		            {
		            	if(tableModel.getRowCount() != 0)
		            	{
			            	table.addRowSelectionInterval(0, 0);
		            	}
		            }
		        });
		    }
		});
	}
	
	public static Color getRandomBorderColor() {
		Random rand = new Random();

		if(CengGUI.definedColors.size() > 0) {
			int randomIndex = rand.nextInt(CengGUI.definedColors.size());
			
			Color pickedColor = CengGUI.definedColors.get(randomIndex);
			
			CengGUI.definedColors.remove(pickedColor);
			
			return pickedColor;
		}
		else {
			// Will produce only bright / light colors
			float r = rand.nextFloat() / 2f;
			float g = rand.nextFloat() / 2f;
			float b = rand.nextFloat() / 2f;
			
			return new Color(r, g, b).brighter();
		}
	}
}
