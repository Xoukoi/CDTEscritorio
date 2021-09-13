/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.EmpresaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Empresa;
import vista.loginEmpresa;
import vista.menuEmpresa;
import vista.vistaEmpresa;

/**
 *
 * @author Draxchaos
 */
public class controladorEmpresa implements ActionListener {

    loginEmpresa loem = new loginEmpresa();
    Empresa em = new Empresa();
    EmpresaDAO edao = new EmpresaDAO();
    vistaEmpresa ve = new vistaEmpresa();
    DefaultTableModel modelo = new DefaultTableModel();

    public controladorEmpresa() {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loem.btnEntrar) {
            Login();
        }
        if (ae.getSource() == ve.btnInactivos) {
            limpiarTabla();
            listarInactivos(ve.tabla);
            nuevo();
        }
        if (ae.getSource() == ve.btnDelete) {
            delete();
            listar(ve.tabla);
            nuevo();
        }
        if (ae.getSource() == ve.btnActualizar) {
            Actualizar();
            listar(ve.tabla);
            nuevo();
        }
        if (ae.getSource() == ve.btnAgregar) {
            add();
            listar(ve.tabla);
            nuevo();
        }
        if (ae.getSource() == ve.btnNuevo) {
            nuevo();
        }
        if (ae.getSource() == ve.btnEditar) {
            int fila = ve.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(ve, "Debee Seleccionar Una fila..!!");
            } else {
                //aca tomo los datos de la fila seleccionada
                String rutEmpresa = (String) ve.tabla.getValueAt(fila, 0);
                String nom = (String) ve.tabla.getValueAt(fila, 1);
                String dir = (String) ve.tabla.getValueAt(fila, 2);
                int telefono = Integer.parseInt((String) ve.tabla.getValueAt(fila, 3).toString());
                String correo = (String) ve.tabla.getValueAt(fila, 4);
                String rubro = (String) ve.tabla.getValueAt(fila, 5);
                String estado = (String) ve.tabla.getValueAt(fila, 6);
                //aca seteo los datos de la fila seleccionada a los campos de texto
                ve.txtId.setText(rutEmpresa);
                ve.txtNom.setText(nom);
                ve.txtDireccion.setText(dir);
                ve.txtCorreo.setText(correo);
                ve.txtTelefono.setText("" + telefono);
                ve.txtRubro.setText(rubro);
                ve.txtEstado.setText(estado);

            }
        }
        if (ae.getSource() == ve.btnListar) {
            limpiarTabla();
            listar(ve.tabla);
            nuevo();
        }
    }

    public controladorEmpresa(loginEmpresa le) {
        this.loem = le;
        this.loem.btnEntrar.addActionListener(this);
    }

    public controladorEmpresa(vistaEmpresa v) {
        this.ve = v;
        this.ve.btnListar.addActionListener(this);
        this.ve.btnAgregar.addActionListener(this);
        this.ve.btnEditar.addActionListener(this);
        this.ve.btnDelete.addActionListener(this);
        this.ve.btnActualizar.addActionListener(this);
        this.ve.btnNuevo.addActionListener(this);
        this.ve.btnInactivos.addActionListener(this);
    }

    public void Login() {
        String correo = loem.txtCorreo.getText();
        String clave = loem.txtClave.getText();

        String encri = em.Encriptacion(clave);

        em = edao.login(correo, encri);

        if (em == null) {
            JOptionPane.showMessageDialog(loem, "Usuario o Contraseña incorrecta");
            loem.dispose();
            loem.main(null);
        } else {
            loem.dispose();
            menuEmpresa.main(null);

        }

    }

    public void Actualizar() {
        if (ve.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(ve, "No se Identifica el Rut, debe selecionar la opcion Editar");
        } else {

            String rut = ve.txtId.getText();
            String nom = ve.txtNom.getText();
            String dir = ve.txtDireccion.getText();
            String correo = ve.txtCorreo.getText();
            int tel = Integer.parseInt(ve.txtTelefono.getText());
            String rubro = ve.txtRubro.getText();
            String estado = ve.txtEstado.getText();
            String clave = ve.txtClave.getText();

            em.setRutEmpresa(rut);
            em.setNombre(nom);
            em.setDireccion(dir);
            em.setTelefono(tel);
            em.setCorreo(correo);
            em.setRubro(rubro);
            em.setEstado(estado);
            em.setClave(clave);

            int r = edao.Actualizar(em);
            if (r == 1) {
                JOptionPane.showMessageDialog(ve, "Empresa Actualizada con Exito.");
            } else {
                JOptionPane.showMessageDialog(ve, "Error");
            }
        }
        limpiarTabla();
    }

    public void add() {
        String rut = ve.txtId.getText();
        String nom = ve.txtNom.getText();
        String dir = ve.txtDireccion.getText();
        String correo = ve.txtCorreo.getText();
        int tel = Integer.parseInt(ve.txtTelefono.getText());
        String rubro = ve.txtRubro.getText();
        String clave = ve.txtClave.getText();
        String estado = ve.txtEstado.getText();

        String encri = em.Encriptacion(clave);

        em.setRutEmpresa(rut);
        em.setNombre(nom);
        em.setDireccion(dir);
        em.setTelefono(tel);
        em.setCorreo(correo);
        em.setRubro(rubro);
        em.setClave(encri);
        em.setEstado(estado);

        int r = edao.agregar(em);

        if (r == 1) {
            JOptionPane.showMessageDialog(ve, "Empresa Agregada con Exito.");
        } else {
            JOptionPane.showMessageDialog(ve, "Error");
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Empresa> lista = edao.listarEmpresa();
        Object[] objeto = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getRutEmpresa();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getDireccion();
            objeto[3] = lista.get(i).getTelefono();
            objeto[4] = lista.get(i).getCorreo();
            objeto[5] = lista.get(i).getRubro();
            objeto[6] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void listarInactivos(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Empresa> lista = edao.listarInactivos();
        Object[] objeto = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getRutEmpresa();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getDireccion();
            objeto[3] = lista.get(i).getTelefono();
            objeto[4] = lista.get(i).getCorreo();
            objeto[5] = lista.get(i).getRubro();
            objeto[6] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void delete() {
        int fila = ve.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(ve, "Debe Seleccionar una Fila...!!!");
        } else {
            String rut = (String) ve.tabla.getValueAt(fila, 0);
            em.setRutEmpresa(rut);
            edao.Delete(em);
            System.out.println("El Reusltaod es" + rut);
            JOptionPane.showMessageDialog(ve, "Empresa Eliminado...!!!");
        }
        limpiarTabla();
    }

    void nuevo() {
        ve.txtId.setText("");
        ve.txtNom.setText("");
        ve.txtDireccion.setText("");
        ve.txtCorreo.setText("");
        ve.txtTelefono.setText("");
        ve.txtClave.setText("");
        ve.txtEstado.setText("");
        ve.txtRubro.setText("");
        ve.txtNom.requestFocus();
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < ve.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < ve.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
