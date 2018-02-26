package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.BitmapUtils;

/**
 * Created by hp1 on 2017/10/10.
 */
public class MyCaigouGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> bitmaps;
    private LayoutInflater layoutInflater;
    public MyCaigouGridViewAdapter(){

    }

    public MyCaigouGridViewAdapter(Context context,List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.gridview_fapiao_img,null);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.gridview_fapiao_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.iv.setImageBitmap(BitmapUtils.zoom(bitmaps.get(position),viewHolder.iv.getWidth(),viewHolder.iv.getHeight()));
        return convertView;
    }

    public static class ViewHolder{

        public ImageView iv;

    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
