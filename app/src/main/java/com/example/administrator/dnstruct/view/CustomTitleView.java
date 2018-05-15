package com.example.administrator.dnstruct.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.dnstruct.R;

/**
 * Created by Administrator on 2017/12/23 0023.
 * 自定义view
 * 页面标题栏
 * 页面中常用的条目栏
 * <p>
 * 使用:
 * 暂时只支持高度写死的情况
 * 分别是左右的图片及文字还有标题
 * 可以有多种用途,有待进一步开发
 */

public class CustomTitleView extends RelativeLayout {
    /**
     * 左右两边,文字和图片的默认间距 dp
     */
    private final int default_distance_between_img_and_text = 5;
    /**
     * 默认的左右文字的大小  sp
     */
    private final int default_textsize_small = 16;

    /**
     * 默认的中间文字大小
     */
    private final int default_textsize_big = 18;

    /**
     * 默认文字颜色
     */
    private final int default_text_color = Color.GRAY;

    /**
     * 默认边距  dp
     */
    private final int default_start = 15;

    /**
     * 标题
     */
    private TextView mTitle;

    /**
     * 左边文字
     */
    private TextView mTextLeft;

    /**
     * 右边文字
     */
    private TextView mTextRight;

    /**
     * 标题颜色
     */
    private int titel_color;

    /**
     * 标题文字大小
     */
    private float title_size;
    /**
     * 左边图片地址
     */
    private int img_left_resId;
    /**
     * 图片边距
     */
    private float left_right_padding;
    /**
     * 右边图片地址
     */
    private int img_right_resId;

    //左边图片点击事件
    private OnClickListener mListener_leftClicked;

    //右边图片点击事件
    private OnClickListener mListener_rightImgClicked;
    /**
     * 左边图片相对文字的位置
     * 0=左,1=上,2=右,3=下
     */
    private int img_left_align_text;
    /**
     * 左边图片设置默认设置点击事件
     */
    private boolean img_left_set_default_listener = false;
    /**
     * 左边图片和文字的距离
     */
    private float img_left_distance;
    /**
     * 左边文字的颜色
     */
    private int img_left_text_color;
    /**
     * 左边文字的尺寸
     */
    private float img_left_text_size;
    /**
     * 左边文字
     */
    private String img_left_text_name;
    /**
     * 右边文字
     */
    private String img_right_text_name;
    /**
     * 右边文字大小
     */
    private float img_right_text_size;
    /**
     * 右边文字颜色
     */
    private int img_right_text_color;
    /**
     * 右边文字和图片的距离
     */
    private float img_right_text_distance;
    /**
     * 右边图片相对文字的位置
     */
    private int img_right_align_text;

    /**
     * 整个布局高度是否是wrap_content的
     * 是wrap_content的时候,各个位子的文字的高度要设置成wrap_content
     * 否则,设置成match_parent
     */
    private boolean isWrapContent;

