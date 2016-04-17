import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Deque<Item> implements Iterable<Item> 
{
	private Node first;
	private Node last;
	private int num = 0; 

	private class Node
	{
		Item item;
		Node next;
		Node previous;
	}
    // construct an empty deque
    public Deque() {
    	first = null;
    	last = null;
    }               
    // is the deque empty?
    public boolean isEmpty() {
    	return num == 0;
    }
    // return the number of items on the deque
    public int size() {
    	return num;
    }
    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) {
    		throw new java.lang.NullPointerException();
    	}
    	if (isEmpty()) {
    		first = new Node();
    		first.item = item;
    		last = first; 
    	}
    	else {
    		Node oldfirst = first; 
    		first = new Node();
    		oldfirst.previous = first;
    		first.item = item; 
    		first.next = oldfirst;
    	}
    	num++;
    }
    // add the item to the end
    public void addLast(Item item) {
    	if (item == null) {
    		throw new java.lang.NullPointerException();
    	}
    	if (isEmpty()) {
			last = new Node();
    		last.item = item;
    		first = last;
    	}
    	else {
    		Node oldlast = last; 
    		last = new Node();
    		last.item = item; 
    		oldlast.next = last;
    		last.previous = oldlast;
    	}
    	num++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	Item item = first.item;
        //StdOut.println("item: "+item);
        //StdOut.println("next: "+first.next.next == null);
    	if (num == 1) { 
            first = null; 
            last = null;
        }
        else { 
            first.next.previous = null;
            first = first.next; 
    	  //  first.previous = null;
        }
    	num--;
    	return item;
    }
    // remove and return the item from the end
    public Item removeLast() {
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	Item item = last.item;
    	if (num == 1) { 
            last = null; 
            first = null; 
        }
        else {
            last.previous.next = null;
            last = last.previous; 
    	    //last.next = null;
        }
    	num--;
    	return item;

    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item>
    {
    	private Node current = first;

    	public boolean hasNext() { return current != null; }
    	public void remove() { throw new java.lang.UnsupportedOperationException(); }
    	public Item next() {
    		if (current == null) { throw new java.util.NoSuchElementException(); }
    		Item item = current.item;
    		if (current.next != null) { current = current.next; }
    		else { current = null; }
    		return item;
    	}

    }
    // unit testing
    public static void main(String[] args) {
    	Deque<Integer> vals = new Deque<Integer>();
    //	vals.addFirst(8);
    //	vals.addFirst(9);
    	vals.addLast(3);
   // 	vals.addLast(4);
    // 	vals.addLast(5);
    //	vals.addFirst(2);
   	    StdOut.println("size: "+vals.size());
    	//StdOut.println(vals.removeFirst());
    	vals.removeLast();
    	StdOut.println("size: "+vals.size());
     	for (int x : vals) 
     		StdOut.println(x);
        
    // 	Iterator<Integer> i = vals.iterator();
    // 	while (i.hasNext())
    // 	{
    // 		int x = i.next();
    // 		StdOut.println("all: "+x);
    // 	}

    }
    
}



