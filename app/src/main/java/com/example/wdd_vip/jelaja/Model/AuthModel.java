package com.example.wdd_vip.jelaja.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("passenger")
    @Expose
    private int passenger;
    @SerializedName("room")
    @Expose
    private int room;
    @SerializedName("night")
    @Expose
    private int night;
    @SerializedName("photo")
    @Expose
    private String photo;

//user serialized
    @SerializedName("id_user")
    @Expose
    private Integer id_user;
    @SerializedName("username")
    @Expose
    private String name;
    @SerializedName("role")
    @Expose
    private String role;

//    detail user serialized
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("factory_name")
    @Expose
    private String factory_name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("bank_name")
    @Expose
    private String bank_name;
    @SerializedName("bank_number")
    @Expose
    private String bank_number;

//    schedule seralize
    @SerializedName("id_schedule")
    @Expose
    private Integer id_schedule;
    @SerializedName("go_at")
    @Expose
    private String go_at;
    @SerializedName("est_time")
    @Expose
    private String est_time;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("price")
    @Expose
    private String price;

//    pay transaction

    @SerializedName("id_ptransaction")
    @Expose
    private int id_ptransaction;
    @SerializedName("amount")
    @Expose
    private String amount;

//    resort

    @SerializedName("id_resort")
    @Expose
    private int id_resort;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("day_open")
    @Expose
    private String day_open;
    @SerializedName("simple_day")
    @Expose
    private String simple_day;
    @SerializedName("open_at")
    @Expose
    private String time_open;
    @SerializedName("close_at")
    @Expose
    private String time_close;
    @SerializedName("simple_time")
    @Expose
    private String simple_time;

//    user function

    public Integer getId() {
        return id_user;
    }
    public void setId(Integer id_user) {
        this.id_user = id_user;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

//    Deatil user function

    public Integer getUser() {
        return user;
    }
    public String getFullName() {
        return full_name;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getCompany_name() { return factory_name; }
    public String getSector() { return sector; }

    public String getBank() { return bank_name; }
    public String getNumber() { return bank_number; }

//    Schedule function

    public int getIdSchedule() {
        return id_schedule;
    }

    public String getGo_at() {
        return go_at;
    }

    public String getEst_time() {
        return est_time;
    }
    public void setEst_time(String est_time) {
        this.est_time= est_time;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source= source;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination= destination;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.destination= price;
    }

    public int getPassenger() {
        return passenger;
    }
    public String getEmail() {
        return email;
    }
    public void setPassenger(int passenger) {
        this.passenger= passenger;
    }

    public int getRoom() {
        return room;
    }
    public int getNight() {
        return night;
    }

//    pay transaction

    public int getPayTransId() {
        return id_ptransaction;
    }
    public String getAmount() {
        return amount;
    }

//    resort
    public int getIdResort() {
    return id_resort;
}
    public String getDescription() {
        return description;
    }
    public String getDay() {
        return day_open;
    }
    public String getSimpleDay() {
        return simple_day;
    }
    public String getTime() {
        return time_open;
    }
    public String getCloseTime() {
        return time_close;
    }
    public String getSimpleTime() {
        return simple_time;
    }
    public String getPhoto() {
        return photo;
    }
}
