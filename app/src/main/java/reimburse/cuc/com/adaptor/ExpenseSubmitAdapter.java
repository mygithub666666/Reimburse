package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.reimburse.ActivityWaitSelected;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/26.
 */
public class ExpenseSubmitAdapter extends RecyclerView.Adapter<ExpenseSubmitAdapter.ViewHolder> {
    private CheckBox checkboxAll;
    private TextView tvExpenseTotal;
    private Context context;
    private List<Consumption> consumptions;

    public ExpenseSubmitAdapter() {
    }

    public ExpenseSubmitAdapter(Context context, List<Consumption> allConsumptions, TextView tvExpenseTotal, CheckBox checkboxAll) {
        this.context = context;
        this.consumptions = allConsumptions;
        this.tvExpenseTotal = tvExpenseTotal;
        this.checkboxAll = checkboxAll;
        showTotalMoney();
        //设置点击事件
        setListener();
        checkAll();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置得到对应的bean对象
                Consumption consu = consumptions.get(position);
                //2.设置取反状态
                consu.setIsSelected(!consu.isSelected());
                //3.刷新状态
                notifyItemChanged(position);
                //校验是否全选
                checkAll();
                //4.重新计算总价格
                showTotalMoney();

            }
        });
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                check_all_no_orno(isCheck);

                //3.计算价格
                showTotalMoney();
            }
        });
    }
    private void check_all_no_orno(boolean isCheck){
        if(consumptions != null && consumptions.size()>0){
            for (int i=0;i<consumptions.size();i++){
                Consumption con = consumptions.get(i);
                con.setIsSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }
    private void checkAll(){
        if(consumptions != null && consumptions.size() >0) {
            int number = 0;
            for (int i=0;i<consumptions.size();i++){
               Consumption consu = consumptions.get(i);
                if(!consu.isSelected()){
                   //非全选
                    checkboxAll.setChecked(false);
                }else {
                    number++;
                }
            }
            if(number == consumptions.size()){
                checkboxAll.setChecked(true);
            }
        }
    }
    private void showTotalMoney() {
        List<Consumption> consumptionsIsSelected = getConsumptionsIsSelected();
        BigDecimal totalMoney = new BigDecimal("0.0");
        for (Consumption consumption : consumptionsIsSelected){
            totalMoney = totalMoney.add(new BigDecimal(consumption.getMoney()));
        }
        Log.e("适配器的总金额:",totalMoney.toString());
        tvExpenseTotal.setText("总计：" +totalMoney);
    }

    public List<Consumption> getConsumptionsIsSelected() {
        List<Consumption> consumptionsIsSelected = new ArrayList<Consumption>();
        //BigDecimal totalMoney = new BigDecimal("0.0");
        if(consumptions!=null && consumptions.size()>0){
            for (int i=0;i<consumptions.size();i++){
              Consumption con = consumptions.get(i);
                if(con.isSelected()) {
                    Log.e("选择了",con.toString());
                    //totalMoney = totalMoney.add(new BigDecimal(con.getMoney()));
                    consumptionsIsSelected.add(con);
                }
            }
        }
        //Log.e("money========",totalMoney.toString());
        //return totalMoney.toString();
        return consumptionsIsSelected;
    }
    /*private String getTotalMoney() {
        BigDecimal totalMoney = new BigDecimal("0.0");
        if(consumptions!=null && consumptions.size()>0){
            for (int i=0;i<consumptions.size();i++){
              Consumption con = consumptions.get(i);
                if(con.isSelected()) {
                    Log.e("选择了",con.toString());
                    totalMoney = totalMoney.add(new BigDecimal(con.getMoney()));
                }
            }
        }
        Log.e("money========",totalMoney.toString());
        return totalMoney.toString();
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context,R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          Consumption consu = consumptions.get(position);
          holder.check.setChecked(consu.isSelected());
          holder.content.setText(consu.getContent());
          holder.date.setText(consu.getDate());
          holder.money.setText(consu.getMoney());
          holder.picurls.setText(consu.getPicUris());
          holder.picurls.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return consumptions.size();
    }

    public ExpenseSubmitAdapter(Context context, List<Consumption> consumptions) {
        this.context = context;
        this.consumptions = consumptions;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox check;
        TextView content;
        TextView date;
        TextView money;
        TextView picurls;
        public ViewHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.cb_expense_submit);
            content = (TextView) itemView.findViewById(R.id.tv_title_cart);
            date = (TextView) itemView.findViewById(R.id.tv_date_cart);
            money = (TextView) itemView.findViewById(R.id.tv_money_cart);
            picurls = (TextView) itemView.findViewById(R.id.tv_picuris_cart);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置item的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
