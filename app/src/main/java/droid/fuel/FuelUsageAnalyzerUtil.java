package droid.fuel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 共通部品
 * @author yasupong
 */
public class FuelUsageAnalyzerUtil {
	
	/** 数値長さ */
	private final static int MAX_LENGTH = 6;
	
	/**
	 * 全データ取得
	 * @param cnt
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static List<FuelUsageRecordEntity> getAllRecord(Context cnt) {
		
		FuelUsageAnalyzerDBHelper dbHelper = new FuelUsageAnalyzerDBHelper(cnt);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		Cursor cAll = db.query(FuelUsageAnalyzerDBHelper.DB_TABLE_FUELUSAGE, new String[]{ "date","odo","amount","price","perprice" }, null, null, null, null, "date desc");

        List<FuelUsageRecordEntity> resultList = new ArrayList<FuelUsageRecordEntity>();
        
		SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
        // リストに落とし込む
        while (cAll.moveToNext()){
        	FuelUsageRecordEntity data = new FuelUsageRecordEntity();
        	
        	data.setDate(sdfDB.format(new Date(cAll.getLong(0))));
        	data.setRawdate(cAll.getLong(0));
        	data.setOdo(cAll.getString(1));
        	data.setAmount(cAll.getString(2));
        	data.setPrice(cAll.getString(3));
          	data.setPerprice(cAll.getString(4));
          	
        	resultList.add(data);
        }
        
        cAll.close();
        db.close();
        
        return resultList;
	}
	
	/**
	 * サブストリングする
	 * @param in
	 * @return
	 */
	public static String subStr(String in) {
		if (in.length() > MAX_LENGTH) {
			in = in.substring(0,MAX_LENGTH);
		}
		return in;
	}
}
