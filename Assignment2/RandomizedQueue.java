import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] s;
   private int num = 0; 
    // construct an empty randomized queue
   public RandomizedQueue() {
      s = (Item[]) new Object[1];
   }
   // is the queue empty?
   public boolean isEmpty() {
      return num == 0; 
   }
   // return the number of items on the queue
   public int size() {
      return num; 
   }
   // add the item
   public void enqueue(Item item) {
      if (item == null) { throw new java.lang.NullPointerException(); }
      if (num == 0) { 
         s[0] = item; 
         num++; 
      }
      else {
         if (num == s.length) { resize(2 * s.length); }
         int index = StdRandom.uniform(num+1);
         if (index == num) { s[num++] = item; }
         else {
            Item temp = s[index];
            s[num++] = temp;
            s[index] = item;
         }
      } 
   }
   // remove and return a random item
   public Item dequeue() {
      if (num == 0) { throw new java.util.NoSuchElementException(); }
      if (num == s.length/4) { 
         resize(s.length/2); 
      }
      int index = StdRandom.uniform(num);
      Item item = s[index];
      if (index == num - 1) { s[--num] = null; }
      else {
         s[index] = s[--num];
         s[num] = null;
      }
      return item;

   }

   private void resize(int capacity) {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < num; i++) { 
         copy[i] = s[i]; 
      }
      s = copy; 
   }


   // return (but do not remove) a random item
   public Item sample() {
      if (num == 0) { throw new java.util.NoSuchElementException(); }
      int index = StdRandom.uniform(num);
      return s[index];
   }
   // return an independent iterator over items in random order
   public Iterator<Item> iterator() { return new ListIterator(); } 

   private class ListIterator implements Iterator<Item> {
      
      private int current; 
      private int[] indexRand;
      
      public ListIterator() {
         current = 0; 
         indexRand = new int[num];
         for (int i = 0; i < num ; i++)
         {
            indexRand[i] = i; 
         }
         StdRandom.shuffle(indexRand);
      }

      public boolean hasNext() { return current < num; }
      public void remove() { throw new java.lang.UnsupportedOperationException(); }
      public Item next() {
         if (current >= num) { throw new java.util.NoSuchElementException(); }

         return s[indexRand[current++]];
      }
   }
   // unit testing   
   public static void main(String[] args) {
  /*    RandomizedQueue<Integer> randque = new RandomizedQueue<Integer>();
      StdOut.println("size: "+randque.size());
      randque.enqueue(1);
      randque.enqueue(4);
      randque.enqueue(6);
       StdOut.println("size: "+randque.size());
     // randque.enqueue(8);
      StdOut.println("sample: "+randque.sample());
       StdOut.println("size: "+randque.size());
       randque.dequeue();
       StdOut.println("size: "+randque.size());
      // for (int x : randque) 
      //    StdOut.println(x);
      Iterator<Integer> i = randque.iterator();
      while (i.hasNext())
      {
         int x = i.next();
         StdOut.println("all: "+x);
      }
*/
   }
}