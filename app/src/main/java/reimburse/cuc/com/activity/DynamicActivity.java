package reimburse.cuc.com.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Traffic_Cost;
import reimburse.cuc.com.bean.Traffic_Fare_Basis;
import reimburse.cuc.com.bean.Traffic_Type;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.util.DensityUtil;
import reimburse.cuc.com.util.FileUtils;
import reimburse.cuc.com.reimburse.PhotoActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

public class DynamicActivity extends Activity implements OnItemClickListener {

	private static final String TAG = DynamicActivity.class.getSimpleName();
	private StringBuffer invoice_pic_urls = new StringBuffer();
	private GridView gridview;
	private GridAdapter adapter;

	private ScrollView activity_selectimg_scrollView;
	private HorizontalScrollView selectimg_horizontalScrollView;


	private float dp;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();

	List<String> urList = new ArrayList<String>();

	/*填充下拉列表的数据*/
	List<String> popWindowList;

	private Button btn_complete;

	private EditText et_input;

	/*票价级别*/
	private EditText et_fare_basis;


	/**
	 *
	 */
	private PopupWindow popupWindow;
	private PopupWindow popupWindowFareBasis;

	private MyBaseAdapter myAdapter;


	private EditText et_traffic_cost_start_city;
	private EditText et_traffic_cost_end_city;

	private EditText et_start_date;
	private EditText et_end_date;

	private EditText et_start_time;
	private EditText et_end_time;


	private EditText et_traffic_cost_unit_price;
	private EditText et_traffic_cost_ticket_number;
	private EditText et_traffic_cost_total_amount;
	private EditText et_traffic_cost_desc;

	private Button btn_save_traffic_cost;

	private ListView listview;
	private ListView traffic_fare_basis_listview;

	List<Traffic_Type> traffic_typeList;
	List<Traffic_Fare_Basis> traffic_fare_basisList;

	private List<String> traffic_cost_invoice_pic_url_list = new ArrayList<>();
	static  final int SHOW_ALL_TRAFFIC_TYPE = 0066;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
        btn_complete = (Button) findViewById(R.id.pic_btn);
		et_input = (EditText) findViewById(R.id.et_input);
		et_fare_basis = (EditText) findViewById(R.id.et_fare_basis);


		et_traffic_cost_start_city = (EditText) findViewById(R.id.et_traffic_cost_start_city);
		et_traffic_cost_end_city = (EditText) findViewById(R.id.et_traffic_cost_end_city);

		et_start_date = (EditText) findViewById(R.id.et_start_date);
		et_end_date = (EditText) findViewById(R.id.et_end_date);

		et_start_time = (EditText) findViewById(R.id.et_start_time);
		et_end_time = (EditText) findViewById(R.id.et_end_time);

		et_traffic_cost_unit_price = (EditText) findViewById(R.id.et_traffic_cost_unit_price);
		et_traffic_cost_ticket_number = (EditText) findViewById(R.id.et_traffic_cost_ticket_number);
		et_traffic_cost_total_amount = (EditText) findViewById(R.id.et_traffic_cost_total_amount);

		et_traffic_cost_desc = (EditText) findViewById(R.id.et_traffic_cost_desc);

		btn_save_traffic_cost = (Button) findViewById(R.id.btn_save_traffic_cost);

