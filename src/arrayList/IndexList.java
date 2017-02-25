package arrayList;
import exception.IndexOutOfBoundsException;

public interface IndexList<E> {
	
	
	public E remove(int i) throws IndexOutOfBoundsException;
	
	public void add(int i, E e) throws IndexOutOfBoundsException;

	public E set(int i,E e) throws IndexOutOfBoundsException;
	
	public E get(int i) throws IndexOutOfBoundsException;
	
	public boolean isEmpty();
	
	public int size();
}
