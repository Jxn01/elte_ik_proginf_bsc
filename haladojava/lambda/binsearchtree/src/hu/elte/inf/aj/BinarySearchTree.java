package hu.elte.inf.aj;

import java.util.List;

public class BinarySearchTree {

	public static void main(String[] args) {
		List<Integer> l = List.of(10,3,20,18,19,17,3); 
		BinarySearchTree bst = new BinarySearchTree();
		for(var e: l) {
			bst.add(e);
		}
	}
	
	Node root;
	
	public void add(int i) {
		if(root == null) {
			root = new Node();
			root.value = i;
		} else {
			insert(i, root);
		}
	}
	
	private void insert(int i, Node node) {
		if(i<node.value) {
			if(node.left == null) {
				node.left = new Node();
				node.left.value=i;
			} else {
				insert(i, node.left);
			}
		} else if (i > node.value) {
			if(node.right == null) {
				node.right = new Node();
				node.right.value = i;
			} else {
				insert(i, node.right);
			}
		} else {
			System.err.println("i: "+i+" is already in the tree");
		}
	}
	
	private static class Node {
		int value;
		Node left;
		Node right;
		@Override
		public String toString() {
			return "Node [value=" + value + ", left=" + left + ", right=" + right + "]";
		}	
		
	}
	
}
