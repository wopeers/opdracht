package com.debreuck.neirynck.opdracht.logline.parse;

public class ParseException extends RuntimeException {

    public ParseException(String s) {
        super(s);
    }

    public ParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
