package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by hp1 on 2017/8/15.
 */
public class ListActivity extends Activity {

    private ImageView list_img;
    private Button list_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        list_img = (ImageView) findViewById(R.id.list_img);
        list_img.setImageURI(Uri.fromFile(new File("/sdcard/myphoto.jpg")));
        list_btn = (Button) findViewById(R.id.list_btn_photo);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }


    public void open() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File("/sdcard/myphoto.jpg");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        list_img.setImageBitmap(bp);
    }


}
