package com.example.monthdemo.uitls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 *
 * 1. 拍照
 * 2. 相册
 * 3. 裁切
 */
public class PhotosUtils {
    public static final int REQUEST_CODE_PAIZHAO = 1;
    public static final int REQUEST_CODE_ZHAOPIAN = 2;
    public static final int REQUEST_CODE_CAIQIE = 3;
    private static Uri mImageUri;
    public static String FILE_PROVIDER_AUTHORITY="com.example.monthdemo.fileprovider";

    /**
     * 拍照
     *
     * @param activity
     */
    public static void goCamera(Activity activity, File imageFile) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
        if(takePhotoIntent.resolveActivity(activity.getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            if(imageFile!=null){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    mImageUri = FileProvider.getUriForFile(activity,FILE_PROVIDER_AUTHORITY,imageFile);
                    takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takePhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mImageUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);//将用于输出的文件Uri传递给相机
                activity.startActivityForResult(takePhotoIntent, REQUEST_CODE_PAIZHAO);//打开相机
            }
        }
    }

    /**
     * 选择图片
     * @param activity
     */
    public static void selectPhoto(Activity activity){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        activity.startActivityForResult(intent, REQUEST_CODE_ZHAOPIAN);
    }

    /**
     * 裁剪
     * @param activity
     * @param uri
     * @param outputFile
     */
    public static void doCrop(Activity activity, Uri uri, File outputFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        FileProviderUtils.setIntentDataAndType( intent, "image/*", uri, true);


        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        //return-data为true时，直接返回bitmap，可能会很占内存，不建议，小米等个别机型会出异常！！！
        //所以适配小米等个别机型，裁切后的图片，不能直接使用data返回，应使用uri指向
        //裁切后保存的URI，不属于我们向外共享的，所以可以使用fill://类型的URI
        Uri outputUri = Uri.fromFile(outputFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        activity.startActivityForResult(intent, REQUEST_CODE_CAIQIE);
    }
}
