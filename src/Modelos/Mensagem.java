package Modelos;

import java.io.Serializable;

public class Mensagem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private Cliente remetente;
	private Cliente destinatario;
	private boolean readed = false;

	public String getMensagem() {
		return message;
	}

	public void setMensagem(String message) {
		this.message = message;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public Cliente getRemetente() {
		return remetente;
	}

	public void setRemetente(Cliente from) {
		this.remetente = from;
	}

	public Cliente getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Cliente to) {
		this.destinatario = to;
	}
}