package priorityQueue;

import java.util.Comparator;
import java.util.Iterator;

import comparator.DefaultComparator;
import position.*;
import nodeList.*;
import entry.Entry;
import exception.EmptyPriorityQueueException;
import exception.InvalidKeyException;

public class UnsortedListPriorityQueue<K,V> implements PriorityQueue<K,V> { 
	 protected PositionList<Entry<K,V>> entries; 
	 protected Comparator<K> c; 

	 

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

		public UnsortedListPriorityQueue () {
			entries = new NodePositionList<Entry<K,V>>();
			c = new DefaultComparator<K>();
		}
		
		
		public UnsortedListPriorityQueue (Comparator<K> comp) {
			entries = new NodePositionList<Entry<K,V>>();
			c = comp;
		}
		
		
		public UnsortedListPriorityQueue (PositionList<Entry<K,V>> list,Comparator<K> comp) {
			entries = list;
			c = comp;
		}
		
		public void setComparator(Comparator<K> comp) throws IllegalStateException {
			if(!isEmpty()) throw new IllegalStateException("la coda a priorita` non e` vuota");
			c = comp;
		}
		
		
		public int size () {
			return entries.size(); 
		}
		
		
		public boolean isEmpty () { 
			return entries.isEmpty(); 
		}
		
		
		protected boolean checkKey(K key) throws InvalidKeyException {
			boolean result;
			try { 
				result = (c.compare(key,key)==0);
			} catch (ClassCastException e) { throw new InvalidKeyException("la chiave non è confrontabile"); }
			return result;
		}
		
		protected void insertEntry(Entry<K,V> e) {
			entries.addLast(e);
		}
		
		public Entry<K,V> insert (K k, V v) throws InvalidKeyException {
			checkKey(k);
			Entry<K,V> entry = new MyEntry<K,V>(k, v);
			insertEntry(entry);
			return entry;
		}
		
		
		public Entry<K, V> min() throws EmptyPriorityQueueException {
			Entry<K,V> minimo = entries.first().element();
			
			for (Position<Entry<K,V>> posentry : entries.positions())
				if (c.compare(posentry.element().getKey(),minimo.getKey()) <= 0){
					minimo = posentry.element();
			}
			return minimo;
		}
		
		
		public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
			
			Position<Entry<K,V>> minimo = entries.first();
			for (Position<Entry<K,V>> posentry : entries.positions())
				if (c.compare(posentry.element().getKey(),minimo.element().getKey()) <= 0){
					minimo = posentry;
			}
			
			Entry<K,V> min=minimo.element();
			
			entries.remove(minimo);
			
			
			return min;
		}
		
		

		public PriorityQueue<K,V> Clone(){

			NodePositionList<Entry<K,V>> l = new NodePositionList<Entry<K,V>>();
			
			for(int i=0;i<size();i++){
				Entry<K,V> e = entries.remove(entries.first());
				l.addLast(e);
				entries.addLast(e);
			}
			
			return new UnsortedListPriorityQueue<K, V>(l,c);	
		}
		
		
		public  PositionList<Entry<K,V>> getEntries(){
			return entries;
		}
		
		
		public boolean equals(UnsortedListPriorityQueue<K,V> l){
			
			int c=0;
			for(int i=0;i<size();i++){
				entries.addLast(entries.remove(entries.first()));
				
				l.getEntries().addLast(l.getEntries().remove(l.getEntries().first()));
							
				MyEntry<K,V> uno = (MyEntry<K, V>) entries.last().element();
				
				MyEntry<K,V> due = (MyEntry<K, V>) l.getEntries().last().element();
		
			
				if(!(uno.equals(due)))
					c++;
			}
			
			return c==0;
		}
		
		public String toString(){
			String c ="";
			for(Entry<K,V> e : getEntries())
				c+=e.getKey()+"-"+e.getValue()+" ";
			return c;
		}
		
		
		public Iterator<V> removeLn(K d,K x){
			
			PositionList<V> l = new NodePositionList<V>();
			
			for(Entry<K,V> e: entries){
				if((c.compare(e.getKey(),d )>= 0) && (c.compare(e.getKey(), x)<= 0))
					l.addLast(e.getValue());
			}
			return l.iterator();
		}
		
		
		public Iterator<V> removeLess(K x){
			
			PositionList<V> l = new NodePositionList<V>();
			
			for(Entry<K,V> e : entries){
				if(c.compare(e.getKey(),x)<=0)
					l.addLast(e.getValue());
			}
			return l.iterator();
		}
		
		
		@SuppressWarnings("unchecked")
		public void azzeraKey(V val){
			
			DefaultComparator<V> comp = new DefaultComparator<V>();
			
			for(Position<Entry<K,V>> e: entries.positions()){
				if(comp.compare(e.element().getValue(),val)==0){
					entries.remove(e);
					insertEntry(new MyEntry<K,V>((K)new Integer(0),val));
						}
				}
		}
	}