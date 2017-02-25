package exception;

public class FullQueueException extends RuntimeException{
	
	public FullQueueException(String m){
		super(m);
	}
}