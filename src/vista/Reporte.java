/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import config.Conector;
import dao.CreacionGraficos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.Empresa;
import modelo.UnidadInterna;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Draxchaos
 */
public class Reporte extends javax.swing.JFrame {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();

    String rutaImagen = "";

    /**
     * Creates new form Reporte
     */
    public Reporte() {
        initComponents();
        // setIconImage(new ImageIcon(getClass().getResource("/imagenes/Logo t.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //colores de los botones
        btnAbrirPdf.setBackground(Color.CYAN);
        btnPdf.setBackground(Color.CYAN);
        btnPrueba.setBackground(Color.CYAN);
        CreacionGraficos cg = new CreacionGraficos();
//        cg.mostrarEmpresa((ChartPanel) jPanel1);
//        jPanel1.set
    }

    //funcion para crear pdf
    public void generar(String nombre) throws FileNotFoundException, DocumentException, IOException, SQLException {
        if (!(txtNombre.getText().isEmpty())) {

            FileOutputStream archivo = new FileOutputStream(nombre + ".pdf");
            Image logo = Image.getInstance("C:\\Users\\Draxchaos\\Downloads\\Portafolio 2021 agosto diciembre\\desarrollo\\MODULO_ESCRITORIO\\CDTEscritorioI1\\build\\classes\\imagenes\\Logo.png");

            try {
                logo.scaleAbsolute(100, 100);//cambia tamaño
                logo.setAbsolutePosition(475, 730);//coloca imagen en la posicion
            } catch (Exception e) {
            }

            Image aca = Image.getInstance(txtNombre.getText() + ".png");
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivo);
            documento.open();
            logo.setAlignment(0);
            documento.add(logo);
            Paragraph titulo = new Paragraph("Reporte de Personal");
            Paragraph parrafo = new Paragraph("Se genero un reporte gráfico indicando la cantidad de empleados que poseen"
                    + " cada unidad interna registrada en cada una de las empresas.");
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(32);
            titulo.setAlignment(1);
            //parrafo.setAlignment(1);
            documento.add(titulo);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Nombre reporte: " + txtNombre.getText()));
            documento.add(new Paragraph(" "));
            documento.add(parrafo);

            documento.add(new Paragraph("A continuación se logra visualizar el gráfico representando la cantidad de empleados por unidad interna: "));
            documento.add(new Paragraph(" "));
            aca.setAlignment(1);
            documento.add(aca);
            // Paragraph seParrafo = new Paragraph("Imagen");

            //consulta sql aca
//            String sql1 = "SELECT  COUNT(*), p.unidadinterna_idunidad, ui.nomuni\n"
//                    + "FROM personal p join unidadinterna ui\n"
//                    + "on(p.unidadinterna_idunidad = ui.idunidad)\n"
//                    + "group by p.unidadinterna_idunidad, ui.nomuni";
//            String sql1 = "select e.nombreempr, ui.nomuni,Count(ui.idunidad), Count(p.rut)\n"
//                    + "from empresa e right  join unidadinterna ui\n"
//                    + "on(e.rutempresa = ui.empresa_rutempresa)\n"
//                    + "right  join personal p\n"
//                    + "on(ui.idunidad = p.unidadinterna_idunidad)\n"
//                    + "group by  e.nombreempr, ui.nomuni";
            String sql1 = "select e.nombreempr, ui.nomuni, Count(p.rut)\n"
                    + "from empresa e right  join unidadinterna ui\n"
                    + "on(e.rutempresa = ui.empresa_rutempresa)\n"
                    + "right  join personal p\n"
                    + "on(ui.idunidad = p.unidadinterna_idunidad)\n"
                    + "group by  e.nombreempr, ui.nomuni\n"
                    + "order by e.nombreempr asc";

            //creo tabla de datos
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            PdfPCell em = new PdfPCell(new Phrase("Empresa"));
            em.setBackgroundColor(BaseColor.CYAN);
            PdfPCell ui = new PdfPCell(new Phrase("Unidad Interna"));
            ui.setBackgroundColor(BaseColor.CYAN);
            PdfPCell cantEmpleados = new PdfPCell(new Phrase("Número de Empleados"));
            cantEmpleados.setBackgroundColor(BaseColor.CYAN);

            tabla.addCell(em);
            tabla.addCell(ui);
            tabla.addCell(cantEmpleados);

            try {
                con = c.getConnection();
                ps = con.prepareStatement(sql1);
                rs = ps.executeQuery();

                while (rs.next()) {

                    tabla.addCell(rs.getString("nombreempr"));
                    // tabla.addCell(rs.getString("nomuni"));
                    tabla.addCell(rs.getString("nomuni"));
                    tabla.addCell(rs.getString("Count(p.rut)"));
                    //documento.add(new Paragraph(unidad));
                }

            } catch (SQLException e) {

                Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, e);
            } finally {

                con.close();
                rs.close();
            }
            documento.add(new Paragraph(Chunk.NEWLINE));
            documento.add(new Paragraph(Chunk.NEWLINE));
            documento.add(new Paragraph("A continuación se puede ver la tabla indicando el número de personal que contempla cada unidad interna de las empresas registradas: "));
            documento.add(new Paragraph(Chunk.NEWLINE));
            documento.add(tabla);
            documento.add(new Paragraph(Chunk.NEWLINE));
            documento.add(new Paragraph(Chunk.NEWLINE));
            //aca creamos la fecha y hora de creación del reporte
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            date.getTime();
            lblHora.setText("Fecha emisión: " + dateFormat.format(date));
            Paragraph fechaHora = new Paragraph("Fecha emisión: " + dateFormat.format(date));
            fechaHora.setAlignment((int) BOTTOM_ALIGNMENT);
            fechaHora.setAlignment((int) LEFT_ALIGNMENT);
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(fechaHora));
            documento.close();
            JOptionPane.showMessageDialog(null, "Archivo PDF creado correctamente", "Información", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos", "Atención", 2);
        }
    }

    //Funcion para abrir el pdf creado segun su nombre
    public void abrir(String nombre) {
        try {
            File path = new File(nombre + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Atención", 2);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPrueba = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        btnPdf = new javax.swing.JButton();
        btnAbrirPdf = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ventana de reporte");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(null);
        setPreferredSize(new java.awt.Dimension(700, 510));
        setSize(new java.awt.Dimension(680, 510));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPrueba.setText("Previsualizar Gráfico");
        btnPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPruebaActionPerformed(evt);
            }
        });
        getContentPane().add(btnPrueba, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel4.setBackground(java.awt.Color.cyan);
        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Nombre de reporte");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 210, -1));

        jPanel1.setBackground(new java.awt.Color(218, 252, 247));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.cyan));
        jPanel1.setLayout(new java.awt.CardLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 580, 200));
        getContentPane().add(lblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 250, 30));

        btnPdf.setText("Generar PDF");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        getContentPane().add(btnPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, -1));

        btnAbrirPdf.setText("Abrir PDF");
        btnAbrirPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirPdfActionPerformed(evt);
            }
        });
        getContentPane().add(btnAbrirPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo t.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 150, 120));

        jLabel2.setBackground(new java.awt.Color(153, 255, 153));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpeg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPruebaActionPerformed

        //String sql = "select rutempresa from CDT1.empresa";
