package com.example.practicenavigation;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private final int spanCount;

    public GridSpacingItemDecoration(int spacing, int spanCount) {
        this.spacing = spacing;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        outRect.left = spacing - column * spacing / spanCount; // spacing - column * (spacing/spanCount)
        outRect.right = (column + 1) * spacing / spanCount;  // (column + 1) * (spacing/spanCount)
        outRect.bottom = spacing;

        // Add top margin only for the first row
        if (position < spanCount) {
            outRect.top = spacing;
        } else {
            outRect.top = 0;
        }
    }
}
