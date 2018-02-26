package reimburse.cuc.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.edmodo.cropper.CropImageView;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2018/1/13.
 */
public class OpenCVTestActivity extends Activity {
    static final String TAG = OpenCVTestActivity.class.getSimpleName();
    private static final int PICTURE = 01;
    private static final int CAMERA = 02;
    private static final int PHOTOVIEW = 03;
    private static final int PHOTOVIEW_RESULT = 04;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    String cropPicPath = null;
    Uri cropImageUri;


    /**
     * TessBaseAPI初始化用到的第一个参数，是个目录。
     */

    File dir = new File(Environment.getExternalStorageDirectory(), "tessdata");
    String TESS_DATA_DIR;
    private static final String DATAPATH = Environment.getExternalStorageDirectory() + File.separator;
    /**
     * 在DATAPATH中新建这个目录，TessBaseAPI初始化要求必须有这个目录。
     */
    private static final String tessdata = DATAPATH + File.separator + "tessdata";

    File dataDir = new File(Environment.getExternalStorageDirectory(), "tessdata");


    /**
     * TessBaseAPI初始化测第二个参数，就是识别库的名字不要后缀名。
     */
    private static final String DEFAULT_LANGUAGE = "chi_sim";
    /**
     * assets中的文件名
     */
    private static final String DEFAULT_LANGUAGE_NAME = DEFAULT_LANGUAGE + ".traineddata";
    /**
     * 保存到SD卡中的完整文件名
     */
    private static final String LANGUAGE_PATH = tessdata + File.separator + DEFAULT_LANGUAGE_NAME;


    Uri picSavePath;
    String picName;
    @Bind(R.id.et_bankNumber)
    EditText etBankNumber;
    @Bind(R.id.btn_opencv_test)
    Button btnOpencvTest;

    @Bind(R.id.img_opencv_gray)
    ImageView imgOpencvGray;
    @Bind(R.id.btn_manual_crop)
    Button btnManualCrop;

    @Bind(R.id.cropImageView)
    CropImageView cropImageView;

    @Bind(R.id.btn_test)
    Button btnTest;
    private String selectedPicUrl;

    @Bind(R.id.img_opencv_test)
    ImageView imgOpencvTest;
    @Bind(R.id.btn_opencv_addImg)
    Button btnOpencvAddImg;

    TessBaseAPI tessBaseAPI = new TessBaseAPI();

