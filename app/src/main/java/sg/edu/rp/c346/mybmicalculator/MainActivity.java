package sg.edu.rp.c346.mybmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalc;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvBMIresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalc);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvBMIresult = findViewById(R.id.textViewBMIResult);

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float fheight = Float.parseFloat(etHeight.getText().toString());
                Float fweight = Float.parseFloat(etWeight.getText().toString());

                Float BMI = fweight / (fheight * fheight);

                if(BMI < 18.5)
                    tvBMIresult.setText("You are underweight");

                else if(BMI > 18.5 && BMI < 24.9)
                    tvBMIresult.setText("Your BMI is normal");

                else if(BMI > 25 && BMI < 29.9)
                    tvBMIresult.setText("You are overweight");

                else if(BMI > 30)
                    tvBMIresult.setText("You are obese");


                tvBMI.setText("Last Calculated BMI: " + BMI);
                tvDate.setText("Last Calculated Date: " + datetime);

            }
        });


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                tvBMI.setText("Last Calculated Date: ");
                tvDate.setText("Last Calculated BMI: 0.0");
                tvBMIresult.setText("");

                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear().commit();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1d: Add the key-value pair
        prefEdit.putString("bmi", tvBMI.getText().toString());
        prefEdit.putString("date", tvDate.getText().toString());
        prefEdit.putString("result", tvBMIresult.getText().toString());

        //Step 1e: call commit() method to save the changes into the SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        String bmi = prefs.getString("bmi","Last Calculated BMI is ");
        String date = prefs.getString("date", "Last Calculated date is ");
        String result = prefs.getString("result","");

        tvDate.setText(date);
        tvBMI.setText(bmi);
        tvBMIresult.setText(result);
    }
}
