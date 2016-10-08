package drutils.function;

/**
 * Created by kobi on 08/10/16.
 */
public class DFunction {
    public static <T> Predicate<T> notNull(){
        return new Predicate<T>() {
            @Override
            public boolean apply(T t) {
                return t != null;
            }
        };
    }

    public static <T> Function<T, T> identity(){
        return new Function<T, T>() {
            @Override
            public T apply(T params) {
                return params;
            }
        };
    }
}
