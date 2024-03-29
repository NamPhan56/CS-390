package com.example.camera;

/*
 * Copyright (C) 2007 The Android Open Source Project
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


/*
 * Reference: http://www.41post.com/3470/programming/android-retrieving-the-camera-preview-as-a-pixel-array
 * https://code.google.com/p/ketai/
 * http://www.javamex.com/tutorials/conversion/decimal_hexadecimal.shtml
 * http://developer.android.com/training/camera/cameradirect.html
 */

//strings.xml, app_name added
//camera_menu.xml under res
//camera permission in manifest x3lines
//change manifest file-> minAPI->from 8 to 9
//changeactivity android:name=".CameraPreview" from mainactivity in manifest.xml

//camera manifest change

//show camera stream on app
//http://stackoverflow.com/questions/11687939/in-android-stream-camera-preview-onto-a-view

//Useful: out of sync: file>refresh and delete file

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;


import edu.umass.cs.client.widget.ContextImageWidget;
import edu.umass.cs.client.widget.ContinuousContextImageWidget;
import edu.umass.cs.client.widget.WidgetBase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
// Need the following import to get access to the app resources, since this
// class is in a sub-package.

// ----------------------------------------------------------------------

public class CameraPreview extends Activity implements OnClickListener{

	private Preview mPreview;
	Camera mCamera;
	int numberOfCameras;
	int cameraCurrentlyLocked;

	boolean isMeasuring=false;
	Button btn_startmeasure;
	TextView txt_status,txt_result;
	static TextView heartbeatView;
	// The first rear facing camera
	int defaultCameraId;

	BufferedWriter bw;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		txt_status=(TextView)findViewById(R.id.txt_status);
		txt_result=(TextView)findViewById(R.id.txt_result);
		heartbeatView = (TextView) findViewById(R.id.heartbeatView);


		//Set the buttons and the text accordingly
		btn_startmeasure = (Button) findViewById(R.id.btn_startmeasure);
		btn_startmeasure.setOnClickListener(this);


		// Create a RelativeLayout container that will hold a SurfaceView,
		// and set it as the content of our activity.
		mPreview = new Preview(this);


		FrameLayout preview = (FrameLayout) findViewById(R.id.cam_preview);
		preview.addView(mPreview);

		// Find the total number of cameras available
		numberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				defaultCameraId = i;
			}
		}

	}

	public static TextView getHeartbeatView(){
		return heartbeatView;
	}

	public void onClick(View v){
		//should change to visualize screen here

		//Start measuring PPG
		if(v==btn_startmeasure){
			Parameters p = mCamera.getParameters();

			if (!isMeasuring){
				txt_status.setText("Measuring...");

				isMeasuring=true;

				//				//Turn on the flash
				//				p.setFlashMode(Parameters.FLASH_MODE_TORCH);	

				// Start the preview
				mCamera.startPreview();
			} 
			else{

				//Turn off the flash
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
			}

			mCamera.setParameters(p);
		}

		//if you tap the camera view on the screen, autofocus the camera
		if(v==mPreview){
			mCamera.autoFocus(null);
			txt_status.setText("Camera Focused");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Open the default i.e. the first rear facing camera.
		mCamera = Camera.open();
		cameraCurrentlyLocked = defaultCameraId;
		mPreview.setCamera(mCamera);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			mPreview.setCamera(null);
			//mPreview.getHolder().removeCallback(mPreview);
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate our menu which can gather user input for switching camera
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.camera_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.switch_cam:
			// check for availability of multiple cameras
			if (numberOfCameras == 1) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(this.getString(R.string.camera_alert))
				.setNeutralButton("Close", null);
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			}

			// OK, we have multiple cameras.
			// Release this camera -> cameraCurrentlyLocked
			if (mCamera != null) {
				mCamera.stopPreview();
				mPreview.setCamera(null);
				mCamera.release();
				mCamera = null;
			}

			// Acquire the next camera and request Preview to reconfigure
			// parameters.
			mCamera = Camera
					.open((cameraCurrentlyLocked + 1) % numberOfCameras);
			cameraCurrentlyLocked = (cameraCurrentlyLocked + 1)
					% numberOfCameras;
			mPreview.switchCamera(mCamera);

			// Start the preview
			//mCamera.startPreview();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	//******************************************************
	// private void drawWidgets(){
	// if (redValue_history == null) 
	// redValue_history = new LinkedList<Float>();
	// widgets[i] = new ContinuousContextImageWidget(this,-20,20,150,redValue_history);
	// widgets[i].setTitle(STREAMS.REDVALUES.toString());
	// widgets[i].addOrRemoveTitleViewAsNecessary();
	// }
	//******************************************************


}


