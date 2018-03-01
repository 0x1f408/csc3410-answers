//bank.java
/*0x1f408
* Purpose: simulate a bank's operation for 2 minutes of realtime; use Queues to assign Customers to tellers, then return various statistics
* Method:
*   a) Generates random Customers, who need to see a teller for N seconds (randomly generated),
*   b) places those Customers in a Queue (in this case, a LinkedList),
*   c) assigns the Customers at the front of the queue to Tellers as they become available,
*   d) who "finish" with the Customer after the earlier generated N seconds (defined in the Customer),
*   e) mark themselves available,
*   f) and increment various counters regarding time occupied with a customer, number of customers seen, etc.
* Control:
*   a) bulk of the program is wrapped in an outer "while" loop, which is controlled after the first run by user-input data,
*   b) inside of that, most variables are set to instantiated/reinstantiated/redeclared,
*   c) and another while loop, set to run for 120 seconds;
*   d) that while loop performs its operations, then "sleeps" for 0.500 seconds (500ms), then checks to see if a full second has passed
*   e) inside that, most operations & methods are assumed to be checked only once per second.
* Precondition: requires bank.java (this class), Teller.java (for the tellers), and Customer.java (for the customers). Minimal additional user input (for loop control) required.
* Postcondition: prints as customers enter the queue, and prints various statistics after the time period is up (default 120 seconds):
*   a) number of customers served in total,
*   b) number of customers served by each teller and duration,
*   c) and number of customers remaining in the queue.
* Datastructures used:
*   a) Queue - for storage of Customers
*   b) array - for storage of Tellers
* */

 import java.time.*;
import java.util.*;

public class bank{
    public static void main(String[]args){
        //I heard you like clocks, so I put clocks in your clocks so you can clock while you clock.
        Clock clock;
        Queue customers;

        Random rand = new Random();
        Scanner scan = new Scanner(System.in);

        Teller[] tellers;
        long nextCustomerAt, current;
        int total, queued, nextCustomerIn;
        String cont = "yes";

        while (cont.equalsIgnoreCase("yes")||cont.equalsIgnoreCase("y")){
            System.out.println("Starting...");

            nextCustomerAt = (System.currentTimeMillis()/1000) + 0;
            nextCustomerIn=0;

            total = 0;
            queued = 0;
            customers = new LinkedList();

            //create and fill the array of Tellers
            tellers = new Teller[5];
            for (int i = 0; i < tellers.length;i++){
                tellers[i] = new Teller();
                tellers[i].next(new Customer((rand.nextInt(4)+2)));
            }

            //start of the time block
            long start = System.currentTimeMillis()/1000;

            //end of the time block (set at the end of every pass; mainly used as loop control)
            long stop = System.currentTimeMillis()/1000;

            //bulk of the program goes here
            while((stop-start)<120){
                current = System.currentTimeMillis()/1000;

                //check if time actually increased by a second...
                if (current>stop){
                    //do stuff
                    if (nextCustomerIn==0){
                        //"Customers will arrive randomly between 2 and 5 seconds"
                        customers.add(new Customer((rand.nextInt(4)+2)));
                        System.out.println("A new customer (#"+ ++queued +") entered the queue.");

                        //customers to arrive randomly between 2 and 6 seconds - rng 5ex, +2
                        nextCustomerAt = (System.currentTimeMillis()/1000) + (long)(rand.nextInt(5)+2);
                        nextCustomerIn=(rand.nextInt(5)+2);
                    }
                    else{
                        nextCustomerIn--;
                    }

                    //check if any tellers are available to take new customers; give them one if so and if one is available (else null)
                    for (int j=0;j<tellers.length;j++){
                        if (tellers[j].isAvailable()){
                            tellers[j].next((Customer)(customers.poll()));
                        }
                        else {
                            tellers[j].update();
                        }
                    }
                }
                //mark the time this loop stopped
                stop = System.currentTimeMillis()/1000;

                //wait 1/2 second (500 ms); cut down on needless operations
                try{
                    Thread.sleep(500); //maybe System.sleep(int n)?
                }
                catch(InterruptedException i){
                    System.out.println("oops. Something's not quite right here...");
                }
            }

            queued = 0;

            //purge the queue and check how many customers remain
            while(customers.poll()!=null){
                total++;
                queued++;
            }

            //print out statistics from this run
            System.out.println("\nTotal customers helped (per teller): ");
            for (int i = 0; i<tellers.length;i++){
                int temp = tellers[i].numHelped();
                System.out.println("Teller #"+(i+1)+": "+temp+" customers, at "+tellers[i].timeSpent()+" seconds.");
                total+=temp;
            }
            System.out.println("\nTotal number of customers: " + total);
            System.out.println("Remaining in queue: "+queued);


            //prompt for next run
            System.out.print("\nRun again (Y/N)?: ");
            cont = scan.next();
        }
        System.out.print("\nTerminating...");
    }
}