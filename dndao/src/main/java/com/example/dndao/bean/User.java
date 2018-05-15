package com.example.dndao.bean;

import com.example.dndao.annotation.DbField;
import com.example.dndao.annotation.DbTable;

@DbTable("tb_user")//表名,用这个注释表名就是注释的内容,不用就是表名就是类名
public class User {
    @DbField("_id")//字段名,用
    private Integer id;
    private  String name;
    private  String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
