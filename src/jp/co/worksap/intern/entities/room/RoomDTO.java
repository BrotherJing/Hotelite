package jp.co.worksap.intern.entities.room;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import jp.co.worksap.intern.entities.ICsvMasterDTO;
import jp.co.worksap.intern.entities.staff.PositionType;
import jp.co.worksap.intern.entities.staff.RankType;

@Entity
@Table(name="room")
public class RoomDTO implements ICsvMasterDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8538243149394207296L;
	
	private Integer roomId;
	private String roomNumber;
	private Integer roomTypeId;
	private String state;
	private Integer customerId;

    private RoomTimeLine timeLine;

	public RoomDTO(){}

	public RoomDTO(String roomNumber, Integer roomTypeId,
			String state, Integer customerId) {
		super();
		this.roomNumber = roomNumber;
		this.roomTypeId = roomTypeId;
		this.state = state;
		this.customerId = customerId;
	}
	

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public RoomTimeLine getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(RoomTimeLine timeLine) {
        this.timeLine = timeLine;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "roomId=" + roomId +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", state='" + state + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
