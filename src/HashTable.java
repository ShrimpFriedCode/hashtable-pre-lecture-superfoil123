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

        if(find == null){
            for(int i = 0; i < 20; i++){
                if(table.get(i).peekFirst() == null){
                    Entry<K,V> ent = new Entry<>(key, value);
                    table.get(i).addFirst(ent);
                    break;
                }
            }
        }
        else{
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
            for(int i = 0; i < 20; i++){
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
        for(int i = 0; i < 20; i++){
            if(table.get(i).peekFirst() != null && key == table.get(i).peekFirst().key){
                return table.get(i).peekFirst();
            }
        }
        return null;
    }

}