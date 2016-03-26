package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.Loglevel;

import static junitparams.JUnitParamsRunner.$;

public class LoglineProvider {

    public static Object[] provideLoglineWithValidity(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", true),
                $("2010-10-06 09:02:10,000[ Thread-5 ]DEBUG[ GeneratingClass ]:simpleLogMessage", true),
                $("\t2010-10-06 09:02:10,000 \t[Thread-5]  DEBUG \t[GeneratingClass]: \tsimpleLogMessage", true),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", true),

                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: \t", false),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass] : simpleLogMessage", false),
                $("2010-10-06 09:02:10,000 [Thread-5] nonsense [GeneratingClass] : simpleLogMessage", false),
                $("2010iasdfasdfalsd;f,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", false),
                $("2010-10-06 09:02:10,000 [] DEBUG [GeneratingClass]: simpleLogMessage", false),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG []: simpleLogMessage", false)
        );
    }

    public static Object[] provideValidLoglineWithExpectedTimestamp(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", "2010-10-06 09:02:10,000"),
                $("\t   2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", "2010-10-06 09:02:10,000")
        );
    }

    public static Object[] provideValidLoglineWithExpectedThread(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", "Thread-5"),
                $("2010-10-06 09:02:10,000 [ Thread-5 ] DEBUG [GeneratingClass]: simpleLogMessage", "Thread-5")
        );
    }

    public static Object[] provideValidLoglineWithExpectedLoglevel(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", Loglevel.DEBUG),
                $("2010-10-06 09:02:10,000 [Thread-5]INFO[GeneratingClass]: simpleLogMessage", Loglevel.INFO),
                $("2010-10-06 09:02:10,000 [Thread-5]  WARN [GeneratingClass]: simpleLogMessage", Loglevel.WARN),
                $("2010-10-06 09:02:10,000 [Thread-5] ERROR  [GeneratingClass]: simpleLogMessage", Loglevel.ERROR)
        );
    }

    public static Object[] provideValidLoglineWithExpectedGeneratingClass(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", "GeneratingClass"),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [ GeneratingClass ]: simpleLogMessage", "GeneratingClass")
        );
    }

    public static Object[] provideValidLoglineWithExpectedMessage(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage", "simpleLogMessage"),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: weirdCharacters:\\!@#$%^&*()=_+`]';./?", "weirdCharacters:\\!@#$%^&*()=_+`]';./?"),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]:  \t   whitespace     ", "whitespace"),
                $("2010-10-06 09:02:10,506 [Thread-5] DEBUG [GeneratingClass]: a message with spaces", "a message with spaces")
        );
    }

    public static Object[] provideInvalidLoglines(){
        return $(
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: \t"),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass] : simpleLogMessage"),
                $("2010-10-06 09:02:10,000 [Thread-5] nonsense [GeneratingClass] : simpleLogMessage"),
                $("2010iasdfasdfalsd;f,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage"),
                $("2010-10-06 09:02:10,000 [] DEBUG [GeneratingClass]: simpleLogMessage"),
                $("2010-10-06 09:02:10,000 [Thread-5] DEBUG []: simpleLogMessage")
        );
    }
}
