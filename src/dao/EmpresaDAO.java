/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.Conector;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Empresa;
import modelo.Usuario;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Draxchaos
 */
public class EmpresaDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();
    Usuario u = new Usuario();
    OracleCallableStatement cst;
    OracleResultSet ors;
    Empresa em = new Empresa();

    public Empresa login(String correo, String clave) {
        Empresa emp = new Empresa();

        String sql = "select rutempresa, nombreempr, direccionemp, correoemp, rubro, estado, clave from CDT1.empresa where correoemp=" + "'" + correo + "'" + " and clave=" + "'" + clave + "'" + " and rutempresa = rutempresa";

        int r = 0;
        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                emp.setRutEmpresa(rs.getString(1));
                emp.setNombre(rs.getString(2));
                emp.setDireccion(rs.getString(3));
                emp.setCorreo(rs.getString(4));
                emp.setRubro(rs.getString(5));
                emp.setEstado(rs.getString(6));
                emp.setClave(rs.getString(7));

                r = r + 1;
            }

            if (r == 1) {
                return emp;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    //lista a un combo box
    public void listarCombo(JComboBox cbox_empresa) {

        String sql = "select rutempresa, NOMBREEMPR from CDT1.empresa";

        int r = 0;
        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            cbox_empresa.removeAllItems();
            while (rs.next()) {

                cbox_empresa.addItem(
                        rs.getString("rutempresa")
                );
                // cbox_empresa.addItem(rs.getString("rutempresa"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {

                con.close();
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }

    }



    //test
    public void listarCombox(JComboBox<Empresa> cbx) throws SQLException {
        String sql = "select rutempresa, NOMBREEMPR from CDT1.empresa";
        int r = 0;
        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            cbx.removeAllItems();
            while (rs.next()) {

                cbx.addItem(new Empresa(
                        rs.getString("rutempresa"),
                        rs.getString("NOMBREEMPR")
                ));
            }

        } catch (SQLException e) {

            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, e);
        } finally {

            con.close();
            rs.close();
        }
    }
    //listar con procedimiento

    public List listarEmpresa() {
        List<Empresa> datos = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTAREMPRESA(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);
            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Empresa e = new Empresa();
                e.setRutEmpresa(ors.getString(1));
                e.setNombre(ors.getString(2));
                e.setDireccion(ors.getString(3));
                e.setTelefono(ors.getInt(4));
                e.setCorreo(ors.getString(5));
                e.setRubro(ors.getString(6));
                e.setEstado(ors.getString(7));
                datos.add(e);
            }

        } catch (Exception e) {
        }
        return datos;
    }

    //lista CON procedimientos INACTIVOS
    public List listarInactivos() {
        List<Empresa> datos = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTAREMPRESAINACTIVA(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Empresa e = new Empresa();
                e.setRutEmpresa(ors.getString(1));
                e.setNombre(ors.getString(2));
                e.setDireccion(ors.getString(3));
                e.setTelefono(ors.getInt(4));
                e.setCorreo(ors.getString(5));
                e.setRubro(ors.getString(6));
                e.setEstado(ors.getString(7));
                datos.add(e);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return datos;

    }

    public int agregar(Empresa em) {
        int r = 0;
        //String sql = "insert into cdt1.empresa(RUTEMPRESA, NOMBREEMPR, DIRECCIONEMP, TELEFONOEMP, CORREOEMP, RUBRO, ESTADO, CLAVE)values(?,?,?,?,?,?,?,?)";
        try {
            con = c.getConnection();
            //ps = con.prepareStatement(sql);
            CallableStatement cmd = con.prepareCall("{call AGREGAREMPRESA(?,?,?,?,?,?,?,?)}");
            cmd.setString(1, em.getRutEmpresa());
            cmd.setString(2, em.getNombre());
            cmd.setString(3, em.getDireccion());
            cmd.setInt(4, em.getTelefono());
            cmd.setString(5, em.getCorreo());
            cmd.setString(6, em.getRubro());
            cmd.setString(8, em.getClave());
            cmd.setString(7, em.getEstado());
            r = cmd.executeUpdate();
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        }
        return r;
    }

    public int Actualizar(Empresa empresa) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call MODIFICAREMPRESA(?,?,?,?,?,?,?,?)}");
            cmd.setString(1, em.getRutEmpresa());
            cmd.setString(2, em.getNombre());
            cmd.setString(3, em.getDireccion());
            cmd.setInt(4, em.getTelefono());
            cmd.setString(5, em.getCorreo());
            cmd.setString(6, em.getRubro());
            cmd.setString(7, em.getEstado());
            cmd.setString(8, em.getClave());
            r = cmd.executeUpdate();
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        }
        return r;
    }

    public int Delete(Empresa empresa) {
        int r = 0;
        //String sql = "delete from usuario where Id=" + id;
        try {
            con = c.getConnection();
            //ps = con.prepareStatement(sql);
            CallableStatement cmd = con.prepareCall("{call ELIMINAREMPRESA(?)}");
            cmd.setString(1, empresa.getRutEmpresa());
            r = cmd.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
