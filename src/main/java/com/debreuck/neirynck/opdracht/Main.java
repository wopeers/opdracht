package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.logline.parse.ILoglineParser;
import com.debreuck.neirynck.opdract.report.Report;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        ReportFromLoglinesGenerator reportFromLoglinesGenerator = new ReportFromLoglinesGenerator();
        ILoglineParser loglineParser = new LoglineParser();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        reader.lines()
                .filter(s -> s.contains("Rendering"))
                .filter(loglineParser::isLogline)
                .map(loglineParser::parseToLogline)
                .forEach(reportFromLoglinesGenerator);

        reader.close();

        Report report = reportFromLoglinesGenerator.generateReport();

        marshallToStream(report, System.out);

    }


    private static void marshallToStream(Report report, PrintStream outputStream) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, outputStream);
    }

}