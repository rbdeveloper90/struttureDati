package eser;

import queue.NodeQueue;
import queue.Queue;

public class prova {

	public static void main(String[] args) {
		Queue<Integer> q = new NodeQueue<Integer>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		System.out.println(q);
		f(q, 4);
		System.out.println(q);
		f(q, 1);
		System.out.println(q);
		f(q, q.size());
		System.out.println(q);
		
		//Test secondo esercizio
	/*	Queue<Integer> qq = new NodeQueue<Integer>();
		qq.enqueue(1);
		qq.enqueue(2);
		qq.enqueue(3);
		qq.enqueue(4);
		qq.enqueue(5);
		qq.enqueue(6);
		qq.enqueue(7);
		System.out.println(qq);
		f_k(qq, 3, 2);
		System.out.println(qq);
		f_k(qq, 2, 3);
		System.out.println(qq);
		f_k(qq, 1, 2);
		System.out.println(qq);*/
	}
	
	public static <E> void f(Queue<E> q, int n) {
		int size = q.size();
		
		for(int i = 0; i < size; i++) {
			if(i == n - 1) {
				q.dequeue();
			} else {
				q.enqueue(q.dequeue());
			}
		}
	}
	
	public static <E> void f_k(Queue<E> q, int n, int k) {
		int size = q.size();
		
		for(int i = 0; i < n - 1; i++) {
			q.enqueue(q.dequeue());
		}
		
		for(int i = 0; i < k; i++) {
			q.dequeue();
		}
		
		for(int i = n + k - 1; i < size; i++) {
			q.enqueue(q.dequeue());
		}
	}

	}


