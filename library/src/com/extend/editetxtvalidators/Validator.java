package com.extend.editetxtvalidators;

/**
 * Validator is abstract class, for using in ExtEditText.
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import android.widget.EditText;

public abstract class Validator {
	protected String errorMessage;
	public Validator(String error) {
		errorMessage = error;
	}
	
	public abstract boolean check(EditText e);

	public String getErrorMessage(EditText e) {
		return errorMessage;
	}
}
