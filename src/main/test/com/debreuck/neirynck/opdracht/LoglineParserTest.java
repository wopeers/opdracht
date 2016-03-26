package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.ILoglineParser;
import com.debreuck.neirynck.opdracht.logline.Loglevel;
import com.debreuck.neirynck.opdracht.logline.Logline;
import com.debreuck.neirynck.opdracht.logline.ParseException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static com.debreuck.neirynck.opdracht.logline.LoglineAssert.AssertThat;
import static org.junit.Assert.assertFalse;

@RunWith(JUnitParamsRunner.class)
public class LoglineParserTest {

    ILoglineParser loglineParser = new LoglineParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideLoglineWithValidity")
    public void shouldReturnValidOrInvalidLogline(String line, boolean isValidLine) {
        assertFalse(loglineParser.isLogline(line) && !isValidLine);
    }

    @Test
    public void shouldParseFullLine() throws ParseException {
        String line = "2010-10-06 09:02:10,000 [Thread-5] DEBUG [GeneratingClass]: simpleLogMessage";
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline)
                .hasFormattedTimestamp("2010-10-06 09:02:10,000")
                .hasThread("Thread-5")
                .hasLoglevel(Loglevel.DEBUG)
                .hasGeneratedClass("GeneratingClass")
                .hasLogMessage("simpleLogMessage");
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideValidLoglineWithExpectedTimestamp")
    public void shouldCreateExpectedTimestamp(String line, String expectedTimestamp) throws ParseException {
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline).hasFormattedTimestamp(expectedTimestamp);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideValidLoglineWithExpectedThread")
    public void shouldCreateExpectedThread(String line, String expectedThread) throws ParseException {
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline).hasThread(expectedThread);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideValidLoglineWithExpectedLoglevel")
    public void shouldCreateExpectedMessage(String line, Loglevel expectedLoglevel) throws ParseException {
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline).hasLoglevel(expectedLoglevel);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideValidLoglineWithExpectedGeneratingClass")
    public void shouldCreateExpectedGeneratingClass(String line, String expectedClass) throws ParseException {
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline).hasGeneratedClass(expectedClass);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideValidLoglineWithExpectedMessage")
    public void shouldCreateExpectedMessage(String line, String expectedMessage) throws ParseException {
        Logline logline = loglineParser.parseToLogline(line);
        AssertThat(logline).hasLogMessage(expectedMessage);
    }

    @Test(expected = ParseException.class)
    @Parameters(source = LoglineProvider.class, method = "provideInvalidLoglines")
    public void shouldThrowException(String invalidLogline) throws ParseException {
        Logline logline = loglineParser.parseToLogline(invalidLogline);
    }

}
