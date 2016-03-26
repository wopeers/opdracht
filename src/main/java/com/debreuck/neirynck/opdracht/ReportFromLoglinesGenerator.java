package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.ILoglineConsumer;
import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.report.IReportGenerator;
import com.debreuck.neirynck.opdract.report.Rendering;
import com.debreuck.neirynck.opdract.report.Report;
import com.debreuck.neirynck.opdract.report.Summary;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportFromLoglinesGenerator implements ILoglineConsumer, IReportGenerator {

    private static final String ARGUMENT_LIST_DELIMITER = ",";
    private static Pattern arguments = Pattern.compile("\\[(.*?)\\]");
    private long count = 0;
    private long duplicates = 0;
    private long unnecessary = 0;

    Table<BigInteger, BigInteger, Rendering> documentToRendering = HashBasedTable.create();
    Map<String, Rendering> threadToRendering = new HashMap<>();
    Map<String, Rendering> uuidToRendering = new HashMap<>();
    List<Rendering> renderingBuilders = new ArrayList<>();

    @Override
    public void accept(Logline logline) {
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
        report.getRendering().addAll(renderingBuilders);
        Summary summary = new Summary();
        summary.setCount(BigInteger.valueOf(count));
        summary.setDuplicates(BigInteger.valueOf(duplicates));
        summary.setUnnecessary(BigInteger.valueOf(unnecessary));
        report.setSummary(summary);
        return report;
    }


    private void updateRenderingWithGet(Logline logline) {
        String uuid = getArrayOfArguments(logline)[0].trim();

        if (uuidToRendering.containsKey(uuid)) {
            if (uuidToRendering.get(uuid).getGet().isEmpty())
                unnecessary--;

            uuidToRendering.get(uuid).getGet().add(logline.getFormattedTimeStamp());
        }
    }

    private void updateRenderingWithUuid(Logline logline) {
        String uuid = logline.getLogMessage().substring(logline.getLogMessage().lastIndexOf(" ")).trim();

        Rendering rendering = threadToRendering.get(logline.getThread());
        rendering.setUid(uuid);
        uuidToRendering.put(uuid, rendering);
    }

    private void createOrUpdateRenderingWithStart(Logline logline) {
        String[] arrayOfArguments = getArrayOfArguments(logline);

        BigInteger document = BigInteger.valueOf(Long.parseLong(arrayOfArguments[0].trim()));
        BigInteger page = BigInteger.valueOf(Long.parseLong(arrayOfArguments[1].trim()));

        if (documentToRendering.contains(document, page)) {
            documentToRendering.get(document, page).getStart().add(logline.getFormattedTimeStamp());
            duplicates++;
        } else {
            Rendering rendering = new Rendering();
            rendering.setDocument(document);
            rendering.setPage(page);
            rendering.getStart().add(logline.getFormattedTimeStamp());

            documentToRendering.put(document, page, rendering);
            threadToRendering.put(logline.getThread(), rendering);
            renderingBuilders.add(rendering);
            unnecessary++;
            count++;
        }
    }

    private String[] getArrayOfArguments(Logline logline) {
        Matcher m;
        m = arguments.matcher(logline.getLogMessage());
        m.find();
        return m.group(1).split(ARGUMENT_LIST_DELIMITER);
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
