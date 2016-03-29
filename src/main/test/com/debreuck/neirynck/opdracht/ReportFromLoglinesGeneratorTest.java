package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdract.report.Report;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.debreuck.neirynck.opdracht.logline.model.LoglineBuilder.*;
import static com.debreuck.neirynck.opdracht.report.RenderingAssert.assertThatRendering;
import static com.debreuck.neirynck.opdracht.report.ReportAssert.assertThatReport;
import static java.math.BigInteger.valueOf;

@RunWith(JUnitParamsRunner.class)
public class ReportFromLoglinesGeneratorTest {

    private ReportFromLoglinesGenerator reportFromLoglinesGenerator = new ReportFromLoglinesGenerator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldHaveCreatedRendering() {
        StreamParameterList(
                a(startRenderingLogline(valueOf(456L), valueOf(24L), "Thread-1")),
                a(returnRenderingLogline("Thread-1", "uuid")),
                a(getRenderingLogline("uuid"))
        ).forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatReport(report)
                .hasNumberOfRenderings(1)
                .hasSummaryCount(1)
                .hasSummaryUnnecessary(0)
                .hasSummaryDuplicates(0);

        assertThatRendering(report.getRendering().get(0))
                .hasDocument(456L)
                .hasPage(24L)
                .hasUuid("uuid")
                .hasNumberOfGets(1)
                .hasNumberOfStarts(1);
    }

    @Test
    public void shouldHaveCreatedRenderingWithMultipleStarts() {
        StreamParameterList(
            a(startRenderingLogline(valueOf(456L), valueOf(24L), "Thread-1")),
            a(startRenderingLogline(valueOf(456L), valueOf(24L), "Thread-2")),
            a(startRenderingLogline(valueOf(456L), valueOf(24L), "Thread-3")),
            a(returnRenderingLogline("Thread-1", "uuid")),
            a(returnRenderingLogline("Thread-2", "uuid"))
        ).forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatRendering(report.getRendering().get(0))
            .hasNumberOfStarts(2);
    }

    @Test
    public void shouldHaveCreatedRenderingWithMultipleGets() {
        StreamParameterList(
                a(startRenderingLogline(valueOf(456L), valueOf(24L), "Thread-1")),
                a(returnRenderingLogline("Thread-1", "uuid")),
                a(getRenderingLogline("uuid")),
                a(getRenderingLogline("uuid"))
        ).forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatRendering(report.getRendering().get(0))
                .hasNumberOfGets(2);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideLoglinesWithExpectedAmountOfRenderingsCreated")
    public void shouldCreateExpectedAmountOfRenderings(int expectedAmount, Logline... loglines) {
        StreamParameterList(loglines)
                .forEach(reportFromLoglinesGenerator);


        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatReport(report)
                .hasNumberOfRenderings(expectedAmount)
                .hasSummaryCount(expectedAmount);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideLoglinesWithExpectedAmountOfDuplicates")
    public void shouldCreateExpectedAmountOfDuplicates(int expectedAmount, Logline... loglines) {
        StreamParameterList(loglines)
                .forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatReport(report)
                .hasSummaryDuplicates(expectedAmount);
    }

    @Test
    @Parameters(source = LoglineProvider.class, method = "provideLoglinesWithExpectedAmountOfUnnecessary")
    public void shouldCreateExpectedAmountOfUnnecessary(int expectedAmount, Logline... loglines) {
        StreamParameterList(loglines)
                .forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        assertThatReport(report)
                .hasSummaryUnnecessary(expectedAmount);
    }

    private <T> Stream<T> StreamParameterList(T... elements) {
        return Arrays.stream(elements);
    }

}
