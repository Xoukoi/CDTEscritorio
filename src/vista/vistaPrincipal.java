/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.controlador;
import controlador.controladorEmpresa;

/**
 *
 * @author Draxchaos
 */
public class vistaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form vistaPrincipal
     */
    public vistaPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        btnEmpresa = new javax.swing.JButton();
        btnUsuario = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU PRINCIPAL");
        setBackground(new java.awt.Color(102, 102, 255));
        setMaximumSize(new java.awt.Dimension(384, 212));
        setPreferredSize(new java.awt.Dimension(575, 300));
        setSize(new java.awt.Dimension(395, 395));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("TIPO DE USUARIO");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 49, -1, -1));

        btnEmpresa.setBackground(java.awt.Color.cyan);
        btnEmpresa.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnEmpresa.setForeground(new java.awt.Color(51, 51, 51));
        btnEmpresa.setText("CLIENTE");
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });
        getContentPane().add(btnEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 184, 76));

        btnUsuario.setBackground(java.awt.Color.cyan);
        btnUsuario.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnUsuario.setForeground(new java.awt.Color(51, 51, 51));
        btnUsuario.setText("EJECUTIVO");
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 184, 76));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpeg"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, 1, 610, 280));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        loginEmpresaD l = new loginEmpresaD();
        controladorEmpresa ce = new controladorEmpresa(l);
        l.setVisible(true);
        l.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        LoginD l = new LoginD();
        controlador c = new controlador(l);
        l.setVisible(true);
        l.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vistaPrincipal().setVisible(true);
//
//                vistaPrincipal vp = new vistaPrincipal();
//                vistaOpcion vo = new vistaOpcion();
////        new vistaPrincipal().jDesktopPane1.add(vo);
//                vp.jDesktopPane1.add(vo);
//                // Login l = new Login();
//                //  l.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
