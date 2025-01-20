import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUIPrimaryLeafNode extends GUITreeNode {
	public GUIPrimaryLeafNode(ScholarNode node) {
		super(node);

		this.setBackground(Color.green);

		ScholarNodePrimaryLeaf castNode = (ScholarNodePrimaryLeaf)node;
		
		for(int i = 0; i < castNode.paperCount(); i++) {
			if(i >= this.labels.size()) {
				System.out.println("Paper count is greater than label count.");
				
				return;
			}
			
 			Integer paperId = castNode.paperIdAtIndex(i);
			 			
			String keyString = "" + paperId;
			
			JLabel correspondingLabel = this.labels.get(i);
			
			correspondingLabel.setText(keyString);
			correspondingLabel.repaint();
		}
	}
}
