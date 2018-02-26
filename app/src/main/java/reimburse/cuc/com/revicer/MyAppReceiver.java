package reimburse.cuc.com.revicer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import reimburse.cuc.com.reimburse.MyBaoxiaodanList;
import reimburse.cuc.com.reimburse.ReimburseFormDisplayActivity;

/**
 * Created by hp1 on 2017/10/20.
 */
public class MyAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
             Bundle bundle = intent.getExtras();
             String msg = bundle.getString(JPushInterface.EXTRA_ALERT);
             Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

         }else if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
             //打开自定义的Activity
             Bundle bundle = intent.getExtras();
             Intent i = new Intent(context, MyBaoxiaodanList.class);
             i.putExtras(bundle);
             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
             context.startActivity(i);
         }
    }
}
