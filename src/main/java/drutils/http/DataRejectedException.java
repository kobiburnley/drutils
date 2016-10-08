package drutils.http;

/**
 * Created by Asus on 20/11/2015.
 */
public class DataRejectedException extends Exception {
    public DataRejectedException(String reason) {
        super(reason);
    }
}
