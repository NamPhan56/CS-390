package edu.umass.cs.accelerometer;

import java.util.LinkedList;


/**
 * @author CS390MB
 * 
 */
public class StepDetection {

	
	private boolean searchRegulation = true;
	private int countSteps = 0;
	private long lastStepTime = -1L;
	private LinkedList<double[]> accelData = new LinkedList<double[]>();
	private LinkedList<Long> timestamps = new LinkedList<Long>();
	private int NSAMPLES = 75;
	
	/**
	 * Constructor for step detector
	 * @param SAMPLES_PER_WINDOW number of samples to collect in a window where detection occurs
	 */
	public StepDetection(int SAMPLES_PER_WINDOW) {
		NSAMPLES = SAMPLES_PER_WINDOW;
	}
	
	public int getDetectedStepsCountInSamplingWindow(long timestamp, double accX, double accY, double accZ) {

		//First, add data to the current sampling window
		double acc[] = new double[]{accX,accY,accZ};
		accelData.add(acc);
		timestamps.add(timestamp);
		
		//If sampling window is not full, do not perform any detection and return 0
		if(accelData.size()!=NSAMPLES) return 0;
		//Else proceed to detect steps
		
		double data[][] = new double[3][NSAMPLES]; //acc[0]=x,acc[1]=y, acc[2]=z
		int X=0,Y=1,Z=2;
		double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;
		for(int i=0;i<NSAMPLES;i++)
		{
			double accelValues[] = accelData.get(i);
			data[X][i] = accelValues[X];
			data[Y][i] = accelValues[Y];
			data[Z][i] = accelValues[Z];
			minX = (data[X][i]<minX?data[X][i]:minX);
			minY = (data[Y][i]<minY?data[Y][i]:minY);
			minZ = (data[Z][i]<minZ?data[Z][i]:minZ);
			maxX = (data[X][i]>maxX?data[X][i]:maxX);
			maxY = (data[Y][i]>maxY?data[Y][i]:maxY);
			maxZ = (data[Z][i]>maxZ?data[Z][i]:maxZ);
		}
		double deltaX = maxX-minX;
		double deltaY = maxY-minY;
		double deltaZ = maxZ-minZ;
		
		//Identify max Delta axis and corresponding dynamic threshold
		int maxDeltaAxis = X;
		double dynamicThreshold = (maxX+minX)/2;
		if(deltaZ>deltaY && deltaZ>deltaX){ 
			maxDeltaAxis = Z; 
			dynamicThreshold = (maxZ+minZ)/2; 
		}
		else if(deltaY>deltaZ && deltaY>deltaX){ 
			maxDeltaAxis = Y; 
			dynamicThreshold = (maxY+minY)/2; 
		}
		//Log.d("STEP","AXIS: "+maxDeltaAxis+"\t Threshold:"+maxDelta);
		boolean stepIndexList[] = new boolean[NSAMPLES];
		for(int i=0;i<NSAMPLES;i++)
			stepIndexList[i] = false;
		
		int stepsInWindow = 0;
		/*
		 * Note that countSteps can start as a non-zero value in this window due to
		 * search regulation mode. We want to output steps detected in this window
		 * only.
		 */
		
		for(int i=1;i<NSAMPLES;i++) {
			if(data[maxDeltaAxis][i]<dynamicThreshold && data[maxDeltaAxis][i-1]>=dynamicThreshold){
				//Potentially a step
				long time = timestamps.get(i);
				if(lastStepTime==-1 || (time-lastStepTime)>5000) 
					lastStepTime=time;
				//Check Time window (0.2s to 2s)
				if((time-lastStepTime)>2000 || (time-lastStepTime)<400) {
					//Failed timing test
					if(searchRegulation) {
						//If already in search regulation mode, discard previous counted steps
						for(int j=i-1;j>=0 && countSteps>0;j--) {
							if(stepIndexList[j] == true) {
								stepIndexList[j] = false;
								countSteps--;
							}
						}
					} else {
						//Output steps detected so far
						stepsInWindow = countSteps;
						//enter search regulation mode
						searchRegulation = true;
						countSteps = 0;
					}
				} else {
					//stepsInWindow++;
					//valid step as far as we know from current observations
					stepIndexList[i] = true;
					if(searchRegulation && countSteps>=4) {
						searchRegulation = false;
					}
					countSteps++;
					lastStepTime = time;
					//will be discarded later if it is still in search regulation mode
				}
			}
		}
		/*for(int i=0;i<stepIndexList.length;i++)
			if(stepIndexList[i]==true)
				stepsInWindow++;
		stepsInWindow = countSteps - stepsInWindow;
		if(stepsInWindow<0) stepsInWindow = 0;*/
		//Now, clear the data so that new samples can be collected
		accelData.clear();
		timestamps.clear();
		
		//Finished
		return countSteps;
	}
	
}
