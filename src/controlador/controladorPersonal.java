/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.PersonalDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Personal;
import vista.vistaEmpleado;

/**
 *
 * @author Draxchaos
 */
public class controladorPersonal implements ActionListener {

    PersonalDAO dao = new PersonalDAO();
    Personal p = new Personal();
    vistaEmpleado vista = new vistaEmpleado();
    DefaultTableModel modelo = new DefaultTableModel();

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.btnDelete) {
            delete();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnListar) {
            limpiarTabla();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnInactivos) {
            limpiarTabla();
            listarInactivos(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnAgregar) {
            add();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnActualizar) {
            Actualizar();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnEditar) {
            int fila = vista.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debee Seleccionar Una fila..!!");
            } else {

                String rut = (String) vista.tabla.getValueAt(fila, 0);
                String nom = (String) vista.tabla.getValueAt(fila, 1);
                String ape = (String) vista.tabla.getValueAt(fila, 2);
                int telefono = Integer.parseInt((String) vista.tabla.getValueAt(fila, 3).toString());
                String correo = (String) vista.tabla.getValueAt(fila, 4);

                String estado = (String) vista.tabla.getValueAt(fila, 5);
                String cargo = (String) vista.tabla.getValueAt(fila, 6);
                int rol = Integer.parseInt((String) vista.tabla.getValueAt(fila, 7).toString());
                int idUnidad = Integer.parseInt((String) vista.tabla.getValueAt(fila, 8).toString());
                String rutPersonal = (String) vista.tabla.getValueAt(fila, 9);

                int op = 0;

                if (estado.equals("I")) {
                    op = 1;
                } else {
                    op = 0;
                }

                vista.txtRut.setText(rut);
                vista.txtNom.setText(nom);
                vista.txtApellidos.setText(ape);
                vista.txtCorreo.setText(correo);
                vista.txtTelefono.setText("" + telefono);
                vista.cbxEstado.setSelectedIndex(op);
                vista.txtCargo.setText(cargo);
                vista.txtRol.setText("" + rol);
                vista.txtUnidad.setText("" + idUnidad);
                vista.txtJefe.setText(rutPersonal);
            }
        }
    }

    public controladorPersonal(vistaEmpleado v) {
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnDelete.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnInactivos.addActionListener(this);
    }

    public void Actualizar() {
        if (vista.txtRut.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Rut, debe selecionar la opcion Editar");
        } else {
            String rut = vista.txtRut.getText();
            String nom = vista.txtNom.getText();
            String ape = vista.txtApellidos.getText();
            String correo = vista.txtCorreo.getText();
            int telefono = Integer.parseInt(vista.txtTelefono.getText());
            String clave = vista.txtClave.getText();
            int estado = vista.cbxEstado.getSelectedIndex();
            String cargo = vista.txtCargo.getText();
            int rol = Integer.parseInt(vista.txtRol.getText());
            int idUnidad = Integer.parseInt(vista.txtUnidad.getText());
            String rutPersonal = vista.txtJefe.getText();

            String encri = p.Encriptacion(clave);

            String op;

            if (estado == 1) {
                op = "I";
            } else {
                op = "A";
            }

            p.setRut(rut);
            p.setNombre(nom);
            p.setApellido(ape);
            p.setTelefono(telefono);
            p.setCorreo(correo);
            p.setClave(encri);
            p.setEstado(op);
            p.setCargo(cargo);
            p.setRol_idrol(rol);
            p.setUnidad_idunidad(idUnidad);
            p.setPersonal_rut(rutPersonal);

            int r = dao.ActualizarProc(p);
            if (r == 1) {
                JOptionPane.showMessageDialog(vista, "Empleado Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error");
            }
        }
        limpiarTabla();
    }

    public void delete() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe Seleccionar una Fila...!!!");
        } else {
            String rut = (String) vista.tabla.getValueAt(fila, 0);
            p.setRut(rut);
            dao.EliminarPersonal(p);
            System.out.println("El Reusltaod es" + rut);
            JOptionPane.showMessageDialog(vista, "Empleado Eliminado...!!!");
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Personal> lista = dao.listaPersonal();
        Object[] objeto = new Object[10];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getRut();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getApellido();
            objeto[3] = lista.get(i).getTelefono();
            objeto[4] = lista.get(i).getCorreo();
            objeto[5] = lista.get(i).getEstado();
            objeto[6] = lista.get(i).getCargo();
            objeto[7] = lista.get(i).getRol_idrol();
            objeto[8] = lista.get(i).getUnidad_idunidad();
            objeto[9] = lista.get(i).getPersonal_rut();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void listarInactivos(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Personal> lista = dao.listaPersonalInactivo();
        Object[] objeto = new Object[10];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getRut();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getApellido();
            objeto[3] = lista.get(i).getTelefono();
            objeto[4] = lista.get(i).getCorreo();
            objeto[5] = lista.get(i).getEstado();
            objeto[6] = lista.get(i).getCargo();
            objeto[7] = lista.get(i).getRol_idrol();
            objeto[8] = lista.get(i).getUnidad_idunidad();
            objeto[9] = lista.get(i).getPersonal_rut();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void add() {
        String rut = vista.txtRut.getText();
        String nom = vista.txtNom.getText();
        String ape = vista.txtApellidos.getText();
        String correo = vista.txtCorreo.getText();
        int telefono = Integer.parseInt(vista.txtTelefono.getText());
        String clave = vista.txtClave.getText();
        int estado = vista.cbxEstado.getSelectedIndex();
        String cargo = vista.txtCargo.getText();
        int rol = Integer.parseInt(vista.txtRol.getText());
        int idUnidad = Integer.parseInt(vista.txtUnidad.getText());
        String rutPersonal = vista.txtJefe.getText();

        String encri = p.Encriptacion(clave);

        String op;

        if (estado == 1) {
            op = "I";
        } else {
            op = "A";
        }

        p.setRut(rut);
        p.setNombre(nom);
        p.setApellido(ape);
        p.setTelefono(telefono);
        p.setCorreo(correo);
        p.setClave(encri);
        p.setEstado(op);
        p.setCargo(cargo);
        p.setRol_idrol(rol);
        p.setUnidad_idunidad(idUnidad);
        p.setPersonal_rut(rutPersonal);

        int r = dao.agregarProc(p);

        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Empleado Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error");
        }
        limpiarTabla();
    }

    void nuevo() {
        vista.txtRut.setText("");
        vista.txtNom.setText("");
        vista.txtApellidos.setText("");
        vista.txtCorreo.setText("");
        vista.txtTelefono.setText("");
        vista.txtClave.setText("");
        vista.txtCargo.setText("");
        vista.txtRol.setText("");
        vista.txtUnidad.setText("");
        vista.txtJefe.setText("");
        vista.cbxEstado.setSelectedIndex(0);
        vista.txtRut.requestFocus();
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
