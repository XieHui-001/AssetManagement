package com.tools.assetmanagement.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class SpannableStringUtils {

    /**
     * 设置字符串中指定字符高亮
     *
     * @param context
     * @param start
     * @param end
     * @param strResId
     * @param color
     * @param textView
     */
    public static void setSpanString(Context context, int start, int end, int strResId, int color, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(context.getString(strResId));
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 设置content的复文本颜色
     *
     * @param context
     * @param colorResId
     * @param content
     * @return
     */
    public static SpannableStringBuilder getSpanString(Context context, int colorResId, String content) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(colorResId));
        builder.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder getSpanString(Context context, int colorResId, int start, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorResId)), start, contentStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder getImageSpanString(Context context, int spanColorResId, int spanTextSize, String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        if (start != -1) {
            int end = start + spanStr.length();
//        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(spanColorResId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(new AbsoluteSizeSpan(spanTextSize,true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(new BackgroundImageSpan(R.drawable.shape_background_blue, context.getResources().getDrawable(R.drawable.shape_background_blue)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder getBoldSpanString(String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        if (start != -1) {
            int end = start + spanStr.length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder getBoldSpanString(int spanTextSize, String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        if (start != -1) {
            int end = start + spanStr.length();
            builder.setSpan(new AbsoluteSizeSpan(spanTextSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder getBoldSpanString(Context context, int spanColorResId, int spanTextSize, String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        if (start != -1) {
            int end = start + spanStr.length();
            builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(spanColorResId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new AbsoluteSizeSpan(spanTextSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder getSizeSpanString(int spanTextSize, String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        int end = start + spanStr.length();
        builder.setSpan(new AbsoluteSizeSpan(spanTextSize,true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }


    public static SpannableStringBuilder getPrivacySpanString(Context context, int colorResId, String content, String... children) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int color = context.getResources().getColor(colorResId);

        ClickableSpan clickSpanPrivacy = new ClickableSpan() {
            @Override
            public void onClick(View view) {

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //设置颜色
                ds.setColor(color);
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickSpanUserProtocol = new ClickableSpan() {
            @Override
            public void onClick(View view) {

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //设置颜色
                ds.setColor(color);
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        };


        ClickableSpan three = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLong("测试");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //设置颜色
                ds.setColor(color);
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        };

        for (String str : children) {
            //查找原始字符串
            int fromIndex = 0; //缓存起始位置，防止相同的字符串无法匹配
            while (true) {
                if (TextUtils.isEmpty(str)) {
                    break;
                }

                int start = content.indexOf(str, fromIndex);
                if (start == -1) {
                    break;
                }

                int end = start + str.length();
                fromIndex = end;
                if ("《隐私政策》".equals(str)) {
                    builder.setSpan(clickSpanPrivacy, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if ("《用户协议》".equals(str)) {
                    builder.setSpan(clickSpanUserProtocol, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }else if ("位置信息".equals(str)){
                    builder.setSpan(three, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return builder;
    }



    /**
     * 字符串中指定某些字符为特定颜色
     *
     * @param context
     * @param colorResId 颜色值
     * @param content    内容字符串
     * @param children   子字符集
     * @return
     */
    public static SpannableStringBuilder getSpanString(Context context, int colorResId, String content, String... children) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int color = context.getResources().getColor(colorResId);
        int fromIndex = 0; //缓存起始位置，防止相同的字符串无法匹配
        for (String str : children) {
            int start = content.indexOf(str, fromIndex);
            if (start != -1) {
                int end = start + str.length();
                fromIndex = end;
                builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }


    public static SpannableStringBuilder getSpanStringAll(Context context, int colorResId, String content, List<String> children) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int color = context.getResources().getColor(colorResId);
        for (String str : children) {
            //查找原始字符串
            int fromIndex = 0; //缓存起始位置，防止相同的字符串无法匹配
            while (true) {
                if (TextUtils.isEmpty(str)) {
                    break;
                }

                int start = content.indexOf(str, fromIndex);
                if (start == -1) {
                    break;
                }
                int end = start + str.length();
                fromIndex = end;
                builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    public static SpannableStringBuilder getSpanString(Context context, int colorResId, String content, List<String> children) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int color = context.getResources().getColor(colorResId);
        int fromIndex = 0; //缓存起始位置，防止相同的字符串无法匹配
        for (String str : children) {
            int start = content.indexOf(str, fromIndex);
            if (start != -1) {
                int end = start + str.length();
                fromIndex = end;
                builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    public static SpannableStringBuilder getUnderlineSpanString(String content, String... children) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int fromIndex = 0; //缓存起始位置，防止相同的字符串无法匹配
        for (String str : children) {
            int start = content.indexOf(str, fromIndex);
            if (start != -1) {
                int end = start + str.length();
                fromIndex = end;
                builder.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    public static SpannableStringBuilder getSpanString(Context context, int spanColorResId, int spanTextSize, String spanStr, String contentStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contentStr);
        int start = contentStr.indexOf(spanStr);
        int end = start + spanStr.length();
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(spanColorResId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(spanTextSize,true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private static SpannableStringBuilder getSpanString(Context context, int spanColorResId, int spanTextSize, int spanStrResId, int contentStrResId) {
        String contentStr = context.getString(contentStrResId);
        String spanStr = context.getString(spanStrResId);
        return getSpanString(context, spanColorResId, spanTextSize, spanStr, contentStr);
    }

}
