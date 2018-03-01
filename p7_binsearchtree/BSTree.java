/*0x1f408
* Purpose: generate an array of random integer values and produce a [sorted] binary tree with them, then printing that tree recursively.
* Method:
*   a) creates an array of size 50 and an empty NTree (this tree is our full tree),
*   b) pass each value of the array into a new NTree and place it appropriately in the full tree,
*       i. the first value inserted becomes the new root of the full tree(n=0),
*       ii. each subsequent value is compared against each node (subtree), (n=0,1,2...)
*       iii. lesser values (val <= contents_of_the_NTree) are passed left,
*       iv. greater values (val > contents_of_the_NTree) are passed right,
*       v. with empty nodes (children) replaced as required
*       vi. the inserted NTree becomes the [left/right] child of the NTree under which it is placed.
*   c) finally, print the full tree with print(), which calls the recursive infix(NTree tree) method,
*       i. print calls infix(root), meaning the first comparison will be against the top level of the tree (n=0),
*       ii. infix checks for a left child of the given tree, calling itself with that child as its argument if one is found
*       iii. contents of the current NTree is printed (and each above, in LIFO order),
*       iv. processes ii & iii are repeated for the right children of the given tree
* Precondition: requires this class (BSTree.java) and a tree class (NTree.java)
* Postcondition: prints the contents of the array (unsorted), and the tree (sorted)
* Datastructures used:
*   a) array - for storing the initial values
*   b) binary tree NTree - for storing/sorting/printing the input values
*       i. it's basically a giant tree made up of nothing but smaller trees,
*       ii. the initially declared tree (just "tree" in the driver is the root, which is then replaced with the first input value).
**/

import java.util.*;

public class BSTree{
    public static void main(String[]args){
        //create an array (size 50) to hold our 50 randomly generated numbers
        int[] NList = new int[50];
        Random rand = new Random();

        //create a new blank tree object (this will initially be root)
        NTree tree = new NTree();

        System.out.println("Initial list: ");

        //fill the array created earlier
        for (int i:NList){
            i = rand.nextInt(99)+1;
            System.out.print(i + " ");

            //add values into the tree as they're added to the array
            tree.insert(new NTree(i));
        }

        System.out.println("Sorted tree: ");
        tree.print();
    }
}