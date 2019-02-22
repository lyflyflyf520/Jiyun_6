package com.example.monthdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.monthdemo.R;
import com.example.monthdemo.view.DialogFromBottom;

import java.io.File;
import java.io.FileNotFoundException;

public class OwnerFragment extends Fragment implements View.OnClickListener {
    private final int PHOTO_REQUEST_CAMERA=99;
    private final  int PHOTO_REQUEST_GALLERY=111;
    private static Uri mCutTempFile;
    private final int PHOTO_REQUEST_CUT=121;
    private static File mCameraTempFile;
    private View view;
    private ImageView mUserImg;
    /**
     * input name
     */
    private EditText mUsername;
    /**
     * input password
     */
    private EditText mPassword;
    /**
     * 登录
     */
    private Button mLoginclick;
    private DialogFromBottom dialogFromBottom;
    private View dialogContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.owner_fragment, null);
        initView(inflate);

        dialogContent = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null, false);
        dialogFromBottom = new DialogFromBottom(getActivity());
        dialogFromBottom.setContentView(dialogContent);

        View camera = dialogContent.findViewById(R.id.open_from_camera);
        View photos = dialogContent.findViewById(R.id.open_album);

        camera.setOnClickListener(this);
        photos.setOnClickListener(this);
        return inflate;
    }

    public void selectImg(){
        dialogFromBottom.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    openCropActivity(getActivity(), data.getData());
                break;
            case  PHOTO_REQUEST_CAMERA:
                File cameraTempFile =  getCameraTempFile();
                if (cameraTempFile != null){
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName(),mCameraTempFile);

                    openCropActivity(getActivity(), photoURI);
                }

                break;
            case  PHOTO_REQUEST_CUT:
                mUserImg.setImageBitmap(getAfterCropBitmap(getActivity()));
                break;
        }
    }
    public static Bitmap getAfterCropBitmap(Activity activity){
        File cameraTempFile2= getCameraTempFile();
        if (cameraTempFile2 != null) {
            cameraTempFile2.delete();
        }

        Uri mCutTempFile= getCutTempFile();
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(mCutTempFile));
        } catch (FileNotFoundException e) {
            return null;
        }
        return bitmap;
    }
    public static File getCameraTempFile() {
        return mCameraTempFile;
    }

    public static Uri getCutTempFile() {
        return mCutTempFile;
    }

    public void uploadFile(){


    }

    public void login(){


    }


    private void initView(View inflate) {
        mUserImg = (ImageView) inflate.findViewById(R.id.user_img);
        mUsername = (EditText) inflate.findViewById(R.id.username);
        mPassword = (EditText) inflate.findViewById(R.id.password);
        mLoginclick = (Button) inflate.findViewById(R.id.loginclick);
        mLoginclick.setOnClickListener(this);
        mUserImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.open_album:
                openGallery(getActivity());
                break;
            case R.id.open_from_camera:
                openCamera(getActivity());
                break;
            case R.id.loginclick:
                login();
                break;
            case R.id.user_img:
                selectImg();
                break;
        }
    }

    /*
     * 剪切图片
     */
    public  void openCropActivity(Activity activity,Uri uri) {

        File file  = new File(Environment.getExternalStorageDirectory().getPath() + File.separator+ "Crop.jpg");
        mCutTempFile = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName(),file);


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutTempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
     * 从相机获取
     */
    public  void openCamera(Activity activity) {
        mCameraTempFile = new File(Environment.getExternalStorageDirectory()+File.separator+"xxx.png");
        Uri photoURI = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName(),mCameraTempFile);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /*
     * 从相册获取
     */
    public  void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


}
