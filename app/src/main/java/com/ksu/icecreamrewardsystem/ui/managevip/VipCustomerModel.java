package com.ksu.icecreamrewardsystem.ui.managevip;

public class VipCustomerModel {
    private int id;
    private String vipId;
    private String fullName;
    private int totalPoints;
    private int monthlyPoints;

    public VipCustomerModel() {

    }

    public VipCustomerModel(int id, String vipId, String fullName, int totalPoints, int monthlyPoints) {
        this.id = id;
        this.vipId = vipId;
        this.fullName = fullName;
        this.totalPoints = totalPoints;
        this.monthlyPoints = monthlyPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(int monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }
}
