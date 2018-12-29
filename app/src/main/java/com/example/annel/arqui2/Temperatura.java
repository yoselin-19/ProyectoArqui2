package com.example.annel.arqui2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Temperatura extends AppCompatActivity {

    private static final String SERVER = "http://35.231.118.246/";
    PieChart pieChart;  //BasicSimpleBarChart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);
        setTitle("Temperatura");
        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        downloadJSON(SERVER + "consulta.php");
    }


    private void setData(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            int[] temp = new int[jsonArray.length()];
            String[] timeStamp = new String[jsonArray.length()];
            String aux = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                temp[i] = obj.getInt("temperatura");
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

            ArrayList<PieEntry> yValues = new ArrayList<>();
            for (int i = 0; i < fecha.size(); i++) {
                int cont = 0; float prom = 0;
                for(int j = 0;j<temp.length;j++){
                    float val = (float) temp[j];
                    if(timeStamp[j].substring(8,10).equals(fecha.get(i).substring(8,10))){
                        prom = prom + val;
                        cont++;
                    }
                }
                yValues.add(new PieEntry(prom/cont, fecha.get(i)));
            }
//            ArrayList<PieEntry> yValues = new ArrayList<>();
//            yValues.add(new PieEntry(34f, "Bangladesh"));
//            yValues.add(new PieEntry(23f, "Guatemala"));
//            yValues.add(new PieEntry(14f, "Costa Rica"));
//            yValues.add(new PieEntry(35, "Venezuela"));
//            yValues.add(new PieEntry(40, "Panamá"));
//            yValues.add(new PieEntry(23, "Canada"));

            PieDataSet dataSet = new PieDataSet(yValues, "Temperatura en \u00b0C");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            // ColorTemplate.JOYFUL_COLORS,ColorTemplate.LIBERTY_COLORS

            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);

            pieChart.setData(data);
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

    private void loadIntoActivity(String json) throws JSONException {
        JSONArray arreglo = new JSONArray(json);
        JSONObject obj = arreglo.getJSONObject(arreglo.length()-1);
    }
}
