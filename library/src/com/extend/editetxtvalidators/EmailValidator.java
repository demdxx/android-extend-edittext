package com.extend.editetxtvalidators;

/**
 * Email validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Pattern;

import android.os.Build;

public class EmailValidator extends RegExpValidator {

	public static Pattern CNDITIONEXP;
	
	static {
		if (Build.VERSION.SDK_INT>=8) {
			CNDITIONEXP = android.util.Patterns.EMAIL_ADDRESS;
		}
		else {
			CNDITIONEXP = Pattern.compile(
							"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
							"\\@" +
							"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
							"(" +
							"\\." +
							"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
							")+"
						);
		}
	}

	public EmailValidator(String error) {
		super(CNDITIONEXP, error);
	}
}
