package jp.co.worksap.intern.entities.customer;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class CustomerDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5538243149394207296L;

	private Integer customerId;
	private String name;
	private String gender;
	private Date birthday;
	private String nationality;
	private String passportNo;
	private String address;
	private String email;
	private String tel;

	public CustomerDTO(){}
	
	/**
	 * Default Constructor
	 *
	 * @param name
	 * @param gender
	 * @param birthday
	 * @param nationality
	 * @param passportNo
	 * @param address
	 * @param email
	 * @param tel
	 */
	public CustomerDTO(String name, String gender,
			Date birthday, String nationality, String passportNo,
			String address, String email, String tel) {
		super();
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.nationality = nationality;
		this.passportNo = passportNo;
		this.address = address;
		this.email = email;
		this.tel = tel;
	}


	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	

}
