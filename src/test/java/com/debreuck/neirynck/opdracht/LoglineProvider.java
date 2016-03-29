package com.debreuck.neirynck.opdracht;

import static com.debreuck.neirynck.opdracht.logline.model.LoglineBuilder.*;
import static java.math.BigInteger.valueOf;
import static junitparams.JUnitParamsRunner.$;

public class LoglineProvider {

    public static Object[] provideLoglinesWithExpectedAmountOfRenderingsCreated() {
        return $(
                $(1,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid"))
                ),
                $(2,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(startRenderingLogline(valueOf(1L), valueOf(2L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid2"))
                ),
                $(2,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(2L), valueOf(1L), "thread2")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread3")),
                        a(returnRenderingLogline("thread1", "uuid1")),
                        a(returnRenderingLogline("thread2", "uuid2")),
                        a(returnRenderingLogline("thread3", "uuid1"))
                ),
                $(2,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "UUID")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread2")),
                        a(returnRenderingLogline("thread2", "UUID2"))
                ),
                $(0,
                        a(getRenderingLogline("uuid")),
                        a(returnRenderingLogline("thread1", "uuid"))
                ),
                $(0,
                        a(logLine())
                )
        );
    }

    public static Object[] provideLoglinesWithExpectedAmountOfDuplicates() {
        return $(
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1"))
                ),
                $(1,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread2")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(returnRenderingLogline("thread2", "uuid"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread2"))
                ),
                $(1,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread2")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(returnRenderingLogline("thread2", "uuid"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(3L), valueOf(1L), "thread2"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(getRenderingLogline("uuid")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(logLine().withLogMessage("foo bar"))
                )
        );
    }

    public static Object[] provideLoglinesWithExpectedAmountOfUnnecessary() {
        return $(
                $(1,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid"))
                ),
                $(2,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(2L), valueOf(2L), "thread2")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(returnRenderingLogline("thread2", "uuid"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(getRenderingLogline("uuid"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(getRenderingLogline("uuid")),
                        a(getRenderingLogline("uuid"))
                ),
                $(0,
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread2")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(returnRenderingLogline("thread2", "uuid")),
                        a(getRenderingLogline("uuid")),
                        a(getRenderingLogline("uuid"))
                ),
                $(1,
                        a(getRenderingLogline("uuid")),
                        a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1")),
                        a(returnRenderingLogline("thread1", "uuid")),
                        a(logLine().withLogMessage("foo bar"))
                )
        );
    }

}
