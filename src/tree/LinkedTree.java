package tree;
import nodeList.*;

import java.util.Iterator;

import exception.InvalidPositionException;
import exception.NonEmptyTreeException;
import exception.BoundaryViolationException;
import position.Position;
import exception.*;


@SuppressWarnings("all")
public class LinkedTree<E> implements Tree<E>{
	
		protected TreePosition<E> root;
		protected int size;
		
	
	public LinkedTree(){
		root=null;
		size=0;
	}
	
	
	public TreeNode<E> checkPosition(Position<E> pos)throws InvalidPositionException{
		
		if(pos==null || !(pos instanceof TreeNode)) throw new InvalidPositionException("La posizione è invalida...");
		
			return (TreeNode<E>) pos;
	}
	
	
	
	public Position<E> addRoot(E e) throws NonEmptyTreeException {
			if(!isEmpty())throw new NonEmptyTreeException("Esiste già una radice..!!");
		
			root=new TreeNode<E>(e,null,null);
			size=1;	
			
				return root;
	}

	
	
	public Iterable<Position<E>> children(Position<E> v)throws InvalidPositionException {
		
		TreeNode<E> temp = checkPosition(v);
		
		if(isExternal(v))throw new InvalidPositionException("Errore,una foglia non può avere figli!!");
		
		return temp.getChildren();
	}

	
	
	public Position<E> addChild(E e , Position<E> v)throws InvalidPositionException {
		
		TreeNode<E> padre = checkPosition(v);
		TreeNode<E> figlio = new TreeNode<E>(e,padre,null);
		
		if(padre.getChildren()!=null)
			
			padre.getChildren().addLast(figlio);
		
		else {
				padre.setChildren(new NodePositionList<Position<E>>());
				padre.getChildren().addLast(figlio);
			  }
		size++;
		
		return figlio;
	}

	
	
	public boolean isEmpty() {
		return (size==0);
	}

	
	
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		
		TreeNode<E> temp = checkPosition(v);
		
