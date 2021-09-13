/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Draxchaos
 */
public class Personal {

    String rut, nombre, apellido, correo, clave, estado, cargo, personal_rut;
    int telefono, rol_idrol, unidad_idunidad;

    public Personal() {
    }

    public Personal(String rut, String nombre, String apellido, String correo, String clave, String estado, String cargo, String personal_rut, int telefono, int rol_idrol, int unidad_idunidad) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
        this.estado = estado;
        this.cargo = cargo;
        this.personal_rut = personal_rut;
        this.telefono = telefono;
        this.rol_idrol = rol_idrol;
        this.unidad_idunidad = unidad_idunidad;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPersonal_rut() {
        return personal_rut;
    }

    public void setPersonal_rut(String personal_rut) {
        this.personal_rut = personal_rut;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getRol_idrol() {
        return rol_idrol;
    }

    public void setRol_idrol(int rol_idrol) {
        this.rol_idrol = rol_idrol;
    }

    public int getUnidad_idunidad() {
        return unidad_idunidad;
    }

    public void setUnidad_idunidad(int unidad_idunidad) {
        this.unidad_idunidad = unidad_idunidad;
    }

    public String Encriptacion(String e) {

        try {
            java.security.MessageDigest jsm = java.security.MessageDigest.getInstance("MD5");
            byte[] array = jsm.digest(e.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString(array[i] & 0xFF | 0x100).substring(1, 3));
            }
            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException ex) {

        }
        return null;
    }

}
