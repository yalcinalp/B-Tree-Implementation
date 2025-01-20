import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUISecondaryLeafNode extends GUITreeNode {
	public GUISecondaryLeafNode(ScholarNode node) {
		super(node);

		this.setBackground(Color.pink);

		ScholarNodeSecondaryLeaf castNode = (ScholarNodeSecondaryLeaf)node;
		
		for(int i = 0; i < castNode.journalCount(); i++) {
			if(i >= this.labels.size()) {
				System.out.println("Journal count is greater than label count.");
				return;
			}
			
 			String journal = castNode.journalAtIndex(i);
			
			JLabel correspondingLabel = this.labels.get(i);
			
			correspondingLabel.setText(journal);
			correspondingLabel.repaint();
		}
	}
	
	
}
