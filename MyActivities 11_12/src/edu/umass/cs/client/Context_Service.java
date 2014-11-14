package edu.umass.cs.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import edu.umass.cs.accelerometer.ActivityFeatureExtractor;
import edu.umass.cs.accelerometer.Filter;
import edu.umass.cs.accelerometer.ReorientAxis;
import edu.umass.cs.voice.FeatureExtractor;
import edu.umass.cs.voice.MicrophoneRecorder;
import edu.umass.cs.voice.MicrophoneRecorder.MicrophoneListener;

/**
 * 
 * Context_Service: This is a sample class to reads sensor data (accelerometer). 
 * 
 * @author CS390MB
 * 
 */
public class Context_Service extends Service implements SensorEventListener{
	
	/**
	* Class to orient axis
	*/
	private ReorientAxis orienter = null;
	/**
	* Feature extractor
	*/
	private ActivityFeatureExtractor extractor = null;

	/**
	 * Notification manager to display notifications
	 */
	private NotificationManager nm;
	
	public static LinkedList<Integer> raw_activity_history = new LinkedList<Integer>();
	public static LinkedList<Integer> raw_voice_history = new LinkedList<Integer>();
	public static LinkedList<Float> accx_history = new LinkedList<Float>();
	public static LinkedList<Float> accy_history = new LinkedList<Float>();
	public static LinkedList<Float> accz_history = new LinkedList<Float>();
	//********************************************************************************
	public static LinkedList<Float> speed_history = new LinkedList<Float>();
	public static LinkedList<Float> speech_history = new LinkedList<Float>();
	public static LinkedList<Float> step_history = new LinkedList<Float>();
	public static LinkedList<Float> calorie_history = new LinkedList<Float>();
	public static LinkedList<Integer> calorie_graph_history = new LinkedList<Integer>();
	//********************************************************************************
	public static List<Integer> selected = new ArrayList<Integer>();

	
	/**
	 * SensorManager
	 */
	private SensorManager mSensorManager;
    /**
     * Accelerometer Sensor
     */
    private Sensor mAccelerometer;
    /**
     * Microphone Sensor
     */
    private MicrophoneRecorder mMicrophoneManager;
    private MicrophoneListener mMicrophone;
    

	//List of bound clients/activities to this service
	ArrayList<Messenger> mClients = new ArrayList<Messenger>();

	//Message codes sent and received by the service
	static final int MSG_REGISTER_CLIENT = 1;
	static final int MSG_UNREGISTER_CLIENT = 2;
	static final int MSG_ACTIVITY_STATUS = 3;
	static final int MSG_STEP_COUNTER = 4;
	static final int MSG_ACCEL_VALUES = 5;
	static final int MSG_START_ACCELEROMETER = 6;
	static final int MSG_STOP_ACCELEROMETER = 7;
	static final int MSG_ACCELEROMETER_STARTED = 8;
	static final int MSG_ACCELEROMETER_STOPPED = 9;
	static final int MSG_START_MICROPHONE = 10;
	static final int MSG_STOP_MICROPHONE = 11;
	static final int MSG_MICROPHONE_STARTED = 12;
	static final int MSG_MICROPHONE_STOPPED = 13;
	static final int MSG_SPEECH_STATUS = 14;
	//********************************************************************************
	static final int MSG_SPEED_VALUES = 15;
	static final int MSG_VIS2_VALUES = 16;
	//********************************************************************************

	static Context_Service sInstance = null;
	private static boolean isRunning = false;
	private static boolean isAccelRunning = false;
	private static boolean isMicrophoneRunning = false;
	private static final int NOTIFICATION_ID = 777;
	
	/**
	 * Filter class required to filter noise from accelerometer
	 */
	private Filter filter = null;
	/**
	 * Step count to be displayed in UI
	 */
	private int stepCount = 0;
	
