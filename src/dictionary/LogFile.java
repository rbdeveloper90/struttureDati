package dictionary;

import nodeList.NodePositionList;
import nodeList.PositionList;
import entry.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import position.Position;
import map.HashEntry;

// Un log file è un dizionario non ordinato implementato con una lista (non ordinata) 

public class LogFile<K, V> implements Dictionary<K, V> {
	private PositionList<Entry<K,V>> lista;


	public LogFile(){
		lista=new NodePositionList<Entry<K,V>>();
	}

	public int size() {
		return lista.size();				
	}

	public boolean isEmpty() {
		if(this.size()!=0)
			return false;
		else
			return true;
	}

	private void checkKey(K key)throws InvalidKeyException{
		if(key.equals(null))											
			throw new InvalidKeyException("Invalid key");
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		this.checkKey(key);					
		for(Entry<K,V> x: lista){				
			if(x.getKey().equals(key))					
				return x;								
		}
		return null;							
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		this.checkKey(key);						
		PositionList<Entry<K,V>> toreturn= new NodePositionList<Entry<K,V>>();				
		for(Entry<K,V> x: lista){				
			if(x.getKey().equals(key))			
				toreturn.addLast(x);			
		}
		return toreturn;							
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		HashEntry<K,V> temp= new HashEntry<K,V>(key,value);					
		lista.addLast(temp);											
		return temp;													
	}

	@SuppressWarnings("unchecked")
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		for(Entry<K,V> x: lista){				
			if(x.equals(e)){						
				lista.remove((Position<Entry<K, V>>)x);				
				return e;
			}
		}
		return null;	
	}

	public Iterable<Entry<K, V>> entries() {
		return lista;
	}

}
