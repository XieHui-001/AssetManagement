package com.tools.assetmanagement.utils.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView垂直方向间距
 */

public class LinearItemDecorationVerticalDIY extends RecyclerView.ItemDecoration {

    private int spacingLeft = 1;
    private int spacingTop = 1;
    private int spacingRight = 1;
    private int spacingBottom = 1;

    private boolean includeTopEdge = true;
    private boolean includeBottomEdge = true;

    public LinearItemDecorationVerticalDIY() {
    }

    public LinearItemDecorationVerticalDIY(int spacingLeft, int spacingTop, int spacingRight, int spacingBottom) {
        this.spacingLeft = spacingLeft;
        this.spacingTop = spacingTop;
        this.spacingRight = spacingRight;
        this.spacingBottom = spacingBottom;
    }

    public LinearItemDecorationVerticalDIY(int spacingLeft, int spacingTop, int spacingRight, int spacingBottom, boolean includeTopEdge, boolean includeBottomEdge) {
        this.spacingLeft = spacingLeft;
        this.spacingTop = spacingTop;
        this.spacingRight = spacingRight;
        this.spacingBottom = spacingBottom;

        this.includeTopEdge = includeTopEdge;
        this.includeBottomEdge = includeBottomEdge;
    }

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (includeTopEdge && includeBottomEdge) {
            //顶部有边距，底部有边距
            if (childAdapterPosition == 0) {
                outRect.set(spacingLeft, spacingTop, spacingRight, spacingBottom);
            } else {
                outRect.set(spacingLeft, 0, spacingRight, spacingBottom);
            }
        } else if (!includeTopEdge && !includeBottomEdge) {
            //顶部无边距，底部无边距
            if (childAdapterPosition == 0) {
                outRect.set(spacingLeft, 0, spacingRight, 0);
            } else {
                outRect.set(spacingLeft, spacingTop, spacingRight, 0);
            }
        } else if (includeTopEdge) {
            //顶部有边距，底部无边距
            outRect.set(spacingLeft, spacingTop, spacingRight, 0);
        } else {
            //顶部无边距，底部有边距
            outRect.set(spacingLeft, 0, spacingRight, spacingBottom);
        }
    }

}