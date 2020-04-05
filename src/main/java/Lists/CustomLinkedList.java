package Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "unused"})
public class CustomLinkedList<T> implements List<T> {

	LinkedListNode<T> first;
	LinkedListNode<T> last;

	private int size;

	// This field is used only if this list is a sublist of another CustomLinkedList
	CustomLinkedList<T> masterList = null;

	public CustomLinkedList() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}

	public CustomLinkedList(T firstNodeValue) {

		LinkedListNode<T> firstNode = LinkedListNode.makeNodeOf(firstNodeValue, null);

		this.size = 1;
		this.first = firstNode;
		this.last = firstNode;

	}

	public CustomLinkedList(List<T> asList) {
		asList.forEach(x -> this.add(x));
	}

	public static <T> CustomLinkedList<T> makeListOf(List<T> inputList) {
		CustomLinkedList<T> result = new CustomLinkedList<T>();

		inputList.forEach(x -> result.add(x));

		return result;
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {

		CustomLinkedList<T> removalList = this.stream().filter(filter::test)
				.collect(Collectors.toCollection(CustomLinkedList::new));

		boolean result = removalList.size() > 0;

		removalList.forEach(x -> this.remove(x));

		return result;

	}

	@Override
	public String toString() {

		if (this.first == null)
			return "";

		String result = "";

		for (T t : this) {
			result = result + t + " ";
		}

		return result;

	}

	// In addition to iterating through the list values for external use
	// this method is meant for iteration through all nodes that are used to store
	// data. Internal usage only assumed.
	protected LinkedListNode<T> nodeByIndex(int index) {

		if (index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}

		int counter = 0;
		LinkedListNode<T> result = this.first;

		while (counter < index) {
			result = result.next;
			counter++;
		}

		return result;
	}

	@Override
	public boolean add(T addedElement) {

		boolean result;

		try {
			if (addedElement == null) {
				throw new IllegalArgumentException();
			}

			if (this.first == null) {

				// No nodes in this list, creating one from scratch
				LinkedListNode<T> firstNode = LinkedListNode.makeNodeOf(addedElement, null);
				this.first = firstNode;
				this.last = firstNode;
				this.sizeIncrease();
				result = true;

			} else {

				// Adding new node to the list, linking it to the last node in the list.
				LinkedListNode<T> addedNode = LinkedListNode.makeNodeOf(addedElement, this.last);

				LinkedListNode<T> connectNode = this.first;

				//Special case - element is added to a sublist
				if(this.masterList!=null && this.last.next!=null) {
					addedNode.next = this.last.next;
					this.last.next.previous = addedNode;

				}

				while (connectNode.next != null && connectNode!=this.last)
					connectNode = connectNode.next;

				connectNode.next = addedNode;
				addedNode.previous = connectNode;

				this.last = addedNode;
				this.sizeIncrease();
				result = true;

			}

		} catch (Exception E) {
			result = false;
			System.out.println("Log:Failed to add the element " + addedElement.toString() + " to the list");
			System.out.println(E.toString());
		}

		return result;
	}

	@Override
	public T get(int index) {

		if (index >= this.size()) {
			throw new IndexOutOfBoundsException();
		} else if (index < 0) {
			throw new IllegalArgumentException();
		}

		LinkedListNode<T> currentNode = this.nodeByIndex(index);

		return currentNode.getNodeValue();
	}

	@Override
	public void add(int index, T element) {

		if (index > this.size()) {
			throw new IndexOutOfBoundsException();
		} else if (index < 0 || element == null) {
			throw new IllegalArgumentException();
		} else if (index == this.size) {
			this.add(element);
		} else if (index > 0) {

			LinkedListNode<T> beforeAddedNode = this.nodeByIndex(index - 1);
			LinkedListNode<T> afterAddedNode = this.nodeByIndex(index);
			LinkedListNode<T> addedNode = LinkedListNode.makeNodeOf(element, beforeAddedNode);

			addedNode.next = afterAddedNode;
			addedNode.previous = beforeAddedNode;

			afterAddedNode.previous = addedNode;
			beforeAddedNode.next = addedNode;

			this.sizeIncrease();

		} else if (index == 0) {

			LinkedListNode<T> addedNode = LinkedListNode.makeNodeOf(element, null);
			addedNode.next = this.first;
			this.first = addedNode;
			this.sizeIncrease();

			if(this.masterList!=null && addedNode.previous == null) {
				this.masterList.first = addedNode;
			}


		}

	}

	@Override
	public T remove(int index) {

		T result = null;

		if (index >= this.size()) {
			throw new IndexOutOfBoundsException();
		} else if (index < 0) {
			throw new IllegalArgumentException();
		} else if (index > 0) {

			LinkedListNode<T> beforeRemovedNode = this.nodeByIndex(index - 1);
			LinkedListNode<T> afterRemovedNode = beforeRemovedNode.next.next;
			result = beforeRemovedNode.next.getNodeValue();

			beforeRemovedNode.next = afterRemovedNode;

			if (afterRemovedNode != null)
				afterRemovedNode.previous = beforeRemovedNode;

			// Last element is removed, "last" value must be updated
			if (index == this.size() - 1) {
				this.last = beforeRemovedNode;
			}

			this.sizeDecrease();

		} else if (index == 0) {

			LinkedListNode<T> removedNote = this.first;
			result = removedNote.getNodeValue();

			this.first = removedNote.next;
			removedNote.next = null;

			if (this.first != null) {
				this.first.previous = null;

				// This can happen only if a given list is a sublist and it's first element must
				// be removed.
				if (removedNote.previous != null) {
					removedNote.previous.next = this.first;
				}

				if(this.masterList!= null)
					this.masterList.first = this.first;

			}

			this.sizeDecrease();

		}

		return result;
	}

	@Override
	public boolean remove(Object removedElement) {

		if (removedElement == null) {
			throw new IllegalArgumentException();
		}

		int removedIndex = this.indexOf(removedElement);

		T result = null;

		if (removedIndex != -1)
			result = this.remove(removedIndex);

		return (result != null);

	}

	@Override
	public Iterator<T> iterator() {
		return new CustomLinkedListIterator<T>(this);
	}

	@Override
	public ListIterator<T> listIterator() {
		return new SpecialListIterator<T>(this);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new SpecialListIterator<T>(this, index);
	}

	@Override
	public int indexOf(Object o) {

		int result = -1;
		int counter = 0;

		for (Object current : this) {
			if (current.equals(o)) {
				result = counter;
				break;
			}
			counter++;
		}

		return result;
	}

	@Override
	public boolean removeAll(Collection<?> toRemove) {

		Object[] cleanThem = toRemove.toArray();

		int initialSize = this.size();

		for (Object obj : cleanThem)
			this.remove(obj);

		return this.size() < initialSize;
	}

	@Override
	public boolean retainAll(Collection<?> toRetain) {

		int initialSize = this.size();

		this.removeIf(x -> !toRetain.contains(x));

		return this.size() < initialSize;
	}

	@Override
	public boolean containsAll(Collection<?> checkAll) {

		boolean result = true;

		for (Object element : checkAll) {
			if (!this.contains(element)) {
				result = false;
				break;
			}
		}

		return result;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public boolean contains(Object o) {
		return this.indexOf(o) > -1;
	}

	@Override
	public Object[] toArray() {

		Object[] result = new Object[this.size()];

		int i = 0;

		for (T element : this)
			result[i++] = element;

		return result;

	}

	@Override
	public <T> T[] toArray(T[] arr) {

		int counter = 0;
		Iterator<T> iterator = (Iterator<T>) this.iterator();

		while (iterator.hasNext()) {
			arr[counter++] = iterator.next();
		}

		return arr;
	}

	@Override
	public boolean addAll(Collection<? extends T> addCollection) {

		int initialSize = this.size();

		addCollection.forEach(x -> this.add(x));

		return this.size() < initialSize;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> addCollection) {

		int initialSize = this.size();

		for (T element : addCollection) {
			this.add(index, element);
			index++;
		}

		return this.size() < initialSize;
	}

	@Override
	public void clear() {

		if (this.masterList == null) {
			this.forEach(x -> this.remove(x));
		}

		// Clearing a sublist
		else {

			this.masterList.removeAll(this);

			this.first = null;
			this.last = null;

		}
		this.size = 0;

	}

	@Override
	public T set(int index, T element) {

		if (index > this.size()) {
			throw new IndexOutOfBoundsException();
		} else if (element == null) {
			throw new IllegalArgumentException();
		}

		LinkedListNode<T> requestedNode = this.nodeByIndex(index);

		T result = requestedNode.getNodeValue();

		requestedNode.setNodeValue(element);

		return result;
	}

	@Override
	public int lastIndexOf(Object o) {

		int result = -1;
		int counter = 0;

		for (Object current : this) {
			if (current.equals(o)) {
				result = counter;

			}
			counter++;
		}

		return result;

	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {

		if (toIndex > this.size || fromIndex > this.size)
			throw new IndexOutOfBoundsException();

		if (fromIndex > toIndex)
			throw new IllegalArgumentException();

		else if (fromIndex == toIndex)
			return new CustomLinkedList<T>();

		CustomLinkedList<T> result = new CustomLinkedList<T>();
		result.first = this.nodeByIndex(fromIndex);
		result.last = this.nodeByIndex(toIndex - 1);

		result.size = toIndex - fromIndex;

		result.masterList = this;

		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = true;

		if (!(o instanceof List))
			return false;

		if (!(o.getClass() == CustomLinkedList.class))
			return false;

		if (this.size() != ((List<T>) o).size())
			return false;

		for (int i = 0; i < this.size(); i++) {
			T current_list_element = this.get(i);
			T tested_list_element = ((List<T>) o).get(i);

			if (!(current_list_element.equals(tested_list_element)))
				return false;
		}

		return result;
	}

	private void sizeIncrease() {
		this.size++;
		if (this.masterList != null)
			this.masterList.size++;
	}

	private void sizeDecrease() {
		this.size--;
		if (this.masterList != null)
			this.masterList.size--;
	}

	public void swapNodes(int firstIndex, int secondIndex) {

		if (firstIndex >= this.size || secondIndex >= this.size)
			throw new IndexOutOfBoundsException();

		T temp = this.get(firstIndex);
		this.set(firstIndex, this.get(secondIndex));
		this.set(secondIndex, temp);

	}

	public void reverse() {

		int range = this.size();

		for (int i = 0; i < range / 2; i++) {
			this.swapNodes(i, range - i - 1);
		}
	}

	public LinkedListNode<T> getLast(){
		return this.last;
	}

}