	//Messenger used by clients
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	/**
	 * Handler to handle incoming messages
	 */
	@SuppressLint("HandlerLeak")
	class IncomingHandler extends Handler { 
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			case MSG_START_ACCELEROMETER:
			{
				isAccelRunning = true;
				mSensorManager.registerListener(sInstance, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
				sendMessageToUI(MSG_ACCELEROMETER_STARTED);
				showNotification();
				//Set up filter
				//Following sets up smoothing filter from mcrowdviz
				//int SMOOTH_FACTOR = 10;
				//filter = new Filter(SMOOTH_FACTOR);
				//OR Use Butterworth filter from mcrowdviz
				double CUTOFF_FREQUENCY = 3.0;
				filter = new Filter(CUTOFF_FREQUENCY);
				stepCount = 0;
				//Set up orienter
				orienter = new ReorientAxis();
				long WINDOW_IN_MILLISECONDS = 5000; //5seconds
				//Set up a feature extractor that extracts features every 5 seconds
				extractor = new ActivityFeatureExtractor(5000);
				break;
			}
			case MSG_STOP_ACCELEROMETER:
			{
				isAccelRunning = false;
				mSensorManager.unregisterListener(sInstance);
				sendMessageToUI(MSG_ACCELEROMETER_STOPPED);
				showNotification();
				//Free filter and step detector
				filter = null;
				//The following needs to be added
				  orienter = null;
				  extractor = null;
				break;
			}
			case MSG_START_MICROPHONE:
			{
				isMicrophoneRunning = true;
				mMicrophoneManager.registerListener(mMicrophone);
				mMicrophoneManager.startRecording();
				sendMessageToUI(MSG_MICROPHONE_STARTED);
				showNotification();	
			}
			case MSG_STOP_MICROPHONE:
			{	
				mMicrophoneManager.stopRecording();
				mMicrophoneManager.unregisterListener(mMicrophone);
				isMicrophoneRunning = false;
				sendMessageToUI(MSG_MICROPHONE_STOPPED);
				showNotification();	
			}
			default:
				super.handleMessage(msg);
			}
		}
	}


	private void sendMessageToUI(int message) {
		for (int i=mClients.size()-1; i>=0; i--) {
			try {
				// Send message value
				mClients.get(i).send(Message.obtain(null, message));
			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	
	private void sendAccelValuesToUI(float accX, float accY, float accZ) {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				
				//Send Accel Values
				Bundle b = new Bundle();
				b.putFloat("accx", accX);
				b.putFloat("accy", accY);
				b.putFloat("accz", accZ);
				Message msg = Message.obtain(null, MSG_ACCEL_VALUES);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	//********************************************************************************
	private void sendSpeedValueToUI(float speed) {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				
				//Send Mean Values
				Bundle b = new Bundle();
				b.putFloat("speed", speed);
				Message msg = Message.obtain(null, MSG_SPEED_VALUES);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	
	private void sendSpeechStatusToUI(int speech) {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				
				//Send Accel Values
				Bundle b = new Bundle();
				b.putFloat("speech", (float)speech);
				Message msg = Message.obtain(null, MSG_SPEECH_STATUS);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	
	private void sendVis2ValueToUI(float mean) {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				
				//Send Mean Values
				Bundle b = new Bundle();
				b.putFloat("mean", mean);
				Message msg = Message.obtain(null, MSG_VIS2_VALUES);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	//********************************************************************************
	private void sendUpdatedStepCountToUI() {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				//Send Step Count
				Bundle b = new Bundle();
				b.putFloat("stepCount", (float)stepCount);
				Message msg = Message.obtain(null, MSG_STEP_COUNTER,stepCount,0);
				msg.setData(b);
				mClients.get(i).send(msg);
				

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	
	private void sendActivityStatusToUI(String activity) {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				
				//Send Activity Status 
				Bundle b = new Bundle();
				b.putString("activity", activity);
				Message msg = Message.obtain(null, MSG_ACTIVITY_STATUS);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}
	

	/**
	 * On Binding, return a binder
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}


	
	

	//Start service automatically if we reboot the phone
	public static class Context_BGReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Intent bootUp = new Intent(context,Context_Service.class);
			context.startService(bootUp);
		}		
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		//Cancel previous notification
		if(nm!=null)
			nm.cancel(NOTIFICATION_ID);
		else
			nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		// The PendingIntent to launch our activity if the user selects this notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

		// Use the commented block of code if your target environment is Android-16 or higher 
		/*Notification notification = new Notification.Builder(this)
		.setContentTitle("Context Service")
		.setContentText("Running").setSmallIcon(R.drawable.icon)
		.setContentIntent(contentIntent)
		.build();
		
		nm.notify(NOTIFICATION_ID, notification); */
		
		//For lower versions of Android, the following code should work
		Notification notification = new Notification();
		notification.icon = R.drawable.icon;
		notification.tickerText = getString(R.string.app_name);
		notification.contentIntent = contentIntent;
        notification.when = System.currentTimeMillis();
        if(isAccelerometerRunning())
        	notification.setLatestEventInfo(getApplicationContext(), getString(R.string.app_name), "Accelerometer Running", contentIntent);
        else
        	notification.setLatestEventInfo(getApplicationContext(), getString(R.string.app_name), "Accelerometer Not Started", contentIntent);
        
        // Send the notification.
        nm.notify(NOTIFICATION_ID, notification);
	}






	/* getInstance() and isRunning() are required by the */
	static Context_Service getInstance(){
		return sInstance;
	}

	protected static boolean isRunning(){
		return isRunning;
	}
	
	protected static boolean isAccelerometerRunning() {
		return isAccelRunning;
	}
	
	protected static boolean isMicrophoneRunning() {
		return isMicrophoneRunning;
	}



	@Override
	public void onCreate() {
		super.onCreate();
		showNotification();
		isRunning = true;
		sInstance = this;
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		nm.cancel(NOTIFICATION_ID); // Cancel the persistent notification.
        isRunning = false;
		//Don't let Context_Service die!
		Intent mobilityIntent = new Intent(this,Context_Service.class);
		startService(mobilityIntent);
	}
	
	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; // run until explicitly stopped.
    }

	
	
	
	

	


	/* (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 */
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}
	
	/* (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
//*******************************************************************************************
	@Override 
	public void onSensorChanged(SensorEvent event) { 
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float accel[] = event.values; 
			sendAccelValuesToUI(accel[0], accel[1], accel[2]); 
			double filtAcc[] = filter.getFilteredValues(accel[0], accel[1], accel[2]); 
			//Now, increment 'stepCount' variable if you detect any steps here 
			stepCount += detectSteps(filtAcc[0], filtAcc[1], filtAcc[2]);
			sendUpdatedStepCountToUI(); 
			long time = event.timestamp/1000000; //convert time to milliseconds from nanoseconds
			  //Orient accelerometer
			  double ortAcc[] =
			    orienter.getReorientedValues(accel[0], accel[1], accel[2]);
			  
			  //Extract Features now
			  Double features[] = extractor.extractFeatures(time, ortAcc[0], ortAcc[1],ortAcc[2], accel[0], accel[1], accel[2]);
			  
			  //Feature vector is not null only when it has buffered
			  //at least 5 seconds of data
			  
			  if(features!=null) {
			    //Classify
			    try{
			      double classId = ActivityClassifier.classify(features);
			  
			      //TODO: 1. The activity labels below will depend on activities in your data set
			      String activity = null;
			      if(classId == 0.0) activity= "walking";
			      else if(classId == 1.0) activity = "stationary";
			      else if(classId == 2.0) activity = "jumping";
			  
			      //TODO: 2. Send new activity label to UI
			      sendActivityStatusToUI(activity);
			      
			    //********************************************************************
			      sendSpeedValueToUI(features[27].floatValue()); //27 is the speed in features[]
			    //********************************************************************
			      
			    }catch(Exception e){
			      e.printStackTrace();
			    }
			  }
			}
		} 
	/**
	 * This should return number of steps detected.
	 * @param filt_acc_x
	 * @param filt_acc_y
	 * @param filt_acc_z
	 * @return
	 */
	
	double previousAccelMagnitude = 0.0; //previous acceleration magnitude holder 
	double currentAccelMagnitude = 0.0; //current acceleration magnitude holder 
	double dynamicThresholdMaximum = 0.0; 
	double dynamicThresholdMinimum = 0.0; 
	double dynamicThreshold = 0.0;
	int sampleCount = 0; //count holder for the size of the sample window
	double magnitudeArray[] = new double [50]; //array that holds the sample values in the sample window
	
	public int detectSteps(double filt_acc_x, double filt_acc_y, double filt_acc_z) { 
		//calculate the current acceleration magnitude of the entire acceleration vector
		previousAccelMagnitude = currentAccelMagnitude; 
		currentAccelMagnitude = Math.sqrt(Math.pow(filt_acc_x, 2) + Math.pow(filt_acc_y, 2) + Math.pow(filt_acc_z, 2));
		
		if (sampleCount < 50) { 
			magnitudeArray[sampleCount] = currentAccelMagnitude; 
			sampleCount++; 
			}
		if (sampleCount == 50) { 
			dynamicThresholdMaximum = magnitudeArray[0]; 
			dynamicThresholdMinimum = magnitudeArray[0]; 
			for (int i=0; i <= 49; i++) { 
				if (magnitudeArray[i] > dynamicThresholdMaximum) { 
					dynamicThresholdMaximum = magnitudeArray[i]; 
					}
				if (magnitudeArray[i] < dynamicThresholdMinimum) { 
					dynamicThresholdMinimum = magnitudeArray[i]; 
					}
				}
			dynamicThreshold = ( (dynamicThresholdMaximum + dynamicThresholdMinimum) / 2 );
			sampleCount = 0; 
			}
		//now, don't count steps if the acceleration change is too low or too high in a sample
		//after that, count steps that cross the dynamic threshold upwards
		if((dynamicThresholdMaximum-dynamicThresholdMinimum>4.0)&&(dynamicThresholdMaximum-dynamicThresholdMinimum<12.0)){
			if(previousAccelMagnitude < dynamicThreshold && currentAccelMagnitude >= dynamicThreshold) { 
				return 1;
				} 
			}
	
	return 0; 
	}
	
	private Object[] result = new Object[12];

    //This method was declared in an interface in MicrophoneRecorder
    public void microphoneBuffer(short[] buffer, int window_size) {

    	//You will break a chunk of one-second-long samples into multiple 25-ms windows. Think about how many of 25-ms windows you will get for a second. 

    	//number of 25-ms-long windows that contains mostly voice in a second
    	int voiced = 0;
    	int speech = 0;

    	//TODO: Fill out appropriate numbers in the following for loop statement 
    	for(int k=0;k<1000;k+=1){ // not sure about those
    		//TODO: replace ‘??’ with appropriate numbers below
    		double[] features = FeatureExtractor.ComputeFeaturesForFrame(buffer,1000,k);
    		try {
    			result=getObjectDoubleArray(features,result);
    			//TODO: classify whether the window is voiced or not 		
    			//If output of the classifier is 0.0d, increment ‘voiced’ variable. 
    			//If output is 1.0d, it is unvoiced. This is assuming that you have the order of classes written in arff file as: “speech{true,false}.” 
    			if(result[k] == "0.0d") { 
    				voiced += 1;
    				}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	//TODO: After you find the number of ‘voiced’ windows, determine whether a-second-long audio mostly contain human voice or not by thresholding. Test out different thresholds and choose whichever works the best. Keep in mind that threshold should be less than the number of 25-ms-long windows that contain in a second. If ‘voiced’ variable is greater than a certain threshold, call sendSpeechStatusToUI() with speech variable=1.
    	if (voiced > 20) {
    		speech = 1;
    	}
    	sendSpeechStatusToUI(speech);
    }

    public static Object[] getObjectDoubleArray(double[] in,Object[] result){
    	for (int i=0;i<in.length;i++){
    		result[i] = new Double(in[i]);
    	}
    	return result;
    }
	
	}
	//***************************************************************************************
