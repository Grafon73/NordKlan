package com.grafon.test.eventsDB;

import com.grafon.test.CalendarQuickstart;

import javax.swing.*;
import java.io.IOException;
import java.security.GeneralSecurityException;


public class CreateEvent {

String username;
JTable table;
EventsDB eventsDB = new EventsDB();
String day, hour;

public CreateEvent(String day, String hour, String username, JTable table){
    this.day=day;
    this.hour=hour;
    this.username=username;
    this.table=table;
}

public void removeEvent(int row, int column){
    table.setValueAt("",row-1,column);
    eventsDB.DeleteSQL(day,hour);
    CalendarQuickstart calendarQuickstart = new CalendarQuickstart();
    try {
        calendarQuickstart.deleteEvent(day,hour,username);
    } catch (GeneralSecurityException | IOException e) {
        e.printStackTrace();
    }
}
public void addEvent(int row, int column, String message){
    CheckEventDate checkEventDate = new CheckEventDate(day, hour);
    if(table.getValueAt(row-1,column)==null) {
        if (checkEventDate.doCheckTime()) {
            eventsDB.WriteSQL(day, hour, username, message);
            table.setValueAt(username + ": " + message, row - 1, column);
            CalendarQuickstart calendarQuickstart = new CalendarQuickstart();
            try {
                calendarQuickstart.createEvent(message,day,hour,username);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Reserve error: Minimum booking interval 30 minutes,\n" +
                    "maximum - 24 hours");
        }
    }else {
        JOptionPane.showMessageDialog(null, "Reserve error: Room is busy at this time");
    }

}
}