// ----------------------------------------------------------------------

/**
 * A simple wrapper around a Camera and a SurfaceView that renders a centered preview of the Camera
 * to the surface. We need to center the SurfaceView because not all devices have cameras that
 * support preview sizes at the same aspect ratio as the device's display.
 */
class Preview extends ViewGroup implements SurfaceHolder.Callback, PreviewCallback {
	private final String TAG = "Preview";

	SurfaceView mSurfaceView;
	SurfaceHolder mHolder;
	Size mPreviewSize;
	List<Size> mSupportedPreviewSizes;
	Camera mCamera;


	int defaultCameraId;

	//***************************************************
	public static enum STREAMS {REDVALUES
	};
	private WidgetBase[] widgets = new WidgetBase[STREAMS.values().length];
	public static LinkedList<Float> redValue_history = new LinkedList<Float>();
	//***************************************************

	CameraPreview myActivity = (CameraPreview) getContext();


	//Parameters for PPG
	//This variable is responsible for getting and setting the camera settings  
	private Parameters parameters;  
	//this array stores the pixels as hexadecimal for RGB color: eg.    
	private int[] pixels;  
	//crop center 100 pixels
	//private int nCenterPixels=200*200; 
	private int npixels;

	//number of frames to record
	private final int nframe=100; 
	//frame countdown till nframe
	private int frameCount=0;
	//bpm result
	private int result=0;

	private List<Double> redMeans = new ArrayList<Double>();
	private List<Double> buffer = new ArrayList<Double>();
	private long startTime =0; //TODO: bookmark
	
	private long holderTime = 0;
	
	//private ArrayList<Float> time;


	double meanFiltSm, meanFiltBW;

	private boolean readyForAnalysis=false;

	double sum=0;
	int color;
	double red;
	private double mean;

	private boolean filterOn=false;


	private double[] meanreds=new double[nframe];

	//	private double[] meanredsFiltSm=new double[nframe];
	//	private double[] meanredsFiltBW=new double[nframe];

	int pp=0;
	String hexStr="";
	//private int indexFrame=0;

