/**
 * NAMA : FAKHRI ADI SAPUTRA
 * NIM : 10119116
 * KELAS : IF-3
 */
package com.fakhrads.uasakbif3101191116;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText etText;
    private EditText titleText;
    private EditText catText;
    private Notes memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        this.etText =  findViewById(R.id.etText);
        this.titleText =  findViewById(R.id.titleText);
        this.catText =  findViewById(R.id.catText);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel =  findViewById(R.id.btnCencel);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            memo = (Notes)bundle.get("MEMO");
            if (memo != null){
                this.etText.setText(memo.getText());
                this.titleText.setText(memo.getTtitle());
                this.catText.setText(memo.getCategory());
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void onSaveClicked(){
        DatabaseAcces databaseAcces = DatabaseAcces.getInstance(this);
        databaseAcces.open();
        Log.i("MEMO",etText.getText().toString());
        Log.i("TITLE",titleText.getText().toString());
        Log.i("CATEGORY",catText.getText().toString());
        if (memo == null){
            Notes temp = new Notes();
            temp.setText(etText.getText().toString().trim());
            temp.setTtitle(titleText.getText().toString().trim());
            temp.setCategory(catText.getText().toString().trim());
            databaseAcces.save(temp);
        }else {
            memo.setText(etText.getText().toString().trim());
            memo.setTtitle(titleText.getText().toString().trim());
            memo.setCategory(catText.getText().toString().trim());
            databaseAcces.update(memo);
        }
        databaseAcces.close();
        this.finish();
    }
}