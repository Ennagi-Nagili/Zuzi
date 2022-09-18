package com.dani.zuzi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dani.zuzi.R;

public class Question extends AppCompatActivity {
    private Button btn_company, btn_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        btn_company = findViewById(R.id.btn_company);
        btn_person = findViewById(R.id.btn_person);

        btn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Question.this, Register_people.class);
                finish();
                startActivity(intent);
            }
        });

        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Question.this);
                alert.setTitle("Zuzi");
                alert.setTitle("Şirkət olaraq qeydiyyattan keçmək 2 azn ödəniş tələb edir. Qəbul edirsiniz?");
                alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Question.this, Register.class);
                        finish();
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alert.show();
            }
        });
    }
}