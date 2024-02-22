package com.tools.assetmanagement.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class GlideUtils {

    /**
     * 显示网络图片
     *
     * @param context
     * @param imageView
     * @param placeHolder 默认图片
     * @param imageUrl    网络图片地址
     */
    public static void showImageCenterCrop(Context context, ImageView imageView, int placeHolder, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)           //加载成功之前占位图
                .error(placeHolder)                 //加载错误之后的错误图
                .centerCrop()                       //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 显示网络图片
     *
     * @param imageView
     * @param placeHolder 默认图片
     * @param imageUrl    网络图片地址
     */
    public static void showImageCenterCrop(ImageView imageView, int placeHolder, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)           //加载成功之前占位图
                .error(placeHolder)                 //加载错误之后的错误图
                .centerCrop()                       //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

        Glide.with(imageView)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 显示网络图片
     *
     * @param imageView
     * @param placeHolder 默认图片
     * @param imageUrl    网络图片地址
     */
    public static void showImageFitCenter(ImageView imageView, int placeHolder, String imageUrl) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)           //加载成功之前占位图
                .error(placeHolder)                 //加载错误之后的错误图
                .fitCenter()                       //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

        Glide.with(imageView)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    public static void showImageDynamicCrop(ImageView imageView, int placeHolder, String imageUrl) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)    //加载成功之前占位图
                .error(placeHolder)    //加载错误之后的错误图
                .centerCrop()//指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片

        Glide.with(imageView)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(new CustomTarget<Bitmap>() { //获取图片的实际尺寸
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();

                        int width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(30);
                        int height = (int) (((double) width / (double) imageWidth) * (double) imageHeight);

                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = width;
                        params.height = height;
                        imageView.setLayoutParams(params);

                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
