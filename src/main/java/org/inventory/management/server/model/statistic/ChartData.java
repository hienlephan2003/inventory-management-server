package org.inventory.management.server.model.statistic;

public  class ChartData {
    private String day;
    private int nhap;
    private int xuat;

    public ChartData(String day, int nhap, int xuat) {
        this.day = day;
        this.nhap = nhap;
        this.xuat = xuat;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getNhap() {
        return nhap;
    }

    public void setNhap(int nhap) {
        this.nhap = nhap;
    }

    public int getXuat() {
        return xuat;
    }

    public void setXuat(int xuat) {
        this.xuat = xuat;
    }
}
