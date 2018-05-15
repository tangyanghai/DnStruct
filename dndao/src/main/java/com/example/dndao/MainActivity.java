package com.example.dndao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dndao.bean.Person;
import com.example.dndao.bean.User;
import com.example.dndao.db.BaseDao;
import com.example.dndao.db.BaseDaoFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 插入
     */
    public void insertObject(View view) {
//        BaseDao baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
//        baseDao.insert(new User(30,"tyh","123"));
        BaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        baseDao.insert(new Person("tyh0", 25));
        baseDao.insert(new Person("tyh1", 26));
        baseDao.insert(new Person("tyh2", 27));
        baseDao.insert(new Person("tyh3", 28));
        Toast.makeText(this, "执行成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 更新
     */
    public void updateObject(View view) {
        BaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        Person tyh_update = new Person();
        tyh_update.setName("update8");
        Person tyh_where = new Person();
        tyh_where.setName("tyh_update");

        long update = baseDao.update(tyh_update, tyh_where);
        Toast.makeText(this, "更新完成 更改项id = " + update, Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除
     */
    public void deleteObject(View view) {
        BaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        Person tyh_update = new Person();
        tyh_update.setName("update8");
        int delete = baseDao.delete(tyh_update);
        Toast.makeText(this, "删除完成  删除项 = " +delete, Toast.LENGTH_SHORT).show();
    }

    /**
     * 查询
     */
    public void queryObject(View view) {
        BaseDao<Person> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        Person tyh_update = new Person();
        tyh_update.setName("tyh3");
        List<Person> querys = baseDao.query(tyh_update);
        for (Person query : querys) {
            Log.e("查询到的数据",query.toString());
        }
    }
}
