package com.example.matatabi.padm.slider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.matatabi.padm.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
        R.drawable.onboarsreenmhs1,
        R.drawable.onboarscreenmhs2,
        R.drawable.onboarscreenmhs3
    };

    public String[] slide_headings = {
        "SELAMAT DATANG",
        "TUJUAN",
        "FITUR-FITUR"
    };

    public String[] slide_desc = {
            "Selamat datang di aplikasi Sistem Informasi Geografis Pemetaan Asal Daerah Mahasiswa (PADM) berbasis android. Aplikasi ini dibuat untuk " +
                    "memudahkan para mahasiswa jurusan Teknik Informatika Universitas Mulawarman Samarinda dalam mencari informasi mengenai daerah asalnya " +
                    "serta mencari informasi mengenai daerah asal mahasiswa lain. Informasi yang ditampilkan dapat berupa data daerah asal mahasiswa tersebut, serta " +
                    "dapat berupa peta lokasi.",
            "Tujuan dibuatnya aplikasi ini yaitu untuk memudahkan dalam menyajikan informasi mengenai daerah asal mahasiswa FKTI UNMUL khususnya jurusan" +
                    "Teknik Informatika. Informasi tersebut dapat berupa peta lokasi serta biodata mahasiswa yang ingin dicari daerah asalnya",
            "Beberapa fitur yang terdapat di aplikasi ini yaitu dapat mencari daerah asal mahasiswa lain dan menampilkannya dalam bentuk peta lokasi, " +
                    "serta dapat melihat daerah asal kita dalam bentuk peta lokasi."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = view.findViewById(R.id.slideimage1);
        TextView slideHead = view.findViewById(R.id.slideheading);
        TextView SlideDesc = view.findViewById(R.id.slidedesc);

        slideImage.setImageResource(slide_images[position]);
        slideHead.setText(slide_headings[position]);
        SlideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