//        String sql1 = "SELECT  COUNT(*), p.unidadinterna_idunidad, ui.nomuni\n"
//                + "FROM personal p join unidadinterna ui\n"
//                + "on(p.unidadinterna_idunidad = ui.idunidad)\n"
//                + "group by p.unidadinterna_idunidad, ui.nomuni";
        String sql1 = "select Count(p.rut), ui.nomuni\n"
                + "from personal p join unidadinterna ui\n"
                + "on(p.unidadinterna_idunidad = ui.idunidad) group by ui.nomuni";
        DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultCategoryDataset datoos = new DefaultCategoryDataset();
        int n1 = 1;

        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();

            while (rs.next()) {

                //  dataset.setValue(rs.getString("rutempresa"));
                //datoos.setValue(rs.getInt("COUNT(*)"), "Unidad Interna", rs.getString("nomuni"));
                dataset.setValue(rs.getString("nomuni"), rs.getInt("Count(p.rut)"));
                n1++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreacionGraficos.class.getName()).log(Level.SEVERE, null, ex);
        }

//         try {
//            con = c.getConnection();
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                //  dataset.setValue(rs.getString("rutempresa"));
//                datoos.setValue(n1, "Empresas activas", rs.getString("RUTEMPRESA"));
//                //dataset.setValue(rs.getString("RUTEMPRESA"), Integer.parseInt(rs.getString("NOMBREEMPR")));
//                n1++;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CreacionGraficos.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //dibujamos el grafico
        //  JFreeChart chart = ChartFactory.createPieChart("Grafico de empresa", dataset, true, true, false);
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Unidades Internas con su cantidad de personal",//nombre grafico
//                "Nombre Unidad",//nombre de las barras o columnas
//                "Número de empleados", //nombre de la numeracion
//                datoos,//datos del grafico      
//                PlotOrientation.VERTICAL,//orientacion
//                true,//leyenda de barras individuales por color
//                true,//herramientras
//                false//url del grafico
//
//        );
        JFreeChart chart = ChartFactory.createPieChart(
                "Unidades Internas con su cantidad de personal",//nombre grafico
                dataset,//datos del grafico      
                true,//leyenda de barras individuales por color
                true,//herramientras
                false//url del grafico
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 200));

        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(panel, BorderLayout.NORTH);

        pack();
        repaint();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        date.getTime();
        lblHora.setText("Fecha emisión: " + dateFormat.format(date));

        String n = txtNombre.getText();

        //tamano para la imagen
//        int ancho = 560;
//        int alto = 380;
        int ancho = 450;
        int alto = 250;
        File f = new File(n + ".png");
        try {
            ChartUtilities.saveChartAsPNG(f, chart, ancho, alto);
        } catch (IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPruebaActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        try {
            generar(txtNombre.getText());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnAbrirPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirPdfActionPerformed
        if (!txtNombre.getText().isEmpty()) {
            abrir(txtNombre.getText());
        } else {
            JOptionPane.showMessageDialog(null, " ** Campo nombre vacio\n ** no se encuentra el archivo con ese nombre ", "Atención", 2);
        }
    }//GEN-LAST:event_btnAbrirPdfActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporte().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirPdf;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnPrueba;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHora;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
