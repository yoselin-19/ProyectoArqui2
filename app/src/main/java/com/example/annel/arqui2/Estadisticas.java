package com.example.annel.arqui2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        ArrayList<Category> category = new ArrayList<Category>();
        Category temp = new Category();
        Category ilumi = new Category();
        Category tim = new Category();
        Category peso = new Category();
        temp.setImagen(getDrawable(R.drawable.oil_temperature));
        temp.setTitle("Temperatura");
        temp.setDescription("Muestra la estadistica de la temperatura");
        ilumi.setImagen(getDrawable(R.drawable.lightbulb_on));
        ilumi.setTitle("Iluminacion");
        ilumi.setDescription("Muestra la estadistica de la luz en el lugar");
        tim.setImagen(getDrawable(R.drawable.timer));
        tim.setTitle("Tiempo Sentado");
        tim.setDescription("Muestra la estadistica del tiempo que estuvo sentado");

        peso.setImagen(getDrawable(R.drawable.weight_kilogram));
        peso.setTitle("Peso");
        peso.setDescription("Muestra la estadistica del peso");
        category.add(temp);
        category.add(ilumi);
        category.add(tim);
        category.add(peso);
        ListView lv = (ListView) findViewById(R.id.listView);

        AdapterItem adapter = new AdapterItem(this, category);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0 ){
                    Intent inte = new Intent(Estadisticas.this,Temperatura.class);
                    startActivity(inte);
                }else if(position == 1){
                    Intent inte = new Intent(Estadisticas.this,Iluminacion.class);
                    startActivity(inte);
                }
                else if(position == 2){
                    Intent inte = new Intent(Estadisticas.this,TiempoSentado.class);
                    startActivity(inte);
                }else if(position == 3){
                    Intent inte = new Intent(Estadisticas.this,Peso.class);
                    startActivity(inte);
                }
            }


        });

    }

    public void E_Temperatura(View view){

    }

    public void E_Iluminacion(View view){

    }

    public void E_TiempoSentado(View view){

    }

    public void E_Peso(View view){

    }
}
