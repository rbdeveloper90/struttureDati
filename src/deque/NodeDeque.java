package deque;

import node.DLNode;
import exception.EmptyDequeException;

public class NodeDeque<E> implements Deque<E> {

	private int size=0;
	private DLNode<E> header,trailer;
	

	public NodeDeque(){
		header=new DLNode<E>();
		trailer=new DLNode<E>();
		header.setNext(trailer);
		trailer.setPrev(header);
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public E getFirst() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("deque vuoto");
		return header.getNext().getElement();
	}

	public E getLast() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("deque vuoto");
		return trailer.getPrev().getElement();
	}

	public void addFirst(E elem) {
		DLNode<E> nuovo = new DLNode<E>(elem,header,header.getNext());
		header.getNext().setPrev(nuovo);
		header.setNext(nuovo);
		size++;
	}

	public void addLast(E elem) {
		DLNode<E> nuovo = new DLNode<E>(elem,trailer.getPrev(),trailer);
		trailer.getPrev().setNext(nuovo);
		trailer.setPrev(nuovo);
		size++;
	}

	public E removeFirst() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("deque vuoto");
		E elem = header.getNext().getElement();
		header.getNext().getNext().setPrev(header);
		header.setNext(header.getNext().getNext());
		size--;
		return elem;
	}

	public E removeLast() throws EmptyDequeException {
		if(isEmpty())
			throw new EmptyDequeException("deque vuoto");
		E elem = trailer.getPrev().getElement();
		trailer.getPrev().getPrev().setNext(trailer);
		trailer.setPrev(trailer.getPrev().getPrev());
		size--;
		return elem;
	}
	
	public String toString(){
		String s="";
		if(size==0)
			return s+="(header)<->(trailer)";
		s+="(header)<->[";
		DLNode<E> cur= header.getNext();
		while(cur!=trailer){
			s+=cur.getElement();
			if(cur.getNext()!=trailer)
				s+=", ";
			cur=cur.getNext();
		}
		return s+="]<->(trailer)";
	}


	
}