    public CustomTitleView(Context context) {
        super(context);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int mWode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hWode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        if (hWode == MeasureSpec.AT_MOST) {
            measureChildren(widthMeasureSpec, heightMeasureSpec);

            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                int currentHight = childAt.getMeasuredHeight();
                if (currentHight > height) {
                    height = currentHight;
                }
            }
            setMeasuredDimension(wSize, height);
            return;
        }
        Log.e("onMeasure", "wSize=" + wSize + "mWode=" + mWode + "hSize=" + hSize + "hWode=" + hWode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Object tag = childAt.getTag();
            if (tag != null && tag instanceof Integer) {
                int i1 = r - l;
                int l1 = 0;
                int r1 = 0;
                switch ((int) tag) {
                    case 1:
                        l1 = l;
                        r1 = l+childAt.getMeasuredWidth();
                        break;
                    case 2:
                        l1 = (i1 - childAt.getMeasuredWidth()) / 2;
                        r1 = l1 + childAt.getMeasuredWidth();
                        break;
                    case 3:
                        l1 = r-childAt.getMeasuredWidth();
                        r1= r;
                        break;
                }
                childAt.layout(l1, t, r1, b);
            }
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray at = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        //高度是否是wrap_content
        checkIsWrapContent(attrs);
        //标题
        String titel_name = at.getString(R.styleable.CustomTitleView_title_name);
        titel_color = at.getColor(R.styleable.CustomTitleView_title_color, default_text_color);
        title_size = at.getDimension(R.styleable.CustomTitleView_title_size, default_textsize_big);
        Log.e("获取的字体大小==", "" + title_size);
        if (titel_name != null) {
            setTitleText(titel_name);
        }
        //--------------分割线-------------------//
        //左右图片边距
        left_right_padding = at.getDimension(R.styleable.CustomTitleView_title_img_padding_start, dip2px(getContext(), default_start));
        Log.e("获取的左右图片边距==", "" + left_right_padding);
        //--------------分割线-------------------//
        //左边图片
        //左边图片文字,尺寸,颜色,距离,图片位置,图片地址,是否设置默认点击事件
        img_left_text_name = at.getString(R.styleable.CustomTitleView_title_left_name);
        //左边文字的尺寸
        img_left_text_size = at.getDimension(R.styleable.CustomTitleView_title_left_size, default_textsize_small);
        //左边字体的颜色
        img_left_text_color = at.getColor(R.styleable.CustomTitleView_title_left_color, default_text_color);
        //左边图片和文字的距离
        img_left_distance = at.getDimension(R.styleable.CustomTitleView_title_left_distance, default_distance_between_img_and_text);
        //是否设置默认点击事件
        img_left_set_default_listener = at.getBoolean(R.styleable.CustomTitleView_title_left_set_default_click_listener, false);
        //图片位置
        img_left_align_text = at.getInt(R.styleable.CustomTitleView_title_left_img_align_text, 0);
        //图片地址
        img_left_resId = at.getResourceId(R.styleable.CustomTitleView_title_left_img, -1);
        if (img_left_resId != -1) {
            setLeftImg(img_left_resId);
        }
        //--------------分割线-------------------//
        //右边图片文字,尺寸,颜色,距离,图片位置,图片地址
        //右边文字
        img_right_text_name = at.getString(R.styleable.CustomTitleView_title_right_name);
        //右边文字大小
        img_right_text_size = at.getDimension(R.styleable.CustomTitleView_title_right_size, default_textsize_small);
        //右边文字颜色
        img_right_text_color = at.getColor(R.styleable.CustomTitleView_title_right_color, default_text_color);
        //右边文字和图片的距离
        img_right_text_distance = at.getDimension(R.styleable.CustomTitleView_title_right_distance, default_distance_between_img_and_text);
        //右边图片相对文字的位置
        img_right_align_text = at.getInt(R.styleable.CustomTitleView_title_right_img_align_text, 0);
        //右边图片地址地址
        img_right_resId = at.getResourceId(R.styleable.CustomTitleView_title_right_img, -1);
        if (img_right_resId != -1) {
            setRightImg(img_right_resId);
        }
        //-------------分割线-----------------
        at.recycle();
    }

