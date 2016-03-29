package com.debreuck.neirynck.opdracht.report;

import com.debreuck.neirynck.opdract.report.Rendering;
import org.assertj.core.api.AbstractAssert;

import static org.junit.Assert.assertNotNull;

public class RenderingAssert extends AbstractAssert<RenderingAssert, Rendering> {

    public static RenderingAssert assertThatRendering(Rendering actual) { return new RenderingAssert(actual); }

    private RenderingAssert(Rendering actual) { super(actual, RenderingAssert.class); }

    public RenderingAssert hasDocument(long document) {
        isNotNull();
        assertNotNull(actual.getDocument());

        long actualDoc = actual.getDocument().longValue();
        if (actualDoc != document)
            failWithMessage("Expected document to be <%s> but was <%s>", document, actualDoc);

        return this;
    }

    public RenderingAssert hasPage(long page) {
        isNotNull();
        assertNotNull(actual.getPage());

        long actualPage = actual.getPage().longValue();
        if (actualPage != page)
            failWithMessage("Expected page to be <%s> but was <%s>", page, actualPage);

        return this;
    }

    public RenderingAssert hasUuid(String uuid) {
        isNotNull();

        if (!uuid.equals(actual.getUid()))
            failWithMessage("Expected uuid to be <%s> but was <%s>", uuid, actual.getUid());

        return this;
    }

    public RenderingAssert hasNumberOfStarts(int count) {
        isNotNull();

        int actualCount = actual.getStart().size();
        if (count != actualCount)
            failWithMessage("Expected <%s> number of starts but was <%s>", count, actualCount);

        return this;
    }

    public RenderingAssert hasNumberOfGets(int count) {
        isNotNull();

        int actualCount = actual.getGet().size();
        if (count != actualCount)
            failWithMessage("Expected <%s> number of gets but was <%s>", count, actualCount);

        return this;
    }

}
