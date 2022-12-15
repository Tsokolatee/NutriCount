package com.example.nutricount;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Confidence implements Comparable<Confidence> {
    int index;
    float value;

    Confidence(int i, float val) {
        this.index = i;
        this.value = val;
    }

    @Override
    public int compareTo(Confidence c) {
        return Float.compare(this.value, c.value);
    }

    @NonNull
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        return df.format(this.value * 100) + "%";
    }
}