	Preview(Context context) {
		super(context);

		mSurfaceView = new SurfaceView(context);
		addView(mSurfaceView);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void setCamera(Camera camera) {
		mCamera = camera;
		if (mCamera != null) {
			mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
			requestLayout();
		}
	}

	public void switchCamera(Camera camera) {
		setCamera(camera);
		try {
			camera.setPreviewDisplay(mHolder);
		} catch (IOException exception) {
			Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
		}
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);


		requestLayout();

		camera.setParameters(parameters);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// We purposely disregard child measurements because act as a
		// wrapper to a SurfaceView that centers the camera preview instead
		// of stretching it.
		final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		setMeasuredDimension(width, height);

		if (mSupportedPreviewSizes != null) {
			//Choose smallest Preview Size
			//128x96
			mPreviewSize = mSupportedPreviewSizes.get(11);//resolution
			//mPreviewSize= getOptimalPreviewSize(mSupportedPreviewSizes, width, height);

			Log.i("finalwidth: ",""+mPreviewSize.width);
			startTime = (System.currentTimeMillis()/1000);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed && getChildCount() > 0) {
			final View child = getChildAt(0);

			final int width = r - l;
			final int height = b - t;

			int previewWidth = width;
			int previewHeight = height;
			if (mPreviewSize != null) {
				previewWidth = mPreviewSize.width;
				previewHeight = mPreviewSize.height;
			}

			// Center the child SurfaceView within the parent.
			if (width * previewHeight > height * previewWidth) {
				final int scaledChildWidth = previewWidth * height / previewHeight;
				child.layout((width - scaledChildWidth) / 2, 0,
						(width + scaledChildWidth) / 2, height);
			} else {
				final int scaledChildHeight = previewHeight * width / previewWidth;
				child.layout(0, (height - scaledChildHeight) / 2,
						width, (height + scaledChildHeight) / 2);
			}
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.

		if (mCamera == null)
		{
			mCamera = Camera.open();

			//CameraPreview myActivity = (CameraPreview) getContext();
			//setCameraDisplayOrientation(myActivity, CameraInfo.CAMERA_FACING_BACK, mCamera);

		}

		try {
			if (mCamera != null) {
				mCamera.setPreviewDisplay(holder);

				parameters = mCamera.getParameters();  


				//Turn on the flash
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);	

				//mPreviewSize = parameters.getPreviewSize();  
				pixels = new int[mPreviewSize.width * mPreviewSize.height];  

				npixels=pixels.length;

				int dataBufferSize=(int)(mPreviewSize.height*mPreviewSize.width*
						(ImageFormat.getBitsPerPixel(parameters.getPreviewFormat())/8.0));

				pp=dataBufferSize;

				mCamera.addCallbackBuffer(new byte[dataBufferSize]);
				mCamera.addCallbackBuffer(new byte[dataBufferSize]);
				mCamera.addCallbackBuffer(new byte[dataBufferSize]);
				//ADDED AFTER SETTTING THE PREVIEW DISPLAY!!! NOT BEFORE
				//sets the camera callback to be the one defined in this class  
				mCamera.setPreviewCallbackWithBuffer(this);  

				//mCamera.setPreviewCallback(this);

			}
		} catch (IOException exception) {
			Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);

		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		if (mCamera != null) {
			mCamera.stopPreview();
		}
	}

	//	private Size getSmallerPreviewSize(List<Size> sizes){
	//		
	//	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) w / h;
		if (sizes == null) return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
		requestLayout();

		//ADDED: remove this: since we already initialized parameters
		//Camera.Parameters parameters = mCamera.getParameters();
		mCamera.setParameters(parameters);
		//mCamera.startPreview();

