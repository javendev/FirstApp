package com.movieapp.service.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movieapp.bean.CategoryModel;
import com.movieapp.bean.MovieModel;
import com.movieapp.bean.Page;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IMoviceService;
import com.movieapp.utils.CommonUtils;
import com.movieapp.volley.VolleyUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Javen on 2016/7/28.
 */
public class MoviceServiceImpl implements IMoviceService {
    private final  static  long CACHE_TIME=1000*60*30;
    @Override
    public void getUserById(Context context, String url, String userId) {

    }

    @Override
    public void buildUser(final Context context, String url, HashMap<String, Object> params) {
        VolleyUtils.doPost(context, url, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                UserModel userModel = null;
                if (result!=null){
                    userModel = new Gson().fromJson(response.toString(), UserModel.class);
                    Logger.i(userModel.toString());
                }
//                EventBus.getDefault().post(new Event.buildUserEvent(userModel));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getAllCategory(final Context context, final String appId, final boolean isRefresh) {

        List<CategoryModel> all = DataSupport.where("appId=?", appId).find(CategoryModel.class);
        for (CategoryModel categoryModel:all) {
            Logger.e("appid查询 " +appId+ "："+categoryModel.toString());
        }
        List<CategoryModel> categoryModels = DataSupport.where("appId=? and insertTime<=?", appId,String.valueOf(System.currentTimeMillis()-CACHE_TIME)).find(CategoryModel.class);
        if (categoryModels!=null && categoryModels.size()>0){
            Logger.e("从数据库中获取的数据....");
            EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels,isRefresh));
            return;
        }else {
            Logger.e("从数net获取的数据....");
        }
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",appId);

        VolleyUtils.doPost(context, CommonUtils.GET_CATEGORY_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                List<CategoryModel> categoryModels = null;
                if (result!=null){
                    categoryModels = new Gson().fromJson(result, new TypeToken<List<CategoryModel>>(){}.getType());
                    for (CategoryModel categoryModel:categoryModels) {
                        categoryModel.setAppId(appId);
                        categoryModel.setInsertTime(System.currentTimeMillis());
                        categoryModel.save();

                        Logger.i("net 获取的数据："+categoryModel.toString());
                    }
                    List<CategoryModel> all = DataSupport.where("appId=?", appId).find(CategoryModel.class);
                    for (CategoryModel categoryModel:all) {
                        Logger.e("save 之后获取的数据："+categoryModel.toString());
                    }

                }
                EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels,isRefresh));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getMoviesByCategoryId(final Context context, String appId, int categoryId, int pageNumber, int pageSize, final int type) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",appId);
        params.put("categoryId",categoryId);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);

        VolleyUtils.doPost(context, CommonUtils.GET_CATEGORY_MOVIE_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                Page page = null;
                if (result!=null){
                    page = new Gson().fromJson(result,Page.class);
                }
                EventBus.getDefault().post(new Event.getMoviceByCategoryId(page,type));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getRankMovices(final Context context, String appId, int rankType, int pageNumber, int pageSize) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",appId);
        params.put("rankType",rankType);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);

        VolleyUtils.doPost(context, CommonUtils.GET_CATEGORY_MOVIE_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                List<MovieModel> movieModels = null;
                if (result!=null){
                    movieModels = new Gson().fromJson(result, new TypeToken<List<MovieModel>>(){}.getType());
                    for (MovieModel movieModel:movieModels) {
                        Logger.i(movieModel.toString());
                    }
                }
//                EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getMovice(final Context context, int id, String title, String description) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        params.put("title",title);
        params.put("description",description);

        VolleyUtils.doPost(context, CommonUtils.GET_MOVIE_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                MovieModel movieModel = null;
                if (result!=null){
                    movieModel = new Gson().fromJson(response.toString(), MovieModel.class);
                    Logger.i(movieModel.toString());
                }
//                EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    /**
     * 解析json
     * @param context
     * @param response
     * @return
     */
    private  String parserJson(Context context, JSONObject response){
        try {
            String respCode = response.getString("respCode");
            String respMessage = response.getString("respMessage");
            if (respCode.equals("00")){
                return response.getString("data");
            }else {
                Toast.makeText(context, respMessage, Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
