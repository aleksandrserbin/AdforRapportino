package it.adfor.rapportino.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import it.adfor.rapportino.model.Activity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SheetGenerator implements DocumentGenerator {

    public File getDocumentByStaff(Iterable<Activity> col, String dateRange)
            throws DocumentException, FileNotFoundException, IOException {
        Activity first;
        if (col.iterator().hasNext()) {
            first = col.iterator().next();
        } else {
            throw new IOException("No activities found");
        }
        String name = first.getEmpl().getName() + " " + first.getEmpl().getSname();
        File f = new File(path + name + ".xls");
        FileOutputStream out = new FileOutputStream(f);
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        bf.write(name);
        bf.write("\n" + dateRange + "\n");
        bf.write("Date\tProject\tType\tHours\n");
        Map<String, Integer[]> activityMap = new HashMap<String, Integer[]>();
        for (Activity a : col) {
            if (!activityMap.containsKey(a.getProj().getName())) {
                Integer[] hours = new Integer[2]; // 0 - P, 1 - N
                if (a.getTypeId() == 5) {
                    hours[0] = 0;
                    hours[1] = a.getHours();
                } else {
                    hours[0] = a.getHours();
                    hours[1] = 0;
                }
                activityMap.put(a.getProj().getName(), hours);
            } else {
                Integer[] hours = activityMap.get(a.getProj().getName());
                if (a.getTypeId() == 5) {
                    hours[1] += a.getHours();
                } else {
                    hours[0] += a.getHours();
                }
                activityMap.put(a.getProj().getName(), hours);
            }
            bf.write(a.getDate().toString() + "\t");
            bf.write(a.getProj().getName() + "\t");
            bf.write((a.getTypeId() == 6 ? "P" : "N") + "\t");
            bf.write(a.getHours().toString() + "\n");
        }
        bf.write("\tSummary\t\n");
        bf.write("Employee\tHours\tN Hours\n");
        for (String key : activityMap.keySet()) {
            bf.write(key + "\t");
            bf.write(activityMap.get(key)[0].toString() + "\t");
            bf.write(activityMap.get(key)[1].toString() + "\n");
        }
        bf.close();
        return f;
    }
    //bad code reuse however deadline is approaching
    @Override
    public File getDocumentByProject(Collection<Activity> activities, String dateRange)
            throws DocumentException, FileNotFoundException, IOException {
        Activity first;
        if (activities.iterator().hasNext()) {
            first = activities.iterator().next();
        } else {
            throw new DocumentException("No activities found");
        }
        File f = new File(path + first.getProj().getName() + ".pdf");
        FileOutputStream out = new FileOutputStream(f);
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        bf.write(first.getProj().getName());
        bf.write("\n" + dateRange + "\n");
        bf.write("Date\tProject\tType\tHours\n");
        Map<String, Integer[]> map = new HashMap<String, Integer[]>();
        for (Activity a : activities) {
            String empl = a.getEmpl().getSname();
            Integer[] hours = new Integer[2];
            if (!map.containsKey(empl)) {
                if (a.getTypeId() == 5) {
                    hours[0] = 0;
                    hours[1] = a.getHours();
                } else {
                    hours[0] = a.getHours();
                    hours[1] = 0;
                }
                map.put(empl, hours);
            } else {
                hours = map.get(empl);
                if (a.getTypeId() == 5) {
                    hours[1] += a.getHours();
                } else {
                    hours[0] += a.getHours();
                }
                map.put(empl, hours);
            }
        }
        for (String key : map.keySet()) {
            bf.write(key+"\t");
            bf.write(map.get(key)[0].toString()+"\t");
            bf.write(map.get(key)[1].toString()+"\n");
        }
        bf.close();
        return f;
    }
}