		//mCamera.stopPreview();	
		//CameraPreview myActivity = (CameraPreview) getContext();
		//setCameraDisplayOrientation(myActivity, 1, mCamera);
		//mCamera.startPreview();

	}



	public void setCameraDisplayOrientation(Activity activity,
			int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info =
				new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0: degrees = 0; break;
		case Surface.ROTATION_90: degrees = 90; break;
		case Surface.ROTATION_180: degrees = 180; break;
		case Surface.ROTATION_270: degrees = 270; break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public int[] getPixelArray(){
		return pixels;
	}


	//Reference: http://stackoverflow.com/questions/20298699/onpreviewframe-data-image-to-imageview
	//define what the callback should do before rendering a preview frame to the screen
	@Override  
	public void onPreviewFrame(byte[] data, Camera camera) {  
		
		//transforms NV21 pixel data into RGB pixels 
		//********************************************

		//PLEASE PUT ALGORITHM HERE AND UPDATE heartbeatCount AS THE ALGORITHM DETECTS A BEAT


		//Collect color data and store them but now replacing each frame with new frame
		decodeYUV420SP(pixels, data, mPreviewSize.width,  mPreviewSize.height);  
		hexStr=Integer.toHexString(pixels[0]);

		for (int i=0;i<npixels;i++){

			hexStr=Integer.toHexString(pixels[i]);

			color = (int)Long.parseLong(hexStr, 16);
			red = (double)(((color >> 16) & 0xFF));
			//********************************************
			//sends updated red values to graph in UI
			sendUpdatedRedValuestoUI(red);
			//********************************************
			sum+=red;
		}


		mean=(double)(sum/npixels);
		
		//coding begins here
		//needs to make sure we get at least... 5 values into the list
		
		buffer.add(mean);
		
		//Log.i("SUM: ",""+sum);
		//Log.i("MEAN: ",""+mean);

		sum=0;
		//window size
		if(buffer.size() >= 15){
			System.out.println("I have >=10 items!");
			redMeans = findDipsOnly2(buffer,redMeans);
			buffer.clear();
			
//			redMeans = findAllPeaksAndDips(buffer, redMeans);
//			buffer.clear();
//			redMeans = findDipsOnly(redMeans);
			
			System.out.println("Size of dips only: " + redMeans.size());
		}
		else{
			System.out.println("less than 5 items");
		}
		
		
		long endRecordTime = (System.currentTimeMillis()/1000);
		System.out.println("timeDifference: " + (endRecordTime - startTime));
		double heartbeatCount = Math.max(1,redMeans.size());
		System.out.println("heartbeatCount: " + heartbeatCount);
		
		int bpm = 0;
		float time = Math.max(1,(endRecordTime- startTime));
		if(time < 60 && time > 0){
			bpm = (int) (heartbeatCount * (60/(time)));
		}
		else if(time !=0){
			bpm = (int)(heartbeatCount/(time));
		}
		
		//TODO: most of the code will go here!
		CameraPreview.getHeartbeatView().setText(""+bpm);
		//********************************************
		camera.addCallbackBuffer(data);
		
		
		//redMeans = randomRestart(redMeans);
		//redMeans = randomRestart();
		
//				if (frameCount<nframe && frameCount!=-1){	
//					meanreds[frameCount]=mean;
//					frameCount++;
//					Log.d("FILE: ","RECORDING..."+frameCount);
//				}
//				else if(frameCount==-1){
//					Log.d("FILE: ","DONE!");
//				}
//				else{
//					Log.d("FILE: ","CREATED!");
//					generateDATA();
//					frameCount=-1;
//				}
	}  

	/**
	 * random restarting, resets start time and empties the heartbeat list
	 * @return
	 */
	private List<Double> randomRestart(List<Double> ret){
		double ran = (double) (Math.random()*100);
		List<Double> empty = new ArrayList<Double>();
		if(ran >=15){
			System.out.println("Random Restarting!");
			startTime = (System.currentTimeMillis()/1000);	
			return empty;
		}
		return ret;
	}
	
	/**
	 * filters the list for only dips and peaks, array must have at least 3 elements in the list
	 * @param List<Double> unFilteredList
	 * @param int startingSlope
	 * @return List<Double> 
	 */
	private List<Double> findAllPeaksAndDips(List<Double> unfilteredBuffer, List<Double>filteredList){
		// adds all lines into arraylist as Double
		double currentRedMeans = unfilteredBuffer.get(0);
		double nextRedMeans = unfilteredBuffer.get(1);

		int slope = classifySlope(currentRedMeans, nextRedMeans);
		boolean isChanged = false;
		int startingPeakIndex = 0;
		int dynamicPeakListSize = unfilteredBuffer.size();

		System.out.println("starting slope: " + slope); //which is 0
		//infinite loop! wee!
		//for each starting value of "u" and "n" peaks
		while(true){ //to avoid array modification errors

			if(startingPeakIndex > dynamicPeakListSize-2){break;}

			currentRedMeans =  unfilteredBuffer.get(startingPeakIndex);
			nextRedMeans = unfilteredBuffer.get(startingPeakIndex+1);

			//if slope is changed... break out
			if(slope != classifySlope(currentRedMeans, nextRedMeans) && classifySlope(currentRedMeans, nextRedMeans)!= 0){
				isChanged = true;
				slope = classifySlope(currentRedMeans, nextRedMeans);
				//once slope changed, add next value of the training data into the list if 
				System.out.println("peaking at: " + currentRedMeans  + " to "+ nextRedMeans + " changed slope to: " + slope);
				
				currentRedMeans = unfilteredBuffer.get(startingPeakIndex);
				unfilteredBuffer.add(startingPeakIndex, unfilteredBuffer.get(startingPeakIndex+1));
				System.out.println("adding peak: " + currentRedMeans);
				startingPeakIndex+=1;
				//set currentRedMeans to the next value to be compared
			}

			//remove first n values from the list until you hit two that suggests a change in slope
			if(!isChanged){
				System.out.println("removing a index: " + startingPeakIndex + "PPG: " + unfilteredBuffer.get(startingPeakIndex));
				unfilteredBuffer.remove(startingPeakIndex);
				currentRedMeans = unfilteredBuffer.get(startingPeakIndex);
				startingPeakIndex--;
			}
			dynamicPeakListSize = unfilteredBuffer.size(); //updating var with new size

			//break out of inner while loop once we hit the end of the array
			isChanged = false; //resets isChanged
			startingPeakIndex++;

			//System.out.println("currentIndex: " + startingPeakIndex + " out of " + peaksList.size());
		}// while loop
		System.out.println("broke out of while loop");
			for(Double d : unfilteredBuffer){
				filteredList.add(d);
		}

		return filteredList;

	}
	/**
	 * takes in a list of only dips and peaks and returns an arrayList of only dips
	 * list needs to be at least 5 elements long
	 * @param List<Double>dipsAndPeaks
	 * @return List<Double>
	 */
	private List<Double> findDipsOnly(List<Double> dipsAndPeaks){
		int downThenUpCount = 0;
		int indexCount = 0;
		List<Double> dipsOnly;
		dipsOnly = dipsAndPeaks; 
		int dynamicPeakListSize = dipsAndPeaks.size();
		//find up then down pattern
		while(true){
			if(indexCount >= dynamicPeakListSize-4){ break;}

			if(downThenUpCount == 1 || classifySlope(dipsOnly.get(indexCount), dipsOnly.get(indexCount+1)) == -1){
				downThenUpCount=1;
				if(classifySlope(dipsOnly.get(indexCount+2),dipsOnly.get(indexCount+3)) == 1){
					//we are going up after a dip now
					downThenUpCount=0;
				}
				else{ // if we don't have down peak next, remove until we do
					dipsOnly.remove(indexCount+2);
					dipsOnly.remove(indexCount+3);
					indexCount-=2;
					dynamicPeakListSize = dipsOnly.size();
				}
			}
			else{ // if we don't start on a up peak remove until we do
				dipsOnly.remove(indexCount);
				dipsOnly.remove(indexCount+1);
				indexCount-=2;
				dynamicPeakListSize = dipsOnly.size();
			}
			indexCount+=2;
		}
		return dipsOnly;
	}
	/**
	 * takes in a list of only dips and peaks and returns an arrayList of only dips
	 * list needs to be at least 5 elements long
	 * @param List<Double>dipsAndPeaks
	 * @return List<Double>
	 */
	private List<Double> findDipsOnly2(List<Double> buffer, List<Double> redMean){
		
		int frameCount =0;
		int downThenUpCount = 0;
		int indexCount = 0;
		List<Double> b = buffer;
		List<Double>dipsOnly = redMean; 
		int dynamicPeakListSize = buffer.size();
		//find up then down pattern
		while(true){
			if(indexCount >= dynamicPeakListSize-4){ break;}
			if(downThenUpCount == 1 || classifySlope(b.get(indexCount), b.get(indexCount+1)) == -1){
				downThenUpCount=1;
				if(classifySlope(buffer.get(indexCount+1),b.get(indexCount+2)) == 1){
					frameCount =0;
					//we are going up after a dip now
					downThenUpCount=0;
				}
				else{ // if we don't have down peak next, remove until we do
					b.remove(indexCount+1);
					//dipsOnly.remove(indexCount+2);
					indexCount-=1;
					dynamicPeakListSize = b.size();
					frameCount++;
				}
			}
			else{ // if we don't start on a up peak remove until we do
				b.remove(indexCount);
				//dipsOnly.remove(indexCount+1);
				indexCount-=1;
				dynamicPeakListSize = b.size();
			}
			indexCount+=1;
		}
		
		for(Double d : b){
			dipsOnly.add(d);
		}
		return dipsOnly;
	}
	/**
	 * not used... 
	 */
	private void setInbetweenValue(){
		//TODO: no time to compare to
		//		//		 for now, adding all peaks into the list
		//		for(int i=0;i<redMeans.size()-2; i+=2){
		//			rrInt = Math.abs(getTimeInMillis(redMeans.get(i)) - getTimeInMillis(redMeans.get(i+2)));
		//			rrInt = (long)Math.pow((double)rrInt, 2);
		//			//System.out.println(peaksList.get(i)[0] + " - " + peaksList.get(i+1)[0] + " = " + rrInt);
		//			if(rrInt >= 10000){
		//				rrIntervals.add(rrInt);
		//			}
		//		}
	}

	/**
	 * takes in a start and the next value and returns the slope
	 * @param double start
	 * @param double oneAfter
	 * @return int slope
	 */
	private int classifySlope(double start, double oneAfter){
		//finds current slope
		int ret;
		if(start > oneAfter){ret = -1;}
		else if(start < oneAfter){ret = 1;}
		else{ret = 0;}
		return ret; //positive slope if true, negative slope if false or 0 if no slope othherwise.
	}

	public void sendUpdatedRedValuestoUI(double redValue){
		float currentRedValue = (float)redValue;
		if (widgets[STREAMS.REDVALUES.ordinal()] !=null){
			((ContextImageWidget)widgets[STREAMS.REDVALUES.ordinal()]).history_view.add(currentRedValue);
		}
	}


	//This method writes the red pixel values in a text file and stored it in Downloads/MyPPG
	public void generateDATA() {
		//This will get the SD Card directory and create a folder named MyFiles in it.
		File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File directory = new File (root.getAbsolutePath() + "/MyPPG");
		directory.mkdirs();


		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("ET"));
		String date = df.format(Calendar.getInstance().getTime());

		//Now create the file in the above directory and write the contents into it
		File file = new File(directory, "RedValues_"+date+".txt");
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write("Index\tTime Stamp\tred Values\n");
			osw.write("Start: "+System.currentTimeMillis()+"\n");

			// this is the file out, don't really need
			for (int i=0; i<meanreds.length;i++){

				if (filterOn){
					//osw.write(i+"\t"+Double.toString(meanreds[i])+"\t"+Double.toString(meanredsFiltSm[i])+"\t"+Double.toString(meanredsFiltBW[i])+"\n");
				}
				else{
					osw.write(i+"\t"+System.currentTimeMillis()+"\t"+Double.toString(meanreds[i])+"\n");
				}

			}
			osw.write("End: "+System.currentTimeMillis());
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double getSum(double[] array){
		double sum=0;

		for (int i=0; i<array.length; i++){
			sum=sum+array[i];
		}
		return (double)sum;
	}
	public double getMean(double[] array){

		return getSum(array)/array.length;
	}

	public boolean readyForAnalysis(){
		return readyForAnalysis;
	}

	public int measureBPM(){
		return result;
	}

	//Decode YUV color code to hex
	void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {

		final int frameSize = width * height;  

		//Log.i("FRAMESIZE: ",""+frameSize);

		for (int j = 0, yp = 0; j < height; j++) {       int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;  
		for (int i = 0; i < width; i++, yp++) {  
			int y = (0xff & ((int) yuv420sp[yp])) - 16;  
			if (y < 0)  
				y = 0;  
			if ((i & 1) == 0) {  
				v = (0xff & yuv420sp[uvp++]) - 128;  
				u = (0xff & yuv420sp[uvp++]) - 128;  
			}  

			int y1192 = 1192 * y;  
			int r = (y1192 + 1634 * v);  
			int g = (y1192 - 833 * v - 400 * u);  
			int b = (y1192 + 2066 * u);  

			if (r < 0)                  r = 0;               else if (r > 262143)  
				r = 262143;  
			if (g < 0)                  g = 0;               else if (g > 262143)  
				g = 262143;  
			if (b < 0)                  b = 0;               else if (b > 262143)  
				b = 262143;  

			rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);  


		}
		}  

	}  
}  