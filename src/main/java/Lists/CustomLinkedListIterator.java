package Lists;

import java.util.Iterator;

public class CustomLinkedListIterator<T> implements Iterator<T>{
	
	CustomLinkedList<T> iteratedList;
	LinkedListNode<T> currentNode;

	public CustomLinkedListIterator(CustomLinkedList<T> customLinkedList) {
		this.iteratedList = customLinkedList;
		this.currentNode = customLinkedList.first;
	}

	@Override
	public boolean hasNext() {
		
		return (this.currentNode!=null && this.currentNode.previous!=this.iteratedList.last);
	}

	@Override
	public T next() {
		 
		T result = this.currentNode.getNodeValue();
		this.currentNode = this.currentNode.next;
		return result;
	}
	



}
