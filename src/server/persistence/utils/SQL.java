package server.persistence.utils;

import server.persistence.utils.exceptions.SQLUtilsException;
import server.persistence.utils.exceptions.TableNonExistent;
import server.persistence.utils.sql_code.SQLOperation;
import server.persistence.utils.sql_code.SQLStatements;
import server.persistence.utils.tables.DatabaseTables;
import server.persistence.utils.tables.Table;
import shared.SaleStrategyType;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.temporal.Temporal;


public class SQL {

	private static final SQLStatements statements;
	private static final SQLOperation operation;
	private static final DatabaseTables tables;

	static {
		statements = new SQLStatements();
		operation = new SQLOperation();
		tables = new DatabaseTables();
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
		try {
			Table auc = tables.getTable("auction"),
							buy = tables.getTable("buyout");
			String[] commonColumns = auc.getCummonColumns(buy);
			String conditionOnBothTables = operation.make(new String[][]{{"itemid", "=", itemID}});
			return statements.union(new Table[]{auc, buy}, commonColumns, conditionOnBothTables);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String selectAmountOfItems(int amount, String ascOrDesc) {
		try {
			Table auc = tables.getTable("auction"),
							buy = tables.getTable("buyout");
			return statements.selectCoalesce(new Table[]{auc, buy}, auc.getCummonColumns(buy), "itemid", ascOrDesc, amount);
		} catch (TableNonExistent e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String selectSaleStrategy(String itemID, String saleStrategy) {
		try {
			Table style = tables.getTable(saleStrategy);
			String conditions = operation.make(new String[][]{{style.getColumn("itemid"), "=", itemID}});
			return statements.select(style, style.getColumns(), conditions);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String auctionBought(String itemID) {
		try {
			Table auc = tables.getTable("auction");
			String conditions = operation.make(new String[][]{{auc.getColumn("itemid"), "=", itemID}});
			return statements.delete(auc, conditions);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String auctionNewBid(String itemID, Double newOffer, String newBidder) {
		try {
			Table auc = tables.getTable("auction");
			String columnsToSet = operation.make(new String[][]{
							{auc.getColumn("currentbid"), ("="), "" + newOffer, ","},
							{auc.getColumn("currentbidder"), ("="), "'" + newBidder + "'"}});
			String conditions = operation.make(new String[][]{{"itemid", ("="), itemID}});
			return statements.update(auc, columnsToSet, conditions);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String auctionsSoonToFinish(String wantedTime) {
		try {
			Table auc = tables.getTable("auction");
			String[] columns = new String[]{auc.getColumn("itemid"), auc.getColumn("auctionenddate")};
			String conditions = operation.make(new String[][]{{columns[1], "<", "'" + wantedTime + "'"}});
			return statements.select(auc, columns, conditions);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String buyoutBought(String itemID, String buyerUsername) {
		try {
			Table buy = tables.getTable("buyout");
			String columnsToSet = operation.make(new String[][]{{buy.getColumn("buyer"), "=", "'" + buyerUsername + "'"}});
			String conditions = operation.make(new String[][]{{buy.getColumn("itemid"), "=", itemID}});
			return statements.update(buy, columnsToSet, conditions);
		} catch (SQLUtilsException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void constructDatabaseTables(Connection c) {
		try {
			tables.reconstructDatabaceTableLayout(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Nu begynder salesman metoderne


	public static String addAuctionItem(double offerAmount, Temporal endTimestamp, SaleStrategyType strategyType, String title, String tags, String description, String salesManUsername) {
		Table auction = null;

		try {
			auction = tables.getTable("auction");


		} catch (TableNonExistent e) {
			e.printStackTrace();
		}
		System.out.println(statements.insert(auction, offerAmount, endTimestamp, strategyType.toString(), title, tags, description, salesManUsername));
		return statements.insert(auction, offerAmount, endTimestamp, strategyType.toString(), title, tags, description, salesManUsername);
	}

	public static String addBuyoutItem(double offerAmount, SaleStrategyType strategyType, String title, String tags, String description, String salesManUsername) {
		Table buyout = null;


		try {
			buyout = tables.getTable("buyout");
		} catch (TableNonExistent e) {
			e.printStackTrace();
		}

		System.out.println(statements.insert(buyout, offerAmount, strategyType.toString(), title, tags, description, salesManUsername));
		return statements.insert(buyout, offerAmount, strategyType.toString(), title, tags, description, salesManUsername);

	}

}
