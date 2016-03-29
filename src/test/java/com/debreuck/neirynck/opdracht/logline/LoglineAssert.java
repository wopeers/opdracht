package com.debreuck.neirynck.opdracht.logline;

import com.debreuck.neirynck.opdracht.logline.model.Loglevel;
import com.debreuck.neirynck.opdracht.logline.model.Logline;
import org.assertj.core.api.AbstractAssert;

public class LoglineAssert extends AbstractAssert<LoglineAssert, Logline> {

    public static LoglineAssert AssertThat(Logline logline) {
        return new LoglineAssert(logline);
    }

    private LoglineAssert(Logline actual) {
        super(actual, LoglineAssert.class);
    }

    public LoglineAssert hasFormattedTimestamp(String timestamp) {
        isNotNull();

        if (!timestamp.equals(actual.getFormattedTimeStamp()))
            failWithMessage("Expected timestamp to be <%s> but was <%s>", timestamp, actual.getFormattedTimeStamp());

        return this;
    }

    public LoglineAssert hasThread(String thread) {
        isNotNull();

        if (!thread.equals(actual.getThread()))
            failWithMessage("Expected thread to be <%s> but was <%s>", thread, actual.getThread());

        return this;
    }

    public LoglineAssert hasLoglevel(Loglevel loglevel) {
        isNotNull();

        if (!loglevel.equals(actual.getLoglevel()))
            failWithMessage("Expected log level to be <%s> but was <%s>", loglevel, actual.getLoglevel());

        return this;
    }

    public LoglineAssert hasGeneratedClass(String generatingClass) {
        isNotNull();

        if (!generatingClass.equals(actual.getGeneratingClass()))
            failWithMessage("Expected log level to be <%s> but was <%s>", generatingClass, actual.getGeneratingClass());

        return this;
    }

    public LoglineAssert hasLogMessage(String logMessage) {
        isNotNull();

        if (!logMessage.equals(actual.getLogMessage()))
            failWithMessage("Expected log message to be <%s> but was <%s>", logMessage, actual.getLogMessage());

        return this;
    }
}
