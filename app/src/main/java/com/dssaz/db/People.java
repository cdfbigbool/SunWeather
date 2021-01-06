package com.dssaz.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


//表名
@DatabaseTable(tableName = "People")
public class People {
    /*
    * columnName  字段名
    * dataType 数据类型
    * defaultValue 字符串默认值
    * canBeNull 可否为空
    * id 主键
    * generatedId 自增主键
    * foreign 外联
    * unique 唯一
    * */
    //自增主键
    @DatabaseField(generatedId = true)
    private int id;

    //列名
    @DatabaseField(columnName = "name")
    private String name;
    /*
    * 常用类型:
    * DataType.STRING
    * DataType.BOOLEAN
    * DataType.DATE
    * DataType.DATE_LONG
    * DataType.CHAR
    * DataType.BYTE
    * DataType.SHORT
    * DataType.INTEGER
    * DataType.LONG
    * DataType.FLOAT
    * DataType.DOUBLE
    * DataType.SERIALIZABLE
    * DataType.TIME_STAMP
    * */
    //列名和类型
    @DatabaseField(columnName = "age",dataType = DataType.INTEGER)
    private int age;

    //必须存在无参构造器
    public People() {
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
