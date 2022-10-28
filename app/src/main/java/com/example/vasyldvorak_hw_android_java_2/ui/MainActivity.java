package com.example.vasyldvorak_hw_android_java_2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vasyldvorak_hw_android_java_2.R;
import com.example.vasyldvorak_hw_android_java_2.model.CalculatorImpl;
import com.example.vasyldvorak_hw_android_java_2.model.Operator;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalculatorView {
    private TextView resultTxt;
    private CalculatorPresenter presenter;
    private static final String NameSharedPreference = "LOGIN";
    private static final String appTheme = "APP_THEME";
    private static final int MyStyleCodeStyle = 0;
    private static final int AppThemeLightCodeStyle = 1;
    private static final int AppThemeDarkCodeStyle = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyStyle));


        if (getScreenOrientation())
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_main_landscape);
        initThemeChooser();


        resultTxt = findViewById(R.id.result);

        presenter = new CalculatorPresenter(this, new CalculatorImpl());
        if (savedInstanceState != null) {

            savedInstanceState.getParcelable("Pres");
            resultTxt.setText(savedInstanceState.getString("TextView"));
            presenter.argOne = savedInstanceState.getFloat("Argument_1");
            presenter.argTwo = savedInstanceState.getFloat("Argument_2");
        }


        Map<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);
        digits.put(R.id.key_0, 0);

        View.OnClickListener digitalClickLisener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                presenter.onDigitPressed(digits.get(view.getId()));
            }
        };


        findViewById(R.id.key_1).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_2).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_3).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_4).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_5).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_6).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_7).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_8).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_9).setOnClickListener(digitalClickLisener);
        findViewById(R.id.key_0).setOnClickListener(digitalClickLisener);

        Map<Integer, Operator> operators = new HashMap<>();

        operators.put(R.id.key_minus, Operator.SUB);
        operators.put(R.id.key_plus, Operator.ADD);
        operators.put(R.id.key_DIV, Operator.DIV);
        operators.put(R.id.key_mult, Operator.MULT);

        View.OnClickListener operatorsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOperatorPressed(operators.get(view.getId()));
            }
        };

        findViewById(R.id.key_DIV).setOnClickListener(operatorsClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operatorsClickListener);
        findViewById(R.id.key_plus).setOnClickListener(operatorsClickListener);
        findViewById(R.id.key_mult).setOnClickListener(operatorsClickListener);

        findViewById(R.id.key_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDotPressed();
            }
        });
        findViewById(R.id.key_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSignPressed();
            }
        });

        findViewById(R.id.key_DEL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDelPressed();
            }
        });
        findViewById(R.id.key_equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEqualPressed();
            }
        });

        findViewById(R.id.key_C).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCPressed();
            }
        });

        findViewById(R.id.key_CE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCEPressed();
            }
        });
    }

    private void initThemeChooser() {
        initRadioButton(findViewById((R.id.radioButtonMaterialComponentsMyStyle)), MyStyleCodeStyle);
        initRadioButton(findViewById((R.id.radioButtonMaterialComponentsLight)), AppThemeLightCodeStyle);
        initRadioButton(findViewById((R.id.radioButtonMaterialComponentsDark)), AppThemeDarkCodeStyle);

        RadioGroup rd = findViewById(R.id.radioButtons);
        ((MaterialRadioButton) rd.getChildAt(getCodeStyle(MyStyleCodeStyle))).setChecked(true);}

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharesPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharesPref.getInt(appTheme, codeStyle);}


    @Override
    public void showResult(String result) {
        resultTxt.setText(result);
    }

    @Override
    public boolean getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return true;
        else
            return false;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("TextView", (String) resultTxt.getText());
        outState.putParcelable("Pres", presenter);
        outState.putFloat("Argument_1", presenter.argOne);
        outState.putFloat("Argument_2", presenter.argTwo);
        super.onSaveInstanceState(outState);
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId((getCodeStyle(codeStyle)));
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case MyStyleCodeStyle:
                return R.style.MyStyle;
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            case AppThemeDarkCodeStyle:
                return R.style.AppThemeDark;
            default:
                return R.style.MyStyle;
        }
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAppTheme(codeStyle);
                recreate();
            }
        });
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeStyle);
        editor.apply();
    }
}
