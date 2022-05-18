package server.persistence.utils.tables;

public class ItemTable extends Table { //Doesn't exist on database, but we play pretend
	public ItemTable() {
		this.tableName = "item";
		this.columns = new String[]
						{
										"itemid",
										"salestrategy"
						};
	}
}
