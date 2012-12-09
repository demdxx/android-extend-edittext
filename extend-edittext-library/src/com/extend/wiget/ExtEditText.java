package com.extend.wiget;

import java.util.ArrayList;

import com.extend.editetxtvalidators.AlphaNumericValidator;
import com.extend.editetxtvalidators.AlphaValidator;
import com.extend.editetxtvalidators.CreditCardValidator;
import com.extend.editetxtvalidators.DomainValidator;
import com.extend.editetxtvalidators.EmailValidator;
import com.extend.editetxtvalidators.IpValidator;
import com.extend.editetxtvalidators.NumericValidator;
import com.extend.editetxtvalidators.OrValidator;
import com.extend.editetxtvalidators.PhoneValidator;
import com.extend.editetxtvalidators.RegExpValidator;
import com.extend.editetxtvalidators.Validator;
import com.extend.editetxtvalidators.WebUrlValidator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class ExtEditText extends EditText {
    private static final int TEST_REGEXP		= 0x0001;
    private static final int TEST_NUMERIC		= 0x0002;
    private static final int TEST_ALPHA			= 0x0004;
    private static final int TEST_ALPHANUMERIC	= 0x0008;
    private static final int TEST_EMAIL			= 0x0010;
    private static final int TEST_CREDITCARD	= 0x0020;
    private static final int TEST_PHONE			= 0x0040;
    private static final int TEST_DOMAINNAME	= 0x0080;
    private static final int TEST_IPADDRESS		= 0x0100;
    private static final int TEST_WEBURL		= 0x0200;
    private static final int TEST_EMPTY			= 0x0400;
    
    private static final int TEST_ALL			= 0x07ff;

    private static final int TEST_NOCHECK		= 0;
    
    private static final int AUTOTEST_TIMER		= 1000;
    
    /**
     * @var Validators heap
     */
    private boolean emptyAllowed = true;
    private boolean autoTest = true;
    private String errorString = null;
    private String emptyErrorString = null;
    protected ArrayList<Validator> validators = new ArrayList<Validator>();
    
    /**
     * Constructor
     * @param context
     * @param attrs
     */
	public ExtEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initControl(context, attrs);
	}
    
    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
	public ExtEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initControl(context, attrs);
	}
	
	/**
	 * Init control params
	 * @param context
	 * @param attrs
	 */
	protected void initControl(Context context, AttributeSet attrs) {
	    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtEditText);
	    autoTest = typedArray.getBoolean(R.styleable.ExtEditText_auto, true);
	    errorString = typedArray.getString(R.styleable.ExtEditText_errorString);
	    emptyErrorString = typedArray.getString(R.styleable.ExtEditText_emptyErrorString);
	    
	    if (null==emptyErrorString || TextUtils.isEmpty(emptyErrorString))
	    	emptyErrorString = getResources().getString(R.string.error_field_must_not_be_empty);

	    addValidator(
	    	typedArray.getInt(R.styleable.ExtEditText_validate, TEST_REGEXP|TEST_NOCHECK),
	    	errorString,
	    	typedArray.getString(R.styleable.ExtEditText_validatorRegexp)
	    );
	    typedArray.recycle();
	    
	    addTextChangedListener(errorValidateTextWatcher);
	}

	/**
	 * Add validator by code
	 * @param code
	 * @param error
	 * @param regExp
	 * @return
	 */
	public boolean addValidator(int code, String error, String regExp) {
		if (0==(code|TEST_ALL))
			return false;

		OrValidator v = new OrValidator();
		if ((0==code || (code&TEST_REGEXP)!=0) && null!=regExp) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_regexp_not_valid);
			v.addValidator(new RegExpValidator(regExp, error));
		}
		if ((code&TEST_NUMERIC)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_this_field_cannot_contain_special_character);
			v.addValidator(new NumericValidator(error));
		}
		if ((code&TEST_ALPHA)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_only_standard_letters_are_allowed);
			v.addValidator(new AlphaValidator(error));
		}
		if ((code&TEST_ALPHANUMERIC)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_this_field_cannot_contain_special_character);
			v.addValidator(new AlphaNumericValidator(error));
		}
		if ((code&TEST_EMAIL)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_email_address_not_valid);
			v.addValidator(new EmailValidator(error));
		}
		if ((code&TEST_CREDITCARD)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_creditcard_number_not_valid);
			v.addValidator(new CreditCardValidator(error));
		}
		if ((code&TEST_PHONE)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_phone_not_valid);
			v.addValidator(new PhoneValidator(error));
		}
		if ((code&TEST_DOMAINNAME)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_domain_not_valid);
			v.addValidator(new DomainValidator(error));
		}
		if ((code&TEST_IPADDRESS)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_ip_not_valid);
			v.addValidator(new IpValidator(error));
		}
		if ((code&TEST_WEBURL)!=0) {
			if (null==error || TextUtils.isEmpty(error))
				error = getResources().getString(R.string.error_url_not_valid);
			v.addValidator(new WebUrlValidator(error));
		}
		if ((code&TEST_EMPTY)!=0)
			emptyAllowed = true;
		else
			emptyAllowed = false;
		
		if (0<v.getCount())
			validators.add(v);

		return true;
	}
	
	/**
	 * Add validator object
	 * @param v
	 */
	public void addValidator(Validator v) {
		if (null!=v)
			validators.add(v);
	}
	
	/**
	 * Check validate
	 * @return
	 */
	public boolean isValid() {
		if (getText().toString().length()<1) {
			if (emptyAllowed) {
				return true;
			}
			else {
				setError(emptyErrorString);
				return false;
			}
		}
		boolean b = false;
		if (null!=validators) {
			for (Validator v : validators) {
				try { b = v.check(this); } catch (Exception e) { e.printStackTrace(); b = false; }
				if (!b) {
					String error = v.getErrorMessage(this);
					if (error==null || TextUtils.isEmpty(error))
						error = errorString;
					if (error!=null && !TextUtils.isEmpty(error))
						setError(error);
					return false;
				}
			}
		}
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Events
	//
	@Override
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)  {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (autoTest && !focused && null==getError()) isValid();
	}

	///////////////////////////////////////////////////////////////////////////////////
    // Error validate watcher
    //
    private TextWatcher errorValidateTextWatcher = new TextWatcher() {
    	@Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        	if (s!=null && s.length()>0 && getError()!=null) {
        		setError(null);
          	}
        	if (autoTest) {
				__handler_timer.removeCallbacks(__task_checker);
				__handler_timer.postDelayed(__task_checker, AUTOTEST_TIMER);
        	}
        }
        
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

	///////////////////////////////////////////////////////////////////////////////////
    // Timer
    //
    private Runnable __task_checker = new Runnable() {
		@Override public void run() { isValid(); }
    };
	private Handler __handler_timer = new Handler();
}
