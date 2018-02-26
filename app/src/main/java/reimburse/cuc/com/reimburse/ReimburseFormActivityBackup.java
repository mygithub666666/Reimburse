package reimburse.cuc.com.reimburse;

import android.app.Activity;

/**
 * Created by hp1 on 2017/8/23.
 */
public class ReimburseFormActivityBackup extends Activity {

    /*private static final int REQUEST_SELECT_EXPENSE_CODE = 1000;
    private ListView listView;
    private EditText et_baoxiaoshiyou;
    private EditText et_baoxiaojine;
    private EditText et_baoxiaoproject;
    private Button expense_select_button;
    private Button reimburse_save;
    private BaoxiaodanDatabaseHelper baoxiaodanDatabaseHelper;
    private List<Consumption> consumptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reimburseform);
        listView = (ListView) findViewById(R.id.listview_form);
        et_baoxiaoshiyou = (EditText) findViewById(R.id.et_baoxiaoshiyou);
        et_baoxiaojine = (EditText) findViewById(R.id.et_baoxiaojine);
        et_baoxiaoproject = (EditText) findViewById(R.id.et_baoxiaoproject);
        expense_select_button = (Button) findViewById(R.id.expense_select_button);
        reimburse_save = (Button) findViewById(R.id.reimburse_save);


        baoxiaodanDatabaseHelper = new BaoxiaodanDatabaseHelper(ReimburseFormActivityBackup.this);
        consumptionList = new ArrayList<Consumption>();
        expense_select_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReimburseFormActivityBackup.this, ActivityWaitSelected.class);
                startActivityForResult(intent, REQUEST_SELECT_EXPENSE_CODE);
            }
        });
        reimburse_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baoxiao_shiyou = et_baoxiaoshiyou.getText().toString();
                Log.e("SHIYOU",baoxiao_shiyou);
                String baoxiao_jine = et_baoxiaojine.getText().toString();
                Log.e("baoxiao_jine",baoxiao_jine);
                String baoxiao_project = et_baoxiaoproject.getText().toString();
                Log.e("baoxiao_project",baoxiao_project);
                StringBuffer xiaofeijilu = new StringBuffer();
                if(consumptionList != null && consumptionList.size() >0) {
                    Log.e("-----------------",consumptionList.size()+"");
                    for (Consumption c : consumptionList){
                        System.out.println("----------------------------"+c.toString());
                    }
                    for (int i=0;i<consumptionList.size();i++){
                        if(i == consumptionList.size()-1) {
                             xiaofeijilu.append(consumptionList.get(i).toString());
                        }else {
                            xiaofeijilu.append(consumptionList.get(i).toString() + "---xiaofei---");

                        }
                    }
                }
                Baoxiaodan baoxiaodan = new Baoxiaodan(baoxiao_shiyou,baoxiao_jine,baoxiao_project,xiaofeijilu.toString());
                baoxiaodanDatabaseHelper.insertConsumption(baoxiaodan);
                startActivity(new Intent(ReimburseFormActivityBackup.this,BaoxiaodanList.class));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SELECT_EXPENSE_CODE && resultCode == 1001){
            BigDecimal totalMoney = new BigDecimal("0.0");
            Bundle bundle = data.getExtras();
            // consumptionList
            Log.e("resultCode",String.valueOf(resultCode));
            consumptionList = (List < Consumption >)bundle.getSerializable("consumptionList");
            for (Consumption c :consumptionList){
                Log.e("=======================",c.getContent()+c.getDate()+c.getMoney()+"picurls="+c.getPicUris());
                totalMoney = totalMoney.add(new BigDecimal(c.getMoney()));
            }
            et_baoxiaojine.setText(totalMoney.toString());
            Log.e("消费记录选择Activity","success return");
            Log.e("ConsumptionList.size()",String.valueOf(consumptionList.size()));
            if(consumptionList != null && consumptionList.size()>0) {

                ConsumptionListAdaptor conada = new ConsumptionListAdaptor(ReimburseFormActivityBackup.this,consumptionList);
                listView.setAdapter(conada);
            }
        }

    }*/
}
