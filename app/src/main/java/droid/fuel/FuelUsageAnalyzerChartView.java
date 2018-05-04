package droid.fuel;

import java.util.ArrayList;
import java.util.List;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.geom.RectShape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * FuelUsageAnalyzerChartView
 * @author yasupong
 */
public class FuelUsageAnalyzerChartView extends View {
	
	private List<FuelUsageRecordEntity> recordList = null;
	private String chart_name = null;
	private String chart_x_label = null;
	private String chart_y_label = null;
	private String chart_plot = null;
	private int chart_type = 0;
	
	/**
	 * コンストラクター
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FuelUsageAnalyzerChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * コンストラクター
	 * @param context
	 * @param attrs
	 */
	public FuelUsageAnalyzerChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * コンストラクター
	 * @param context
	 */
	public FuelUsageAnalyzerChartView(Context context) {
		super(context);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
        RectShape chartArea = new RectShape(0,0,canvas.getWidth(),400);
        
        DefaultCategoryDataset data = new DefaultCategoryDataset();
    	
    	if (chart_type == 0) {
    		getDifChartPlot(data);
    	}
    	else if (chart_type == 1) {
    		getAmountChartPlot(data);
    	}
    	else if (chart_type == 2) {
    		getPriceChartPlot(data);
    	}
    	else if (chart_type == 3) {
    		getPPUChartPlot(data);
    	}
    	else if (chart_type == 4) {
    		getFEChartPlot(data);
    	}
    	
        AFreeChart chart = ChartFactory.createBarChart(chart_name,chart_x_label,chart_y_label,data,PlotOrientation.VERTICAL,true,false,false);
        
        // 整数だけにする
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        chart.draw(canvas, chartArea);
	}

	/**
	 * 走行距離
	 * @param dataSet
	 */
	private void getDifChartPlot(DefaultCategoryDataset dataSet) {
		
		List<String> dataList = new ArrayList<String>();
		List<String> wkDateList = new ArrayList<String>();
		
		String firstDate = null;
		String firstOdo = "0";
		
		for (FuelUsageRecordEntity data : recordList) {
			String currentDate = data.getDate().substring(5, 10);
			
			String diff = String.valueOf(Math.abs(Double.parseDouble(data.getOdo()) - Double.parseDouble(firstOdo)));
			
			if (firstDate == null) {
				firstDate = currentDate;
				firstOdo = data.getOdo();
			} else if (!currentDate.equals(firstDate)) {
				dataList.add(diff);
				wkDateList.add(firstDate);
				firstDate = currentDate;
				firstOdo = data.getOdo();
			} else {
				dataList.add(diff);
				wkDateList.add(firstDate);
			}
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			dataSet.addValue(Double.parseDouble(dataList.get(i)), chart_plot, wkDateList.get(i));
		}
	}
	
	/**
	 * 給油量
	 * @param dataSet
	 */
	private void getAmountChartPlot(DefaultCategoryDataset dataSet) {
		
		List<String> dataList = new ArrayList<String>();
		List<String> wkDateList = new ArrayList<String>();
		
		String firstDate = null;
		double amount = 0;
		
		for (FuelUsageRecordEntity data : recordList) {
			String currentDate = data.getDate().substring(5, 10);
			
			amount = amount + Double.parseDouble(data.getAmount());
			
			if (firstDate == null) {
				firstDate = currentDate;
				amount = 0;
			} else if (!currentDate.equals(firstDate)) {
				dataList.add(String.valueOf(amount));
				wkDateList.add(firstDate);
				firstDate = currentDate;
				amount = 0;
			} else {
				dataList.add(String.valueOf(amount));
				wkDateList.add(firstDate);
			}
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			dataSet.addValue(Double.parseDouble(dataList.get(i)), chart_plot, wkDateList.get(i));
		}
	}

