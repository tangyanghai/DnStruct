package com.example.dndao.db;

import android.database.sqlite.SQLiteDatabase;

public class BaseDaoFactory {

    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();
    public static BaseDaoFactory getOurInstance(){
        return ourInstance;
    }

    private SQLiteDatabase sqLiteDatabase;
    //定义建数据库的路径
    //建议写到SD卡中,好处,APP删除了,下次再次安装的时候,数据还在
    private String sqLiteDatabaselPath;
    private BaseDaoFactory(){
        //可以先判断有没有SD卡
        //设置路径,可以单独写配置文件
        sqLiteDatabaselPath = "data/data/com.example.dndao/jett.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqLiteDatabaselPath,null);
    }

    /**
     * 用来生产basedao对象
     */
    public <T> BaseDao<T> getBaseDao(Class<T> entityClass){
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase,entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

}
