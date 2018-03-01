public class BankAccount{
	private String lastname, firstname;
	private int number;
	private double balance;
	public BankAccount(String ln, String fn, int n){
		lastname = ln;
		firstname = fn;
		number = n;
		balance = 0.0;
	}
	public BankAccount(String ln, String fn, int n, double b){
		lastname = ln;
		firstname = fn;
		number = n;
		balance = b;
	}
	
	public String printRecord(){
		return (firstname + " " + lastname + ", " + number);
	}
	
	public void deposit(double amt){
		balance += amt;
	}
	public void withdraw(double amt){
		if (balance >= amt)
			balance -= amt;
		else
			System.out.print("Insufficient funds!");
	}
	public void chPhone(int n){
		number = n;
	}
	public void chFirst(String fn){
		firstname = fn;
	}
	public void chLast(String ln){
		lastname = ln;
	}
	public String getFirst(){
		return firstname;
	}
	public String getLast(){
		return lastname;
	}
	public int getNum(){
		return number;
	}
	//returns TRUE if it would be placed before another account
	public boolean compareTo(BankAccount cmp){
		if (lastname.equals(cmp.getLast())){
			if (firstname.equals(cmp.getFirst())){
				//compare phone numbers - do this LAST.
				if (number == cmp.getNum()){
					System.out.print("The two sets are identical!");
					return false;
				}
				return number > cmp.getNum();
			}
		//compare FIRST names - do this SECOND.
		String tempfirst=cmp.getFirst();
		for (int i=0;i<tempfirst.length()&&i<firstname.length();i++){
			if(firstname.charAt(i) < tempfirst.charAt(i)) return true;
			if(firstname.charAt(i) > tempfirst.charAt(i)) return false;
		}
		return (firstname.length()>tempfirst.length());
		}
	//compare LAST names - do this FIRST.
	String templast=cmp.getLast();
	for (int j=0;j<lastname.length()&&j<templast.length();j++){
		if (lastname.charAt(j) < templast.charAt(j)) return true;
		if (lastname.charAt(j) > templast.charAt(j)) return false;
	}
	return (lastname.length()>templast.length());
	}
}