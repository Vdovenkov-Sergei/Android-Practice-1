package com.example.practice_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SHOW_OTHER_ACTIVITY = 1;

    private TextView greeting;
    private EditText editTextGreeting;

    // В работе предусмотрена возможность переключения локали на RU и возможность переключения
    // ночного режима

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greeting = findViewById(R.id.greeting);
        editTextGreeting = findViewById(R.id.txtEditGreeting);
        Button buttonEnter = findViewById(R.id.btnEnter);

        buttonEnter.setOnClickListener(v -> {
            String data = editTextGreeting.getText().toString();
            if (!data.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                intent.putExtra(OtherActivity.GREETING_WORD, editTextGreeting.getText().toString());
                startActivityForResult(intent, SHOW_OTHER_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_OTHER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(OtherActivity.GREETING);
            editTextGreeting.setText("");
            greeting.setText(result);
        }
    }

    // Например, при повороте экрана activity перезапускается (проходит через callbacks OnStop -> OnStart),
    // что может привести к потере информации.
    // Такое поведение наблюдается при любом изменении конфигурации устройства

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("state_text_view", greeting.getText().toString());
        savedInstanceState.putString("state_edit_text", editTextGreeting.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextGreeting.setText(savedInstanceState.getString("state_edit_text"));
        greeting.setText(savedInstanceState.getString("state_text_view"));
    }

}