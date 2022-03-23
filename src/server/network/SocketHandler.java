package server.network;

import server.model.Chat;
import shared.transferobjects.Message;
import shared.transferobjects.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler implements Runnable {

	private final Chat chat;
	private final Socket socket;
	private final Pool pool;
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;

	public SocketHandler(Socket socket, Pool pool, Chat chat) {
		this.socket = socket;
		this.pool = pool;
		this.chat = chat;

		try {
			inFromClient = new ObjectInputStream(socket.getInputStream());
			outToClient = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				Request request = (Request) inFromClient.readObject();

				if (request.getType().equals("NEW_MESSAGE")) {

					pool.broadcast();
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(Message message) {
		try {
			outToClient.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
