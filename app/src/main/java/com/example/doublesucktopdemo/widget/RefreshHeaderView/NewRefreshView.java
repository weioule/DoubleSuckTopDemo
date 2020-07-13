package com.example.doublesucktopdemo.widget.RefreshHeaderView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weioule
 * @date 2019/7/26.
 * 很重要一点，绘制不能从  坐标0 开始 会有 stroke*1 的偏移量
 * 绘制时会进行  canvas.translate的操作，所以 绘制坐标时不需要进行 坐标偏移计算
 */
@SuppressLint("NewApi")
public class NewRefreshView extends View {

    private Context mContext;

    private Paint mPaint;
    private State mDragState;

    private Path roundPath; //最外层 圆形Path

    private int strokeWidth;  //线宽

    //绘制不能从  坐标0 开始 会有 stroke*1 的偏移量
    private int contentWidth, contentHeight;  //内容宽度 内容高度

    private float roundCorner;  //外层 圆角矩形 圆角半径
    private float lineWidth;  // 线条宽度

    private float rectWidth;  //小矩形宽度

    private float shortLineWidth; //短线宽度

    private float spaceRectLine;  //小矩形距 断线距离

    public int getContentHeight() {
        return contentHeight;
    }

    public NewRefreshView(Context context) {
        this(context, null);
    }

    public NewRefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        strokeWidth = 2;
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    /**
     * 初始化 path
     */
    private void initPath() {

        roundPath = new Path();

        roundPath.moveTo(contentWidth, roundCorner);
        roundPath.arcTo(contentWidth - roundCorner * 2, 0, contentWidth, roundCorner * 2, 0, -90, false);
        roundPath.lineTo(roundCorner, 0);
        roundPath.arcTo(0, 0, roundCorner * 2, roundCorner * 2, -90, -90, false);
        roundPath.lineTo(0, contentHeight - roundCorner);
        roundPath.arcTo(0, contentHeight - roundCorner * 2, roundCorner * 2, contentHeight, -180, -90, false);
        roundPath.lineTo(contentWidth - roundCorner, contentHeight);
        roundPath.arcTo(contentWidth - roundCorner * 2, contentHeight - roundCorner * 2, contentWidth, contentHeight, -270, -90, false);
        roundPath.close();

        mDragState = new DragState();
    }


    /**
     * 根据 左上 坐标 创建 矩形 Path
     *
     * @param left 左坐标
     * @param top  上坐标
     * @return
     */
    public Path provideRectPath(float left, float top) {
        Path path = new Path();
        path.moveTo(left + rectWidth, top);
        path.lineTo(left, top);
        path.lineTo(left, top + roundCorner * 2f);
        path.lineTo(left + rectWidth, top + roundCorner * 2f);
        path.close();
        return path;
    }


    /**
     * 根据线条 左上 坐标和线宽创建线条 Path
     *
     * @param left      左坐标
     * @param top       上坐标
     * @param lineWidth 线宽
     * @return
     */
    public Path provideLinePath(float left, float top, float lineWidth) {
        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(left + lineWidth, top);
        return path;
    }


    public void setDragState() {
        if (mDragState instanceof DragState) {
            mDragState = new RefreshState1();
        } else if (mDragState instanceof RefreshState1) {
            mDragState = new RefreshState2();
        } else if (mDragState instanceof RefreshState2) {
            mDragState = new RefreshState3();
        } else if (mDragState instanceof RefreshState3) {
            mDragState = new RefreshState4();
        } else if (mDragState instanceof RefreshState4) {
            mDragState = new RefreshState1();
        }
        postInvalidate();
    }

    public void setDrag() {
        mDragState = new DragState();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.restore();
        canvas.translate(strokeWidth, strokeWidth);
        mDragState.onDraw(canvas, mPaint);
        canvas.save();
    }

    public void setFraction(float fraction) {
        if (mDragState instanceof DragState) {
            DragState dragState = (DragState) mDragState;
            dragState.setFraction(fraction);
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);


        //设定最小值时，增加 stoke 的偏移保证 边界绘制完整
        int minWidth = dip2px(25) + strokeWidth * 2;

        int minHeight = dip2px(25) + strokeWidth * 2;

        //判断 测量模式  如果是  wrap_content 需要对 宽高进行限定
        //同时确定 高度 也对 最小值进行限定

        if (widthMode == MeasureSpec.AT_MOST) {
            width = minWidth;
        } else if (widthMode == MeasureSpec.EXACTLY && width < minWidth) {
            width = minWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = minHeight;
        } else if (heightMode == MeasureSpec.EXACTLY && height < minHeight) {
            height = minHeight;
        }

        // 在确定宽高之后 对内容 宽高再次进行计算，留出 stroke 的偏移
        contentWidth = width - strokeWidth * 2;

        contentHeight = height - strokeWidth * 2;

        setMeasuredDimension(width, height);

        initNeedParamn();

        initPath();
    }

