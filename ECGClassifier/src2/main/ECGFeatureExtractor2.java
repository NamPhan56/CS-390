package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ECGFeatureExtractor2 {



	//LinkedLists to keep accelerometer readings for a window
	private LinkedList<Long> rrIntervals = new LinkedList<Long>();
	private LinkedList<Long> timeVector = new LinkedList<Long>();
	private CopyOnWriteArrayList<String[]> peaksList = new CopyOnWriteArrayList<String[]>();
	private static int currentIndex = 0;
	private long WINDOW_IN_MILLISEC; 
	private double lastECGValue = 0, lastRRinterval = 0;

	/**
	 * Constructor for the extractor
	 * @param WINDOW window size in milliseconds
	 */
	public ECGFeatureExtractor2(long WINDOW) {
		WINDOW_IN_MILLISEC = WINDOW;
	}

	public void computeRRintervals(String inputDir) throws FileNotFoundException{
		int iteratingIndex = 0;

		boolean isChanged = false;
		boolean isPositive = true; //assume positive slope to begin with
		boolean slopIsPositive = true; // 0 = none, 1 = pos, -1 = neg
		double currentECGval = -1;
		//used for reading the file
		Scanner br = new Scanner(new File(inputDir));

		String s = br.nextLine(); //skip the first line as they are all labels

		// adds all lines into arraylist as string[]
		while(br.hasNext()) {
			peaksList.add(br.nextLine().split(","));
		}

		//infinite loop! wee!
		while(currentIndex < peaksList.size() ){
			//sets the first ECGval to the first item in the set
			currentECGval = Double.parseDouble(peaksList.get(currentIndex)[1]);
			//finds the beginning slope
			isPositive = (currentECGval < Double.parseDouble(peaksList.get(currentIndex+1)[1]));
			//iterate through all objects in peaksList until it finds a change in slope
			
			for(String[] sa : peaksList){
				
				//if slope is changed...
				if(isPositive != (currentECGval < Double.parseDouble(sa[1]))){
					isChanged = true;
					break;
				}
				if(!isChanged){ //if slope is not changed remove until you hit a changed slope
					peaksList.remove(iteratingIndex);
					System.out.println("removing: " + iteratingIndex + "\n because:" + currentECGval + " less than " + Double.parseDouble(sa[1]) + " is " + isPositive);
					//iteratingIndex--; // to account for size being changed
				}

				iteratingIndex++;
			}
			currentIndex++;
			System.out.println("currentIndex: " + currentIndex + " out of " + peaksList.size());
			iteratingIndex = 0;

		}
		System.out.println("broke out of while loop");

	} // end of method

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

	private void generateArffFile(String inputDir){
		String arffFile = "./ecg-data.arff";
		String featureNames[] = {"time,ECGValue,rrInterval"};

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
			while((s=br.readLine())!=null) {
				String tokens[] = s.split(",");
				long time = getTimeInMillis(tokens[0]);
				double ECGValue = Double.parseDouble(tokens[1]);
				double rrInterval = it.next();
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
		switch(computer){
		case "Nam" :
			INPUT_DIR = "C:/Users/Nam Phan/Desktop/Repo/CS-390/ECGClassifier/src2/main/ECG_Data.csv";
			break;
		case "Maxine" : 
			INPUT_DIR = "/Users/maxinegerhard/Documents/workspace/ECGClassifier/src/ECG_Data.csv";
			break;
		default : 
			System.out.println("Please specify a computer!");
			break;

		}



		ECGFeatureExtractor2 ecgfe = new ECGFeatureExtractor2(100); //calculated window size to be about 2-5 r peaks per window
		ecgfe.computeRRintervals(INPUT_DIR);
		ecgfe.generateArffFile(INPUT_DIR);
	}

}
