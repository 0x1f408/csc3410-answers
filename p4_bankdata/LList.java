/*
* LinkedList class and Node subclass
* Similar to Java's included LinkedList, but each node has pointers to next AND previous nodes.
* Takes in new BankAccount objects and inserts them at an appropriate location in the chain;
* automatically moves them on modification.
* */

public class LList{
	private Node head;
	private int len, index;
    private Node cur;
	
	//Define a blank LList; Nodes will be added to it later.
	public LList(){
		head = new Node(null);
		len = 0;
        index = 0;
	}

    //add a new Node to the list, given a BankAccount object. Add it to the end of the list if the list is empty; else, sort it.
	public void add(BankAccount ba){
		//temporary node with the BankAccount object we want to add
		Node temp = new Node(ba);
		Node current = head;
        boolean inserted = false;
		
		//crawl the list, one node at a time; SORT AS YOU GO
		while (current.next!=null&&!inserted){
            //M A G I C
            //Inserts the new Node (the one we're trying to add) into an appropriate spot in the list.
            if (current.getContents()!=null&&temp.getContents().compareTo(current.getContents())&&temp.prev()!=null){
                temp.setPrev(current);
                temp.setNext(current.next());
                current.next().setPrev(temp);
                current.setNext(temp);
                inserted=true;
                break;
            }
            current = current.next();
		}
        //appends the new Node to the end of the list, if there was no other appropriate place to add it.
        if (!inserted){
            current.setNext(temp);
            temp.setPrev(current);
        }

	}
    //deletes the CURRENTLY SELECTED record
    public void remove(){
        Node current = cur;
        if (curSelected()) {
            if (current.prev() != null) {
                current.prev().setNext(current.next());
            }
            if (current.next() != null) {
                current.next().setPrev(current.prev());
            }
            cur = null;
        }
    }
    //Accessed only by select(ln,fn); could technically combine them, but I'm lazy.
    private Node search(String ln, String fn){
        Node current=head;
        while (current.next()!=null){
            if (current.getContents()!=null&&current.getContents().getLast().equals(ln)&&current.getContents().getFirst().equals(fn)) return current;
            current = current.next();
        }
        //Will this work? Tune in next time - same bat time, same bat channel!
        return null;
    }

    //Yes, I realize I should have put the following 3 methods into the BankAccount class.
    //change the FIRST name associated with the account
    public String changeFirst(String newFirst){
        if (cur!=null){
            cur.getContents().chFirst(newFirst);
            return modify();
        }
        else
            return("No record selected!");
    }
    //change the LAST name associated with the account
    public String changeLast(String newLast){
        if (cur!=null){
            cur.getContents().chLast(newLast);
            return modify();
        }
        else
            return ("No record selected!");
    }
    //change the PHONE NUMBER associated with the account
    public String changeNum(int num){
        if (curSelected()){
            cur.getContents().chPhone(num);
            return modify();
        }
        return ("No record selected!");
    }
    //call this to shuffle around the NODE if its contents() are modified by the above methods
    private String modify(){
        Node current = cur;
        if (curSelected()){
            remove();
            add(current.getContents());
            cur = current;
            return(cur.getContents().printRecord());
        }
        return ("If you're seeing this, someone messed up bad. Woops.");
    }
    //print the contents() of the current Node
    public void printCur(){
        if (curSelected()) {
            Node current = cur;
            System.out.println("Current record is: " + current.getContents().getFirst() + " " + current.getContents().getLast() + " " + current.getContents().getNum());
        }
    }
    //bankdata.java - Case 1
    public void printAll() {
        Node current = head;
        System.out.println("First Name\tLast Name\tPhone Number");
        System.out.println("----------\t---------\t------------");
        while (current!=null) {
            if (current.getContents()!=null)
                System.out.println(current.getContents().getFirst() + "\t\t" + current.getContents().getLast() + "\t\t" + current.getContents().getNum());
            current = current.next();
        }
    }
    //mark a Node as being currently selected
    public boolean select(String ln, String fn){
        cur = search(ln, fn);
        return (cur!=null);
    }
    //returns whether a non-null current Node is selected
    public boolean curSelected(){
        if (cur==null) System.out.println("No record selected!");
        return (cur!=null);
    }
    //get Current (selected) node. Not to be confused with the local Node current.
    public Node getCur(){
        return cur;
    }

	//!!\\START OF THE NODE OBJECT HERE//!!\\
	//Nodes - defines behavior of the individual blocks of data
	public class Node{
		Node next, prev;
		int contents;
	
		//declare the first & last nodes in the series (head, tail)
		public Node(int inp){
			contents = inp;
			next = null;
			prev = null;
		}
		
		//declare any other node in the series
		public Node(int inp, Node n){
			contents = inp;
			next = n;
			prev = null;
		}
        //identify/retrieve the next node in the List
        public Node next(){
            return next;
        }
        //identify/retrieve the previous node in the List
        public Node prev(){
            return prev;
        }
        //retrieve contents of the node
        public int getContents(){
            return contents;
        }
        //change the pointer to the next node to a new destination
        public void setNext(Node n){
            next = n;
        }
        //change the pointer to the previous node to a new destination
        public void setPrev(Node p){
            prev = p;
        }
	}
}
