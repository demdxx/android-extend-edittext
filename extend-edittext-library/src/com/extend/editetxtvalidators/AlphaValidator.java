package com.extend.editetxtvalidators;

/**
 * Alphabet validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

public class AlphaValidator extends RegExpValidator {
	public AlphaValidator(String error) {
		super("^[a-zA-Z \\.,:;/-]*$", error);
	}
}
