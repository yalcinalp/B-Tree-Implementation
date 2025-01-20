import java.awt.EventQueue;

public class CengScholar {
	
	private static Integer order;
	private static String fileName;
	private static Integer guiOptions;
	private static Boolean guiEnabled;
	private static ScholarTree scholarTree;
	private static CengGUI window;
	private static Boolean wrapNodes = true;
	private static Boolean packFrame = false;
	
	public static void main(String[] args) throws Exception {
		if(args.length <2) {
			throw new Exception("Usage : java CengScholar -order- -guiOptions- (-guiFileName-) ");
		}		
		
		order = Integer.parseInt(args[0]);
		guiOptions = Integer.parseInt(args[1]);
		
		if(args.length == 2 && guiOptions!=0) {
			throw new Exception("In order to use GUI, provide an input file");			
		}
		
		if(guiOptions>0 && guiOptions<4) {
			guiEnabled=true;
			fileName = args[2];
		}
		else if (guiOptions==0) guiEnabled=false;
		else {
			throw new Exception("Invalid GUI Options Value");			
		}		
		
		scholarTree = new ScholarTree(order);
				
		Integer orderN = 2 * order + 1; // N-based order, for GUI purposes only.

		CengGUI.orderN = orderN;
		
		if(guiEnabled) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new CengGUI(guiOptions);
						window.show();
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		ScholarParser.startParsingCommandLine();
	}
	
	public static void addPaper (CengPaper paper) {
		scholarTree.addPaper(paper);
		
		if(guiEnabled) {
			if(window == null) {
				System.out.println("Err: Window is not initialized yet.");
				return;
			}
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}

	public static void searchPaper(Integer key) {
		scholarTree.searchPaper(key);
	}

	public static void searchJournal(String key) {
		scholarTree.searchJournal(key);
	}
	
	public static void printPrimary() {
		scholarTree.printPrimaryScholar();
		
		if(guiEnabled) {
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}
	
	public static void printSecondary() {
		scholarTree.printSecondaryScholar();
		
		if(guiEnabled) {
			window.modelNeedsUpdate(guiOptions, scholarTree.primaryRoot, scholarTree.secondaryRoot);
		}
	}
	
	public static void setPrimaryRoot(ScholarNode newRoot) {
		scholarTree.primaryRoot=newRoot;
	}
	
	public static void setSecondaryRoot(ScholarNode newRoot) {
		scholarTree.secondaryRoot=newRoot;
	}
	
	public static String getFilenameToParse()
	{
		return CengScholar.fileName;
	}
	
	public static Boolean shouldWrap()
	{
		return CengScholar.wrapNodes;
	}
	
	public static Boolean shouldPack()
	{
		return CengScholar.packFrame;
	}
}

	