	/**
	 * 給油価格
	 * @param dataSet
	 */
	private void getPriceChartPlot(DefaultCategoryDataset dataSet) {
		
		List<String> dataList = new ArrayList<String>();
		List<String> wkDateList = new ArrayList<String>();
		
		String firstDate = null;
		double price = 0;
		
		for (FuelUsageRecordEntity data : recordList) {
			String currentDate = data.getDate().substring(5, 10);
			
			price = price + Double.parseDouble(data.getPrice());
			
			if (firstDate == null) {
				firstDate = currentDate;
				price = 0;
			} else if (!currentDate.equals(firstDate)) {
				dataList.add(String.valueOf(price));
				wkDateList.add(firstDate);
				firstDate = currentDate;
				price = 0;
			} else {
				dataList.add(String.valueOf(price));
				wkDateList.add(firstDate);
			}
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			dataSet.addValue(Double.parseDouble(dataList.get(i)), chart_plot, wkDateList.get(i));
		}
	}
	
	/**
	 * リッター価格
	 * @param dataSet
	 */
	private void getPPUChartPlot(DefaultCategoryDataset dataSet) {
		
		List<String> dataList = new ArrayList<String>();
		List<String> wkDateList = new ArrayList<String>();
		
		String firstDate = null;
		double perPrice = 0;
		int dateCount = 0;
		
		for (FuelUsageRecordEntity data : recordList) {
			String currentDate = data.getDate().substring(5, 10);
			
			perPrice = perPrice + Double.parseDouble(data.getPerprice());
			dateCount++;
			
			if (firstDate == null) {
				firstDate = currentDate;
				perPrice = 0;
				dateCount = 0;
			} else if (!currentDate.equals(firstDate)) {
				dataList.add(String.valueOf(perPrice/dateCount));
				wkDateList.add(firstDate);
				firstDate = currentDate;
				perPrice = 0;
				dateCount = 0;
			} else {
				dataList.add(String.valueOf(perPrice/dateCount));
				wkDateList.add(firstDate);
			}
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			dataSet.addValue(Double.parseDouble(dataList.get(i)), chart_plot, wkDateList.get(i));
		}
	}
	
	/**
	 * 燃費
	 * @param dataSet
	 */
	private void getFEChartPlot(DefaultCategoryDataset dataSet) {
		
		List<String> dataList = new ArrayList<String>();
		List<String> wkDateList = new ArrayList<String>();
		
		String firstDate = null;
		double amount = 0;
		String firstOdo = "0";
		
		for (FuelUsageRecordEntity data : recordList) {
			String currentDate = data.getDate().substring(5, 10);
			
			double diff = Math.abs(Double.parseDouble(data.getOdo()) - Double.parseDouble(firstOdo));
			amount = amount + Double.parseDouble(data.getAmount());
			
			if (firstDate == null) {
				firstDate = currentDate;
				amount = 0;
				firstOdo = data.getOdo();
			} else if (!currentDate.equals(firstDate)) {
				dataList.add(String.valueOf(diff / amount));
				wkDateList.add(firstDate);
				firstDate = currentDate;
				amount = 0;
				firstOdo = data.getOdo();
			} else {
				dataList.add(String.valueOf(diff / amount));
				wkDateList.add(firstDate);
			}
		}
		
		for (int i = 0; i < dataList.size(); i++) {
			dataSet.addValue(Double.parseDouble(dataList.get(i)), chart_plot, wkDateList.get(i));
		}
	}
	
	/**
	 * ログリスト取得
	 * @param logList
	 */
	public void setLogList(List<FuelUsageRecordEntity> logList) {
		this.recordList = logList;
	}

	/**
	 * @param chart_name the chart_name to set
	 */
	public void setChart_name(String chart_name) {
		this.chart_name = chart_name;
	}

	/**
	 * @param chart_x_label the chart_x_label to set
	 */
	public void setChart_x_label(String chart_x_label) {
		this.chart_x_label = chart_x_label;
	}

	/**
	 * @param chart_y_label the chart_y_label to set
	 */
	public void setChart_y_label(String chart_y_label) {
		this.chart_y_label = chart_y_label;
	}

	/**
	 * @param chart_plot the chart_plot to set
	 */
	public void setChart_plot(String chart_plot) {
		this.chart_plot = chart_plot;
	}

	/**
	 * @param chart_type the chart_type to set
	 */
	public void setChart_type(int chart_type) {
		this.chart_type = chart_type;
	}
}
