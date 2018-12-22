package com.example.annel.arqui2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Estadisticas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
    }

    public void E_Temperatura(View view){
        Intent inte = new Intent(Estadisticas.this,Temperatura.class);
        startActivity(inte);
    }

    public void E_Iluminacion(View view){
        Intent inte = new Intent(Estadisticas.this,Iluminacion.class);
        startActivity(inte);
    }

    public void E_TiempoSentado(View view){
        Intent inte = new Intent(Estadisticas.this,TiempoSentado.class);
        startActivity(inte);
    }

    public void E_Peso(View view){
        Intent inte = new Intent(Estadisticas.this,Peso.class);
        startActivity(inte);
    }
}
