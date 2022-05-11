package server.model.temps;

public class TempItem {

	private int id;
	private TempSaleStrategy tempSaleStrategy;


	public TempItem(int id, TempSaleStrategy tempSaleStrategy) {
		this.id = id;
		this.tempSaleStrategy = tempSaleStrategy;
	}


	public int getId() {
		return id;
	}

	public TempSaleStrategy getTempSaleStrategy() {
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
