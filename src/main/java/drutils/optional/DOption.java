package drutils.optional;


/**
 * Created by kobi on 08/10/16.
 */
public class DOption<T> {

    T t;

    public DOption(T t){
        this.t = t;
    }

    public T orElse(T t2){
        return this.t != null? this.t : t2;
    }

    public static <T> DOption<T> of(T t){
        return new DOption<>(t);
    }
}
