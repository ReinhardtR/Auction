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
	private ObjectOutputStream outToClient;
	private ObjectInputStream inFromClient;

	public SocketHandler(Socket socket, Chat chat) {
		this.socket = socket;
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
			Request request = (Request) inFromClient.readObject();

			switch (request.getType()) {
				case "LISTENER":
					chat.addListener("NEW_MESSAGE", this::sendMessage);
					break;
				case "NEW_MESSAGE":
					System.out.println("Message Received");
					chat.sendMessage((String) request.getArg());
					break;
				default:
					System.out.println("Invalid request type.");
					break;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(Message message) {
		try {
			outToClient.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
