/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIQytetet;

import modeloqytetet.Jugador;

/**
 *
 * @author david
 */
public class VistaJugador extends javax.swing.JPanel {   

    /**
     * Creates new form VistaJugador
     */
    public VistaJugador() {
        initComponents();
    }
    
    public void actualizar(String descripcionCasilla){
        this.jtTextoJugador.setText(descripcionCasilla);
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
        jtTextoJugador = new javax.swing.JTextArea();

        jtTextoJugador.setEditable(false);
        jtTextoJugador.setBackground(new java.awt.Color(219, 251, 194));
        jtTextoJugador.setColumns(20);
        jtTextoJugador.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        jtTextoJugador.setRows(5);
        jtTextoJugador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jugador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(1, 140, 0))); // NOI18N
        jtTextoJugador.setPreferredSize(new java.awt.Dimension(350, 135));
        jScrollPane1.setViewportView(jtTextoJugador);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JTextArea jtTextoJugador;
    // End of variables declaration//GEN-END:variables
}
