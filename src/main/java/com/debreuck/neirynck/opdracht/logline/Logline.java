package com.debreuck.neirynck.opdracht.logline;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logline {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    private Date timestamp;
    private Loglevel loglevel;
    private String thread;
    private String generatingClass;
    private String logMessage;

    public Logline(Date timestamp, String thread, Loglevel loglevel, String generatingClass, String logMessage) {
        this.timestamp = timestamp;
        this.loglevel = loglevel;
        this.thread = thread;
        this.generatingClass = generatingClass;
        this.logMessage = logMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimeStamp() {
        return dateFormat.format(timestamp);
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Loglevel getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(Loglevel loglevel) {
        this.loglevel = loglevel;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getGeneratingClass() {
        return generatingClass;
    }

    public void setGeneratingClass(String generatingClass) {
        this.generatingClass = generatingClass;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    @Override
    public String toString() {
        return "Logline{" +
                "timestamp=" + timestamp +
                ", loglevel=" + loglevel +
                ", thread='" + thread + '\'' +
                ", generatingClass='" + generatingClass + '\'' +
                ", logMessage='" + logMessage + '\'' +
                '}';
    }
}
