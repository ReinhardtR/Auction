package server.softwarehouseacces.temps;

public class Item {

	private int id;
	private SaleStrategy tempSaleStrategy;


	public Item(int id, SaleStrategy tempSaleStrategy) {
		this.id = id;
		this.tempSaleStrategy = tempSaleStrategy;
	}


	public int getId() {
		return id;
	}

	public SaleStrategy getTempSaleStrategy()
	{
		return tempSaleStrategy;
	}

	@Override
	public String toString() {
		return "TempItem{" +
						"id=" + id +
						", tempSaleStrategy=" + tempSaleStrategy +
						'}';
	}
}
