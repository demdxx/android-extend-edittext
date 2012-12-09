package com.extend.editetxtvalidators;

/**
 * Phone validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Pattern;

import android.os.Build;

public class PhoneValidator extends RegExpValidator {

	public static Pattern CNDITIONEXP;
	
	static {
		if (Build.VERSION.SDK_INT>=8) {
			CNDITIONEXP = 	android.util.Patterns.PHONE;
		}
		else {
			CNDITIONEXP =   Pattern.compile("^(\\d{3}-\\d{7}|\\d{10,11})$");
		}
	}

	public PhoneValidator(String error) {
		super(CNDITIONEXP, error);
	}

}
