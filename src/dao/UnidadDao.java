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
import java.util.ArrayList;
import java.util.List;
import modelo.UnidadInterna;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Draxchaos
 */
public class UnidadDao {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();
    UnidadInterna unidad = new UnidadInterna();
    OracleCallableStatement cst;
    OracleResultSet ors;

    //lista CON procedimientos INACTIVOS
    public List listarInactivos() {
        List<UnidadInterna> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARUNIDADINACTIVA(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                UnidadInterna dad = new UnidadInterna();

                dad.setIdUnidad(ors.getInt(1));
                dad.setNombre(ors.getString(2));
                dad.setEncargado(ors.getString(3));
                dad.setDepartamento(ors.getString(4));
                dad.setSubdepartamento(ors.getString(5));
                dad.setSeccion(ors.getString(6));
                dad.setEstado(ors.getString(7));
                dad.setEmpresarut(ors.getString(8));
                lista.add(dad);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    //listar con procedimientos
    public List listaUnidades() {
        List<UnidadInterna> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARUNIDAD(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                UnidadInterna dad = new UnidadInterna();

                dad.setIdUnidad(ors.getInt(1));
                dad.setNombre(ors.getString(2));
                dad.setEncargado(ors.getString(3));
                dad.setDepartamento(ors.getString(4));
                dad.setSubdepartamento(ors.getString(5));
                dad.setSeccion(ors.getString(6));
                dad.setEstado(ors.getString(7));
                dad.setEmpresarut(ors.getString(8));
                lista.add(dad);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    public int agregarProc(UnidadInterna unid) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call AGREGARUNIDAD(?,?,?,?,?,?,?)}");

            // cmd.setInt(1, unid.getIdUnidad());
            cmd.setString(1, unid.getNombre());
            cmd.setString(2, unid.getEncargado());
            cmd.setString(3, unid.getDepartamento());
            cmd.setString(4, unid.getSubdepartamento());
            cmd.setString(5, unid.getSeccion());
            cmd.setString(6, unid.getEstado());
            cmd.setString(7, unid.getEmpresarut());
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

    public int ActualizarProc(UnidadInterna unid) {
        int r = 0;
        // String sql = "update usuario set nombre=?, apellidos=?, correo=?, usuario=?, clave=?, estado=? where idusuario=?";

        try {
            con = c.getConnection();
            // ps = con.prepareStatement(sql);
            CallableStatement cmd = con.prepareCall("{call MODIFICARUNIDAD(?,?,?,?,?,?,?,?)}");
            cmd.setInt(1, unid.getIdUnidad());
            cmd.setString(2, unid.getNombre());
            cmd.setString(3, unid.getEncargado());
            cmd.setString(4, unid.getDepartamento());
            cmd.setString(5, unid.getSubdepartamento());
            cmd.setString(6, unid.getSeccion());
            cmd.setString(7, unid.getEstado());
            cmd.setString(8, unid.getEmpresarut());
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

    public int Delete(UnidadInterna ui) {
        int r = 0;
        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call ELIMINARUNIDAD(?)}");
            cmd.setInt(1, ui.getIdUnidad());
            r = cmd.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

}
