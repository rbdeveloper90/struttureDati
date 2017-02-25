package iterator;

import nodeList.*;
import exception.*;

import java.util.Iterator;

import position.Position;

public class ElementIterator<E> implements Iterator<E> {
	protected PositionList<E> list; 

	protected Position<E> cur = null; 
	
    public ElementIterator(){ }
	
	
	public ElementIterator(PositionList<E> L) {
		list = L;
		if (!list.isEmpty())
			cur = list.first();
	}

	public boolean hasNext() {
		return cur!=null;
	}

	public E next() throws NoSuchElementException{
		if(!hasNext()) throw new NoSuchElementException("nessun'altra posizione");
		E toReturn = cur.element();
		if(cur==list.last())
			cur=null;
		else cur = list.next(cur);
		return toReturn;
	}

	public void remove()throws UnsupportedOperationException{
		throw new UnsupportedOperationException("remove");
	}

}
