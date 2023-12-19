package com.lkc.www.util;

public class LoginDtod {
    private String username;
    private String password;
    private Integer age;

    public LoginDtod() {
    }

    public LoginDtod(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return "LoginDtod{username = " + username + ", password = " + password + ", age = " + age + "}";
    }
}
