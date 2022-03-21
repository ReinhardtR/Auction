package client.network;

import shared.transferobjects.Request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements Client {

	private final PropertyChangeSupport support;

	public SocketClient() {
		support = new PropertyChangeSupport(this);
	}

	public void startClient() {
		try {
			Socket socket = new Socket("localhost", 2910);
			ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

			Thread thread = new Thread(() -> listenToServer(outToServer, inFromServer));
			thread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listenToServer(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
		try {
			outToServer.writeObject(new Request("Listener", null));
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
			Request response = request(content, "NEW_MESSAGE");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Request request(String arg, String type) throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 2910);
		ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
		outToServer.writeObject(new Request(type, arg));
		return (Request) inFromServer.readObject();
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
