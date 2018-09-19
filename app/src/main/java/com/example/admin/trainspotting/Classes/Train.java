package com.example.admin.trainspotting.Classes;

import android.util.Log;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Train implements Serializable {
    Integer trainNumber;
    String departureDate;
    int operatorUICCode;
    String operatorShortCode;
    String trainType;
    String trainCategory;
    boolean runningCurrently;
    boolean cancelled;
    List<TimeTableRow> timeTableRows;

    public String getTrainNumber() {
        return trainNumber.toString();
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public int getOperatorUICCode() {
        return operatorUICCode;
    }

    public void setOperatorUICCode(int operatorUICCode) {
        this.operatorUICCode = operatorUICCode;
    }

    public String getOperatorShortCode() {
        return operatorShortCode;
    }

    public void setOperatorShortCode(String operatorShortCode) {
        this.operatorShortCode = operatorShortCode;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public void setTrainCategory(String trainCategory) {
        this.trainCategory = trainCategory;
    }

    public boolean isRunningCurrently() {
        return runningCurrently;
    }

    public void setRunningCurrently(boolean runningCurrently) {
        this.runningCurrently = runningCurrently;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public List<TimeTableRow> getTimeTableRows() {
        return timeTableRows;
    }

    public void setTimeTableRows(List<TimeTableRow> timeTableRows) {
        this.timeTableRows = timeTableRows;
    }

    public String getDepartureStation() {
        return timeTableRows.get(0).getStationShortCode();
    }

    public String getDestinationStation() {
        return timeTableRows.get(timeTableRows.size() - 1).getStationShortCode();
    }

    public TimeTableRow getArrivingStation(String stationCode) {

        for(TimeTableRow timeTableRow : timeTableRows) {
            if(timeTableRow.getStationShortCode().equals(stationCode) &&
                timeTableRow.getType().equals("ARRIVAL")) {
                    Log.i("STATION: ", timeTableRow.toString());
                    return timeTableRow;
            }
        }
        return null;
    }

    public TimeTableRow getDepartingStation(String stationCode) {

        for(TimeTableRow timeTableRow : timeTableRows) {
            if(timeTableRow.getStationShortCode().equals(stationCode) &&
                    timeTableRow.getType().equals("DEPARTURE")) {
                Log.i("STATION: ", timeTableRow.toString());
                return timeTableRow;
            }
        }
        return null;
    }


}
