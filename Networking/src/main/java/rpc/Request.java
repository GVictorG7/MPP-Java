package rpc;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType type;
    private Object data;

    private Request() {
    }

    RequestType type() {
        return type;
    }

    private void type(RequestType type) {
        this.type = type;
    }

    Object data() {
        return data;
    }

    private void data(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

    static class Builder {
        private final Request request = new Request();

        Builder type(RequestType type) {
            request.type(type);
            return this;
        }

        Builder data(Object data) {
            request.data(data);
            return this;
        }

        Request build() {
            return request;
        }
    }
}
