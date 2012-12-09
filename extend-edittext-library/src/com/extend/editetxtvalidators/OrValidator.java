package com.extend.editetxtvalidators;

/**
 * OR validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.ArrayList;

import android.widget.EditText;

public class OrValidator extends Validator {
	
	protected ArrayList<Validator> validators = new ArrayList<Validator>();
	protected String error2 = null;

	public OrValidator() {
		super(null);
	}

	public OrValidator(String error) {
		super(error);
	}
	
	public void addValidator(Validator v) {
		if (null!=v)
			validators.add(v);
	}
	
	public int getCount() {
		return validators.size();
	}

	@Override
	public String getErrorMessage(EditText e) {
		return null!=error2 ? error2 : errorMessage;
	}

	@Override
	public boolean check(EditText e) {
		for (Validator v : validators) {
			try {
				if (v.check(e))
					return true;
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			error2 = v.getErrorMessage(e);
		}
		return false;
	}

}
