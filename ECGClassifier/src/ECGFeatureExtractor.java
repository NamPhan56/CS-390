//	import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//
//public class ECGFeatureExtractor {
//
//
//
//		//LinkedLists to keep accelerometer readings for a window
//		private LinkedList<Long> rrIntervals = new LinkedList<Long>();
//		private LinkedList<Long> timeVector = new LinkedList<Long>();
//
//
//		
//		private long WINDOW_IN_MILLISEC; 
//		private double lastECGValue = 0, lastRRinterval = 0;
//
//
//		/**
//		 * Constructor for the extractor
//		 * @param WINDOW window size in milliseconds
//		 */
//		public ECGFeatureExtractor(long WINDOW) {
//			WINDOW_IN_MILLISEC = WINDOW;
//		}
//
//		public void computeRRintervals(String inputDir){
//			long rrInt = 1500; //rough estimate of time between rr peaks when not exercising
//			double threshold = 498; //rough estimate of average ECG value over a window of ~100 samples
//			long timeThresholdCross1 = 0; 
//			long timeThresholdCross2 = 0;
//			double maxECGval = 0;
//			double minECGval = 0;
//			double prevECGval = 0;
//			double currentECGval = 0;
//			boolean flagForThresholdCross = false;
//
//			try {
//				BufferedReader br = new BufferedReader(new FileReader(inputDir));
//				
//				String s = br.readLine(); //skip the first line as they are all labels
//				s = br.readLine();
//				if(s == null){
//					return;
//				}
//				while(true) {
//					for(int i=0; i<this.WINDOW_IN_MILLISEC; i++){ //computes a new rrInterval every 100 samples
//						String tokens[] = s.split(",");
//						//************************************************************************************************
//						currentECGval = Double.parseDouble(tokens[1]);
//						
//						//checks to see if the data crosses the threshold with a positive slope
//						if(prevECGval<threshold && currentECGval>=threshold){
//							if(flagForThresholdCross == false){
//								timeThresholdCross1 = getTimeInMillis(tokens[0]);
//								flagForThresholdCross = true;
//							}
//							else{
//								timeThresholdCross2 = getTimeInMillis(tokens[0]);
//								flagForThresholdCross = false;
//							}
//						}
//						
//						//sets min and max values over the current window
//						maxECGval = Math.max(maxECGval,currentECGval);
//						minECGval = Math.min(minECGval,currentECGval);
//						//************************************************************************************************
//						
//						rrIntervals.add(rrInt);
//						prevECGval = currentECGval;
//						
//						if((s=br.readLine())==null){
//							return;
//						}
//		
//					}
//					
//					threshold = (maxECGval + minECGval)/2;
//					rrInt = Math.abs(timeThresholdCross1-timeThresholdCross2);	
//					
//				}
//					
//				
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//
//		/**
//		 * Clear values for the next window 
//		 */
//		private void clearValues(){
//			rrIntervals.clear();
//			timeVector.clear();
//		}
//
//		
//		private long getTimeInMillis(String time) {
//			try{
//				String date = time;
//				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
//				Date d;
//				d = format.parse(date);
//				long timeStamp = d.getTime();
//				return timeStamp;
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return 0;
//			}
//		}
//		
//		private void generateArffFile(String inputDir){
//			String arffFile = "/Users/maxinegerhard/Documents/workspace/ECGClassifier/src/ecg-data.arff";
//			String featureNames[] = {"time,ECGValue,rrInterval"};
//			
//			try{
//
//				BufferedReader br = new BufferedReader(new FileReader(inputDir));
//				BufferedWriter bw = new BufferedWriter(new FileWriter(arffFile));
//				
//				//First write the header information
//				bw.write("@relation classType\n");
//				for(int i=0;i<featureNames.length;i++)
//					bw.write("@attribute "+featureNames[i]+" NUMERIC \n");
//				
//				bw.write("@attribute classType { Exercise , BaseLine }\n");
//				bw.write("@data\n");
//				
//				String s = br.readLine(); //skips the first labels
//				Iterator<Long>it = rrIntervals.iterator();
//				while((s=br.readLine())!=null) {
//					String tokens[] = s.split(",");
//					long time = getTimeInMillis(tokens[0]);
//					double ECGValue = Double.parseDouble(tokens[1]);
//					double rrInterval = it.next();
//					String classType = tokens[2];
//					
//					bw.write(time+" ,"+ECGValue+" ,"+rrInterval+" ,"+classType+"\n");
//				}
//				br.close();
//				bw.close();
//				System.out.println("Successfully generated Arff File:"+arffFile);
//			} catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		public static void main(String args[]) {
//			String INPUT_DIR = "/Users/maxinegerhard/Documents/workspace/ECGClassifier/src/ECG_Data.csv";
//			ECGFeatureExtractor ecgfe = new ECGFeatureExtractor(100); //calculated window size to be about 2-5 r peaks per window
//			ecgfe.computeRRintervals(INPUT_DIR);
//			ecgfe.generateArffFile(INPUT_DIR);
//		}
//
//	}
