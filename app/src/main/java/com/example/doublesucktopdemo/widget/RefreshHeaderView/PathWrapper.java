package com.example.doublesucktopdemo.widget.RefreshHeaderView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * @author weioule
 * @date 2019/7/26.
 */
public class PathWrapper {
    protected Path mPath; //图形 Path
    protected float fraction;  //绘制的比例

    public PathWrapper(Path path, float fraction) {
        mPath = path;
        this.fraction = fraction;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        if (fraction <= 0) {
            return;
        }

        Path dst = new Path();
        // 将 Path 与 PathMeasure 关联
        PathMeasure measure = new PathMeasure(mPath, false);

        float length = measure.getLength();

        // 截取一部分 并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(0, length * fraction, dst, true);
        canvas.drawPath(dst, paint);
    }
}
