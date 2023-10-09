package rpc;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType type;
    private Object data;

    private Response() {
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

    ResponseType type() {
        return type;
    }

    Object data() {
        return data;
    }

    private void type(ResponseType type) {
        this.type = type;
    }

    private void data(Object data) {
        this.data = data;
    }

    static class Builder {
        private final Response response = new Response();

        Builder type(ResponseType type) {
            response.type(type);
            return this;
        }

        Builder data(Object data) {
            response.data(data);
            return this;
        }

        Response build() {
            return response;
        }
    }
}
