package com.example.wdd_vip.jelaja.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JelapayModel {

    @SerializedName("id_ptransaction")
    @Expose
    private int id_ptransaction;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("factory_name")
    @Expose
    private String company_name;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("rich_url")
    @Expose
    private String rich_url;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("tgl")
    @Expose
    private String berangkat;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("message")
    @Expose
    private String message;

//    user function

    public Integer getId() {
        return id_ptransaction;
    }
    public void setId(Integer id_ptransaction) {
        this.id_ptransaction= id_ptransaction;
    }

    public String getAmount() {
        return amount;
    }
    public void setMessage(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUser() {
        return user;
    }
    public void setUser(Integer user) {
        this.user = user;
    }

    public String getName() {
        return username;
    }
    public void setName(String username) {
        this.username = username;
    }

    public String getRich_url() {
        return rich_url;
    }
    public void setRich_url(String rich_url) {
        this.rich_url = rich_url;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDetail() { return detail; }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBerangkat() {
        return berangkat;
    }
    public void setBerangkat(String berangkat) {
        this.berangkat = berangkat;
    }

    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public String getMessage() {
        return message;
    }
    public String getKota() {
        return kota;
    }
    public void setKota(String kota) {
        this.kota = kota;
    }

}

