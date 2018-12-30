package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

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
                    yvalues.add(new PieEntry(Float.parseFloat(total), kabupaten));
                }

                Description description = new Description();
                description.setText("Total Data Persebaran Mahasiswa FKTI UNMUL PRODI TI");
                description.setTextSize(10);
                pieChart.setDescription(description);

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet pieDataSet = new PieDataSet(yvalues, "Kabupaten/Kota");
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setSelectionShift(5f);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                PieData data = new PieData(pieDataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);

            }

            @Override
            public void onFailure(Call<PadmResponse> call, Throwable t) {

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
