package shared.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserServer extends Remote {

	CustomerServer getCustomerServer() throws RemoteException;

	SalesmanServer getSalesmanServer() throws RemoteException;
}
