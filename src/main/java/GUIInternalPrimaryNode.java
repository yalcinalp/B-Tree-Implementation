import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIInternalPrimaryNode extends GUITreeNode {
	public GUIInternalPrimaryNode(ScholarNode node) {
		super(node);
		
		this.setBackground(Color.lightGray);

		ScholarNodePrimaryIndex castNode = (ScholarNodePrimaryIndex)node;
		
		for(int i = 0; i < castNode.paperIdCount(); i++) {
			if(i < this.labels.size()) {
	 			Integer paperId = castNode.paperIdAtIndex(i);
	 			
				String keyString = "" + paperId;
				
				JLabel correspondingLabel = this.labels.get(i);
				
				correspondingLabel.setText(keyString);
				
				correspondingLabel.repaint();
			}
			else {
				System.out.println("paperId count is greater than label count.");
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
