/*bankdata.java - driver class
* 0x1f408
*
* requires: BankAccount.java (store actual account information), LList.java (Linked List-style datastructure)
* Reads in input commands, printing the menu each time until told to stop; executes user commands/modifications. Uses a custom Linked-List-style structure to hold account data; navigates it through the use of nodes.
* Commands:
*	1: Show all Records - prints out a list of the contents of each Node, in LList.java
*	2: Delete the current record (Node) - removes all links from the selected record, instead connecting the preceding and proceeding Nodes.[1]
*	3, 4, 6: Modify name & telephone number values for the selected account, move it to an appropriate new location in the list, if required.[1]
*	5: Add a new record; it will be either inserted into the list so it falls in alphanumerical order (last name, first name, number[if required]).
*	7, 8: Add or remove funds from the selected account; prevent funds from being removed if the amount being withdrawn is greater than the amount in the account.[1]
*	9: Search for an account to make the current account; must match the first and last name of the account owner.
*	10: Exit the program (instantly mark the "while" loop condition 'Active' as false
*Conditions:
*	[1]: an account must be selected; else, the attempted operation will fail - this is verified with boolean curSelected(), which also prints a message to console if it evaluates to FALSE.
*	[2]: numbers must be entered while choosing menu options; else, an error message will be printed and the loop will repeat.
*Classes:
	* bankdata.java - this one. Driver class - mainly consists of the I/O operations and menu(s), and a few possible exceptions.
	 * BankAccount.java - stores bank account information - owner name, phone number, balance.
	 * LList.java - contains the actual Linked List-style framework and the Node class, and controls what is transferred between BankAccount, the Nodes, and the Driver.
 * Purpose: Takes user-input names, [phone] numbers, and [account] balances, and creates a BankAccount object, stored in a Linked List, sorts them, and allows the user to perform various manipulations on them (changing owner data, depositing/withdrawing cash), while remaining sorted.
*/

//importing mostly for the Scanner class and Exceptions (inputMismatchException)
import java.util.*;

public class bankdata{
	public static void main(String[]args){
		Scanner scan = new Scanner(System.in);
		String fn, ln;
		boolean active = true;
		int inp, num;
		double bal;
		LList list = new LList();
		
		while (active){
			//Menu block
			System.out.println("1\tShow all records.");
			System.out.println("2\tDelete the current record.");
			System.out.println("3\tChange the first name in the current record.");
			System.out.println("4\tChange the last name in the current record.");
			System.out.println("5\tAdd a new record.");
			System.out.println("6\tChange the phone number in the current record.");
			System.out.println("7\tAdd a deposit to the current balance in the current record.");
			System.out.println("8\tMake a withdrawal from the current record, if there are sufficient funds.");
			System.out.println("9\tSelect a record from the record list to become the current record.");
			System.out.println("10\tQuit.");
			
			System.out.println("\nEnter a command from the list above (10 to quit): ");
			try{
				inp = scan.nextInt();
				switch(inp){
					case 1: //show all records
						list.printAll();
						break;
					case 2: //Delete current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
                        if (list.curSelected()) {
                            list.remove();
                        }
                        break;
					case 3: //change first name in current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
                        if (list.curSelected()) {
                            System.out.print("\nEnter new first name: ");
                            fn = scan.next();
                            list.changeFirst(fn);
                        }
						break;
					case 4: //change last name in current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
                        if (list.curSelected()) {
                            System.out.print("\nEnter new last name: ");
                            ln = scan.next();
                            list.changeLast(ln);
                        }
                        break;
					case 5: //add a new record
                        System.out.print("Enter first name: ");
						fn = scan.next();
						System.out.print("\nEnter last name: ");
						ln = scan.next();
						System.out.print("\nEnter phone number: ");
						num = scan.nextInt();
						System.out.print("\nEnter balance: ");
						bal = scan.nextDouble();
						BankAccount tmp = new BankAccount(ln, fn, num, bal);
                        list.add(tmp);
                        System.out.println("\n"+tmp.printRecord());
						break;
					case 6: //change phone number in current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
                        if (list.curSelected()) {
                            System.out.print("\nEnter new phone number: ");
                            num = scan.nextInt();
                            list.changeNum(num);
                        }
						break;
					case 7: //deposit to current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
						if (list.curSelected()){ 
							System.out.print("\nEnter the value to deposit: ");
							list.getCur().getContents().deposit(scan.nextDouble());
						}
						break;
					case 8: //withdraw from current record
                        //CONDITION TO MAKE SURE THIS IS VALID - NEED A VALUE SELECTED
						if (list.curSelected()){
							System.out.print("\nEnter the value to withdraw: ");
							list.getCur().getContents().withdraw(scan.nextDouble());
						}
						break;
					case 9: //select a record to become current
						System.out.print("\nEnter first name: ");
						fn = scan.next();
						System.out.print("\nEnter last name: ");
						ln = scan.next();
						if(list.select(ln, fn))
                            System.out.print("\nCurrent record is: " + list.getCur().getContents().printRecord());
                        else
                            System.out.print("\nNo record found for that user!");
                        break;
					case 10: //exit
						active = false;
						break;
					default:
						System.out.println("Invalid selection. Try again.");
				}
			}
			catch(InputMismatchException e){
                //need to figure out how to keep this from going into an infinite loop! - NVM fixed it, scan.next() clears current token.
				System.out.println("Illegal command");
                scan.next();
			}
            System.out.println("\n");
		}
	}
}