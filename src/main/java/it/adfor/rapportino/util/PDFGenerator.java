
package it.adfor.rapportino.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.adfor.rapportino.model.Activity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class PDFGenerator {
    public File getDocument(Iterable<Activity> col) throws DocumentException, FileNotFoundException{
        Activity first = col.iterator().next();
        String name = first.getEmpl().getName()+ " "+first.getEmpl().getSname();
        File f =  new File(name+".pdf");
        Document doc = new Document();
        PdfWriter out = PdfWriter.getInstance(doc,
                new FileOutputStream(f));
        doc.open();
        doc.addTitle("Rapportino report for "+name+
                "\n"+first.getDate());
        doc.add(new Paragraph("Rapportino report for "+name+
                "\n"+first.getDate()));
        PdfPTable table =  new PdfPTable(2);
        PdfPCell cell;
        Map<String, Integer> activityMap =  new HashMap<String,Integer>();
        for (Activity a : col){
            if (!activityMap.containsKey(a.getProj().getName())){
                activityMap.put(a.getProj().getName(), a.getHours());
            } else {
                activityMap.put(a.getProj().getName(),
                        (Integer) activityMap.get(a.getProj().getName())+a.getHours());
            }
            table.addCell(a.getProj().getName());
            table.addCell(a.getHours().toString());
        }
        doc.add(table);
        doc.add(new Paragraph("Summary:"+name+
                "\n"+first.getDate()));
        table = new PdfPTable(2);
        for (String key : activityMap.keySet()){
            table.addCell(key);
            table.addCell(activityMap.get(key).toString());
        }
        doc.add(table);
        doc.close();
        return f;
    }
}
