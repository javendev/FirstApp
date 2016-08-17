package com.movieapp.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.movieapp.bean.MovieModel;
import com.movieapp.mian.PlayActivity;
import com.movieapp.view.vitamio.VideoViewActivity;

/**
 * Created by Javen on 2016/8/15.
 */
public class LogicService {
    private static LogicService mLogicSercice;
    private Context mContext;
    private LogicService(Context context){
        mContext = context;
    }
    public static LogicService getInstance(Context context){
        if (mLogicSercice == null){
            synchronized (LogicService.class){
                if (mLogicSercice == null){
                    mLogicSercice = new LogicService(context);
                }
            }
        }
        return mLogicSercice;
    }

    /**
     * 跳转到播放的Activity
     * @param movice
     */
    public  void toPlayActivity(MovieModel movice){
        Intent intent= new Intent(mContext, PlayActivity.class);
        intent.putExtra("movice",movice);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 播放视频
     * @param url
     */
    public void viewPlay(String url){
        try {
            Intent mediaIntent= new Intent(Intent.ACTION_VIEW);
            mediaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String extension = MimeTypeMap.getFileExtensionFromUrl(url);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            mediaIntent.setDataAndType(Uri.parse(url),mimeType);
            mContext.startActivity(mediaIntent);
        } catch (Exception e) {
            Toast.makeText(mContext, "播放异常", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 播放视频
     * @param url
     */
    public void vitamioPlay(String url){
        try {
            Intent appIntent = new Intent(mContext,VideoViewActivity.class);
            appIntent.putExtra(VideoViewActivity.VIDEO_PATH,url);
            appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(appIntent);
        } catch (Exception e) {
            Toast.makeText(mContext, "播放异常", Toast.LENGTH_SHORT).show();
        }

    }
}
