/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIQytetet;


/**
 *
 * @author david
 */
public class VistaCasilla extends javax.swing.JPanel {
//    private static Casilla casilla;

    /**
     * Creates new form VistaCasilla
     */
    public VistaCasilla() {
        initComponents();
    }
    
    public void actualizar(String descripcionCasilla){
        this.jtTextoCasilla.setText(descripcionCasilla);
        this.repaint();
        this.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtTextoCasilla = new javax.swing.JTextArea();

        setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N

        jtTextoCasilla.setEditable(false);
        jtTextoCasilla.setBackground(new java.awt.Color(254, 230, 236));
        jtTextoCasilla.setColumns(20);
        jtTextoCasilla.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jtTextoCasilla.setRows(5);
        jtTextoCasilla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Casilla del Jugador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(203, 25, 57))); // NOI18N
        jtTextoCasilla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jtTextoCasilla);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JTextArea jtTextoCasilla;
    // End of variables declaration//GEN-END:variables
}