package com.example.practice_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OtherActivity extends AppCompatActivity {
    public static final String GREETING_WORD = "project.greeting_word";
    public static final String GREETING = "project.greeting";

    private EditText editTextName;
    private TextView greeting_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        greeting_word = findViewById(R.id.greetingWord);
        editTextName = findViewById(R.id.txtEditName);
        Button buttonBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        greeting_word.setText(String.format("%s!", intent.getStringExtra(GREETING_WORD)));

        buttonBack.setOnClickListener(v -> {
            String data = editTextName.getText().toString();
            if (!data.isEmpty()) {
                Intent result = new Intent();
                result.putExtra(GREETING, String.format("%s, %s!",
                        intent.getStringExtra(GREETING_WORD),
                        data));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    // Например, при повороте экрана activity перезапускается (проходит через callbacks OnStop -> OnStart),
    // что может привести к потере информации.
    // Такое поведение наблюдается при любом изменении конфигурации устройства

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("state_text_view", greeting_word.getText().toString());
        savedInstanceState.putString("state_edit_text", editTextName.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextName.setText(savedInstanceState.getString("state_edit_text"));
        greeting_word.setText(savedInstanceState.getString("state_text_view"));
    }

}