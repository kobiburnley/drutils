package drutils;

import drutils.function.Function;
import drutils.function.Predicate;

import java.util.Map;

public class DCompare {
    public static <T> Predicate<T> getMapPredicate(final Map<String, Integer> map, final Function<T, String> function) {
        return new Predicate<T>() {
            public boolean apply(T t) {
                return map.get(function.apply(t)) != null;
            }
        };
    }
}
