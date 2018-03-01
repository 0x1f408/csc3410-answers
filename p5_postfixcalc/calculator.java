/*Calculator.java
* 0x1f408
* Requires: Converter.java, for infix-to-postfix conversion
*
* Description: the main (driver) class for the calculator project: it takes a String (assumed to be an infix equation of unknown validity), and changes it to a postfix equation with a Converter object
*
* Purpose:
* a) take an input string expression and pass it into the converter,
* b) which parses it token by token,
* c) adding operands and maintaining a stack, to generate a postfix String,
* d) and return it to the driver (calculator);
* THEN:
* e) if a variable 'x' was entered as part of the prefix string, build a loop to test various user-input values for x in the post-fix expression, until 'q' is entered
* f) else, evaluate the string and print the results.
*
* Conditions:
* a) terminate the program and return an appropriate error message if an error is encountered
* b) in-fix expressions must be valid; else, an error message will be generated.
* c) require additional user input if a variable x is specified; else, run once and terminate/
**/

import java.util.*;

public class calculator{
	public static void main(String[] args){
		Converter con = new Converter();
        Scanner in = new Scanner(System.in);

        String infix;
        int var;

        //request the infix string
        System.out.print("Enter infix expression: ");
        infix = in.next();

        //Just for the sake of neatness/formatting...
        System.out.println("\n");

        //tracker for whether a given conversion was successful
        //the build() method returns true/false and another method [getPost()] will be needed to get the actual postfix String.
        //literally 90% of what goes on in this program happens in the this method; explained inline in Converter.java
        boolean success = con.build(infix);

        if(success){
            System.out.println("Converted expression: "+con.getPost());
            if(con.hasVariable()) {
                //control string for the while loop
                String cont = "q";

                //Evaluate the expression (with variables) by requesting user input for variable values; quit when user enters "q"
                while (cont.equalsIgnoreCase("q")) {
                    System.out.print("Enter a value of x (q to quit): ");
                    cont = in.next();

                    if (!cont.equalsIgnoreCase("q")){
                        //eval() the expression given
                        try{
                            var = Integer.parseInt(cont);
                            System.out.println("Answer to expression: "+con.eval(var));
                        }
                        catch(NumberFormatException n){
                            System.out.println("Error in expression! Invalid [int] value!");
                        }
                        catch(EmptyStackException e){
                            System.out.println("Woops! Something's not quite right here...");
                        }
                    }
                }
            }
            else{
                System.out.println("Answer to expression: " + con.eval());
            }
        }

        else{
            //print an error message + the reason
            System.out.print("Error in expression! "+con.getReason());
        }
	}

}