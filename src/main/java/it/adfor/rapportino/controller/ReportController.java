
package it.adfor.rapportino.controller;

import it.adfor.rapportino.repository.ActivityRepository;
import it.adfor.rapportino.util.PDFGenerator;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    @RequestMapping(value="{id}", method=RequestMethod.GET,produces = "application/pdf")
    public void getReport(@PathVariable("id") Integer id, 
    @RequestParam("start") String s,
    @RequestParam("end") String e,
    HttpServletResponse response){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date start = formatter.parse(s);
            Date end = formatter.parse(e);
            PDFGenerator gen =  new PDFGenerator();
            InputStream is = new FileInputStream(
                    gen.getDocument(
                            activityRepository.findByDateBetweenAndEmplId(start, end, id)));
            response.setContentType("application/pdf");
            OutputStream os = response.getOutputStream();
            int n = 0;
            byte[] byteBuffer =  new byte[16384];
            while((n = is.read(byteBuffer)) > -1) {
                os.write(byteBuffer, 0, n);   // Don't allow any extra bytes to creep in, final write
            }
            response.flushBuffer();
        } catch (Exception ex){
        
        }
        
    }    
}