		et_traffic_cost_ticket_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {

				}else{

					String traffic_cost_unit_price = et_traffic_cost_unit_price.getText().toString();
					String traffic_cost_ticket_number = et_traffic_cost_ticket_number.getText().toString();

					if(traffic_cost_ticket_number != null && traffic_cost_unit_price != null) {

						BigDecimal unit_price = new BigDecimal(traffic_cost_unit_price);
						BigDecimal ticket_number = new BigDecimal(traffic_cost_ticket_number);
						BigDecimal total_amount = unit_price.multiply(ticket_number);
						et_traffic_cost_total_amount.setText(total_amount.toString());
					}

				}
			}
		});




		traffic_typeList = new ArrayList<>();
		traffic_fare_basisList = new ArrayList<>();

		listview = new ListView(this);
		listview.setBackgroundResource(R.drawable.listview_background);

		traffic_fare_basis_listview = new ListView(this);
		traffic_fare_basis_listview.setBackgroundResource(R.drawable.listview_background);

		Init();




		GetAllTrafficTypeWithFareBasisList();

		for (Traffic_Type traffic_type : traffic_typeList){
			Log.e(TAG,traffic_type.getTra_type_name());
			Log.e(TAG,traffic_type.getTra_type_uuid()+"");
			List<Traffic_Fare_Basis> traffic_fare_basises = traffic_type.getTraffic_Fare_Basis_List();
		}

		listview.setAdapter(new MyBaseAdapter());




		et_input.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (popupWindow == null) {
					popupWindow = new PopupWindow(DynamicActivity.this);
					popupWindow.setWidth(et_input.getWidth());
					int height = DensityUtil.dip2px(DynamicActivity.this, 200);//dp->px
					//Toast.makeText(DynamicActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
					popupWindow.setHeight(height);//px

					popupWindow.setContentView(listview);
					popupWindow.setFocusable(true);//设置焦点
				}

				popupWindow.showAsDropDown(et_input, 0, 0);
			}
		});

		et_fare_basis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (popupWindowFareBasis == null) {
					popupWindowFareBasis = new PopupWindow(DynamicActivity.this);
					popupWindowFareBasis.setWidth(et_fare_basis.getWidth());
					int height = DensityUtil.dip2px(DynamicActivity.this, 200);//dp->px
					Toast.makeText(DynamicActivity.this, "height==" + height, Toast.LENGTH_SHORT).show();
					popupWindowFareBasis.setHeight(height);//px

					popupWindowFareBasis.setContentView(traffic_fare_basis_listview);
					popupWindowFareBasis.setFocusable(true);//设置焦点
				}

				popupWindowFareBasis.showAsDropDown(et_fare_basis, 0, 0);
			}
		});




		defaultDate(et_start_date);
		defaultDate(et_end_date);
		defaultTime(et_start_time);
		defaultTime(et_end_time);

		et_start_date.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDate(v);
			}
		});

		et_end_date.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDate(v);
			}
		});


		et_start_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTime(v);
			}
		});

		et_end_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTime(v);
			}
		});




		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Traffic_Type traffic_type = traffic_typeList.get(position);
				//1.得到数据
				String msg = traffic_typeList.get(position).getTra_type_name();
				//2.设置输入框
				et_input.setText(msg);
				et_fare_basis.setText("客票级别");
				traffic_fare_basisList = traffic_type.getTraffic_Fare_Basis_List();
				traffic_fare_basis_listview.setAdapter(new FareBasisBaseAdapter());

				if (popupWindow != null && popupWindow.isShowing()) {

					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});

		traffic_fare_basis_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Traffic_Fare_Basis  traffic_fare_basis = traffic_fare_basisList.get(position);
				//1.得到数据
				String msg = traffic_fare_basis.getBl_traffic_fare_basis_name();
				//2.设置输入框
				et_fare_basis.setText(msg);

				if (popupWindowFareBasis != null && popupWindowFareBasis.isShowing()) {

					popupWindowFareBasis.dismiss();
					popupWindowFareBasis = null;
				}
			}
		});


		/**
		 *保存发票图片
		 */
		btn_complete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//urList.clear();
				if (bmp.size() < 1) {
					Toast.makeText(getApplicationContext(), "至少需要一张图片",
							Toast.LENGTH_SHORT).show();
					return;
				}

				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(DynamicActivity.this
										.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);

				for (int i = 0; i < drr.size(); i++) {
					urList.add(drr.get(i));
				}
				for (int i = 0; i < urList.size(); i++) {
					Log.e(TAG, "第"+i+"个图片地址:" + urList.get(i));
				}

				for (int i=0;i< urList.size();i++) {
					String pic = urList.get(i);
					final File file = new File(pic);
					new Thread(new Runnable() {
						@Override
						public void run() {
							RequestBody filebody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
							OkHttpClient client = new OkHttpClient();
							MultipartBody requestBody = new MultipartBody.Builder()
									.addFormDataPart("fapiaoImg", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(10) + ".png", filebody)
									.build();
							Request request = new Request.Builder().post(requestBody).url(Constants.SAVE_FAPIAO_IMG_URL).build();
							Response response = null;
							try {
								response = client.newCall(request).execute();
								String picUrl = response.body().string();
								traffic_cost_invoice_pic_url_list.add(picUrl);
								/*if (i == urList.size() - 1) {
									invoice_pic_urls.append(picUrl);
								} else {
									invoice_pic_urls.append(picUrl + "|");
								}*/
								Log.e("---上传图片返回的结果---", picUrl);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}).start();
				}


				//saveTrafficCost();
				// 图片地址 urList；
			}
		});

		btn_save_traffic_cost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveTrafficCost();
			}
		});


		/**
		 * intent.putExtra("TrafficCostJsonString",jsonString);
		 */
		Intent intent = getIntent();

		String TrafficCostJsonString = intent.getStringExtra("TrafficCostJsonString");
		// JSON串转用户组对象
		Traffic_Cost traffic_cost = JSON.parseObject(TrafficCostJsonString,Traffic_Cost.class);

		et_traffic_cost_start_city.setText(traffic_cost.getTraffic_cost_start_city());
		et_traffic_cost_end_city.setText(traffic_cost.getTraffic_cost_end_city());


	}

	/**
	 * 向服务端保存交通费
	 */
	public void saveTrafficCost(){
		for(int i=0;i<traffic_cost_invoice_pic_url_list.size();i++){
			if(i == traffic_cost_invoice_pic_url_list.size()-1) {
			    invoice_pic_urls.append(traffic_cost_invoice_pic_url_list.get(i));
			}else {
				invoice_pic_urls.append(traffic_cost_invoice_pic_url_list.get(i)+"|");
			}
		}

		new Thread() {
			public void run() {
				try {

					// 1.指定提交数据的路径,post的方式不需要组拼任何的参数
					String path = Constants.AndroidSaveTraffic_Cost;
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//2.指定请求方式为post
					conn.setRequestMethod("POST");
					//3.设置http协议的请求头
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

					String data = "traffic_cost_type="+ URLEncoder.encode(et_input.getText().toString(), "utf-8")+
							"&traffic_cost_fare_basis="+URLEncoder.encode(et_fare_basis.getText().toString(),"utf-8")
							+"&traffic_cost_start_city="+URLEncoder.encode(et_traffic_cost_start_city.getText().toString(),"utf-8")
							+"&traffic_cost_end_city="+URLEncoder.encode(et_traffic_cost_end_city.getText().toString(),"utf-8")
							+"&traffic_cost_start_datetime="+URLEncoder.encode(et_start_date.getText().toString()+" "+et_start_time.getText().toString(),"utf-8")
							+"&traffic_cost_end_datetime="+URLEncoder.encode(et_end_date.getText().toString()+" "+et_end_time.getText().toString(),"utf-8")
							+"&traffic_cost_unit_price="+URLEncoder.encode(et_traffic_cost_unit_price.getText().toString(),"utf-8")
							+"&traffic_cost_ticket_number="+URLEncoder.encode(et_traffic_cost_ticket_number.getText().toString(),"utf-8")
							+"&traffic_cost_total_amount="+URLEncoder.encode(et_traffic_cost_total_amount.getText().toString(),"utf-8")
							+"&traffic_cost_desc="+URLEncoder.encode(et_traffic_cost_desc.getText().toString(),"utf-8")
							+"&traffic_cost_invoice_pic_url="+URLEncoder.encode(invoice_pic_urls.toString(),"utf-8")
							+"&traffic_cost_user_id="+URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID), "utf-8");

					Log.e(TAG+"交通费参数：",data);

					conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
					//post的请求是把数据以流的方式写给了服务器
					//指定请求的输出模式
					conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
					conn.getOutputStream().write(data.getBytes());
					Log.e("post size",String.valueOf(data.getBytes().length));
					int code = conn.getResponseCode();
					if(code == 200){
						InputStream is = conn.getInputStream();
						final String result = StreamTools.readStream(is);
						showToastInAnyThread(result);
						if(result.equals("保存成功")) {
							finish();
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intent);*/
						}
					}else{
						showToastInAnyThread("请求失败");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}


	private void showToastInAnyThread(final String result) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {//运行在主线程,内部做了处理
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			}
		});
	}

	public void GetAllTrafficTypeWithFareBasisList(){
		new Thread() {
			public void run() {
				try {
					// 1.指定提交数据的路径,post的方式不需要组拼任何的参数
					String path = Constants.AndroidGetAllTrafficTypeWithFareBasisList;
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//2.指定请求方式为post
					conn.setRequestMethod("POST");
					//3.设置http协议的请求头
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型

					String data ="&user_id="+URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID),"utf-8");

					conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
					//post的请求是把数据以流的方式写给了服务器
					//指定请求的输出模式
					conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
					conn.getOutputStream().write(data.getBytes());
					Log.e("post size", String.valueOf(data.getBytes().length));
					int code = conn.getResponseCode();

					if(code == 200){
						InputStream is = conn.getInputStream();
						final String result = StreamTools.readStream(is);
						traffic_typeList = JSON.parseArray(result, Traffic_Type.class);


						Log.e("返回的JSON数据", result);
						//showToastInAnyThread(result);
						if(result.equals("保存成功")) {
							//finish();
                          /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                           startActivity(intent);*/
						}
					}else{
						showToastInAnyThread("请求失败");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}


	class  MyBaseAdapter extends  BaseAdapter{

		@Override
		public int getCount() {
			return traffic_typeList.size();
		}

		@Override
		public Object getItem(int position) {
			return traffic_typeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView  == null){
				convertView = View.inflate(DynamicActivity.this,R.layout.popwindow_item,null);
				viewHolder = new ViewHolder();
				viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}

			//根据位置得到数据
			final Traffic_Type traffic_type = traffic_typeList.get(position);
			viewHolder.tv_msg.setText(traffic_type.getTra_type_name());

			return convertView;
		}
	}

	static class ViewHolder{
		TextView tv_msg;
	}
	class  FareBasisBaseAdapter extends  BaseAdapter{

		@Override
		public int getCount() {
			return traffic_fare_basisList.size();
		}

		@Override
		public Object getItem(int position) {
			return traffic_fare_basisList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FareBasisBaseViewHolder viewHolder;
			if(convertView  == null){
				convertView = View.inflate(DynamicActivity.this,R.layout.fare_basis_popwindow_item,null);
				viewHolder = new FareBasisBaseViewHolder();
				viewHolder.tv_fare_basis = (TextView) convertView.findViewById(R.id.tv_fare_basis);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (FareBasisBaseViewHolder) convertView.getTag();
			}

			//根据位置得到数据
			final Traffic_Fare_Basis fare_basis = traffic_fare_basisList.get(position);
			viewHolder.tv_fare_basis.setText(fare_basis.getBl_traffic_fare_basis_name());

			return convertView;
		}
	}

	static class FareBasisBaseViewHolder{
		TextView tv_fare_basis;
	}


	public void Init() {
		dp = getResources().getDimension(R.dimen.dp);
		selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.selectimg_horizontalScrollView);
		gridview = (GridView) findViewById(R.id.noScrollgridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridviewInit();

		activity_selectimg_scrollView = (ScrollView) findViewById(R.id.activity_selectimg_scrollView);
		activity_selectimg_scrollView.setVerticalScrollBarEnabled(false);

		final View decorView = getWindow().getDecorView();
		final WindowManager wm = this.getWindowManager();

		decorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						@SuppressWarnings("deprecation")
						int displayheight = wm.getDefaultDisplay().getHeight();
						Rect rect = new Rect();
						decorView.getWindowVisibleDisplayFrame(rect);
						int dynamicHight = rect.bottom - rect.top;
						float ratio = (float) dynamicHight
								/ (float) displayheight;

						if (ratio > 0.2f && ratio < 0.6f) {
							activity_selectimg_scrollView.scrollBy(0,
									activity_selectimg_scrollView.getHeight());
						}
					}
				});

	}




	public void gridviewInit() {
		adapter = new GridAdapter(this);
		adapter.setSelectedPosition(0);
		int size = 0;
		if (bmp.size() < 6) {
			size = bmp.size() + 1;
		} else {
			size = bmp.size();
		}
		LayoutParams params = gridview.getLayoutParams();
		final int width = size * (int) (dp * 9.4f);
		params.width = width;
		gridview.setLayoutParams(params);
		gridview.setColumnWidth((int) (dp * 9.4f));
		gridview.setStretchMode(GridView.NO_STRETCH);
		gridview.setNumColumns(size);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);

		selectimg_horizontalScrollView.getViewTreeObserver()
				.addOnPreDrawListener(// 绘制完毕
						new OnPreDrawListener() {
							public boolean onPreDraw() {
								selectimg_horizontalScrollView.scrollTo(width * 3,
										0);
								selectimg_horizontalScrollView
										.getViewTreeObserver()
										.removeOnPreDrawListener(this);
								return false;
							}
						});
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		// temp=comment_content.getText().toString().trim();
		super.onPause();
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater listContainer;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public class ViewHolder {
			public ImageView image;
			public Button bt;
		}

		public GridAdapter(Context context) {
			listContainer = LayoutInflater.from(context);
		}

		public int getCount() {
			if (bmp.size() < 6) {
				return bmp.size() + 1;
			} else {
				return bmp.size();
			}
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int sign = position;
			// 自定义视图
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				// 获取list_item布局文件的视图

				convertView = listContainer.inflate(
						R.layout.item_published_grida, null);

				// 获取控件对象
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				holder.bt = (Button) convertView
						.findViewById(R.id.item_grida_bt);
				// 设置控件集到convertView
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				holder.bt.setVisibility(View.GONE);
				if (position == 6) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(bmp.get(position));
				holder.bt.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						PhotoActivity.bitmap.remove(sign);
						bmp.get(sign).recycle();
						bmp.remove(sign);
						drr.remove(sign);

						gridviewInit();
					}
				});
			}

			return convertView;
		}
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			// ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
			// R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			/*拍照按钮*/
			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(
							// 相册
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, RESULT_LOAD_IMAGE);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0;
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int CUT_PHOTO_REQUEST_CODE = 2;
	private static final int SELECTIMG_SEARCH = 3;
	private String path = "";
	private Uri photoUri;

	public void photo() {
		try {
			Intent openCameraIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);

			String sdcardState = Environment.getExternalStorageState();
			String sdcardPathDir = android.os.Environment
					.getExternalStorageDirectory().getPath() + "/tempImage/";
			File file = null;
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				// 有sd卡，是否有myImage文件夹
				File fileDir = new File(sdcardPathDir);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				// 是否有headImg文件
				file = new File(sdcardPathDir + System.currentTimeMillis()
						+ ".JPEG");
			}
			if (file != null) {
				path = file.getPath();
				photoUri = Uri.fromFile(file);
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

				startActivityForResult(openCameraIntent, TAKE_PICTURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case TAKE_PICTURE:
				if (drr.size() < 6 && resultCode == -1) {// 拍照
					startPhotoZoom(photoUri);
				}
				break;
			case RESULT_LOAD_IMAGE:
				if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {// 相册返回
					Uri uri = data.getData();
					if (uri != null) {
						startPhotoZoom(uri);
					}
				}
				break;
			case CUT_PHOTO_REQUEST_CODE:
				if (resultCode == RESULT_OK && null != data) {// 裁剪返回
					Bitmap bitmap = Bimp.getLoacalBitmap(drr.get(drr.size() - 1));
					PhotoActivity.bitmap.add(bitmap);
					bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
							(int) (dp * 1.6f));
					bmp.add(bitmap);
					gridviewInit();
				}
				break;
		}
	}

	private void startPhotoZoom(Uri uri) {

		Log.e(TAG,uri.toString());
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			// 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String address = sDateFormat.format(new java.util.Date())+new Random().nextInt(30);
			if (!FileUtils.isFileExist("")) {
				FileUtils.createSDDir("");

			}



			drr.add(FileUtils.SDPATH + address + ".JPEG");
			Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
					+ ".JPEG");

			final Intent intent = new Intent("com.android.camera.action.CROP");

			// 照片URL地址
			intent.setDataAndType(uri, "image/*");

			intent.putExtra("crop", "true");
			/*intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);*/
			// 裁剪框的比例，width：height
			intent.putExtra("aspectX", width);
			intent.putExtra("aspectY", height);
			intent.putExtra("outputX", width);
			intent.putExtra("outputY", height);
			// 输出路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 输出格式
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			// 不启用人脸识别
			intent.putExtra("noFaceDetection", false);
			intent.putExtra("return-data", false);
			startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void onDestroy() {

		FileUtils.deleteDir(FileUtils.SDPATH);
		FileUtils.deleteDir(FileUtils.SDPATH1);
		// 清理图片缓存
		for (int i = 0; i < bmp.size(); i++) {
			bmp.get(i).recycle();
		}
		for (int i = 0; i < PhotoActivity.bitmap.size(); i++) {
			PhotoActivity.bitmap.get(i).recycle();
		}
		PhotoActivity.bitmap.clear();
		bmp.clear();
		drr.clear();
		super.onDestroy();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(DynamicActivity.this
								.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		if (arg2 == bmp.size()) {
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				new PopupWindows(DynamicActivity.this, gridview);
			} else {
				Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Intent intent = new Intent(DynamicActivity.this,
					PhotoActivity.class);

			intent.putExtra("ID", arg2);
			startActivity(intent);
		}
	}


	/*22222222222222222222222222222222===日期显示处理开始===2222222222222222222222222222222222222222222222*/
	public void defaultDate(View view){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		et_start_date.setText(year + "-" + (month + 1) + "-" + day);
		et_end_date.setText(year + "-" + (month + 1) + "-" + day);
	}
	public void defaultTime(View view){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		et_start_time.setText(hour+":"+minute);
		et_end_time.setText(hour+":"+minute);
	}
	public void setDate(View view){
		SetDateDialog sdd = new SetDateDialog();
		sdd.show(getFragmentManager(), "datepicker");
	}
	//创建日期选择对话框
	@SuppressLint("ValidFragment")
	class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
			return  dpd;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			et_start_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
			et_end_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		}
	}

	//创建时间选择对话框
	@SuppressLint("ValidFragment")
	class SetTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),this,hour,minute,true);
			return timePickerDialog;
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			et_start_time.setText(hourOfDay+":"+minute);
			et_end_time.setText(hourOfDay+":"+minute);
		}
	}

	public void setTime(View view){
		SetTimeDialog setTimeDialog = new SetTimeDialog();
		setTimeDialog.show(getFragmentManager(),"mytimePicker");
	}







/*22222222222222222222222222222222===日期显示处理结束===2222222222222222222222222222222222222222222222*/



}
