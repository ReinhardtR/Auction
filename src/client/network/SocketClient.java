package client.network;

import shared.transferobjects.Request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements Client, Runnable {

	private final PropertyChangeSupport support;
	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;

	// TODO(): Two approaches to listening to server changes.
	//  Problem: not being broadcasted a message you sent yourself.
	//  Solution: Open new socket that is not listening.

	public SocketClient() {
		support = new PropertyChangeSupport(this);

		try {
			Socket socket = new Socket("localhost", 2910);
			outToServer = new ObjectOutputStream(socket.getOutputStream());
			inFromServer = new ObjectInputStream(socket.getInputStream());

			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			outToServer.writeObject(new Request("LISTENER", null));
			while (true) {
				Request request = (Request) inFromServer.readObject();
				support.firePropertyChange(request.getType(), null, request.getArg());
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String content) {
		try {
			outToServer.writeObject(new Request(content, "NEW_MESSAGE"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addListener(String eventName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(eventName, listener);
	}

	@Override
	public void removeListener(String eventName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(eventName, listener);
	}
}
