package com.example.monthdemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.monthdemo.R;
import com.example.monthdemo.adapter.RecyclerViewAdapter;
import com.example.monthdemo.bean.WarBean;
import com.example.monthdemo.uitls.DbUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的列表
 */
public class MallFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private List<WarBean> warBeanList = new ArrayList<>();

    private boolean isCreate;
    private boolean isVisible; //是否显示

    private static final String TAG = "MallFragment";
    private Button selectAll;
    private Button editBtn;
    /**
     * 删除
     */
    private Button mDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.mall_fragment, null);

        isCreate = true;

        initView(inflate);
        initData();
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isCreate && isVisible) {
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisible = isVisibleToUser;

        if (isVisible) {
            initData();
        }

    }

    private void initData() {
        if (warBeanList != null) {
            warBeanList.clear();
            List<WarBean> warBeans = DbUtils.queryAll();
            warBeanList.addAll(warBeans);
            recyclerViewAdapter.updateDate(warBeanList);
        }

    }

    private void initView(View inflate) {
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        selectAll = (Button) inflate.findViewById(R.id.select_all);
        editBtn = (Button) inflate.findViewById(R.id.edit_btn);

        selectAll.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        recyclerViewAdapter.setOnLongClick(new RecyclerViewAdapter.onLongClick() {
            @Override
            public void onLongItemClick(WarBean warBean) {
                // 点击收藏item
                DbUtils.delete(warBean);
                Toast.makeText(getActivity(), "删除了" + warBean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mDelete = (Button) inflate.findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
    }

    boolean isEdit;
    boolean isAll;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                if (isEdit) { // 必须是编辑模式才能全选
                    if (!isAll) { // 假如未选择 全选，改变状态，设置为全选
                        isAll = true;
                    } else {
                        isAll = false;
                    }
                    recyclerViewAdapter.selectAllMode(isAll);
                }
                break;
            case R.id.edit_btn:
                if (!isEdit) {
                    isEdit = true;
                } else {
                    isEdit = false;
                }
                recyclerViewAdapter.setEditMode(isEdit);
                break;
            case R.id.delete:

                recyclerViewAdapter.delete();

                break;
        }
    }
}
