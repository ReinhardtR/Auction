package server.model.item.SaleStrategy;

import server.model.item.Cart;
import shared.SaleStrategyType;
import shared.network.model.Item;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.temporal.Temporal;

public class BuyoutStrategy implements SaleStrategy {
	private double price;
	private String buyer;

	public BuyoutStrategy(double price) {
		this.price = price;
	}

	@Override
	public void offer(Item item, double amount, String username) {
		try {
			Cart.getInstance().itemBought(item);

			price = amount;
			buyer = username;
		} catch (RemoteException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean getIsSold() {
		return buyer != null;
	}

	@Override
	public String getBuyer() {
		return buyer;
	}

	@Override
	public double getOfferAmount() {
		return price;
	}

	@Override
	public SaleStrategyType strategyType() {
		return SaleStrategyType.BUYOUT;
	}

	@Override
	public Temporal getEndTime() {
		return null;
	}
}
