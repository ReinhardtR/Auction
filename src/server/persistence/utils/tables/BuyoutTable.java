package server.persistence.utils.tables;

public class BuyoutTable extends Table {
	public BuyoutTable() {
		this.schemaName = "public";
		this.tableName = "buyout";
		this.columns = new String[]{
						"price",
						"buyer"
		};
	}
}
