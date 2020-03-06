package lazyTrees;

/**
 * Function class designed to print an object
 * Created by zachrooney on 2/4/17.
 */
public class PrintObject<E> implements Traverser<E> {


    /**
     * Prints the generic object x
     * @param x
     */
    @Override
    public void visit(E x) {
        System.out.print(x+" ");
    }
}
