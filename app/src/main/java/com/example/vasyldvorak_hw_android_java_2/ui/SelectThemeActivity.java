package com.example.vasyldvorak_hw_android_java_2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vasyldvorak_hw_android_java_2.R;
import com.example.vasyldvorak_hw_android_java_2.model.Theme;
import com.example.vasyldvorak_hw_android_java_2.model.ThemeRepository;
import com.example.vasyldvorak_hw_android_java_2.model.ThemeRepositoryImpl;

import java.util.List;


public class SelectThemeActivity extends AppCompatActivity {
    public static final String EXTRA_THEME = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);
        ThemeRepository themeRepository = ThemeRepositoryImpl.getInstance(this);
        List<Theme> themes = themeRepository.getAll();
        LinearLayout container = findViewById(R.id.container);
        Intent intent = getIntent();
        Theme selectedThem = (Theme) intent.getSerializableExtra(EXTRA_THEME);

        for (Theme theme : themes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_theme, container, false);

            TextView title = itemView.findViewById(R.id.title);
            title.setText(theme.getTitle());
            CardView cardView = itemView.findViewById(R.id.theme_card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_THEME, theme);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
            });
            ImageView checked = itemView.findViewById(R.id.checked);
            if (theme.equals(selectedThem)) {
                checked.setVisibility(View.VISIBLE);
            } else {
                checked.setVisibility(View.GONE);
            }
            container.addView(itemView);
        }
    }
}