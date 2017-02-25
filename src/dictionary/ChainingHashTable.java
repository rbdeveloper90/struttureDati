package dictionary;

import java.util.Comparator;
import java.util.Iterator;

import comparator.DefaultComparator;
import nodeList.NodePositionList;
import nodeList.PositionList;
import entry.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;

public class ChainingHashTable<K, V> implements Dictionary<K, V> {
	protected int size=0, capacity;
	protected LogFile<K,V> A[];
	protected Comparator<K> c;

	@SuppressWarnings("unchecked")
	public ChainingHashTable(){ 
		capacity = 500;
		c = new DefaultComparator<K>();
		A = new LogFile[capacity];
		for (int i=0; i<capacity; i++) 
			A[i] = new LogFile<K,V>();
	}
	
	/**Restituisce una collezzione iterabile di tutte le entry del dizionario**/
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista= new NodePositionList<Entry<K,V>>();					
		Iterator<Entry<K,V>> temp;															
		Entry<K,V> x;																		
		for (int i=0; i<capacity; i++){														
			temp=A[i].entries().iterator();															
			while(temp.hasNext()){																	
				x=temp.next();																		
				if(!x.equals(null))																		
					lista.addLast(x);																		
			}
		}
			return lista;																	
	}

	public Entry<K,V> find(K key) throws InvalidKeyException{
		checkKey(key);
		int i = hashValue(key); 
		return A[i].find(key);
	}

	private int hashValue(K key) {
		int i=key.hashCode();
		return i%capacity;
	}

	private void checkKey(K key) {
		if(key.equals(null))											
			throw new InvalidKeyException("Invalid key");
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		this.checkKey(key);							
		int x= this.hashValue(key);					
		return A[x].entries();						
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		this.checkKey(key);							
		if(size==capacity-1)
			this.rehash();
		int x= this.hashValue(key);					
		Entry<K,V> e= A[x].insert(key, value);		
		return e;									
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		PositionList<Entry<K,V>> temp= (PositionList<Entry<K, V>>) this.entries();			
		Iterator<Entry<K,V>> iter= temp.iterator();												
		Entry<K,V> x;
		capacity=capacity*2;
		
		LogFile<K,V> B[];
		B = new LogFile[capacity];
		
		for (int i=0; i<capacity; i++) 
			B[i] = new LogFile<K,V>();
		
		while(iter.hasNext()){
			x=iter.next();
			int h= this.hashValue(x.getKey());
			B[h].insert(x.getKey(), x.getValue());
			}
		A=B;
	}


	public boolean isEmpty() {
		return this.size()==0;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		int x=this.hashValue(e.getKey());					
		Entry<K,V> temp=A[x].remove(e);						
		return temp;
		
	}

	public int size() {
		return size;
	}
	
	public void UpdateKey(K key, K newKey){
		this.checkKey(key);																		
		this.checkKey(newKey);																	
		
		PositionList<Entry<K,V>> temp= (PositionList<Entry<K, V>>)this.findAll(key);			
		Iterator<Entry<K,V>> iter= temp.iterator();												
		Entry<K,V> x;																			
		
		while(iter.hasNext()){																	
			x=iter.next();																			
			this.remove(x);																			
			this.insert(newKey, x.getValue());														
		}
			
	}

}
