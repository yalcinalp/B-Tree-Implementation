import java.util.ArrayList;

public class ScholarNodeSecondaryIndex extends ScholarNode {
	public ArrayList<String> journals;
	public ArrayList<ScholarNode> children;

	public ScholarNodeSecondaryIndex(ScholarNode parent) {
		super(parent);
		journals = new ArrayList<String>();
		children = new ArrayList<ScholarNode>();
		this.type = ScholarNodeType.Internal;
	}
	
	public ScholarNodeSecondaryIndex(ScholarNode parent, ArrayList<String> journals, ArrayList<ScholarNode> children) {
		super(parent);
		this.journals = journals;
		this.children = children;
		this.type = ScholarNodeType.Internal;
	}
	
	// GUI Methods - Do not modify
	public ArrayList<ScholarNode> getAllChildren()
	{
		return this.children;
	}
	
	public ScholarNode getChildrenAt(Integer index) {

		return this.children.get(index);
	}

	public Integer journalCount()
	{
		return this.journals.size();
	}
	
	public String journalAtIndex(Integer index) {
		if(index >= this.journalCount() || index < 0) {
			return "Not Valid Index!!!";
		}
		else {
			return this.journals.get(index);
		}
	}
	
	// Extra functions if needed

}
