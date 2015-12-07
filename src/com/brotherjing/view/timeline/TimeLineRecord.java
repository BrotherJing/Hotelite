package com.brotherjing.view.timeline;

import jp.co.worksap.intern.entities.staff.ShiftRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class TimeLineRecord {

    Date recordStartTime;
    Date recordEndTime;

    public TimeLineRecord(Date recordStartTime, Date recordEndTime) {
        this.recordStartTime = recordStartTime;
        this.recordEndTime = recordEndTime;
    }

    public Date getRecordStartTime() {
        return recordStartTime;
    }

    public void setRecordStartTime(Date recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public Date getRecordEndTime() {
        return recordEndTime;
    }

    public void setRecordEndTime(Date recordEndTime) {
        this.recordEndTime = recordEndTime;
    }

    public static List<TimeLineRecord> from(List<ShiftRecord> list){
        List<TimeLineRecord> records = new ArrayList<TimeLineRecord>();
        for(ShiftRecord record:list){
            records.add(new TimeLineRecord(record.getRecordStartTime(),record.getRecordEndTime()));
        }
        return records;
    }
}
