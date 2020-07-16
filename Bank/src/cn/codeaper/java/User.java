package cn.codeaper.java;

import java.sql.Date;
import java.time.LocalDate;


/**
 * @description: 用户类
 * @author: qzl
 * @created: 2020/07/15 19:12
 */

public class User {
    private int id;
    private String name;
    private String sex;
    private String phone;
    private int money;
    private Date start_card;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getStart_card() {
        return start_card;
    }

    public void setStart_card(LocalDate start_card) {
        Date date = Date.valueOf(start_card);
        this.start_card =date;
    }
}
