/**
 * 
 */
package implementations;

/**
 * @param <E>
 * 
 */
public class MyDLLNode<E> {

	/**
	 * 
	 */
	public MyDLLNode() {
		// TODO Auto-generated constructor stub
	}
	
	E data;
    MyDLLNode<E> next;
    MyDLLNode<E> prev;

    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public MyDLLNode<E> getNext() {
        return next;
    }

    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }

    public MyDLLNode<E> getPrev() {
        return prev;
    }

    public void setPrev(MyDLLNode<E> prev) {
        this.prev = prev;
    }

}
