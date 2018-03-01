import java.util.Random;

public class SelectionSort_0x1f408{
	public static void main(String[]args){
		/*Write a program to generate 10 random numbers in an array,
		* sort it using SELECTION sortPrint the sorted array.
		*/

        //create a new 10-number array of random numbers 0-50
		Random randgen = new Random();
		int[] list = new int[10];
		for (int i=0;i<10;i++){
			list[i]=randgen.nextInt(50);
		}

        //sort the list from least to greatest
        for (int j=0;j<list.length;j++){
            int index=j;
            int hold=list[index];
			for (int k=j;k<list.length;k++){
				if (list[k]<list[index]){
					index=k;
				}
			}
            hold = list[index];
            list[index]=list[j];
            list[j]=hold;
		}

        //print out the final list; the if statement is just there for formatting
		for (int l=0;l<list.length;l++){
			System.out.print(list[l]);
			if(l!=list.length-1){
				System.out.print(", ");
			}
		}
	}
}