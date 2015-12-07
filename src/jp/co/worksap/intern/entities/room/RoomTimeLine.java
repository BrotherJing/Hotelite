package jp.co.worksap.intern.entities.room;

import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-05.
 */
public class RoomTimeLine {

    private List<RoomRecord> recordList;
    private Date start,end;

    public RoomTimeLine(List<RoomRecord> recordList, Date start, Date end) {
        this.recordList = recordList;
        this.start = start;
        this.end = end;
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

    public List<RoomRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RoomRecord> recordList) {
        this.recordList = recordList;
    }
}
