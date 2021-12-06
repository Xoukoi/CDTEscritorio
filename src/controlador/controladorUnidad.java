/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UnidadDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.UnidadInterna;
import vista.vistaUnidad;

/**
 *
 * @author Draxchaos
 */
public class controladorUnidad implements ActionListener {

    UnidadInterna unidad = new UnidadInterna();
    vistaUnidad vu = new vistaUnidad();
    UnidadDao udao = new UnidadDao();
    DefaultTableModel modelo = new DefaultTableModel();

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vu.btnActualizar) {
            Actualizar();
            listar(vu.tabla);
            nuevo();
        }
        if (ae.getSource() == vu.btnDelete) {
            delete();
            listar(vu.tabla);
            nuevo();
        }
        if (ae.getSource() == vu.btnAgregar) {
            add();
            listar(vu.tabla);
            nuevo();
        }
        if (ae.getSource() == vu.btnListar) {
            limpiarTabla();
            listar(vu.tabla);
            nuevo();
        }
        if (ae.getSource() == vu.btnInactivos) {
            limpiarTabla();
            listarInactivos(vu.tabla);
            nuevo();
        }
        if (ae.getSource() == vu.btnNuevo) {
            nuevo();
        }
        if (ae.getSource() == vu.btnEditar) {
            int fila = vu.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vu, "Debee Seleccionar Una fila..!!");
            } else {
                int idUnidad = Integer.parseInt((String) vu.tabla.getValueAt(fila, 0).toString());
                String nombre = (String) vu.tabla.getValueAt(fila, 1);
                String encargado = (String) vu.tabla.getValueAt(fila, 2);
                String departamento = (String) vu.tabla.getValueAt(fila, 3);
                String subdepa = (String) vu.tabla.getValueAt(fila, 4);
                String seccion = (String) vu.tabla.getValueAt(fila, 5);
                String estado = (String) vu.tabla.getValueAt(fila, 6);
                String empresa = (String) vu.tabla.getValueAt(fila, 7);

                vu.txtIdUnidad.setText("" + idUnidad);
                vu.txtNombre.setText(nombre);
                vu.txtEncargado.setText(encargado);
                vu.txtDepartamento.setText(departamento);
                vu.txtSubDepartamento.setText(subdepa);
                vu.txtSeccion.setText(seccion);
                vu.txtEstado.setText(estado);
                vu.jcboxEmpresas.setSelectedItem(empresa);
            }
        }
    }

    public controladorUnidad(vistaUnidad v) {
        this.vu = v;
        this.vu.btnListar.addActionListener(this);
        this.vu.btnAgregar.addActionListener(this);
        this.vu.btnEditar.addActionListener(this);
        this.vu.btnDelete.addActionListener(this);
        this.vu.btnActualizar.addActionListener(this);
        this.vu.btnNuevo.addActionListener(this);
        this.vu.btnInactivos.addActionListener(this);
    }

    public void delete() {
        int fila = vu.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vu, "Debe Seleccionar una Fila...!!!");
        } else {
            int id = Integer.parseInt((String) vu.tabla.getValueAt(fila, 0).toString());
            unidad.setIdUnidad(id);
            udao.Delete(unidad);
            System.out.println("El Reusltaod es" + id);
            JOptionPane.showMessageDialog(vu, "Unidad Eliminada...!!!");
        }
        limpiarTabla();
    }

    public void Actualizar() {
        if (vu.txtIdUnidad.getText().equals("")) {
            JOptionPane.showMessageDialog(vu, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int idUnidad = Integer.parseInt(vu.txtIdUnidad.getText());
            String nombre = vu.txtNombre.getText();
            String encargado = vu.txtEncargado.getText();
            String departamento = vu.txtDepartamento.getText();
            String subdepa = vu.txtSubDepartamento.getText();
            String seccion = vu.txtSeccion.getText();
            String estado = vu.txtEstado.getText();
            String empresa = vu.jcboxEmpresas.getSelectedItem().toString();

            unidad.setIdUnidad(idUnidad);
            unidad.setNombre(nombre);
            unidad.setEncargado(encargado);
            unidad.setDepartamento(departamento);
            unidad.setSubdepartamento(subdepa);
            unidad.setSeccion(seccion);
            unidad.setEstado(estado);
            unidad.setEmpresarut(empresa);
            int r = udao.ActualizarProc(unidad);
            if (r == 1) {
                JOptionPane.showMessageDialog(vu, "Usuario Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vu, "Error");
            }
        }
        limpiarTabla();
    }

    public void add() {

        String nombre = vu.txtNombre.getText();
        String encargado = vu.txtEncargado.getText();
        String departamento = vu.txtDepartamento.getText();
        String subdepa = vu.txtSubDepartamento.getText();
        String seccion = vu.txtSeccion.getText();
        String estado = vu.txtEstado.getText();
        String empresa = vu.jcboxEmpresas.getSelectedItem().toString();

        unidad.setNombre(nombre);
        unidad.setEncargado(encargado);
        unidad.setDepartamento(departamento);
        unidad.setSubdepartamento(subdepa);
        unidad.setSeccion(seccion);
        unidad.setEstado(estado);
        unidad.setEmpresarut(empresa);
        int r = udao.agregarProc(unidad);

        if (r == 1) {
            JOptionPane.showMessageDialog(vu, "Unidad Interna Agregada con Exito.");
        } else {
            JOptionPane.showMessageDialog(vu, "Error");
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<UnidadInterna> lista = udao.listaUnidades();
        Object[] objeto = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdUnidad();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getEncargado();
            objeto[3] = lista.get(i).getDepartamento();
            objeto[4] = lista.get(i).getSubdepartamento();
            objeto[5] = lista.get(i).getSeccion();
            objeto[6] = lista.get(i).getEstado();
            objeto[7] = lista.get(i).getEmpresarut();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void listarInactivos(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<UnidadInterna> lista = udao.listarInactivos();
        Object[] objeto = new Object[8];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdUnidad();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getEncargado();
            objeto[3] = lista.get(i).getDepartamento();
            objeto[4] = lista.get(i).getSubdepartamento();
            objeto[5] = lista.get(i).getSeccion();
            objeto[6] = lista.get(i).getEstado();
            objeto[7] = lista.get(i).getEmpresarut();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    void nuevo() {
        vu.txtIdUnidad.setText("");
        vu.txtNombre.setText("");
        vu.txtEncargado.setText("");
        vu.txtDepartamento.setText("");
        vu.txtSubDepartamento.setText("");
        vu.txtSeccion.setText("");
        vu.txtEstado.setText("");
        vu.jcboxEmpresas.setSelectedIndex(0);
        vu.txtNombre.requestFocus();
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vu.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vu.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

}
