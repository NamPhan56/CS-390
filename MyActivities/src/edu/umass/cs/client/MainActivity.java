package edu.umass.cs.client;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.umass.cs.client.ContextActivity.STREAMS;
import edu.umass.cs.client.widget.ContextImageWidget;
import android.widget.ImageView;

/**
 * <p>The first activity/UI visible upon launching the application
 * 
 * @author CS390MB
 * 
 */
public class MainActivity extends Activity {
    
	
	/** 
	 * Variable to check if accelerometer is running
	 */
	private boolean accelStarted = false;
	
	/** 
	 * Variable to check if microphone is running
	 */
	private boolean microStarted = false;
	
	/**
	 * Instance of this activity
	 */
	private MainActivity activity;
	
	
	/*
	 * Various UI components 
	 */
	private TextView accelXView, accelYView, accelZView, Vis1View;
	private TextView statusView, stepsView, statusSpeechView;
	private CompoundButton accelButton;
	private CompoundButton microButton;
	private ImageView activityView;
	private Button vizButton;
	/**
	 * Messenger service for exchanging messages with the background service
	 */
	private Messenger mService = null;
    /**
     * Variable indicating if this activity is connected to the service
     */
	private boolean mIsBound;
    /**
     * Messenger receiving messages from the background service to update UI
     */
	private final Messenger mMessenger = new Messenger(new IncomingHandler());
    
