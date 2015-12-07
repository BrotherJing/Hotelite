package jp.co.worksap.intern.entities.staff;

import jp.co.worksap.intern.entities.ICsvMasterDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

public class StaffDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8538243149394207296L;
	
	private Integer staffId;
	private String name;
	private String gender;
	private String rank;
	private String position;
	private Integer hotelId;
    private String staffState;
    private List<ShiftRecord> shiftRecordList;
    private Integer taskId;

    public StaffDTO() {
    }

    /**
	 * Default Constructor
	 *
	 * @param name
	 * @param gender
	 * @param rank
	 * @param position
	 * @param hotelId
	 */
	public StaffDTO(String name, String gender,
			String rank, String position, Integer hotelId,String staffState,Integer taskId) {
		super();
		this.name = name;
		this.gender = gender;
		this.rank = rank;
		this.position = position;
		this.hotelId = hotelId;
        this.staffState = staffState;
        this.taskId = taskId;
	}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
	public Integer getStaffId() {
		return staffId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getRank() {
		return rank;
	}

	public String getPosition() {
		return position;
	}

	public Integer getHotelId() {
		return hotelId;
	}

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getStaffState() {
        return staffState;
    }

    public void setStaffState(String staffState) {
        this.staffState = staffState;
    }

    public List<ShiftRecord> getShiftRecordList() {
        return shiftRecordList;
    }

    public void setShiftRecordList(List<ShiftRecord> shiftRecordList) {
        this.shiftRecordList = shiftRecordList;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
