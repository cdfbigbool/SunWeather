package com.dssaz.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


//表名
@DatabaseTable(tableName = "tb_exam")
public class Exam implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "subject")
    private String subject;

    @DatabaseField(columnName = "exector")
    private String exector;

    @DatabaseField(columnName = "one")
    private String one;

    @DatabaseField(columnName = "two")
    private String two;

    @DatabaseField(columnName = "three")
    private String three;

    @DatabaseField(columnName = "detail")
    private String detail;

    @DatabaseField(columnName = "time")
    private String time;

    @DatabaseField(columnName = "reason")
    private String reason;

    //0,待审核
    //1,审核通过
    //2,审核驳回
    @DatabaseField(columnName = "status",dataType = DataType.INTEGER)
    private int status;

    @DatabaseField(columnName = "examminor")
    private String examminor;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExector() {
        return exector;
    }

    public void setExector(String exector) {
        this.exector = exector;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExamminor() {
        return examminor;
    }

    public void setExamminor(String examminor) {
        this.examminor = examminor;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", exector='" + exector + '\'' +
                ", one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", three='" + three + '\'' +
                ", detail='" + detail + '\'' +
                ", time='" + time + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}
