package com.jiyun_z5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun_z5.bean.BannerItem;
import com.jiyun_z5.dao.BannerItemDao;
import com.jiyun_z5.dao.DaoSession;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.edittext_update)
    EditText updateEdit;

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

            case R.id.insert:
                insertData();
                break;
            case R.id.delete:
                deleteData();
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

        Random random = new Random();//指定种子数字
        int index = random.nextInt(999);

        Log.d(TAG, "insertData: index=" + index);

        DaoSession daoSession = JiyunApplication.getDaoSession();
        BannerItem bannerItem = new BannerItem();
        bannerItem.setItmeId((int) index);
        bannerItem.setImagePath(img_url);
        bannerItem.setDesc("this is description");
        bannerItem.setUrl("this is url  ");
        bannerItem.setTitle("hehe");
        daoSession.getBannerItemDao().insert(bannerItem);
    }

    public void deleteData() {
        DaoSession daoSession = JiyunApplication.getDaoSession();
        if (TextUtils.isEmpty(deleteEdit.getText().toString())) {
            daoSession.getBannerItemDao().deleteAll();
        } else {
            String id = deleteEdit.getText().toString().trim();

            Log.d(TAG, "deleteData: id=" + id);

            BannerItem bannerItem = queryDataById(id);
            daoSession.getBannerItemDao().delete(bannerItem);
        }


    }

    public void updateData() {

        String input = updateEdit.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "输入itmeid", Toast.LENGTH_SHORT).show();
        }


        DaoSession daoSession = JiyunApplication.getDaoSession();

        BannerItem bannerItem = queryDataById(input);

        bannerItem.setDesc("this is update description");

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

            Log.d(TAG, "queryData: " + bannerItem.getDesc());
            str += bannerItem.getItmeId() + ",";
        }

        resultTv.setText(str);

    }

    public BannerItem queryDataById(String id) {
        DaoSession daoSession = JiyunApplication.getDaoSession();

        BannerItem bannerItem = daoSession.getBannerItemDao().queryBuilder().where(BannerItemDao.Properties.ItmeId.eq(id)).build().unique();

        return bannerItem;

    }


}
