/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import vista.Login;
import vista.menuUsuario;
import vista.vistaUsuario;

/**
 *
 * @author Draxchaos
 */
public class controlador implements ActionListener {

    UsuarioDAO dao = new UsuarioDAO();
    Usuario u = new Usuario();
    vistaUsuario vista = new vistaUsuario();
    Login login = new Login();
    DefaultTableModel modelo = new DefaultTableModel();

    public controlador() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login.btnEntrar) {

            Login();

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
        if (ae.getSource() == vista.btnEditar) {
            int fila = vista.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debee Seleccionar Una fila..!!");
            } else {
                int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
                String nom = (String) vista.tabla.getValueAt(fila, 1);
                String ape = (String) vista.tabla.getValueAt(fila, 2);
                String correo = (String) vista.tabla.getValueAt(fila, 3);
                String usuario = (String) vista.tabla.getValueAt(fila, 4);
                String estado = (String) vista.tabla.getValueAt(fila, 5);
                int op = 0;

                if (estado.equals("I")) {
                    op = 1;
                } else {
                    op = 0;
                }

                //   String estado = (String) vista.tabla.getValueAt(fila, 6);
                vista.txtId.setText("" + id);
                vista.txtNom.setText(nom);
                vista.txtApellidos.setText(ape);
                vista.txtCorreo.setText(correo);
                vista.txtUsuario.setText(usuario);
                vista.cbxEstado.setSelectedIndex(op);
                // vista.txtEstado.setText(estado);
            }
        }
        if (ae.getSource() == vista.btnActualizar) {
            Actualizar();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnDelete) {
            delete();
            listar(vista.tabla);
            nuevo();
        }
        if (ae.getSource() == vista.btnNuevo) {
            nuevo();
        }
    }

    public controlador(Login l) {
        this.login = l;
        this.login.btnEntrar.addActionListener(this);
    }

    public controlador(vistaUsuario v) {
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnDelete.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnInactivos.addActionListener(this);
    }

    public void delete() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe Seleccionar una Fila...!!!");
        } else {
            int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
            u.setIdusuario(id);
            dao.Delete(u);
            System.out.println("El Reusltaod es" + id);
            JOptionPane.showMessageDialog(vista, "Usuario Eliminado...!!!");
        }
        limpiarTabla();
    }

    public void Login() {
        String user = login.txtUsuario.getText();
        String clave = login.txtClave.getText();

        String encri = u.Encriptacion(clave);

        u = dao.login(user, encri);

        if (u == null) {
            JOptionPane.showMessageDialog(vista, "Usuario o Contraseña incorrecta");
            login.dispose();
            Login.main(null);
        } else {
            login.dispose();
            menuUsuario.main(null);

        }

    }

    public void Actualizar() {
        if (vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int id = Integer.parseInt(vista.txtId.getText());
            String nom = vista.txtNom.getText();
            String ape = vista.txtApellidos.getText();
            String correo = vista.txtCorreo.getText();
            String usuario = vista.txtUsuario.getText();
            String clave = vista.txtClave.getText();
            int estado = vista.cbxEstado.getSelectedIndex();

            String encri = u.Encriptacion(clave);

            String op;

            if (estado == 1) {
                op = "I";
            } else {
                op = "A";
            }

            u.setIdusuario(id);
            u.setNombre(nom);
            u.setApellidos(ape);
            u.setCorreo(correo);
            u.setUsuario(usuario);
            u.setClave(encri);
            u.setEstado(op);
            int r = dao.ActualizarProc(u);
            if (r == 1) {
                JOptionPane.showMessageDialog(vista, "Usuario Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error");
            }
        }
        limpiarTabla();
    }

    public void add() {

        String nom = vista.txtNom.getText();
        String ape = vista.txtApellidos.getText();
        String correo = vista.txtCorreo.getText();
        String usuario = vista.txtUsuario.getText();
        String clave = vista.txtClave.getText();
        int estado = vista.cbxEstado.getSelectedIndex();

        String encri = u.Encriptacion(clave);

        String op;

        if (estado == 1) {
            op = "I";
        } else {
            op = "A";
        }

        u.setNombre(nom);
        u.setApellidos(ape);
        u.setCorreo(correo);
        u.setUsuario(usuario);
        u.setClave(encri);
        u.setEstado(op);
        int r = dao.agregarProc(u);

        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Usuario Agregado con Exito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error");
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Usuario> lista = dao.listarProc();
        Object[] objeto = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdusuario();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getApellidos();
            objeto[3] = lista.get(i).getCorreo();
            objeto[4] = lista.get(i).getUsuario();
            objeto[5] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    public void listarInactivos(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Usuario> lista = dao.listarInactivos();
        Object[] objeto = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getIdusuario();
            objeto[1] = lista.get(i).getNombre();
            objeto[2] = lista.get(i).getApellidos();
            objeto[3] = lista.get(i).getCorreo();
            objeto[4] = lista.get(i).getUsuario();
            objeto[5] = lista.get(i).getEstado();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(10);

    }

    void nuevo() {
        vista.txtId.setText("");
        vista.txtNom.setText("");
        vista.txtApellidos.setText("");
        vista.txtCorreo.setText("");
        vista.txtUsuario.setText("");
        vista.txtClave.setText("");
        vista.cbxEstado.setSelectedIndex(0);
        vista.txtNom.requestFocus();
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
