package dictionary;

import java.util.Random;

import exception.InvalidEntryException;
import nodeList.NodePositionList;
import nodeList.PositionList;
import entry.Entry;
import exception.InvalidKeyException;

public class HashTableDictionary<K, V> implements Dictionary<K, V> {

	protected Entry<K,V> AVAILABLE = new HashEntry<K, V>(null, null); 
	protected int n = 0; 
	protected int capacity; 
	protected int prime; 
	protected Entry<K,V>[] bucket; 
	protected long scale; 
	protected long shift; 
	
	public static class HashEntry<K,V> implements Entry<K,V>{

		public HashEntry(K k, V v){
			key = k;
			value = v;
		}
		
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		public V setValue(V val){
			V oldValue = value;
			value = val;
			return oldValue;
		}
		public boolean equals(Entry<K,V> e){
			HashEntry<K,V> ent;
			try{
				ent = (HashEntry<K,V>) e;
			}
			catch(ClassCastException exception){
				return false;
			}
			return (ent.getKey() == key) && (ent.getValue() == value);
		}
		
		public String toString(){
			return key +"-"+ value;
		}
		
		protected K key;
		protected V value;
	}
	
	public HashTableDictionary() {
		this(109345121, 1024);
	}

	public HashTableDictionary(int cap) {
		this(109345121, cap);
	}

	public HashTableDictionary(int p,int cap) {
		prime = p;
		capacity = cap;
		bucket = (Entry<K,V>[]) new Entry[capacity];
		Random rand = new Random();
		scale = rand.nextInt(prime-1)+1;
		shift = rand.nextInt(prime);
	}
	
	public int hashValue(K key){
		return (int)((Math.abs(key.hashCode()*scale+shift)%prime)%capacity);
	}

	
	public int size() {
		return n;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public Iterable<K> keys() {
		PositionList<K> keys = new NodePositionList<K>();
		for(int i=0; i<capacity; i++)
			if((bucket[i] != null) && (bucket[i] != AVAILABLE))
				keys.addLast(bucket[i].getKey());
		return keys;
	}

	public Iterable<V> values() {
		PositionList<V> values = new NodePositionList<V>();
		for(int i=0; i<capacity; i++)
			if((bucket[i] != null) && (bucket[i] != AVAILABLE))
			values.addLast(bucket[i].getValue());
		return values;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> entries = new NodePositionList<Entry<K, V>>();
		Entry<K,V> e;
		for(int i=0; i<capacity; i++)
			if((bucket[i] != null) && (bucket[i] != AVAILABLE)){
				e = new HashEntry<K, V>(bucket[i].getKey(),bucket[i].getValue()) ;
				entries.addLast(e);
			}
		return entries;				
	}

	

	protected void checkKey(K k) throws InvalidKeyException{
		if(k == null)
			throw new InvalidKeyException("Chiave non valida: null");
	}

	protected int findEntry(K key) throws InvalidKeyException{
		int avail = -1;
		checkKey(key);
		int i = hashValue(key);
		int j = i;
		do{
			Entry<K,V> e = bucket[i];
			if(e == null){
				if(avail < 0)
					avail = i; 
				break;
			}
			if(key.equals(e.getKey())) 
				return i;
			if(e == AVAILABLE){
				if(avail < 0)
					avail = i; 
			}
			i = (i+1)%capacity;
		}while(i != j);
		
		return -(avail+1); 
	}

	protected void rehash(){
		capacity = 2*capacity;
		Entry<K,V>[] old = bucket;
		bucket = (Entry<K,V>[]) new Entry[capacity]; 
		Random rand = new Random();
		scale = rand.nextInt(prime-1)+1; 
		shift = rand.nextInt(prime); 
		for(int i=0; i<old.length; i++){
			Entry<K,V> e = old[i];
			if((e != null) && (e != AVAILABLE)){ 
				int j = -1-findEntry(e.getKey());
				bucket[j] = e;
			}
		}
	}
	
	public void ShowBucket(){
		  for (int i=0; i<capacity; i++)
			  System.out.println("bucket[" + i + "]= " + bucket[i]);	  
	}
	public void ShowHashVaue(){
		  for (K k :keys())
			  System.out.println("hashValue("+ k + ")=" + hashValue(k));
	}
	
	public int clusterSize(K key){
		int value = 0;
		checkKey(key);
		for(K k: keys())
			if(hashValue(k) == hashValue(key)){
				System.out.println("L' hashValue di "+ k.toString() + " �: " +hashValue(k)+ " l'hashValue di " + key.toString() + " �: " + hashValue(key));
				value++;
			}
		return value;
	}

	@Override
	public entry.Entry<K, V> find(K key) throws exception.InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<entry.Entry<K, V>> findAll(K key)
			throws exception.InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public entry.Entry<K, V> insert(K key, V value)
			throws exception.InvalidKeyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public entry.Entry<K, V> remove(entry.Entry<K, V> e)
			throws InvalidEntryException {
		// TODO Auto-generated method stub
		return null;
	}
}
