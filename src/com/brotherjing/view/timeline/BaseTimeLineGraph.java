package com.brotherjing.view.timeline;

import jp.co.worksap.intern.entities.room.Constants;
import jp.co.worksap.intern.entities.room.RoomRecord;
import jp.co.worksap.intern.entities.room.RoomTimeLine;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class BaseTimeLineGraph extends JPanel{

    private Date fromDate,toDate;
    private List<TimeLineRecord> timeLine;

    private final int HEIGHT = 100;

    public BaseTimeLineGraph(Date fromDate,Date toDate, List<TimeLineRecord> timeLine) {
        super();
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.timeLine = timeLine;

        repaint();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        repaint();
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
        repaint();
    }

    public List<TimeLineRecord> getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(List<TimeLineRecord> timeLine) {
        this.timeLine = timeLine;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(fromDate==null||toDate==null||timeLine==null)return;

        int maxWidth = getWidth();
        long toTimestamp = toDate.getTime();
        long fromTimestamp = fromDate.getTime();
        long length = toTimestamp-fromTimestamp;

        int left,right;
        for(TimeLineRecord record:timeLine){
            if(record.getRecordStartTime().before(fromDate)){
                left=0;
            }else{
                left = (int)((record.getRecordStartTime().getTime()-fromTimestamp)*1.0/length*maxWidth);
            }
            if(record.getRecordEndTime().after(toDate)){
                right = maxWidth;
            }else{
                right=(int)((record.getRecordEndTime().getTime()-fromTimestamp)*1.0/length*maxWidth);
            }

            g.setColor(Color.ORANGE);
            /*if(record.getRecordType().equals(Constants.BOOK))g.setColor(Color.CYAN);
            else if(record.getRecordType().equals(Constants.CLEAN))g.setColor(Color.GREEN);
            else if(record.getRecordType().equals(Constants.ARRANGE))g.setColor(Color.ORANGE);*/
            g.fillRect(left, 0, right - left,HEIGHT);
        }

        int step;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int start;
        //draw scalar
        if(length>=3*24*3600*1000){
            //range larger than 3 days
            //day
            step = (int)(24*3600*1000*1.0/length*maxWidth);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }else{
            //hour
            step = (int)(3600*1000*1.0/length*maxWidth);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.HOUR_OF_DAY,1);
        }
        start = (int)((calendar.getTime().getTime()-fromDate.getTime())*1.0/length*maxWidth);
        g.setColor(Color.pink);
        for(;start<maxWidth;start+=step){
            g.fillRect(start,0,1,HEIGHT);
        }

        //draw current time
        start = (int)((new Date().getTime()-fromDate.getTime())*1.0/length*maxWidth);
        g.setColor(Color.RED);
        g.fillRect(start,0,3,HEIGHT);

    }

}
