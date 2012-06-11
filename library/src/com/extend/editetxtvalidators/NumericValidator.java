package com.extend.editetxtvalidators;

/**
 * Numeric validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import android.text.TextUtils;
import android.widget.EditText;

public class NumericValidator extends Validator {
	public NumericValidator(String error) {
		super(error);
	}

	@Override
	public boolean check(EditText e) {
		return TextUtils.isDigitsOnly(e.getText());
	}
}
