package server.softwarehouse.utils;

import server.softwarehouse.utils.exceptions.TableNonExistent;
import server.softwarehouse.utils.sqlcode.SQLOperation;
import server.softwarehouse.utils.sqlcode.SQLStatements;
import server.softwarehouse.utils.tables.AuctionTable;
import server.softwarehouse.utils.tables.BuyoutTable;
import server.softwarehouse.utils.tables.ItemTable;
import server.softwarehouse.utils.tables.Table;

import java.util.Map;

public class SQL {

	private static final SQLStatements statements;
	private static final SQLOperation operation;
	private static final Map<String, Table> TABLES_ON_DATABASE;

	static {
		statements = new SQLStatements();
		Table itemTable = new ItemTable(),
						auctionTable = new AuctionTable(),
						buyoutTable = new BuyoutTable();

		TABLES_ON_DATABASE = Map.of(
						itemTable.getTableName(), itemTable,
						auctionTable.getTableName(), auctionTable,
						buyoutTable.getTableName(), buyoutTable
		);

		operation = new SQLOperation();
	}


	public static String getURL() {
		return "jdbc:postgresql://hattie.db.elephantsql.com:5432/isgypvka";
	}

	public static String getUSERNAME() {
		return "isgypvka";
	}

	public static String getPASSWORD() {
		return "UkY3C9sbYugpjto58d8FAk9M54JiLanr";
	}

	public static String selectItem(String itemID) {
		Table item = null,
						auc = null,
						buy = null;
		String conditionOnBothTables = null;
		try {
			item = table("item");
			auc = table("auction");
			buy = table("buyout");
			conditionOnBothTables = operation.make(new String[][]{{item.getColumn("itemid"), "=", itemID}});
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		assert item != null;
		return statements.union(new Table[]{auc, buy}, item.getColumns(), conditionOnBothTables);
	}

	public static String selectAmountOfItems(int amount, String ascOrDesc) {
		Table auc = null,
						buy = null;
		try {
			auc = table("auction");
			buy = table("buyout");
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}

		return statements.selectAmount(new Table[]{auc, buy}, "itemid", ascOrDesc, amount);
	}

	public static String selectSaleStrategy(String itemID, String saleStrategy) {
		Table style = null;
		String[] columnNames = null;
		String conditions = null;
		try {
			style = table(saleStrategy);
			columnNames = style.getColumns();
			conditions = operation.make(new String[][]{{"itemid", "=", itemID}});
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		return statements.select(style, columnNames, conditions);
	}

	public static String auctionBought(String itemID) {
		Table auc = null;
		String conditions = null;
		try {
			auc = table("auction");
			conditions = operation.make(new String[][]{{"itemid", "=", itemID}});
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		assert auc != null;
		return statements.delete(auc, conditions);
	}

	public static String auctionNewBid(String itemID, Double newOffer, String newBidder) {
		Table auc = null;
		String columnsToSet = null;
		String conditions = null;
		try {
			auc = table("auction");
			columnsToSet = operation.make(new String[][]{
							{auc.getColumn("currentbid"), ("="), "" + newOffer, ","},
							{auc.getColumn("currentbidder"), ("="), "'" + newBidder + "'"}});
			conditions = operation.make(new String[][]{{"itemid", ("="), itemID}});
			return statements.update(auc, columnsToSet, conditions);
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		assert auc != null;
		return statements.update(auc, columnsToSet, conditions);
	}


	public static String auctionsSoonToFinish(String wantedTime) {
		Table auc = null;
		String[] columns = null;
		String conditions = null;
		try {
			auc = table("auction");
			columns = new String[]{"itemid", auc.getColumn("auctionenddate")};
			conditions = operation.make(new String[][]{{auc.getColumn("auctionenddate"), "<", wantedTime}});
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		return statements.select(auc, columns, conditions);
	}

	public static String buyoutBought(String itemID, String buyerUsername) {
		Table buy = null;
		String columnsToSet = null;
		String conditions = null;
		try {
			buy = table("buyout");
			columnsToSet = operation.make(new String[][]{{buy.getColumn("buyer"), "=", "'" + buyerUsername + "'"}});
			conditions = operation.make(new String[][]{{"itemid", "=", itemID}});
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		assert buy != null;
		return statements.update(buy, columnsToSet, conditions);
	}

	private static Table table(String tableName) throws TableNonExistent {
		Table wantedTable = TABLES_ON_DATABASE.get(tableName.strip().toLowerCase());
		if (wantedTable == null) {
			throw new TableNonExistent(
							"The Table does either not exist in the database, or util.SQL is not updated." +
											"\nPlease check if !\"" + tableName + "\"! should exists in either"
			);
		}
		return wantedTable;
	}
}
