package com.jiyun_z5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiyun_z5.bean.BannerItem;
import com.jiyun_z5.dao.BannerItemDao;
import com.jiyun_z5.dao.DaoSession;
import com.jiyun_z5.JiyunApplication;
import com.jiyun_z5.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jiyun_z5.utils.Constant.img_url;

public class DbDaoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.base_data)
    TextView textView;
    @BindView(R.id.insert)
    Button insertBtn;
    @BindView(R.id.delete)
    Button deleteBtn;
    @BindView(R.id.update)
    Button updateBtn;
    @BindView(R.id.query)
    Button queryBtn;
    @BindView(R.id.result)
    TextView resultTv;
    @BindView(R.id.edittext_delete)
    EditText deleteEdit;

    private static final String TAG = "DbDaoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_dao);

        ButterKnife.bind(this);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.edittext_delete:
//                deleteData();
//                break;
            case R.id.insert:
                insertData();
                break;
            case R.id.delete:
                new Thread(){
                    public void run(){
                        deleteData();
                    }
                }.start();

                break;
            case R.id.update:
                updateData();
                break;
            case R.id.query:
                queryData();
                break;
        }
    }

    public void insertData() {

//        Random random = new Random(1000);//指定种子数字
//        int index = random.nextInt(100);
//        double d = Math.random();

        DaoSession daoSession = JiyunApplication.getDaoSession();
        BannerItem bannerItem = new BannerItem();
//        bannerItem.setId(index);
        bannerItem.setImagePath(img_url);
        bannerItem.setDesc("this is description");
        bannerItem.setUrl("this is url  ");
        daoSession.getBannerItemDao().insert(bannerItem);
    }

    public void deleteData() {
        DaoSession daoSession = JiyunApplication.getDaoSession();
        if (TextUtils.isEmpty(deleteEdit.getText().toString())) {
            daoSession.getBannerItemDao().deleteAll();
        } else {
            String id = deleteEdit.getText().toString().trim();

            BannerItem bannerItem = queryDataById(id);
            daoSession.getBannerItemDao().delete(bannerItem);
        }


    }

    public void updateData() {
        DaoSession daoSession = JiyunApplication.getDaoSession();

        BannerItem bannerItem = new BannerItem();

//        bannerItem.setId(12432413);
//        bannerItem.setImagePath(img_url);
        bannerItem.setDesc("this is update description");
//        bannerItem.setUrl("this is url  ");


        daoSession.getBannerItemDao().update(bannerItem);
    }

    /**
     * loadAll()：查询所有记录
     * load(Long key)：根据主键查询一条记录
     * queryBuilder().list()：返回：List<User>列表
     * queryBuilder().where(UserDao.Properties.Name.eq("")).list()：返回：List<User>列表
     * queryRaw(String where,String selectionArg)：返回：List<User>列表
     */
    public void queryData() {
        DaoSession daoSession = JiyunApplication.getDaoSession();

        List<BannerItem> bannerItemList = daoSession.getBannerItemDao().loadAll();

        String str = "";
        for (BannerItem bannerItem : bannerItemList) {

            Log.d(TAG, "queryData: " + bannerItem.getId());
            str += bannerItem.getId() + ",";
        }

        resultTv.setText(str);

    }

    public BannerItem queryDataById(String id) {
        DaoSession daoSession = JiyunApplication.getDaoSession();

        BannerItem bannerItem = daoSession.getBannerItemDao().queryBuilder().where(BannerItemDao.Properties.Id.eq(id)).build().unique();

        return bannerItem;

    }


}
