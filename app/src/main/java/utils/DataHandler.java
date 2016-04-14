package utils;

/**
 * Created by guolchen on 2014/12/13.
 */
public interface DataHandler<T> {
    void onData(int code, String msg, T obj);
}