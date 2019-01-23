package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Padm;
import com.example.matatabi.padm.model.PadmResponse;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrafikPadmActivity extends AppCompatActivity {
    private List<Padm> padmList;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_padm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart = findViewById(R.id.chartPadm);

        Call<PadmResponse> call = RetrofitClient.getmInstance().getApi().showPadm();
        call.enqueue(new Callback<PadmResponse>() {
            @Override
            public void onResponse(@NonNull Call<PadmResponse> call, @NonNull Response<PadmResponse> response) {
                assert response.body() != null;
                padmList = response.body().getPadmList();
//
                pieChart.setUsePercentValues(false);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleRadius(30);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(40f);

                ArrayList<PieEntry> yvalues = new ArrayList<>();
                for (int i = 0; i < padmList.size(); i++) {
                    Padm padm = padmList.get(i);
                    String kabupaten = padm.getNm_kabupaten();
                    String total = padm.getTotal_mahasiswa();
                    yvalues.add(new PieEntry(Integer.parseInt(total), kabupaten));
                }

                Description description = new Description();
                description.setText("Total Data Persebaran Mahasiswa FKTI UNMUL PRODI TI");
                description.setTextSize(10);
                pieChart.setDescription(description);

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet pieDataSet = new PieDataSet(yvalues, "Kabupaten/Kota");
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setSelectionShift(5f);

                final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                        Color.rgb(127,127,127), Color.rgb(146,208,80),
                        Color.rgb(0,176,80), Color.rgb(79,129,189), Color.rgb(255,0,149),
                        Color.rgb(230,0,255), Color.rgb(18,0,255), Color.rgb(245,139,9)};
                ArrayList<Integer> colors = new ArrayList<>();

                for(int c: MY_COLORS) colors.add(c);
                pieDataSet.setColors(colors);
                pieDataSet.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));

                PieData data = new PieData(pieDataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);

            }

            @Override
            public void onFailure(Call<PadmResponse> call, Throwable t) {
                Toast.makeText(GrafikPadmActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
