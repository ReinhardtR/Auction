package server.softwarehouseacces.utils.tables;

public class ItemTable extends Table {
	public ItemTable() {
		this.tableName = "item";
		this.columns = new String[]
						{
										"itemid",
										"salestrategy"
						};
	}
}
