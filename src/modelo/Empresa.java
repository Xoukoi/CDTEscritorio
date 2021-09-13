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
public class Empresa {

    String rutEmpresa, nombre, direccion, correo, rubro, estado, clave;
    int telefono;

    public Empresa() {
    }

    public Empresa(String rutEmpresa, String nombre, String direccion, String correo, String rubro, String estado, String clave, int telefono) {
        this.rutEmpresa = rutEmpresa;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.rubro = rubro;
        this.estado = estado;
        this.clave = clave;
        this.telefono = telefono;
    }

    public String getRutEmpresa() {
        return rutEmpresa;
    }

    public void setRutEmpresa(String rutEmpresa) {
        this.rutEmpresa = rutEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
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
