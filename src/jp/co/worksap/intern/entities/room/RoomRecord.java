package jp.co.worksap.intern.entities.room;


import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class RoomRecord {

	private Integer recordId;
	private String recordType;
	private Integer roomId;
	private Integer customerId;
	private Integer staffId;
	private Date recordStartTime;
	private Date recordEndTime;
	
	public RoomRecord(){}
	
	public RoomRecord(String recordType, Integer roomId, Integer customerId, Integer staffId, Date recordStartTime,
			Date recordEndTime) {
		super();
		this.roomId = roomId;
		this.recordType = recordType;
		this.customerId = customerId;
		this.staffId = staffId;
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

    @Override
    public String toString() {
        return "RoomRecord{" +
                "recordId=" + recordId +
                ", recordType='" + recordType + '\'' +
                ", roomId=" + roomId +
                ", customerId=" + customerId +
                ", staffId=" + staffId +
                ", recordStartTime=" + recordStartTime +
                ", recordEndTime=" + recordEndTime +
                '}';
    }
}
