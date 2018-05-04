package droid.fuel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベース接続
 * @author yasupong
 */
public class FuelUsageAnalyzerDBHelper extends SQLiteOpenHelper {

	public static String DB_NAME = "FuelUsageAnalyzer";
	public static int DB_VERSON = 1;
	public static String DB_TABLE_FUELUSAGE = "FuelUsageRecord";
	
	public FuelUsageAnalyzerDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSON);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL( "create table if not exists " + DB_TABLE_FUELUSAGE + 
						"(date text primary key," +
						"odo text not null," +
						"amount text not null," +
						"price text not null," +
						"perprice text not null" +
						")" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL( "drop table if exists " + DB_TABLE_FUELUSAGE );
		onCreate(arg0);
	}
}
