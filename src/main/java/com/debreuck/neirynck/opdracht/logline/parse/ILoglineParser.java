package com.debreuck.neirynck.opdracht.logline.parse;

import com.debreuck.neirynck.opdracht.logline.model.Logline;

public interface ILoglineParser {

    boolean isLogline(String line);

    Logline parseToLogline(String line) throws ParseException;
}
