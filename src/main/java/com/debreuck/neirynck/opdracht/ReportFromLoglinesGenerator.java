package com.debreuck.neirynck.opdracht;

import com.debreuck.neirynck.opdracht.logline.ILoglineConsumer;
import com.debreuck.neirynck.opdracht.logline.model.Logline;
import com.debreuck.neirynck.opdracht.report.IReportGenerator;
import com.debreuck.neirynck.opdract.report.Report;

public class ReportFromLoglinesGenerator implements ILoglineConsumer, IReportGenerator {

    @Override
    public void accept(Logline logline) {
        throw new UnsupportedOperationException("Operation not yet implemented.");
    }

    @Override
    public Report generateReport() {
        throw new UnsupportedOperationException("Operation not yet implemented.");
    }
}
