package com.debreuck.neirynck.opdracht.logline;

public interface ILoglineParser {

    boolean isLogline(String line);

    Logline parseToLogline(String line) throws ParseException;
}
