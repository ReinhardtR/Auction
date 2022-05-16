package shared.network.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedClient extends Remote {
	void onServerEvent(String eventName, String itemID) throws RemoteException;
}
