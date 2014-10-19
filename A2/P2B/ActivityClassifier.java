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
	    /* yFFT3 */
	    if (i[14] == null) { return 0.2012195121951229; } else if (((Double)i[14]).doubleValue() <= 9.743825015918528) { return -1.4656488549618325; } else { return 2.100000000000001; }
	  }
	}
	class ActivityClassifier_0_1 {
	  public static double classify(Object[] i) {
	    /* speedFFT3 */
	    if (i[32] == null) { return -0.27436672907948606; } else if (((Double)i[32]).doubleValue() <= 20.264852587110564) { return -1.229102616139981; } else { return 0.7276387049646967; }
	  }
	}
	class ActivityClassifier_0_2 {
	  public static double classify(Object[] i) {
	    /* energyFFT1 */
	    if (i[36] == null) { return -0.17281698129389073; } else if (((Double)i[36]).doubleValue() <= 324.90549330499596) { return 0.3186493261162764; } else { return -1.834081901006792; }
	  }
	}
	class ActivityClassifier_0_3 {
	  public static double classify(Object[] i) {
	    /* energyXYMean */
	    if (i[40] == null) { return 0.03404252508477092; } else if (((Double)i[40]).doubleValue() <= 1.8641226469678576) { return -1.4694354531361462; } else { return 0.466902899441856; }
	  }
	}
	class ActivityClassifier_0_4 {
	  public static double classify(Object[] i) {
	    /* energyXYDev */
	    if (i[41] == null) { return -0.11615768707092147; } else if (((Double)i[41]).doubleValue() <= 2.362612196747107) { return 0.21237017130518887; } else { return -2.511398086108125; }
	  }
	}
	class ActivityClassifier_0_5 {
	  public static double classify(Object[] i) {
	    /* energyFFT1 */
	    if (i[36] == null) { return 0.032973743726380046; } else if (((Double)i[36]).doubleValue() <= 320.20110377740315) { return 0.38262745298662104; } else { return -1.4018158576328978; }
	  }
	}
	class ActivityClassifier_0_6 {
	  public static double classify(Object[] i) {
	    /* yVelocityChange */
	    if (i[16] == null) { return -0.1117182370518003; } else if (((Double)i[16]).doubleValue() <= 26517.38497711953) { return -0.3352104309692433; } else { return 2.6273036395039395; }
	  }
	}
	class ActivityClassifier_0_7 {
	  public static double classify(Object[] i) {
	    /* yDistance */
	    if (i[17] == null) { return 0.02904614015706123; } else if (((Double)i[17]).doubleValue() <= 2529110.470827855) { return 0.4019091056588242; } else { return -0.8449606171194927; }
	  }
	}
	class ActivityClassifier_0_8 {
	  public static double classify(Object[] i) {
	    /* energyXYMean */
	    if (i[40] == null) { return 0.004289619066108669; } else if (((Double)i[40]).doubleValue() <= 1.8641226469678576) { return -1.1980132112300605; } else { return 0.31137448688123676; }
	  }
	}
	class ActivityClassifier_0_9 {
	  public static double classify(Object[] i) {
	    /* energyFFT1 */
	    if (i[36] == null) { return -0.17441611068213222; } else if (((Double)i[36]).doubleValue() <= 320.20110377740315) { return 0.11607371754666727; } else { return -1.1684585141034698; }
	  }
	}
	class ActivityClassifier_1_0 {
	  public static double classify(Object[] i) {
	    /* yDev */
	    if (i[10] == null) { return 0.2195121951219522; } else if (((Double)i[10]).doubleValue() <= 0.20204462340516494) { return 2.738372093023258; } else { return -1.1343750000000017; }
	  }
	}
	class ActivityClassifier_1_1 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return 0.044360480378661804; } else if (((Double)i[27]).doubleValue() <= 2.5717983073210817) { return 0.6674586537652553; } else { return -1.3561412448696986; }
	  }
	}
	class ActivityClassifier_1_2 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return -0.10904489390940543; } else if (((Double)i[30]).doubleValue() <= 10.904122209580532) { return 1.1920257491543653; } else { return -0.7225982493283103; }
	  }
	}
	class ActivityClassifier_1_3 {
	  public static double classify(Object[] i) {
	    /* speedCrossRate */
	    if (i[29] == null) { return 0.0105164011053561; } else if (((Double)i[29]).doubleValue() <= 0.1455026455026455) { return 1.0138801572040081; } else { return -0.41975628673105025; }
	  }
	}
	class ActivityClassifier_1_4 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return 0.06412002667495065; } else if (((Double)i[27]).doubleValue() <= 2.5717983073210817) { return 0.3383884026181963; } else { return -1.6955865770368896; }
	  }
	}
	class ActivityClassifier_1_5 {
	  public static double classify(Object[] i) {
	    /* energyXYDev */
	    if (i[41] == null) { return -0.09503673792601618; } else if (((Double)i[41]).doubleValue() <= 0.7092657945054686) { return -0.4114111082115806; } else { return 0.9590841124461276; }
	  }
	}
	class ActivityClassifier_1_6 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return 0.1613411974342112; } else if (((Double)i[1]).doubleValue() <= 0.17459987136559274) { return 1.0869048229438818; } else { return -0.3301221672041479; }
	  }
	}
	class ActivityClassifier_1_7 {
	  public static double classify(Object[] i) {
	    /* yFFT1 */
	    if (i[12] == null) { return 0.0632640228433301; } else if (((Double)i[12]).doubleValue() <= 21.932535702124085) { return 0.37992925161133173; } else { return -1.2221554302211195; }
	  }
	}
	class ActivityClassifier_1_8 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return -0.06685644737708775; } else if (((Double)i[27]).doubleValue() <= 2.5717983073210817) { return 0.17328508213641625; } else { return -1.998239248397281; }
	  }
	}
	class ActivityClassifier_1_9 {
	  public static double classify(Object[] i) {
	    /* energyFFT4 */
	    if (i[39] == null) { return 0.07177581872828735; } else if (((Double)i[39]).doubleValue() <= 241.875) { return 0.3478085114923188; } else { return -0.9618715858335136; }
	  }
	}
	class ActivityClassifier_2_0 {
	  public static double classify(Object[] i) {
	    /* zVelocityChange */
	    if (i[25] == null) { return -0.42073170731707227; } else if (((Double)i[25]).doubleValue() <= 50110.72048901323) { return -0.7826086956521735; } else { return 1.5000000000000067; }
	  }
	}
	class ActivityClassifier_2_1 {
	  public static double classify(Object[] i) {
	    /* yDistance */
	    if (i[17] == null) { return 0.2222394021860891; } else if (((Double)i[17]).doubleValue() <= 2195116.0884203864) { return -0.2534671640627539; } else { return 1.392068225860304; }
	  }
	}
	class ActivityClassifier_2_2 {
	  public static double classify(Object[] i) {
	    /* speedFFT1 */
	    if (i[30] == null) { return 0.20954305170187665; } else if (((Double)i[30]).doubleValue() <= 12.275720458197455) { return -1.0174502016302354; } else { return 0.6482941660093501; }
	  }
	}
	class ActivityClassifier_2_3 {
	  public static double classify(Object[] i) {
	    /* xDistance */
	    if (i[8] == null) { return -0.03115984026745375; } else if (((Double)i[8]).doubleValue() <= 1406483.7773402082) { return 0.9171422130667456; } else { return -0.48037386126084175; }
	  }
	}
	class ActivityClassifier_2_4 {
	  public static double classify(Object[] i) {
	    /* energyXYDev */
	    if (i[41] == null) { return 0.028894711205428393; } else if (((Double)i[41]).doubleValue() <= 2.362612196747107) { return -0.18184621561831857; } else { return 2.5847226813891053; }
	  }
	}
	class ActivityClassifier_2_5 {
	  public static double classify(Object[] i) {
	    /* xDev */
	    if (i[1] == null) { return 0.04951171068285912; } else if (((Double)i[1]).doubleValue() <= 0.7365038972006259) { return 0.5734810563076813; } else { return -0.7204913745503337; }
	  }
	}
	class ActivityClassifier_2_6 {
	  public static double classify(Object[] i) {
	    /* speedMean */
	    if (i[27] == null) { return -0.06413082681681821; } else if (((Double)i[27]).doubleValue() <= 0.5799349301302033) { return -1.3203147464061238; } else { return 0.2828418896892938; }
	  }
	}
	class ActivityClassifier_2_7 {
	  public static double classify(Object[] i) {
	    /* zCrossRate */
	    if (i[20] == null) { return -0.06342274140101171; } else if (((Double)i[20]).doubleValue() <= 0.1905241935483871) { return -0.6667247814781703; } else { return 0.36777047470733615; }
	  }
	}
	class ActivityClassifier_2_8 {
	  public static double classify(Object[] i) {
	    /* xFFT1 */
	    if (i[3] == null) { return 0.04140366013271417; } else if (((Double)i[3]).doubleValue() <= 19.88895454839775) { return 0.6992609461626793; } else { return -0.4760140909157381; }
	  }
	}
	class ActivityClassifier_2_9 {
	  public static double classify(Object[] i) {
	    /* yFFT3 */
	    if (i[14] == null) { return 0.04900762194186059; } else if (((Double)i[14]).doubleValue() <= 24.08245247253936) { return -0.2065996461323469; } else { return 0.9227487513672129; }
	  }
	}