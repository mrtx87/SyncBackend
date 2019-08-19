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