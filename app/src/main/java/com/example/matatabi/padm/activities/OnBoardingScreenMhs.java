package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.slider.SliderAdapter;

import java.security.PrivateKey;

public class OnBoardingScreenMhs extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button nextBtn, prevBtn, startBtn;
    private int currenctPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen_mhs);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mSlideViewPager = findViewById(R.id.slideViewPager1);
        mDotLayout = findViewById(R.id.dot_layout);

        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.previousBtn);
        startBtn = findViewById(R.id.startBtn);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currenctPage + 1);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currenctPage -1 );
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingScreenMhs.this, MahasiswaActivity.class));
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i =0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.onBoardingTransparent));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.onBoardingWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currenctPage = i;
            if (i == 0){
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(false);
                startBtn.setEnabled(false);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.INVISIBLE);
                startBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                prevBtn.setText("");
                startBtn.setText("");
            }else if (i == mDots.length -1){
                nextBtn.setEnabled(false);
                startBtn.setEnabled(true);
                prevBtn.setEnabled(true);
                prevBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);

                startBtn.setText("Start");
                nextBtn.setText("");
                prevBtn.setText("Back");
            }else{
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(true);
                startBtn.setEnabled(false);
                prevBtn.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                prevBtn.setText("Back");
                startBtn.setText("");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
