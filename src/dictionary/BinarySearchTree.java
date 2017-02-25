package dictionary;

import java.util.Comparator;
import java.util.Iterator;
import nodeList.NodePositionList;
import nodeList.PositionList;
import binaryTree.LinkedBinaryTree;
import comparator.DefaultComparator;
import dictionary.Dictionary;
import entry.Entry;
import exception.InvalidEntryException;
import exception.InvalidKeyException;
import position.Position;



public class BinarySearchTree<K, V> extends LinkedBinaryTree<Entry<K,V>> implements Dictionary<K, V> {
	protected Comparator<K> C; 							
	protected Position<Entry<K,V>> actionPos; 			
	protected int numEntries = 0; 						
	
	
	public BinarySearchTree() {
		C = new DefaultComparator<K>(); 
		addRoot(null);
	} 
	
	public BinarySearchTree(Comparator<K> c) {
		C = c; 
		addRoot(null);
	}
	
	protected K key(Position<Entry<K,V>> position) { 
		return position.element().getKey();    
		} 
   
	
   protected V value(Position<Entry<K,V>> position) { 
		return position.element().getValue();      
		} 
   
   
   protected Entry<K,V> entry(Position<Entry<K,V>> position) { 
 		return position.element();     
 		} 
   
   
   protected void replaceEntry(Position <Entry<K,V>> pos, Entry<K,V> ent) { 
		((BSTEntry<K,V>) ent).pos = pos;			
		replace(pos, ent);								
		}
   
   
   protected void checkKey(K key) throws InvalidKeyException {
	   if(key == null)  								
	   throw new InvalidKeyException("null key"); 
	   }
   

   protected void checkEntry(Entry<K,V> ent) throws InvalidEntryException {
	   if(ent == null || !(ent instanceof BSTEntry))           
	   throw new InvalidEntryException("invalid entry");		
	   }
	
   
   protected Position<Entry<K,V>> treeSearch(K key, Position<Entry<K,V>> pos) { 
	   if (isExternal(pos)) 												
		   return pos; 				
	   else { 
		   		K curKey = this.key(pos); 									
		   		int comp = C.compare(key, curKey); 								
		   		if (comp < 0) 													
		   			return treeSearch(key, left(pos)); 								
		   		else 
		   			if (comp > 0) 
		   				return treeSearch(key, right(pos)); 							
   return pos; 																
   } 
  }
   
   
   protected void addAll(PositionList<Entry<K,V>> L, Position<Entry<K,V>> v, K k) { 
	   if (isExternal(v)) 									
		   return; 
	   Position<Entry<K,V>> pos = treeSearch(k, v); 		
	   if (!isExternal(pos)) { 								
	   	addAll(L, left(pos), k); 								
	   	L.addLast(pos.element()); 								
	   	addAll(L, right(pos), k);								
	   	}
   }
   
   public int size() { 
	   return numEntries; 
	   } 
   
   public boolean isEmpty() { 
	   return size() == 0; 
	 } 
   
   public Entry<K,V> find(K key) throws InvalidKeyException { 
	   checkKey(key); 												
	   Position<Entry<K,V>> curPos = treeSearch(key, root()); 		
	   actionPos = curPos; 											
	   if (isInternal(curPos)) 										
		   return entry(curPos); 										
   return null; 													
   } 
   
   public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException { 
   checkKey(key); 													
   PositionList<Entry<K,V>> L = new NodePositionList<Entry<K,V>>(); 
   addAll(L, root(), key);											
   return L;														
   }
		
	public Iterable<Entry<K, V>> entries() {
		PositionList<Position<Entry<K,V>>> temp=new NodePositionList<Position<Entry<K,V>>>();					
		this.preorderPositions(root, temp);																		
		Iterator<Position<Entry<K,V>>> iter=temp.iterator();													
		NodePositionList<Entry<K,V>> lista= new NodePositionList<Entry<K,V>>();									
		while(iter.hasNext()){																				
			actionPos=iter.next();																				
			if(actionPos.element()!=null)																			
				lista.addLast(actionPos.element());																			
		}
		return lista;																							
		
	}


	protected Entry<K,V> insertAtExternal(Position<Entry<K,V>> v, Entry<K,V> e) {
		expandExternal(v,null,null); 		
		replace(v, e); 						
		numEntries++;						
		return e;							
	}
	
	public Entry<K,V> insert(K k, V x) throws InvalidKeyException { 
		
		checkKey(k); 														
		Position<Entry<K,V>> insPos = treeSearch(k, root()); 				
		while (!isExternal(insPos)) 										 
			insPos = treeSearch(k, left(insPos)); 								
		actionPos = insPos; 												
		return insertAtExternal(insPos, new BSTEntry<K,V>(k, x, insPos)); 	
	}
	
	

	protected void removeExternal(Position<Entry<K,V>> v) {
	removeAboveExternal(v); 
	numEntries--;
	}

	public Entry<K,V> remove(Entry<K,V> ent) throws InvalidEntryException { 
		checkEntry(ent); 													
		Position<Entry<K,V>> remPos = ((BSTEntry<K,V>) ent).position();		
		Entry<K,V> toReturn = this.entry(remPos); 							
		
		if (isExternal(left(remPos))) 										
			remPos = left(remPos); 											
			else 
				if (isExternal(right(remPos))) 								
					remPos = right(remPos); 								
		else { 														
			Position<Entry<K,V>> swapPos = remPos; 					
			remPos = right(swapPos); 										
			do 
				remPos = left(remPos); 							
			while (isInternal(remPos)); 						
		
			replaceEntry(swapPos, (Entry<K,V>) parent(remPos).element()); 		
		} 
		
		removeExternal(remPos); 								
		return toReturn; 
		}


	public Entry<K, V> first() {
		actionPos=this.root();													
		while(!this.isExternal(actionPos))										
			actionPos=this.left(actionPos);										
		return this.parent(actionPos).element();								
	}

	public Entry<K, V> last() {
		actionPos=this.root();													
		while(!this.isExternal(actionPos))										
			actionPos=this.right(actionPos);									
		return this.parent(actionPos).element();								
		}


	public int conta_nodi_interni(Position<Entry<K,V>> p){
		int contadestra=0;
		int contasinistra=0;
		if(this.isExternal(p))
			return 0;
		if(this.hasLeft(p))
			contasinistra=1+conta_nodi_interni(this.left(p));
		if(this.hasRight(p))
			contadestra=1+conta_nodi_interni(this.right(p));
		return contadestra+contasinistra-1;
	}
	
	
	public Position<Entry<K,V>> seleziona(Position<Entry<K,V>> p, int k){
		int t=0;
		Position<Entry<K,V>> pos = null;
		
		if(this.isExternal(p))
			return null;
		
		if(this.hasLeft(p))
			t=conta_nodi_interni(this.left(p));
		
		if(k==t)
			return p;
		
		if(k<t)
			pos= seleziona(this.left(p),k);
		else
			if(k>t)
				pos= seleziona(this.right(p),k-t-1);
		return pos;
		
	}
	
	
	
}

