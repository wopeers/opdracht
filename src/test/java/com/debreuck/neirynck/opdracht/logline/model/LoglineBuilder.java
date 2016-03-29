package com.debreuck.neirynck.opdracht.logline.model;

import java.math.BigInteger;
import java.util.Date;

import static java.lang.String.format;

public class LoglineBuilder {

    public static Logline a(LoglineBuilder builder) { return builder.build();}

    public static LoglineBuilder logLine() { return new LoglineBuilder(); }

    public static LoglineBuilder startRenderingLogline(BigInteger doc, BigInteger page, String thread) {
        return new LoglineBuilder()
                .withTimeStamp(new Date())
                .withThread(thread)
                .withLogMessage(format("Executing request startRendering with arguments [%d, %d]",doc.longValue(),page.longValue()));
    }

    public static LoglineBuilder returnRenderingLogline(String thread, String uid) {
        return new LoglineBuilder()
                .withTimeStamp(new Date())
                .withThread(thread)
                .withLogMessage(format("Service startRendering returned %s",uid));
    }

    public static LoglineBuilder getRenderingLogline(String uid) {
        return new LoglineBuilder()
                .withTimeStamp(new Date())
                .withLogMessage(format("Executing request getRendering with arguments [%s]",uid));
    }

    private Logline logline = new Logline();

    private Logline build(){
        return logline;
    }

    public LoglineBuilder withTimeStamp(Date timeStamp){
        logline.setTimestamp(timeStamp);
        return this;
    }

    public LoglineBuilder withThread(String thread){
        logline.setThread(thread);
        return this;
    }

    public LoglineBuilder withLoglevel(Loglevel loglevel){
        logline.setLoglevel(loglevel);
        return this;
    }

    public LoglineBuilder withGeneratingClass(String generatingClass){
        logline.setGeneratingClass(generatingClass);
        return this;
    }

    public LoglineBuilder withLogMessage(String logMessage){
        logline.setLogMessage(logMessage);
        return this;
    }




}
