package shared.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SalesmanServer extends Remote {


	void createItem() throws RemoteException;
}
