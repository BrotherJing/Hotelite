package com.brotherjing.view.timeline;

import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-07.
 */
public class BaseTimeLine {

    private List<TimeLineRecord> recordList;
    private Date start,end;

    public BaseTimeLine(List<TimeLineRecord> recordList, Date start, Date end) {
        this.recordList = recordList;
        this.start = start;
        this.end = end;
    }

    public List<TimeLineRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<TimeLineRecord> recordList) {
        this.recordList = recordList;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
