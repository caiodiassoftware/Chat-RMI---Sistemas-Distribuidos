package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Modelos.Mensagem;
import Modelos.Cliente;

public class Servidor extends UnicastRemoteObject implements IChatRMI {

    private static final long serialVersionUID = 1L;
    private ArrayList<Cliente> connectedUsers;
    private ArrayList<Mensagem> messages;

    public Servidor() throws RemoteException {
        super();        
        connectedUsers = new ArrayList<Cliente>();
        messages = new ArrayList<Mensagem>();
    }
    
    private int novoCliente(Cliente cliente) {
        Cliente u = cliente;
        u.setId(this.connectedUsers.size() + 1);
        this.connectedUsers.add(u);
        
        Mensagem m = new Mensagem();
        m.setMensagem("\nUsuário " + u.getName() + " (" + u.getIp() + ") conectou. ID: " + u.getId() + "!");

        try {
            m.setRemetente(u);
            DifundeMensagem(m);
        } catch (RemoteException ex) {
            
        }

        return (u.getId());
    }

    @Override
    public int registrar(String name, String ip) throws RemoteException {
        Cliente user = new Cliente(name, ip);
        return this.novoCliente(user);
    }

    @Override
    public ArrayList<Cliente> getConnectedUsers() throws RemoteException {

        ArrayList<Cliente> users = new ArrayList<Cliente>();

        // Percorre todos os usuários e retorna apenas os conectados
        for (int i = 0; i < this.connectedUsers.size(); i++) {
            if (this.connectedUsers.get(i).isConnected()) {
                users.add(this.connectedUsers.get(i));
            }
        }

        return users;
    }

    @Override
    public ArrayList<Mensagem> getMensagens(int idUser) throws RemoteException {

        ArrayList<Mensagem> mess = new ArrayList<Mensagem>();
        
        for (int i = 0; i < this.messages.size(); i++) {
            Mensagem m = this.messages.get(i);
            if (!m.isReaded() && m.getDestinatario().getId() == idUser) {
                mess.add(m);
                m.setReaded(true);
            }
        }
        return mess;
    }
    
    private Cliente getUserByID(int idUser) {

        for (int i = 0; i < this.connectedUsers.size(); i++) {
            Cliente user = this.connectedUsers.get(i);
            if (user.getId() == idUser) {
                return user;
            }
        }
        return new Cliente();
    }

    @Override
    public void DifundeMensagem(Mensagem message)
            throws RemoteException {
        
        for (int i = 0; i < this.connectedUsers.size(); i++) {
            
            if (message.getRemetente().getId() != this.connectedUsers.get(i).getId()) {
                Mensagem m = new Mensagem();
                m.setRemetente(message.getRemetente());
                m.setMensagem(message.getMensagem());  
                m.setDestinatario(this.connectedUsers.get(i));                
                this.messages.add(m);
            }
        }
    }

    @Override
    public void disconnect(int idUser) throws RemoteException {

        Cliente user = this.getUserByID(idUser);
        user.disconnect();

        Mensagem m = new Mensagem();
        m.setMensagem("\nUsuário " + user.getId() + " - " + user.getName() + " (" + user.getIp() + ") desconectou!");

        try {
            m.setRemetente(user);
            DifundeMensagem(m);
        } catch (RemoteException ex) {
        }
    }
}