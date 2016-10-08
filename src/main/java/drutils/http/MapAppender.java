package drutils.http;

import java.util.HashMap;

/**
 * Created by Kobi on 6/14/2016.
 */
public class MapAppender<K,V> extends HashMap<K,V> {

    public static <K,V> MapAppender<K,V> composite(K key, V value){
        return new MapAppender<K,V>().append(key, value);
    }

    private MapAppender<K, V> append(K key, V value) {
        put(key, value);
        return this;
    }
}
