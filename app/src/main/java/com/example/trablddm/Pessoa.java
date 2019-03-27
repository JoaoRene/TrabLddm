package com.example.trablddm;

public class Pessoa {
    private String name;
    private String phone;
    private String email;

    public Pessoa() {
        this.name = "";
        this.phone = "";
        this.email = "";
    }

    public Pessoa(String nome, String phone, String email) {
        this.name = nome;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
