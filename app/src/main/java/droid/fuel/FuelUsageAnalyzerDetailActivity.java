package droid.fuel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


/**
 * 燃料消費記録
 * @author yasupong
 */
public class FuelUsageAnalyzerDetailActivity extends Activity {
	
	/** ブランク */
	private final String STR_BLANK = " ";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);
        
        // リストビューを構築する
        makeRecordStatListView();
    }
    
	/**
	 * リストビューを構築する
	 */
	private void makeRecordStatListView() {
		
        List<String> viewList = new ArrayList<String>();
        List<FuelUsageRecordEntity> resultList = FuelUsageAnalyzerUtil.getAllRecord(this);
        
        if (resultList == null || resultList.size() == 0) {
        	viewList.add("NO DATA");
        }
        else {
        	// 直近のデータを表示
        	FuelUsageRecordEntity latestData = resultList.get(0);
        	viewList.add(getString(R.string.detail_latest_fill_date) + STR_BLANK + latestData.getDate() );
        	viewList.add(getString(R.string.detail_latest_odo) + STR_BLANK + latestData.getOdo());
        	viewList.add(getString(R.string.detail_latest_amount) + STR_BLANK + latestData.getAmount() + STR_BLANK + getString(R.string.detail_unit));
        	viewList.add(getString(R.string.detail_latest_price) + STR_BLANK + latestData.getPrice() + STR_BLANK + getString(R.string.detail_currency));
        	viewList.add(getString(R.string.detail_latest_per_price) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(latestData.getPerprice()) + STR_BLANK + getString(R.string.detail_currency));
        	
        	// 前回データがある場合
        	if (resultList.size() > 1) {
            	FuelUsageRecordEntity beforeData = resultList.get(1);
            	
        		// 前回データからの比較を行う
        		double meterNow = Double.parseDouble(latestData.getOdo());
        		double meterold = Double.parseDouble(beforeData.getOdo());
        		double meterDif = meterNow - meterold;
        		
            	viewList.add(getString(R.string.detail_meter_dif) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(meterDif)));
            	
            	double amountNow = Double.parseDouble(latestData.getAmount());
            	
            	viewList.add(getString(R.string.detail_fuel_economy) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(meterDif / amountNow)));
            	
            	// 全データをなめる
            	double culAmount = 0;
            	double culPrice = 0;
            	double culPPU = 0;
            	
            	for (Iterator<FuelUsageRecordEntity> iterator = resultList.iterator(); iterator.hasNext();) {
					FuelUsageRecordEntity fuelUsageRecordEntity = (FuelUsageRecordEntity) iterator.next();
					
					culAmount = culAmount + Double.parseDouble(fuelUsageRecordEntity.getAmount());
					culPrice = culPrice + Double.parseDouble(fuelUsageRecordEntity.getPrice());
					culPPU = culPPU + Double.parseDouble(fuelUsageRecordEntity.getPerprice()); 
				}
            	
            	FuelUsageRecordEntity oldestData = resultList.get(resultList.size()-1);
        		double meteroldest = Double.parseDouble(oldestData.getOdo());
        		double totalMeterDif = meterNow - meteroldest;
        		
        		// 集計値の表示
            	viewList.add(getString(R.string.detail_total_count) + STR_BLANK + resultList.size());
            	viewList.add(getString(R.string.detail_total_meter_dif) + STR_BLANK + totalMeterDif);
            	viewList.add(getString(R.string.detail_total_amount) + STR_BLANK + culAmount + STR_BLANK + getString(R.string.detail_unit));
            	viewList.add(getString(R.string.detail_total_price) + STR_BLANK + culPrice + STR_BLANK + getString(R.string.detail_currency));
            	
            	// 平均値の表示
        		double amountOldest = Double.parseDouble(oldestData.getAmount());
               	viewList.add(getString(R.string.detail_average_fuel_economy) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(totalMeterDif / (culAmount - amountOldest))));
              	viewList.add(getString(R.string.detail_average_fill_amount) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(culAmount / resultList.size()) + STR_BLANK + getString(R.string.detail_unit)));
              	viewList.add(getString(R.string.detail_average_fill_price) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(culPrice / resultList.size()) + STR_BLANK + getString(R.string.detail_currency)));
              	viewList.add(getString(R.string.detail_average_per_price) + STR_BLANK + FuelUsageAnalyzerUtil.subStr(String.valueOf(culPPU / resultList.size()) + STR_BLANK + getString(R.string.detail_currency)));
        	}
        }
        
        // 統計表示リストを画面に表示
        ListView lv = new ListView(this);
        @SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList.toArray(new String[0]));
        lv.setAdapter(adapter);
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.statLayout);
        layout.removeAllViews();
        
        addAdView(layout);
        
        layout.addView(lv);
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