package com.extend.editetxtvalidators;

/**
 * Credit card validator extend;
 * @author Dmitry Ponomarev <demdxx@gmail.com>
 * @year 2012
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

public class CreditCardValidator extends Validator {
    public static final int MASTERCARD = 0, VISA = 1;
    public static final int AMEX = 2, DISCOVER = 3, DINERS = 4;
    public static final int[] CARDS = {MASTERCARD, VISA, AMEX, DISCOVER, DINERS};
    
    public int validatedType = -1;

	public CreditCardValidator(String error) {
		super(error);
	}

	@Override
	public boolean check(EditText e) {
        String number = e.getText().toString();
        Matcher m = Pattern.compile("[^\\d\\s.-]").matcher(number);
        if (m.find()) return false;

        Matcher matcher = Pattern.compile("[\\s.-]").matcher(number);
        number = matcher.replaceAll("");

        validatedType = -1;
        for (int type : CARDS) {
        	if (validate(number, type)) {
        		validatedType = type;
        		return true;
        	}
        }
        return false;
	}
 
    // Check that cards start with proper digits for
    // selected card type and are also the right length.    
 
    private boolean validate(String number, int type) {
        switch(type) {
		
        case MASTERCARD:
            if (number.length() != 16 ||
                Integer.parseInt(number.substring(0, 2)) < 51 ||
                Integer.parseInt(number.substring(0, 2)) > 55)
            {
                return false;
            }
            break;
			
        case VISA:
            if ((number.length() != 13 && number.length() != 16) ||
                    Integer.parseInt(number.substring(0, 1)) != 4)
            {
                return false;
            }
            break;
			
        case AMEX:
            if (number.length() != 15 ||
                (Integer.parseInt(number.substring(0, 2)) != 34 &&
                    Integer.parseInt(number.substring(0, 2)) != 37))
            {
                return false;
            }
            break;
			
        case DISCOVER:
            if (number.length() != 16 ||
                Integer.parseInt(number.substring(0, 5)) != 6011)
            {
                return false;
            }
            break;
			
        case DINERS:
            if (number.length() != 14 ||
                ((Integer.parseInt(number.substring(0, 2)) != 36 &&
                    Integer.parseInt(number.substring(0, 2)) != 38) &&
                    Integer.parseInt(number.substring(0, 3)) < 300 ||
                        Integer.parseInt(number.substring(0, 3)) > 305))
            {
                return false;
            }
            break;
        }
        return luhnValidate(number);
    }
 
    // The Luhn algorithm is basically a CRC type
    // system for checking the validity of an entry.
    // All major credit cards use numbers that will
    // pass the Luhn check. Also, all of them are based
    // on MOD 10.
	
    private boolean luhnValidate(String numberString) {
        char[] charArray = numberString.toCharArray();
        int[] number = new int[charArray.length];
        int total = 0;
		
        for (int i=0; i < charArray.length; i++) {
            number[i] = Character.getNumericValue(charArray[i]);
        }
		
        for (int i = number.length-2; i > -1; i-=2) {
            number[i] *= 2;
			
            if (number[i] > 9)
                number[i] -= 9;
        }
		
        for (int i=0; i < number.length; i++)
            total += number[i];
		
            if (total % 10 != 0)
                return false;
		
        return true;
    }

}
