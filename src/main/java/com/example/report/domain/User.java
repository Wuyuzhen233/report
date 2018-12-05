package com.example.report.domain;

/**
 * Created by wangyuan on 2018/12/5.
 */
public class User {
    private int id;
    private String base;
    private String name;
    private String phone;
    private String password;
    private int role;// 用户角色，超管10，员工11
    private int status;// 用户状态，正常20，离职21

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":");
        sb.append(id);
        sb.append(",\"base\":\"");
        sb.append(base).append('\"');
        sb.append(",\"name\":\"");
        sb.append(name).append('\"');
        sb.append(",\"phone\":\"");
        sb.append(phone).append('\"');
        sb.append(",\"password\":\"");
        sb.append(password).append('\"');
        sb.append(",\"role\":");
        sb.append(role);
        sb.append(",\"status\":");
        sb.append(status);
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
