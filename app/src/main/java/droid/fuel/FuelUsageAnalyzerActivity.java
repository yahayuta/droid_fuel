package droid.fuel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

/**
 * 燃料消費記録
 * @author yasupong
 */
public class FuelUsageAnalyzerActivity extends Activity implements OnClickListener {
	
	/** メニューアイテムID0 */
	private final static int MENU_ITEM0 = 0;
	/** メニューアイテムID2 */
	private final static int MENU_ITEM2 = 2;
	/** メニューアイテムID3 */
	private final static int MENU_ITEM3 = 3;
	/** メニューアイテムID4 */
	private final static int MENU_ITEM4 = 4;
	/** メニューアイテムID5 */
	private final static int MENU_ITEM5 = 5;
	
	/** 新規登録モードであるか */
	private boolean isReg = true;
	/** メンバー変数：データリスト */
	private List<FuelUsageRecordEntity> dataList = null;
	/** メンバー変数：表示中のインデックス */
	private int currentIndex = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // リスナー登録
        Button btnSave = (Button)findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(this);
        Button btnFwd = (Button)findViewById(R.id.buttonFwd);
        btnFwd.setOnClickListener(this);
        Button btnBwd = (Button)findViewById(R.id.buttonRev);
        btnBwd.setOnClickListener(this);
        
