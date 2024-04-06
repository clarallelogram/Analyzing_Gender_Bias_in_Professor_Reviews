package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<MyPair<K,V>> {
    // num of entries to the table
    private int size;
    // num of buckets
    private int capacity = 16;
    // load factor needed to check for rehashing
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<MyPair<K, V>>> buckets;


    // constructors
    public MyHashTable() {
        // ADD YOUR CODE BELOW THIS
        this.size = 0;
        this.buckets = new ArrayList<LinkedList<MyPair<K, V>>>(this.capacity);
        for (int i = 0; i < this.capacity; i++) {
            this.buckets.add(new LinkedList<MyPair<K, V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }

    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
        this.size = 0;
        this.capacity = initialCapacity;
        this.buckets = new ArrayList<LinkedList<MyPair<K, V>>>(this.capacity);
        for (int i = 0; i < this.capacity; i++) {
            this.buckets.add(new LinkedList<MyPair<K, V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int numBuckets() {
        return this.capacity;
    }

    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList<MyPair<K, V>>> getBuckets() {
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode()) % this.capacity;
        return hashValue;
    }

    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        // ADD YOUR CODE BELOW HERE
        for (MyPair<K, V> myPair : this.buckets.get(this.hashFunction(key))) {
            if (myPair.getKey().equals(key)) {
                V tempValue = myPair.getValue();
                myPair.setValue(value);
                return tempValue;
            }
        }
        this.buckets.get(this.hashFunction(key)).add(new MyPair<K, V>(key, value));
        this.size++;

        if ((double) this.size/this.capacity > MAX_LOAD_FACTOR) {
            this.rehash();
        }

        return null;
        // ADD YOUR CODE ABOVE HERE
    }

    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */

    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
        LinkedList<MyPair<K, V>> bucket = buckets.get(hashFunction(key));
        for (MyPair<K, V> myPair : bucket) {
            if (myPair.getKey().equals(key)) {
                return myPair.getValue();
            }
        }
        return null;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
        for (MyPair<K, V> myPair : buckets.get(this.hashFunction(key))) {
            if (myPair.getKey().equals(key)) {
                V tempValue = myPair.getValue();
                this.buckets.get(this.hashFunction(key)).remove(myPair);
                this.size--;
                return tempValue;
            }
        }
        return null;
    }
        //ADD YOUR CODE ABOVE HERE
    /**
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<LinkedList<MyPair<K, V>>> doubleBuckets = new ArrayList<>(this.capacity * 2);
        for (int i = 0; i < this.capacity * 2; i++) {
            doubleBuckets.add(new LinkedList<MyPair<K, V>>());
        }

        this.capacity *= 2;

        for (LinkedList<MyPair<K, V>> bucket : this.buckets) {
            for (MyPair<K, V> myPair : bucket) {
                doubleBuckets.get(this.hashFunction(myPair.getKey())).add(myPair);
            }
        }
        this.buckets = doubleBuckets;
    }
    //ADD YOUR CODE ABOVE HERE

    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */

    public ArrayList<K> getKeySet() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<K> keySet = new ArrayList<K>(this.size);
        for (LinkedList<MyPair<K, V>> bucket : this.buckets) {
            for (MyPair<K, V> myPair : bucket) {
                keySet.add(myPair.getKey());
            }
        }
        return keySet;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> getValueSet() {
        //ADD CODE BELOW HERE
        ArrayList<V> valueSet = new ArrayList<V>(this.size);
        MyHashTable<V, K> hashSet = new MyHashTable<V, K>();
        for (LinkedList<MyPair<K, V>> bucket : this.buckets) {
            for (MyPair<K, V> myPair : bucket) {
                if (hashSet.get(myPair.getValue())==null) {
                    valueSet.add(myPair.getValue());
                    hashSet.put(myPair.getValue(), myPair.getKey());
                }
            }
        }
        return valueSet;
        //ADD CODE ABOVE HERE
    }

    /**
     * Returns an ArrayList of all the key-value pairs present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<MyPair<K, V>> getEntries() {
        //ADD CODE BELOW HERE
        ArrayList<MyPair<K, V>> entrySet = new ArrayList<MyPair<K, V>>(this.size);
        for (LinkedList<MyPair<K, V>> bucket : this.buckets) {
            for (MyPair<K, V> myPair : bucket) {
                entrySet.add(myPair);
            }
        }
        return entrySet;
        //ADD CODE ABOVE HERE
    }

    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }
    private class MyHashIterator implements Iterator<MyPair<K, V>> {
        private Iterator<MyPair<K, V>> iterator;
        private ArrayList<MyPair<K, V>> entries;

        private MyHashIterator() {
            this.entries = getEntries();
            this.iterator = entries.iterator();
        }

        @Override
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
            return this.iterator.hasNext();
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        public MyPair<K, V> next() {
            //ADD YOUR CODE BELOW HERE
            if (!this.iterator.hasNext()) {
                return null;
            }
            return this.iterator.next();
            //ADD YOUR CODE ABOVE HERE
        }

    }
}
