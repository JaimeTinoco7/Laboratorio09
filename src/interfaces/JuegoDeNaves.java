/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaces;


import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @author jaime
 */
public class JuegoDeNaves extends javax.swing.JFrame {
    
    private int balaX, balaY;
    private int enemigoX, enemigoY;
    private int deltaX,deltaY;
    private boolean disparando;
    private boolean juegoTerminado;
    private int velocidadEnemigo = 2;
    private int ronda = 1;
    private final Random random = new Random();

    public JuegoDeNaves() {
        initComponents();
        movimientoDeEnemigo();
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enemigo = new javax.swing.JLabel();
        bala = new javax.swing.JLabel();
        nave = new javax.swing.JLabel();
        galaxia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        enemigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/enemenigo.png"))); // NOI18N
        enemigo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(enemigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 140, 130));

        bala.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/miisil.png"))); // NOI18N
        getContentPane().add(bala, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 30, 30));

        nave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/navee (1).png"))); // NOI18N
        nave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(nave, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 100, 110));

        galaxia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pixel-art-space-planet-design-pixel-art-8-bit-700-240553628.jpg"))); // NOI18N
        getContentPane().add(galaxia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 610, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE && !disparando) {
            disparar();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP
                || evt.getKeyCode() == KeyEvent.VK_W) {
            nave.setBounds(nave.getX(), nave.getY() - 5,
                    nave.getWidth(), nave.getHeight());
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN
                || evt.getKeyCode() == KeyEvent.VK_S) {
            nave.setBounds(nave.getX(), nave.getY() + 5,
                    nave.getWidth(), nave.getHeight());
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT
                || evt.getKeyCode() == KeyEvent.VK_D) {
            nave.setBounds(nave.getX() + 5, nave.getY(),
                    nave.getWidth(), nave.getHeight());
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT
                || evt.getKeyCode() == KeyEvent.VK_A) {
            nave.setBounds(nave.getX() - 5, nave.getY(),
                    nave.getWidth(), nave.getHeight());
        }

    }//GEN-LAST:event_formKeyPressed

    private void movimientoDeEnemigo() {
        Thread enemigoThread = new Thread(() -> {
            while (true) {
                deltaX = nave.getX() - enemigoX;
                 deltaY = nave.getY() - enemigoY;

                int distancia = 
                        (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distancia > 0) {
                    int moveX = velocidadEnemigo * deltaX / distancia;
                    int moveY = velocidadEnemigo * deltaY / distancia;

                    enemigoX += moveX;
                    enemigoY += moveY;

                    if (enemigoX < 0) {
                        enemigoX = 0;
                    }
                    if (enemigoX + enemigo.getWidth() > getWidth()) {
                        enemigoX = getWidth() - enemigo.getWidth();
                    }
                    if (enemigoY < 0) {
                        enemigoY = 0;
                    }
                    if (enemigoY + enemigo.getHeight() > getHeight()) {
                        enemigoY = getHeight() - enemigo.getHeight();
                    }

                    enemigo.setLocation(enemigoX, enemigoY);

                    if (distancia < 60) {
                        mensajePerdedor();
                        nave.setVisible(false);
                    }
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        enemigoThread.start();
    }

    private void mensajePerdedor() {
        if (!juegoTerminado) {
            juegoTerminado = true;
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog
        (this, "¡Perdiste!");
                dispose();
            });
        }
    }

    private void disparar() {
        {
            balaX = nave.getX() + nave.getWidth() / 2 - bala.getWidth() / 2;
            balaY = nave.getY();
            bala.setBounds(balaX, balaY, bala.getWidth(),
                    bala.getHeight());
            bala.setVisible(true);

            Thread disparoThread = new Thread(() -> {
                while (balaY > 0) {
                    balaY -= 10;
                    bala.setLocation(balaX, balaY);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Detectar colisión entre la bala y el enemigo
                    if (bala.getBounds().intersects(enemigo.getBounds())) {
                        eliminarEnemigo();
                        mensajeGanador();
                        bala.setVisible(false);
                        return;
                    }
                }
                bala.setVisible(false);
            });
            disparoThread.start();
        }
    }

    private void eliminarEnemigo() {
        enemigo.setVisible(false);
    }

    private void mensajeGanador() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                    "¡Ganaste la ronda " + ronda + "!");
            ronda++;
            aumentarVelocidadEnemigo();
            reaparecerEnemigo();

        });
    }

    private void aumentarVelocidadEnemigo() {
        velocidadEnemigo += 1;
    }

    private void reaparecerEnemigo() {

        int maxX = getWidth() - enemigo.getWidth();
        int maxY = getHeight() - enemigo.getHeight();

        int minDistance = 200;
        int newX, newY;

        do {

            newX = random.nextInt(maxX);
            newY = random.nextInt(maxY / 2);
        } while (Point2D.distance(newX, newY,
                nave.getX(), nave.getY()) < minDistance);

        enemigoX = newX;
        enemigoY = newY;

        enemigo.setLocation(enemigoX, enemigoY);
        enemigo.setVisible(true);
    }

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
            java.util.logging.Logger.getLogger(JuegoDeNaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JuegoDeNaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JuegoDeNaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JuegoDeNaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JuegoDeNaves().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bala;
    private javax.swing.JLabel enemigo;
    private javax.swing.JLabel galaxia;
    private javax.swing.JLabel nave;
    // End of variables declaration//GEN-END:variables
}
