/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlGUI;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import sqlDB.MetaDB;
import sqlProcess.regex.ContextProxy;

/**
 *
 * @author Napo Escritorio
 */
public class frmSql extends javax.swing.JFrame {
    
    MetaDB metaDB;
    DefaultTableModel model;
    ContextProxy context;
 
    public frmSql() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("SqlUC");
        setIconImage(new ImageIcon(getClass().getResource("/ImagesGUI/SqlUC.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/ImagesGUI/FondoSQLUCSuperior.jpg"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        try {
            metaDB = MetaDB.getMetaDB();
            context = new ContextProxy();
        } catch (IOException ex) {
            Logger.getLogger(frmSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        metaTable = new javax.swing.JTable();
        Execute = new javax.swing.JButton();
        codeLine = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        menuPrincipal = new javax.swing.JMenuBar();
        menuSintaxis = new javax.swing.JMenu();
        menuHistorial = new javax.swing.JMenu();
        menuInformacion = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane1.setOpaque(false);

        metaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre Tabla", "Nro.Registros", "Campos", "Campo Clave", "Longitud Máxima"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(metaTable);

        Execute.setFont(new java.awt.Font("Khmer UI", 1, 10)); // NOI18N
        Execute.setText("META DB");
        Execute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecuteActionPerformed(evt);
            }
        });

        codeLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeLineActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingrese aquí su línea de código:");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagesGUI/IconCodeMakers.fw.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagesGUI/SqlUC.png"))); // NOI18N

        menuPrincipal.setBackground(new java.awt.Color(255, 204, 153));
        menuPrincipal.setBorder(new javax.swing.border.MatteBorder(null));
        menuPrincipal.setForeground(new java.awt.Color(255, 204, 204));

        menuSintaxis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagesGUI/buscarlistadolapiz.fw.png"))); // NOI18N
        menuSintaxis.setText("Sintaxis");
        menuSintaxis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSintaxisMouseClicked(evt);
            }
        });
        menuPrincipal.add(menuSintaxis);

        menuHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagesGUI/listado.fw.png"))); // NOI18N
        menuHistorial.setText("Historial");
        menuPrincipal.add(menuHistorial);

        menuInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagesGUI/buscar.fw.png"))); // NOI18N
        menuInformacion.setText("Información");
        menuInformacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuInformacionMouseClicked(evt);
            }
        });
        menuPrincipal.add(menuInformacion);

        setJMenuBar(menuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                .addComponent(Execute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(codeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel4)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Execute)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        codeLine.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codeLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeLineActionPerformed
       
    }//GEN-LAST:event_codeLineActionPerformed

    private void menuSintaxisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSintaxisMouseClicked
        dispose();
        frmSintaxis frmSint = new frmSintaxis();
        frmSint.setVisible(true); 
    }//GEN-LAST:event_menuSintaxisMouseClicked

    private void menuInformacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuInformacionMouseClicked
        dispose();
        frmInformacion frmInf = new frmInformacion();
        frmInf.setVisible(true); 
    }//GEN-LAST:event_menuInformacionMouseClicked

    private void ExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecuteActionPerformed
        context.request(codeLine.getText());
        fillTable();
    }//GEN-LAST:event_ExecuteActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        fillTable();
    }//GEN-LAST:event_formWindowOpened

    private void fillTable(){
        String titulos[] = {"Nombre Tabla","No. Registros","Campos",
            "Campo Clave","Longitud Campo"};
        String registro[] = new String[5];
        
        model = new DefaultTableModel(null, titulos){ //Se crea el modelo de la tabla
            @Override
            public boolean isCellEditable(int row, int col){   //impedimos que las celdas sean modificables
                return false;
            }
        };
        try {
            List<String[]> metaRecords = metaDB.getMetaRecord();
            
            for(String [] record: metaRecords){
                for(int i=1;i<record.length;i++)
                    registro[i-1] = record[i];
                model.addRow(registro);
            }            
        } catch (IOException ex) {
            Logger.getLogger(frmSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        metaTable.setModel(model);
    }
    
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
            java.util.logging.Logger.getLogger(frmSql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSql.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSql().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Execute;
    private java.awt.TextField codeLine;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menuHistorial;
    private javax.swing.JMenu menuInformacion;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JMenu menuSintaxis;
    private javax.swing.JTable metaTable;
    // End of variables declaration//GEN-END:variables
}
