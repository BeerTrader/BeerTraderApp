package com.jim.demo1.Exceptions;

/**
 * Created by jasekurasz on 5/21/15.
 */
public class ObjectMappingException extends Exception {
    private final String message;

    public ObjectMappingException(String s) {
        message = "Exception: error mapping string " + s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ObjectMappingException [message" + message + "]";
    }
}
