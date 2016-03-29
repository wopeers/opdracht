package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.parse.ILoglineParser;
import com.debreuck.neirynck.opdract.report.Report;
import com.google.common.base.Stopwatch;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static Stopwatch stopwatch = Stopwatch.createStarted();
    private static InputStream inputStream = System.in;
    private static PrintStream printStream = System.out;
    private static boolean printTime = false;

    public static void main(String[] args) throws JAXBException, IOException, ParseException {

        evalCommandLine(args);

        ReportFromLoglinesGenerator reportFromLoglinesGenerator = new ReportFromLoglinesGenerator();
        ILoglineParser loglineParser = new LoglineParser();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines()
                .filter(s -> s.contains("Rendering"))
                .filter(loglineParser::isLogline)
                .map(loglineParser::parseToLogline)
                .forEach(reportFromLoglinesGenerator);

        reader.close();

        Report report = reportFromLoglinesGenerator.generateReport();

        marshallToStream(report, printStream);

        printElapsedTimeIfAsked();
    }

    private static void evalCommandLine(String[] args) throws ParseException, FileNotFoundException {
        final Options options = new Options();
        options.addOption("o", "output", true, "file to print report to (default = stdout)");
        options.addOption("f", "file", true, "logfile to parse (default = stdin)");
        options.addOption("t", "time", false, "print elapsed time");
        options.addOption("h", "help", false, null);

        CommandLineParser parser = new DefaultParser();

        CommandLine cl = parser.parse(options, args);

        if (cl.hasOption("h")) {
            new HelpFormatter().printHelp("java -jar opdracht.jar", "Generate rendering report based on logfile", options, null, true);
            System.exit(0);
        }

        if (cl.hasOption("t"))
            printTime = true;

        evalInputOutputOptions(cl);
    }

    private static void evalInputOutputOptions(CommandLine cmd) throws FileNotFoundException {
        if (cmd.hasOption("f"))
            inputStream = new FileInputStream(cmd.getOptionValue("f"));

        if (cmd.hasOption("o"))
            printStream = new PrintStream(new FileOutputStream(cmd.getOptionValue("o")));
    }

    private static void marshallToStream(Report report, PrintStream outputStream) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, outputStream);
    }

    private static void printElapsedTimeIfAsked() {
        if (printTime)
            System.out.printf("%d Milliseconds elapsed while generate report\n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

}
