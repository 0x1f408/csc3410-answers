//import Scanner class; will need it for file input
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class Anagram{
public static void main(String[] args) {
    //create Scanner object to read initial prompt
    Scanner scan = new Scanner(System.in);
    Word wordList[] = new Word[50];
    int wcount = 0;

    //gets name (and location, if needed) of input file; can be .dat or .txt
    System.out.println("Please enter the name of the input file: ");
    String inputname = scan.nextLine();

	/* //!!\\ READ PROCESS //!!\\
	* a) Create needed variables & file scanner;
	* b) check if input file is empty;
	* c) read through the file, saving each line to String "hold";
	* d) read through each iteration of "hold", checking every token, if any;
	* e) increment the counter every time a valid word (length<=12) is encountered; 
	* f) save all valid words encountered to the WordsList array; 
	* g) program terminates if the number of words valid words exceeds 50;
	* h) if no terminating conditions are encountered, close() the file and proceed.
	*/
    BufferedReader input;
    try {
        input = new BufferedReader(new FileReader(inputname));

        //Scanner reader = new Scanner(inputname);
        String hold, cur;
        StringTokenizer lst;

        while (((hold = input.readLine()) != null)) {
            lst = new StringTokenizer(hold);
            while (lst.hasMoreTokens()) {
                cur = lst.nextToken();
                if (wcount > 50) {
                    System.out.println("There are more than 50 words in the input file!");
                    input.close();
                    System.exit(0);
                } else if (cur.length() <= 12) {
                    wordList[wcount] = new Word(cur);
                    wcount++;
                }
            }
        }
        if (wcount == 0) {
            System.out.print("The input file is empty!");
            System.exit(0);
        }
        System.out.println("File read successfully!");
        input.close();
    }
    catch (FileNotFoundException f) {
        System.out.print("File not found.");
        System.exit(0);
    }
    catch(IOException e){
        System.out.print("An unexpected I/O error has occurred.");
    }
	
	/* //!!\\ WRITE PROCESS //!!\\
	* a) Create FileWriter, setting it to write to a file named "output.txt"
	* b) Scan through the list of Words:
	*	1) save the sorted breakdown of each word as a String variable to test against (tester)
	*	2) test the breakdown of every Word in the array against the tester variable
	*	3) if the previous statement evaluates as true (the breakdowns are the same), then mark the word as "used" and print it to file.
	* c) close the file
	*/

    FileWriter f;
    try {
        f = new FileWriter("output.txt");

        Word tester;
        for (int i = 0; i < wcount; i++) {
            if (!(wordList[i].isUsed())) {
                tester = wordList[i];
                for (int j = 0; j < wcount; j++) {
                    if (tester.compare(wordList[j])) {
                        wordList[j].markUsed();
                        f.write(wordList[j].getVal() + " ");
                    }
                }
            f.write(System.getProperty("line.separator"));
            }
        }
        System.out.println("Anagrams successfully written to output.txt!");
        f.close();
    }
    catch (IOException i){
        System.out.print("Unexpected I/O exception.");
        System.exit(0);
    }
	}
}
