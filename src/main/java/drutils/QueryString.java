package drutils;

import drutils.function.DFunction;
import drutils.function.Function;
import drutils.function.Predicate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by kobi on 08/10/16.
 */
public class QueryString {

    public static Map.Entry<String, String> queryStringEntry(String encodedKeyVal, String sep) {
        int idx = encodedKeyVal.indexOf(sep);
        Map.Entry<String, String> retVal = null;
        if (idx >= 0) {
            try {
                retVal = new AbstractMap.SimpleImmutableEntry<>(URLDecoder.decode(encodedKeyVal.substring(0, idx), "UTF-8"), URLDecoder.decode(encodedKeyVal.substring(idx + 1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    public static String entryToKeyVal(Map.Entry<String, String> entry) {
        String s = null;
        try {
            s = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    public static Map<String, String> splitQuery(String str) {
        return splitQuery(str, "&", "=");
    }

    public static Map<String, String> ofUrl(String url) {
        int idx = url.indexOf("?");
        Map<String, String> retVal = new HashMap<>();
        if (idx >= 0) {
            return splitQuery(url.substring(idx + 1));
        }
        return retVal;
    }

    public static Map<String, String> splitQuery(String source, String sep1, final String sep2) {
        if (DStrings.isEmpty(source)) return new HashMap<>();
        Collection<Map.Entry<String, String>> entries = DCollections.transform(Arrays.asList(source.split(sep1)), new Function<String, Map.Entry<String, String>>() {
            @Override
            public Map.Entry<String, String> apply(String params) {
                return queryStringEntry(params, sep2);
            }
        });
        return DCollections.keyBy(
                DCollections.filter(entries, DFunction.<Map.Entry<String, String>>notNull()),
                new Function<Map.Entry<String, String>, String>() {
                    @Override
                    public String apply(Map.Entry<String, String> params) {
                        return params.getKey();
                    }
                },
                new Function<Map.Entry<String, String>, String>() {
                    @Override
                    public String apply(Map.Entry<String, String> params) {
                        return params.getValue();
                    }
                }
        );
    }

    public static String toQueryString(Map<String, String> hashMap) {
        return DCollections.join(
                DCollections.transform(
                        hashMap.entrySet(),
                        new Function<Map.Entry<String, String>, String>() {
                            @Override
                            public String apply(Map.Entry<String, String> params) {
                                return entryToKeyVal(params);
                            }
                        }
                ),
                "&"
        );
    }

}
