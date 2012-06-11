package com.extend.editetxtvalidators;

/**
 * Web URL validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Pattern;

import android.os.Build;

public class WebUrlValidator extends RegExpValidator {

	public static Pattern CNDITIONEXP;
	
	static {
		if (Build.VERSION.SDK_INT>=8) {
			CNDITIONEXP = 	android.util.Patterns.WEB_URL;
		}
		else {
			CNDITIONEXP =   Pattern.compile(
					"^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/)([\\w-.]+[^#?\\s]+)(.*)?(#[\\w-]+)?$");
		}
	}

	public WebUrlValidator(String error) {
		super(CNDITIONEXP, error);
	}

}
