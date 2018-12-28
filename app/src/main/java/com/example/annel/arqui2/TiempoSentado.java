package com.example.annel.arqui2;

import android.content.res.Resources;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TiempoSentado extends AppCompatActivity {

    private static final String SERVER = "http://35.231.118.246/";
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo_sentado);
        setTitle("Tiempo");

        chart = findViewById(R.id.chart1);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        downloadJSON(SERVER + "consulta.php");
    }

    private void setData(String json) {
        try{
            JSONArray jsonArray = new JSONArray(json);
            int[] estado = new int[jsonArray.length()];
            String[] timeStamp = new String[jsonArray.length()];
            String aux = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                estado[i] = obj.getInt("estado");
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
            ArrayList<BarEntry> values = new ArrayList<>();
            for (int i = 0; i < fecha.size(); i++) {
                aux2 = fecha.get(i).substring(8,10);
                double tiempo = 0;
                for(int j = 0;j<estado.length;j++){
                    if(timeStamp[j].substring(8,10).equals(fecha.get(i).substring(8,10))){
                        if(estado[j] == 1){
                            tiempo = tiempo + 0.17;
                        }
                    }
                }
                values.add(new BarEntry(Integer.parseInt(aux2), (float) tiempo,
                        getResources().getDrawable(R.drawable.ic_launcher_background)));
            }

            BarDataSet set1;

            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();

            } else {
                set1 = new BarDataSet(values, "Minutos");
                set1.setDrawIcons(false);

                int startColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
                int startColor4 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
                int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
                int startColor2 = ContextCompat.getColor(this, android.R.color.holo_green_light);
                int startColor1 = ContextCompat.getColor(this, android.R.color.holo_red_light);

                int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
                int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
                int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
                int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
                int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

                List<GradientColor> gradientColors = new ArrayList<>();
                gradientColors.add(new GradientColor(startColor1, endColor1));
                gradientColors.add(new GradientColor(startColor2, endColor2));
                gradientColors.add(new GradientColor(startColor3, endColor3));
                gradientColors.add(new GradientColor(startColor4, endColor4));
                gradientColors.add(new GradientColor(startColor5, endColor5));

                set1.setGradientColors(gradientColors);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setBarWidth(0.9f);

                chart.setData(data);
            }
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
