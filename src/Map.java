public interface Map<K,V> {
    void put(K key, V value);
    void remove(K key);
    V get(K key) throws Exception;
    boolean contains(K key);
}
