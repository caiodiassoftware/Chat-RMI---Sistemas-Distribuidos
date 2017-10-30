package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Modelos.Mensagem;
import Modelos.Cliente;

public interface IChatRMI extends Remote {
    public int registrar(String name, String ipOrigem) throws RemoteException;
    public void disconnect(int idUser) throws RemoteException;
    public void DifundeMensagem(Mensagem message) throws RemoteException;
    public ArrayList<Mensagem> getMensagens(int idUser) throws RemoteException;
    public ArrayList<Cliente> getConnectedUsers() throws RemoteException;
}
