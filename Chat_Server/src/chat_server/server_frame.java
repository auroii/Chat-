package chat_server;

//declaracao de bibliotecas
import java.io.*;
import java.net.*;
import java.util.*;


//declaracao da biblioteca que implementa o servidor do chat
public class server_frame extends javax.swing.JFrame 
{
   ArrayList saidaClientes;
   ArrayList<String> usuarios;

   public class nomeCliente implements Runnable	
   {
       BufferedReader leitor;
       Socket sock;
       PrintWriter cliente;

       
       /**
        * construtor para a classe que implementa o cliente que ira se 
        * conectar ao server
        * @param clientSocket o socket que representara o cliente no server
        * a
        * @param user a stream de dados que o usuario ira usar para escrever no server 
        */
       public nomeCliente(Socket clientSocket, PrintWriter user) 
       {
            cliente = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                leitor = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Erro inesperado\n");
            }

       }
       
       
       //implementacao da interface Runnable para executar o servidor e faze-lo responder
       //em tempo real as requisicoes dos clientes
       @Override
       public void run() 
       {
            String mensagem, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((mensagem = leitor.readLine()) != null) 
                {
                    ta_chat.append("Received: " + mensagem + "\n");
                    data = mensagem.split(":");
                    
                    for (String token:data) 
                    {
                        ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        enviarMensagem((data[0] + ":" + data[1] + ":" + chat));
                        adicionarUsuario(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        enviarMensagem((data[0] + ":has disconnected." + ":" + chat));
                        removerUsuario(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        enviarMensagem(mensagem);
                    } 
                    else 
                    {
                        ta_chat.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                saidaClientes.remove(cliente);
             } 
	} 
    }

    public server_frame() 
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Server's frame");
        setName("server"); // NOI18N
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_users.setText("Online Users");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 103, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_start)
                    .addComponent(b_users))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_clear)
                    .addComponent(b_end))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //acoes para implementat os botoes
    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try 
        {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        enviarMensagem("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");
        
        ta_chat.setText("");
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new InicializarServidor());
        starter.start();
        
        ta_chat.append("Server started...\n");
    }//GEN-LAST:event_b_startActionPerformed

    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        ta_chat.append("\n Online users : \n");
        for (String current_user : usuarios)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        
    }//GEN-LAST:event_b_usersActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new server_frame().setVisible(true);
            }
        });
    }
    
    /**
     * inicializa o novo servidor pela interface Runnable
     */
    public class InicializarServidor implements Runnable 
    {
        @Override
        public void run() 
        {
            saidaClientes = new ArrayList();
            usuarios = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) {
			Socket clientSock = serverSock.accept();
			PrintWriter escritorServidor = new PrintWriter(clientSock.getOutputStream());
			saidaClientes.add(escritorServidor);

			Thread listener = new Thread(new nomeCliente(clientSock, escritorServidor));
			listener.start();
			ta_chat.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }
    
    
    
    /**
     * adiciona um usuario ao server, no caso o cliente que abstrai o usuario via socket
     * @param data dado que aponta ao novo user
     */
    public void adicionarUsuario (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before " + name + " added. \n");
        usuarios.add(name);
        ta_chat.append("After " + name + " added. \n");
        String[] listaTemporaria = new String[(usuarios.size())];
        usuarios.toArray(listaTemporaria);

        for (String token:listaTemporaria) 
        {
            message = (token + add);
            enviarMensagem(message);
        }
        enviarMensagem(done);
    }
    
    /**
     * remove usuario do servidor
     * @param data o dado que aponta para o usuario a ser removido
     */
    public void removerUsuario (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        usuarios.remove(name);
        String[] listaTemporaria = new String[(usuarios.size())];
        usuarios.toArray(listaTemporaria);

        for (String token:listaTemporaria) 
        {
            message = (token + add);
            enviarMensagem(message);
        }
        enviarMensagem(done);
    }
    /**
     * enviar uma mensagem aos clientes conectados
     * @param mensagem a mensagem a ser enviada
     */
    public void enviarMensagem(String mensagem) 
    {
	Iterator it = saidaClientes.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter escritorAtual = (PrintWriter) it.next();
		escritorAtual.println(mensagem);
		ta_chat.append("Enviando: " + mensagem + "\n");
                escritorAtual.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("erro ao enviar. \n");
            }
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
