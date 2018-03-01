import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.util.ArrayList;

public class Anagram2{
	public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        //List to store all the created Word objects
        ArrayList<Word> wordlist = new ArrayList<>();

        //counter for the number of words input
        int wcount=0;

        //gets name (and location, if needed) of input file; can be .dat or .txt
        System.out.println("Please enter the name (and location, if in a different directory) of the input file: ");
        String inputname = scan.nextLine();


        /*///!!\\\ I N P U T ///!!\\\*/
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
                        wordlist.add(new Word(cur));
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

        /*///!!\\\ O U T P U T ///!!\\\*/
        FileWriter f;
        try {
            f = new FileWriter("output.txt");

            Word tester;
            for (int i = 0; i < wcount; i++) {
                if (!(wordlist.get(i).isUsed())) {
                    tester = wordlist.get(i);
                    for (int j = 0; j < wcount; j++) {
                        if (tester.compare(wordlist.get(j))) {
                            wordlist.get(j).markUsed();
                            f.write(wordlist.get(j).getVal() + " ");
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