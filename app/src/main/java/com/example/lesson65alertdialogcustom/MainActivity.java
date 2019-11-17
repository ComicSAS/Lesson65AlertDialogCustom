package com.example.lesson65alertdialogcustom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import static android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {

    final int DIALOG = 1;

    int btn;
    LinearLayout view;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    TextView tvCount;
    ArrayList<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews = new ArrayList<>(10);
    }

    public void onclick(View v) {
        btn = v.getId();
        showDialog(DIALOG);
    }

    @SuppressLint("InflateParams")
    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Custom dialog");
// создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения кол-ва
        tvCount = view.findViewById(R.id.tvCount);
        return adb.create();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {
            // Находим TextView для отображения времени и показываем текущее
            // время
            TextView tvTime = Objects.requireNonNull(dialog.getWindow()).findViewById(R.id.tvTime);
            tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
            // если была нажата кнопка Добавить
            if (btn == R.id.btnAdd) {
                // создаем новое TextView, добавляем в диалог, указываем текст
                TextView tv = new TextView(this);
                view.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                textViews.add(tv);
                tv.setText("New TextView " + textViews.size());
            } else {
                // если коллекция созданных TextView непуста
                if (textViews.size() > 0) {
                    // находим в коллекции последний TextView
                    TextView tv = textViews.get(textViews.size() - 1);
                    // удаляем из диалога
                    view.removeView(tv);
                    // удаляем из коллекции
                    textViews.remove(tv);
                }
            }
            // обновляем счетчик
            tvCount.setText("TextView count = " + textViews.size());
        }
    }
}
