package com.brots.music.application.apiInterface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class validations {

	CoordinatorLayout cdl;

	public validations(CoordinatorLayout coordinatorLayout) {

		cdl = coordinatorLayout;
	}


	public static boolean match(String str) {
		Pattern ps = Pattern.compile("^[a-zA-Z -?.,]+$");
		Matcher ms = ps.matcher(str);
		boolean bs = ms.matches();

		return bs;

	}

	public static boolean match1(String str)
	{
	    Pattern ps = Pattern.compile("^[a-zA-Z0-9._]+$");
		Matcher ms = ps.matcher(str);
		boolean bs = ms.matches();
		return bs;

	}


	String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	private static boolean IsMatch(String s, String pattern) {
		try {
			Pattern patt = Pattern.compile(pattern);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}


	private boolean isValidEmaillId(String email) {

//	     return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
//	               + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//	               + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//	               + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//	               + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
//	               + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();

		return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
	}

	public static String integerpattern = "[0-9]+";
	//public static String phonepattern = "[0-9]{10}+";
	public static String phonepattern = "[0-9]+";
	//public static String phonepattern="((\\+*)(0*|(0 )*|(0-)*|(91 )*)(\\d{12}+|\\d{10}+))|\\d{5}([- ]*)\\d{6}";

	//**********************************Login with email************************


	//**********************************Login with email************************
	public boolean Loginvalidate(Context context, String username, String userpassword) {

		if (username.length() == 0 && userpassword.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (username.length() == 0) {
			String message = "Please enter email address /phone no/username ";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (!isValidEmaillId(username.trim())) {

			String message = "Please enter a valid username/or phone no or email adddress ";
			// showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}
		if (userpassword.length() == 0) {
			String message = "Please enter password";
			snackbarMessage(context, message);
			//showMessageOKCancel(context, message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	//**********************************Login with email************************
	public boolean Loginvalidate_phone(Context context, String username, String userpassword) {

		if (username.length() == 0 ||userpassword.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);

			return false;
		}

		if (username.length() == 0) {
			String message = "Please enter email address /phone no/username ";

			snackbarMessage(context, message);

			return false;
		}
	     if (!(username.matches("\\d{10}")))
	     {
			 String message = "Please enter a valid username/or phone no or email adddress ";

			 snackbarMessage(context, message);

			 return false;
		 }

		if ((username.length()<10)||(username.length()>15))
		{
			String message = "Phone no length should be min 10 and max 15 digits.";

			snackbarMessage(context, message);

			return false;
		}
     	if (userpassword.length() == 0)
		{
			String message = "Please enter password";
			snackbarMessage(context, message);

			return false;
		}  else {
			return true;
		}
	}
	//**********************************Login with email************************
	public boolean Loginvalidate_username(Context context, String username, String userpassword) {
		if (username.length() == 0 && userpassword.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
			return false;
		}
		if (username.length() == 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(username.trim())) {
			String message = "Please enter a valid email address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		if (userpassword.length() == 0) {
			String message = "Please enter password";
			snackbarMessage(context, message);
			return false;
		} else {
			return true;
		}
	}

	public boolean resetpassword(Context context, String new_password, String cnf_password) {


		if (new_password.length() == 0 || cnf_password.length() == 0) {
			String message = "PLease fill all Password Field";
			snackbarMessage(context, message);
			return false;
		} else if (new_password.length() < 6 || new_password.length() >= 15) {
			String message = "New Password and Confirm Password length should be 6 to 15 digits";
			snackbarMessage(context, message);

			return false;
		} else if (cnf_password.length() < 6 || cnf_password.length() >= 15) {
			String message = "New Password and Confirm Password length should be 6 to 15 digits";
			snackbarMessage(context, message);

			return false;
		} else if (new_password.equals(cnf_password)) {

			return true;
		} else {
			String message = "New Password and Confirm Password are not match";
			snackbarMessage(context, message);
			return false;
		}

	}
	public boolean ChangePasswordValidate(Context context, String old_password, String new_password, String cnf_password) {


		if (old_password.length() == 0 || new_password.length() == 0 || cnf_password.length() == 0) {
			String message = "PLease fill all Password Field";
			snackbarMessage(context, message);
			return false;
		} else if (new_password.length() < 6 || new_password.length() >= 15) {
			String message = "New Password and Confirm Password length should be 6 to 15 digits";
			snackbarMessage(context, message);

			return false;
		} else if (cnf_password.length() < 6 || cnf_password.length() >= 15) {
			String message = "New Password and Confirm Password length should be 6 to 15 digits";
			snackbarMessage(context, message);

			return false;
		} else if (new_password.equals(cnf_password)) {

			return true;
		} else {
			String message = "New Password and Confirm Password are not match";
			snackbarMessage(context, message);
			return false;
		}

	}

	public boolean editProfileValidate(Context context, String name, String email, String address, String phone) {

		if (name.length() == 0 && email.length() == 0 && address.length() == 0 && phone.length() == 0) {
			String message = "Please fill all fields";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;
		}
		if (name.length() == 0) {
			String message = "Please enter name field";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (email.length() == 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}


		if (address.length() == 0) {
			String message = "Please enter address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}

		if ((phone.length() < 10) || (phone.length() > 15)) {
			String message = "Phone length should be 10 to 15 digits ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		if (!(phone.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		} else
			return true;
	}

	public boolean registervalidatenew(Context context, String name, String email, String mobile, String dob) {



		if (name.length() == 0  &&email.length() == 0&& mobile.length() == 0 &&   dob.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (name.length() == 0) {
			String message = "Please enter name field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (email.length()== 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}


		if ((mobile.length() < 10) || (mobile.length() > 12)) {
			String message = "Please enter a valid Mobile Number ";
			snackbarMessage(context, message);

			return false;
		}
		if (!(mobile.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			snackbarMessage(context, message);

			return false;
		}

		if (dob.length() == 0) {
			String message = "Please enter Date Of Birth field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(dob)) {

			String message = "Enter characters only in  Date of Birth field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}




//if(qualification.equals("Select Qualification/ਯੋਗਤਾ")){
//	String message = "Please Select Qualification field";
//	snackbarMessage(context, message);
//	return false;
//}

//		if (qualification.length()==0) {
//			String message = "Please Select Qualification field";
//			snackbarMessage(context, message);
////            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//			return false;
//
//		}
//          if (qualification.equals("")) {
//            String message = "Please Select Qualification ";
//            snackbarMessage(context, message);
////            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//
//
//        }
//		if (qualification.toString().trim().equals("")){
//			String message = "Please Select Qualification";
//			snackbarMessage(context, message);
//			return false;
//		}

		else{
			return true;
		}

	}

	//**********************************Registration with email************************

	public boolean registervalidate(Context context, String name, String surname,  String dob, String email, String password, String city, String country,String gender) {



		if (name.length() == 0  && surname.length() == 0   && dob.length() == 0&& email.length() == 0&& password.length() == 0 && city.length() == 0 && country.length()==0 && gender.length()==0  ) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (name.length() == 0) {
			String message = "Please enter name field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (dob.length() == 0) {
			String message = "Please enter Date Of Birth field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(dob)) {

			String message = "Enter characters only in  Date of Birth field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (email.length()== 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}

		if (gender.length() == 0) {
			String message = "Please enter Gender field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(gender)) {

			String message = "Enter characters only in  Gender field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
//if(qualification.equals("Select Qualification/ਯੋਗਤਾ")){
//	String message = "Please Select Qualification field";
//	snackbarMessage(context, message);
//	return false;
//}

//		if (qualification.length()==0) {
//			String message = "Please Select Qualification field";
//			snackbarMessage(context, message);
////            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//			return false;
//
//		}
//          if (qualification.equals("")) {
//            String message = "Please Select Qualification ";
//            snackbarMessage(context, message);
////            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//
//
//        }
//		if (qualification.toString().trim().equals("")){
//			String message = "Please Select Qualification";
//			snackbarMessage(context, message);
//			return false;
//		}

		if (password.length() == 0) {
			String message = "Please enter password";
			snackbarMessage(context, message);
			//showMessageOKCancel(context, message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (city.length() == 0) {
			String message = "Please enter City field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(city)) {

			String message = "Enter characters only in  City field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (country.length() == 0) {
			String message = "Please enter Country field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(country)) {

			String message = "Enter characters only in  Country field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		else{
			return true;
		}

	}



	public boolean Timer(Context context, String date) {

		if (date.length() == 0 ) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		if (date.length() == 0) {
			String message = "Please Select date.";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(date)) {

			String message = "Please Select date field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		 else
			return true;
	}

	public boolean Address(Context context, String address, String suite, String city, String zip) {

		if (address.length() == 0 && suite.length() == 0 && city.length() == 0 && zip.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		if (address.length() == 0) {
			String message = "Please enter street Address field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(address)) {

			String message = "Enter characters only in  street Address  field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		if (suite.length() == 0) {
			String message = "Please enter Api Suite field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(suite)) {

			String message = "Enter characters only in   Api Suite  field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (city.length() == 0) {
			String message = "Please enter city field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else if (!match(city)) {

			String message = "Enter characters only in  city  field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}


		if ((zip.length() < 6) || (zip.length() > 6)) {
			String message = "Zip Code length should be  6 digits ";
			snackbarMessage(context, message);

			return false;
		}
		if (!(zip.trim().matches(phonepattern))) {
			String message = "Please enter a valid Zip Code";
			snackbarMessage(context, message);

			return false;
		} else
			return true;
	}

	public boolean vaildateemailmobile(Context context, String email, String phone) {

		if ( email.length() == 0  && phone.length() == 0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}


		if (email.length() == 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}

		if ((phone.length() < 10) || (phone.length() > 15)) {
			String message = "Phone length should be 10 to 15 digits ";
			snackbarMessage(context, message);

			return false;
		}
		if (!(phone.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			snackbarMessage(context, message);

			return false;
		} else
			return true;
	}

	public boolean Company_validation(Context context, String name, String email, String about, String address, String phone, String website) {

		if (name.length() == 0 && email.length() == 0 && about.length() == 0 && address.length() == 0 && phone.length() == 0 && website.length() == 0) {
			String message = "Please fill all fields";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}

		if (name.length() == 0) {
			String message = "Please enter name field";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (email.length() == 0) {
			String message = "Please enter email address";

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		if (about.length() == 0) {
			String message = "Please enter about your company";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

	/* else if(!match(about))
	 {
		 String message  = "Enter characters only in about your comapny field ";
		 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

		 return false;
	 }*/

		if (address.length() == 0) {
			String message = "Please enter address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (address.length() < 15) {
			String message = "Minimum 15 characters for address";

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}/*else if(!match1(address))
	 {

	 String message  = "Address field contains only characters and alphanumeric special character such as , # - in address field ";
	 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	 return false;
	 }*/

		if ((phone.length() < 10) || (phone.length() > 15)) {
			String message = "Phone length should be 10 to 15 digits ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}


		if (!(phone.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;
		}

		if (website.length() == 0) {
			String message = "Please enter Website Link";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else
			return true;
	}

	public boolean Offer_card_validation(Context context, String price, String discount, String start_date_string, String Ex_date_string) {

		if (price.length() == 0 && discount.length() == 0 && start_date_string.length() == 0 && Ex_date_string.length() == 0) {
			String message = "Please fill all fields";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}
		if (price.length() == 0) {
			String message = "Please enter offer price";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}/*else if(!match(name))
		{

			String message  = "Enter characters only in  name field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}*/


		if (discount.length() == 0) {
			String message = "Please enter discount";

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		/*else if((Float.parseFloat(discount)<=1)||(Float.parseFloat(discount)>100))
		{

			String message  = " Discount in between 1 to 100" ;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;


		}*/
		/*else if((Float.parseFloat(discount)>100))
		{

			String message  = " Discount inbetween 1 to 100" ;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}*/
		if (start_date_string.length() == 0) {
			String message = "Please enter about your company";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (Ex_date_string.length() == 0) {
			String message = "Please enter address";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else
			return true;
	}


	public boolean Offer_validation(Context context, String description, String price, String discount, String start_date_string, String Ex_date_string) {

		if (description.length() == 0 && price.length() == 0 && discount.length() == 0 && start_date_string.length() == 0 && Ex_date_string.length() == 0) {
			String message = "Please fill all fields";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		if (description.length() == 0) {
			String message = "Please enter offer description";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (description.length() < 10) {
			String message = "Minimum 30 characters for description";

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}

 	if (price.length() == 0) {
			String message = "Please enter offer price";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}/*else if(!match(name))
		{
			String message  = "Enter characters only in  name field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}*/


		if (discount.length() == 0) {
			String message = "Please enter discount";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}

		if (discount.equals("0")) {
			String message = "Discount can't be zero";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}
/*
		else if((Float.parseFloat(discount)<=1)||(Float.parseFloat(discount)>100))
		{

			String message  = " Discount in between 1 to 100" ;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}
		else if((Float.parseFloat(discount)>100))
		{

			String message  = " Discount in between 1 to 100" ;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}*/
		if (start_date_string.length() == 0) {
			String message = "Please enter start date of the offer";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}


		if (Ex_date_string.length() == 0) {
			String message = "Please enter expiry date of the offer";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}
		/*else if(Ex_date_string.length()<10|| Ex_date_string.length()>=15)
		{
			String message  ="Adddress Between 10 To 15 Digits";

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}else if(!match(address))
		{

			String message  = "Enter characters only in address field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;
		}

		if (!(phone.trim().matches(phonepattern)))


		{
			String message  = "Please enter a valid phone";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;
		}

*/

		else
			return true;
	}


//**********************************Place_card_vlaidate************************

	public boolean Place_card_vlaidate(Context context, String fname, String lname, String email, String phone, String address, String tel) {

		String fname1 = fname.trim();
		String lname1 = lname.trim();
		String email1 = email.trim();
		String address1 = address.trim();
		String tel1 = tel.trim();
		String phone1 = phone.trim();


		/*if (fname1.length() == 0 && lname1.length() == 0 && email1.length() == 0 && address1.length() == 0 && tel.length() == 0) {
			String message = "Please fill all fields";
			//showMessageOKCancel(context, message);
			return false;
		}*/
		//*************first Name****************
		if (fname1.length() == 0) {
			String message = "Please enter first name";
            snackbarMessage(context, message);
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		} else if (!match(fname1)) {

			String message = "Enter characters only in first name field ";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		}

		//*************last Name****************
		if (lname1.length() == 0) {
			String message = "Please enter last name";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;

		} else if (!match(lname1)) {
			String message = "Enter characters only in last name field";

//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		}

		//*************Phone Name****************

		if (!(phone1.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		}

		//*************tel Name****************

		if (!(tel1.trim().matches(phonepattern))) {
			String message = "Please enter the telephone number";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		}

		//*************email Name****************

		if (email.length() == 0) {
			String message = "Please enter email address";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		} else if (!isValidEmaillId(email.trim())) {
			String message = "Please enter a valid email address";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		}

		if (address1.length() == 0) {
			String message = "Please enter address";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            snackbarMessage(context, message);
			return false;
		} else
			return true;
	}


	public boolean forgotvalidate(Context context, String email1) {

		if (email1.length() == 0) {

			String message = "Please fill email field";
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;

		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {

			String message = "Enter valid email address";
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;
		} else {
			return true;
		}

	}

	private void showMessageOKCancel(Context context, String message) {
		new AlertDialog.Builder(context).
				setTitle("Error!")
				.setMessage(message)
				.setPositiveButton("Ok", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).
				create().show();
	}

	private void snackbarMessage(Context c, String message) {
		Snackbar snackbar = Snackbar
				.make(cdl, message, Snackbar.LENGTH_SHORT);

		snackbar.setActionTextColor(Color.parseColor("#803d3b3b"));

		// Changing action button text color
		View sbView = snackbar.getView();
		TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
		textView.setTextColor(Color.WHITE);
		sbView.setBackgroundColor(Color.parseColor("#803d3b3b"));
		textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		textView.setTextSize(16);
		//textView.setAllCaps(true);
		snackbar.show();

		/*final Dialog dialog = new Dialog(c);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_alert_dialog);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.gravity = Gravity.CENTER;
		dialog.getWindow().setAttributes(lp);


		TextView alert_message=(TextView)dialog.findViewById(R.id.message);

		alert_message.setText(message);


		Button ok_button=(Button)dialog.findViewById(R.id.ok_button);

		ok_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();


			}
		});

		dialog.show();
*/






	}

	public boolean EnquireValidate(Context context, String pack_title, String from_location, String start_date_str, String end_date_str, String guest, String budget, String description, String transport_str, String name, String email, String mobile_no) {


		if ((name.length() == 0) || (email.length() == 0) || (pack_title.length() == 0) || (from_location.length() == 0) || (start_date_str.length() == 0) || (end_date_str.length() == 0) || (guest.length() == 0) || (budget.length() == 0) || (description.length() == 0) || (transport_str.length() == 0) || (mobile_no.length() == 0)) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}


		if (name.length() == 0) {
			String message = "Please enter name field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

		if (email.length() == 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}

		if (pack_title.length() == 0) {
			String message = "Please enter Package Title";
			snackbarMessage(context, message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}
		if (from_location.length() == 0) {
			String message = "Please enter Location Name ";
            snackbarMessage(context, message);
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}
		/*else if(address.length()<10|| address.length()>=15)
		{
			String message  ="Adddress Between 10 To 15 Digits";
			//showMessageOKCancel(context, message);
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}else if(!match(address))
		{

			String message  = "Enter characters only in  address field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}*/
		if ((mobile_no.length() < 10) || (mobile_no.length() > 15)) {
			String message = "Phone length should be 10 to 15 digits ";
			snackbarMessage(context, message);
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}
		if (!(mobile_no.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			snackbarMessage(context, message);
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		} else
			return true;


	}

	public boolean Plan_a_trip_Vaildate(Context context, String wher_location, String from_location, String start_date_str, String end_date_str, String guest, String budget, String description, String transport_str) {

	/*	if(((wher_location.length()==0) ||(from_location.length()==0)||(start_date_str.length()==0)|| (end_date_str.length()==0)|| (guest.length()==0)||(budget.length()==0) ||(description.length()==0)||(transport_str.length()==0)))
		{
			String message  = "Please fill all fields";
			snackbarMessage(context,message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		else*/ if(from_location.equals(""))
		{
			String message  = "Please Enter From Location ";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;

		}
		else if(wher_location.equals(""))
		{
			String message  = "Please Enter Where To ";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;
		}
		else if(start_date_str.equals(""))
		{
			String message  = "Please Select start date ";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;
		}
		else if(end_date_str.equals(""))
		{
			String message  = "Please select end date ";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;
		}
		/*else if(budget.equals("0"))
		{
			String message  = "Please fill the budget not accepted 0 amount.";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;

		}*/
		else if(guest.equals("0"))
		{
			String message  = "Please fill the Minimum 1 person not accepted";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			showMessageOKCancel(context, message);
			return false;

		}
		/*else if(description.length()<20)
		{
			String message  = "Descrption Should be 20 character.";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
			return false;
		}*/
		else if(transport_str.equals(""))
		{
			String message  = "Please fill the transport type.";
//			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			snackbarMessage(context, message);
//			showMessageOKCancel(context, message);
			return false;

		}
		/*else if(address.length()<10|| address.length()>=15)
		{
			String message  ="Adddress Between 10 To 15 Digits";
			//showMessageOKCancel(context, message);
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;

		}else if(!match(address))
		{

			String message  = "Enter characters only in  address field ";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}*/

		else
			return true;


	}

	public boolean editprofilevalidate(Context context, String name, String address, String phoneNo, String date_of_birth) {

		if (name.length() == 0  && address.length() == 0 && phoneNo.length() == 0 && date_of_birth.length()==0) {
			String message = "Please fill all fields";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}


		if (name.length() == 0) {
			String message = "Please enter name field";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		} else if (!match(name)) {

			String message = "Enter characters only in  name field ";
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;
		}

        if (address.length() == 0) {
            String message = "Please enter name field";
            snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            return false;

        }



		/*if (email.length() == 0) {
			String message = "Please enter email address";
			//showMessageOKCancel(context, message);
			snackbarMessage(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		} else if (!isValidEmaillId(email.trim())) {

			String message = "Please enter a valid email address";
			snackbarMessage(context, message);
//              Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		}

		if (password.length() == 0) {
			String message = "Please enter password";
			snackbarMessage(context, message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			//showMessageOKCancel(context, message);
			return false;

		} else if (password.length() < 8 || password.length() >= 15) {
			String message = "Password between 8 To 15 digits";
			snackbarMessage(context, message);
			//showMessageOKCancel(context, message);
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			return false;
		}*/

		if(date_of_birth.length()==0)
		{
			String message  = "Please select Date";
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

			return false;

		}

		if ((phoneNo.length() < 10) || (phoneNo.length() > 15)) {
			String message = "Phone length should be 10 to 15 digits ";
			snackbarMessage(context, message);

			return false;
		}
		if (!(phoneNo.trim().matches(phonepattern))) {
			String message = "Please enter a valid phone number";
			snackbarMessage(context, message);

			return false;
		} else
			return true;




	}


/*	public boolean Loginvalidate_phone(Login login, String trim, String trim1) {




	}*/
}

