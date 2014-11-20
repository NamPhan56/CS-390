package edu.umass.cs.voice;

import android.content.Context;

public class SpeechDetector {
	
	  public static double classify(Object[] i)
	    throws Exception {

	    double p = Double.NaN;
	    p = SpeechDetector.N4cc3fc610(i);
	    return p;
	  }
	  static double N4cc3fc610(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() <= 77.33795295053552) {
	    p = SpeechDetector.N56ee6ba61(i);
	    } else if (((Double) i[0]).doubleValue() > 77.33795295053552) {
	    p = SpeechDetector.N2b72d4a30(i);
	    } 
	    return p;
	  }
	  static double N56ee6ba61(Object []i) {
	    double p = Double.NaN;
	    if (i[2] == null) {
	      p = 1;
	    } else if (((Double) i[2]).doubleValue() <= 4.212643344010947) {
	    p = SpeechDetector.N3edd8be02(i);
	    } else if (((Double) i[2]).doubleValue() > 4.212643344010947) {
	    p = SpeechDetector.N4c2bac4f18(i);
	    } 
	    return p;
	  }
	  static double N3edd8be02(Object []i) {
	    double p = Double.NaN;
	    if (i[8] == null) {
	      p = 1;
	    } else if (((Double) i[8]).doubleValue() <= 2.5748786460132087) {
	    p = SpeechDetector.N430a3f5e3(i);
	    } else if (((Double) i[8]).doubleValue() > 2.5748786460132087) {
	    p = SpeechDetector.Naea7f0b16(i);
	    } 
	    return p;
	  }
	  static double N430a3f5e3(Object []i) {
	    double p = Double.NaN;
	    if (i[1] == null) {
	      p = 0;
	    } else if (((Double) i[1]).doubleValue() <= 6.230494465684298) {
	    p = SpeechDetector.N55c656b44(i);
	    } else if (((Double) i[1]).doubleValue() > 6.230494465684298) {
	    p = SpeechDetector.N1b35db6f8(i);
	    } 
	    return p;
	  }
	  static double N55c656b44(Object []i) {
	    double p = Double.NaN;
	    if (i[2] == null) {
	      p = 0;
	    } else if (((Double) i[2]).doubleValue() <= 3.5814731024520334) {
	    p = SpeechDetector.N50666f6a5(i);
	    } else if (((Double) i[2]).doubleValue() > 3.5814731024520334) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N50666f6a5(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 73.32760709133086) {
	    p = SpeechDetector.N751cc1626(i);
	    } else if (((Double) i[0]).doubleValue() > 73.32760709133086) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N751cc1626(Object []i) {
	    double p = Double.NaN;
	    if (i[11] == null) {
	      p = 0;
	    } else if (((Double) i[11]).doubleValue() <= -0.8696721589740825) {
	      p = 0;
	    } else if (((Double) i[11]).doubleValue() > -0.8696721589740825) {
	    p = SpeechDetector.N32dc44ff7(i);
	    } 
	    return p;
	  }
	  static double N32dc44ff7(Object []i) {
	    double p = Double.NaN;
	    if (i[5] == null) {
	      p = 1;
	    } else if (((Double) i[5]).doubleValue() <= 0.26047440202143535) {
	      p = 1;
	    } else if (((Double) i[5]).doubleValue() > 0.26047440202143535) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N1b35db6f8(Object []i) {
	    double p = Double.NaN;
	    if (i[1] == null) {
	      p = 1;
	    } else if (((Double) i[1]).doubleValue() <= 9.114976864245662) {
	    p = SpeechDetector.N6cd9625d9(i);
	    } else if (((Double) i[1]).doubleValue() > 9.114976864245662) {
	    p = SpeechDetector.N399b6c6915(i);
	    } 
	    return p;
	  }
	  static double N6cd9625d9(Object []i) {
	    double p = Double.NaN;
	    if (i[7] == null) {
	      p = 1;
	    } else if (((Double) i[7]).doubleValue() <= 4.518532271920463) {
	    p = SpeechDetector.N33c8ff7f10(i);
	    } else if (((Double) i[7]).doubleValue() > 4.518532271920463) {
	    p = SpeechDetector.N6d7f31c014(i);
	    } 
	    return p;
	  }
	  static double N33c8ff7f10(Object []i) {
	    double p = Double.NaN;
	    if (i[5] == null) {
	      p = 1;
	    } else if (((Double) i[5]).doubleValue() <= -2.606571960342558) {
	    p = SpeechDetector.N50fe056811(i);
	    } else if (((Double) i[5]).doubleValue() > -2.606571960342558) {
	    p = SpeechDetector.N5515186212(i);
	    } 
	    return p;
	  }
	  static double N50fe056811(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() <= -0.6435961153043146) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() > -0.6435961153043146) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N5515186212(Object []i) {
	    double p = Double.NaN;
	    if (i[6] == null) {
	      p = 1;
	    } else if (((Double) i[6]).doubleValue() <= 3.0262816162400012) {
	      p = 1;
	    } else if (((Double) i[6]).doubleValue() > 3.0262816162400012) {
	    p = SpeechDetector.N5bf3f59113(i);
	    } 
	    return p;
	  }
	  static double N5bf3f59113(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 70.73315285414125) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() > 70.73315285414125) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N6d7f31c014(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() <= 73.24509461280718) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() > 73.24509461280718) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N399b6c6915(Object []i) {
	    double p = Double.NaN;
	    if (i[9] == null) {
	      p = 1;
	    } else if (((Double) i[9]).doubleValue() <= -1.12828641153073) {
	      p = 1;
	    } else if (((Double) i[9]).doubleValue() > -1.12828641153073) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double Naea7f0b16(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() <= -3.5486581980828813) {
	    p = SpeechDetector.N293ab0c617(i);
	    } else if (((Double) i[4]).doubleValue() > -3.5486581980828813) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N293ab0c617(Object []i) {
	    double p = Double.NaN;
	    if (i[1] == null) {
	      p = 0;
	    } else if (((Double) i[1]).doubleValue() <= 6.059144955475833) {
	      p = 0;
	    } else if (((Double) i[1]).doubleValue() > 6.059144955475833) {
	      p = 1;
	    } 
	    return p;
	  }
	  static double N4c2bac4f18(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 73.62563177030262) {
	    p = SpeechDetector.N473d9d9a19(i);
	    } else if (((Double) i[0]).doubleValue() > 73.62563177030262) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N473d9d9a19(Object []i) {
	    double p = Double.NaN;
	    if (i[5] == null) {
	      p = 0;
	    } else if (((Double) i[5]).doubleValue() <= 1.5941884203634675) {
	    p = SpeechDetector.N1e360e0020(i);
	    } else if (((Double) i[5]).doubleValue() > 1.5941884203634675) {
	    p = SpeechDetector.N333d612e29(i);
	    } 
	    return p;
	  }
	  static double N1e360e0020(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() <= 65.53626649955955) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() > 65.53626649955955) {
	    p = SpeechDetector.N6ed1317e21(i);
	    } 
	    return p;
	  }
	  static double N6ed1317e21(Object []i) {
	    double p = Double.NaN;
	    if (i[2] == null) {
	      p = 0;
	    } else if (((Double) i[2]).doubleValue() <= 6.091447057560059) {
	    p = SpeechDetector.N6808800822(i);
	    } else if (((Double) i[2]).doubleValue() > 6.091447057560059) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N6808800822(Object []i) {
	    double p = Double.NaN;
	    if (i[10] == null) {
	      p = 1;
	    } else if (((Double) i[10]).doubleValue() <= -2.208306852200045) {
	    p = SpeechDetector.N60dc29423(i);
	    } else if (((Double) i[10]).doubleValue() > -2.208306852200045) {
	    p = SpeechDetector.N7cc4613b26(i);
	    } 
	    return p;
	  }
	  static double N60dc29423(Object []i) {
	    double p = Double.NaN;
	    if (i[1] == null) {
	      p = 1;
	    } else if (((Double) i[1]).doubleValue() <= 8.075055859585094) {
	    p = SpeechDetector.N716185a624(i);
	    } else if (((Double) i[1]).doubleValue() > 8.075055859585094) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N716185a624(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 0;
	    } else if (((Double) i[4]).doubleValue() <= -3.4646158862042324) {
	      p = 0;
	    } else if (((Double) i[4]).doubleValue() > -3.4646158862042324) {
	    p = SpeechDetector.N398d8f7125(i);
	    } 
	    return p;
	  }
	  static double N398d8f7125(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() <= 72.49626799595728) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() > 72.49626799595728) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N7cc4613b26(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() <= -3.4289598952461655) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() > -3.4289598952461655) {
	    p = SpeechDetector.N40cba87b27(i);
	    } 
	    return p;
	  }
	  static double N40cba87b27(Object []i) {
	    double p = Double.NaN;
	    if (i[8] == null) {
	      p = 0;
	    } else if (((Double) i[8]).doubleValue() <= 1.0684877328217695) {
	      p = 0;
	    } else if (((Double) i[8]).doubleValue() > 1.0684877328217695) {
	    p = SpeechDetector.N7aa2447828(i);
	    } 
	    return p;
	  }
	  static double N7aa2447828(Object []i) {
	    double p = Double.NaN;
	    if (i[7] == null) {
	      p = 1;
	    } else if (((Double) i[7]).doubleValue() <= 0.04733114056617844) {
	      p = 1;
	    } else if (((Double) i[7]).doubleValue() > 0.04733114056617844) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N333d612e29(Object []i) {
	    double p = Double.NaN;
	    if (i[7] == null) {
	      p = 1;
	    } else if (((Double) i[7]).doubleValue() <= 4.62476807459112) {
	      p = 1;
	    } else if (((Double) i[7]).doubleValue() > 4.62476807459112) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N2b72d4a30(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 0;
	    } else if (((Double) i[4]).doubleValue() <= 1.761929404832566) {
	    p = SpeechDetector.N47fe56aa31(i);
	    } else if (((Double) i[4]).doubleValue() > 1.761929404832566) {
	    p = SpeechDetector.N1a6633f33(i);
	    } 
	    return p;
	  }
	  static double N47fe56aa31(Object []i) {
	    double p = Double.NaN;
	    if (i[9] == null) {
	      p = 0;
	    } else if (((Double) i[9]).doubleValue() <= 4.768397923665101) {
	      p = 0;
	    } else if (((Double) i[9]).doubleValue() > 4.768397923665101) {
	    p = SpeechDetector.Naebd7d332(i);
	    } 
	    return p;
	  }
	  static double Naebd7d332(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() <= 81.31404403584891) {
	      p = 1;
	    } else if (((Double) i[0]).doubleValue() > 81.31404403584891) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N1a6633f33(Object []i) {
	    double p = Double.NaN;
	    if (i[4] == null) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() <= 1.8158788043764014) {
	      p = 1;
	    } else if (((Double) i[4]).doubleValue() > 1.8158788043764014) {
	    p = SpeechDetector.N52bdbdf134(i);
	    } 
	    return p;
	  }
	  static double N52bdbdf134(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 79.56134385567455) {
	    p = SpeechDetector.N270143a735(i);
	    } else if (((Double) i[0]).doubleValue() > 79.56134385567455) {
	      p = 0;
	    } 
	    return p;
	  }
	  static double N270143a735(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 79.21365923924782) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() > 79.21365923924782) {
	      p = 1;
	    } 
	    return p;
	  }
	}
