package com.example.admin.trainspotting.Classes;

import java.util.List;

public class Train {
    Integer trainNumber;
    String departureDate;
    int operatorUICCode;
    String operatorShortCode;
    String trainType;
    String trainCategory;
    boolean runningCurrently;
    boolean cancelled;
    List<TimeTableRow> timeTableRows;
}
