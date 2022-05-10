package server.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cart {

	private static Cart instance;
	private static Lock lock = new ReentrantLock();

	public static Cart getInstance()
	{
		if(instance == null)
		{
			synchronized (lock) {
				if (instance == null)
					instance = new Cart();
			}
		}
		return instance;
	}
}
