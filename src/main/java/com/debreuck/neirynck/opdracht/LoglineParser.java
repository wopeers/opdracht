package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.model.Loglevel;
import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.logline.parse.ILoglineParser;
import com.debreuck.neirynck.opdracht.logline.parse.ParseException;
import com.google.common.base.Preconditions;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.debreuck.neirynck.opdracht.logline.model.Logline.dateFormat;

public class LoglineParser implements ILoglineParser {

    private static Pattern loglinePattern = Pattern.compile("^(.+?)\\[(.+?)\\](.+?)\\[(.+?)\\]:(.+)$", Pattern.MULTILINE);

    @Override
    public boolean isLogline(String line) {
        if (line == null)
            return false;

        Matcher m = loglinePattern.matcher(line);
        if (!m.find())
            return false;

        try {
            if (parseDate(m) == null)
                return false;

            getLoglevel(m);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Logline parseToLogline(String line) throws ParseException {
        Preconditions.checkNotNull(line);

        Matcher m = loglinePattern.matcher(line);
        Date date;
        Loglevel loglevel;
        if (!m.find())
            throw new ParseException("Input string dit not match expected log line pattern.");

        try {
            date = parseDate(m);
            loglevel = getLoglevel(m);
        } catch (Exception e) {
            throw new ParseException("Error while parsing Logline: " + line, e);
        }

        return new Logline(date, m.group(2).trim(), loglevel, m.group(4).trim(), m.group(5).trim());
    }

    private Loglevel getLoglevel(Matcher m) {
        return Loglevel.valueOf(m.group(3).trim());
    }

    private Date parseDate(Matcher m) throws java.text.ParseException {
        return dateFormat.parse(m.group(1).trim());
    }

}
