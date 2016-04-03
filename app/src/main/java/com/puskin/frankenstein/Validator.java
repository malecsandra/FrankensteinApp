package com.puskin.frankenstein;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /**
     * CNP Validation. Checks the blocks of the CNP and does the math.
     *
     * @param unCnp The CNP to check
     * @return Returns <b>true</b> if the CNP is valid.
     *         <p>Returns <b>false</b> if the CNP is invalid
     */
    public static boolean cnpText(String unCnp) {
        Log.d("DBG", "Read CNP: " + unCnp);

        //Regular expression setup
        //Sex   |Year|    Month     |          Zi          |               Judet             | Ultimele 4
        String regEx = "\\b[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])(0[1-9]|[1-4]\\d|5[0-2]|99|88|77)\\d{4}\\b";
        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(unCnp);

        Log.d("DBG", "CNP matcher returned " + match.matches());

        if (match.matches()) //CNP regex was ok
        {
            int verification[] = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9}; //Numbers courtesy of http://ro.wikipedia.org/wiki/Cod_numeric_personal#C
            //Control number calculation variables
            int sum = 0;
            int rest;
            int control;

            //Control number calculation//
            for (int i = 0; i < verification.length; i++) {
                sum += verification[i] * Character.getNumericValue(unCnp.charAt(i));
            }

            rest = sum % 11;

            if (rest == 10)
                control = 1;
            else
                control = rest;

            Log.d("DBG", "Control a dat: " + Integer.toString(control));
            Log.d("DBG", "Ce e la sfarsitu string-ului: " + unCnp.charAt(unCnp.length() - 1));

            if (control == Character.getNumericValue(unCnp.charAt(unCnp.length() - 1))) {
                Log.d("DBG", "CNP-u e bun");
                return true;
            } else
                return false;
            //////////////////////////////
        }
        return false;
    }

    /**
     * General name validation. Checks if name starts with upper case and other minor elements.
     *
     * @param name The name to check
     * @return Returns <b>true</b> if the name is valid.
     *         <p>Returns <b>false</b> if the name is invalid.
     */
    public static boolean nameText(String name) {
        //1 name minimum|one or more extra names separated by dashes|
        String regEx = "([A-Z][a-z]+)(\\s?-\\s?([A-Z][a-z]+))?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(name);

        Log.d("DBG", "Name matcher returned " + match.matches());

        return match.matches();

    }

    /**
     * General telephone number validation.
     *
     * @param phone The phone number to check
     * @return Returns <b>true</b> if the phone number is valid.
     *         <p>Returns <b>false</b> if the phone number is invalid.
     */
    public static boolean phoneText(String phone) {
    //Max 1 plus sign| Any symbol |x or ext with max 1 space |  Any symbol  |
        String regEx = "[+]?([\\d\\s()./-])*(\\s?(x|ext)?)?([\\d()./-])*";

        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(phone);

        Log.d("DBG", "Phone matcher returned " + match.matches());

        return match.matches();
    }

    /**
     * General age validation. Doesn't allow people to live thousands of years
     *
     * @param age The age to check
     * @return Returns <b>true</b> if the age is valid.
     *         <p>Returns <b>false</b> if the age is invalid.
     */
    public static boolean ageText(String age) {
        String regEx = "\\d{1,3}";

        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(age);

        Log.d("DBG", "Age matcher returned " + match.matches());

        return match.matches();
    }

    /**
     * General income validation. Allows for values of "boolean" format
     *
     * @param income The income to check
     * @return Returns <b>true</b> if the income is valid.
     *         <p>Returns <b>false</b> if the income is invalid.
     */
    public static boolean salaryText(String income) {
        String regEx = "\\d+[,.]?\\d*";

        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(income);

        Log.d("DBG", "Salary matcher returned " + match.matches());

        return match.matches();
    }

    /**
     * General ethnicity validation.
     *
     * @param ethnicity The ethnicity to check
     * @return Returns <b>true</b> if the ethnicity is valid.
     *         <p>Returns <b>false</b> if the ethnicity is invalid.
     */
    public static boolean ethnicityText(String ethnicity) {
        //1 name minimum|one or more extra names separated by dashes|
        String regEx = "([A-Z][a-z]+)(\\s?-\\s?([A-Z][a-z]+))?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher match = pattern.matcher(ethnicity);

        Log.d("DBG", "Ethnicity matcher returned " + match.matches());

        return match.matches();

    }

    /**
     * General password validation. Password rules are in development.
     * <p>As of 19.12.2014 this validation only checks for strings larger than 8 characters</p>
     *
     * @param password The password to check
     * @return Returns <b>true</b> if the password is valid.
     *         <p>Returns <b>false</b> if the password is invalid.
     */
    public static boolean passwordText(String password) {
        return password.length() >= 4;
        //TODO Modify rules based on specifications
    }

    /**
     * General username validation. username rules are in development.
     * <p>As of 19.12.2014 this validation only checks for strings larger than 0 characters</p>
     *
     * @param username The username to check
     * @return Returns <b>true</b> if the username is valid.
     *         <p>Returns <b>false</b> if the username is invalid.
     */
    public static boolean userNameText(String username) {
        return username.length() != 0;

        //TODO Change username validation based on specifications
    }

    /**
     * Checks if any text exists.
     *
     * @param text The text to check
     * @return Returns <b>true</b> if the text exists.
     *         <p>Returns <b>false</b> if the text does not exist.
     */
    public static boolean existsText(String text) {
        return text.length() != 0;

    }
}
