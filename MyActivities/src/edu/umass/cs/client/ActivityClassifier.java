package edu.umass.cs.client;
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
    /* yDev */
    if (i[10] == null) { return 1.2794117647058836; } else if (((Double)i[10]).doubleValue() <= 0.35513543686655424) { return -1.4999999999999991; } else { return 2.1818181818181825; }
  }
}
class ActivityClassifier_0_1 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return 0.22366487857153558; } else if (((Double)i[28]).doubleValue() <= 3.219581926209064) { return 0.8229464358891907; } else { return -1.5520912728386849; }
  }
}
class ActivityClassifier_0_2 {
  public static double classify(Object[] i) {
    /* xDev */
    if (i[1] == null) { return 0.12352137400633939; } else if (((Double)i[1]).doubleValue() <= 0.48535842096484993) { return -1.4037429172474476; } else { return 0.6050427012247214; }
  }
}
class ActivityClassifier_0_3 {
  public static double classify(Object[] i) {
    /* speedMean */
    if (i[27] == null) { return -0.040455317376919875; } else if (((Double)i[27]).doubleValue() <= 4.992850456334283) { return 0.4988164661360562; } else { return -1.1279271152551498; }
  }
}
class ActivityClassifier_0_4 {
  public static double classify(Object[] i) {
    /* xDev */
    if (i[1] == null) { return -0.04995899478263066; } else if (((Double)i[1]).doubleValue() <= 0.48535842096484993) { return -1.208677460513744; } else { return 0.462655058920029; }
  }
}
class ActivityClassifier_0_5 {
  public static double classify(Object[] i) {
    /* energyXYDev */
    if (i[41] == null) { return 0.015440091336704516; } else if (((Double)i[41]).doubleValue() <= 1.0956996432901231) { return 0.4483782902058507; } else { return -1.0302320930122157; }
  }
}
class ActivityClassifier_0_6 {
  public static double classify(Object[] i) {
    /* zDev */
    if (i[19] == null) { return -0.17263158107034127; } else if (((Double)i[19]).doubleValue() <= 0.8027892091851546) { return -1.1411189763742435; } else { return 0.5752046562694112; }
  }
}
class ActivityClassifier_0_7 {
  public static double classify(Object[] i) {
    /* xDev */
    if (i[1] == null) { return -0.2915848003273418; } else if (((Double)i[1]).doubleValue() <= 1.7048962694638117) { return 0.2730889173176697; } else { return -1.0162094392052308; }
  }
}
class ActivityClassifier_0_8 {
  public static double classify(Object[] i) {
    /* zFFT4 */
    if (i[24] == null) { return -0.04872173678931509; } else if (((Double)i[24]).doubleValue() <= 334.6875) { return -0.45728831930969166; } else { return 0.9122423427464772; }
  }
}
class ActivityClassifier_0_9 {
  public static double classify(Object[] i) {
    /* xCrossRate */
    if (i[2] == null) { return 0.1576138650196136; } else if (((Double)i[2]).doubleValue() <= 0.17532467532467533) { return 0.5552482870814774; } else { return -0.9488725444361658; }
  }
}
class ActivityClassifier_1_0 {
  public static double classify(Object[] i) {
    /* xDev */
    if (i[1] == null) { return -0.4852941176470591; } else if (((Double)i[1]).doubleValue() <= 0.17290268304329315) { return 2.9999999999999987; } else { return -1.4999999999999996; }
  }
}
class ActivityClassifier_1_1 {
  public static double classify(Object[] i) {
    /* energyXYMean */
    if (i[40] == null) { return -0.4990532590156607; } else if (((Double)i[40]).doubleValue() <= 0.8475895713135315) { return 1.1030871091952625; } else { return -1.1095705819680313; }
  }
}
class ActivityClassifier_1_2 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return -0.20429229526020135; } else if (((Double)i[28]).doubleValue() <= 0.1840816516427478) { return 1.0551782769519953; } else { return -1.0410370038985142; }
  }
}
class ActivityClassifier_1_3 {
  public static double classify(Object[] i) {
    /* xDev */
    if (i[1] == null) { return -0.40234828243223864; } else if (((Double)i[1]).doubleValue() <= 0.17290268304329315) { return 1.0131267951759784; } else { return -1.0235162859253635; }
  }
}
class ActivityClassifier_1_4 {
  public static double classify(Object[] i) {
    /* yDev */
    if (i[10] == null) { return -0.2101225894949006; } else if (((Double)i[10]).doubleValue() <= 0.1544054706278286) { return 1.0071330503789602; } else { return -1.0085582115899112; }
  }
}
class ActivityClassifier_1_5 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return -0.3913057507362524; } else if (((Double)i[28]).doubleValue() <= 0.1840816516427478) { return 1.0020941971948427; } else { return -1.0035630634391772; }
  }
}
class ActivityClassifier_1_6 {
  public static double classify(Object[] i) {
    /* yDev */
    if (i[10] == null) { return -0.24202285628598028; } else if (((Double)i[10]).doubleValue() <= 0.1544054706278286) { return 1.0011649280133215; } else { return -1.0028320268494133; }
  }
}
class ActivityClassifier_1_7 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return -0.3497408707108012; } else if (((Double)i[28]).doubleValue() <= 0.1840816516427478) { return 1.0003212262798247; } else { return -1.0006506228274477; }
  }
}
class ActivityClassifier_1_8 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return -0.21808912797419805; } else if (((Double)i[28]).doubleValue() <= 0.1840816516427478) { return 1.000155471485789; } else { return -1.0002054232340707; }
  }
}
class ActivityClassifier_1_9 {
  public static double classify(Object[] i) {
    /* speedDev */
    if (i[28] == null) { return -0.16855527127944755; } else if (((Double)i[28]).doubleValue() <= 0.1840816516427478) { return 1.0001018083689093; } else { return -1.0000749766021193; }
  }
}
class ActivityClassifier_2_0 {
  public static double classify(Object[] i) {
    /* speedFFT3 */
    if (i[32] == null) { return -0.7941176470588233; } else if (((Double)i[32]).doubleValue() <= 205.57342048546948) { return -1.3977272727272723; } else { return 3.0000000000000018; }
  }
}
class ActivityClassifier_2_1 {
  public static double classify(Object[] i) {
    /* xDistance */
    if (i[8] == null) { return 0.07263292933818441; } else if (((Double)i[8]).doubleValue() <= 503184.9734673984) { return -0.9159221759917083; } else { return 1.498922253619698; }
  }
}
class ActivityClassifier_2_2 {
  public static double classify(Object[] i) {
    /* yFFT1 */
    if (i[12] == null) { return -0.02594662812296425; } else if (((Double)i[12]).doubleValue() <= 39.77288170722049) { return -0.7980255665564684; } else { return 1.3480382593754185; }
  }
}
class ActivityClassifier_2_3 {
  public static double classify(Object[] i) {
    /* xFFT1 */
    if (i[3] == null) { return 0.2004261108055188; } else if (((Double)i[3]).doubleValue() <= 50.80134511903644) { return -0.8128396174977273; } else { return 0.946384114532977; }
  }
}
class ActivityClassifier_2_4 {
  public static double classify(Object[] i) {
    /* yFFT1 */
    if (i[12] == null) { return 0.12476700422007661; } else if (((Double)i[12]).doubleValue() <= 24.606484292874043) { return -1.0309303346011194; } else { return 0.6426956888202108; }
  }
}
class ActivityClassifier_2_5 {
  public static double classify(Object[] i) {
    /* speedFFT1 */
    if (i[30] == null) { return 0.047409629797579025; } else if (((Double)i[30]).doubleValue() <= 389.44233559407394) { return -0.41489784494711246; } else { return 1.0307145017779111; }
  }
}
class ActivityClassifier_2_6 {
  public static double classify(Object[] i) {
    /* yVelocityChange */
    if (i[16] == null) { return 0.2125312864863575; } else if (((Double)i[16]).doubleValue() <= 4236.59793700181) { return -0.5386510293904382; } else { return 1.1639564055857268; }
  }
}
class ActivityClassifier_2_7 {
  public static double classify(Object[] i) {
    /* xVelocityChange */
    if (i[7] == null) { return 0.32126454652001935; } else if (((Double)i[7]).doubleValue() <= 1332.6706233650698) { return -1.0029752449031042; } else { return 0.626567087741584; }
  }
}
class ActivityClassifier_2_8 {
  public static double classify(Object[] i) {
    /* zFFT4 */
    if (i[24] == null) { return 0.06040490870214001; } else if (((Double)i[24]).doubleValue() <= 334.6875) { return 0.4828312923294871; } else { return -0.947114735913836; }
  }
}
class ActivityClassifier_2_9 {
  public static double classify(Object[] i) {
    /* xCrossRate */
    if (i[2] == null) { return -0.15533792481648742; } else if (((Double)i[2]).doubleValue() <= 0.17532467532467533) { return -0.5591392903739268; } else { return 0.9496196230300271; }
  }
}