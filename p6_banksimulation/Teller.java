//bank.java
//-> Teller.java
//**create an array Tellers in bank.java to hold each of the Teller objects
//**queue "Customers" in bank.java

public class Teller{
	//whether or not the teller is available to the next customer
	boolean available;
	//time the teller was occupied (in seconds) and the number of customers "helped"
	int time_occupied, customers_helped;
    //time until the teller is done with the current customer
    int current_done;

	public Teller(){
		available = true;
		time_occupied = 0;
		customers_helped = 0;
	}
	
	//feed the next customer to the teller; do we also need to take time as a param?
	public void next(Customer n){
		//make sure the passed "Customer" is actually valid (i.e.: the queue is not empty)
        if (n!=null){
            current_done = n.getRequest();
            customers_helped++;
            available = false;
        }
	}

    //check if a teller is done with their current Customer
    public void update(){
        if (current_done<=0){
            available = true;
        }
        //my thought is that the update will only be called once a second, I can save a few System calls here (not requesting time)
        current_done--;
        time_occupied++;
    }

    //return number of customers helped
    public int numHelped(){
        return customers_helped;
    }

    //return the total amount of time (in seconds) this teller was occupied
    public int timeSpent(){
        return time_occupied;
    }
	//set whether the teller is available or not
	/*public void setAvailabile(boolean avbl){
		available = avbl;
	}
	*/
	//get whether the teller is available
	public boolean isAvailable(){
		return available;
	}
}