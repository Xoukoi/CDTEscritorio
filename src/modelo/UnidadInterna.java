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
public class UnidadInterna {
    int idUnidad ;
    String nombre, encargado, departamento, subdepartamento, seccion, estado, empresarut;

    public UnidadInterna() {
    }

    public UnidadInterna(int idUnidad, String nombre, String encargado, String departamento, String subdepartamento, String seccion, String estado, String empresarut) {
        this.idUnidad = idUnidad;
        this.nombre = nombre;
        this.encargado = encargado;
        this.departamento = departamento;
        this.subdepartamento = subdepartamento;
        this.seccion = seccion;
        this.estado = estado;
        this.empresarut = empresarut;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSubdepartamento() {
        return subdepartamento;
    }

    public void setSubdepartamento(String subdepartamento) {
        this.subdepartamento = subdepartamento;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmpresarut() {
        return empresarut;
    }

    public void setEmpresarut(String empresarut) {
        this.empresarut = empresarut;
    }
    
    
    
}
