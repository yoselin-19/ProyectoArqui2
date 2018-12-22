package com.example.annel.arqui2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button recomendaciones;
    Button estadisticas;
    Button estadoactual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        recomendaciones = (Button)findViewById(R.id.btn_recomendaciones);
        estadisticas = (Button)findViewById(R.id.btn_estadisticas);
        estadoactual = (Button)findViewById(R.id.btn_estadoactual);
    }

    public void I_Recomendaciones(View view){
        Intent inte = new Intent(Principal.this,Recomendaciones.class);
        startActivity(inte);
    }

    public void I_Estadisticas(View view){
        Intent inte = new Intent(Principal.this,Estadisticas.class);
        startActivity(inte);
    }

    public void I_EstadoActual(View view){
        Intent inte = new Intent(Principal.this,Estado_Actual.class);
        startActivity(inte);
    }
}
