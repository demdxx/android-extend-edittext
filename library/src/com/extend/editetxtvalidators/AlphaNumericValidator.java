package com.extend.editetxtvalidators;

/**
 * Alphabet and numeric validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

public class AlphaNumericValidator extends RegExpValidator {
	public AlphaNumericValidator(String error) {
		super("^[a-zA-Z0-9 \\./-]*$", error);
	}
}
