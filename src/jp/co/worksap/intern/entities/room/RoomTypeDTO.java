package jp.co.worksap.intern.entities.room;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

@Entity
@Table(name="room_type")
public class RoomTypeDTO implements ICsvMasterDTO{
	
	private static final long serialVersionUID = -8538243149394207296L;
	
	private Integer roomTypeId;
	private String roomType;
	private Double price;
	private String priceUnit;
	
	public RoomTypeDTO(){}
	
	public RoomTypeDTO(String roomType, Double price, String priceUnit) {
		super();
		this.roomType = roomType;
		this.price = price;
		this.priceUnit = priceUnit;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
