import java.util.ArrayList;

public class ScholarNodePrimaryLeaf extends ScholarNode {
	private ArrayList<CengPaper> papers;  // keep the papers in the leaf node
	
	public ScholarNodePrimaryLeaf(ScholarNode parent) {
		super(parent);
		papers = new ArrayList<CengPaper>();
		this.type = ScholarNodeType.Leaf;
	}
	
	public ScholarNodePrimaryLeaf(ScholarNode parent, ArrayList<CengPaper> papers ) {
		super(parent);
		this.papers = papers;
		this.type = ScholarNodeType.Leaf;
	}
	
	public void addPaper(int index, CengPaper paper) {
		papers.add(index, paper);
	}
	
	
	// GUI Methods - Do not modify
	public int paperCount()
	{
		return papers.size();
	}

	public Integer paperIdAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return -1;
		}
		else {
			CengPaper paper = this.papers.get(index);
			return paper.paperId();
		}
	}
	
	public String paperJournalAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return null;
		}
		else {
			CengPaper paper = this.papers.get(index);
			
			return paper.journal();
		}
	}
	
	public CengPaper paperAtIndex(Integer index) {
		if(index >= this.paperCount()) {
			return null;
		}
		else {
			return this.papers.get(index);
		}
	}
	
	public ArrayList<CengPaper> getPapers(){
		return papers;
	}
}
