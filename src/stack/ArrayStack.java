package stack;

import exception.EmptyStackException;

public class ArrayStack <E> implements Stack<E>{
	private int capacity;
	private E S[];
	private int top=-1;

	// capacita` di default
	public static final int CAPACITY=1000;

	// costruttore di default
	public ArrayStack(){
		this(CAPACITY);
	}

	// costruttore parametrico
	public ArrayStack(int cap){
		capacity=cap;
		S=(E[]) new Object[capacity];
	}

	public void push(E element){
		if(capacity==size()){
			E[] newArray= (E[]) new Object[capacity*2];
			for(int i=0;i<capacity;i++)
				newArray[i]=S[i];
			S=newArray;
			capacity*=2;
		}
		top=top+1;
		S[top]=element;
	}

	public E top() throws EmptyStackException{

		if(isEmpty())
			throw new EmptyStackException("stack vuoto");
		return S[top];
	}

	public E pop() throws EmptyStackException{
		E element;
		if(isEmpty())
			throw new EmptyStackException("stack vuoto");
		element=S[top];
		S[top--]=null;	
		if(size()<=capacity/4){
			capacity/=2;
			E[] A = (E[]) new Object[capacity];
			for(int j=0; j<size(); j++)
				A[j]=S[j];
			S=A;
		}
		return element;
	}

	public int size(){
		return top+1;
	}

	public boolean isEmpty(){
		return(size()==0);
	}

	public int getCapacity(){
		return capacity;
	}

	public String toString() {
		String s="(bottom) [";
		if(!isEmpty()){
			for(int i=0;i<size();i++){
				s+=S[i];
				if(i!=size()-1)
					s+=", ";
			}
		}
		s+="] (top)";
		return s;
	}

}
