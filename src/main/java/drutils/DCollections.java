package drutils;

import drutils.function.Function;
import drutils.function.Predicate;
import drutils.function.VoidFunction;

import java.util.*;

/**
 * Created by kobi on 08/10/16.
 */
public class DCollections {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> void each(Collection<T> collection, VoidFunction<T> function) {
        if (collection != null) {
            for (T t : collection) {
                if (t != null) function.apply(t);
            }
        }
    }

    public static <T, E, K> Map<E, K> keyBy(Collection<T> collection, Function<T, E> keyMapper, Function<T, K> valueMapper) {
        Map<E, K> map = new HashMap<>();
        if (collection != null) {
            for (T t : collection) {
                if (t != null) map.put(keyMapper.apply(t), valueMapper.apply(t));
            }
        }
        return map;
    }


    public static <T, E> Map<E, T> map(Collection<T> collection, Function<T, E> function) {
        Map<E, T> map = new HashMap<>();
        if (collection != null) {
            for (T t : collection) {
                if (t != null) map.put(function.apply(t), t);
            }
        }
        return map;
    }


    public static <T, E> List<E> transform(Collection<T> collection, Function<T, E> function) {
        List<E> list = new ArrayList<>();
        if (collection != null) {
            for (T t : collection) {
                if (t != null) list.add(function.apply(t));
            }
        }
        return list;
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<? super T> predicate) {
        List<T> list = new ArrayList<>();
        if (collection != null) {
            for (T t : collection) {
                if (t != null && predicate.apply(t)) list.add(t);
            }
        }
        return list;
    }

    public static <T> T find(Collection<T> collection, Predicate<? super T> predicate) {
        if (collection != null) {
            for (T t : collection) {
                if (t != null && predicate.apply(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T> List<T> range(List<T> list, int start, int end) {
        List<T> collection = new ArrayList<>();
        if (!isEmpty(list)) {
            for (int i = start; i < end; i++) {
                collection.add(list.get(i));
            }
        }
        return collection;
    }

    public static <T> List<T> inLeftButNotInRight(Collection<T> left, Collection<T> right) {
        List<T> collection = new ArrayList<>();

        for (T t : left) {
            if (!right.contains(t)) {
                collection.add(t);
            }
        }
        return collection;
    }


    public static <T, E> List<T> inLeftButNotInRight(Collection<T> left, Collection<E> right, Function<T, E> function) {
        List<T> collection = new ArrayList<>();

        for (T t : left) {
            if (!right.contains(function.apply(t))) {
                collection.add(t);
            }
        }
        return collection;
    }

    public static <T> List<Map.Entry<T, Integer>> zip(Collection<T> collection) {
        List<Map.Entry<T, Integer>> arrayList = new ArrayList<>();
        if (collection != null) {
            int i = 0;
            for (T next : collection) {
                if (next != null) {
                    arrayList.add(new AbstractMap.SimpleImmutableEntry<>(next, i));
                }
                i++;
            }
        }
        return arrayList;
    }

    public static Map<String, Integer> zipWithIndexMap(Collection<String> collection) {
        return keyBy(zip(collection), new Function<Map.Entry<String, Integer>, String>() {
            @Override
            public String apply(Map.Entry<String, Integer> params) {
                return params.getKey();
            }
        }, new Function<Map.Entry<String, Integer>, Integer>() {
            @Override
            public Integer apply(Map.Entry<String, Integer> params) {
                return params.getValue();
            }
        });
    }

    public static <T> String toString(Collection<T> collection) {
        String s = "";
        if (collection != null) {
            for (T t : collection) {
                s += ", " + t.toString();
            }
        }
        return s;
    }

    public static <T> String join(Collection<T> collection, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> parts = collection.iterator();
        if (parts.hasNext()) {
            stringBuilder.append(parts.next());
            while (parts.hasNext()) {
                stringBuilder.append(separator);
                stringBuilder.append(parts.next());
            }
        }
        return stringBuilder.toString();
    }

    public static boolean inRange(Collection list, int i) {
        return !isEmpty(list) && i >= 0 && i < list.size();
    }
}
