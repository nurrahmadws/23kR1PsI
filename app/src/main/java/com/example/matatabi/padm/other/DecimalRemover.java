package com.example.matatabi.padm.other;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class DecimalRemover implements IValueFormatter {
    private DecimalFormat format;

    public DecimalRemover(DecimalFormat decimalFormat) {
        this.format = decimalFormat;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if(value < 0) return "";
        return format.format(value) + " Mahasiswa";
    }
}
