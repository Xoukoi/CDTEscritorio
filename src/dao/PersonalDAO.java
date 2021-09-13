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
import modelo.Personal;
import modelo.Rol;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Draxchaos
 */
public class PersonalDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();
    Rol rol = new Rol();
    OracleCallableStatement cst;
    OracleResultSet ors;

    public Personal login(String correo, String clave) {
        Personal per = new Personal();

        String sql = "select rut, NOMBRE, apepate, telefono, correo, contrania, ESTADO, cargo, rol_idrol, unidadinterna_idunidad, personal_rut from CDT1.personal where correo=" + "'" + correo + "'" + " and contrania=" + "'" + clave + "'" + " and rut = rut";

        int r = 0;
        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                per.setRut(rs.getString(1));
                per.setNombre(rs.getString(2));
                per.setApellido(rs.getString(3));
                per.setTelefono(rs.getInt(4));
                per.setCorreo(rs.getString(5));
                per.setClave(rs.getString(6));
                per.setEstado(rs.getString(7));
                per.setCargo(rs.getString(8));
                per.setRol_idrol(rs.getInt(9));
                per.setUnidad_idunidad(rs.getInt(10));
                per.setPersonal_rut(rs.getString(11));

                r = r + 1;
            }

            if (r == 1) {
                return per;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    public List listaPersonal() {
        List<Personal> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARPERSONAL(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Personal per = new Personal();

                per.setRut(ors.getString(1));
                per.setNombre(ors.getString(2));
                per.setApellido(ors.getString(3));
                per.setTelefono(ors.getInt(4));
                per.setCorreo(ors.getString(5));
                per.setClave(ors.getString(6));
                per.setEstado(ors.getString(7));
                per.setCargo(ors.getString(8));
                per.setRol_idrol(ors.getInt(9));
                per.setUnidad_idunidad(ors.getInt(10));
                per.setPersonal_rut(ors.getString(11));

                lista.add(per);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    public List listaPersonalInactivo() {
        List<Personal> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARPERSONALINACTIVO(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Personal per = new Personal();

                per.setRut(ors.getString(1));
                per.setNombre(ors.getString(2));
                per.setApellido(ors.getString(3));
                per.setTelefono(ors.getInt(4));
                per.setCorreo(ors.getString(5));
                per.setClave(ors.getString(6));
                per.setEstado(ors.getString(7));
                per.setCargo(ors.getString(8));
                per.setRol_idrol(ors.getInt(9));
                per.setUnidad_idunidad(ors.getInt(10));
                per.setPersonal_rut(ors.getString(11));

                lista.add(per);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    public int agregarProc(Personal per) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call AGREGARPERSONAL(?,?,?,?,?,?,?,?,?,?,?)}");

            cmd.setString(1, per.getRut());
            cmd.setString(2, per.getNombre());
            cmd.setString(3, per.getApellido());
            cmd.setInt(4, per.getTelefono());
            cmd.setString(5, per.getCorreo());
            cmd.setString(6, per.getClave());
            cmd.setString(7, per.getEstado());
            cmd.setString(8, per.getCargo());
            cmd.setInt(9, per.getRol_idrol());
            cmd.setInt(10, per.getUnidad_idunidad());
            cmd.setString(11, per.getPersonal_rut());
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

    public int EliminarPersonal(Personal per) {
        int r = 0;
        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call ELIMINARPERSONAL(?)}");
            cmd.setString(1, per.getRut());
            r = cmd.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public int ActualizarProc(Personal per) {
        int r = 0;
       
        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call MODIFICARPERSONAL(?,?,?,?,?,?,?,?,?,?,?)}");
            cmd.setString(1, per.getRut());
            cmd.setString(2, per.getNombre());
            cmd.setString(3, per.getApellido());
            cmd.setInt(4, per.getTelefono());
            cmd.setString(5, per.getCorreo());
            cmd.setString(6, per.getClave());
            cmd.setString(7, per.getEstado());
            cmd.setString(8, per.getCargo());
            cmd.setInt(9, per.getRol_idrol());
            cmd.setInt(10, per.getUnidad_idunidad());
            cmd.setString(11, per.getPersonal_rut());

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

}
