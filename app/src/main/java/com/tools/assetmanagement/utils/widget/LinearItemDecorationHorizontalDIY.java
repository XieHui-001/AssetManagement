package com.tools.assetmanagement.utils.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView水平方向间距
 */

public class LinearItemDecorationHorizontalDIY extends RecyclerView.ItemDecoration {

    private int spacingLeft = 1;
    private int spacingTop = 1;
    private int spacingRight = 1;
    private int spacingBottom = 1;

    private boolean includeLeftEdge;      //整个列表左侧是否有边距
    private boolean includeRightEdge;     //整个列表右侧是否有边距

    public LinearItemDecorationHorizontalDIY() {
    }

    public LinearItemDecorationHorizontalDIY(int spacingLeft, int spacingTop, int spacingRight, int spacingBottom) {
        this.spacingLeft = spacingLeft;
        this.spacingTop = spacingTop;
        this.spacingRight = spacingRight;
        this.spacingBottom = spacingBottom;
    }

    public LinearItemDecorationHorizontalDIY(int spacingLeft, int spacingTop, int spacingRight, int spacingBottom, boolean includeLeftEdge, boolean includeRightEdge) {
        this.spacingLeft = spacingLeft;
        this.spacingTop = spacingTop;
        this.spacingRight = spacingRight;
        this.spacingBottom = spacingBottom;

        this.includeLeftEdge = includeLeftEdge;
        this.includeRightEdge = includeRightEdge;
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
        if (includeLeftEdge && includeRightEdge) {
            //左侧有边距，右侧有边距
            if (childAdapterPosition == 0) {
                outRect.set(spacingLeft, spacingTop, spacingRight, spacingBottom);
            } else {
                outRect.set(0, spacingTop, spacingRight, spacingBottom);
            }
        } else if (!includeLeftEdge && !includeRightEdge) {
            //左侧无边距，右侧无边距
            if (childAdapterPosition == 0) {
                outRect.set(0, spacingTop, 0, spacingBottom);
            } else {
                outRect.set(spacingLeft, spacingTop, 0, spacingBottom);
            }
        } else if (includeLeftEdge) {
            //左侧有边距，右侧无边距
            outRect.set(spacingLeft, spacingTop, 0, spacingBottom);
        } else {
            //左侧无边距，右侧有边距
            outRect.set(0, spacingTop, spacingRight, spacingBottom);
        }
    }

}