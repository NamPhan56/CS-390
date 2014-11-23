package namsrc;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ECGFeatureExtractor3 {



	//LinkedLists to keep accelerometer readings for a window
	private LinkedList<Long> rrIntervals = new LinkedList<Long>();
	private LinkedList<Long> timeVector = new LinkedList<Long>();
	private List<String[]> peaksList = new ArrayList<String[]>();
	private static int startingPeakIndex = 0;
	private long WINDOW_IN_MILLISEC; 
	private double lastECGValue = 0, lastRRinterval = 0;

	/**
	 * Constructor for the extractor
	 * @param WINDOW window size in milliseconds
	 */
	public ECGFeatureExtractor3(long WINDOW) {
		WINDOW_IN_MILLISEC = WINDOW;
	}

	public void computeRRintervals(String inputDir) throws FileNotFoundException{
		int startingPeakIndex = 0;
		long rrInt=0;
		boolean isChanged = false;
		int slope = 0; // 1 = positive,0 = none, -1 = negative

		//used for reading the file
		Scanner br = new Scanner(new File(inputDir));

		String s = br.nextLine(); //skip the first line as they are all labels

		// adds all lines into arraylist as string[]
		while(br.hasNext()) {
			peaksList.add(br.nextLine().split(","));
		}
		int dynamicPeakListSize = peaksList.size();


		double currentECGval = Double.parseDouble(peaksList.get(0)[1]);
		double nextECGval = Double.parseDouble(peaksList.get(1)[1]);
		//classifies the slope using ecg values: (start, oneAfter)

		slope = classifySlope(currentECGval, nextECGval);

		System.out.println("starting slope: " + slope); //which is 0
		//infinite loop! wee!
		//for each starting value of "u" and "n" peaks
		while(true){ //to avoid array modification errors

			if(startingPeakIndex > dynamicPeakListSize-2){break;}

			currentECGval =  Double.parseDouble(peaksList.get(startingPeakIndex)[1]);
			nextECGval = Double.parseDouble(peaksList.get(startingPeakIndex+1)[1]);

			//if slope is changed... break out
			if(slope != classifySlope(currentECGval, nextECGval) && classifySlope(currentECGval, nextECGval)!= 0){
				isChanged = true;
				slope = classifySlope(currentECGval, nextECGval);
				//once we found add next value of the training data into the list
				//System.out.println("Peak at index: " + startingPeakIndex + " ECGvalue: " + peaksList.get(startingPeakIndex)[1] + " slope changed to " + classifySlope(currentECGval, nextECGval));
				System.out.println("peaking at: " + Double.parseDouble(peaksList.get(startingPeakIndex)[1]) + " changed slope to: " + slope);

				currentECGval = Double.parseDouble(peaksList.get(startingPeakIndex)[1]);
				peaksList.add(startingPeakIndex, peaksList.get(startingPeakIndex+1));
				startingPeakIndex+=1;
				//set currentECGval to the next value to be compared
			}

			//remove first n values from the list until you hit two that suggests a change in slope
			if(!isChanged){
				System.out.println("removing a index: " + startingPeakIndex + " ECGvalue: " + peaksList.get(startingPeakIndex)[1]);
				peaksList.remove(startingPeakIndex);
				currentECGval = Double.parseDouble(peaksList.get(startingPeakIndex)[1]);
				startingPeakIndex--;
			}
			dynamicPeakListSize = peaksList.size(); //updating var with new size

			//break out of inner while loop once we hit the end of the array
			isChanged = false; //resets isChanged
			startingPeakIndex++;

			//System.out.println("currentIndex: " + startingPeakIndex + " out of " + peaksList.size());
		}
		System.out.println("broke out of while loop");
		//needs to now filter out which peaks are real and which are false.
		// add them rrIntervals which are a list of longswa



		// for now, adding all peaks into the list
		//		for(int i=0;i<peaksList.size()-2; i+=2){
		//			rrInt = Math.abs(getTimeInMillis(peaksList.get(i)[0]) - getTimeInMillis(peaksList.get(i+2)[0]));
		//			//System.out.println(peaksList.get(i)[0] + " - " + peaksList.get(i+1)[0] + " = " + rrInt);
		//			rrIntervals.add(rrInt);
		//		}

	} // end of method

	private int classifySlope(double start, double oneAfter){
		int ret;
		//sets the first ECGval to the first item in the set
		//finds the current slope
		//System.out.println(""+ start + " < " + oneAfter + " " + isPositive );
		if(start > oneAfter){
			ret = -1;
		}
		else if(start < oneAfter){
			ret = 1;
		}
		else{
			ret = 0;
		}
		return ret; //positive slope if true, negative or no slope otherwise.
	}
	/**
	 * Clear values for the next window 
	 */
	private void clearValues(){
		rrIntervals.clear();
		timeVector.clear();
	}


	private long getTimeInMillis(String time) {
		try{
			String date = time;
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
			Date d;
			d = format.parse(date);
			long timeStamp = d.getTime();
			return timeStamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	private void generateArffFile(String inputDir, String inputDirOut){
		String arffFile = inputDirOut;
		String featureNames[] = {"time,ECGValue,rrInterval,"};

		try{

			BufferedReader br = new BufferedReader(new FileReader(inputDir));
			BufferedWriter bw = new BufferedWriter(new FileWriter(arffFile));

			//First write the header information
			bw.write("@relation classType\n");
			for(int i=0;i<featureNames.length;i++)
				bw.write("@attribute "+featureNames[i]+" NUMERIC \n");

			bw.write("@attribute classType { Exercise , BaseLine }\n");
			bw.write("@data\n");

			String s = br.readLine(); //skips the first labels

			Iterator<Long>it = rrIntervals.iterator();
			int index =0;
			while((s=br.readLine())!=null) {
				String tokens[] = s.split(",");
				long time = getTimeInMillis(tokens[0]);
				double ECGValue = Double.parseDouble(tokens[1]);
				long rrInterval = it.next();
				String classType = tokens[2];
				bw.write(time+" ,"+ECGValue+" ,"+rrInterval+" ,"+classType+"\n");
			}
			br.close();
			bw.close();
			System.out.println("Successfully generated Arff File:"+arffFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws FileNotFoundException {
		String computer = "Nam"; //"Maxine" or "Nam"
		String INPUT_DIR = "";
		String INPUT_DIR2 = "";

		switch(computer){
		case "Nam" :
			INPUT_DIR = "C:/Users/Nam Phan/Desktop/Repo/CS-390/ECGClassifier/Nam's/namsrc/ECG_Data.csv";
			INPUT_DIR2 = "C:/Users/Nam Phan/Desktop/Repo/CS-390/ECGClassifier/Nam's/namsrc/ecg-data.arff";
			break;
		case "Maxine" : 
			INPUT_DIR = "/Users/maxinegerhard/Documents/Repo/CS-390/ECGClassifier/src/main/ECG_Data.csv";
			INPUT_DIR2 = "/Users/maxinegerhard/Documents/Repo/CS-390/ECGClassifier/src/main/ecg-data.arff";
			break;
		default : 
			System.out.println("Please specify a computer!");
			break;

		}
		ECGFeatureExtractor3 ecgfe = new ECGFeatureExtractor3(100); //calculated window size to be about 2-5 r peaks per window
		ecgfe.computeRRintervals(INPUT_DIR);
		ecgfe.generateArffFile(INPUT_DIR,INPUT_DIR2);
	}

}
