package shared.network.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedClient extends Remote {
	void onNewBid(String itemID) throws RemoteException;

	void getItemToClient(String itemID) throws RemoteException;
}
