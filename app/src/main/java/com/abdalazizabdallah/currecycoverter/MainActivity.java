package com.abdalazizabdallah.currecycoverter;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abdalazizabdallah.currecycoverter.model.BackResult;
import com.abdalazizabdallah.currecycoverter.model.Result;
import com.abdalazizabdallah.currecycoverter.viewModel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity implements Observer<Result> {

    private static final String TAG = "MainActivity";
    private CountryCodePicker countryCodePickerAbove;
    private CountryCodePicker countryCodePickerBelow;
    private ImageButton imageButton;
    private EditText editText;
    private TextView textViewResult;
    private MainViewModel mainViewModel;
    private Toolbar toolbar;
    private TextView textViewSymbolSource;
    private TextView textViewSymbolTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageButton = findViewById(R.id.imageButton_exchange);
        countryCodePickerAbove = findViewById(R.id.spinner_flag_above);
        countryCodePickerAbove.setCountryForNameCode("us");
        countryCodePickerBelow = findViewById(R.id.spinner_flag_below);
        countryCodePickerBelow.setCountryForNameCode("il");

        textViewResult = findViewById(R.id.textView_below);
        textViewSymbolSource = findViewById(R.id.textViewSymbolSource);
        textViewSymbolTarget = findViewById(R.id.textViewSymbolTarget);
        editText = findViewById(R.id.editText_above);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mainViewModel.getDataWrapper().observe(this, this);


        Log.e(TAG, " MainActivity :onCreate: " + countryCodePickerBelow.getSelectedCountryNameCode(), null);

        mainViewModel.requestCurrencyCode(countryCodePickerBelow.getSelectedCountryNameCode(), countryCodePickerAbove.getSelectedCountryNameCode(), editText.getText().toString());


        countryCodePickerBelow.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Snackbar.make(findViewById(android.R.id.content), countryCodePickerBelow.getSelectedCountryNameCode(), Snackbar.LENGTH_LONG).show();
                mainViewModel.requestCurrencyCode(countryCodePickerBelow.getSelectedCountryNameCode(), countryCodePickerAbove.getSelectedCountryNameCode(), editText.getText().toString());
            }
        });

        editText.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                Snackbar.make(findViewById(android.R.id.content), countryCodePickerBelow.getSelectedCountryNameCode(), Snackbar.LENGTH_LONG).show();
                                mainViewModel.requestCurrencyCode(countryCodePickerBelow.getSelectedCountryNameCode(), countryCodePickerAbove.getSelectedCountryNameCode(), editText.getText().toString());

                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );

    }

    public void changeConutry(View view) {
        String countryBelow = countryCodePickerBelow.getSelectedCountryNameCode();
        String countryAbove = countryCodePickerAbove.getSelectedCountryNameCode();
        countryCodePickerAbove.setCountryForNameCode(countryBelow);
        countryCodePickerBelow.setCountryForNameCode(countryAbove);
    }
//

    @Override
    public void onChanged(Result result) {

        Log.e(TAG, "onChanged: " + result.status, null);

        switch (result.status) {
            case SUCCESS:
                if (result.data != null) {
                    BackResult resu = (BackResult) result.data;
                    textViewResult.setText(String.valueOf(resu.getResult()));
                    textViewSymbolSource.setText(String.valueOf(resu.getSymbolSource()));
                    textViewSymbolTarget.setText(String.valueOf(resu.getSymbolTaget()));
                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                }
                break;
            case ERROR:
                Log.e(TAG, "onChanged: " + result.data, null);
                //if(result.data!=null) {
                String resu = (String) result.data;
                Toast.makeText(this, resu, Toast.LENGTH_LONG).show();
                // }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_not_show, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.visiable) {
            // TODO //
        }
        return super.onOptionsItemSelected(item);
    }
}
