package com.example.vasyldvorak_hw_android_java_2.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.vasyldvorak_hw_android_java_2.R;
import com.example.vasyldvorak_hw_android_java_2.model.CalculatorImpl;
import com.example.vasyldvorak_hw_android_java_2.model.Operator;
import com.example.vasyldvorak_hw_android_java_2.model.Theme;
import com.example.vasyldvorak_hw_android_java_2.model.ThemeRepository;
import com.example.vasyldvorak_hw_android_java_2.model.ThemeRepositoryImpl;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CalculatorView {
    private TextView resultTxt;
    private CalculatorPresenter presenter;
    private ThemeRepository themeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeRepository= ThemeRepositoryImpl.getInstance(this);
        setTheme(themeRepository.getSavedTheme().getThemeRes());

        if (getScreenOrientation())
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_main_landscape);

        resultTxt = findViewById(R.id.result);
        presenter = new CalculatorPresenter(this, new CalculatorImpl());
        if(getIntent().hasExtra("first_argument")){
            presenter.argOne = getIntent().getFloatExtra("first_argument",0);
            showResult(String.valueOf(presenter.argOne));
            presenter.next = false;
           presenter.dot = true;
            presenter.dotn = true;
        }
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

        ActivityResultLauncher<Intent> themeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    Theme selectedTheme = (Theme) intent.getSerializableExtra(SelectThemeActivity.EXTRA_THEME);
                    themeRepository.saveTheme(selectedTheme);
                    recreate();
                }
            }
        });
        findViewById(R.id.theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
                intent.putExtra(SelectThemeActivity.EXTRA_THEME, themeRepository.getSavedTheme());
                themeLauncher.launch(intent);
            }
        });

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
        findViewById(R.id.gb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gb.ru"));
                startActivity(Intent.createChooser(browserIntent, null));
            }
        });
    }

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
}
