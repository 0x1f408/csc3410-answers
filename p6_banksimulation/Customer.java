public class Customer{
	//the amount of time (seconds) the customer wants to spend with a teller
	int time_desired;

	public Customer(int t){
		time_desired = t;
	}

	//return how much time the customer wants to spend with a given Teller
	public int getRequest(){
		return time_desired;
    }
}
