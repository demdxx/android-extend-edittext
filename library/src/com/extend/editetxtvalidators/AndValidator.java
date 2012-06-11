package com.extend.editetxtvalidators;

/**
 * AND validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import android.widget.EditText;

public class AndValidator extends OrValidator {

	public AndValidator() {
		super();
	}

	public AndValidator(String error) {
		super(error);
	}

	@Override
	public boolean check(EditText e) {
		for (Validator v : validators) {
			try {
				if (!v.check(e)) {
					error2 = v.getErrorMessage(e);
					return false;
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				error2 = v.getErrorMessage(e);
				return false;
			}
		}
		return true;
	}

}
