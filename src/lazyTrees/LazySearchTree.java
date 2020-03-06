package lazyTrees;

import java.util.*;

/**
 *     This class stores a list of generic items as a BST. It extends Comparable to be able to compare the items stored.
 *     It contains a private helper Class LazySTNode to create the nodes for the BST.
 *
 * Created by zachrooney on 2/4/17.
 */
public class LazySearchTree <E extends Comparable< ? super E > >
    implements Cloneable
{
    private int mSize;
    private LazySTNode mRoot;
    private int mSizeHard;

    /**
     * Constructor that creates a new instance of the class and calls clear() to set the instance variables to 0 or null.
     */
    public LazySearchTree()
    {
        clear();
    }

    /**
     * Boolean that indicates if the list is empty or not
     * @return boolean (mSize == 0)
     */
    public boolean empty()
    {
        return (mSize == 0);
    }

    /**
     * Returns the soft size of the tree (only counts objects not marked as deleted)
     * @return mSize
     */
    public int size()
    {
        return mSize;
    }

    /**
     * Returns the hard size of the tree (counts all objects whether they are marked as deleted or not)
     * @return mSizeHard
     */
    public int sizeHard() {
        return mSizeHard;
    }

    /**
     * Resets class members to 0 or null
     */
    public void clear()
    {
        mSize = 0;
        mRoot = null;
        mSizeHard= 0;
    }

    /**
     * Returns the height of the tree
     * @return int
     */
    public int showHeight()
    {
        return findHeight(mRoot, -1);
    }

    /**
     * Returns the object of least value in the tree
     * @return E x  // Generic Object
     */
    public E findMin()
    {
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMin(mRoot).data;
    }

    /**
     * Returns the object of greatest value in the tree
     * @return E x // Generic Object
     */
    public E findMax()
    {
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMax(mRoot).data;
    }

    /**
     * Searches the tree for a specified object. Returns the object if found or throws
     * NoSuchElementException if not found
     * @param x
     * @return E x  // Generic Object
     */
    public E find( E x )
    {
        LazySTNode resultNode;
        resultNode = find(mRoot, x);
        if (resultNode == null)
            throw new NoSuchElementException();
        return resultNode.data;
    }

    /**
     * Searches the tree for a specified object.
     * @param x
     * @return boolean
     */
    public boolean contains(E x)
    { return find(mRoot, x) != null; }

    /**
     * Inserts an object into the tree. Returns a boolean denoting if addition was successful.
     * @param x
     * @return boolean
     */
    public boolean insert( E x )
    {
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Removes a specified object from the tree. Returns a boolean denoting if removal was successful.
     * @param x
     * @return
     */
    public boolean remove( E x )
    {
        int oldSize = mSize;
        //mRoot = remove(mRoot, x);  //original HARD REMOVE,
        remove(mRoot,x);   //Lazy remove
        return (mSize != oldSize);
    }

    /**
     * Searches Lazy Search Tree for nodes marked as deleted and hard removes them. Returns boolean
     * indicating if the size of the tree has changed
     * @return
     */
    public boolean collectGarbage() {
        int oldSize = mSize;
        collectGarbage(mRoot);
        return (mSize != oldSize);
    }

    /**
     * Prints the tree regardless of lazy deletion notation
     * @param printObject
     */
    public void traverseHard(PrintObject<E> printObject) {
        traverseHard(printObject,mRoot);
    }

    /**
     * Prints objects in the tree that are not marked as deleted
     * @param printObject
     */
    public void traverseSoft(PrintObject<E> printObject) {
        traverseSoft(printObject,mRoot);
    }

    /**
     * Returns a shallow copy of the tree
     * @return newObject
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        LazySearchTree<E> newObject = (LazySearchTree<E>)super.clone();
        newObject.clear();  // can't point to other's data

        newObject.mRoot = cloneSubtree(mRoot);
        newObject.mSize = mSize;

        return newObject;
    }

    // --------------------------private helper methods ----------------------------------------

    /**
     * Returns the object of least value in the tree regardless if it is marked as deleted or not
     * (the true minimum node)
     * @param root
     * @return
     */
    protected LazySTNode findMinHard(LazySTNode root){

        if (root == null)
            return null;

        if (root.lftChild != null)
            findMinHard(root.lftChild);


            return root;
        }


    /**
     * Returns the object of least value in the tree. Designed for recursive use
     * @param root
     * @return root
     */
    protected LazySTNode findMin(LazySTNode root )
    {
        if (root == null)
            return null;

        if (!root.deleted  && root.lftChild==null){
            return root;
        }

        if (root.deleted && root.lftChild == null){
            return findMin(root.rtChild);
        }

        LazySTNode test = findMin(root.lftChild);
        if (test != null){
            return test;
        }
        else if (root.deleted)
        {
            return findMin(root.rtChild);
        }
        else {
            return root;
        }
    }

    /**
     * Returns the object of greatest value in the tree regardless if it is marked as deleted or not
     * (the true maximum node)
     * @param root
     * @return
     */
    protected LazySTNode findMaxHard(LazySTNode root){

        if (root == null)
            return null;

        if (root.rtChild != null)
             findMaxHard(root.rtChild);

        return root;
    }

    /**
     * Returns the object of greatest value in the tree that is not marked as deleted.
     * Designed for recursive use
     * @param root
     * @return root
     */
    protected LazySTNode findMax(LazySTNode root )
    {
        if (root == null)
            return null;

        if (!root.deleted  && root.rtChild == null){
            return root;
        }

        if (root.deleted && root.rtChild == null){
            return findMax(root.lftChild);
        }

        LazySTNode test = findMax(root.rtChild);
        if (test != null){
            return test;
        }
        else if (root.deleted)
        {
            return findMax(root.lftChild);
        }
        else {
            return root;
        }

    }

    /**
     * Inserts a specified object into the tree.
     * Designed for recursive use
     * @param root
     * @param x
     * @return root
     */
    protected LazySTNode insert(LazySTNode root, E x )
    {
        int compareResult;  // avoid multiple calls to compareTo()



        if (root == null)
        {
            mSizeHard++;
            mSize++;
            return new LazySTNode(x, null, null);
        }
        compareResult = x.compareTo(root.data);

        if ( compareResult == 0 && root.deleted) {
            root.deleted = false;
            mSize++;
            return root;}

        else if ( compareResult < 0 )
            root.lftChild = insert(root.lftChild, x);

        else if ( compareResult > 0 )
            root.rtChild = insert(root.rtChild, x);

        return root;
    }

    /**
     * Searches tree for a specified object and marks it as deleted if found (Lazy Deletion)
     * Designed for recursive use
     * @param root
     * @param x
     */
    protected void remove (LazySTNode root, E x  ){
        LazySTNode tempNode = find(root, x);
        if (tempNode != null) {
            tempNode.deleted = true;
            mSize--;
        }
        if (tempNode == null){                          // For debugging
            System.out.println("ERROR REMOVE CALLED BUT REQUESTED OBJECT NOT FOUND");
        }
    }

    /**
     * Checks the tree for nodes marked as deleted (soft deleted) and removes them.
     */
    protected void collectGarbage(LazySTNode root) {
        if (root == null)
            return;

        if (root.lftChild != null)
            collectGarbage(root.lftChild);
        if (root.rtChild != null)
            collectGarbage(root.rtChild);
        if (root.deleted)
            removeHard(mRoot,root.data);

    }

    /**
     * Removes a specified object from the tree
     * @param root
     * @param x
     * @return root
     */
    protected LazySTNode removeHard(LazySTNode root, E x  )
    {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if ( compareResult < 0 )
            root.lftChild = removeHard(root.lftChild, x);
        else if ( compareResult > 0 )
            root.rtChild = removeHard(root.rtChild, x);

           // found the node
        else if (root.lftChild != null && root.rtChild != null)
        {
            root.data = findMin(root.rtChild).data;
            root.deleted = false;
            root.rtChild = removeHard(root.rtChild, root.data);
        }
        else
        {
            if (root == mRoot) {
                root =
                        (root.lftChild != null) ? root.lftChild : root.rtChild;
                mRoot = root;
                mSizeHard--;
            }
            else {
                root =
                        (root.lftChild != null) ? root.lftChild : root.rtChild;
                mSizeHard--;
            }
        }
        return root;

    }

    /**
     * Searches the tree for a specified object and returns it if found
     * @param root
     * @param x
     * @return root
     */
    protected LazySTNode find(LazySTNode root, E x )
    {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult == 0 && root.deleted)
            return null;
        if (compareResult < 0)
            return find(root.lftChild, x);
        if (compareResult > 0)
            return find(root.rtChild, x);
        return root;   // found
    }

    /**
     * Clones a portion of the tree
     * @param root
     * @return
     */
    protected LazySTNode cloneSubtree(LazySTNode root)
    {
        LazySTNode newNode;
        if (root == null)
            return null;

        // does not set myRoot which must be done by caller
        newNode = new LazySTNode
                (
                        root.data,
                        cloneSubtree(root.lftChild),
                        cloneSubtree(root.rtChild)
                );
        return newNode;
    }

    /**
     * Returns the height of a node in the tree
     * @param treeNode
     * @param height
     * @return
     */
    protected int findHeight(LazySTNode treeNode, int height )
    {
        int leftHeight, rightHeight;
        if (treeNode == null)
            return height;
        height++;
        leftHeight = findHeight(treeNode.lftChild, height);
        rightHeight = findHeight(treeNode.rtChild, height);
        return (leftHeight > rightHeight)? leftHeight : rightHeight;
    }

    /**
     * Prints all objects in the tree
     * @param printObject
     */
    protected void traverseHard(PrintObject<E> printObject, LazySTNode treeNode )
    {
        if (treeNode == null)
            return;

        traverseHard(printObject, treeNode.lftChild);
        printObject.visit(treeNode.data);
        traverseHard(printObject, treeNode.rtChild);
    }

    /**
     * Prints all objects in the tree that are not marked as deleted
     * @param printObject
     */
    protected void traverseSoft(PrintObject<E> printObject, LazySTNode treeNode) {
        if (treeNode == null)
            return;

        traverseSoft(printObject, treeNode.lftChild);
        if (!treeNode.deleted) printObject.visit(treeNode.data);
        traverseSoft(printObject, treeNode.rtChild);
    }


    /**
     *  Private helper class. Container class used to wrap elements to be stored in the tree.
     * @param
     */
    private class LazySTNode
    {
        // use public access so the tree or other classes can access members 
        private LazySTNode lftChild, rtChild;
        private E data;
        private LazySTNode myRoot;  // needed to test for certain error
        private boolean deleted;

        /**
         * Creates Node object to be used in LazySearchTree
         * @param d
         * @param lft
         * @param rt
         */
        public LazySTNode(E d, LazySTNode lft, LazySTNode rt )
        {
            lftChild = lft;
            rtChild = rt;
            data = d;
            deleted = false;
        }

        /**
         * Generic Constructor
         */
        public LazySTNode()
        {
            this(null, null, null);
        }
    }
}