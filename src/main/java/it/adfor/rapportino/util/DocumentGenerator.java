
package it.adfor.rapportino.util;

import com.itextpdf.text.DocumentException;
import it.adfor.rapportino.model.Activity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public interface DocumentGenerator {
    final String path = "src/main/webapp/res/generated/";
    public File getDocumentByStaff(Iterable<Activity> col, String dateRange)
            throws DocumentException, FileNotFoundException, IOException;
    public File getDocumentByProject(Collection<Activity> activities, String dateRange)
            throws DocumentException, FileNotFoundException, IOException;
}
