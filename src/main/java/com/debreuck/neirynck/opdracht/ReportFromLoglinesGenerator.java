package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.ILoglineConsumer;
import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.report.IReportGenerator;
import com.debreuck.neirynck.opdract.report.Rendering;
import com.debreuck.neirynck.opdract.report.Report;
import com.debreuck.neirynck.opdract.report.Summary;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigInteger.*;

public class ReportFromLoglinesGenerator implements ILoglineConsumer, IReportGenerator {

    private static final String ARGUMENT_LIST_DELIMITER = ",";
    private static Pattern arguments = Pattern.compile("\\[(.*?)\\]");
    private long count = 0;
    private long duplicates = 0;

    Map<String, Rendering> threadToRendering = new HashMap<>();
    Map<String, Rendering> uuidToRendering = new HashMap<>();
    List<Rendering> orderedRenderings = new ArrayList<>();

    @Override
    public void accept(Logline logline) {
        Preconditions.checkNotNull(logline);

        if (isStartRendering(logline))
            createOrUpdateRenderingWithStart(logline);

        else if (isReturnRendering(logline) && threadToRendering.containsKey(logline.getThread()))
            updateRenderingWithUuid(logline);

        else if (isGetRendering(logline))
            updateRenderingWithGet(logline);

    }

    @Override
    public Report generateReport() {
        Report report = new Report();
        report.getRendering().addAll(orderedRenderings);
        Summary summary = new Summary();
        summary.setCount(valueOf(count));
        summary.setDuplicates(valueOf(duplicates));

        int unnecessary = orderedRenderings.stream()
                .mapToInt(rendering -> rendering.getStart().size() - rendering.getGet().size())
                .map(i -> i < 0 ? 0 : i)
                .sum();

        summary.setUnnecessary(valueOf(unnecessary));
        report.setSummary(summary);
        return report;
    }


    private void updateRenderingWithGet(Logline logline) {
        String uuid = getArrayOfArguments(logline)[0];

        if (uuidToRendering.containsKey(uuid))
            uuidToRendering.get(uuid).getGet().add(logline.getFormattedTimeStamp());
    }

    private void updateRenderingWithUuid(Logline logline) {
        String uuid = logline.getLogMessage().substring(logline.getLogMessage().lastIndexOf(" ")).trim();

        Rendering rendering = threadToRendering.get(logline.getThread());
        if (uuidToRendering.containsKey(uuid)) {
            uuidToRendering.get(uuid).getStart().addAll(rendering.getStart());
            duplicates++;
        } else {
            rendering.setUid(uuid);
            uuidToRendering.put(uuid, rendering);
            orderedRenderings.add(rendering);
            count++;
        }
        threadToRendering.remove(logline.getThread());
    }

    private void createOrUpdateRenderingWithStart(Logline logline) {
        String[] arrayOfArguments = getArrayOfArguments(logline);

        BigInteger document = valueOf(Long.parseLong(arrayOfArguments[0]));
        BigInteger page = valueOf(Long.parseLong(arrayOfArguments[1]));

        Rendering rendering = new Rendering();
        rendering.setDocument(document);
        rendering.setPage(page);
        rendering.getStart().add(logline.getFormattedTimeStamp());

        threadToRendering.put(logline.getThread(), rendering);
    }

    private String[] getArrayOfArguments(Logline logline) {
        Matcher m;
        m = arguments.matcher(logline.getLogMessage());
        m.find();
        return Iterables.toArray(
                Splitter.on(ARGUMENT_LIST_DELIMITER)
                        .trimResults()
                        .omitEmptyStrings()
                        .split(m.group(1)), String.class);
    }

    private static boolean isGetRendering(Logline ll) {
        return logMessagecontains(ll, "Executing request getRendering");
    }

    private static boolean isReturnRendering(Logline ll) {
        return logMessagecontains(ll, "Service startRendering returned");
    }

    private static boolean isStartRendering(Logline ll) {
        return logMessagecontains(ll, "Executing request startRendering");
    }

    private static boolean logMessagecontains(Logline ll, String charSequence) {
        return ll.getLogMessage() != null && ll.getLogMessage().contains(charSequence);
    }


}
