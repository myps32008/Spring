package viettel.CQRSES.Events;

public interface IEventListener<T> {
    void onData(T data);
    void processComplete();
}
