package com.example.mahmoudsaed.movieappstage1;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mahmoud Saed on 10/9/2017.
 */

public class column_space extends RecyclerView.ItemDecoration {


    private int mItemOffset;

    public column_space(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public column_space(Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }

}