        // 状態更新
        initStatus();
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
        Button btnSave = (Button)findViewById(R.id.buttonSave);
        Button btnFwd = (Button)findViewById(R.id.buttonFwd);
        Button btnBwd = (Button)findViewById(R.id.buttonRev);
        if (arg0 == btnSave) {
        	execBtnSave();
        }
        if (arg0 == btnFwd) {
        	currentIndex++;
        	updateStatus();
        }
        if (arg0 == btnBwd) {
        	currentIndex--;
        	updateStatus();
        }
	}
	
	/**
	 * SAVEボタン処理
	 */
	private void execBtnSave() {
		// DB更新
		FuelUsageAnalyzerDBHelper dbHelper = new FuelUsageAnalyzerDBHelper(getBaseContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			
			// 日時
			if (isReg) {
				values.put("date", System.currentTimeMillis());
			}
			else {
				values.put("date", ((FuelUsageRecordEntity)dataList.get(currentIndex)).getRawdate());	
			}
			
			// オドメータ
			EditText textOdo = (EditText)findViewById(R.id.editTextOdo);
			// NULLチェック
			if (isNull(textOdo.getText().toString())) return;
			// 数値チェック
			if (!isInteger(textOdo.getText().toString())) return;
			
			values.put("odo", textOdo.getText().toString());
			
			// ガソリン量
			EditText textFAmount = (EditText)findViewById(R.id.editTextFAmount);
			// NULLチェック
			if (isNull(textFAmount.getText().toString())) return;
			// 数値チェック
			if (!isInteger(textFAmount.getText().toString())) return;
			
			values.put("amount", textFAmount.getText().toString());
			
			double fAmount = Double.parseDouble(textFAmount.getText().toString());
			
			// ガソリン価格
			EditText textPrice = (EditText)findViewById(R.id.editTextPrice);
			// NULLチェック
			if (isNull(textPrice.getText().toString())) return;
			// 数値チェック
			if (!isInteger(textPrice.getText().toString())) return;
			
			values.put("price", textPrice.getText().toString());
			
			double price = Double.parseDouble(textPrice.getText().toString());
			
			// リッターあたりの価格
			double ppa = price / fAmount;
			String strPpa = String.valueOf(ppa);
			
			values.put("perprice", FuelUsageAnalyzerUtil.subStr(strPpa));
			
			// データ挿入
			if (isReg) {
				db.insert(FuelUsageAnalyzerDBHelper.DB_TABLE_FUELUSAGE, null, values);
				
				// 表示くクリア
				textOdo.setText("");
				textFAmount.setText("");
				textPrice.setText("");
			}
			// データ更新
			else {
				db.update(FuelUsageAnalyzerDBHelper.DB_TABLE_FUELUSAGE, values, "date=" + ((FuelUsageRecordEntity)dataList.get(currentIndex)).getRawdate(), null);
			}
		}
		finally {
			db.close();
		}

		// 正常保存
		AlertDialog.Builder dlg = new AlertDialog.Builder(this);
		dlg.setMessage(getString(R.string.conf_act_save_msg));
		dlg.setPositiveButton("OK", null);
		dlg.show();
		
		initStatus();
	}
	
	/**
	 * NULLチェック
	 * @param str
	 * @return
	 */
	private boolean isNull(String str) {
		if (str == null || str.length() == 0) {
			AlertDialog.Builder dlg = new AlertDialog.Builder(this);
			dlg.setMessage(getString(R.string.conf_act_err_notnull));
			dlg.setPositiveButton("OK", null);
			dlg.show();
			return true;
		}
		
		return false;
	}
	
	/**
	 * 数値チェック
	 * @param num
	 * @return
	 */
	private boolean isInteger(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			AlertDialog.Builder dlg = new AlertDialog.Builder(this);
			dlg.setMessage(getString(R.string.conf_act_err_char));
			dlg.setPositiveButton("OK", null);
			dlg.show();
			return false;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		// 給油記録メニュー追加
		MenuItem actionItem0 = menu.add(0, MENU_ITEM0, 0, getString(R.string.menu_detail));
		actionItem0.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 給油記録新規登録メニュー追加
		MenuItem actionItem2 = menu.add(0, MENU_ITEM2, 0, getString(R.string.menu_new));
		actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 給油記録編集メニュー追加
		MenuItem actionItem3 = menu.add(0, MENU_ITEM3, 0, getString(R.string.menu_edit));
		actionItem3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 給油記録エクスポートメニュー追加
		MenuItem actionItem4 = menu.add(0, MENU_ITEM4, 0, getString(R.string.menu_trans));
		actionItem4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// リセットメニュー追加
		MenuItem actionItem5 = menu.add(0, MENU_ITEM5, 0, getString(R.string.menu_reset));
		actionItem5.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ITEM0:
				// 給油記録
		        Intent intent = new Intent(FuelUsageAnalyzerActivity.this, FuelUsageAnalyzerDetailActivity.class);
		        startActivity(intent);
				return true;
			case MENU_ITEM2:
				// 給油記録新規登録
				isReg = true;
				initStatus();
				return true;
			case MENU_ITEM3:
				// 給油記録編集
				isReg = false;
				initStatus();
				return true;
			case MENU_ITEM4:
				// 給油記録エクスポート
				exportRecordLog();
				return true;
			case MENU_ITEM5:
				// リスナー登録
				DialogInterface.OnClickListener listnerDel = new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						if (DialogInterface.BUTTON_POSITIVE == which) {
							deleteAll();
						}
					}
				};
				// 確認ダイアログ表示
				AlertDialog.Builder dlgDel = new AlertDialog.Builder(this);
				dlgDel.setMessage(getString(R.string.menu_msg_reset));
				dlgDel.setPositiveButton("Yes", listnerDel);
				dlgDel.setNegativeButton("No", listnerDel);
				dlgDel.show();
				
				return true;
		}
		return true;
	}
	
	/**
	 * 全データ削除
	 */
	public void deleteAll() {
		// 全データ削除
		FuelUsageAnalyzerDBHelper dbHelper = new FuelUsageAnalyzerDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			db.delete(FuelUsageAnalyzerDBHelper.DB_TABLE_FUELUSAGE, null, null);
		}
		finally {
			db.close();
			dbHelper.close();
		}
	}
	
	/**
	 * 給油記録保存
	 */
	private void exportRecordLog() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		
		String text = "";
        List<FuelUsageRecordEntity> resultList = FuelUsageAnalyzerUtil.getAllRecord(this);
        
        // ログリスト作成
		for (Iterator<FuelUsageRecordEntity> iterator = resultList.iterator(); iterator.hasNext();) {
			FuelUsageRecordEntity data = (FuelUsageRecordEntity) iterator.next();
			text = text + data.getDate() + ",";
			text = text + data.getOdo() + ",";
			text = text + data.getAmount() + ",";
			text = text + data.getPrice() + ",";
			text = text + FuelUsageAnalyzerUtil.subStr(data.getPerprice()) + "\n";
		}
		
		intent.putExtra(Intent.EXTRA_TEXT, text);  
		startActivity(Intent.createChooser(intent, getString(R.string.menu_msg_save_log)));
	}
	
	/**
	 * 状態初期化
	 */
	private void initStatus() {
		
		// 画面部品取得
		EditText textOdo = (EditText)findViewById(R.id.editTextOdo);
		EditText textFAmount = (EditText)findViewById(R.id.editTextFAmount);
		EditText textPrice = (EditText)findViewById(R.id.editTextPrice);
		
		Button btnFwd = (Button)findViewById(R.id.buttonFwd);
        Button btnRev = (Button)findViewById(R.id.buttonRev);
        
        TextView textTitle = (TextView)findViewById(R.id.textEditMode);
        TextView textDate = (TextView)findViewById(R.id.textDate);
        
		// 新規登録モード
		if (isReg) {
			// 全表示クリア
			textOdo.setText("");
			textFAmount.setText("");
			textPrice.setText("");
            btnFwd.setVisibility(View.GONE);
        	btnRev.setVisibility(View.GONE);
        	textTitle.setVisibility(View.GONE);
        	textDate.setVisibility(View.GONE);
		}
		// 更新モード
		else {
			// 全データ取得
			dataList = FuelUsageAnalyzerUtil.getAllRecord(this);
			if (dataList == null || dataList.size() == 0) {
				isReg = true;
				return;
			}
			
        	textTitle.setVisibility(View.VISIBLE);
        	textDate.setVisibility(View.VISIBLE);
        	
        	setData();
			
			if (dataList.size() > 1) {
	            btnFwd.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * データ状態更新
	 */
	private void updateStatus() {
		
		// 画面部品取得
		Button btnFwd = (Button)findViewById(R.id.buttonFwd);
        Button btnRev = (Button)findViewById(R.id.buttonRev);
 
        if ((currentIndex + 1) >= dataList.size()) {
            btnFwd.setVisibility(View.GONE);
        }
        else {
            btnFwd.setVisibility(View.VISIBLE);
        }

        if (currentIndex == 0) {
        	btnRev.setVisibility(View.GONE);
        }
        else {
        	btnRev.setVisibility(View.VISIBLE);
        }
        
    	setData();
	}
	
	/**
	 * 画面にデータ表示
	 */
	private void setData() {
		EditText textOdo = (EditText)findViewById(R.id.editTextOdo);
		EditText textFAmount = (EditText)findViewById(R.id.editTextFAmount);
		EditText textPrice = (EditText)findViewById(R.id.editTextPrice);
		
        TextView textDate = (TextView)findViewById(R.id.textDate);
    	textDate.setText(((FuelUsageRecordEntity)dataList.get(currentIndex)).getDate());
		textOdo.setText(((FuelUsageRecordEntity)dataList.get(currentIndex)).getOdo());
		textFAmount.setText(((FuelUsageRecordEntity)dataList.get(currentIndex)).getAmount());
		textPrice.setText(((FuelUsageRecordEntity)dataList.get(currentIndex)).getPrice());
	}
}