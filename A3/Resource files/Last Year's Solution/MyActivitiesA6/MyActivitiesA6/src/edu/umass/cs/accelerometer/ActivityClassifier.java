/**
 * 
 */
package edu.umass.cs.accelerometer;

/**
 * @author Abhinav Parate
 * 
 */
public class ActivityClassifier {

	  public static double classify(Object[] i)
	    throws Exception {

	    double p = Double.NaN;
	    p = ActivityClassifier.Nc23c5ff0(i);
	    return p;
	  }
	  static double Nc23c5ff0(Object []i) {
	    double p = Double.NaN;
	    if (i[34] == null) {
	      p = 1;
	    } else if (((Double) i[34]).doubleValue() <= 1.064211666883456) {
	    p = ActivityClassifier.N9bee931(i);
	    } else if (((Double) i[34]).doubleValue() > 1.064211666883456) {
	    p = ActivityClassifier.N6202bc299(i);
	    } 
	    return p;
	  }
	  static double N9bee931(Object []i) {
	    double p = Double.NaN;
	    if (i[33] == null) {
	      p = 1;
	    } else if (((Double) i[33]).doubleValue() <= 10.393982717479389) {
	    p = ActivityClassifier.N7d4bf5342(i);
	    } else if (((Double) i[33]).doubleValue() > 10.393982717479389) {
	    p = ActivityClassifier.N1ccd2bfc8(i);
	    } 
	    return p;
	  }
	  static double N7d4bf5342(Object []i) {
	    double p = Double.NaN;
	    if (i[21] == null) {
	      p = 1;
	    } else if (((Double) i[21]).doubleValue() <= 868.6590364824083) {
	    p = ActivityClassifier.N5cf69303(i);
	    } else if (((Double) i[21]).doubleValue() > 868.6590364824083) {
	    p = ActivityClassifier.N7d304ef46(i);
	    } 
	    return p;
	  }
	  static double N5cf69303(Object []i) {
	    double p = Double.NaN;
	    if (i[10] == null) {
	      p = 1;
	    } else if (((Double) i[10]).doubleValue() <= 0.604320414809842) {
	      p = 1;
	    } else if (((Double) i[10]).doubleValue() > 0.604320414809842) {
	    p = ActivityClassifier.N7402d14a4(i);
	    } 
	    return p;
	  }
	  static double N7402d14a4(Object []i) {
	    double p = Double.NaN;
	    if (i[33] == null) {
	      p = 1;
	    } else if (((Double) i[33]).doubleValue() <= 9.943550569635939) {
	    p = ActivityClassifier.N64fa8cc65(i);
	    } else if (((Double) i[33]).doubleValue() > 9.943550569635939) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N64fa8cc65(Object []i) {
	    double p = Double.NaN;
	    if (i[41] == null) {
	      p = 0;
	    } else if (((Double) i[41]).doubleValue() <= 1.1180826954411711) {
	      p = 0;
	    } else if (((Double) i[41]).doubleValue() > 1.1180826954411711) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N7d304ef46(Object []i) {
	    double p = Double.NaN;
	    if (i[5] == null) {
	      p = 1;
	    } else if (((Double) i[5]).doubleValue() <= 1.2896452082951089) {
	    p = ActivityClassifier.N6e8fb5617(i);
	    } else if (((Double) i[5]).doubleValue() > 1.2896452082951089) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N6e8fb5617(Object []i) {
	    double p = Double.NaN;
	    if (i[27] == null) {
	      p = 1;
	    } else if (((Double) i[27]).doubleValue() <= 0.06296873617937018) {
	      p = 1;
	    } else if (((Double) i[27]).doubleValue() > 0.06296873617937018) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N1ccd2bfc8(Object []i) {
	    double p = Double.NaN;
	    if (i[34] == null) {
	      p = 1;
	    } else if (((Double) i[34]).doubleValue() <= 0.4921876210632781) {
	      p = 1;
	    } else if (((Double) i[34]).doubleValue() > 0.4921876210632781) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N6202bc299(Object []i) {
	    double p = Double.NaN;
	    if (i[27] == null) {
	      p = 0;
	    } else if (((Double) i[27]).doubleValue() <= 14.691042312171158) {
	    p = ActivityClassifier.N218f5a0410(i);
	    } else if (((Double) i[27]).doubleValue() > 14.691042312171158) {
	    p = ActivityClassifier.N3535a92b13(i);
	    } 
	    return p;
	  }
	  static double N218f5a0410(Object []i) {
	    double p = Double.NaN;
	    if (i[34] == null) {
	      p = 0;
	    } else if (((Double) i[34]).doubleValue() <= 1.2659478519921943) {
	    p = ActivityClassifier.N4a5ecdd211(i);
	    } else if (((Double) i[34]).doubleValue() > 1.2659478519921943) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N4a5ecdd211(Object []i) {
	    double p = Double.NaN;
	    if (i[33] == null) {
	      p = 1;
	    } else if (((Double) i[33]).doubleValue() <= 10.388504241782725) {
	    p = ActivityClassifier.N161abc2312(i);
	    } else if (((Double) i[33]).doubleValue() > 10.388504241782725) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N161abc2312(Object []i) {
	    double p = Double.NaN;
	    if (i[6] == null) {
	      p = 0;
	    } else if (((Double) i[6]).doubleValue() <= 191.25) {
	      p = 0;
	    } else if (((Double) i[6]).doubleValue() > 191.25) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N3535a92b13(Object []i) {
	    double p = Double.NaN;
	    if (i[9] == null) {
	      p = 0;
	    } else if (((Double) i[9]).doubleValue() <= 0.07092596055369291) {
	    p = ActivityClassifier.N55f5615714(i);
	    } else if (((Double) i[9]).doubleValue() > 0.07092596055369291) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N55f5615714(Object []i) {
	    double p = Double.NaN;
	    if (i[2] == null) {
	      p = 1;
	    } else if (((Double) i[2]).doubleValue() <= 0.42857142857142855) {
	      p = 1;
	    } else if (((Double) i[2]).doubleValue() > 0.42857142857142855) {
	      p = 0;
	    } 
	    return p;
	  }
	}
