package com.debreuck.neirynck.opdracht.logline;

public class ParseException extends Exception {

    public ParseException(String s) {
        super(s);
    }

    public ParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}