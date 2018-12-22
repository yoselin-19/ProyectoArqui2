package com.example.annel.arqui2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bienvenida extends AppCompatActivity {
    Button iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        iniciar = (Button)findViewById(R.id.btn_inicio);
    }

    public void Start(View view){
        Intent inte = new Intent(Bienvenida.this,Principal.class);
        startActivity(inte);
    }
}
