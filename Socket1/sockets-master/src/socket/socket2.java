
package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;


public class socket2 extends javax.swing.JFrame {
DataInputStream entrada;
DataOutputStream salida;
    public static ServerSocket server;
    public socket2() {
        initComponents();
        this.setVisible(true);
        iniciarServidor();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        messageField = new javax.swing.JTextField();
        boton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(700, 300, 0, 0));

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        boton.setText("Enviar");
        boton.setEnabled(false);
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(boton))
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(boton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageFieldActionPerformed

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActionPerformed

        enviarMensaje();
    }//GEN-LAST:event_botonActionPerformed

    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.awt.EventQueue.invokeLater(() -> new socket2().setVisible(true));
                
            }
        });
    }
    
    
    private void enviarMensaje() {
        try {
            String mensaje = messageField.getText();
            salida.writeUTF(mensaje);
            chatArea.append("Tú: " + mensaje + "\n");
            messageField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> chatArea.append(mensaje));
    }
    private void iniciarServidor() {
        new Thread(() -> {
            try {
                server = new ServerSocket(9999);
                mostrarMensaje("Servidor esperando conexión...\n");

                Socket socket = server.accept();
                mostrarMensaje("Cliente conectado.\n");

                entrada = new DataInputStream(socket.getInputStream());
                salida = new DataOutputStream(socket.getOutputStream());

                SwingUtilities.invokeLater(() -> boton.setEnabled(true)); // Habilita el botón

                while (true) {
                    String mensaje = entrada.readUTF();
                    mostrarMensaje("Cliente: " + mensaje + "\n");
                }
            } catch (IOException e) {
                mostrarMensaje("Error en el servidor.\n");
                e.printStackTrace();
            }
        }).start();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton;
    private static javax.swing.JTextArea chatArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField messageField;
    // End of variables declaration//GEN-END:variables
}
//