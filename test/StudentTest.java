import org.junit.Test;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void containsOnEmpty() throws Exception {
        HashTable<String,Integer> T = new HashTable<>();
        assertFalse(T.contains("foo"));
    }

    @Test
    public void getOnEmpty() throws Exception {
        HashTable<String,Integer> T = new HashTable<>();
        try {
            T.get("foo");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void put() {
        HashTable<String,Integer> T = new HashTable<>();
        T.put("foo", 42);
        assertTrue(T.contains("foo"));
        T.put("bar", 5);
        assertTrue(T.contains("bar"));
        T.put("foo", 44);
        assertTrue(T.contains("foo"));
        assertTrue(T.contains("bar"));
    }

    @Test
    public void putAndGet() throws Exception {
        HashTable<String,Integer> T = new HashTable<>();
        T.put("foo", 42);
        assertEquals(new Integer(42), T.get("foo"));

        T.put("bar", 5);
        assertEquals(new Integer(5), T.get("bar"));

        T.put("foo", 44);
        assertEquals(new Integer(44), T.get("foo"));
    }

    @Test
    public void remove() throws Exception {
        HashTable<String,Integer> T = new HashTable<>();
        T.remove("foo");
        T.put("foo", 42);
        T.put("bar", 5);
        assertTrue(T.contains("bar"));
        T.remove("foo");
        assertFalse(T.contains("foo"));
        assertTrue(T.contains("bar"));
    }

    @Test
    public void big() throws Exception{
        HashTable<String,Integer> T = new HashTable<>();
        int[] ar2 = new int[100];
        int[] ar1 = new int[100];
        for(int i = 0; i < ar1.length; i++) {
            ar1[i] = (int)(Math.random() * 1000);
        }

        for(int i = 0; i < ar2.length; i++) {
            ar2[i] = (int)(Math.random() * 1000);
        }

        for(int i = 0; i < ar2.length; i++){
            T.put(Integer.toString(ar1[i]), ar2[i]);
            //System.out.println(T.table.get(i).peekFirst().key);
        }

        for(int i = 0; i < ar1.length; i++){
            System.out.println(Integer.toString(ar1[i]));
            System.out.println(T.contains(Integer.toString(ar1[i])));
        }

    }

}