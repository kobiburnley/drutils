package drutils.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Kobi
 * Date: 11/19/15
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class KVList<K,V> extends ArrayList<KeyValue<K,V>> {

    public void put(K key, V value){
        add(new KeyValue<K, V>(key, value));
    }

    public KVList<K,V> append(K key, V value){
        put(key, value);
        return this;
    }

    public static <K,V> List<KeyValue<K,V>> composite(K key, V value){
        return new KVList<K,V>().append(key, value);
    }
}