    /**
     * Handler to handle incoming messages
     */
    @SuppressLint("HandlerLeak")
	class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case Context_Service.MSG_ACTIVITY_STATUS:
            {
            	String activity = msg.getData().getString("activity");
            	setImage(activity);
            	break;
            }
            case Context_Service.MSG_STEP_COUNTER:
            {
            	stepsView.setText(""+msg.arg1);
            	break;
            }
            case Context_Service.MSG_ACCEL_VALUES:
            {
            	float accX = msg.getData().getFloat("accx");
            	float accY = msg.getData().getFloat("accy");
            	float accZ = msg.getData().getFloat("accz");
            	activity.setAccelValues(accX,accY,accZ);
            	break;
            }
            case Context_Service.MSG_ACCELEROMETER_STARTED:
            {
            	if(accelButton!=null) {
            		accelButton.setChecked(true);
            		accelStarted = true;
            		statusView.setText("Accelerometer Started");
            	}
            	break;
            }
            case Context_Service.MSG_ACCELEROMETER_STOPPED:
            {
            	if(accelButton!=null) {
            		accelButton.setChecked(false);
            		accelStarted = false;
            		statusView.setText("Accelerometer Stopped");
            	}
            	break;
            }
            case Context_Service.MSG_MICROPHONE_STARTED:
            {
            	if(microButton!=null) {
            		microButton.setChecked(true);
            		microStarted = true;
            		statusView.setText("Microphone Started");
            	}
            	break;
            }
            case Context_Service.MSG_MICROPHONE_STOPPED:
            {
            	if(microButton!=null) {
            		microButton.setChecked(false);
            		microStarted = false;
            		statusView.setText("Microphone Stopped");
            	}
            	break;
            }
            case Context_Service.MSG_SPEECH_STATUS:
            {
            	Log.d("","got message");
            	float speech = msg.getData().getFloat("speech");
            	Log.d("speech:","" + speech);
            	if(speech == (float)0){
            	statusView.setText("No Speech");
            	}
            	else{
            		statusView.setText("Speech");
            	}
            	break;
            }
            default:
                super.handleMessage(msg);
            }
        }
    }
    
    /**
     * Connection with the service
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            statusView.setText("Attached to the Service");
            mIsBound = true;
            try {
                Message msg = Message.obtain(null, Context_Service.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even do anything with it
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
        	mIsBound = false;
            mService = null;
            statusView.setText("Disconnected from the Service");
        }
    };
	
    /* Invoked when an activity is created
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	activity = this;
    	super.onCreate(savedInstanceState);
        //Set Layout
        setContentView(R.layout.main);
        
        //Setting up text views
        statusView = (TextView) findViewById(R.id.StatusView);
        statusSpeechView = (TextView) findViewById(R.id.StatusSpeechView);
        stepsView = (TextView) findViewById(R.id.StepCountView);
        activityView = (ImageView) findViewById(R.id.ActivityImageView);
        accelXView = (TextView) findViewById(R.id.AccelXView);
        accelYView = (TextView) findViewById(R.id.AccelYView);
        accelZView = (TextView) findViewById(R.id.AccelZView);
        statusView.setText("Service Not Bound");
        
        
        vizButton = (Button)findViewById(R.id.VizualizeButton);
        
        //Start Background Service if not already started
        if(!Context_Service.isRunning()) {
        	Intent cssBg = new Intent(activity,Context_Service.class);
    		startService(cssBg);
        }

        
        //Bind to the service if it is already running
        bindToServiceIfIsRunning();
        
        //Determine if the accelerometer is on
        accelStarted = false;
        if(Context_Service.isAccelerometerRunning())
        	accelStarted = true;
        
        //Set the buttons and the text accordingly
        accelButton = (ToggleButton) findViewById(R.id.StartButton);
        accelButton.setChecked(accelStarted);
        accelButton.setOnCheckedChangeListener(
        		new OnCheckedChangeListener() {
        		    public void onCheckedChanged(CompoundButton btn,boolean isChecked) {
        		    	accelStarted = Context_Service.isAccelerometerRunning();
        		    	if(!accelStarted)
        		    		startAccelerometer();
        		    	else
        		    		stopAccelerometer();
        		    	//accelStarted = !accelStarted;
        		    }
        		}
        );
        
      //Determine if the microphone is on
        microStarted = false;
        if(Context_Service.isMicrophoneRunning())
        	microStarted = true;
        
        
        //Set the buttons and the text accordingly
        microButton = (ToggleButton) findViewById(R.id.StartButton2);
        microButton.setChecked(microStarted);
        microButton.setOnCheckedChangeListener(
        		new OnCheckedChangeListener() {
        		    public void onCheckedChanged(CompoundButton btn,boolean isChecked) {
        		    	microStarted = Context_Service.isMicrophoneRunning();
        		    	if(!microStarted)
        		    		startMicrophone();
        		    	else
        		    		stopMicrophone();
        		    }
        		}
        );
        
      //Determine if the microphone is on TODO: add microstarted to contextservice
        
        vizButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ContextActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
        
    }
        
       
    
    /**
     * Binds this activity to the service if the service is already running
     */
    private void bindToServiceIfIsRunning() {
        //If the service is running when the activity starts, we want to automatically bind to it.
        if (Context_Service.isRunning()) {
            doBindService();//
            statusView.setText("Request to bind service");
        }
    }
	
	
    /**
     * This method is required to send a request to the background service.
     * In current application, we are not sending any message yet.
     * @param message
     */
    private void sendMessageToService(int message) {
        if (mIsBound) {
            if (mService != null) {
            	try {
                    Message msg = Message.obtain(null, message);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                }
            }
        }
    }
    
    /**
     * Display accelerometer values in UI
     * @param accX
     * @param accY
     * @param accZ
     */
    public void setAccelValues(float accX, float accY, float accZ) {
    	String text = String.format("%2.2f", accX);
    	accelXView.setText(text);
    	text = String.format("%2.2f", accY);
    	accelYView.setText(text);
    	text = String.format("%2.2f", accZ);
    	accelZView.setText(text);
    }
    
    public void setImage(String label){
    	ImageView image = activityView;
    	if(label.equals("stationary"))
    		image.setImageResource(R.drawable.stat);
    	else if(label.equals("walking"))
    		image.setImageResource(R.drawable.walking);
    	else if(label.equals("jumping"))
    		image.setImageResource(R.drawable.jumping);
    }
    
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	try {
            doUnbindService();
        } catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }
    
    /**
     * Binds the activity to the background service
     */
    void doBindService() {
        bindService(new Intent(this, Context_Service.class), mConnection, Context.BIND_AUTO_CREATE);
        statusView.setText("Binding to Service");
    }
    
    /**
     * Unbind this activity from the background service
     */
    void doUnbindService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null, Context_Service.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service has crashed.
                }
            }
            // Detach our existing connection.
            unbindService(mConnection);
            statusView.setText("Unbinding from Service");
        }
    }
    
    /**
     * Sends Accelerometer Start Request
     */
    private void startAccelerometer() {
    	if(!mIsBound) {
    		doBindService();
    		//In this case, start accelerometer won't work because service is not bound
    		accelButton.setChecked(false);
    	}
    	if(mIsBound) {
    		sendMessageToService(Context_Service.MSG_START_ACCELEROMETER);
    	}
    }
    
    /**
     * Sends Accelerometer Stop Request
     */
    private void stopAccelerometer() {
    	if(!mIsBound) {
    		doBindService();
    	}
    	if(mIsBound) {
    		sendMessageToService(Context_Service.MSG_STOP_ACCELEROMETER);
    	}
    }

/**
 * Sends Microphone Start Request
 */
private void startMicrophone() {
	if(!mIsBound) {
		doBindService();
		//In this case, start microphone won't work because service is not bound
		microButton.setChecked(false);
	}
	if(mIsBound) {
		sendMessageToService(Context_Service.MSG_START_MICROPHONE);
	}
}

/**
 * Sends Accelerometer Stop Request
 */
private void stopMicrophone() {
	if(!mIsBound) {
		doBindService();
	}
	if(mIsBound) {
		sendMessageToService(Context_Service.MSG_STOP_MICROPHONE);
	}
}
}