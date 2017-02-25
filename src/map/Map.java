package map;

import entry.Entry;
import exception.InvalidKeyException;

public interface Map<K,V> { 
 
 public int size(); 
 
 public boolean isEmpty(); 
 
 
 public V put(K key, V value) throws InvalidKeyException; 
 
 public V get(K key) throws InvalidKeyException; 
  
 public V remove(K key) throws InvalidKeyException; 
 
 public Iterable<K> keys(); 
 
 public Iterable<V> values(); 
 
 public Iterable<Entry<K,V>> entries(); 
 }