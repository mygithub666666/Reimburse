package reimburse.cuc.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.bean.Employee;
import reimburse.cuc.com.bean.Pro_Budget;
import reimburse.cuc.com.bean.Project;
import reimburse.cuc.com.bean.TongyiXiaofeiBean;
import reimburse.cuc.com.bean.User;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/4/4.
 */
public class ProjectDisplayActivity extends Activity {
    private static final int REQUEST_SEARCH_REIM_USER = 01;
    private static final int REIM_USER_ADD_COMPLETED = 02;
    public static final int RESPONSE_AND_REIM_USER_SUCCESS = 03;
    public static final int RESPONSE_ADD_REIM_USER_SUCCESS = 06;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_p_name)
    TextView tvPName;
    @Bind(R.id.et_p_name)
    EditText etPName;
    @Bind(R.id.tv_p_type)
    TextView tvPType;
    @Bind(R.id.tv_p_leader)
    TextView tvPLeader;
    @Bind(R.id.et_p_leader)
    EditText etPLeader;
    @Bind(R.id.tv_p_startdate)
    TextView tvPStartdate;
    @Bind(R.id.et_p_startdate)
    EditText etPStartdate;
    @Bind(R.id.tv_p_enddate)
    TextView tvPEnddate;
    @Bind(R.id.et_p_enddate)
    EditText etPEnddate;
    @Bind(R.id.tv_p_totalmoney)
    TextView tvPTotalmoney;
    @Bind(R.id.et_p_totalmoney)
    EditText etPTotalmoney;
    @Bind(R.id.tv_p_finished_money)
    TextView tvPFinishedMoney;
    @Bind(R.id.et_p_finished_money)
    EditText etPFinishedMoney;
    @Bind(R.id.addReimUserBtn)
    Button addReimUserBtn;
    @Bind(R.id.et_p_type)
    EditText etPType;

    private ScrollView scrollView;

    Employee employee = null;
    @Bind(R.id.list_view_pro_budget)
    ListView listViewProBudget;
    @Bind(R.id.list_view_pro_reim_user)
    ListView listViewProReimUser;
    List<User> userList = new ArrayList<>();
    UserListAdapter userListAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_display);
        scrollView = (ScrollView) findViewById(R.id.outer_scrollview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        /**
         *  Log.e("项目信息列表的点击事件", ", projectJSONStr:" + projectJSONStr);
         intent.putExtra("projectJSONStr", projectJSONStr);
         */

        final String projectJSONStr = intent.getStringExtra("projectJSONStr");
        // JSON串转用户组对象
        Project project = JSON.parseObject(projectJSONStr, Project.class);

        List<Pro_Budget> pro_budgets = project.getPro_Budgets();
        userList = project.getUsers();

        etPName.setText(project.getP_name());
        etPType.setText(project.getP_type());
        etPLeader.setText(project.getP_leader());
        etPStartdate.setText(project.getP_startdate());
        etPEnddate.setText(project.getP_enddate());
        etPTotalmoney.setText(project.getP_totalmoney());
        etPFinishedMoney.setText(project.getP_finished_money());
        listViewProBudget.setAdapter(new PrpBudgetListAdapter(pro_budgets));

        userListAdapter = new UserListAdapter(userList);
        listViewProReimUser.setAdapter(userListAdapter);

        listViewProBudget.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        listViewProReimUser.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        //listViewProBudget


        addReimUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* DailyReim dailyReim = dailyReimList.get(position);
                String dailyReimJSONStr = JSON.toJSONString(dailyReim);*/
                Intent intent = new Intent(ProjectDisplayActivity.this, SearchReimUserActivity.class);
                // 用户组对象转JSON串
                //String jsonString = JSON.toJSONString(cost);
                Log.e("跳转到添加报销用户界面", ", projectJSONStr:" + projectJSONStr);
                intent.putExtra("projectJSONStr", projectJSONStr);
                startActivityForResult(intent, REQUEST_SEARCH_REIM_USER);
            }
        });


    }




    class PrpBudgetListAdapter extends BaseAdapter {

        List<Pro_Budget> budgetList;

        PrpBudgetListAdapter(List<Pro_Budget> budgetList) {
            this.budgetList = budgetList;
        }

        @Override
        public int getCount() {
            return budgetList.size();
        }

        @Override
        public Object getItem(int position) {
            return budgetList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_pro_budget, null);
                viewHolder.tv_pro_budget_name = (TextView) convertView.findViewById(R.id.lv_item_pro_budget_name);
                viewHolder.tv_pro_budget_percentage = (TextView) convertView.findViewById(R.id.lv_item_pro_budget_percentage);
                viewHolder.tv_pro_budget_amount = (TextView) convertView.findViewById(R.id.lv_item_pro_budget_amount);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Pro_Budget pro_budget = budgetList.get(position);
            viewHolder.tv_pro_budget_name.setText(pro_budget.getPro_budget_name());
            viewHolder.tv_pro_budget_percentage.setText(pro_budget.getPro_budget_percentage());
            viewHolder.tv_pro_budget_amount.setText(pro_budget.getPro_budget_amount());
            return convertView;
        }
    }

    /**
     * `pro_budget_name` varchar(60) NOT NULL DEFAULT '',
     * `pro_budget_percentage` varchar(30) NOT NULL DEFAULT '',
     * `pro_budget_amount
     */
    static class ViewHolder {
        public TextView tv_pro_budget_name;
        public TextView tv_pro_budget_percentage;
        public TextView tv_pro_budget_amount;
    }

    class UserListAdapter extends BaseAdapter {

        List<User> userList;

        UserListAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UserViewHolder viewHolder;
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                viewHolder = new UserViewHolder();
                convertView = layoutInflater.inflate(R.layout.lv_item_pro_reim_user, null);
                viewHolder.tv_pro_reim_user_name = (TextView) convertView.findViewById(R.id.lv_item_pro_reim_user_name);
                viewHolder.tv_pro_reim_mobile_phone_number = (TextView) convertView.findViewById(R.id.lv_item_pro_reim_mobile_phone_number);
                viewHolder.tv_pro_reim_user_job_number = (TextView) convertView.findViewById(R.id.lv_item_pro_reim_user_job_number);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (UserViewHolder) convertView.getTag();

            }
            User user = userList.get(position);
            viewHolder.tv_pro_reim_user_name.setText(user.getUser_name());
            viewHolder.tv_pro_reim_mobile_phone_number.setText(user.getMobile_phone_number());
            viewHolder.tv_pro_reim_user_job_number.setText(user.getReim_user_job_number());
            return convertView;
        }
    }

    /**
     * `pro_budget_name` varchar(60) NOT NULL DEFAULT '',
     * `pro_budget_percentage` varchar(30) NOT NULL DEFAULT '',
     * `pro_budget_amount
     */
    static class UserViewHolder {
        public TextView tv_pro_reim_user_name;
        public TextView tv_pro_reim_mobile_phone_number;
        public TextView tv_pro_reim_user_job_number;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SEARCH_REIM_USER && resultCode == RESPONSE_ADD_REIM_USER_SUCCESS){
            //Bundle bundle = data.getExtras();
            String new_user_json = data.getStringExtra("new_user_json");
            Log.e("添加报销人员成功返回的结果码: ", String.valueOf(resultCode));
            User newUser = JSON.parseObject(new_user_json,User.class);
            Log.e("新添加的报销用户: ",newUser.toString());
            if(newUser != null) {
                userList.add(newUser);
                //listViewProReimUser.setAdapter(new UserListAdapter(userList));
                userListAdapter.notifyDataSetChanged();
            }
        }
    }

}
