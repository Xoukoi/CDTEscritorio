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
import modelo.Rol;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Draxchaos
 */
public class RolDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();
    Rol rol = new Rol();
    OracleCallableStatement cst;
    OracleResultSet ors;

//    lista CON procedimientos INACTIVOS
//    public List listarInactivos() {
//        List<UnidadInterna> lista = new ArrayList<>();
//        try {
//            con = c.getConnection();
//            cst = (OracleCallableStatement) con.prepareCall("{call LISTARUNIDADINACTIVA(?)}");
//            cst.registerOutParameter(1, OracleTypes.CURSOR);
//
//            cst.execute();
//            ors = (OracleResultSet) cst.getObject(1);
//            while (ors.next()) {
//                UnidadInterna dad = new UnidadInterna();
//
//                dad.setIdUnidad(ors.getInt(1));
//                dad.setNombre(ors.getString(2));
//                dad.setEncargado(ors.getString(3));
//                dad.setDepartamento(ors.getString(4));
//                dad.setSubdepartamento(ors.getString(5));
//                dad.setSeccion(ors.getString(6));
//                dad.setEstado(ors.getString(7));
//                dad.setEmpresarut(ors.getString(8));
//                lista.add(dad);
//            }
//            rs.close();
//            ps.close();
//        } catch (Exception e) {
//
//        }
//        return lista;
//
//    }
    //listar con procedimientos
    public List listaRoles() {
        List<Rol> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARROL(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Rol dad = new Rol();

                dad.setIdRol(ors.getInt(1));
                dad.setDescripcion(ors.getString(2));
                dad.setEstado(ors.getString(3));
                lista.add(dad);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    public int agregarProc(Rol o) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call AGREGARROL(?,?)}");

            cmd.setString(1, o.getDescripcion());
            cmd.setString(2, o.getEstado());
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

    public int ActualizarProc(Rol rl) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call MODIFICARROL(?,?,?)}");
            cmd.setInt(1, rl.getIdRol());
            cmd.setString(2, rl.getDescripcion());
            cmd.setString(3, rl.getEstado());

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

    public int Delete(Rol r) {
        int asd = 0;
        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call ELIMINARROL(?)}");
            cmd.setInt(1, r.getIdRol());
            asd = cmd.executeUpdate();
        } catch (Exception e) {
        }
        return asd;
    }

}
