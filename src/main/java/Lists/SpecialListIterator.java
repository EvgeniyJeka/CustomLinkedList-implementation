package Lists;

import java.util.ListIterator;

public class SpecialListIterator<T> implements ListIterator<T> {

	CustomLinkedList<T> iteratedList;
	LinkedListNode<T> currentNode;
	int index;
	int size;
	
	//Used only for special case handling.
	LinkedListNode<T> prevNode;
	

	public SpecialListIterator(CustomLinkedList<T> customLinkedList) {
		this.iteratedList = customLinkedList;
		this.currentNode = customLinkedList.first;
		this.index = 0;
		this.size = customLinkedList.size();

	}

	public SpecialListIterator(CustomLinkedList<T> customLinkedList, int index) {
		this.iteratedList = customLinkedList;
		this.currentNode = customLinkedList.nodeByIndex(index);
		this.index = index;
		this.size = customLinkedList.size();

	}

	@Override
	public boolean hasNext() {
		
		return index < size;
	}

	@Override
	public T next() {
		T result = this.currentNode.getNodeValue();

		this.prevNode = this.currentNode;

		this.currentNode = this.currentNode.next;
		this.index++;
		return result;

	}

	@Override
	public boolean hasPrevious() {

		return index > 0;
	}

	@Override
	public T previous() {

		T result = null;

		if (this.currentNode == null) {
			
			//Covering edge-case: following the previous iteration current node is null
			this.currentNode = this.prevNode;
			result = this.currentNode.getNodeValue();
			index--;
		}

		else {
			//Regular case
			result = this.currentNode.previous.getNodeValue();
			this.currentNode = this.currentNode.previous;
			this.index--;
		}

		return result;

	}

	@Override
	public int nextIndex() {
		return this.index;
	}

	@Override
	public int previousIndex() {
		return this.index - 1;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

	@Override
	public void set(T e) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void add(T e) {
		throw new UnsupportedOperationException();

	}

}
