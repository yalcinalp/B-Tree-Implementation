import java.util.ArrayList;

public class ScholarNodeSecondaryLeaf extends ScholarNode {
    private ArrayList<String> journals;  // keeps journals in the leaf node
    private ArrayList<ArrayList<Integer>> paperIdBucket;  // keeps papers of the journals in the leaf node

    public ScholarNodeSecondaryLeaf(ScholarNode parent) {
        super(parent);
        journals = new ArrayList<String>();
        paperIdBucket = new ArrayList<ArrayList<Integer>>();
        this.type = ScholarNodeType.Leaf;
    }

    public ScholarNodeSecondaryLeaf(ScholarNode parent, ArrayList<String> journals, 
                                    ArrayList<ArrayList<Integer>> paperIdBucket) {
        super(parent);
        this.journals = journals;
        this.paperIdBucket = paperIdBucket;
        this.type = ScholarNodeType.Leaf;
    }

    public void addPaper(int index, CengPaper paper) {
        if(paperIdBucket.size() <= index) {
            paperIdBucket.add(new ArrayList<>());
            journals.add(paper.journal());
        }
        else if(!paper.journal().equalsIgnoreCase(this.journalAtIndex(index))){
            paperIdBucket.add(index, new ArrayList<>());
            journals.add(index, paper.journal());
        }
        paperIdBucket.get(index).add(paper.paperId());
    }


    // GUI Methods - Do not modify
    public int journalCount() {
        return journals.size();
    }

    public String journalAtIndex(Integer index) {
        if(index >= this.journalCount()) {
            return null;
        }
        else {
            return this.journals.get(index);
        }
    }

    public ArrayList<Integer> papersAtIndex(Integer index) {
        if(index >= this.journalCount()) {
            return null;
        }
        else if (paperIdBucket.get(index).size() == 0) {
            return null;
        }
        else {
            return this.paperIdBucket.get(index);
        }
    }

    public ArrayList<ArrayList<Integer>> getPaperIdBucket(){
        return paperIdBucket;
    }

    public ArrayList<String> getJournals(){
        return journals;
    }
}
