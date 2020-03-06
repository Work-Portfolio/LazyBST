package lazyTrees;

/**
 *  Interface used in traversing a list.
 * Created by zachrooney on 2/4/17.
 */
public interface Traverser<E> {


    /**
     *
     * @param x
     */
    public void visit(E x);
}
