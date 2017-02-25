package map;

import java.util.Random;

import node.Node;
import nodeList.NodePositionList;
import nodeList.PositionList;
import entry.Entry;
import exception.InvalidKeyException;

@SuppressWarnings("all")

public class HashTableMap<K,V> implements Map<K,V> {

	
	protected Entry<K,V> AVAILABLE = new HashEntry<K,V>(null, null);
	protected int n = 0; 
	protected int prime, capacity; 
	protected Entry<K,V>[] bucket;
	protected int scale, shift; 
	
	public HashTableMap(){ 
		this(109345121,1024); 
		}
	
	public HashTableMap(int cap) {
		this(109345121, cap);
		}
	
	public HashTableMap(int p, int cap) {
	
		prime = p;
		capacity = cap;
		bucket = (Entry<K,V>[]) new Entry[capacity]; 
		Random rand = new Random();
		scale = rand.nextInt(prime-1) + 1; 
		shift = rand.nextInt(prime); 
	}
	
	protected void checkKey(K k) {
		
		if (k == null) throw new InvalidKeyException("Chiave invalida: null.");
		}
		
	public int hashValue(K key) {
		return (int)((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
		}
		
	public Iterable<K> keys() {
		
		PositionList<K> keys = new NodePositionList<K>();
		
		for (int i=0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				keys.addLast(bucket[i].getKey());
		
		return keys;
		}
	
	
	protected int findEntry(K key) throws InvalidKeyException {
		
		int avail = -1;
		checkKey(key);
		int i = hashValue(key);
		int j = i;
		
			do {
				Entry<K,V> e = bucket[i];
					if ( e == null) {
							if (avail < 0)
								avail = i; 
					break; 
				}
		
			if (key.equals(e.getKey())) 
				
				return i; 
		
			if (e == AVAILABLE) {
				if (avail < 0)
					avail = i; 
			}
		
			i = (i + 1) % capacity; 
			}
			
			while (i != j);
			
		return -(avail + 1); 
		}
	
	
	public V get (K key) throws InvalidKeyException {
	
		int i = findEntry(key);
		
		if (i < 0) 
			return null; 
	
		return bucket[i].getValue();
	}
	
	public V put (K key, V value) throws InvalidKeyException {
	
		int i = findEntry(key); 
	
			if (i >= 0) 
				return ((HashEntry<K,V>) bucket[i]).setValue(value); 
			
			if (n >= capacity/2) {
				rehash(); 
				i = findEntry(key);
			}
	
			bucket[-i-1] = new HashEntry<K,V>(key, value);
			n++;
	
		return null; 
	}
	
	
	protected void rehash() {
		
		capacity = 2*capacity;
		Entry<K,V>[] old = bucket;
		
		bucket = (Entry<K,V>[]) new Entry[capacity]; 
		
		Random rand = new Random();
		
		scale = rand.nextInt(prime-1) + 1;
		shift = rand.nextInt(prime); 
		
		for (int i = 0; i < old.length; i++) {
			Entry<K,V> e = old[i];
			
			if ((e != null) && (e != AVAILABLE)) { 
				int j = - 1 - findEntry(e.getKey());
				bucket[j] = e;
			      }
			}
	}
	
	
	public V remove (K key) throws InvalidKeyException {
		
		int i = findEntry(key);
		
			if (i < 0)
				return null; 
		
			V toReturn = bucket[i].getValue();
			bucket[i] = AVAILABLE; 
			n--;
		
		return toReturn;
	}

	public Iterable<Entry<K, V>> entries() {
		
		PositionList<Entry<K,V>> list = new NodePositionList<Entry<K,V>>();
		
		for(int i=0;i<capacity;i++){
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				list.addLast(bucket[i]);
				}
		
		return list;
	}

	public Iterable<V> values() {
	
		PositionList<V> list = new NodePositionList<V>();
		
		for(int i=0;i<capacity;i++){
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				list.addLast(bucket[i].getValue());
				}
		
		return list;
	}
	
	public boolean isEmpty() {
		return n==0;
	}

	public int size() {
		return n;
	}
	
	public Iterable<Entry<K,V>>  sameHashEntries(K key){
		
		int hashv = hashValue(key);
		PositionList<Entry<K,V>> list = new NodePositionList<Entry<K,V>>();
		
		
		for(Entry<K,V> e : entries()){
			
			if(hashValue(e.getKey())== hashv)
				
				list.addLast(e);
			}
		
		return list;
	}

	public int clusterSize(K key){
		
		int hashv=hashValue(key);
		int size=0;
		
		for(Entry<K,V> e: entries())
			if(hashv==hashValue(e.getKey()))
				size++;
		
		return size;
	}
	
}