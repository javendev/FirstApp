package com.movieapp.widget.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.movieapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Javen on 2016/7/21.
 */
public class Imageloader{
    private static Imageloader imageloader;
    private Context mContext;

    private Imageloader(Context context){
        mContext=context.getApplicationContext();
    }

    public static Imageloader getInstance(Context context){
        if (imageloader ==null){
            synchronized (Imageloader.class){
                if (imageloader ==null){
                    imageloader = new Imageloader(context);
                }
            }
        }
        return imageloader;
    }
    public  void setImage(String url, ImageView imageView, int defaultImage) {
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .placeholder(defaultImage)
                .into(imageView);
    }

    public void setImageResource(int resId, ImageView imageView, int defaultImage) {
        Glide.with(mContext)
                .load(resId)
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .placeholder(defaultImage)
                .into(imageView);
    }

    public void setImageBitmap(Bitmap bitmap, ImageView imageView, int defaultImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(mContext)
                .load(bytes)
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .placeholder(defaultImage)
                .into(imageView);
    }
//    private SimpleTarget target = new SimpleTarget<Bitmap>( 200, 200 ) {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            imageView.setImageBitmap( bitmap );
//        }
//    };
    public void setImageBitmapTarget(int resId, SimpleTarget target,int defaultImage) {
        Glide.with(mContext.getApplicationContext())
                .load(resId)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .placeholder(defaultImage)
                .into(target);
    }

    //本地的视频文件才有效String filePath = "/storage/emulated/0/Pictures/example_video.mp4";
    public void setImageVideo(String filePath, ImageView imageView, int defaultImage) {
        Glide.with(mContext)
                .load(Uri.fromFile(new File(filePath)))
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .placeholder(defaultImage)
                .into(imageView);
    }

    public void setImageGif(String gifUrl, ImageView imageView, int defaultImage) {
        Glide.with(mContext)
                .load(gifUrl)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .error( R.drawable.default_item_picture )
                .into( imageView );
    }

    public void setImageGif(int resId, ImageView imageView, int defaultImage) {
        Glide.with(mContext)
                .load(resId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .error(R.drawable.default_item_picture)
                .into(imageView);
    }

    public void clearMemory(){
        Glide.get(mContext).clearMemory();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext.getApplicationContext()).clearDiskCache();
            }
        });
    }
}
