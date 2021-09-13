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
import modelo.Usuario;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Draxchaos
 */
public class UsuarioDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conector c = new Conector();
    Usuario u = new Usuario();
    OracleCallableStatement cst;
    OracleResultSet ors;

    public Usuario login(String usuario, String clave) {
        Usuario user = new Usuario();

        String sql = "select IDUSUARIO, NOMBRE, APELLIDOS, CORREO, USUARIO, CLAVE, ESTADO from CDT1.USUARIO where USUARIO=" + "'" + usuario + "'" + " and CLAVE=" + "'" + clave + "'" + " and IDUSUARIO = IDUSUARIO";

        int r = 0;
        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                user.setIdusuario(rs.getInt(1));
                user.setNombre(rs.getString(2));
                user.setApellidos(rs.getString(3));
                user.setCorreo(rs.getString(4));
                user.setUsuario(rs.getString(5));
                user.setClave(rs.getString(6));
                user.setEstado(rs.getString(7));

                r = r + 1;
            }

            // usuario.setPassword(null);
            if (r == 1) {
                return user;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }



    //lista CON procedimientos INACTIVOS
    public List listarInactivos() {
        List<Usuario> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARUSUARIOINACTIVO(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Usuario um = new Usuario();
                um.setIdusuario(ors.getInt(1));
                um.setNombre(ors.getString(2));
                um.setApellidos(ors.getString(3));
                um.setCorreo(ors.getString(4));
                um.setUsuario(ors.getString(5));
                um.setEstado(ors.getString(6));
                lista.add(um);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    //listar con procedimientos
    public List listarProc() {
        List<Usuario> lista = new ArrayList<>();
        try {
            con = c.getConnection();
            cst = (OracleCallableStatement) con.prepareCall("{call LISTARUSUARIO(?)}");
            cst.registerOutParameter(1, OracleTypes.CURSOR);

            cst.execute();
            ors = (OracleResultSet) cst.getObject(1);
            while (ors.next()) {
                Usuario um = new Usuario();

                um.setIdusuario(ors.getInt(1));
                um.setNombre(ors.getString(2));
                um.setApellidos(ors.getString(3));
                um.setCorreo(ors.getString(4));
                um.setUsuario(ors.getString(5));
                um.setEstado(ors.getString(6));
                lista.add(um);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }
        return lista;

    }

    public int agregarProc(Usuario user) {
        int r = 0;

        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call AGREGARUSUARIO(?,?,?,?,?,?)}");

            cmd.setString(1, user.getNombre());
            cmd.setString(2, user.getApellidos());
            cmd.setString(3, user.getCorreo());
            cmd.setString(4, user.getUsuario());
            cmd.setString(5, user.getClave());
            cmd.setString(6, user.getEstado());
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

    public int Actualizar(Usuario user) {
        int r = 0;
        String sql = "update usuario set nombre=" + "'" + user.getNombre() + "'" + ", apellidos=" + "'" + user.getApellidos() + "'" + ", correo=" + "'" + user.getCorreo() + "'" + ",usuario=" + "'" + user.getUsuario() + "'" + ",clave=" + "'" + user.getClave() + "'" + ", estado=" + "'" + user.getEstado() + "'" + " where idusuario=?";

        try {
            con = c.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellidos());
            ps.setString(3, user.getCorreo());
            ps.setString(4, user.getUsuario());
            ps.setString(5, user.getClave());
            ps.setString(6, user.getEstado());
            r = ps.executeUpdate();
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
        }
        return r;
    }

    public int ActualizarProc(Usuario user) {
        int r = 0;
        // String sql = "update usuario set nombre=?, apellidos=?, correo=?, usuario=?, clave=?, estado=? where idusuario=?";

        try {
            con = c.getConnection();
            // ps = con.prepareStatement(sql);
            CallableStatement cmd = con.prepareCall("{call MODIFICARUSUARIO(?,?,?,?,?,?,?)}");
            cmd.setInt(1, user.getIdusuario());
            cmd.setString(2, user.getNombre());
            cmd.setString(3, user.getApellidos());
            cmd.setString(4, user.getCorreo());
            cmd.setString(5, user.getUsuario());
            cmd.setString(6, user.getClave());
            cmd.setString(7, user.getEstado());

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

    public int Delete(Usuario user) {
        int r = 0;
        try {
            con = c.getConnection();
            CallableStatement cmd = con.prepareCall("{call ELIMINARUSUARIO(?)}");
            cmd.setInt(1, user.getIdusuario());
            r = cmd.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
