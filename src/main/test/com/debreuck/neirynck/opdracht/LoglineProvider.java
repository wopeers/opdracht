package com.debreuck.neirynck.opdracht;

import static com.debreuck.neirynck.opdracht.logline.model.LoglineBuilder.*;
import static java.math.BigInteger.valueOf;
import static junitparams.JUnitParamsRunner.$;

public class LoglineProvider {

    public static Object[] provideLoglinesWithExpectedAmountOfRenderingsCreated() {
        return $(
                $(1, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1"))
                )),
                $(2, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(1L), valueOf(2L), "thread1"))
                )),
                $(3, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(2L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(3L), valueOf(1L), "thread1"))
                )),
                $(0, $Array(
                        a(getRenderingLogline("uuid")),
                        a(returnRenderingLogline("thread1", "uuid"))
                )),
                $(0, $Array(
                        a(logLine())
                ))
        );
    }

    public static Object[] provideLoglinesWithExpectedAmountOfDuplicates() {
        return $(
                $(0, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1"))
                )),
                $(1, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1"))
                )),
                $(0, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(3L), valueOf(1L), "thread1"))
                )),
                $(0, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(getRenderingLogline("uuid")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(logLine().withLogMessage("foo bar"))
                ))
        );
    }

    public static Object[] provideLoglinesWithExpectedAmountOfUnnecessary() {
        return $(
                $(1, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid"))
                )),
                $(2, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(2L), valueOf(1L), "thread1"))
                )),
                $(0, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(getRenderingLogline("uuid"))
                )),
                $(0, $Array(
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(getRenderingLogline("uuid")),
                        a(getRenderingLogline("uuid"))
                )),
                $(1, $Array(
                        a(getRenderingLogline("uuid")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(logLine().withLogMessage("foo bar"))
                ))
        );
    }

    public static <T> T[] $Array(T... elements){
        return elements;
    }


}
