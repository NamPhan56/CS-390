/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.umass.cs.client;

import java.util.LinkedList;
import java.util.List;

import edu.umass.cs.client.widget.ContextImageWidget;
import edu.umass.cs.client.widget.WidgetBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ContextActivity extends ListActivity {
	
    public static enum STREAMS {ACTIVITY
//									,VOICE
//									,ACT1
//									,ACT2
//									,ACT3
// ,STEPS
		};
    private WidgetBase[] widgets = new WidgetBase[STREAMS.values().length]; // Using a base-class makes it easier to serve different widgets in the listview
    
    private static final String TAG = "ContextActivity";
	
	
	// Datasource for the listview
    private ContextAdapter adapter;
    
    // Messenger service for exchanging messages with the background service
    private Messenger mService = null;
    
    // Variable indicating if this activity is connected to the service
    private boolean mIsBound;
    //   Messenger receiving messages from the background service to update UI
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    
    private ServiceConnection mConnection; //TODO:: refer to MainActivity.java for the communication protocol with the background service
    // TODO:: when a connection is established subscribe to updates


    
    @SuppressLint("HandlerLeak")
	class IncomingHandler extends Handler {
	@Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Context_Service.MSG_ACTIVITY_STATUS:
            {
            	Log.d(TAG,"got message");
            	String activity = msg.getData().getString("activity");
		int state = getStateFromActivityString(activity);
		if (widgets[0] !=null){
		    ((ContextImageWidget)widgets[0]).history_view.add(state);
		    ((ContextImageWidget)widgets[0]).setImage(state);
		}
            	break;
            }
            default:
                super.handleMessage(msg);
            }
        }
    }
    
    private int getStateFromActivityString(String label){
    	if(label.equals("STATIONARY"))
    		return 0;
    	else if(label.equals("WALKING"))
    		return 1;
    	else if(label.equals("DRIVE"))
    		return 2;
    	return -1;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(null);
        
	//        View view = this.getWindow().getDecorView();
        //view.setBackgroundColor(Color.WHITE);
    }
    
    
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        //TODO:: Bind to the service if it is not already running

        
        if(Context_Service.selected.size() > 0){
        	if (adapter ==null){
        		adapter = new ContextAdapter();
        		setListAdapter(adapter);
        	}
            drawWidgets();
        } else { // unset the listadapter so android doesn't draw a zero item list which throws an error
	        widgets = new WidgetBase[STREAMS.values().length];
        	setListAdapter(null);
        	adapter = null;
        }
        
    }
    
    private void drawWidgets(){
    	List<Integer> selected = Context_Service.selected;
    	for(int i : selected){
    		switch(STREAMS.values()[i]){
    			case ACTIVITY:
    			    if (Context_Service.raw_activity_history == null) 
    			   		Context_Service.raw_activity_history = new LinkedList<Integer>();
    			   	widgets[i] = new ContextImageWidget(this,2,Context_Service.raw_activity_history);
    			   	widgets[i].setTitle("Raw Activity: ");
    			   	widgets[i].addOrRemoveTitleViewAsNecessary();
    				break;
//    			case VOICE:
//    				//TODO::
//    				break;
//    			case ACT1:
//    			case ACT2:
//    			case ACT3:
//    				//TODO::
//    				break;

    		}
    	}
    }

    @Override
    public void onPause() {
	//TODO:: unregister updated and unbind from service
        super.onPause();
    }    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

    }
    
    private class ContextAdapter extends BaseAdapter {

    	/****/
        @Override
        public int getViewTypeCount(){
	    return Context_Service.selected.size(); //forcing to create a new view for each position
        }
 
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        
        @Override
        public int getCount() {
            return Context_Service.selected.size();
        }
 
        @Override
        public String getItem(int position) {
            return "";
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v(TAG,"getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = newView(position, getActivity());
            } 
            return convertView;
        }
        
        public View newView(int position, Context context) {
	    return widgets[position];
        }
    }
    private Activity getActivity(){
    	return this;
    }
}