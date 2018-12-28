package com.example.annel.arqui2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Estado_Actual extends AppCompatActivity {

    private static final String SERVER = "http://35.231.118.246/";
    private TextView temp;
    private TextView ilum;
    private TextView time;
    private TextView peso;
    private TextView fecha_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado__actual);
        setTitle("ESTADO ACTUAL");

        temp = (TextView)findViewById(R.id.tv_temperatura);
        ilum = (TextView)findViewById(R.id.tv_iluminacion);
        time = (TextView)findViewById(R.id.tv_tiempo);
        peso = (TextView)findViewById(R.id.tv_peso);
        fecha_actual = (TextView)findViewById(R.id.textView6);

        downloadJSON(SERVER + "consulta.php");
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    //loadIntoActivity(s)
                    JSONArray arreglo = new JSONArray(s);
                    JSONObject obj = arreglo.getJSONObject(arreglo.length()-1);

                    temp.setText(obj.getString("temperatura"));
                    ilum.setText(obj.getString("luz"));

                    peso.setText(obj.getString("peso"));


                    JSONArray jsonArray = new JSONArray(s);
                    int[] estado = new int[jsonArray.length()];
                    String[] timeStamp = new String[jsonArray.length()];
                    String aux = "";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj2 = jsonArray.getJSONObject(i);
                        estado[i] = obj2.getInt("estado");
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

                    fecha_actual.setText(fecha.get(fecha.size()-1).substring(0,10));

                    DecimalFormat format = new DecimalFormat();
                    format.setMaximumFractionDigits(2); //Define 2 decimales.


                    if(tiempo<60)time.setText(""+format.format(tiempo)+" Min");
                    else if(tiempo/60 == 1)time.setText(""+format.format(tiempo/60)+" Hr");
                    else time.setText(""+format.format(tiempo/60)+" Hrs");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
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
//        JSONArray jsonArray = new JSONArray(json);
//        String[] stocks = new String[jsonArray.length()];
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject obj = jsonArray.getJSONObject(i);
//            stocks[i] = obj.getString("name") + " " + obj.getString("price");
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
//        listView.setAdapter(arrayAdapter);

        JSONArray arreglo = new JSONArray(json);
        JSONObject obj = arreglo.getJSONObject(arreglo.length()-1);

//        Toast toast1 =
//                Toast.makeText(getApplicationContext(),"texto", Toast.LENGTH_SHORT);
//        toast1.show();

        temp.setText(obj.getString("temperatura"));
        ilum.setText(obj.getString("luz"));
        time.setText("Hora");
        peso.setText(obj.getString("peso"));
    }
}