    /**
     * 判断高度设置的属性是否是wrap_content
     */
    private void checkIsWrapContent(AttributeSet attrs) {
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            if (attrName.equals("layout_height")) {
                isWrapContent = attrVal.equals("-2");
                break;
            }
        }
    }


    public void setTitleText(String name) {
        initTitleText();
        mTitle.setText(name);
    }

    public void setTitleColor(int color) {
        titel_color = color;
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
    }

    public void setTitleSize(float title_size) {
        this.title_size = title_size;
        if (mTitle != null) {
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_size);
        }
    }

    private void initTitleText() {
        if (mTitle == null) {
            mTitle = new TextView(getContext());
            mTitle.setTag(2);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, isWrapContent ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mTitle.setGravity(Gravity.CENTER);
            mTitle.setLayoutParams(params);
            mTitle.setTextColor(titel_color);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_size);
            addView(mTitle);
        }
    }

    //---------------------分割线------------------------

    public void setLeftImg(int resId) {
        if (resId != -1 || img_left_text_name != null) {
            //创建左边textview
            initLeftText();
            if (resId != -1) {
                //设置ImageView图片
                Drawable drawable = getContext().getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                switch (img_left_align_text) {
                    case 0:
                        mTextLeft.setCompoundDrawables(drawable, null, null, null);
                        break;
                    case 1:
                        mTextLeft.setCompoundDrawables(null, drawable, null, null);
                        break;
                    case 2:
                        mTextLeft.setCompoundDrawables(null, null, drawable, null);
                        break;
                    case 3:
                        mTextLeft.setCompoundDrawables(null, null, null, drawable);
                        break;
                }
            }
        }
    }

    public void setLeftTextSize(float size) {
        img_left_text_size = size;
        if (mTextLeft != null) {
            mTextLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setLeftText(String text) {
        img_left_text_name = text;
        initLeftText();
        mTextLeft.setText(img_left_text_name);
    }

    public void setLeftTextColor(int color) {
        img_left_text_color = color;
        if (mTextLeft != null) {
            mTextLeft.setTextColor(img_left_text_color);
        }
    }

    public void setLeftImgClickedListener(OnClickListener listener) {
        mListener_leftClicked = listener;
        if (mTextLeft != null) {
            mTextLeft.setOnClickListener(listener);
        }
    }

    /**
     * 初始化左边文字
     */
    private void initLeftText() {
        if (mTextLeft == null) {
            mTextLeft = new TextView(getContext());
            mTextLeft.setTag(1);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, isWrapContent ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            mTextLeft.setLayoutParams(params);
            mTextLeft.setPadding((int) left_right_padding, 0, (int) left_right_padding, 0);
            mTextLeft.setGravity(Gravity.CENTER_VERTICAL);
            //设置文字属性
            mTextLeft.setTextColor(img_left_text_color);
            mTextLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, img_left_text_size);
            if (img_left_text_name != null) {
                mTextLeft.setText(img_left_text_name);
            }
            mTextLeft.setCompoundDrawablePadding((int) img_left_distance);
            //点击事件 默认结束
            if (img_left_set_default_listener) {
                mTextLeft.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = getContext();
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                });
            }
            addView(mTextLeft);
        }
    }


    //---------------------分割线------------------------
    private void setRightImg(int resId) {
        //创建ImageView
        if (resId != -1 || img_right_text_name != null) {
            initRightText();
            //设置ImageView图片
            if (resId != -1) {
                //设置ImageView图片
                Drawable drawable = getContext().getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                switch (img_right_align_text) {
                    case 0:
                        mTextRight.setCompoundDrawables(drawable, null, null, null);
                        break;
                    case 1:
                        mTextRight.setCompoundDrawables(null, drawable, null, null);
                        break;
                    case 2:
                        mTextRight.setCompoundDrawables(null, null, drawable, null);
                        break;
                    case 3:
                        mTextRight.setCompoundDrawables(null, null, null, drawable);
                        break;
                }
            }
        }
    }

    /**
     * 初始化右边文字
     */
    private void initRightText() {
        if (mTextRight == null) {
            mTextRight = new TextView(getContext());
            mTextRight.setTag(3);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, isWrapContent ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            mTextRight.setLayoutParams(params);
            mTextRight.setPadding((int) left_right_padding, 0, (int) left_right_padding, 0);
            mTextRight.setGravity(Gravity.CENTER_VERTICAL);
            //设置文字属性
            mTextRight.setTextColor(img_right_text_color);
            mTextRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, img_right_text_size);
            if (img_right_text_name != null) {
                mTextRight.setText(img_right_text_name);
            }
            mTextRight.setCompoundDrawablePadding((int) img_right_text_distance);
            addView(mTextRight);
        }
    }

    public void setRightTextSize(float size) {
        img_right_text_size = size;
        if (mTextRight != null) {
            mTextRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setRightText(String text) {
        img_right_text_name = text;
        initRightText();
        mTextRight.setText(img_left_text_name);
    }

    public void setRightTextColor(int color) {
        img_right_text_color = color;
        if (mTextRight != null) {
            mTextRight.setTextColor(img_left_text_color);
        }
    }

    public void setRightImgClickedListener(OnClickListener listener) {
        mListener_rightImgClicked = listener;
        if (mTextRight != null) {
            mTextRight.setOnClickListener(listener);
        }
    }

    //-----------------分割线------------------


    /**
     * @return 标题textview
     */
    public TextView getmTitle() {
        return mTitle;
    }

    /**
     * @return 左边textview
     */
    public TextView getmTextLeft() {
        return mTextLeft;
    }

    /**
     * @return 右边textview
     */
    public TextView getmTextRight() {
        return mTextRight;
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
