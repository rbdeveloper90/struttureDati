package node;

import position.Position;
import exception.InvalidPositionException;

public class DNode<E> implements Position<E> {
	private E element;
	private DNode <E> next,prev;
	private int index=0;
	

	public DNode(DNode <E> p, DNode <E> n, E e)
	{
	prev = p;
	element = e;
	next = n;
	}
	
	public E element() {
		if((prev==null)||(next==null)) 
			throw new InvalidPositionException("posizione non valida");
		return element;
	}
	
	public DNode <E> getNext() { return next; }
	
	public DNode <E> getPrev() { return prev; }
	
	public int getIndex(){
		return index;
	}
	public void setElement(E newElem) {
		element = newElem;
	}
	
	public void setNext(DNode <E> newNext) {
		next = newNext;
	}
	
	public void setPrev(DNode <E> newPrev) {
		prev = newPrev;
	}

	public void setIndex(int i){
		index=i;
	}
}
