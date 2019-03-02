
package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.PadmKecamatan;
import com.example.matatabi.padm.model.PadmKecamatanResponse;
import com.example.matatabi.padm.other.DecimalRemover;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrafikPadmKecamatanActivity extends AppCompatActivity {
    private List<PadmKecamatan> padmKecamatanList;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_padm_kecamatan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart = findViewById(R.id.chartPadmKecamatan);
        Intent intent = getIntent();
        String nm_kabupaten = intent.getStringExtra("nm_kabupaten");

        Call<PadmKecamatanResponse> call = RetrofitClient.getmInstance().getApi().showPadmKec(nm_kabupaten);
        call.enqueue(new Callback<PadmKecamatanResponse>() {
            @Override
            public void onResponse(@NonNull Call<PadmKecamatanResponse> call, @NonNull Response<PadmKecamatanResponse> response) {
                assert response.body() != null;
                padmKecamatanList = response.body().getPadmKecamatanList();

                pieChart.setUsePercentValues(false);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleRadius(30);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(40f);

                ArrayList<PieEntry> yValues = new ArrayList<>();
                for (int i = 0; i < padmKecamatanList.size(); i++){
                    PadmKecamatan padmKecamatan = padmKecamatanList.get(i);
                    String kecamatan = padmKecamatan.getNm_kecamatan();
                    String total = padmKecamatan.getTotal_mahasiswa();
                    yValues.add(new PieEntry(Integer.parseInt(total), kecamatan));
                }

                Description description = new Description();
                description.setText("Total Data Persebaran Mahasiswa FKTI UNMUL PRODI TI");
                description.setTextSize(10);
                pieChart.setDescription(description);

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet pieDataSet = new PieDataSet(yValues, "Kecamatan");
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
            public void onFailure(Call<PadmKecamatanResponse> call, Throwable t) {

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
