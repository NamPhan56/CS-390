package edu.umass.cs.client.widget;

import java.util.Calendar;
import java.util.LinkedList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
//import edu.umass.cs.client.R;
import com.example.camera.R;
import edu.umass.cs.client.widget.ContextImageWidget.RollingHistoryView;


/**
 * ContextImageWidget.java
 * @author musthag
 */
public class ContextGraphWidget extends WidgetBase
{
	private static final String LOG_TAG = "ContextWidget";
	protected static final int CONTAINER_ID=35;
	private static final int IMAGE_ID=40;
	protected static final int HISTORY_ID=50;
    
	private RelativeLayout container;
	private ImageView image_view;
	
	
	private String value;
	
	//********************************************
	//public LinkedList<Integer> history_view;
	//private GraphViewSeries exampleSeries;
	private GraphView graphView;
	private Calendar c = Calendar.getInstance(); 
	//********************************************
	
	
	public ContextGraphWidget(Context context,int numStates, LinkedList<Integer> history) {
		super(context);
    	drawGraph();
    }
	//********************************************
	public void update(LinkedList<Integer> count_history, LinkedList<Float> calorie_history){
		GraphViewData[] graphViewData = new GraphViewData[count_history.size()];
		Integer[] countHistory = count_history.toArray(new Integer[count_history.size()]);
		Float[] calorieHistory = calorie_history.toArray(new Float[count_history.size()]);
		for (int i = 0; i < count_history.size(); i++){

		     graphViewData[i] = new GraphViewData(calorieHistory[i].doubleValue(), countHistory[i].doubleValue());
		     }

		GraphViewSeries series = new GraphViewSeries(graphViewData);
		graphView.addSeries(series);
		invalidate();
	}
	//********************************************
	protected void drawGraph(){
    	container = new RelativeLayout(context);
    	container.setId(CONTAINER_ID);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		body.addView(container,params); // add to view
		
		
		RelativeLayout.LayoutParams graphParams = 
                new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    250);
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	            new GraphViewData(0, 0.0)
	        });
		
		//history_view = new LinkedList<Integer>();
		//history_view.add(0);
	         
	        graphView = new LineGraphView(
	            context // context
	            , "" // heading
	        );
	        graphView.addSeries(exampleSeries); // data
	        graphView.setId(1);
	        container.addView(graphView,graphParams);
	        
	        //setContentView(myLayout);
    }

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void dbUpdateValue() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loadFromDB(int field_id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addOrRemoveTitleViewAsNecessary(){
    	if (title != null && title.length() > 0) drawTitle();
    	else {
    		Log.v(LOG_TAG,"removing the title label");
    		this.removeView(title_view);
    		title_view = null;
    	}
    }
	@Override
	public void addOrRemoveDescriptionViewAsNecessary() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void drawTitle(){
    	super.drawTitle();
    	this.removeView(title_view);
    	container.removeView(title_view);
    	title_view.setPadding(0, 10, 10, 10);
    	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
    			ViewGroup.LayoutParams.WRAP_CONTENT,
    			ViewGroup.LayoutParams.WRAP_CONTENT);
    	params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	params.addRule(RelativeLayout.LEFT_OF,IMAGE_ID);
    	container.addView(title_view, params);
    }
    
	
	
    	
    
}