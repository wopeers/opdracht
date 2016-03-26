package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.report.ReportAssert;
import com.debreuck.neirynck.opdract.report.Report;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.debreuck.neirynck.opdracht.logline.model.LoglineBuilder.a;
import static com.debreuck.neirynck.opdracht.logline.model.LoglineBuilder.startRenderingLogline;
import static java.math.BigInteger.valueOf;

public class ReportFromLoglinesGeneratorTest {

    private ReportFromLoglinesGenerator reportFromLoglinesGenerator = new ReportFromLoglinesGenerator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldCreateRendering() {
        StreamParameters(
                a(startRenderingLogline(valueOf(1L), valueOf(1L), "thread1"))
        ).forEach(reportFromLoglinesGenerator);

        Report report = reportFromLoglinesGenerator.generateReport();

        ReportAssert.assertThat(report)
                .hasNumberOfRenderings(1)
                .hasSummaryCount(1);
    }

    private <T> Stream<T> StreamParameters(T... elements) {
        return Arrays.stream(elements);
    }

}
