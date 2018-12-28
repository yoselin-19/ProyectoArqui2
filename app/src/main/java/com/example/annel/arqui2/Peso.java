package com.example.annel.arqui2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Peso extends AppCompatActivity {

    private static final String SERVER = "http://35.231.118.246/";
    private LineChart chart;

    //InvertedAxis
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);
        setTitle("Peso");

        chart = findViewById(R.id.chart1);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        XAxis xl = chart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setInverted(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        downloadJSON(SERVER + "consulta.php");
    }

    private void setData(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            int[] peso = new int[jsonArray.length()];
            String[] timeStamp = new String[jsonArray.length()];
            String aux = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                peso[i] = obj.getInt("peso");
                timeStamp[i] = obj.getString("fecha");
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

            String aux2 = "";
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = 0; i < fecha.size(); i++) {
                aux2 = fecha.get(i).substring(8,10);
                int cont = 0; float prom = 0;
                for(int j = 0;j<peso.length;j++){
                    float val = (float) peso[j];
                    if(timeStamp[j].substring(8,10).equals(fecha.get(i).substring(8,10))){
                        prom = prom + val;
                        cont++;
                    }
                }
                values.add(new Entry(Float.parseFloat(aux2), prom/cont));
            }
//
//            ArrayList<Entry> entries = new ArrayList<>();
//
//            for (int i = 0; i < count; i++) {
//                float xVal = (float) (Math.random() * range);
//                float yVal = (float) (Math.random() * range);
//                entries.add(new Entry(xVal, yVal));
//            }

            // sort by x-value
            Collections.sort(values, new EntryXComparator());

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(values, "Peso en gramos");

            set1.setLineWidth(1.5f);
            set1.setCircleRadius(4f);

            // create a data object with the data sets
            LineData data = new LineData(set1);

            // set data
            chart.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                setData(s);
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
}
