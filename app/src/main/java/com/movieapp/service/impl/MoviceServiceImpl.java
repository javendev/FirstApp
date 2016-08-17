package com.movieapp.service.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movieapp.bean.CategoryModel;
import com.movieapp.bean.HomePage;
import com.movieapp.bean.MovieModel;
import com.movieapp.bean.Page;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IMoviceService;
import com.movieapp.utils.CommonUtils;
import com.movieapp.utils.NetWorkUtils;
import com.movieapp.utils.PhoneHelper;
import com.movieapp.volley.VolleyUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
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
    private Context mContext;

    public MoviceServiceImpl(Context context) {
        mContext=context;
    }


    @Override
    public void getUserById(final Context context, String url, final String userId) {

        HashMap<String, Object> params =new HashMap<>();
        params.put("userId",userId);
        params.put("appId",CommonUtils.APPID);
        VolleyUtils.doPost(context, url, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                String result = parserJson(context, response);
                UserModel userModel = null;
                if (result!=null){
                    userModel = new Gson().fromJson(result.toString(), UserModel.class);
                    if (userModel!=null){
                        userModel.save();
                        EventBus.getDefault().post(new Event.getUserEvent(userModel));
                    }
                }
            }
            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void buildUser(final Context context, String url) {

        List<UserModel> users = DataSupport.findAll(UserModel.class);
        if (users!=null && users.size()>0){
            EventBus.getDefault().post(new Event.getUserEvent(users.get(0)));
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("appId",CommonUtils.APPID);
        params.put("mac", PhoneHelper.getInstance(context).getMac());
        params.put("imei", PhoneHelper.getInstance(context).getImei());
        params.put("iccid", PhoneHelper.getInstance(context).getIccid());
        params.put("imsi", PhoneHelper.getInstance(context).getSubscriberId());

        VolleyUtils.doPost(context, url, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                String result = parserJson(context, response);
                if (result!=null){
                    UserModel userModel = new Gson().fromJson(result.toString(), UserModel.class);
                    if (userModel !=null){
                        getUserById(context,CommonUtils.GET_GET_USER_URL,userModel.getUserid());
                    }else{
                        Logger.e("buildUser 返回的数据为空");
                    }
                }
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getAllCategory(final Context context, final String appId, final boolean isRefresh) {
        //判断是否存在缓存
        if (getAllCategoryCheckCache(appId, isRefresh)) return;

        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",appId);

        VolleyUtils.doPost(context, CommonUtils.GET_CATEGORY_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                String result = parserJson(context, response);
                List<CategoryModel> categoryModels = null;
                if (result!=null){
                    categoryModels = new Gson().fromJson(result, new TypeToken<List<CategoryModel>>(){}.getType());
                    if (categoryModels!=null && categoryModels.size()>0){
                        //删除缓存的数据
                        DataSupport.deleteAll(CategoryModel.class);
                        for (CategoryModel categoryModel:categoryModels) {
                            categoryModel.setAppId(appId);
                            categoryModel.setInsertTime(System.currentTimeMillis());
                            categoryModel.save();
                        }
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

    private boolean getAllCategoryCheckCache(String appId, boolean isRefresh) {
        List<CategoryModel> all = DataSupport.where("appId=?", appId).find(CategoryModel.class);
        Logger.e("分类数据库中的数量："+all.size());

        List<CategoryModel> categoryModels;
        Logger.e("网络的状态："+NetWorkUtils.isNetworkConnected(mContext));
        //没有网络的情况
        if (!NetWorkUtils.isNetworkConnected(mContext)){
            Logger.i("暂无网络...");
            categoryModels = DataSupport.where("appId=?", appId).find(CategoryModel.class);
            if (categoryModels!=null && categoryModels.size()>0) {
                EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels, isRefresh));
                return true;
            }
        }

        Logger.i("查询缓存...");

        categoryModels = DataSupport.where("appId=? and insertTime>=?", appId,String.valueOf(System.currentTimeMillis()-CACHE_TIME)).find(CategoryModel.class);
        if (categoryModels!=null && categoryModels.size()>0){
            Logger.e("从数据库中获取的数据....");
            EventBus.getDefault().post(new Event.getCategoryEvent(categoryModels,isRefresh));
            return true;
        }

        Logger.e("从数net获取的数据....");
        return false;
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
    public void getRankMovices(final Context context, String appId, final int rankType, int pageNumber, int pageSize, final int type) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",appId);
        params.put("rankType",rankType);
        params.put("pageNumber",pageNumber);
        params.put("pageSize",pageSize);

        VolleyUtils.doPost(context, CommonUtils.GET_TYPE_MOVIE_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                Page page = null;
                if (result!=null){
                    page = new Gson().fromJson(result,Page.class);
                }
                if (rankType == CommonUtils.TANK_TYPE_CHOICE) {
                    EventBus.getDefault().post(new Event.getRankMovicesChoice(page,type));
                }else if(rankType == CommonUtils.TANK_TYPE_VIP){
                    EventBus.getDefault().post(new Event.getRankMovicesVip(page,type));
                }
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


    @Override
    public void getMain(final Context context) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("appId",CommonUtils.APPID);

        VolleyUtils.doPost(context, CommonUtils.GET_MAIN_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());
                String result = parserJson(context, response);
                try {

                    HomePage homePage=new HomePage();
                    JSONObject object= new JSONObject(result);
                    JSONArray titleArray = object.getJSONArray("title");
                    JSONArray topArray = object.getJSONArray("top");

                    List<MovieModel> topList = new Gson().fromJson(topArray.toString(), new TypeToken<List<MovieModel>>() {
                    }.getType());
                    Logger.e("广告栏："+topList.size());
                    homePage.setTop(topList);

                    List<HomePage.Title> titleList = new Gson().fromJson(titleArray.toString(), new TypeToken<List<HomePage.Title>>() {
                    }.getType());

                    Logger.e("title："+titleList.size());
                    homePage.setTitle(titleList);

                    EventBus.getDefault().post(new Event.getMainPage(homePage));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public void getFour(final Context context, int videoId) {

        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("videoId",videoId);

        VolleyUtils.doPost(context, CommonUtils.GET_FOUR_URL, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                String result = parserJson(context, response);
                Logger.e("接收到的内容为："+result);
                List<MovieModel> fours = null;
                if (result!=null){
                    fours = new Gson().fromJson(result, new TypeToken<List<MovieModel>>(){}.getType());
                }
                EventBus.getDefault().post(new Event.getFour(fours));
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
