package com.example.dhruboandroid.routemaster;

/**
 * Created by Dhrubo Android on 7/6/2018.
 */


/**
 * Created by Dhrubo Android on 7/6/2018.
 *POJO class for recieving and posting data.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Businfo {

    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("total_vehicle")
    @Expose
    private String totalVehicle;
    @SerializedName("first_trip_time")
    @Expose
    private String firstTripTime;
    @SerializedName("last_trip_time")
    @Expose
    private String lastTripTime;
    @SerializedName("start_point")
    @Expose
    private String startPoint;
    @SerializedName("end_point")
    @Expose
    private String endPoint;
    @SerializedName("minimum_fare")
    @Expose
    private String minimumFare;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("fare")
    @Expose
    private String fare;

    @SerializedName("stops")
    @Expose
    private List<Stop> stops = null;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTotalVehicle() {
        return totalVehicle;
    }

    public void setTotalVehicle(String totalVehicle) {
        this.totalVehicle = totalVehicle;
    }

    public String getFirstTripTime() {
        return firstTripTime;
    }

    public void setFirstTripTime(String firstTripTime) {
        this.firstTripTime = firstTripTime;
    }

    public String getLastTripTime() {
        return lastTripTime;
    }

    public void setLastTripTime(String lastTripTime) {
        this.lastTripTime = lastTripTime;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getMinimumFare() {
        return minimumFare;
    }

    public void setMinimumFare(String minimumFare) {
        this.minimumFare = minimumFare;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }


    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String toString(){
        return getVehicleName()+"\n"+getVehicleType()+"\n"+getTotalVehicle()+"\n"+getCreatedAt()+"\n"+getCreatedBy()+"\n"+getEndPoint()+"\n"+getFare()+"\n"+getStops();

    }
}
