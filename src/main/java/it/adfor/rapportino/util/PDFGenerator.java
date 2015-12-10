
package it.adfor.rapportino.util;

import com.google.common.collect.HashBiMap;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Staff;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PDFGenerator implements DocumentGenerator{
    public File getDocumentByStaff(Iterable<Activity> col, String dateRange) 
            throws IOException,DocumentException, FileNotFoundException{
        System.out.println("pdf controller");
        Activity first;
        if (col.iterator().hasNext()) first = col.iterator().next();
        else throw new DocumentException("No activities found");
        String name = first.getEmpl().getName()+ " "+first.getEmpl().getSname();
        File f =  new File(path+name+".pdf");
        Document doc = new Document();
        PdfWriter out = PdfWriter.getInstance(doc,
                new FileOutputStream(f));
        doc.open();
        doc.add(new Paragraph("Rapportino report for "+name+
                "\n"+dateRange+"\n\n"));
        PdfPTable table =  new PdfPTable(4);
        PdfPCell cell;
        table.setWidths(new float[]{2,6,1,1});
        table.addCell("Date");
        table.addCell("Project");
        table.addCell("Type");
        table.addCell("Hours");
        Map<String, Integer[]> activityMap =  new HashMap<String,Integer[]>();
        for (Activity a : col){
            if (!activityMap.containsKey(a.getProj().getName())){
                Integer[] hours = new Integer[2]; // 0 - P, 1 - N
                if (a.getTypeId()==5) {
                    hours[0] = 0;
                    hours[1] = a.getHours();
                } else {
                    hours[0]  =a.getHours();
                    hours[1] = 0;
                }
                activityMap.put(a.getProj().getName(), hours);
            } else {
                Integer[] hours = activityMap.get(a.getProj().getName());
                if (a.getTypeId()==5) hours[1]+=a.getHours();
                else hours[0]+=a.getHours();
                activityMap.put(a.getProj().getName(), hours);
            }
            table.addCell(a.getDate().toString());
            table.addCell(a.getProj().getName());
            table.addCell(a.getTypeId()==6?"P":"N");
            table.addCell(a.getHours().toString());
        }
        doc.add(table);
        doc.add(new Paragraph("Summary:"+name+
                "\n"+dateRange+"\n\n"));
        table = new PdfPTable(3);
        table.setWidths(new float[]{6,2,2});
        table.addCell("Project");
        table.addCell("Payable hours");
        table.addCell("Not payable hours");
        for (String key : activityMap.keySet()){
            table.addCell(key);
            table.addCell(activityMap.get(key)[0].toString());
            table.addCell(activityMap.get(key)[1].toString());
        }
        doc.add(table);
        doc.close();
        return f;
    }
    
    public File getDocumentByProject(Collection<Activity> activities, String dateRange)
            throws DocumentException, FileNotFoundException, IOException{
        Activity first;
        if (activities.iterator().hasNext()) first = activities.iterator().next();
        else throw new DocumentException("No activities found");
        File f =  new File(path+first.getProj().getName()+".pdf");
        Document doc = new Document();
        PdfWriter out = PdfWriter.getInstance(doc,
                new FileOutputStream(f));
        doc.open();
        doc.add(new Paragraph("Rapportino report for "+first.getProj().getName()+
                "\n"+dateRange+"\n\n"));
        PdfPTable table =  new PdfPTable(3);
        table.setWidths(new float[]{6,2,2});
        table.addCell("Employee");
        table.addCell("Payable hours");
        table.addCell("Not Payable hours");
        Map<String,Integer[]> map = new HashMap<String,Integer[]>();
        for (Activity a: activities){
            String empl = a.getEmpl().getSname();
            Integer[] hours = new Integer[2];
            if (!map.containsKey(empl)){
                if (a.getTypeId()==5){
                    hours[0] =0;
                    hours[1]=a.getHours();
                } else {
                    hours[0] =a.getHours();
                    hours[1]=0;
                }
                map.put(empl, hours);
            } else {
                hours = map.get(empl);
                if (a.getTypeId()==5) hours[1]+=a.getHours();
                else hours[0]+=a.getHours();
                map.put(empl, hours);
            }
        }
        
        for (String key : map.keySet()){
            table.addCell(key);
            table.addCell(map.get(key)[0].toString());
            table.addCell(map.get(key)[1].toString());
        }
        doc.add(table);
        doc.close();
        return f;
    }
}
