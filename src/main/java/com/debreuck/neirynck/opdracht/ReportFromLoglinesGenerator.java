package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.logline.parse.ILoglineParser;
import com.debreuck.neirynck.opdracht.logline.parse.ParseException;
import com.debreuck.neirynck.opdracht.report.IReportGenerator;
import com.debreuck.neirynck.opdract.report.Report;

public class ReportFromLoglinesGenerator implements ILoglineParser, IReportGenerator {

    @Override
    public boolean isLogline(String line) {
        throw new UnsupportedOperationException("Operation not yet implemented.");
    }

    @Override
    public Logline parseToLogline(String line) throws ParseException {
        throw new UnsupportedOperationException("Operation not yet implemented.");
    }

    @Override
    public Report generateReport() {
        throw new UnsupportedOperationException("Operation not yet implemented.");
    }
}
