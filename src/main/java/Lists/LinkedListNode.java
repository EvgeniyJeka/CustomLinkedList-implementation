package Lists;

public class LinkedListNode<T> {

	private T nodeValue;
	LinkedListNode<T> previous;
	LinkedListNode<T> next;

	public LinkedListNode(T value, LinkedListNode<T> previous) {
		this.nodeValue = value;
		this.previous = previous;
		this.next = null;
	}

	public static <T> LinkedListNode<T> makeNodeOf(T value, LinkedListNode<T> previous) {
		return new LinkedListNode<T>(value, previous);
	}

	public T getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(T nodeValue) {
		this.nodeValue = nodeValue;
	}

	@Override
	public String toString() {
		String result = "Node Value: " + this.getNodeValue();

		if (this.next != null)
			result += ", Next Node Value: " + this.next.getNodeValue();

		if (this.previous != null)
			result += ", Previous Node Value: " + this.previous.getNodeValue();

		return result;
	}

}
