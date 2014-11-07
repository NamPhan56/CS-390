import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class main {
	// init example series data
	GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	    new GraphViewData(1, 2.0d)
	    , new GraphViewData(2, 1.5d)
	    , new GraphViewData(3, 2.5d)
	    , new GraphViewData(4, 1.0d)
	});
	// context, // heading
	GraphView graphView = new LineGraphView(this,"GraphViewDemo" );
	graphView.addSeries(exampleSeries); // data
	 
	LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	layout.addView(graphView);
}
