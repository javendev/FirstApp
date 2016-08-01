package com.movieapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.movieapp.R;
import com.orhanobut.logger.Logger;

public class UI {

	public interface ItemOnListener{
		void itemOnListener(View view);
		void closeOnListener(View view);
		void btnOnListener(View view);
	}
	
	public static void showDialog(Context context,ItemOnListener listener){
		//必须使用getApplicationContext() 否则不能再home显示对话框
		showDialog(context.getApplicationContext(), R.layout.layout_d_vp, R.style.bs_transparent_dialog, listener);
	}
	
	public static void showDialog(Context context,int resource,int style,final ItemOnListener listener){
		 View view=LayoutInflater.from(context).inflate(resource, null);
		 final AlertDialog dialog = new AlertDialog.Builder(context,style).create();

		 int sdkApi=android.os.Build.VERSION.SDK_INT;
		 if (sdkApi>=19) {
			 dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
		 }else {
			 dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
		}
		 dialog.show();
		
		 WindowManager.LayoutParams params = dialog.getWindow().getAttributes();// 得到属性
		 params.gravity = Gravity.CENTER;// 显示在中间
		
		 DisplayMetrics metrics=  context.getResources().getDisplayMetrics();
		 
		 int dispayWidth =metrics.widthPixels;
		 int dispayHeight =metrics.heightPixels;
		 
//		 params.width=(int)(dispayWidth * 0.9);
//		 params.height=(int)(dispayHeight* 0.9);
		 params.width=dispayWidth;
		 params.height=dispayHeight;
		 dialog.setCanceledOnTouchOutside(false);
		 dialog.setCancelable(false);
		
		 dialog.getWindow().setAttributes(params);// 设置属性
		 dialog.getWindow().setContentView(view);// 把自定义view加上去
		 
		 ImageView close = (ImageView) view.findViewById(R.id.dv_x);
		 Button btn_y = (Button) view.findViewById(R.id.btn_y);
		 Button btn_m = (Button) view.findViewById(R.id.btn_m);
		 Button btn_n = (Button) view.findViewById(R.id.btn_n);


		 view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.itemOnListener(v);
//				dialog.dismiss();
			}
		});
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.closeOnListener(v);
				dialog.dismiss();
			}
		});
		btn_y.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.btnOnListener(v);
				dialog.dismiss();
			}
		});
		btn_m.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.btnOnListener(v);
				dialog.dismiss();
			}
		});
		btn_n.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.btnOnListener(v);
				dialog.dismiss();
			}
		});

	}
	/**
	 * dip 转换成px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	public static float getDensity(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		return density;
	}

	/**
	 * 支付弹窗
	 * @param mContext
     */
	public static void showPayDialog(final Context mContext){
		UI.showDialog(mContext,new UI.ItemOnListener(){

			@Override
			public void itemOnListener(View view) {
				Logger.i("点击了item");
			}

			@Override
			public void closeOnListener(View view) {
				Logger.i("点击了关闭");
				Toast.makeText(mContext,"点击了关闭",Toast.LENGTH_LONG).show();
			}

			@Override
			public void btnOnListener(View view) {
				switch (view.getId()){
					case R.id.btn_m:
						Logger.i("包月");
						Toast.makeText(mContext,"点击了包月",Toast.LENGTH_LONG).show();
						break;
					case R.id.btn_y:
						Logger.i("包年");
						Toast.makeText(mContext,"点击了包年",Toast.LENGTH_LONG).show();
						break;
					case R.id.btn_n:
						Logger.i("终身");
						Toast.makeText(mContext,"点击了终身免费",Toast.LENGTH_LONG).show();
						break;
					default:
						break;
				}
			}
		});
	}

}
