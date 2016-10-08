package drutils.http;

/**
 * Created with IntelliJ IDEA.
 * User: Kobi
 * Date: 11/19/15
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyValue<K, V> {
    K key;
    V value;

    public KeyValue(K key, V value) {
        this.value = value;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String toString(){
        return key + ":" + value;
    }
}
