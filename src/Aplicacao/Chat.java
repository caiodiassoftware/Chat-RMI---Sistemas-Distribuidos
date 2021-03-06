/*
 * ChatRMIView.java
 */
package Aplicacao;

import Modelos.Mensagem;
import Modelos.Cliente;
import Servidor.Servidor;
import java.net.UnknownHostException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import Servidor.IChatRMI;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import java.net.InetAddress;
import java.rmi.server.ExportException;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * The application's main frame.
 */
public class Chat extends FrameView {

    // Atributos internos para conexão
    private Registry registry;
    private Registry registryServer = null;
    private IChatRMI servidor;
    private String clienteNome;
    private String ip = "127.0.0.1";
    private int codigo;
    private ArrayList<Cliente> clientesConectados;
    private java.util.Timer timerUsers;
    private java.util.Timer timerMessages;
    private String hostaddress;

    public Chat(SingleFrameApplication app) {
        super(app);
        initComponents();
        
        try {
            this.hostaddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            this.hostaddress = "IP Desconhecido";
        }
        
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConversa = new javax.swing.JTextArea();
        txtMensagem = new javax.swing.JTextField();
        btSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        connectedUsers = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        menuStartServer = new javax.swing.JMenuItem();
        menuStopServer = new javax.swing.JMenuItem();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        menuConnect = new javax.swing.JMenuItem();
        menuDisconnect = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(Aplicacao.ChatApp.class).getContext().getResourceMap(Chat.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtConversa.setBackground(resourceMap.getColor("txtConversa.background")); // NOI18N
        txtConversa.setColumns(20);
        txtConversa.setEditable(false);
        txtConversa.setRows(5);
        txtConversa.setName("txtConversa"); // NOI18N
        jScrollPane1.setViewportView(txtConversa);

        txtMensagem.setText(resourceMap.getString("txtMensagem.text")); // NOI18N
        txtMensagem.setEnabled(false);
        txtMensagem.setName("txtMensagem"); // NOI18N
        txtMensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMensagemKeyPressed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(Aplicacao.ChatApp.class).getContext().getActionMap(Chat.class, this);
        btSend.setAction(actionMap.get("EnviarMensagem")); // NOI18N
        btSend.setText(resourceMap.getString("btSend.text")); // NOI18N
        btSend.setName("btSend"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        connectedUsers.setBorder(new javax.swing.border.SoftBevelBorder(0));
        connectedUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        connectedUsers.setEnabled(false);
        connectedUsers.setName("connectedUsers"); // NOI18N
        jScrollPane2.setViewportView(connectedUsers);

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(txtMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btSend))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btSend, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        menuStartServer.setAction(actionMap.get("UpServidor")); // NOI18N
        menuStartServer.setText(resourceMap.getString("menuStartServer.text")); // NOI18N
        menuStartServer.setName("menuStartServer"); // NOI18N
        jMenu1.add(menuStartServer);

        menuStopServer.setAction(actionMap.get("stopServer")); // NOI18N
        menuStopServer.setText(resourceMap.getString("menuStopServer.text")); // NOI18N
        menuStopServer.setName("menuStopServer"); // NOI18N
        jMenu1.add(menuStopServer);

        jMenu2.add(jMenu1);

        fileMenu.setMnemonic('C');
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setToolTipText(resourceMap.getString("fileMenu.toolTipText")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        menuConnect.setAction(actionMap.get("Registrar")); // NOI18N
        menuConnect.setText(resourceMap.getString("menuConnect.text")); // NOI18N
        menuConnect.setName("menuConnect"); // NOI18N
        fileMenu.add(menuConnect);

        menuDisconnect.setAction(actionMap.get("disconnect")); // NOI18N
        menuDisconnect.setText(resourceMap.getString("menuDisconnect.text")); // NOI18N
        menuDisconnect.setName("menuDisconnect"); // NOI18N
        fileMenu.add(menuDisconnect);

        jMenu2.add(fileMenu);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        jMenu2.add(exitMenuItem);

        menuBar.add(jMenu2);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 625, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMensagemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMensagemKeyPressed
        if (evt.getKeyCode() == 10) {
            EnviarMensagem();
        }
    }//GEN-LAST:event_txtMensagemKeyPressed

    // Habilita ou desabilita os campos da tela
    private void enableFilds(boolean enable) {
        this.btSend.setEnabled(enable);
        this.txtMensagem.setEnabled(enable);
        this.connectedUsers.setEnabled(enable);
    }

    // Utilizada para atualizar a lista de Mensagens
    class refreshMessagesTask extends java.util.TimerTask {

        public void run() {
            try {
                //Terminate the timer thread
                timerMessages.cancel();
            } catch (Exception e){
                showMensagem("Erro não fatal: " + e.getMessage());
            }finally{
                // Chama de novo
                refreshMessagesList();
            }
        }
    }
    
    private void refreshMessagesList() {

        try {
            try {
                
                ArrayList<Mensagem> messages = servidor.getMensagens(this.codigo);
                
                for (int i = 0; i < messages.size(); i++) {
                    Mensagem m = messages.get(i);
                    showMensagem(m.getRemetente().getName() + "(" + m.getRemetente().getIp() + "): " + m.getMensagem());
                }

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }

            timerMessages = new java.util.Timer();
            timerMessages.schedule(new refreshMessagesTask(), 3 * 1000);

        } catch (Exception er){
            showMensagem("Erro ao buscar Mensagens: " + er.getMessage());
            disconnect();
        }
    }

    // Utilizada para atualizar a lista de usuários
    class refreshUsersTask extends java.util.TimerTask {

        public void run() {
            try {
                timerUsers.cancel();
            } catch (Exception e){
                showMensagem("Erro não fatal: " + e.getMessage());
            } finally {
                refreshUserList();
            }
        }
    }
    
    private void refreshUserList() {

        try {
            try {
                
                clientesConectados = servidor.getConnectedUsers();
                
                connectedUsers.removeAll();
                
                DefaultListModel model = new DefaultListModel();

                // Atualiza a lista na tela
                for (int i = 0; i < clientesConectados.size(); i++) {
                    Cliente u = clientesConectados.get(i);
                    model.add(i, u.getName());
                }
                connectedUsers.setModel(model);

            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }

            timerUsers = new java.util.Timer();
            timerUsers.schedule(new refreshUsersTask(), 5 * 1000);

        } catch(Exception er){
            showMensagem("Erro ao buscar lista de usuários: " + er.getMessage());
            disconnect();
        }
    }
    
    private String getString(String msg, boolean obrigatorio, String valorDefaut)
            throws Exception {

        String info = JOptionPane.showInputDialog(msg, valorDefaut);

        while ((!(info instanceof String) || info.equals("")) && obrigatorio) {

            // Se clicou em cancelar
            if (!(info instanceof String)) {
                if (JOptionPane.showConfirmDialog(null,
                        "Cancelar entrada?", "Atenção",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    throw new Exception("CANCEL");
                }
            }

            info = JOptionPane.showInputDialog(msg + "\n* Obrigatório.");

        }
        return info;
    }

    @Action
    public void Registrar() {
        try {
            
            this.ip = getString("Informe o IP do Servidor", true, this.ip);
            this.clienteNome = getString("Informe seu Nome", true, this.clienteNome);
            
            this.registry = LocateRegistry.getRegistry(ip, 1000);
            this.servidor = (IChatRMI) registry.lookup("ServidorRMI");
            
            this.codigo = this.servidor.registrar(this.clienteNome, this.hostaddress);
            
            this.enableFilds(true);
            
            this.showMensagem("Bem vindo " + this.clienteNome + ". Seu ID é: " + this.codigo);

            this.txtMensagem.requestFocus();
            
            this.menuConnect.setEnabled(false);
            this.menuDisconnect.setEnabled(true);
            
            this.refreshUserList();
          
            this.refreshMessagesList();

        } catch (AccessException e) {
            this.showMensagem("Erro no Acesso: " + e.getMessage());
            disconnect();
        } catch (RemoteException e) {
            this.showMensagem("Erro Remoto: " + e.getMessage());
            disconnect();
        } catch (NotBoundException e) {
            this.showMensagem("Servidor Não encontrado: " + e.getMessage());
            disconnect();
        } catch (Exception e) {
            if (!e.getMessage().equals("CANCEL")) {
                this.showMensagem(e.getMessage());
            }
        }
    }

    @Action
    public void EnviarMensagem() {
        try {
            
            Cliente eu = new Cliente();
            eu.setId(this.codigo);
            eu.setIp(this.hostaddress);
            eu.setName(this.clienteNome);
            
            Mensagem m = new Mensagem();
            m.setRemetente(eu);
            m.setMensagem(this.txtMensagem.getText());
            
            servidor.DifundeMensagem(m);

            this.showMensagem(this.clienteNome + " (" + this.hostaddress + "): " + m.getMensagem());
            this.txtMensagem.setText("");
            this.txtMensagem.requestFocus();

        } catch (AccessException e) {
            this.showMensagem("Erro no acesso: " + e.getMessage());
            disconnect();
        } catch (RemoteException e) {
            this.showMensagem("Erro Remoto: " + e.getMessage());
            disconnect();
        }
    }
    
    private void showMensagem(String text) {
        this.txtConversa.append("\n" + text);
    }

    @Action
    public void UpServidor() throws AlreadyBoundException {

        try {

            Servidor chatServer = new Servidor();
            
            if (registryServer == null) {
                registryServer = LocateRegistry.createRegistry(1000);
            }
            registryServer.bind("ServidorRMI", chatServer);
            
            this.showMensagem("Servidor criado e disponível!");
            
            this.menuStartServer.setEnabled(false);
            this.menuStopServer.setEnabled(true);

        }catch (ExportException e) {
            this.showMensagem("Já existe um Servidor disponível!");
            Registrar();
        }        
        catch (RemoteException e) {
            this.showMensagem("Erro ao instanciar Servidor. Erro:" + e.getMessage());
        }

    }

    @Action
    public void disconnect() {

        try {
            try {
                servidor.disconnect(this.codigo);
            } catch (AccessException e) {
                this.showMensagem("Erro no acesso. Erro: " + e.getMessage());
            } catch (RemoteException e) {
                this.showMensagem("Erro remoto. Erro: " + e.getMessage());
            } catch (Exception e){
                
            } finally {
                
                this.enableFilds(false);

                try {
                    timerUsers.cancel();
                } catch (Exception e){

                }
                
                this.showMensagem(this.clienteNome + ", você está fora.");
                
                this.menuConnect.setEnabled(true);
                this.menuDisconnect.setEnabled(false);
            }
        } catch (Exception e) {
            this.showMensagem("Erro: " + e.getMessage());
        }
    }

    @Action
    public void stopServer() {        
        JOptionPane.showMessageDialog(menuConnect, "Para parar o servidor, feche o programa.");
    }

    @Action
    public void openServerLog() {        

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSend;
    private javax.swing.JList connectedUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuConnect;
    private javax.swing.JMenuItem menuDisconnect;
    private javax.swing.JMenuItem menuStartServer;
    private javax.swing.JMenuItem menuStopServer;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextArea txtConversa;
    private javax.swing.JTextField txtMensagem;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
}
