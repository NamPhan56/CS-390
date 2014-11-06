package edu.umass.cs.client;

import java.util.ArrayList;

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
import edu.umass.cs.accelerometer.ActivityClassifier;
import edu.umass.cs.accelerometer.ActivityFeatureExtractor;
import edu.umass.cs.accelerometer.Filter;
import edu.umass.cs.accelerometer.ReorientAxis;

/**
 * 
 * Context_Service: This is a sample class to reads sensor data (accelerometer). 
 * 
 * @author CS390MB
 * 
 */
public class Context_Service extends Service implements SensorEventListener{

	/**
	 * Notification manager to display notifications
	 */
	private NotificationManager nm;

	/**
	 * SensorManager
	 */
	private SensorManager mSensorManager;
	/**
	 * Accelerometer Sensor
	 */
	private Sensor mAccelerometer;

	/**
	 * Class to orient axis
	 */
	private ReorientAxis orienter = null;
	/**
	 * Feature extractor
	 */
	private ActivityFeatureExtractor extractor = null;

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

	static Context_Service sInstance = null;
	private static boolean isRunning = false;
	private static boolean isAccelRunning = false;
	private static final int NOTIFICATION_ID = 777;

	private final int changeSize = 15 ; // pref: 30
	private ArrayList<Double> changeList = new ArrayList<Double>();
	private double last = 20;

	/**
	 * Filter class required to filter noise from accelerometer
	 */

	private Filter filter = null;
	/**
	 * Step count to be displayed in UI
	 */
	private int stepCount = 0;
	private double doubCount = 0.0;


	// activity classifier

	private String activity = "";
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
				filter = new Filter(1);
				//OR Use Butterworth filter from mcrowdviz
				//double CUTOFF_FREQUENCY = 0.3;
				//filter = new Filter(CUTOFF_FREQUENCY);
				stepCount = 0;
				doubCount = 0;
				changeList = new ArrayList<Double>();
				//Set up orienter 
				orienter = new ReorientAxis(); 
				//long WINDOW_IN_MILLISECONDS = 5000; //5seconds
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
				changeList = null;
				orienter = null; 
				extractor = null;
				break;
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



	private void sendUpdatedStepCountToUI() {
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				// send step count
				Message msg = Message.obtain(null, MSG_STEP_COUNTER,stepCount,0);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
				mClients.remove(i);
			}
		}
	}

	//for P2B

	private void sendUpdatedActivityToUI(){
		for (int i=mClients.size()-1; i>=0; i-- ) {
			try {
				//Send Step Count
				Message msg = Message.obtain(null, MSG_STEP_COUNTER,stepCount,0);
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
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float accel[] = event.values;
			sendAccelValuesToUI(accel[0], accel[1], accel[2]);

			long time = event.timestamp/1000000; //convert time to milliseconds from nanoseconds

			//Feature vector is not null only when it has buffered
			//at least 5 seconds of data

			/**
			 * TODO: Step Detection
			 */
			//First, Get filtered values
			double filtAcc[] = filter.getFilteredValues(accel[0], accel[1], accel[2]);
			//Now, increment 'stepCount' variable if you detect any steps here

			doubCount += detectSteps(filtAcc[0], filtAcc[1], filtAcc[2]); 
			stepCount = (int)doubCount;
			sendUpdatedStepCountToUI();
			//detectSteps() is not implemented
			//Orient accelerometer
			double ortAcc[] = orienter.getReorientedValues(accel[0], accel[1], accel[2]);

			//Extract Features now 
			Double features[] = extractor.extractFeatures(time, ortAcc[0], ortAcc[1],ortAcc[2], accel[0], accel[1], accel[2]);

			if(features!=null) {
				//Classify 
				try{
					double classId = ActivityClassifier.classify(features); // walking = 0.0 , stationary = 1.0, driving = 2.0

					//TODO: 1. The activity labels below will depend on activities in your data set
					//String activity = null;
					if(classId == 0.0) activity= "walking";
					else if(classId == 1.0) activity = "stationary";
					else if(classId == 2.0) activity = "driving";
					sendUpdatedActivityToUI();
					//TODO: 2. Send new activity label to UI
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
	public double detectSteps(double filt_acc_x, double filt_acc_y, double filt_acc_z) {

		double x = filt_acc_x;
		double y = filt_acc_y;
		double z = filt_acc_z;		
		double ret = 0;
		double th = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2) + Math.pow(z, 2));
		double maxThreshold = 0.60; // 0.621
		double minThreshold = 0.54; // 0.51
		int size = changeList.size();
		if(size < changeSize){
			changeList.add(new Double(th));
		}
		else{
			changeList.remove(0);
			changeList.add(new Double(th));
		}

		Double aveChange = 0.0;

		for(Double a : changeList){
			aveChange += a;
		}
		aveChange /= changeList.size(); // takes the average of x accelerations

		double change = Math.abs(th - aveChange);

		if (change > minThreshold && change <= maxThreshold){
			ret = 0.5;
		}
		//last = th;
		return ret;

	}

	private double domAx(double x, double y, double z){
		if(Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)){
			return x;
		}
		else if(Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)){
			return y;
		}
		else if(Math.abs(z) > Math.abs(x) && Math.abs(z) > Math.abs(y)){
			return z;
		}
		else{
			return x;
		}

	}

}
