package model;


import reactor.core.publisher.MonoSink;

/**
 * Created by gede on 13/04/18.
 */
public class SCTPRequestMessage<T> {

    private  MonoSink<T> sink;

    private  Diameter message;

    private String destinationHost;

    public MonoSink<T> getSink() {
        return sink;
    }

    public void setSink(MonoSink<T> sink) {
        this.sink = sink;
    }

    public Diameter getMessage() {
        return message;
    }

    public void setMessage(Diameter message) {
        this.message = message;
    }

    public String getDestinationHost() {
        return destinationHost;
    }

    public void setDestinationHost(String destinationHost) {
        this.destinationHost = destinationHost;
    }

    public SCTPRequestMessage(MonoSink<T> sink, Diameter message, String destinationHost) {
        this.sink = sink;
        this.message = message;
        this.destinationHost = destinationHost;
    }
}
