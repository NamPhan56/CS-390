package edu.umass.cs.accelerometer;

public class ActivityClassifier {

	private static double RtoP(double []R, int j) {
	    double Rcenter = 0;
	    for (int i = 0; i < R.length; i++) {
	      Rcenter += R[i];
	    }
	    Rcenter /= R.length;
	    double Rsum = 0;
	    for (int i = 0; i < R.length; i++) {
	      Rsum += Math.exp(R[i] - Rcenter);
	    }
	    return Math.exp(R[j]) / Rsum;
	  }

	  public static double classify(Object[] i) {
	    double [] d = distribution(i);
	    double maxV = d[0];
	    int maxI = 0;
	    for (int j = 1; j < 3; j++) {
	      if (d[j] > maxV) { maxV = d[j]; maxI = j; }
	    }
	    return (double) maxI;
	  }

	  public static double [] distribution(Object [] i) {
	    double [] Fs = new double [3];
	    double [] Fi = new double [3];
	    double Fsum;
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_0.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_0.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_0.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_1.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_1.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_1.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_2.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_2.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_2.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_3.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_3.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_3.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_4.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_4.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_4.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_5.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_5.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_5.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_6.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_6.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_6.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_7.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_7.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_7.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_8.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_8.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_8.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    Fsum = 0;
	    Fi[0] = ActivityClassifier_0_9.classify(i); Fsum += Fi[0];
	    Fi[1] = ActivityClassifier_1_9.classify(i); Fsum += Fi[1];
	    Fi[2] = ActivityClassifier_2_9.classify(i); Fsum += Fi[2];
	    Fsum /= 3;
	    for (int j = 0; j < 3; j++) { Fs[j] += (Fi[j] - Fsum) * 2 / 3; }
	    double [] dist = new double [3];
	    for (int j = 0; j < 3; j++) {
	      dist[j] = RtoP(Fs, j);
	    }
	    return dist;
	  }
	}
	class ActivityClassifier_0_0 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return 0.6828358208955191; } else if (((Double)i[30]).doubleValue() <= 63.89712066112715) { return -1.222748815165876; } else { return 2.787958115183244; }
	  }
	}
	class ActivityClassifier_0_1 {
	  public static double classify(Object[] i) {
	    /* yFFT3 */
	    if (i[14] == null) { return -0.020504215258524933; } else if (((Double)i[14]).doubleValue() <= 7.621705865275975) { return -1.189713976722531; } else { return 0.6426240028626409; }
	  }
	}
	class ActivityClassifier_0_2 {
	  public static double classify(Object[] i) {
	    /* yDistance */
	    if (i[17] == null) { return 0.018451135208785347; } else if (((Double)i[17]).doubleValue() <= 2529110.470827855) { return 0.3458149093452575; } else { return -1.5316365280695063; }
	  }
	}
	class ActivityClassifier_0_3 {
	  public static double classify(Object[] i) {
	    /* yDistance */
	    if (i[17] == null) { return -0.03510978304448678; } else if (((Double)i[17]).doubleValue() <= 453304.4394975098) { return -1.2597577298094895; } else { return 0.33542229244937; }
	  }
	}
	class ActivityClassifier_0_4 {
	  public static double classify(Object[] i) {
	    /* zCrossRate */
	    if (i[20] == null) { return -0.18198857790710868; } else if (((Double)i[20]).doubleValue() <= 0.13269794721407624) { return 0.8275492725988489; } else { return -0.5555387575842152; }
	  }
	}
	class ActivityClassifier_0_5 {
	  public static double classify(Object[] i) {
	    /* speedFFT3 */
	    if (i[32] == null) { return 0.04773125296830038; } else if (((Double)i[32]).doubleValue() <= 18.70699586069444) { return -0.6098025002455357; } else { return 0.586941724134694; }
	  }
	}
	class ActivityClassifier_0_6 {
	  public static double classify(Object[] i) {
	    /* energyXYDev */
	    if (i[41] == null) { return -0.019731116411542515; } else if (((Double)i[41]).doubleValue() <= 1.6488858960551294) { return 0.24437395491081298; } else { return -1.2371159581665205; }
	  }
	}
	class ActivityClassifier_0_7 {
	  public static double classify(Object[] i) {
	    /* xFFT3 */
	    if (i[5] == null) { return 0.07528835972805717; } else if (((Double)i[5]).doubleValue() <= 10.47758218394075) { return -0.9870851776491865; } else { return 0.3992400710190631; }
	  }
	}
	class ActivityClassifier_0_8 {
	  public static double classify(Object[] i) {
	    /* yVelocityChange */
	    if (i[16] == null) { return -0.11037988958670374; } else if (((Double)i[16]).doubleValue() <= 26517.38497711953) { return -0.24865661750315213; } else { return 2.391210272609723; }
	  }
	}
	class ActivityClassifier_0_9 {
	  public static double classify(Object[] i) {
	    /* xFFT4 */
	    if (i[6] == null) { return 0.04349900311970692; } else if (((Double)i[6]).doubleValue() <= 8.4375) { return -1.1713256302516144; } else { return 0.27956411462084896; }
	  }
	}
	class ActivityClassifier_1_0 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return 0.15671641791044416; } else if (((Double)i[30]).doubleValue() <= 15.496563795790161) { return 2.660958904109584; } else { return -1.2714843749999993; }
	  }
	}
	class ActivityClassifier_1_1 {
	  public static double classify(Object[] i) {
	    /* yDev */
	    if (i[10] == null) { return -0.04422862905253056; } else if (((Double)i[10]).doubleValue() <= 0.18179101628091424) { return 1.165405720472286; } else { return -0.9020040020661717; }
	  }
	}
	class ActivityClassifier_1_2 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return -0.06632558883767053; } else if (((Double)i[27]).doubleValue() <= 2.5717983073210817) { return 0.38081541078783065; } else { return -1.477703034640641; }
	  }
	}
	class ActivityClassifier_1_3 {
	  public static double classify(Object[] i) {
	    /* yVelocityChange */
	    if (i[16] == null) { return -0.12707916836917355; } else if (((Double)i[16]).doubleValue() <= -4547.938118281485) { return -2.1023676908593782; } else { return 0.12554284535739152; }
	  }
	}
	class ActivityClassifier_1_4 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return 0.0904720817579827; } else if (((Double)i[27]).doubleValue() <= 2.5717983073210817) { return 0.3252696746506602; } else { return -1.5263778563439767; }
	  }
	}
	class ActivityClassifier_1_5 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return 0.06552759841071838; } else if (((Double)i[1]).doubleValue() <= 0.17583149732054984) { return 1.003056368799065; } else { return -0.2757993368022442; }
	  }
	}
	class ActivityClassifier_1_6 {
	  public static double classify(Object[] i) {
	    /* xFFT3 */
	    if (i[5] == null) { return -0.00807497283875308; } else if (((Double)i[5]).doubleValue() <= 59.59727509073343) { return -0.17329022072770914; } else { return 2.0374569318846043; }
	  }
	}
	class ActivityClassifier_1_7 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return 0.0026227049503673005; } else if (((Double)i[1]).doubleValue() <= 0.5730910343386941) { return -0.40765552154282003; } else { return 0.9162030299106314; }
	  }
	}
	class ActivityClassifier_1_8 {
	  public static double classify(Object[] i) {
	    /* yFFT1 */
	    if (i[12] == null) { return 0.16887424652541283; } else if (((Double)i[12]).doubleValue() <= 23.784749127759753) { return 0.4180210843005303; } else { return -1.159039218819831; }
	  }
	}
	class ActivityClassifier_1_9 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return -0.09911788163069843; } else if (((Double)i[30]).doubleValue() <= 10.904122209580532) { return 0.8505193784090579; } else { return -0.46611405527392946; }
	  }
	}
	class ActivityClassifier_2_0 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return -0.8395522388059694; } else if (((Double)i[1]).doubleValue() <= 0.7318427964317734) { return -0.32608695652173825; } else { return -1.2729357798165086; }
	  }
	}
	class ActivityClassifier_2_1 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return 0.06712223370989034; } else if (((Double)i[27]).doubleValue() <= 0.5799349301302033) { return -1.1620030167339477; } else { return 0.4905672788502014; }
	  }
	}
	class ActivityClassifier_2_2 {
	  public static double classify(Object[] i) {
	    /* energyXYDev */
	    if (i[41] == null) { return 0.033153630459846845; } else if (((Double)i[41]).doubleValue() <= 2.362612196747107) { return -0.13142048861465289; } else { return 2.6875028446713536; }
	  }
	}
	class ActivityClassifier_2_3 {
	  public static double classify(Object[] i) {
	    /* energyXYMean */
	    if (i[40] == null) { return 0.11956966512006473; } else if (((Double)i[40]).doubleValue() <= 0.8949089180650052) { return 1.0866271187980998; } else { return -0.33094643935773294; }
	  }
	}
	class ActivityClassifier_2_4 {
	  public static double classify(Object[] i) {
	    /* zCrossRate */
	    if (i[20] == null) { return 0.06387580503657077; } else if (((Double)i[20]).doubleValue() <= 0.13269794721407624) { return -0.9013573873521825; } else { return 0.4633728502801961; }
	  }
	}
	class ActivityClassifier_2_5 {
	  public static double classify(Object[] i) {
	    /* yDistance */
	    if (i[17] == null) { return -0.07985399774287; } else if (((Double)i[17]).doubleValue() <= 2195116.0884203864) { return -0.44035518487574765; } else { return 0.9255581084422881; }
	  }
	}
	class ActivityClassifier_2_6 {
	  public static double classify(Object[] i) {
	    /* energyMean */
	    if (i[33] == null) { return 0.019770198885378076; } else if (((Double)i[33]).doubleValue() <= 10.348410815630217) { return -0.16200622277979246; } else { return 1.3560964043625052; }
	  }
	}
	class ActivityClassifier_2_7 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return -0.056273171836569595; } else if (((Double)i[1]).doubleValue() <= 0.6287544144948185) { return 0.5314250035852871; } else { return -0.6550502196235294; }
	  }
	}
	class ActivityClassifier_2_8 {
	  public static double classify(Object[] i) {
	    /* yFFT3 */
	    if (i[14] == null) { return -0.06195853104945825; } else if (((Double)i[14]).doubleValue() <= 24.08245247253936) { return -0.314059159937422; } else { return 1.1620052783391381; }
	  }
	}
	class ActivityClassifier_2_9 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return 0.048091207740085365; } else if (((Double)i[30]).doubleValue() <= 7.723607827579974) { return -1.1137719642815855; } else { return 0.22495323453384952; }
	  }
	}