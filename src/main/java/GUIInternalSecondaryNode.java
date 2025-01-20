import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIInternalSecondaryNode extends GUITreeNode {
	public GUIInternalSecondaryNode(ScholarNode node) {
		super(node);
		
		this.setBackground(Color.white);

		ScholarNodeSecondaryIndex castNode = (ScholarNodeSecondaryIndex)node;
		
		for(int i = 0; i < castNode.journalCount(); i++) {
			if(i < this.labels.size()) {
	 			String journal = castNode.journalAtIndex(i);
				
				JLabel correspondingLabel = this.labels.get(i);
				
				correspondingLabel.setText(journal);
				
				correspondingLabel.repaint();
			}
			else {
				System.out.println("Journal count is greater than label count.");
			}
		}
		
		ArrayList<ScholarNode> allChildren = castNode.getAllChildren();
		
		if(allChildren.size() > this.paddings.size()) {
			System.out.println("Children count is greater than padding count.");
		}
		
		for(int i = 0; i < this.paddings.size(); i++) {
			if(i < allChildren.size()) {
				ScholarNode child = allChildren.get(i);
				
				this.paddings.get(i).setBackground(Color.BLUE);				
			}
		}
		
		this.repaint();
	}
}
