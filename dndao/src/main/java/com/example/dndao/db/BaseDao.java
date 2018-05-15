package com.example.dndao.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import com.example.dndao.annotation.DbField;
import com.example.dndao.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1.完成自动建表功能
 */
public class BaseDao<T> implements IBaseDao<T> {
    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;
    //持有操作数据库所对应的java类型
    private Class<T> entityClass;
    //标识: 用来表示是否做过初始化操作
    private boolean isInit = false;
    //减少反射过程,定义一个缓存空间(key--字段名, value-成员变量)
    private HashMap<String, Field> cacheMap;


    //框架内部逻辑最好不要提供构造方法给调用层调用
    //这样可以保证框架层的内容对于调用者是封闭的
    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        //可以根据传入的entityClass类型来建立表,只需要建立一次
        if (!isInit) {
            //自动建表
            //取到表名
            if (entityClass.getAnnotation(DbTable.class) == null) {
                //反射到类名
                tableName = entityClass.getSimpleName();
            } else {
                //取注解上的名字
                tableName = entityClass.getAnnotation(DbTable.class).value();
            }

            if (!sqLiteDatabase.isOpen()) {
                return false;
            }

            //执行建表操作
            //create table if not exists tb_user(_id integer, name varchar(20), password varchar(20))
            //单独用个方法来生成create命令
            String createTableSql = getCreateTableSql();
            sqLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    /**
     * 缓存字段名和成员变量
     */
    private void initCacheMap() {
        //1.取所有的列名
        String sql = "select * from " + tableName + " limit 1,0";//空表
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        cursor.close();
        //2.取所有的成员变量
        Field[] columnFields = entityClass.getDeclaredFields();
        for (Field field : columnFields) {
            field.setAccessible(true);
        }
        //对1和2进行映射
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : columnFields) {
                String fieldName = null;
                if (field.getAnnotation(DbField.class) != null) {
                    fieldName = field.getAnnotation(DbField.class).value();
                } else {
                    fieldName = field.getName();
                }

                if (columnName.equals(fieldName)) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                cacheMap.put(columnName, columnField);
            }
        }
    }

    /**
     * @return 建表语句
     */
    private String getCreateTableSql() {
        //create table if not exists tb_user(_id integer, name varchar(20), password varchar(20))
        StringBuffer stringBuffer = new StringBuffer("create table if not exists ");
        stringBuffer.append(tableName)
                .append("(");
        //反射来得到所有的成员变量,用来控制类型
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();//拿到成员的类型
            if (field.getAnnotation(DbField.class) != null) {
                if (type == String.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " TEXT,");
                } else if (type == Integer.class || type == int.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " INTEGER,");
                } else if (type == Long.class || type == long.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BIGINT,");
                } else if (type == Double.class || type == long.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BLOB,");
                } else {
                    //不支持的类型
                    continue;
                }
            } else {
                if (type == String.class) {
                    stringBuffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class || type == int.class) {
                    stringBuffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class || type == long.class) {
                    stringBuffer.append(field.getName() + " BIGINT,");
                } else if (type == Double.class || type == double.class) {
                    stringBuffer.append(field.getName() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getName() + " BLOB,");
                } else {
                    //不支持的类型sel
                    continue;
                }
            }
        }

        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public long insert(T entity) {
        //重点是拿到ContentValues
        //ContentValues contentValues = new ContentValues();
        //contentValues.put("_id",1);
        //sqLiteDatabase.insert(tableName,null,contentValues);
        //准备好 ContentValue中需要的数据
        Map<String, String> map = getValues(entity);
        //把数据转移到ContentValues中
        ContentValues contentValues = getContentValues(map);
        //开始插入
        long result = sqLiteDatabase.insert(tableName, null, contentValues);

        return result;
    }

    /**
     * 更新数据
     *
     * @param entity 要更新的
     * @param where  选择条件
     * @return 被更新的内容
     */
    @Override
    public long update(T entity, T where) {
//        sqLiteDatabase.update(tableName,contentValues,"name = ?",new String[]{"jett"});
        int result = -1;
        Map<String, String> values = getValues(entity);
        ContentValues contentValues = getContentValues(values);
        Map whereCause = getValues(where);
        Condition condition = new Condition(whereCause);
        result = sqLiteDatabase.update(tableName, contentValues, condition.whereCause, condition.whereArgs);
        return result;
    }

    @Override
    public int delete(T where) {
//        sqLiteDatabase.delete(tableName,whereClause,whereArgs);
        Map<String, String> whereMap = getValues(where);
        Condition condition = new Condition(whereMap);
        int result = sqLiteDatabase.delete(tableName, condition.whereCause, condition.whereArgs);
        return result;
    }

    @Override
    public List<T> query(T where) {
        return query(where,null,null,null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> whereMap = getValues(where);
        String limitString = null;
        if (startIndex!=null&&limit!=null) {
            limitString = startIndex+" , "+limit;
        }
        Condition condition = new Condition(whereMap);
        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereCause, condition.whereArgs, null, null, orderBy,limitString);
        return getResult(cursor);
    }

    /**
     * @param cursor 游标
     * @return  根据游标返回数据列表
     */
    private List<T> getResult(Cursor cursor) {
        List<T> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] columnNames = cursor.getColumnNames();
            try {
                T t = entityClass.newInstance();
                for (String columnName : columnNames) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    if (columnIndex == -1) {
                        continue;
                    }
                    Field field = cacheMap.get(columnName);
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type == Integer.class) {
                        field.set(t, cursor.getInt(columnIndex));
                    } else if (type == Double.class) {
                        field.set(t, cursor.getDouble(columnIndex));
                    } else if (type == String.class) {
                        field.set(t, cursor.getString(columnIndex));
                    } else if (type == Long.class) {
                        field.set(t, cursor.getLong(columnIndex));
                    } else if (type == byte[].class) {
                        field.set(t, cursor.getBlob(columnIndex));
                    }
                }
                list.add(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 将数据从map转换成ContentValues
     */
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        //返回所有的成员变量
        Iterator<Field> fieldIterator = cacheMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            //获取成员变量的值
            try {
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }
                String values = object.toString();
                //获取列名
                String key = null;
                if (field.getAnnotation(DbField.class) != null) {
                    key = field.getAnnotation(DbField.class).value();
                } else {
                    key = field.getName();
                }
                //存入缓存
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(values)) {
                    map.put(key, values);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 用于封装  "name = ?",new String[]{"jett"}
     */
    private class Condition {
        private String whereCause;//"name=?"
        private String[] whereArgs;//new String[]{"jett"}

        public Condition(Map<String, String> whereCasue) {
            ArrayList list = new ArrayList();//whereArgs里面的内容
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1=1");
            //取所有的字段名
            Set<String> keys = whereCasue.keySet();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                String value = whereCasue.get(key);
                if (value != null) {
                    stringBuilder.append(" and " + key + "=?");
                    list.add(value);
                }
            }
            this.whereCause = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
        }


    }


}
