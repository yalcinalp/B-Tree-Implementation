import java.awt.Color;
public class ScholarNode {
	static protected Integer order;
	private ScholarNode parent;
	protected ScholarNodeType type; // Type needs to be set for proper GUI. Check ScholarNodeType.java.

	// GUI Accessors - do not modify
	public Integer level;
	public Color color;
	
	public ScholarNode(ScholarNode parent) {
		this.parent = parent;
				
		if (parent != null)
		this.level = parent.level + 1;
		else
			this.level = 0;
	}
	
	// Getters-Setters - Do not modify
	public ScholarNode getParent()
	{
		return parent;
	}
	
	public void setParent(ScholarNode parent)
	{
		this.parent = parent;
	}
	
	public ScholarNodeType getType()
	{
		return type;
	}
	
	// GUI Methods - Do not modify
	public Color getColor()
	{
		return this.color;
	}

}
