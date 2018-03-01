public class Word{
	//Create holding variable for the original word
	private String val, bdval;
	private int tracker;
	private boolean used;
	StringBuffer stval = new StringBuffer();
	
	//boolean value to check equivalency of a given word's breakdown to another's

	//Construct Word class; original value, input as wrd, stored in String val
	public Word(String wrd){
		tracker=0;
		used=false;
		val=wrd;
		bdval=this.breakdown(val.toLowerCase());
	}
	
	//breakdown input String val into its component letters, sort into alphabetical order
	public String breakdown(String wrd){
		for (int i=0;i<wrd.length();i++){
			//gets character at index i of String wrd
			char chtest=wrd.charAt(i);
			
			//gets Unicode value of chtest
			int chval=Character.getNumericValue(chtest);
			//check if unicode value falls in the range of values assigned to lowercase letters a-z 
			if (this.isLetter(chval)){
				//tracker for the number of valid letters is incremented
				tracker++;

				/*!! ALPHABETIC SORT !!
				 unless I completely screwed this up, this SHOULD
				 1)run a loop every time a new chval is evaluated as being a letter,
				 2)loop duration is tracker, or the number of letters in the stringBuffer stval so far 
				 3)checks if numeric value of the character at the index is greater than value of current letter
				 4)inserts char[acter] chtest at index k
				 */

                //mark whether the given letter has been inserted already; for loop control.
                boolean instd=false;
                for (int k=0;k<stval.length();k++) {
                    if (Character.getNumericValue(stval.charAt(k)) <= chval) {
                        stval.insert(k, chtest);
                        instd=true;
                        break;
                    }
                }
                if (!instd) {
                    stval.append(chtest);
                }
			}
		}
        //convert to a String object and return
	    return (stval.toString());
	}
	
	private boolean isLetter(int ch){
		/*check if the test value of char falls in the range of values assigned to lowercase letters a-z (in UnicodeASCII);
		 return true only if value is INSIDE the approved range (i.e. character IS a letter)
		 all characters are lowercase already, so we don't have to worry about checking for uppercase
		*/
		return (ch>=10 && ch<=35);
	}
	
	//return original input value
	public String getVal(){
		return val;
	}
	
	//return anagram breakdown
	public String getBD(){
		return bdval;
	}
	
	//return anagram length
	public int getAlen(){
		return tracker;
	}
	
	//track whether or not the word has been used (when calculating output)
	public void markUsed(){
		used=true;
	}
	public boolean isUsed(){
		return used;
	}
    public boolean compare(Word testee){
        if ((this.getBD()==null)||(testee.getBD()==null))
            return false;
        else
            return(this.getBD().equals(testee.getBD()));
    }
}