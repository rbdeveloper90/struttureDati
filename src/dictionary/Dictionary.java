package dictionary;

import entry.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;

public interface Dictionary<K,V> {
	

	public int size();

	public boolean isEmpty();

	public Entry<K,V> find(K key) throws InvalidKeyException;

    public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException;

    public Entry<K,V> insert(K key, V value) throws InvalidKeyException;

    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException;

    public Iterable<Entry<K,V>> entries();
}