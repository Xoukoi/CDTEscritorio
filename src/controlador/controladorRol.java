/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.RolDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Rol;
import vista.vistaRol;

/**
 *
 * @author Draxchaos
 */
public class controladorRol implements ActionListener {

    Rol rol = new Rol();
    vistaRol vr = new vistaRol();
    RolDAO rdao = new RolDAO();
    DefaultTableModel modelo = new DefaultTableModel();

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vr.btnActualizar) {
            Actualizar();
            listar(vr.tabla);
            nuevo();

        }

        if (ae.getSource() == vr.btnDelete) {
            delete();
            listar(vr.tabla);
            nuevo();
        }
        if (ae.getSource() == vr.btnAgregar) {
            add();
            listar(vr.tabla);
            nuevo();
        }
        if (ae.getSource() == vr.btnListar) {
            limpiarTabla();
            listar(vr.tabla);
            nuevo();
        }
        if (ae.getSource() == vr.btnNuevo) {
            nuevo();
        }
        if (ae.getSource() == vr.btnEditar) {
            int fila = vr.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vr, "Debee Seleccionar Una fila..!!");
            } else {
                int idROL = Integer.parseInt((String) vr.tabla.getValueAt(fila, 0).toString());
                String desc = (String) vr.tabla.getValueAt(fila, 1);
                String estado = (String) vr.tabla.getValueAt(fila, 2);

                vr.txtIdRol.setText("" + idROL);
                vr.txtDesc.setText(desc);
                vr.txtEstado.setText(estado);
            }
        }

    }

    public controladorRol(vistaRol v) {
        this.vr = v;
        this.vr.btnListar.addActionListener(this);
        this.vr.btnAgregar.addActionListener(this);
        this.vr.btnEditar.addActionListener(this);
        this.vr.btnDelete.addActionListener(this);
        this.vr.btnActualizar.addActionListener(this);
        this.vr.btnNuevo.addActionListener(this);
        //   this.vr.btnInactivos.addActionListener(this);
    }

    public void delete() {
        int fila = vr.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vr, "Debe Seleccionar una Fila...!!!");
        } else {
            int id = Integer.parseInt((String) vr.tabla.getValueAt(fila, 0).toString());
            rol.setIdRol(id);
            rdao.Delete(rol);
            System.out.println("El Reusltaod es" + id);
            JOptionPane.showMessageDialog(vr, "Rol Eliminado...!!!");
        }
        limpiarTabla();
    }

    public void add() {

        //int idRol = Integer.parseInt(vr.txtIdRol.getText());
        String desc = vr.txtDesc.getText();
        String estado = vr.txtEstado.getText();

        // rol.setIdRol(idRol);
        rol.setDescripcion(desc);
        rol.setEstado(estado);
        int r = rdao.agregarProc(rol);

        if (r == 1) {
            JOptionPane.showMessageDialog(vr, "Rol Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vr, "Error");
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Rol> lista = rdao.listaRoles();
        Object[] objeto = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdRol();
            objeto[1] = lista.get(i).getDescripcion();
            objeto[2] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void Actualizar() {
        if (vr.txtIdRol.getText().equals("")) {
            JOptionPane.showMessageDialog(vr, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int id = Integer.parseInt(vr.txtIdRol.getText());
            String nom = vr.txtDesc.getText();
            String ape = vr.txtEstado.getText();
            rol.setIdRol(id);
            rol.setDescripcion(nom);
            rol.setEstado(ape);
            int r = rdao.ActualizarProc(rol);
            if (r == 1) {
                JOptionPane.showMessageDialog(vr, "Rol Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vr, "Error");
            }
        }
        limpiarTabla();
    }

//    public void listarInactivos(JTable tabla) {
//        centrarCeldas(tabla);
//        modelo = (DefaultTableModel) tabla.getModel();
//        tabla.setModel(modelo);
//        List<UnidadInterna> lista = udao.listarInactivos();
//        Object[] objeto = new Object[8];
//        for (int i = 0; i < lista.size(); i++) {
//            objeto[0] = lista.get(i).getIdUnidad();
//            objeto[1] = lista.get(i).getNombre();
//            objeto[2] = lista.get(i).getEncargado();
//            objeto[3] = lista.get(i).getDepartamento();
//            objeto[4] = lista.get(i).getSubdepartamento();
//            objeto[5] = lista.get(i).getSeccion();
//            objeto[6] = lista.get(i).getEstado();
//            objeto[7] = lista.get(i).getEmpresarut();
//            modelo.addRow(objeto);
//        }
//        tabla.setRowHeight(35);
//        tabla.setRowMargin(10);
//
//    }
    void nuevo() {
        vr.txtIdRol.setText("");
        vr.txtDesc.setText("");
        vr.txtEstado.setText("");
        vr.txtDesc.requestFocus();
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vr.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vr.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

}
