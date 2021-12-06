/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Empresa;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Draxchaos
 */
public class CreacionGraficos {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();

    private static CreacionGraficos instancia;

    public static CreacionGraficos getInstancia() {
        if (instancia == null) {
            instancia = new CreacionGraficos();
        }
        return instancia;
    }

    public void mostrarEmpresa(ChartPanel panel) {

        String sql = "select rutempresa from CDT1.empresa";
        DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultCategoryDataset datoos = new DefaultCategoryDataset();
        int n1 = 1;

        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                //  dataset.setValue(rs.getString("rutempresa"));
                datoos.setValue(n1, "Empresas activas", rs.getString("RUTEMPRESA"));
                //dataset.setValue(rs.getString("RUTEMPRESA"), Integer.parseInt(rs.getString("NOMBREEMPR")));
                n1++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreacionGraficos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //dibujamos el grafico
        //  JFreeChart chart = ChartFactory.createPieChart("Grafico de empresa", dataset, true, true, false);
        JFreeChart chart = ChartFactory.createBarChart3D(
                "las empresas",//nombre grafico
                "Rut de las empresas",//nombre de las barras o columnas
                "Posicion de empresa", //nombre de la numeracion
                datoos,//datos del grafico      
                PlotOrientation.VERTICAL,//orientacion
                true,//leyenda de barras individuales por color
                true,//herramientras
                false//url del grafico

        );

        //aca para hacerlo visible
        panel = new ChartPanel(chart);

        //tamano para la imgen
        /*int ancho = 560;
        int alto = 380;
        File f = new File("Grafico.png");
        ChartUtilities.saveChartAsPNG(f, chart, ancho, alto);*/
    }
//    public void datoGrafico(ChartPanel panel) {
////        List<Empresa> datos = new ArrayList<>();
//        String sql = "select rutempresa from CDT1.empresa";
//
//        con = c.getConnection();
//        ps = con.prepareStatement(sql);
//        rs = ps.executeQuery();
//
////        DefaultPieDataset dataset = new DefaultPieDataset();
//        DefaultCategoryDataset datoos = new DefaultCategoryDataset();
//        int n1 = 1;
//        while (rs.next()) {
//
//            //  dataset.setValue(rs.getString("rutempresa"));
//            datoos.setValue(n1, "Empresas activas", rs.getString("RUTEMPRESA"));
//            //dataset.setValue(rs.getString("RUTEMPRESA"), Integer.parseInt(rs.getString("NOMBREEMPR")));
//            n1++;
//        }
//
//    }
//
//    private CategoryDataset dg = datoGrafico();
//
//    public JFreeChart chart = ChartFactory.createBarChart3D(
//            "las empresas",//nombre grafico
//            "Rut de las empresas",//nombre de las barras o columnas
//            "Posicion de empresa", //nombre de la numeracion
//            dg,//datos del grafico      
//            PlotOrientation.VERTICAL,//orientacion
//            true,//leyenda de barras individuales por color
//            true,//herramientras
//            false//url del grafico
//
//    );

}
