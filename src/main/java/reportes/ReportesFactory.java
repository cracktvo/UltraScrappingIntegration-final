package reportes;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import vista.rows.ClienteRow;
/**
 *
 * @author oscar
 */
public class ReportesFactory {    
    
    /**
     *path
     */
    public static final String path = Paths.get("").toAbsolutePath().toString();    
    
    public static void crearInventarioReporte(Collection<ClienteRow> clientes, String outPath) {
        ReporteDataSource datasource = new ReporteDataSource();
        clientes.stream().forEach((entity) -> {
            datasource.add(entity);
            });
        crearReporte(datasource, outPath, "Reporte","Clientes");
    }
    
    private static void crearReporte( JRDataSource dataSource, String outPath, String nombre, String titulo){ 
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy.HHmmss");
            JasperPrint print = JasperFillManager.fillReport(path + "//src//reportes//"+nombre+".jasper", null, dataSource);
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outPath+"/"+titulo.trim()+ dateFormat.format(new Date()) +".xlsx"));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
            configuration.setIgnoreCellBorder(Boolean.FALSE);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(ReportesFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
