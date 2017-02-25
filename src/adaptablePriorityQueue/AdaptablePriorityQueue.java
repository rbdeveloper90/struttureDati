package adaptablePriorityQueue;

import priorityQueue.PriorityQueue;
import entry.Entry;
import exception.InvalidKeyException;

public interface AdaptablePriorityQueue<K,V> extends PriorityQueue<K,V> {

public Entry<K,V> remove(Entry<K,V> e);

public K replaceKey(Entry<K,V> e, K key) throws InvalidKeyException;

public V replaceValue(Entry<K,V> e, V value);
}