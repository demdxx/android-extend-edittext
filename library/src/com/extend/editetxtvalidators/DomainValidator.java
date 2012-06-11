package com.extend.editetxtvalidators;

/**
 * Domain name validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Pattern;

import android.os.Build;

public class DomainValidator extends RegExpValidator {

	public static Pattern CNDITIONEXP;
	
	static {
		if (Build.VERSION.SDK_INT>=8) {
			CNDITIONEXP = 	android.util.Patterns.DOMAIN_NAME;
		}
		else {
			CNDITIONEXP =   Pattern.compile("^[a-zA-Z0-9]+\\.[a-zA-Z0-9\\.]+$");
		}
	}

	public DomainValidator(String error) {
		super(CNDITIONEXP, error);
	}

}
