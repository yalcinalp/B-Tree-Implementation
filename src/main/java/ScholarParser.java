import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ScholarParser {
	// Parsing the input file in order to use GUI tables.
	public static ArrayList<CengPaper> parsePapersFromFile(String filename) {
		ArrayList<CengPaper> paperList = new ArrayList<CengPaper>();
		Scanner s = null;;
		try {
			s = new Scanner(new File(filename), "UTF-8");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		while ( s.hasNextLine()) {
			String myLine = s.nextLine();
			//System.out.println(myLine);

			String[] array = myLine.split("[|]");
			Integer key = Integer.parseInt(array[1]);
			String journal = array[2];
			String name = array[3];
			String author = array[4];

			paperList.add(new CengPaper(key,name,author,journal));
		}
		s.close();
		return paperList;
	}
	
	public static void startParsingCommandLine() throws IOException {
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;
		String inpLine = null;
		while(running){
			inpLine = reader.readLine();
			String[] array = inpLine.split("[|]");
			String command = array[0];
			if(command.equalsIgnoreCase("add")){
				Integer key = Integer.parseInt(array[1]);
				String journal = array[2];
				String name = array[3];
				String author = array[4];
				
				CengScholar.addPaper(new CengPaper(key,name,author,journal));
			}
			else if(command.equalsIgnoreCase("quit")){
				running = false;
			}
			else if(command.equalsIgnoreCase("search1")){
				int key = Integer.parseInt(array[1]);
				CengScholar.searchPaper(key);
			}
			else if(command.equalsIgnoreCase("search2")){
				String key = array[1];
				CengScholar.searchJournal(key);
			}
			else if(command.equalsIgnoreCase("print1")){
				CengScholar.printPrimary();
			}
			else if(command.equalsIgnoreCase("print2")){
				CengScholar.printSecondary();
			}
		}
		
		// 
		// There are 5 commands:
		// 1) quit : End the application. Print nothing, call nothing, just terminate.
		// 2) add : Parse and create the paper, calls CengScholar.addPaper(newPaper)
		// 3) search1 : Parse the key in primary tree, calls CengScholar.searchPaper(key)
		// 4) search2 : Parse the key in secondary tree, calls CengScholar.searchJournal(key)
		// 5) print1 : Print the whole primary tree, calls CengScholar.printPrimary()
		// 6) print2 : Print the whole secondary tree, calls CengScholar.printSecondary()

		// Commands (quit, add, search1, search2, print1, print2) are case-insensitive.
	}
}