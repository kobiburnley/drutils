package drutils;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryStringTest {
    @Test
    public void splitQuery() throws Exception {
        Map<String, String> map = QueryString.splitQuery("a=1&b=2&c=3");
        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
        assertEquals("3", map.get("c"));
    }

    @Test
    public void ofUrl() throws Exception {
        Map<String, String> map = QueryString.ofUrl("https://dsgsdgs.com?a=1&b=2&c=3");
        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
        assertEquals("3", map.get("c"));
    }

    @Test
    public void toQueryString() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        assertEquals("a=1&b=2&c=3", QueryString.toQueryString(map));
    }

    @Test
    public void entryToKeyVal() throws Exception {
        assertEquals(
                "a=1", QueryString.entryToKeyVal(new AbstractMap.SimpleImmutableEntry<String, String>("a", "1"))
        );
    }

    @org.junit.Test
    public void queryStringEntry() throws Exception {
        Map.Entry<String, String> entry = QueryString.queryStringEntry("a=1", "=");
        assertEquals("a", entry.getKey());
        assertEquals("1", entry.getValue());
    }

}