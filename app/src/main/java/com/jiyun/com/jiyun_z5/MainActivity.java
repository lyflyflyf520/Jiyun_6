package com.jiyun.com.jiyun_z5;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiyun.com.jiyun_z5.adapter.XutilAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.jiyun.com.jiyun_z5.utils.Urls.PAKEAGE_NAME;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.xrecyclerview)
     XRecyclerView xRecyclerView;

    private static final String TAG = "MainActivity";
    private List<String> itemList = new ArrayList<>();
    private XutilAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        methodRequiresTwoPermission();



    }
    @AfterPermissionGranted(1010)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            initView();
            Log.d("","Already have permission, do the thing");
        } else {
            // Do not have permissions, request them now
            Log.d("","Do not have permissions, request them now");
            EasyPermissions.requestPermissions(this, "camera_and_send_sms", 1010, perms);
        }
    }

    public void initView(){
        adapter = new XutilAdapter(this);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        xRecyclerView.setLoadingMoreEnabled(false);

        adapter.setOnItemClickListener(new XutilAdapter.ItemOnClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                String className =   adapter.getKey(position);
                Intent intent = new Intent();
                String packageName = PAKEAGE_NAME;
                intent.setClassName(MainActivity.this,packageName+className);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean isFrist = true;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        if (isFrist){
            initView();
            isFrist = false;
        }
        Log.d("","Some permissions have been granted=" + requestCode);
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        Log.d("","Some permissions have been denied=" + requestCode);
    }
}
