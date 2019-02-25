package com.example.monthdemo.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.example.monthdemo.R;
import com.example.monthdemo.bean.MsgEvent;
import com.example.monthdemo.uitls.FileProviderUtils;
import com.example.monthdemo.uitls.GlideApp;
import com.example.monthdemo.uitls.PhotosUtils;
import com.example.monthdemo.view.DialogFromBottom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private static final String TAG = "OwnerFragment";
    private String uploadUrl="http://yun918.cn/study/public/file_upload.php";
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
//                    mUserImg.setImageBitmap(bitmap);

                    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                            .skipMemoryCache(true);//不做内存缓存


                    GlideApp.with(this)
                            .load(bitmap)
                            .centerCrop()
                            .apply(mRequestOptions)
                            .placeholder(R.drawable.ic_launcher_background)//加载中显示的图片
                            .error(R.drawable.ic_launcher_foreground)// 错误后显示的图片
                            .into(mUserImg);
                    // file path
                    // okhttp
                    uploadUserIcon(outputFile);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
        }
    }



    public void uploadUserIcon(final File file){

        // file --> RequestBody-- MultiPartBody
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key","monthdemo")
                .addFormDataPart("file",file.getName(),requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request =new Request.Builder()
                .url(uploadUrl)
                .post(multipartBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: e="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                {"code":200,"res":"上传文件成功","data":{"url":"http:\/\/yun918.cn\/study\/public\/uploadfiles\/monthdemo\/tupian_out.jpg"}}

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    jsonObject = jsonObject.getJSONObject("data");
                    String result = jsonObject.optString("url");

                    // 把图片的服务器url 发送给homeFragment
//                    MsgEvent msgEvent = new MsgEvent();
//                    msgEvent.imgUrl = result;

                    Intent intent = new Intent("com.monthdemo.recevier.imgurl");
                    intent.putExtra("imgUrl",file.getAbsolutePath());

                    getActivity().sendBroadcast(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
                dialogFromBottom.dismiss();
                break;
            case R.id.open_from_camera:
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "xxx.png");
                PhotosUtils.goCamera(getActivity(),file);
                dialogFromBottom.dismiss();
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
