package partition;

import position.Position;
import comparator.DefaultComparator;
import map.HashTableMap;
import map.Map;
import nodeList.NodePositionList;
import nodeList.PositionList;
import set.OrderedListSet;
import set.Set;


public class ListPartition<E> implements Partition <E>{

	protected PositionList<Set <E>> partizione;		
	protected Map<E,Set<E>> elementi;
	
	public ListPartition(){
		partizione = new NodePositionList<Set<E>>();
		elementi = new HashTableMap<E,Set<E>>();
	}

	public int size() {
		return partizione.size();
	}

	public boolean isEmpty() {
		return partizione.isEmpty();
	}

	public Set<E> makeSet(E x) {
		OrderedListSet<E> newSet = new OrderedListSet<E>(x,
						new DefaultComparator<E>());
		elementi.put(x, newSet);
		partizione.addLast(newSet);
		newSet.setLocation(partizione.last());		
		return newSet;
	}

	public Set<E> find(E x) {
		return elementi.get(x);
	}

	public Set<E> union(Set<E> A, Set<E> B) {
		OrderedListSet<E> toAdd = new OrderedListSet<E>();	
		OrderedListSet<E> AUB = new OrderedListSet<E>();
		
		if(A.size() > B.size()){
			toAdd = (OrderedListSet<E>) B;
			AUB = (OrderedListSet<E>) A;
		}else{
			toAdd = (OrderedListSet<E>)A;
			AUB = (OrderedListSet<E>)B;
		}
		
		Position<Set<E>> toRemove;
		
		toRemove = toAdd.location();
		
		PositionList<E> lToAdd = toAdd.getSet();
		for(E element: lToAdd)	
			elementi.put(element, AUB);
		
		AUB.fastUnion(toAdd);  
		partizione.remove(toRemove);	
		return AUB;
	}
	
	
	


}
