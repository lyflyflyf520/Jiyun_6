package com.example.day_05;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private EditText inputContent;
    private TextView result;
    private Button insert;
    private Button delete;
    private Button update;
    private Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputContent = (EditText) findViewById(R.id.input_content);
        result = (TextView) findViewById(R.id.result);
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.query);


        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        query.setOnClickListener(this);
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

    private void insertData() {

        String input = inputContent.getText().toString();

        DaoMaster daoMaster = MyApplication.getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();

        Person person = new Person();

        String[] params = null;   // lisi,24
        if (input.contains(",")) {  //根据输入对象拆分内容
            params = input.split(",");
            person.setName(params[0]);
            person.setAge(params[1]);

        } else {
            person.setName(input);
        }
        daoSession.insert(person); // 插入数据

    }

    /**
     * 删除某人，先查询是否有这个人
     */
    private void deleteData() {
        //lisi,24
        String name = inputContent.getText().toString();
        // 先查询某人
        Person person = queryDataByOne(name);

        DaoMaster daoMaster = MyApplication.getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();

        daoSession.delete(person);// 删除数据

    }

    private void updateData() {


        // lisi
        // list,22
        String input = inputContent.getText().toString();

        DaoMaster daoMaster = MyApplication.getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();

        // 先查询某人
        Person person = queryDataByOne(input);

        person.setAge("50");

        daoSession.update(person);// 更新数据
    }

    private void queryData() {
        String name = inputContent.getText().toString();
        DaoMaster daoMaster = MyApplication.getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();

        // queryBuilder  where()  build  list
        Person person =  daoSession.queryBuilder(Person.class)
                .where(PersonDao.Properties.Name.eq(name))// equals
                .build()
                .unique();

        result.setText(person.getName()+"==age="+person.getAge());
    }

    /**
     * 根据某个人的名称查询
     *
     * @param name lisi
     */
    private Person queryDataByOne(String name) {

        DaoMaster daoMaster = MyApplication.getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();


        // queryBuilder  where()  build  list
        List<Person> personList =  daoSession.queryBuilder(Person.class)
                .where(PersonDao.Properties.Name.eq(name))// equals
                .build()
                .unique();
        // 如果是多个数据，只取第一个
        return personList.size() > 0 ? personList.get(0) : null;

    }
}
