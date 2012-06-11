package com.extend.editetxtvalidators;

/**
 * RegExp validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Pattern;

import android.widget.EditText;

public class RegExpValidator extends Validator {

	protected Pattern pattern;
	
	public RegExpValidator(String regexp, String error) {
		super(error);
		pattern = Pattern.compile(regexp);
	}
	
	public RegExpValidator(Pattern ptr, String error) {
		super(error);
		pattern = ptr;
	}

	@Override
	public boolean check(EditText e) {
		return pattern.matcher(e.getText()).matches();
	}

}