    Boolean isInitTessTwo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opencv_test);
        ButterKnife.bind(this);
        initLoadOpenCV();

        cropImageView.setGuidelines(CropImageView.DEFAULT_GUIDELINES);//设置显示网格的时机，默认为on_touch

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                List<Mat> numberImgs = new ArrayList<>();
                Bitmap bitmap = cropImageView.getCroppedImage();//得到裁剪好的图片
                //convertBitMapToGray(bitmap);
                /**
                 * 1.二值化处理
                 */
                /**
                 * 1.1灰度化
                 */
                Mat src = new Mat();
                Utils.bitmapToMat(bitmap, src);

                //gray
                Mat gray = new Mat();

                //binary
                Mat binary = new Mat();


                Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
                //src.release();

                //Utils.matToBitmap(dest, bitmap);
                /**
                 * 1.2 二值化
                 */
                Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
                gray.release();

                /*Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,3));
                Imgproc.morphologyEx(binary, binary, Imgproc.MORPH_CLOSE, kernel);*/

                Mat cannyMat = new Mat();

                /*Imgproc.Canny(binary, cannyMat, 127, 154, 3, false);
                binary.release();*/
                Core.bitwise_not(binary,binary);
                List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
                Imgproc.findContours(binary, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
                //Rect[] textBoxes = new Rect[contours.size()];
                List<Rect> rectList = new ArrayList<Rect>();
                int index = 0;
                int minWidth = 1000000;
                Mat contourImg = new Mat(binary.size(), CvType.CV_8UC1);
                contourImg.setTo(new Scalar(255));

                for (int i=0;i<contours.size();i++){
                    MatOfPoint points = contours.get(i);
                    Rect bounding = Imgproc.boundingRect(points);
                    minWidth = Math.min(bounding.width,minWidth);
                    rectList.add(bounding);
                    Imgproc.drawContours(contourImg, contours, i, new Scalar(0),1);

                    Moments moments = Imgproc.moments(points, false);
                    double m00 = moments.get_m00();
                    double m10 = moments.get_m10();
                    double m01 = moments.get_m01();
                    double x0 = m10 / m00;
                    double y0 = m01 / m00;
                    double arcLength = Imgproc.arcLength(new MatOfPoint2f(points.toArray()),true);
                    double contourAra = Imgproc.contourArea(points);
                    Log.e("----------->", "第" + i + "个轮廓的周长与面积为: arcLength = " + arcLength + ", contourAra = " + contourAra);
                }
                minWidth = minWidth * 2;

                Collections.sort(rectList, new Comparator<Rect>() {
                    @Override
                    public int compare(Rect lhs, Rect rhs) {
                        return (lhs.x > rhs.x) ? 1 : 0;
                    }
                });

                for (int k=0;k < rectList.size();k++){
                    Log.e("------->","遍历第"+k+"个单字符,没有粘连字符");
                    Mat oneText = new Mat();
                    contourImg.submat(rectList.get(k)).copyTo(oneText);
                    numberImgs.add(oneText);
                    /*if(rectList.get(k).width > minWidth) {
                        Log.e("------->","遍历第"+k+"个粘连 字符,存在粘连字符");
                        Mat twoText = new Mat();
                        contourImg.submat(rectList.get(k)).copyTo(twoText);
                        int xpos = getSplitLinePos(binary.submat(rectList.get(k)));
                        numberImgs.add(twoText.submat(new Rect(0, 0, xpos-1, rectList.get(k).height)));
                        numberImgs.add(twoText.submat(new Rect(xpos + 1, 0, rectList.get(k).width - xpos - 1, rectList.get(k).height)));

                    }else {
                        Log.e("------->","遍历第"+k+"个单字符,没有粘连字符");
                        Mat oneText = new Mat();
                        contourImg.submat(rectList.get(k)).copyTo(oneText);
                        numberImgs.add(oneText);
                    }*/

                }
                for (int j =0;j<numberImgs.size();j++){
                    Mat oneTextMat = numberImgs.get(j);
                    Bitmap bitmap_save = Bitmap.createBitmap(oneTextMat.cols(),oneTextMat.rows(), Bitmap.Config.ARGB_8888);
                    //Imgproc.cvtColor(oneTextMat, oneTextMat, Imgproc.COLOR_BGR2RGBA);
                    Utils.matToBitmap(oneTextMat, bitmap_save);
                    saveDebugImage(bitmap_save);
                }
                Utils.matToBitmap(contourImg, bitmap);
                src.release();
                binary.release();
                cannyMat.release();
                contourImg.release();

                imgOpencvTest.setImageBitmap(bitmap);
            }
        });

        btnOpencvAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"图库", "相机"};
                new AlertDialog.Builder(OpenCVTestActivity.this).setTitle("选择来源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    /**
                                     * 图库选择
                                     */
                                    case 0:

                                        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(picture, PICTURE);
                                        break;

                                    /**
                                     * 相机
                                     */
                                    case 1:

                                        /**
                                         * 正常调用系统拍照的路径
                                         * /storage/emulated/0/DCIM/Camera/1515926968405.jpg
                                         */
                                        Log.e(TAG, "SD卡可用");
                                        File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
                                        dir.mkdirs();
                                        /*File dir = new File(Environment.getExternalStorageDirectory(),"DCIM");
                                        dir.mkdirs();*/
                                        Log.e(TAG, "消费照片的存储路径：" + dir.toString());
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                                        picName = dir.getAbsolutePath() + File.separator + sdf.format(new Date()) + new Random().nextInt(100) + ".jpg";
                                        Log.e(TAG, "照片的名称：" + picName);
                                        File file = new File(picName);
                                        picSavePath = Uri.fromFile(file);
                                        Log.e(TAG, "picSavePath = " + picSavePath);
                                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                        camera.putExtra(MediaStore.EXTRA_OUTPUT, picSavePath);

                                        startActivityForResult(camera, CAMERA);
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        btnManualCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastInAnyThread("操作图片的uri = " + picSavePath);

                /**
                 * 成功的操作，别修改
                 */
                File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
                Log.e(TAG, "剪裁后照片的存储文件夹：" + dir.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String cut_pic_path = dir.getAbsolutePath() + File.separator + sdf.format(new Date()) + new Random().nextInt(100) + ".jpg";
                File file = new File(cut_pic_path);
                cropImageUri = Uri.fromFile(file);
                Log.e(TAG, "剪裁后图片的保存路径: " + cut_pic_path);
                //crop(cropImageUri);

                startPhotoZoom(picSavePath);

                /*File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
                Log.e(TAG, "剪裁后照片的存储文件夹：" + dir.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String cut_pic_path = dir.getAbsolutePath() + File.separator + sdf.format(new Date()) + new Random().nextInt(100) + ".jpg";
                File file = new File(cut_pic_path);
                Uri  mycropImageUri = Uri.fromFile(file);*/


            }
        });


        btnOpencvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"二值化与字符分割01","灰度化", "ocr识别", "取反", "相减", "相加", "Mat对象的使用", "获取子图",
                        "sobel-X方向梯度处理", "sobel-Y方向梯度处理", "sobel-整体图像梯度处理",
                        "阈值二值化", "canny_边缘提取", "图像模糊-1",
                        "轮廓发现", "寻找银行卡区域"};
                new AlertDialog.Builder(OpenCVTestActivity.this).setTitle("选择来源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:

                                        /*Bitmap bitmap = BitmapFactory.decodeFile(selectedPicUrl, null);
                                        convertToGray(bitmap);*/
                                        break;
                                    case 1:
                                        //得到Bitmap对象
                                        Bitmap bitmap_1 = BitmapFactory.decodeFile(selectedPicUrl, null);
                                        Mat src = new Mat();
                                        Mat dst = new Mat();
                                        Utils.bitmapToMat(bitmap_1, src);
                                        // 灰度化
                                        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGRA2GRAY);
                                        //二值化
                                        Imgproc.threshold(src, dst, 127, 254, Imgproc.THRESH_BINARY);
                                        Utils.matToBitmap(dst, bitmap_1);
                                        //ocrTessTwo(bitmap_1);
                                        tessBaseAPI.setImage(bitmap_1);
                                        String text = tessBaseAPI.getUTF8Text();
                                        Log.i(TAG, "识别出的文字 text = " + text);

                                        //显示识别结果
                                        etBankNumber.setText(text);
                                        tessBaseAPI.end();

                                        src.release();
                                        dst.release();

                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

    }

    private int getSplitLinePos(Mat mtexts) {
        int hx = mtexts.cols() / 2;
        int width = mtexts.cols();
        int height = mtexts.rows();
        byte[] data = new byte[width*height];
        mtexts.get(0, 0, data);
        int startx = hx - 10;
        int endx = hx + 10;
        int linepos = hx;
        int min = 1000000;
        int c = 0;
        for(int col=startx; col<=endx; col++) {
            int total = 0;
            for(int row=0; row<height; row++) {
                c = data[row*width+col]&0xff;
                if(c == 0) {
                    total++;
                }
            }
            if(total < linepos) {
                linepos = total;
                linepos = col;
            }
        }
        return linepos;
    }

    /**
     * 剪裁图片
     *
     * @param uri
     * @return
     */
    private Uri crop(Uri uri) {
        // 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 270);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        return uri;
    }

    /**
     * 将Uri转成Bitmap
     *
     * @param uri
     * @return
     */
    public Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private void initLoadOpenCV() {
        boolean success = OpenCVLoader.initDebug();
        if (success) {
            Log.e(TAG, "OpenCV4Android is successfuly loaded!");
        } else {
            showToastInAnyThread("OpenCV4Android加载失败!");
        }
    }

    //将Bitmap保存到本地的操作

    /**
     * 数据的存储。（5种）
     * Bimap:内存层面的图片对象。
     * <p/>
     * 存储--->内存：
     * BitmapFactory.decodeFile(String filePath);
     * BitmapFactory.decodeStream(InputStream is);
     * 内存--->存储：
     * bitmap.compress(Bitmap.CompressFormat.PNG,100,OutputStream os);
     */
    private String saveImage(Bitmap bitmap) {
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");

        } else {//手机内部存储
            //路径：data/data/包名/files
            filesDir = this.getFilesDir();

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String cropName = filesDir.getAbsolutePath() + File.separator + sdf.format(new Date()) + new Random().nextInt(10) + "_crop.jpg";
        FileOutputStream fos = null;
        try {
            File file = new File(cropName);
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return cropName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cropName;
    }


    private void convertToGray(Bitmap bitmap) {
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);

        Mat dest = new Mat();

        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(src, dest, 127, 254, Imgproc.THRESH_BINARY);
        src.release();

        Utils.matToBitmap(dest, bitmap);
        dest.release();
        imgOpencvGray.setImageBitmap(bitmap);

    }

    private Bitmap convertBitMapToGray(Bitmap bitmap) {
        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);

        Mat dest = new Mat();

        Imgproc.cvtColor(src, dest, Imgproc.COLOR_BGR2GRAY);
        src.release();

        Utils.matToBitmap(dest, bitmap);
        dest.release();
        return bitmap;
        //imgOpencvGray.setImageBitmap(bitmap);

    }


    private void ocrTessTwo(Bitmap bitmap) {


        //Bitmap destBitmap = convertBitMapToGray(bitmap);
        //Log.e(TAG, "OCR识别时，样本数据集的位置 ：TESS_DATA_DIR = " + TESS_DATA_DIR);
        tessBaseAPI.setImage(bitmap);
        String text = tessBaseAPI.getUTF8Text();
        Log.i(TAG, "识别出的文字 text = " + text);

        etBankNumber.setText(text);

        tessBaseAPI.end();


    }


    /**
     * 将assets中的识别库复制到SD卡中
     *
     * @param path 要存放在SD卡中的 完整的文件名。这里是"/storage/emulated/0/tessdata/chi_sim.traineddata"
     * @param name assets中的文件名 这里是 "chi_sim.traineddata"
     */
    public void copyToSD(String path, String name) {
        Log.i(TAG, "copyToSD: " + path);
        Log.i(TAG, "copyToSD: " + name);

        //如果存在就删掉
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        if (!f.exists()) {
            File p = new File(f.getParent());
            if (!p.exists()) {
                p.mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = this.getAssets().open(name);
            File file = new File(path);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 270);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //图片路径
            //urlpath = FileUtilcll.saveFile(PhotoShoot.this, "temphead.jpg", photo);
            //System.out.println("----------路径----------" + urlpath);
            imgOpencvGray.setImageBitmap(photo);
        }
    }


    private void saveDebugImage(Bitmap bitmap) {
        File filedir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
        String name = String.valueOf(System.currentTimeMillis()) + "_ocr.jpg";
        File tempFile = new File(filedir.getAbsoluteFile() + File.separator, name);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        } catch (IOException ioe) {
            Log.e("DEBUG-ERR", ioe.getMessage());
        } finally {
            try {
                output.flush();
                output.close();
            } catch (IOException e) {
                Log.e("DEBUG-INFO", e.getMessage());
            }
        }
    }


    /**
     * 显示图片防止OOM
     *
     * @return
     */
    private void displaySelectedImage(String selectedURL) {

        Log.e(TAG, "返回的图片的路径: " + selectedURL);
        Uri uri = Uri.parse(selectedURL);
        Log.e(TAG, "转换后的Uri : " + uri.toString());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri.getPath(), options);
        int w = options.outWidth;
        int h = options.outHeight;
        int inSample = 1;
        if (w > 1000 || h > 1000) {
            while (Math.max(w / inSample, h / inSample) > 1000) {
                inSample *= 2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSample;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
        imgOpencvTest.setImageBitmap(bitmap);
    }


    private File generatePicFileAndPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "SD卡可用");
            File dir = new File(Environment.getExternalStorageDirectory(), "reim_cost_imgs");
            dir.mkdirs();
            Log.e(TAG, "消费照片的存储路径：" + dir.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String picName = dir.getAbsolutePath() + File.separator + sdf.format(new Date()) + new Random().nextInt(100) + ".jpg";
            Log.e(TAG, "照片的名称：" + picName);
            File file = new File(picName);
            picSavePath = Uri.fromFile(file);
            Log.e(TAG, "picSavePath = " + picSavePath);
            return file;
        } else {
            Log.e(TAG, "SD卡不可用!");
            return null;
        }

    }


    private void showToastInAnyThread(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//运行在主线程,内部做了处理
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });
    }




    /**
     * 图片选择相关
     */
    /**
     * 处理相册相关
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && data == null) {//相机

            showToastInAnyThread("拍照返回后, picName = " + picName);
            Intent intent = new Intent(OpenCVTestActivity.this, ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url", picName);
            startActivityForResult(intent, PHOTOVIEW);
            //displaySelectedImage();
            //displaySelectedImage();
            /*Bitmap bitmap = BitmapFactory.decodeFile(picName, null);
            imgOpencvTest.setImageBitmap(bitmap);*/
            /*showToastInAnyThread("拍照返回的保存路径： "+picSavePath.toString());

            String pathResult = getPath(picSavePath);
            Log.e("根据uri转换得到的绝对路径: ", pathResult);
            Bitmap bitmap = BitmapFactory.decodeFile(pathResult);
            imgOpencvTest.setImageBitmap(bitmap);*/


            //获取intent中的图片对象
            /*Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            //img_test.setImageBitmap(bitmap);

            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            String photoPath = getPath(uri);


            Log.e("从相机返回的图片的绝对路径: ", photoPath);*/
            //imgOpencvTest.setImageBitmap(bitmap);
            //Intent intent = new Intent(OpenCVTestActivity.this, ShowInvoiceLargePicTestActivity.class);
            //intent.putExtra("invoice_pic_url", photoPath);
            //startActivityForResult(intent, PHOTOVIEW);
            /**
             * 从相机返回的图片的绝对路径:: /storage/emulated/0/DCIM/Camera/1514598888572.jpg
             */


        } else if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && data != null) {//图库

            //图库
            Uri selectedImage = data.getData();
            picSavePath = selectedImage;
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,
            // 所以要保证无论是哪个系统版本都能正确获取到图片资源的话就需要针对各种情况进行一个处理了
            //这里返回的uri情况就有点多了
            //在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....
            // 在4.4.2返回的是content://com.android.providers.media.documents/document/image

            String pathResult = getPath(selectedImage);
            Log.e("从相册返回的绝对路径: ", pathResult);
            /**
             * 从相册返回的绝对路径:: /storage/emulated/0/DCIM/Camera/1514599043591.jpg
             * 从相册返回的绝对路径:: /storage/emulated/0/lagou/image/https:www.lgstatic.comiimageM007472CgpEMlo4uOSAF3PFAALGeI1RKvM745.JPG
             */
            //存储--->内存
            Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
            //img_test.setImageBitmap(decodeFile);
            Intent intent = new Intent(OpenCVTestActivity.this, ShowInvoiceLargePicTestActivity.class);
            intent.putExtra("invoice_pic_url", pathResult);
            startActivityForResult(intent, PHOTOVIEW);


        } else if (requestCode == PHOTOVIEW && resultCode == PHOTOVIEW_RESULT && data != null) {

            /**
             *    success
             * ================================
             */
            /*String resultPath = data.getStringExtra("Photo返回的filePath");
            Log.e("Photo返回的filePath", resultPath);
            selectedPicUrl = resultPath;
            Bitmap imgBitMap = BitmapFactory.decodeFile(resultPath);
            Log.e(TAG, "选择的图片的绝对路径： " + resultPath);
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), imgBitMap, null, null));
            Log.e(TAG, "图片文件的Uri = " + uri.toString());
            picSavePath = uri;


            Glide.with(OpenCVTestActivity.this)
                    .load(resultPath)
                    .priority(Priority.HIGH)
                    .into(imgOpencvTest);*/

            /**
             *    success
             * ================================
             */



            String resultPath = data.getStringExtra("Photo返回的filePath");
            Log.e("Photo返回的filePath", resultPath);
            selectedPicUrl = resultPath;
            Bitmap imgBitMap = BitmapFactory.decodeFile(resultPath);
            cropImageView.setImageBitmap(imgBitMap);
            //imgBitMap.recycle();







            //displaySelectedImage(selectedPicUrl);

           /* Bitmap imgBitMap = BitmapFactory.decodeFile(resultPath);
            Log.e(TAG, "选择的图片的绝对路径： " + resultPath);
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), imgBitMap, null, null));
            Log.e(TAG, "图片文件的Uri = " + uri.toString());
            picSavePath = uri;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream, null, options);
                imgOpencvTest.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } else if (requestCode == CUT_PHOTO_REQUEST_CODE && data != null) {
            showToastInAnyThread("裁剪返回!" + "图片Uri = " + cropImageUri);

            Log.e(TAG, resultCode + " : " + resultCode);
            Log.e(TAG, "====================================================================");
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            Log.e(TAG, "剪裁返回" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));

            //Uri uri = data.getData();

            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                saveImage(photo);
                Log.e(TAG, "剪裁返回,裁剪的图片保存完毕." + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                Log.e(TAG, "剪裁返回,裁剪的图片保存完毕." + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                Log.e(TAG, "剪裁返回,裁剪的图片保存完毕." + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                Log.e(TAG, "剪裁返回,裁剪的图片保存完毕." + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));

                Log.e(TAG, "cropImageUri = " + cropImageUri);
                //cropImageUri
                //Bitmap bitmap = BitmapFactory.decodeFile();
                //convertBitMapToGray(photo);

                Glide.with(OpenCVTestActivity.this)
                        .load(cropImageUri)
                        .priority(Priority.HIGH)
                        .into(imgOpencvGray);
                ocrTessTwo(photo);
                photo.recycle();
            }


        }
    }


    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(OpenCVTestActivity.this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(OpenCVTestActivity.this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = OpenCVTestActivity.this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(OpenCVTestActivity.this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
