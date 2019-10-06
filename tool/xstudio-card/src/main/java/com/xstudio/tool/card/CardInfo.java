package com.xstudio.tool.card;

import java.io.Serializable;
import java.util.Date;

/**
 * 证件信息
 *
 * @author xiaobiao
 * @version 2018/6/28
 */
public class CardInfo implements Serializable {
    /**
     * 性别
     */
    private Gender gender = Gender.N;

    /**
     * 年龄
     */
    private Integer age = 0;
    /**
     * 生日
     */
    private Date birthday;

    /**
     * 星座
     */
    private Constellations constellation;

    /**
     * 生肖
     */
    private Zodiac zodiac;

    /**
     * 证件值
     *
     * 如果是身份证，则为18位
     */
    private String value;

    /**
     * Getter for property 'gender'.
     *
     * @return Value for property 'gender'.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Setter for property 'gender'.
     *
     * @param gender Value to set for property 'gender'.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Getter for property 'genderZhString'.
     *
     * @return Value for property 'genderZhString'.
     */
    public String getGenderZhString() {
        return getGender().getZhString();
    }

    /**
     * Getter for property 'age'.
     *
     * @return Value for property 'age'.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Setter for property 'age'.
     *
     * @param age Value to set for property 'age'.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Getter for property 'birthday'.
     *
     * @return Value for property 'birthday'.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Setter for property 'birthday'.
     *
     * @param birthday Value to set for property 'birthday'.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Getter for property 'constellation'.
     *
     * @return Value for property 'constellation'.
     */
    public Constellations getConstellation() {
        return constellation;
    }

    /**
     * Setter for property 'constellation'.
     *
     * @param constellation Value to set for property 'constellation'.
     */
    public void setConstellation(Constellations constellation) {
        this.constellation = constellation;
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for property 'value'.
     *
     * @param value Value to set for property 'value'.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for property 'zodiac'.
     *
     * @return Value for property 'zodiac'.
     */
    public Zodiac getZodiac() {
        return zodiac;
    }

    /**
     * Setter for property 'zodiac'.
     *
     * @param zodiac Value to set for property 'zodiac'.
     */
    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
    }
}