		return(temp.getChildren()==null || temp.getChildren().isEmpty());
	}

	
	
	public boolean isInternal(Position<E> v) throws InvalidPositionException {

	return (!isExternal(v));
	}

	
	
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		
		checkPosition(v);
		
		return (v==root());
	}

	
	public Iterator<E> iterator() {
		
		Iterable<Position<E>> l = positions();
		NodePositionList<E> listElem = new NodePositionList();
		
		for(Position<E> pos : l)
			listElem.addLast(pos.element());
		
		return listElem.iterator();
	}

	
	public Position<E> parent(Position<E> v) throws InvalidPositionException,BoundaryViolationException {
		
		TreeNode<E> n = checkPosition(v);
		
			if(isRoot(v)) throw new BoundaryViolationException("Non posso tornare il parent di un elemento root!!!");
			
		return n.getParent();
	}

	
	
	public Iterable<Position<E>> positions() {
		NodePositionList<Position<E>> l = new NodePositionList<Position<E>>();
		if(!isEmpty())
			preorderPositions(root,l);
		return l;
	}

	
	
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		
		TreeNode<E> temp = checkPosition(v);
		E el = temp.element();
		temp.setElement(e);
		
		return el;
	}

	
	
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty()) throw new EmptyTreeException("L'albero è vuoto!!");
		return root;
	}

	
	public int size() {
			return size;
	}
	
	
	
	protected void preorderPositions(Position<E> v,PositionList<Position<E>> pos) throws InvalidPositionException {
		checkPosition(v);
		pos.addLast(v);//lista di tutte le posizioni dell'albero
		if(isInternal(v))
			for (Position<E> w : children(v))
				preorderPositions(w, pos); // ricorsione su ciascun figlio
	}
	

	
	protected void postorderPositions(Position<E> v,PositionList<Position<E>> pos) throws InvalidPositionException {
		checkPosition(v);
		if(isInternal(v))
			for (Position<E> w : children(v))
				postorderPositions(w, pos); // ricorsione su ciascun figlio
		
		pos.addLast(v);//lista di tutte le posizioni dell'albero
		
	}
	
	
	
	public int depth(Position<E> pos)throws InvalidPositionException{
		TreeNode<E> temp = checkPosition(pos);
		
		if(pos==root())
			return 0;
		
		return 1+depth(temp.getParent());
	}
	
	//antenato
	public boolean ancestor(Position<E> c,Position<E>p)throws InvalidPositionException{
		
		TreeNode<E> e1 = checkPosition(c);
		TreeNode<E> e2 = checkPosition(p);
		
		if(c==p)
			return true;
		if(p==root())
			return false;
		
		else 
			return ancestor(e1,e2.getParent());
	}
	
	
	
	public Position<E> lca(Position<E> v,Position<E> w)throws InvalidPositionException{
		
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> ww = checkPosition(w);
		
		while(vv!=ww){
			if(depth(vv) > depth(ww))
				vv = vv.getParent();
			else
				ww = ww.getParent();
		}
		
		return vv;
	}
	
	
	public Position<E> lcaRic(Position<E> v,Position<E> w)throws InvalidPositionException{
		
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> ww = checkPosition(w);
		
		if(vv == ww)
			return vv;
		else if(vv == root() || ww == root())
			return root();
		else{
			if(depth(vv) > depth(ww))
				return lca(vv.getParent(),ww);
			else if (depth(vv) < depth(ww))
				return lca(vv,ww.getParent());
			else
				return lca(vv.getParent(),ww.getParent());
		
		}
	}
	
	
	public PositionList<Position<E>> path(Position<E> u, Position<E> v){
		PositionList<Position<E>> lista = new NodePositionList<Position<E>>();
		
		Position<E> ant = lca(u,v);
		if(u==ant)
			lista.addLast(u);
		else{
			lista.addLast(u);
			while(u!=ant){
				u = parent(u);
				lista.addLast(u);
			}
		}
		if(ant==v)
			return lista;
		else
			while(ant != v)
				for(Position<E> pos : children(ant))
					if(isDiscendent(v, pos)){
						lista.addLast(pos);
						ant=pos;
					}
		return lista;
	}
	
	
	public int contaFoglie(Position<E> pos){
		
		if(isExternal(pos))
			return 1;
		
		int x=0;
		for(Position p: children(pos))
			x+=contaFoglie(p);
		
		return x;
		
	}
	
	
	public boolean	isDiscendent(Position<E> p, Position<E> q){
		checkPosition(p);
		checkPosition(q);
		if(p == q) 								
			return true;						
		else if(isExternal(q))					
			return false;
		else
			for(Position<E> pos : children(q))
				if(isDiscendent(p,pos)==true)
					return true;
		return false;
	}
	
	
	 public void remove(Position<E> u, Position<E> v){
	  TreePosition<E> uu = checkPosition(u);
	  TreePosition<E> vv = checkPosition(v);
	  
	  if(isRoot(uu))
	   throw new InvalidPositionException("Non posso rimuovere la radice!");
	  if(isRoot(vv))
	   throw new InvalidPositionException("Non posso aggiungere fratelli alla radice!");
	  
	 
	  for(Position<Position<E>> pos : uu.getParent().getChildren().positions())
	   if(pos.element().element() == u.element())
	    uu.getParent().getChildren().remove(pos);
	 
	
	  for(Position<E> p : children(uu)){
	   ((TreePosition<E>) p).setParent(vv.getParent());
	   vv.getParent().getChildren().addLast(p);
	  }
    }
	
	 public void remove (Position<E> v){
		 
		 TreePosition<E> vv = checkPosition(v);
		 
		 if(isRoot(vv))
			 throw new InvalidPositionException("Non è possibile rimuovere la radice!!!");
	 
		 PositionList<Position<E>> listaChild = new NodePositionList<Position<E>>();
		 for(Position<Position<E>> pos : vv.getParent().getChildren().positions()){
			 if(pos.element().element()== vv.element())
				 vv.getParent().getChildren().remove(pos);
		 }
		
		 for(Position<E> pos : children(vv)){
			 for(Position<E> posi : vv.getParent().getChildren()){
				 ((TreePosition<E>)pos).setParent((TreePosition<E>) posi);
				 ((TreePosition<E>) posi).getParent().getChildren().addLast(pos);
				 break;
			} 	
		 }
	 }
}
