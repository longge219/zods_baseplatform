package com.zods.plugins.zods.bean;


import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class ResponseBean implements Serializable {
    private transient ThreadPoolExecutor executor;
    private String code;
    private String message;
    private Object[] args;
    private Object data;

    public ResponseBean() {
        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1), (r) -> {
            return new Thread(r, "ResponseBean.then.executor");
        });
    }

    private ResponseBean(ResponseBean.Builder builder) {
        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1), (r) -> {
            return new Thread(r, "ResponseBean.then.executor");
        });
        this.code = builder.code;
        this.args = builder.args;
        this.message = builder.message;
        this.data = builder.data;
    }

    public ResponseBean thenAsync(Consumer<Object> consumer, Object param) {
        this.executor.execute(() -> {
            consumer.accept(param);
        });
        return this;
    }

    public ResponseBean then(Consumer<Object> consumer, Object param) {
        consumer.accept(param);
        return this;
    }

    public static ResponseBean.Builder builder() {
        return new ResponseBean.Builder();
    }

    public String getCode() {
        return this.code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class Builder {
        private String code;
        private Object data;
        private String message;
        private Object[] args;

        private Builder() {
            this.code = "200";
        }

        public ResponseBean.Builder code(String code) {
            this.code = code;
            return this;
        }

        public ResponseBean.Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBean.Builder args(Object[] args) {
            this.args = args;
            return this;
        }

        public ResponseBean.Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ResponseBean build() {
            return new ResponseBean(this);
        }
    }
}
