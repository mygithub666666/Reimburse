package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/8/23.
 */
public class ReimburseFormActivity extends Activity {

    private static final String TAG = ReimburseFormActivity.class.getSimpleName();
    private static final int REQUEST_SELECT_EXPENSE_CODE = 1000;
    private ListView listView;
    private EditText et_baoxiaoshiyou;
    private EditText et_baoxiaojine;
    private EditText et_baoxiaoproject;
    private Button tianjiaxiaofei_btn;
    private Button tijiaobaoxiao_btn;
    private PopupWindow popupWindow;
    private List<TongyiXiaofeiBean> tongyiXiaofeiBeanList;
    private StringBuilder stringBuilder = new StringBuilder();

    private ListView projectListView;
    private List<String> projectNameList;

    protected static  final int SHOW_ALL_PROJECT = 0001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SHOW_ALL_PROJECT) {

                projectListView.setAdapter(new ProjectListAdapter());
                et_baoxiaoproject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow == null) {
                            popupWindow = new PopupWindow(ReimburseFormActivity.this);
                            popupWindow.setWidth(et_baoxiaoproject.getWidth());
                            popupWindow.setHeight(200);


                            popupWindow.setContentView(projectListView);
                            popupWindow.setFocusable(true);

                        }
                        popupWindow.showAsDropDown(et_baoxiaoproject, 0, 0);
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reimburseform);
        listView = (ListView) findViewById(R.id.listview_form);
        et_baoxiaoshiyou = (EditText) findViewById(R.id.et_baoxiaoshiyou);
        et_baoxiaojine = (EditText) findViewById(R.id.et_baoxiaojine);
        et_baoxiaoproject = (EditText) findViewById(R.id.et_baoxiaoproject);
        tianjiaxiaofei_btn = (Button) findViewById(R.id.tianjiaxiaofei_btn);
        tijiaobaoxiao_btn = (Button) findViewById(R.id.tijiaobaoxiao_btn);
        projectListView = new ListView(this);
        projectListView.setBackgroundResource(R.color.white);
        tongyiXiaofeiBeanList = new ArrayList<TongyiXiaofeiBean>();

        getAllProject();

        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.得到数据
                String proName = projectNameList.get(position);
                et_baoxiaoproject.setText(proName);
                if(popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        et_baoxiaoproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow == null){
                    popupWindow = new PopupWindow(ReimburseFormActivity.this);
                    popupWindow.setWidth(et_baoxiaoproject.getWidth());
                    popupWindow.setHeight(200);


                    popupWindow.setContentView(projectListView);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(et_baoxiaoproject,0,0);
            }
        });
        tianjiaxiaofei_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReimburseFormActivity.this, DengdaiXuanzedeXiaofeiList.class);
                startActivityForResult(intent, REQUEST_SELECT_EXPENSE_CODE);
            }
        });
        tijiaobaoxiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToastInAnyThread("提交报销的用户ID: " + LauncherActivity.ANDROID_USER_ID);
                for (int i=0;i<tongyiXiaofeiBeanList.size();i++){
                    TongyiXiaofeiBean bean = tongyiXiaofeiBeanList.get(i);
                    //: 餐费 单位: 份 单价: 1 数量: 3 总价: 3 描述: 食堂吃饭===201710162313196652.png|201710162313196651.pngxiaofei采购费 采购商品: 优盘 单位: 个 单价: 8 数量: 6 总价: 48 日期:2017-10-16===实验室办公xiaofei交通费 出发地~目的地： 北京~广州 起止日期: 2017-10-16~2017-10-16 交通工具及座位类型: 高铁商务座 票价: 688 描述: 广州出差乘坐火车===201710161840395117.png

                    switch (bean.getXiaofei_type()){
                        case "采购费":

// 餐费 单位:份 单价:8 数量:1 总价:8 日期:2017-10-16 描述:招待高级专家用餐===201710161949263453.png|201710161949263426.pngxiaofei采购费 采购商品:优盘 单位:个 单价:8 数量:6 总价:48 日期:2017-10-16 描述:实验室办公===201710161541256000.png|201710161541256046.pngxiaofei交通费 出发地~目的地:北京~广州 起止日期:2017-10-16~2017-10-16 交通工具及座位类型:高铁商务座 票价:688 描述:广州出差乘坐火车===201710161840395117.png
                            //餐费 单位: 份 单价: 1 数量: 3 总价: 3 描述: 食堂吃饭===201710162313196652.png|201710162313196651.pngxiaofei采购费 采购商品: 优盘 单位: 个 单价: 8 数量: 6 总价: 48 日期:2017-10-16===实验室办公xiaofei交通费 出发地~目的地： 北京~广州 起止日期: 2017-10-16~2017-10-16 交通工具及座位类型: 高铁商务座 票价: 688 描述: 广州出差乘坐火车===201710161840395117.png

                            //"优盘=个=8=6=实验室办公=201710161541256000.png|201710161541256046.png"/
                            String infostr = bean.getOtherInfo();
                            String[] infoArr = infostr.split("=");
                            String xiaoFeiInfo = bean.getXiaofei_type()+" 采购商品:"+infoArr[0]+" 单位:"+infoArr[1]+" 单价:"+infoArr[2]
                                    +" 数量:"+infoArr[3]+" 总价:"+bean.getTotalMoney()+" 日期:"+bean.getRiqi()+" 描述:"+infoArr[4]+"==="+infoArr[5];
                            if(i == tongyiXiaofeiBeanList.size()-1) {
                                stringBuilder.append(xiaoFeiInfo);
                            }else {
                                stringBuilder.append(xiaoFeiInfo+"xiaofei");
                            }
                            //showToastInAnyThread(stringBuilder.toString());
                            break;
                        case "交通费":
                            //  "北京=广州=高铁商务座=广州出差乘坐火车=201710161840395117.png|201710161840395117.png",
                            String infostr1 = bean.getOtherInfo();
                            String[] infoArr1 = infostr1.split("=");
                            String xiaoFeiInfo1 = bean.getXiaofei_type()+" 出发地~目的地:"+infoArr1[0]+"~"+infoArr1[1]+" 起止日期:"
                                    +bean.getRiqi()+"~"+infoArr1[2]+" 交通工具及座位类型:"+infoArr1[3]+" 票价:"+bean.getTotalMoney()+
                                    " 描述:"+infoArr1[4]+"==="+infoArr1[5];
                            if(i == tongyiXiaofeiBeanList.size()-1) {
                                stringBuilder.append(xiaoFeiInfo1);
                            }else {
                                stringBuilder.append(xiaoFeiInfo1+"xiaofei");
                            }
                            //showToastInAnyThread(stringBuilder.toString());
                            break;

                        case "餐费":
                            /*intent2.putExtra("canfei_display_xiaofei_type",bean.getXiaofei_type());
                            intent2.putExtra("canfei_danwei",attrInfoArr[0]);
                            intent2.putExtra("canfei_danjia",attrInfoArr[2]);
                            intent2.putExtra("canfei_shuliang",attrInfoArr[1]);
                            intent2.putExtra("canfei_jine",bean.getTotalMoney());
                            intent2.putExtra(" canfei_riqi",bean.getRiqi());
                            intent2.putExtra("canfei_desc",attrInfoArr[3]);
                            intent2.putExtra("fapiaoUrls",attrInfoArr[4]);*/
                            String infostr2 = bean.getOtherInfo();
                            String[] infoArr2 = infostr2.split("=");
                            String xiaoFeiInfo2 = bean.getXiaofei_type()+" 单位:"+infoArr2[0]+" 单价:"+infoArr2[2]
                                    +" 数量:"+infoArr2[1]+" 总价:"+bean.getTotalMoney()+" 日期:"+bean.getRiqi()+
                                    " 描述:"+infoArr2[3]+"==="+infoArr2[4];
                            if(i == tongyiXiaofeiBeanList.size()-1) {
                                stringBuilder.append(xiaoFeiInfo2);
                            }else {
                                stringBuilder.append(xiaoFeiInfo2+"xiaofei");
                            }
                            //showToastInAnyThread(stringBuilder.toString());
                            break;
                    }

                }

                //showToastInAnyThread(stringBuilder.toString());
                Log.e("报销的附加信息", stringBuilder.toString());
                saveBaoXiao();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //RESPONSE_SELECT_EXPENSE_COMPLETED

        if(requestCode == REQUEST_SELECT_EXPENSE_CODE && resultCode == 1001){
            BigDecimal totalMoney = new BigDecimal("0.0");
            Bundle bundle = data.getExtras();
            Log.e("resultCode",String.valueOf(resultCode));
            tongyiXiaofeiBeanList = (List < TongyiXiaofeiBean >)bundle.getSerializable("tongyiXiaofeiBeanList");
            for (TongyiXiaofeiBean c :tongyiXiaofeiBeanList){
                Log.e("选择好的消费: ",c.getId()+":"+c.getXiaofei_type()+":"+c.getRiqi()+":"+c.getTotalMoney()+":"+c.getOtherInfo());
                totalMoney = totalMoney.add(new BigDecimal(c.getTotalMoney()));
            }
            et_baoxiaojine.setText(totalMoney.toString());
            Log.e("消费记录选择Activity","success return");
            Log.e("选择的消费条数: ",String.valueOf(tongyiXiaofeiBeanList.size()));
            if(tongyiXiaofeiBeanList != null && tongyiXiaofeiBeanList.size()>0) {

                listView.setAdapter(new TianjiaXiaofeiAdapter(tongyiXiaofeiBeanList));
            }
        }

    }


    class ProjectListAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
             return  projectNameList.size();
        }

        @Override
        public Object getItem(int position) {
             return  projectNameList.get(position);
        }

        @Override
        public long getItemId(int position) {
             return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProjectViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ProjectViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_projectname,null);
                viewHolder.projectName = (TextView) convertView.findViewById(R.id.tv_project_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ProjectViewHolder) convertView.getTag();

            }
            String  projectName = projectNameList.get(position);
            viewHolder.projectName.setText(projectName);
            return convertView;
        }
    }

    class TianjiaXiaofeiAdapter extends BaseAdapter{

        List < TongyiXiaofeiBean > data;
        public TianjiaXiaofeiAdapter(List < TongyiXiaofeiBean > data){
           this.data = data;
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_caigou,null);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.lv_item_caigou_type);
                viewHolder.tvDate = (TextView) convertView.findViewById(R.id.lv_item_caigou__date);
                viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.lv_item_caigou_count);
                viewHolder.tvOhterInfo = (TextView) convertView.findViewById(R.id.lv_item_caigou__date_picuris);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }
            TongyiXiaofeiBean consumption = data.get(position);
            viewHolder.tvType.setText(consumption.getXiaofei_type());
            viewHolder.tvDate.setText(consumption.getRiqi());
            viewHolder.tvMoney.setText(consumption.getTotalMoney());

            viewHolder.tvOhterInfo.setVisibility(View.INVISIBLE);
            viewHolder.tvOhterInfo.setText(consumption.getOtherInfo());
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvType;
        public TextView tvDate;
        public TextView tvMoney;
        public TextView tvOhterInfo;
    }

    static  class  ProjectViewHolder{
        public TextView projectName;
    }
    public void saveBaoXiao(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.BaoXiaoDan_SaveBaoXiaoDan;
                    // 2017-12-10 20:03:48
                    String commitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Log.e("报销单提交的时间戳: ",commitTime);
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型
                    //username=abc&password=123

                    String data = "baoxiaodan_shiyou="+ URLEncoder.encode(et_baoxiaoshiyou.getText().toString(), "utf-8")+
                           "&baoxiaodan_jine="+URLEncoder.encode(et_baoxiaojine.getText().toString(),"utf-8")
                           +"&baoxiaodan_project="+URLEncoder.encode(et_baoxiaoproject.getText().toString(),"utf-8")
                           +"&commitTime="+URLEncoder.encode(commitTime,"utf-8")
                           +"&xiaofeiids="+URLEncoder.encode(stringBuilder.toString(),"utf-8")
                           +"&user_id="+URLEncoder.encode(String.valueOf(LauncherActivity.ANDROID_USER_ID),"utf-8");

                    Log.e(TAG+",报销所提及的参数：",data);

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
                    showToastInAnyThread("请求失败");
                }

            };
        }.start();
    }


    /**
     * 得到所有的项目信息，展示成下拉框
     */
    public void getAllProject(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.GET_ALL_PROJECT_URL;
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
                        projectNameList = JSON.parseArray(result, String.class);

                        Message msg = Message.obtain();
                        msg.what = SHOW_ALL_PROJECT;
                        msg.obj = projectNameList;
                        handler.sendMessage(msg);
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
                    showToastInAnyThread("请求失败");
                }

            };
        }.start();
    }





    public void testPost(){
        new Thread() {
            public void run() {
                try {
                    // 1.指定提交数据的路径,post的方式不需要组拼任何的参数
                    String path = Constants.Get_ALL_Caigou_List;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //2.指定请求方式为post
                    conn.setRequestMethod("POST");
                    //3.设置http协议的请求头
                    //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置发送的数据为表单类型


                    //conn.setRequestProperty("Content-Length", String.valueOf(data.length()));//告诉服务器发送的数据的长度
                    //post的请求是把数据以流的方式写给了服务器
                    //指定请求的输出模式
                    //conn.setDoOutput(true);//运行当前的应用程序给服务器写数据
                    //conn.getOutputStream().write(data.getBytes());
                    //Log.e("post size", String.valueOf(data.getBytes().length));
                    int code = conn.getResponseCode();
                    if(code == 200){
                        InputStream is = conn.getInputStream();
                        final String result = StreamTools.readStream(is);
                        //caigouList = JSON.parseArray(result, Caigou.class);


                        Log.e("返回的JSON数据",result);
                        showToastInAnyThread(result);
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
                    showToastInAnyThread("请求失败");
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
}
