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
import com.example.matatabi.padm.model.PadmKecamatan;
import com.example.matatabi.padm.model.PadmKelurahan;
import com.example.matatabi.padm.model.PadmKelurahanResponse;
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

public class GrafikPadmKelurahanActivity extends AppCompatActivity {
    private List<PadmKelurahan> padmKelurahanList;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik_padm_kelurahan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart = findViewById(R.id.chartPadmKelurahan);
        Intent intent = getIntent();
        String nm_kecamatan = intent.getStringExtra("nm_kecamatan");

        Call<PadmKelurahanResponse> call = RetrofitClient.getmInstance().getApi().showPadmKel(nm_kecamatan);
        call.enqueue(new Callback<PadmKelurahanResponse>() {
            @Override
            public void onResponse(@NonNull Call<PadmKelurahanResponse> call, @NonNull Response<PadmKelurahanResponse> response) {
                assert response.body() != null;
                padmKelurahanList = response.body().getPadmKelurahanList();

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleRadius(30);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(40f);

                ArrayList<PieEntry> yValues = new ArrayList<>();
                for (int i = 0; i < padmKelurahanList.size(); i++){
                    PadmKelurahan padmKelurahan = padmKelurahanList.get(i);
                    String kelurahan = padmKelurahan.getNm_kelurahan();
                    String total = padmKelurahan.getTotal_mahasiswa();
                    yValues.add(new PieEntry(Integer.parseInt(total), kelurahan));
                }

                Description description = new Description();
                description.setText("Total Data Persebaran Mahasiswa FKTI UNMUL PRODI TI Berdasarkan Kelurahan/Desa");
                description.setTextSize(7);
                pieChart.setDescription(description);

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet pieDataSet = new PieDataSet(yValues, "(Kelurahan/Desa)");
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setSelectionShift(5f);

                final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                        Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189),
                        Color.rgb(255,0,149), Color.rgb(230,0,255), Color.rgb(18,0,255), Color.rgb(245,139,9),
                        Color.rgb(46,0,255), Color.rgb(203,21,239), Color.rgb(132,0,0), Color.rgb(255,222,7),
                        Color.rgb(4,237,155), Color.rgb(6,153,91)};
                ArrayList<Integer> colors = new ArrayList<>();

                for(int c: MY_COLORS) colors.add(c);
                pieDataSet.setColors(colors);
                pieDataSet.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));

                PieData data = new PieData(pieDataSet);
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.BLACK);

                pieChart.getLegend().setWordWrapEnabled(true);
                pieChart.setData(data);
            }

            @Override
            public void onFailure(@NonNull Call<PadmKelurahanResponse> call, @NonNull Throwable t) {
                Toast.makeText(GrafikPadmKelurahanActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
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
