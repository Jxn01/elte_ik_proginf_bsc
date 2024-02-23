package generics;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		MyList<Integer> intList = new MyList<>();
		MyList<Double> doubleList = new MyList<>();
		intList.add(1);
		intList.add(2);
		System.out.println(intList.size());
		System.out.println(intList.get(1));
		System.out.println(doubleList.size());
		Integer i = intList.get(0);
		System.out.println(i+2);
	}

	
	private static class MyList<T> {
		
		Node<T> head = null;
		
		void add(T value) {
			if(head == null) {
				head = new Node<>(value);
			}
			Node<T> n = head;
			while(n.next!=null) {
				n = n.next;
			}
			n.next = new Node<>(value);
		}
		
		int size() {
			int count = 0;
			Node<T> n = head;
			while(n!=null) {
				n = n.next;
				++count;
			}
			return count;
		}
		
		T get(int n) {
			Node<T> node = head;
			int i=0;
			while(node != null && i <n) {
				node = node.next;
				++i;
			}
			return node.value;
		}
		
		private static class Node<T> {
			T value;
			Node<T> next;
			
			Node(T value) {
				this.value=value;
				next = null;
			}
		}
	}
}
