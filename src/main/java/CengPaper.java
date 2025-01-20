
public class CengPaper {
	private Integer paperId;
	private String paperName;
	private String author;
	private String journal;
	
	public CengPaper(Integer paperId, String paperName, String author, String journal) {
		this.paperId = paperId;
		this.paperName = paperName;
		this.author = author;
		this.journal = journal;
	}
	
	// Getters
	public Integer paperId()
	{
		return paperId;
	}
	public String paperName()
	{
		return paperName;
	}
	public String author()
	{
		return author;
	}
	public String journal() {return journal;}
	
	// GUI method - do not modify
	public String fullName()
	{
		return "" + paperId() + "|" + journal() + "|" + paperName() + "|" + author();
	}
	
	// DO NOT ADD SETTERS
}
