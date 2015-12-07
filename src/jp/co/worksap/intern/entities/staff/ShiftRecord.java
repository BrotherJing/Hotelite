package jp.co.worksap.intern.entities.staff;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.brotherjing.view.timeline.TimeLineRecord;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class ShiftRecord{
	
	/*
	 * shift_id
  staff_id
  shift_start_time
  shift_end_time*/
	
	private Integer shiftId;
	private Integer staffId;
	private Date recordStartTime;
	private Date recordEndTime;
	
	public ShiftRecord(){}
	
	public ShiftRecord(Integer staffId, Date recordStartTime,
			Date recordEndTime) {
		super();
		this.staffId = staffId;
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getShiftId() {
		return shiftId;
	}
	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
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
	
	
	
}