    /**
     * 初始化绘制所需要的参数
     */
    private void initNeedParamn() {
        roundCorner = contentHeight / 7f;

        lineWidth = contentWidth - roundCorner * 2;

        rectWidth = lineWidth / 2f;

        //短线条宽度
        shortLineWidth = (lineWidth / 8f) * 3f;

        //矩形与线条之间间距
        spaceRectLine = (lineWidth / 8f) * 1f;
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class RefreshState4 extends State {

        @Override
        protected void initStatePath() {
            PathWrapper pathWrapper = new PathWrapper(roundPath, 1);
            mPathList.add(pathWrapper);


            Path rectPath = provideRectPath(roundCorner, roundCorner * 4f);
            pathWrapper = new RectPathWrapper(rectPath, 1);
            mPathList.add(pathWrapper);


            float shortLeft = roundCorner + rectWidth + spaceRectLine;
            Path shortLine1 = provideLinePath(shortLeft, roundCorner * 4f, shortLineWidth);
            pathWrapper = new RectPathWrapper(shortLine1, 1);
            mPathList.add(pathWrapper);


            Path shortLine2 = provideLinePath(shortLeft, roundCorner * 5f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine2, 1);
            mPathList.add(pathWrapper);


            Path shortLine3 = provideLinePath(shortLeft, roundCorner * 6f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine3, 1);
            mPathList.add(pathWrapper);


            Path longLine1 = provideLinePath(roundCorner, roundCorner * 1f, lineWidth);
            pathWrapper = new PathWrapper(longLine1, 1);
            mPathList.add(pathWrapper);


            Path longLine2 = provideLinePath(roundCorner, roundCorner * 2f, lineWidth);
            pathWrapper = new PathWrapper(longLine2, 1);
            mPathList.add(pathWrapper);


            Path longLine3 = provideLinePath(roundCorner, roundCorner * 3f, lineWidth);
            pathWrapper = new PathWrapper(longLine3, 1);
            mPathList.add(pathWrapper);
        }
    }

    class RefreshState3 extends State {

        @Override
        protected void initStatePath() {
            PathWrapper pathWrapper = new PathWrapper(roundPath, 1);
            mPathList.add(pathWrapper);


            Path rectPath = provideRectPath(contentWidth - roundCorner - rectWidth, roundCorner * 4f);
            pathWrapper = new RectPathWrapper(rectPath, 1);
            mPathList.add(pathWrapper);


            float shortLeft = roundCorner;
            Path shortLine1 = provideLinePath(shortLeft, roundCorner * 4f, shortLineWidth);
            pathWrapper = new RectPathWrapper(shortLine1, 1);
            mPathList.add(pathWrapper);


            Path shortLine2 = provideLinePath(shortLeft, roundCorner * 5f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine2, 1);
            mPathList.add(pathWrapper);


            Path shortLine3 = provideLinePath(shortLeft, roundCorner * 6f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine3, 1);
            mPathList.add(pathWrapper);


            Path longLine1 = provideLinePath(roundCorner, roundCorner * 1f, lineWidth);
            pathWrapper = new PathWrapper(longLine1, 1);
            mPathList.add(pathWrapper);


            Path longLine2 = provideLinePath(roundCorner, roundCorner * 2f, lineWidth);
            pathWrapper = new PathWrapper(longLine2, 1);
            mPathList.add(pathWrapper);


            Path longLine3 = provideLinePath(roundCorner, roundCorner * 3f, lineWidth);
            pathWrapper = new PathWrapper(longLine3, 1);
            mPathList.add(pathWrapper);
        }
    }

    class RefreshState2 extends State {

        @Override
        protected void initStatePath() {
            PathWrapper pathWrapper = new PathWrapper(roundPath, 1);
            mPathList.add(pathWrapper);


            Path rectPath = provideRectPath(contentWidth - roundCorner - rectWidth, roundCorner);
            pathWrapper = new RectPathWrapper(rectPath, 1);
            mPathList.add(pathWrapper);


            float shortLeft = roundCorner;
            Path shortLine1 = provideLinePath(shortLeft, roundCorner, shortLineWidth);
            pathWrapper = new RectPathWrapper(shortLine1, 1);
            mPathList.add(pathWrapper);


            Path shortLine2 = provideLinePath(shortLeft, roundCorner * 2f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine2, 1);
            mPathList.add(pathWrapper);


            Path shortLine3 = provideLinePath(shortLeft, roundCorner * 3f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine3, 1);
            mPathList.add(pathWrapper);


            Path longLine1 = provideLinePath(roundCorner, roundCorner * 4f, lineWidth);
            pathWrapper = new PathWrapper(longLine1, 1);
            mPathList.add(pathWrapper);


            Path longLine2 = provideLinePath(roundCorner, roundCorner * 5f, lineWidth);
            pathWrapper = new PathWrapper(longLine2, 1);
            mPathList.add(pathWrapper);


            Path longLine3 = provideLinePath(roundCorner, roundCorner * 6f, lineWidth);
            pathWrapper = new PathWrapper(longLine3, 1);
            mPathList.add(pathWrapper);
        }
    }

    class RefreshState1 extends State {

        @Override
        protected void initStatePath() {
            PathWrapper pathWrapper = new PathWrapper(roundPath, 1);
            mPathList.add(pathWrapper);


            Path rectPath = provideRectPath(roundCorner, roundCorner);
            pathWrapper = new RectPathWrapper(rectPath, 1);
            mPathList.add(pathWrapper);


            float shortLeft = roundCorner + rectWidth + spaceRectLine;
            Path shortLine1 = provideLinePath(shortLeft, roundCorner, shortLineWidth);
            pathWrapper = new RectPathWrapper(shortLine1, 1);
            mPathList.add(pathWrapper);


            Path shortLine2 = provideLinePath(shortLeft, roundCorner * 2f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine2, 1);
            mPathList.add(pathWrapper);


            Path shortLine3 = provideLinePath(shortLeft, roundCorner * 3f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine3, 1);
            mPathList.add(pathWrapper);


            Path longLine1 = provideLinePath(roundCorner, roundCorner * 4f, lineWidth);
            pathWrapper = new PathWrapper(longLine1, 1);
            mPathList.add(pathWrapper);


            Path longLine2 = provideLinePath(roundCorner, roundCorner * 5f, lineWidth);
            pathWrapper = new PathWrapper(longLine2, 1);
            mPathList.add(pathWrapper);


            Path longLine3 = provideLinePath(roundCorner, roundCorner * 6f, lineWidth);
            pathWrapper = new PathWrapper(longLine3, 1);
            mPathList.add(pathWrapper);
        }
    }

    class DragState extends State {

        private float fraction = 0f;

        public void setFraction(float fraction) {
            this.fraction = fraction;
            mPathList.clear();
            initStatePath();
        }

        @Override
        protected void initStatePath() {
            PathWrapper pathWrapper = new PathWrapper(roundPath, fraction);
            mPathList.add(pathWrapper);


            Path rectPath = provideRectPath(roundCorner, roundCorner);
            pathWrapper = new RectPathWrapper(rectPath, Math.min(1, 4 * fraction));
            mPathList.add(pathWrapper);


            float shortLeft = roundCorner + rectWidth + spaceRectLine;
            Path shortLine1 = provideLinePath(shortLeft, roundCorner, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine1, Math.min(1, 12.5f * (fraction - 0.25f)));
            mPathList.add(pathWrapper);


            Path shortLine2 = provideLinePath(shortLeft, roundCorner * 2f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine2, Math.min(1, 12.5f * (fraction - 0.33f)));
            mPathList.add(pathWrapper);


            Path shortLine3 = provideLinePath(shortLeft, roundCorner * 3f, shortLineWidth);
            pathWrapper = new PathWrapper(shortLine3, Math.min(1, 12.5f * (fraction - 0.41f)));
            mPathList.add(pathWrapper);


            Path longLine1 = provideLinePath(roundCorner, roundCorner * 4f, lineWidth);
            pathWrapper = new PathWrapper(longLine1, Math.min(1, 6.25f * (fraction - 0.5f)));
            mPathList.add(pathWrapper);


            Path longLine2 = provideLinePath(roundCorner, roundCorner * 5f, lineWidth);
            pathWrapper = new PathWrapper(longLine2, Math.min(1, 6.25f * (fraction - 0.66f)));
            mPathList.add(pathWrapper);


            Path longLine3 = provideLinePath(roundCorner, roundCorner * 6f, lineWidth);
            pathWrapper = new PathWrapper(longLine3, Math.min(1, 6.25f * (fraction - 0.82f)));
            mPathList.add(pathWrapper);
        }
    }

    /**
     * 绘制的状态
     */
    public abstract class State {

        protected List<PathWrapper> mPathList;

        public State() {
            mPathList = new ArrayList<>();
            initStatePath();
        }

        protected abstract void initStatePath();

        void onDraw(Canvas canvas, Paint paint) {
            for (PathWrapper path : mPathList) {
                path.onDraw(canvas, paint);
            }
        }
    }
}
