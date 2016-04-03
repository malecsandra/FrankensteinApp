package com.puskin.frankenstein.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.Validator;
import com.puskin.frankenstein.events.RegisterEvent;
import com.puskin.frankenstein.models.Person;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity {

    @Bind(R.id.input_username)
    EditText inputUsername;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.input_name)
    EditText inputName;
    @Bind(R.id.input_surname)
    EditText inputSurname;
    @Bind(R.id.input_digitcode)
    EditText inputDigitcode;
    @Bind(R.id.radio_f)
    RadioButton radioF;
    @Bind(R.id.radio_m)
    RadioButton radioM;
    @Bind(R.id.radioGroup_sexGrp)
    RadioGroup radioGroupSexGrp;
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_phoneno)
    EditText inputPhoneno;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.input_layout_username)
    TextInputLayout inputLayoutUsername;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @Bind(R.id.input_layout_surname)
    TextInputLayout inputLayoutSurname;
    @Bind(R.id.input_layout_digitcode)
    TextInputLayout inputLayoutDigitcode;
    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_phoneno)
    TextInputLayout inputLayoutPhoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields())
                    doRegister();
                else
                    Toast.makeText(Register.this, "Some fields are wrong", Toast.LENGTH_SHORT).show();
            }
        });

        inputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutUsername.setErrorEnabled(false);
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutPassword.setErrorEnabled(false);
            }
        });

        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutName.setErrorEnabled(false);
            }
        });

        inputSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutSurname.setErrorEnabled(false);
            }
        });

        inputDigitcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutDigitcode.setErrorEnabled(false);
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutEmail.setErrorEnabled(false);
            }
        });

        inputPhoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                inputLayoutPhoneno.setErrorEnabled(false);
            }
        });
    }

    private void doRegister() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String name = inputName.getText().toString();
        String surname = inputSurname.getText().toString();
        String digitcode = inputDigitcode.getText().toString();
        String sex;

        int sexid = radioGroupSexGrp.getCheckedRadioButtonId();
        if (sexid == R.id.radio_f)
            sex = "F";
        else sex = "M";

        String email = inputEmail.getText().toString();
        String phoneno = inputPhoneno.getText().toString();

        Date date = getDateFromCnp();

        Person p = new Person(-1, name, surname, digitcode, date, sex, email, phoneno);
        User u = new User(-1, username, password, surname + " " + name, true, p);

        NetworkHelper.doRegister(u);
    }

    private Date getDateFromCnp() {
        String year = "";
        String month = "";
        String day = "";

        char[] digitcodearray = inputDigitcode.getText().toString().toCharArray();
        if (digitcodearray[0] == '1' || digitcodearray[0] == '2')
            year += "19" + digitcodearray[1] + digitcodearray[2];
        else if (digitcodearray[0] == '3' || digitcodearray[0] == '4')
            year += "18" + digitcodearray[1] + digitcodearray[2];
        else if (digitcodearray[0] == '5' || digitcodearray[0] == '6')
            year += "20" + digitcodearray[1] + digitcodearray[2];
        else year += "19" + digitcodearray[1] + digitcodearray[2];

        month += "" + digitcodearray[3] + digitcodearray[4];
        day += "" + digitcodearray[5] + digitcodearray[6];

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

        return calendar.getTime();
    }

    private boolean validateFields() {
        boolean allFieldsValid = true;
        if (!Validator.passwordText(inputPassword.getText().toString())) {
            inputLayoutPassword.setErrorEnabled(true);
            inputLayoutPassword.setError("Password too short");
            allFieldsValid = false;
        }
        
        if (!Validator.cnpText(inputDigitcode.getText().toString())) {
            inputLayoutDigitcode.setErrorEnabled(true);
            inputLayoutDigitcode.setError("Digit code is not valid");
            allFieldsValid = false;
        }
        
        if (!Validator.existsText(inputPassword.getText().toString())) {
            inputLayoutPassword.setErrorEnabled(true);
            inputLayoutPassword.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputUsername.getText().toString())) {
            inputLayoutUsername.setErrorEnabled(true);
            inputLayoutUsername.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputName.getText().toString())) {
            inputLayoutName.setErrorEnabled(true);
            inputLayoutName.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputSurname.getText().toString())) {
            inputLayoutSurname.setErrorEnabled(true);
            inputLayoutSurname.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputDigitcode.getText().toString())) {
            inputLayoutDigitcode.setErrorEnabled(true);
            inputLayoutDigitcode.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputEmail.getText().toString())) {
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError("Field is empty");
            allFieldsValid = false;
        }

        if (!Validator.existsText(inputPhoneno.getText().toString())) {
            inputLayoutPhoneno.setErrorEnabled(true);
            inputLayoutPhoneno.setError("Field is empty");
            allFieldsValid = false;
        }

        return allFieldsValid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onRegisterEvent(RegisterEvent registerEvent) {
        if (registerEvent.getResponseCode() == 204) {
            Toast.makeText(Register.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("username",registerEvent.getUsername());
            setResult(LogIn.RESULT_OK,i);
            finish();
        } else {
            Toast.makeText(this, registerEvent.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
