package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.repository.ActivityRepository;
import it.adfor.rapportino.util.DocumentGenerator;
import it.adfor.rapportino.util.PDFGenerator;
import it.adfor.rapportino.util.SheetGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/reports")
public class ReportController {


    @Autowired
    ActivityRepository activityRepository;
    
    DocumentGenerator gen;
    
    @RequestMapping(value = "staff/{id}/{type}", method = RequestMethod.GET, produces = "application/pdf")
    public void getReport(@PathVariable("id") Integer id,
            @RequestParam("start") String s,
            @RequestParam("end") String e,
            @PathVariable("type") String type,
            HttpServletResponse response) throws IOException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (type.equals("pdf")) gen = new PDFGenerator();
        else gen = new SheetGenerator();
        try {
            Date start = formatter.parse(s);
            Date end = formatter.parse(e);
            String dateHeader = start+" - "+end;
            Collection<Activity> acts = activityRepository
                    .findByDateBetweenAndEmplIdAndSubmittedTrue(start, end, id);
            InputStream is = new FileInputStream(
                    gen.getDocumentByStaff(acts,dateHeader));
            if (type.equals("pdf")) response.setContentType("application/pdf");
            else response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            OutputStream os = response.getOutputStream();
            int n = 0;
            byte[] byteBuffer = new byte[16384];
            while ((n = is.read(byteBuffer)) > -1) {
                os.write(byteBuffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }
            response.flushBuffer();
        } catch (Exception ex) {
        }
    }

    @RequestMapping(value = "staff/{id}/{m}/{type}", method = RequestMethod.GET, produces = "application/xls")
    public void getStaffReportByMonth(@PathVariable("id") Integer id,
            @PathVariable("m") Integer month,
            @PathVariable("type") String type,
            HttpServletResponse response) throws IOException {
        String dateHeader = new DateFormatSymbols().getMonths()[month];
        if (type.equals("pdf")) gen = new PDFGenerator();
        else gen = new SheetGenerator();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginDate = new Date(cal.getTime().getTime());
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = new Date(cal.getTime().getTime());
        Collection<Activity> list = activityRepository
                .findByDateBetweenAndEmplIdAndSubmittedTrue(beginDate, endDate, id);
        try {
            InputStream is = new FileInputStream(
                    gen.getDocumentByStaff(list, dateHeader));
            if (type.equals("pdf")) response.setContentType("application/pdf");
            else response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            OutputStream os = response.getOutputStream();
            int n = 0;
            byte[] byteBuffer = new byte[16384];
            while ((n = is.read(byteBuffer)) > -1) {
                os.write(byteBuffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }
            response.flushBuffer();
        } catch (Exception ex) {
        }
    }

    @RequestMapping(value = "projects/{id}/{type}")
    public void getProjectReport(@PathVariable("id") Integer id,
            @RequestParam(value = "start", required = false) String b,
            @RequestParam(value = "end", required = false) String e,
            @PathVariable("type") String type,
            HttpServletResponse response) throws IOException {
        if (type.equals("pdf")) gen = new PDFGenerator();
        else gen = new SheetGenerator();
        if (b != null && e != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date begin = formatter.parse(b);
                Date end = formatter.parse(e);
                Collection<Activity> col = activityRepository
                        .findByDateBetweenAndProjId(begin, end, id);
                InputStream is = new FileInputStream(
                        gen.getDocumentByProject(col,begin+"-"+end));
                if (type.equals("pdf")) response.setContentType("application/pdf");
                else response.setContentType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                OutputStream os = response.getOutputStream();
                int n = 0;
                byte[] byteBuffer = new byte[16384];
                while ((n = is.read(byteBuffer)) > -1) {
                    os.write(byteBuffer, 0, n);   // Don't allow any extra bytes to creep in, final write
                }
                response.flushBuffer();
            } catch (Exception ex) {
            }
        } else {
            try {
                Collection<Activity> col = activityRepository
                        .findByProjId(id);
                InputStream is = new FileInputStream(
                        gen.getDocumentByProject(col,"Whole period"));
                response.setContentType("application/pdf");
                OutputStream os = response.getOutputStream();
                int n = 0;
                byte[] byteBuffer = new byte[16384];
                while ((n = is.read(byteBuffer)) > -1) {
                    os.write(byteBuffer, 0, n);   // Don't allow any extra bytes to creep in, final write
                }
                response.flushBuffer();
            } catch (Exception ex) {
            }
        }
    }

//    @RequestMapping(value = "general", method = RequestMethod.GET)
//    public void getGeneralReport(
//            @RequestParam(value = "eid", required = false) Integer eid,
//            @RequestParam(value = "pid", required = false) Integer pid,
//            @RequestParam(value = "begin", required = false) String begin,
//            @RequestParam(value = "end", required = false) String end,
//            @RequestParam(value = "tid", required = false) Integer tid,
//            HttpServletResponse response) throws Exception {
//                PDFGenerator gen = new PDFGenerator();
//                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                Collection<Activity> col;
//                if (eid==null && pid!=null) {
//                    if (tid!=null){
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndTypeIdAndProjId(
//                                            b, e, tid,pid);
//                        } else col=activityRepository
//                                .findByTypeIdAndProjId(tid, pid);
//                    } else{
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndProjId(b, e,pid);
//                        } else col=activityRepository
//                                .findByProjId(pid);
//                    }
//                } else if (eid!=null && pid==null){
//                    if (tid!=null){
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndTypeIdAndEmplId(
//                                            b, e, tid,eid);
//                        } else col=activityRepository
//                                .findByTypeIdAndEmplId(tid,eid);
//                    } else{
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndEmplId(b, e,eid);
//                        } else col=activityRepository
//                                .findByEmplId(eid);
//                    }
//                } else if (eid!=null && pid!=null) {
//                    if (tid!=null){
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndTypeIdAndEmplIdAndProjId(
//                                            b, e, tid,eid,pid);
//                        } else col=activityRepository
//                                .findByTypeIdAndEmplIdAndPojId(tid,eid,pid);
//                    } else{
//                        if (begin!=null && end!=null){
//                            Date b = formatter.parse(begin);
//                            Date e = formatter.parse(end);
//                            col = activityRepository
//                                    .findByDateBetweenAndEmplIdAndProjId(b, e,eid,pid);
//                        } else col=activityRepository
//                                .findByEmplIdAndProjId(eid,pid);
//                    }
//                } else throw new Exception("Incorrect query");
//
//                try {
//                    InputStream is = new FileInputStream(gen.getDocumentByProject(col));
//                    response = write(response, is);
//                    response.flushBuffer();
//                } catch (Exception ex) {
//                    
//                    
//                }
//
//    }

    private HttpServletResponse write(HttpServletResponse r, InputStream i) throws IOException {
        OutputStream o = r.getOutputStream();
        r.setContentType("application/pdf");
        int n = 0;
        byte[] byteBuffer = new byte[16384];
        while ((n = i.read(byteBuffer)) > -1) {
            o.write(byteBuffer, 0, n);
        }
        return r;
    }
}
