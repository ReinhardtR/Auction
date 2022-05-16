package server.softwarehouseacces.utils.tables;

public class AuctionTable extends Table {
	public AuctionTable() {
		this.schemaName = "public";
		this.tableName = "auction";
		this.columns = new String[]{
						"itemid",
						"currentbid",
						"currentbidder",
						"auctionenddate",
						"salestrategy"
		};
	}
}
