public class NTree{
    NTree current, left, right, root;
    Object contents;

    //create a new Tree (this is the root, the start of the full tree!)
    public NTree(){
        root = null;
    }

    //this will be to create new [sub]trees
    public NTree(int val){
        contents = val;
    }

    //add a tree (assuming new NTree(val) given by the driver...
    public void insert(NTree tree){
        //if this is the first value given...
        if (root==null){
            //this tree is the root
            root = tree;
            //System.out.println("Root given an initial value of " + root.getContents());
        }

        //if this is not the first value given...
        else{
            //start this method at the root of the tree each time it's called
            current = root;
            //control variable - whether or not the given tree (parameter) has been placed
            boolean inserted = false;
            //while the tree we're attempting to insert hasn't yet been placed...
            while(!inserted){
                //check if it's less than or equal to (int) contents of current tree (<=)...
                if ((int)tree.getContents()<=(int)current.getContents()){
                    //if the current tree has a left child already...
                    if (current.hasLeft()){
                        //then set this left branch to current and loop through again
                        current = current.getLeft();
                        //System.out.println("node taken - moving to "+current.getContents());
                    }
                    else{
                        //set the new tree as the child of the current
                        current.setLeft(tree);

                        if (current == root) {
                            //System.out.println(current.getContents()+" set as left child of root!");
                            root = current;
                        }

                        inserted = true;
                        //System.out.println("inserted " + tree.getContents()+" under (left) "+ current.getContents());
                    }
                }
                //otherwise, if it's greater than (int) contents of current tree (>)...
                else{
                    //if the current tree has a right child already...
                    if (current.hasRight()){
                        current = current.getRight();
                        //System.out.println("node taken - moving to "+current.getContents());
                    }
                    else{
                        //set the new tree as the child of the current
                        current.setRight(tree);

                        if (current == root) {
                            //System.out.println(current.getContents()+" set as right child of root!");
                            root = current;
                        }

                        inserted = true;
                        //System.out.println("inserted " + tree.getContents()+" under (right) "+ current.getContents());
                    }
                }
            }
        }
    }

    //quick reference for print from the driver (because "tree.infix(tree)" looks kinda odd)
    public void print(){
        infix(root);
    }

    //headache inbound... (this is case-in-point why I hate recursion)
    //recursively print all values in the tree
    public void infix(NTree tree){
        //if the current tree has a left child...
        if (tree.hasLeft())
            //call infix() on the left child
            infix(tree.getLeft());
        //print contents of current tree
        System.out.print((int)tree.getContents()+ " ");
        //if the current tree has a right child...
        if (tree.hasRight())
            //call infix() on the right child
            infix(tree.getRight());
    }

    //check for left & right children
    public boolean hasLeft(){
        return (left!=null);
    }
    public boolean hasRight(){
        return (right!=null);
    }
    //get left & right children
    public NTree getLeft(){
        return left;
    }
    public NTree getRight(){
        return right;
    }

    //set the left child (child < parent)
    public void setLeft(NTree ltree){
        left = ltree;
    }
    //set the right child (child > parent)
    public void setRight(NTree rtree){
        right = rtree;
    }

    //retrieve contents
    public Object getContents(){
        return contents;
    }
}