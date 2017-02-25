package priorityQueue;

import nodeList.*;

import java.util.Comparator;

import comparator.DefaultComparator;
import position.Position;
import nodeList.PositionList;
import entry.Entry;
import exception.*;

public class SortedListPriorityQueue<K, V>  implements PriorityQueue<K,V>{
	private PositionList<Entry<K,V>> entries;
	private Comparator<K> c;
	
	protected static class MyEntry<K,V> implements Entry<K,V>{
		protected K k; 
		protected V v; 
		public MyEntry(K key, V value){
			k=key;
			v=value;
		}
		public K getKey(){
			return k;
		}
		public V getValue(){
			return v;
		}
		public String toString(){
			return "("+k+","+v+")";
		}
	}
	
	public SortedListPriorityQueue(){
		entries= new NodePositionList<Entry<K,V>>();
		c= new DefaultComparator<K>();
	}
	
	public SortedListPriorityQueue(Comparator<K> comp){
		entries = new NodePositionList<Entry<K,V>>();
		c=comp;
	}
	
	public Entry<K,V> min() throws EmptyPriorityQueueException{
		if(entries.isEmpty())
			throw new EmptyPriorityQueueException("coda a priorità vuota");
		else
			return entries.first().element();
	}
	
	public Entry<K,V> insert(K k, V v) throws InvalidKeyException{
			 checkKey(k); 
			 Entry<K,V> entry = new MyEntry<K,V>(k, v); 
			 insertEntry(entry); 
			 return entry; 

	}
	
	public void insertEntry(Entry<K,V> e){
		if(entries.isEmpty())
			entries.addFirst(e);
		else if(c.compare(e.getKey(), entries.last().element().getKey())>0)
			entries.addLast(e);
		else{ 
			Position<Entry<K,V>> curr= entries.first();
			while(c.compare(e.getKey(), curr.element().getKey())>0)
				curr=entries.next(curr);
			entries.addBefore(curr, e);
		}
	}
	
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException{
		if(entries.isEmpty())
			throw new EmptyPriorityQueueException("la coda a priorità è vuota");
		else 
			return entries.remove(entries.first());
	}
	
	protected boolean checkKey(K key){
		boolean result;
		try{
			result= (c.compare(key, key)==0);
		}
		catch(ClassCastException e){
			throw new InvalidKeyException("chiave non confrontabile");
		}
		return result;
	}


	
	public int size () {
		return entries.size(); 
	}
	
	
	public boolean isEmpty () { 
		return entries.isEmpty(); 
	}
	
	public String toString(){
		String c ="";                                       
		for(Entry<K,V> e : getEntries())
			c+=e.getKey()+"-"+e.getValue()+" ";
		return c;
	}
	public  PositionList<Entry<K,V>> getEntries(){
		return entries;
	}
}