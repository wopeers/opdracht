package com.debreuck.neirynck.opdracht.report;

import com.debreuck.neirynck.opdract.report.Report;
import org.assertj.core.api.AbstractAssert;

import static org.junit.Assert.assertNotNull;

public class ReportAssert extends AbstractAssert<ReportAssert, Report>{

    public static ReportAssert assertThat(Report actual) { return new ReportAssert(actual); }

    private ReportAssert(Report actual) { super(actual, ReportAssert.class); }

    public ReportAssert hasNumberOfRenderings(int count){
        isNotNull();

        int actualNbrOfRenderings = actual.getRendering().size();
        if ( actualNbrOfRenderings != count )
            failWithMessage("Expected <%s> renderings but was <%s>", count, actualNbrOfRenderings);

        return this;
    }

    public ReportAssert hasSummaryCount(long count){
        isNotNull();
        assertNotNull(actual.getSummary());
        assertNotNull(actual.getSummary().getCount());

        long actualSummaryCount = actual.getSummary().getCount().longValue();
        if ( actualSummaryCount != count )
            failWithMessage("Expected summary count to be <%s> but was <%s>", count, actualSummaryCount);

        return this;
    }

    public ReportAssert hasSummaryDuplicates(long duplicates){
        isNotNull();
        assertNotNull(actual.getSummary());
        assertNotNull(actual.getSummary().getDuplicates());

        long actualSummaryDuplicates = actual.getSummary().getDuplicates().longValue();
        if ( actualSummaryDuplicates != duplicates )
            failWithMessage("Expected summary duplicates to be <%s> but was <%s>", duplicates, actualSummaryDuplicates);

        return this;
    }

    public ReportAssert hasSummaryUnnecessary(long unnecessary){
        isNotNull();
        assertNotNull(actual.getSummary());
        assertNotNull(actual.getSummary().getUnnecessary());

        long actualSummaryUnnecessary = actual.getSummary().getUnnecessary().longValue();
        if ( actualSummaryUnnecessary != unnecessary )
            failWithMessage("Expected summary unnecessary to be <%s> but was <%s>", unnecessary, actualSummaryUnnecessary);

        return this;
    }
}
