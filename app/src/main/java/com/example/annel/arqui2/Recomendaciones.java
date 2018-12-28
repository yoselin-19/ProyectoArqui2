package com.example.annel.arqui2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Recomendaciones extends AppCompatActivity {

    private static final String SERVER = "http://35.231.118.246/consulta.php";
    private TextView FechaRecomendacion;
    private ListView lista;
    private ArrayList<String> listaMsj;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        setTitle("Recomendaciones");

        FechaRecomendacion = (TextView)findViewById(R.id.txv_fecha);

        listaMsj = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaMsj);
        lista=(ListView)findViewById(R.id.list_recomendaciones);
        lista.setAdapter(adaptador);

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoActivity(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(SERVER );
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoActivity(String json) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray(json);
            int[] estado = new int[jsonArray.length()];
            int[] luz = new int[jsonArray.length()];
            String[] timeStamp = new String[jsonArray.length()];
            String aux = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj2 = jsonArray.getJSONObject(i);
                estado[i] = obj2.getInt("estado");
                luz[i] = obj2.getInt("luz");
                timeStamp[i] = obj2.getString("fecha");
            }

            ArrayList<String> fecha = new ArrayList<>();
            for(int i = 0; i<timeStamp.length;i++){
                if(aux.equals("")){
                    fecha.add(timeStamp[i].substring(0,11));
                    aux = timeStamp[i].substring(0,11);
                }else{
                    if(!aux.equals(timeStamp[i].substring(0,11))){
                        fecha.add(timeStamp[i].substring(0,11));
                        aux = timeStamp[i].substring(0,11);
                    }
                }
            }

            double tiempo = 0;
            for(int j = 0;j<estado.length;j++){
                if(timeStamp[j].substring(8,10).equals(fecha.get(fecha.size()-1).substring(8,10))){
                    if(estado[j] == 1){ tiempo = tiempo + 0.17; }
                }
            }

            double tiempoSinLuz = 0;
            for(int j = 0;j<luz.length;j++){
                if(timeStamp[j].substring(8,10).equals(fecha.get(fecha.size()-1).substring(8,10))){
                    if(luz[j] == 0){ tiempoSinLuz = tiempoSinLuz + 0.17; }
                }
            }

            FechaRecomendacion.setText(fecha.get(fecha.size()-1).substring(0,10));


            /*Recomendacion para pararse un momento*/
            if(tiempo/60 > 3){
                listaMsj.add("Has pasado mas de 3 horas sentado, deberias pararte unos minutos");
                adaptador.notifyDataSetChanged();
            }
            else {
                listaMsj.remove("Has pasado mas de 3 horas sentado, deberias pararte unos minutos");
            }

            /* Recomendacion para encender la luz*/
            if(tiempoSinLuz>1){
                listaMsj.add("Te quedaste sin suficiente luz, deberias encender la luz");
                adaptador.notifyDataSetChanged();
            }
            else {
                listaMsj.remove("Te quedaste sin suficiente luz, deberias encender la luz");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
