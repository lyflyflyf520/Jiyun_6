package com.example.monthdemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.monthdemo.R;
import com.example.monthdemo.uitls.FileProviderUtils;
import com.example.monthdemo.uitls.PhotosUtils;
import com.example.monthdemo.view.DialogFromBottom;

import java.io.File;

public class OwnerFragment extends Fragment implements View.OnClickListener {
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

    public void selectImg() {
        dialogFromBottom.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri filtUri;
        File outputFile = new File("/mnt/sdcard/tupian_out.jpg");//裁切后输出的图片
        switch (requestCode) {
            case PhotosUtils.REQUEST_CODE_PAIZHAO:
                //拍照完成，进行图片裁切
                File file = new File("/mnt/sdcard/tupian.jpg");
                filtUri = FileProviderUtils.uriFromFile(getActivity(), file);
                PhotosUtils.doCrop(getActivity(), filtUri, outputFile);
                break;
            case PhotosUtils.REQUEST_CODE_ZHAOPIAN:
                //相册选择图片完毕，进行图片裁切
                if (data == null ||  data.getData()==null) {
                    return;
                }
                filtUri = data.getData();
                PhotosUtils.doCrop(getActivity(), filtUri, outputFile);
                break;
            case PhotosUtils.REQUEST_CODE_CAIQIE:
                //图片裁切完成，显示裁切后的图片
                try {
                    Uri uri = Uri.fromFile(outputFile);
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                    mUserImg.setImageBitmap(bitmap);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
        }
    }



    public void uploadFile() {


    }

    public void login() {


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
                PhotosUtils.selectPhoto(getActivity());
                break;
            case R.id.open_from_camera:
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "xxx.png");
                PhotosUtils.goCamera(getActivity(),file);
                break;
            case R.id.loginclick:
                login();
                break;
            case R.id.user_img:
                selectImg();
                break;
        }
    }




}
