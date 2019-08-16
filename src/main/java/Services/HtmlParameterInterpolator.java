package Services;

import java.util.HashMap;

public class HtmlParameterInterpolator {
	
	public static String interpolate(String toInterpolate, HashMap<String, String> parameters) {
		for(String key : parameters.keySet()) {
			toInterpolate = toInterpolate.replace("${"+key+"}", parameters.get(key));
		}
		return toInterpolate;
	}
}

//		if(toInterpolate != null) {
//			for(int i = 0; i < toInterpolate.length(); i++) {
//				if(toInterpolate.charAt(i) == '$') {
//					int begin = i+2;
//					String rest = toInterpolate.substring(begin);
//					int end = rest.indexOf('}');
//					if(end < 0) {
//						System.err.println("Interpolate parameters definition error.");
//						break;
//					}
//					String key = toInterpolate.substring(begin, end);
//					String parameter = parameters.get(key);
//					toInterpolate.replace("${"+key+"}", toInterpol);
//				}
//			}
//		}