//Ethan Anderson
import java.util.ArrayList;
import java.util.LinkedList;

class Entry<K,V> {
    Entry(K k, V v) { key = k; value = v; }
    K key; V value;
};

public class HashTable<K,V> implements Map<K,V> {

    ArrayList<LinkedList<Entry<K,V>>> table;

    public HashTable() {
        int m = 20;
        table = new ArrayList<>(m);
        for (int i = 0; i != m; ++i)
            table.add(new LinkedList<>());
    }

    public boolean contains(K key) { return findEntry(key) != null; }

    public V get(K key) throws Exception {
        Entry<K,V> e = findEntry(key);
        if (e == null)
            throw new Exception("there is no entry with key " + key.toString());
        else
            return e.value;
    }

    /*
     * TODO
     *
     * The put() method should associate the value with the key, so that
     * subsequent invocations of get() on the same key should return the value.
     */
    public void put(K key, V value) {
        Entry find = findEntry(key);
        System.out.println("Attempting to put key : " + key + " value :" + value);
        boolean space = true;

        if(find == null){//if key DNE
            for(int i = 0; i < table.size(); i++){
                if(table.get(i).peekFirst() == null){//if empty node found
                    table.get(i).addFirst(new Entry<>(key, value));//populate
                    space = false;
                    break; //end iteration
                }
            }
            System.out.println(table.size());
            if(space){//if iterated through whole set, addition to set
                LinkedList toadd = new LinkedList<>();
                toadd.addFirst(new Entry<>(key, value));
                table.add(toadd);
            }

        }
        else{//else val must be in tree
            System.out.println("ex");
            find.value = value;
        }



    }

    /*
     * TODO
     *
     * The remove() method removes the entry whose key matches the key parameter
     * from the hashtable, if such an entry exists.
     */
    public void remove(K key) {
        if(findEntry(key) != null){
            for(int i = 0; i < table.size(); i++){
                if(table.get(i).peekFirst() != null && key == table.get(i).peekFirst().key){
                    table.get(i).remove();
                }
            }
        }
    }

    /**********************
     *
     *
     * The findEntry() helper function returns an entry whose key matches the key parameter,
     * or else returns null if there is not such an entry in the table.
     */

    protected Entry<K,V> findEntry(K key) {
        Entry first;
        for(int i = 0; i < table.size(); i++){
            first = table.get(i).peekFirst();
            if(first != null && key == first.key){
                return first;
            }
        }
        return null;
    }

}