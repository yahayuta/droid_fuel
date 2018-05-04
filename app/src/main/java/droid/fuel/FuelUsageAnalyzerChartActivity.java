package droid.fuel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * FuelUsageAnalyzerChartActivity
 * @author yasupong
 */
public class FuelUsageAnalyzerChartActivity extends Activity {
	
	/** メニューアイテムID0 */
	private final int MENU_ITEM0 = 0;
	/** メニューアイテムID1 */
	private final int MENU_ITEM1 = 1;
	/** メニューアイテムID2 */
	private final int MENU_ITEM2 = 2;
	/** メニューアイテムID3 */
	private final int MENU_ITEM3 = 3;
	/** メニューアイテムID4 */
	private final int MENU_ITEM4 = 4;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);
        
        // デフォルト表示
        loadDifChart();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		// 走行距離
		MenuItem actionItem0 = menu.add(0, MENU_ITEM0, 0, getString(R.string.chart_dif));
		actionItem0.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 給油量
		MenuItem actionItem1 = menu.add(0, MENU_ITEM1, 0, getString(R.string.chart_amount));
		actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 給油価格
		MenuItem actionItem2 = menu.add(0, MENU_ITEM2, 0, getString(R.string.chart_price));
		actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// リッター価格
		MenuItem actionItem3 = menu.add(0, MENU_ITEM3, 0, getString(R.string.chart_ppu));
		actionItem3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 燃費
		MenuItem actionItem4 = menu.add(0, MENU_ITEM4, 0, getString(R.string.chart_fe));
		actionItem4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ITEM0:
				// 走行距離
				loadDifChart();
				return true;
			case MENU_ITEM1:
				// 給油量
				loadAmountChart();
				return true;
			case MENU_ITEM2:
				// 給油価格
				loadPriceChart();
				return true;
			case MENU_ITEM3:
				// リッター価格
				loadPPUChart();
				return true;
			case MENU_ITEM4:
				// 燃費
				loadFEChart();
				return true;
		}
		return true;
	}
	
	/**
	 * 走行距離チャート
	 */
	private void loadDifChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        // ビューの作成
        FuelUsageAnalyzerChartView cView = new FuelUsageAnalyzerChartView(this);
        // ログビリスト取得
        cView.setLogList(FuelUsageAnalyzerUtil.getAllRecord(this));
        cView.setChart_name(getString(R.string.chart1_title));
        cView.setChart_x_label(getString(R.string.chart1_x));
        cView.setChart_y_label(getString(R.string.chart1_y));
        cView.setChart_plot(getString(R.string.chart1_plot));
        cView.setChart_type(0);
        
        layout.addView(cView);
	}
	
	/**
	 * 給油量チャート
	 */
	private void loadAmountChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        // ビューの作成
        FuelUsageAnalyzerChartView cView = new FuelUsageAnalyzerChartView(this);
        // ログビリスト取得
        cView.setLogList(FuelUsageAnalyzerUtil.getAllRecord(this));
        cView.setChart_name(getString(R.string.chart2_title));
        cView.setChart_x_label(getString(R.string.chart2_x));
        cView.setChart_y_label(getString(R.string.chart2_y));
        cView.setChart_plot(getString(R.string.chart2_plot));
        cView.setChart_type(1);
        
        layout.addView(cView);
	}
	
	/**
	 * 給油価格チャート
	 */
	private void loadPriceChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        // ビューの作成
        FuelUsageAnalyzerChartView cView = new FuelUsageAnalyzerChartView(this);
        // ログビリスト取得
        cView.setLogList(FuelUsageAnalyzerUtil.getAllRecord(this));
        cView.setChart_name(getString(R.string.chart3_title));
        cView.setChart_x_label(getString(R.string.chart3_x));
        cView.setChart_y_label(getString(R.string.chart3_y));
        cView.setChart_plot(getString(R.string.chart3_plot));
        cView.setChart_type(2);
        
        layout.addView(cView);
	}
	
	/**
	 * リッター価格チャート
	 */
	private void loadPPUChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        // ビューの作成
        FuelUsageAnalyzerChartView cView = new FuelUsageAnalyzerChartView(this);
        // ログビリスト取得
        cView.setLogList(FuelUsageAnalyzerUtil.getAllRecord(this));
        cView.setChart_name(getString(R.string.chart4_title));
        cView.setChart_x_label(getString(R.string.chart4_x));
        cView.setChart_y_label(getString(R.string.chart4_y));
        cView.setChart_plot(getString(R.string.chart4_plot));
        cView.setChart_type(3);
        
        layout.addView(cView);
	}
	
	/**
	 * 燃費チャート
	 */
	private void loadFEChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        // ビューの作成
        FuelUsageAnalyzerChartView cView = new FuelUsageAnalyzerChartView(this);
        // ログビリスト取得
        cView.setLogList(FuelUsageAnalyzerUtil.getAllRecord(this));
        cView.setChart_name(getString(R.string.chart5_title));
        cView.setChart_x_label(getString(R.string.chart5_x));
        cView.setChart_y_label(getString(R.string.chart5_y));
        cView.setChart_plot(getString(R.string.chart5_plot));
        cView.setChart_type(4);
        
        layout.addView(cView);
	}
	
	/**
	 * adMobビューを追加する
	 * @param layout
	 */
	private void addAdView(LinearLayout layout) {
        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-4280826531916194/7080368700");
        adView.setAdSize(AdSize.SMART_BANNER);
        layout.addView(adView);
		adView.loadAd(new AdRequest.Builder().build());
	}
}