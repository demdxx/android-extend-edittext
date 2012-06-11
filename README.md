android-extend-edittext
=======================

This is an extension of the control EditText with built-in validation of the content.

Example
-------

The test application with the implementation of different types of validation.
[Google Play](https://play.google.com/store/apps/details?id=com.extend.example).

![Examples](http://lh4.ggpht.com/zJ4KpsN1adE_adMkQ2-axJZKO8rOv9hRoMppPWAiHwmh0flJ_v3BqIMDhawf-sK7MMbX)

How to
======

Import the namespace to the application.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:something="http://schemas.android.com/apk/res/your.package.name"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
    ....
    <!-- Content... -->
    ....
</LinearLayout>
```

**something** is a keyword that will be available for an additional property of the control.

Params
------

- **validate**: combination of flags validation. **regexp|numeric|alpha|alphaNumeric|email|creditCard|phone|domainName|ipAddress|webUrl|empty**. Default: **regexp**.
- **validatorRegexp**: regular expression valid in the presence of the flag **regexp**.
- **errorString**: custom error message.
- **emptyErrorString**: custom empty value error message.
- **auto**: automatic validate.

Example
=======

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:wiget="http://schemas.android.com/apk/res/com.extend.example"
    android:id="@+id/scroll" android:layout_width="fill_parent"
    android:layout_height="wrap_content">
	<LinearLayout android:layout_width="fill_parent"
	    android:layout_height="wrap_content"
	    android:orientation="vertical" >

	    <com.extend.wiget.ExtEditText
	        wiget:validate="regexp|empty"
	        wiget:validatorRegexp="^\\w+$"
	        wiget:errorString="Custom error message!"
	        wiget:emptyErrorString="Custom empty error message!"
	        wiget:auto="true"
	        android:id="@+id/extEditText10"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:hint="Regular expression"
	        android:text="">
	    </com.extend.wiget.ExtEditText>
	
	</LinearLayout>
</ScrollView>
```

An example of validation of the code of Java.

```java
	public void onClickNext(View v) {
		ExtEditText[] field	= { mName, mLastname, mCity, mCountry, mPhone };

		boolean valid = true;
		for (ExtEditText field: allFields) {
			valid = field.testValidity() && valid;
		}
		
		if (valid) {
			// Ok
		} else {
			// Invalid
		}
	}
```

Custom validator
================

An example of a validator.

```java
class ExampleValidator extends Validator {

	public ExampleValidator() {
		super("Error message...");
	}
	
	@Override
	public boolean check(EditText et) {
		// Check code ...
	}

}
```

Autor
=====

 * Dmitry Ponomarev <demdxx@gmail.com>



