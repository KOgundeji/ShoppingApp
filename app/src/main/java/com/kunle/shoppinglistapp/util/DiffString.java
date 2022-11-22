package com.kunle.shoppinglistapp.util;

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DiffString extends TypefaceSpan {
    public DiffString(@Nullable String family) {
        super(family);
    }

    public DiffString(@NonNull Typeface typeface) {
        super(typeface);
    }

    public DiffString(@NonNull Parcel src) {
        super(src);
    }
}
