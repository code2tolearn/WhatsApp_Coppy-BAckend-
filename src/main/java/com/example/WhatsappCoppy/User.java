package com.example.WhatsappCoppy;
import java.util.HashSet ;

public class User {
    private String name;
    private String mobile;
    static HashSet<String> set;

    public User(String name, String mobile) {
        new HashSet<>();
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}