package edu.umass.cs.accelerometer;

public class ActivityClassifier {

  public static double classify(Object[] i)
    throws Exception {

    double p = Double.NaN;
    p = ActivityClassifier.N9226910(i);
    return p;
  }
  static double N9226910(Object []i) {
    double p = Double.NaN;
    if (i[10] == null) {
      p = 1;
    } else if (((Double) i[10]).doubleValue() <= 0.21192993680436856) {
    p = ActivityClassifier.N6cedbc841(i);
    } else if (((Double) i[10]).doubleValue() > 0.21192993680436856) {
    p = ActivityClassifier.N86f4f7c3(i);
    } 
    return p;
  }
  static double N6cedbc841(Object []i) {
    double p = Double.NaN;
    if (i[27] == null) {
      p = 1;
    } else if (((Double) i[27]).doubleValue() <= 2.557946099404869) {
    p = ActivityClassifier.N6d21c00c2(i);
    } else if (((Double) i[27]).doubleValue() > 2.557946099404869) {
      p = 2;
    } 
    return p;
  }
  static double N6d21c00c2(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.34375) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.34375) {
      p = 2;
    } 
    return p;
  }
  static double N86f4f7c3(Object []i) {
    double p = Double.NaN;
    if (i[40] == null) {
      p = 2;
    } else if (((Double) i[40]).doubleValue() <= 1.85384499013501) {
    p = ActivityClassifier.N19d8564e4(i);
    } else if (((Double) i[40]).doubleValue() > 1.85384499013501) {
    p = ActivityClassifier.N974e67a7(i);
    } 
    return p;
  }
  static double N19d8564e4(Object []i) {
    double p = Double.NaN;
    if (i[24] == null) {
      p = 2;
    } else if (((Double) i[24]).doubleValue() <= 337.5) {
    p = ActivityClassifier.N18d0d06d5(i);
    } else if (((Double) i[24]).doubleValue() > 337.5) {
      p = 1;
    } 
    return p;
  }
  static double N18d0d06d5(Object []i) {
    double p = Double.NaN;
    if (i[11] == null) {
      p = 2;
    } else if (((Double) i[11]).doubleValue() <= 0.07142857142857142) {
    p = ActivityClassifier.N7b9242db6(i);
    } else if (((Double) i[11]).doubleValue() > 0.07142857142857142) {
      p = 2;
    } 
    return p;
  }
  static double N7b9242db6(Object []i) {
    double p = Double.NaN;
    if (i[11] == null) {
      p = 2;
    } else if (((Double) i[11]).doubleValue() <= 0.05) {
      p = 2;
    } else if (((Double) i[11]).doubleValue() > 0.05) {
      p = 1;
    } 
    return p;
  }
  static double N974e67a7(Object []i) {
    double p = Double.NaN;
    if (i[36] == null) {
      p = 0;
    } else if (((Double) i[36]).doubleValue() <= 323.66575421754953) {
    p = ActivityClassifier.N5245ab3c8(i);
    } else if (((Double) i[36]).doubleValue() > 323.66575421754953) {
    p = ActivityClassifier.N25e55d6110(i);
    } 
    return p;
  }
  static double N5245ab3c8(Object []i) {
    double p = Double.NaN;
    if (i[17] == null) {
      p = 0;
    } else if (((Double) i[17]).doubleValue() <= 2193247.10088709) {
      p = 0;
    } else if (((Double) i[17]).doubleValue() > 2193247.10088709) {
    p = ActivityClassifier.N4c084aa19(i);
    } 
    return p;
  }
  static double N4c084aa19(Object []i) {
    double p = Double.NaN;
    if (i[20] == null) {
      p = 0;
    } else if (((Double) i[20]).doubleValue() <= 0.12903225806451613) {
      p = 0;
    } else if (((Double) i[20]).doubleValue() > 0.12903225806451613) {
      p = 2;
    } 
    return p;
  }
  static double N25e55d6110(Object []i) {
    double p = Double.NaN;
    if (i[13] == null) {
      p = 2;
    } else if (((Double) i[13]).doubleValue() <= 11.25) {
      p = 2;
    } else if (((Double) i[13]).doubleValue() > 11.25) {
      p = 1;
    } 
    return p;
  }
}