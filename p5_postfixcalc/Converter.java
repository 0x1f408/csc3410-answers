import java.util.*;

public class Converter{
	String in, post, reason, delims, tester, op, evaltkn;
	boolean valid, prevWasOperand, variable;
	Stack ops, evalops;
	StringTokenizer tkn, evalst;

    boolean closed = false;
    String lparen = "(";
    int result;
	
	public Converter(){
		//build an empty stack to store operators in
		ops = new Stack();
		//initialize error messages/control variables
		valid = true;
		prevWasOperand=false;
        //keep track of whether the expression has a variable, x
        variable = false;
		reason = "Expression is empty";
		//make an empty output string
		post = "";
        result = 0;
		
		//define delimiters for Tokenizer
		delims = "+-*/%().x ";
	}

	//attempt to build a postfix string; return 
	public boolean build(String infix){
		tkn = new StringTokenizer(infix,delims,true);
		//build a postfix string from the infix
		/*
		* mark conditions for boolean VALID to be true/false:
		* Valid will be FALSE if...
		* a) after parsing, a left parenthese remains '(',
		* b) a right parenthese ')' is entered while no left parenthese is on the stack,
		* c) the first value entered is an operator,
		* d) the last value entered is an operator,
		* e) two operands are entered in a row without an operator,
		* f) two (non-unary) operators are entered in a row, e.g. ** or %/
		* */
		while (valid&&tkn.hasMoreTokens()){
			op = tkn.nextToken();

            //push left parenthese to the stack
            if (op.equals("(")){
                ops.push(op);
            }
            //clear most recent left parenthese from the stack when a right parenthese is found
            else if (op.equals(")")){
                System.out.println(post);

                valid = false;
                while(!valid){
                    if (!ops.empty()) {
                        String tester = ops.peek().toString();

                        System.out.println("X," + tester + "," + (int) tester.charAt(0));
                        System.out.println((tester.equals("(")||tester=="("));
                        if (tester.equals("(")||tester=="(") {
                            System.out.println("Y");
                            //do stuff
                            valid = true;
                            ops.pop();
                            break;
                        }
                        post += ops.pop().toString();
                    }
                    else {
                        reason = "Missing a left parenthese!";
                        return valid = false;
                    }
                }
            }

            //if previous value was an OPERAND... (initialized to FALSE)
            else if (prevWasOperand){
                //if token is another operand, valid = false;
				if ((op.charAt(0)>=0&&op.charAt(0)<=9)||op.equals("x")) {
                    valid = false;
                }
                else if (op.equals("*")||op.equals("/")||op.equals("%")){
                    //pop current value on top of stack if same precedence, then push; just push if same precedence
                    if (!ops.empty()) {
                        //create a temporary testing variable, mostly for the sake of neatness/minor efficiency
                        tester=ops.peek().toString();
                        if (tester.equals("*")||tester.equals("/")||tester.equals("%")){
                            post+=ops.pop();
                        }

                    }
                    //since these are the highest priority operators, they'll be pushed in all cases
                    ops.push(op);
                }
                else if (op.equals("+")||op.equals("-")){
                    if (!ops.empty()){
                        String tester = ops.peek().toString();
                        if (!tester.equals("(")&&!tester.equals(")")) {
                            post += ops.pop();
                        }
                    }
                    ops.push(op);
                }
                prevWasOperand=false;
			}
			//else (if previous value was an OPERATOR...
			else{
                //token is a *, /, or %, valid = false;
				if (op.equals("*")||op.equals("/")||op.equals("%")){
                    valid = false;
                    reason = "Operator "+ op + " cannot follow an operator!";
                }
                else if (op.equals("-")){
                    post+="_";
                }
                else if (op.equals("+")){
                    //do nothing
                }
                else if (Character.isDigit(op.charAt(0))){
                    post+=op;
                    prevWasOperand=true;
                }
                else if (op.equals("x")){
                    post+=op;
                    variable = true;
                    prevWasOperand=true;
                }
                //just to prevent this getting picked up by the ELSE
                else if (op.equals("(")||op.equals(")"));

                else{
                    valid = false;
                    reason = "Operator " + op + " is not a valid operator! (1)";
                }
			}


            //add a space onto the postfix string to improve readability
            post+=" ";
		}

        //if the last value was an operand and there are no operators applicable...
        if (ops.empty()&&prevWasOperand) {
            valid = false;
        }

        //second pass to clear the rest of the stack
        while (!ops.empty()&&valid){
            String tester = ops.peek().toString();
            if (tester.equals("(")){
                valid = false;
                reason = "Statement is missing a right parenthese \')\'";
            }
            else post+=ops.pop();
        }

        //return and terminate the build() method
		return valid;
	}
    //evaluate the postfix expression with a variable X present
    public int eval(int x){
        evalst = new StringTokenizer(post,delims+"_",true);
        evalops = new Stack();

        //loop through the postfix string
        while (evalst.hasMoreTokens()){
            evaltkn=evalst.nextToken();
            System.out.println(post + ", " + evaltkn);
            if ((Character.isDigit(evaltkn.charAt(0)))){
                evalops.push(evaltkn);
            }
            else{
                switch(evaltkn){
                    case "+":
                        evalops.push(Integer.parseInt(evalops.pop().toString())+(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "-":
                        evalops.push(Integer.parseInt(evalops.pop().toString())-(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "*":
                        evalops.push(Integer.parseInt(evalops.pop().toString())*(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "/":
                        evalops.push(Integer.parseInt(evalops.pop().toString())/(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "%":
                        evalops.push(Integer.parseInt(evalops.pop().toString())%(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "_":
                        //make the number before the _ negative
                        evalops.push(0-(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "x":
                        evalops.push(x);
                        break;
                }
            }
        }
        result = Integer.parseInt(evalops.pop().toString());
        return result;
    }
    //evaluate the postfix expression with no variables present
    public int eval(){
        evalst = new StringTokenizer(post,delims+"_",true);
        evalops = new Stack();

        System.out.println(post + ", " + evaltkn);

        //loop through the postfix string
        while (evalst.hasMoreTokens()){
            evaltkn=evalst.nextToken();
            if (Character.isDigit(evaltkn.charAt(0))){
                evalops.push(evaltkn);
            }
            else{
                switch(evaltkn){
                    case "+":
                        evalops.push(Integer.parseInt(evalops.pop().toString())+(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "-":
                        evalops.push(Integer.parseInt(evalops.pop().toString())-(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "*":
                        evalops.push(Integer.parseInt(evalops.pop().toString())*(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "/":
                        evalops.push(Integer.parseInt(evalops.pop().toString())/(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "%":
                        evalops.push(Integer.parseInt(evalops.pop().toString())%(Integer.parseInt(evalops.pop().toString())));
                        break;
                    case "_":
                        //make the number before the _ negative
                        evalops.push(0-(Integer.parseInt(evalops.pop().toString())));
                        break;
                }
            }
        }
        result = Integer.parseInt(evalops.pop().toString());
        return result;
    }
	//if the expression is valid; control for the driver to continue
	public boolean isValid(){
	    return valid;
	}
    //return whether or not the expression has a variable
    public boolean hasVariable(){
        return variable;
    }
    //return failure reason
    public String getReason(){
        return reason;
    }
    public String getPost(){
        return post;
    }
}