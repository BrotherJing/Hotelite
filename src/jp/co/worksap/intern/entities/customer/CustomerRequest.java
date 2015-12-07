package jp.co.worksap.intern.entities.customer;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class CustomerRequest {

	/*request_id
  request_time
  request_content
  customer_id*/
	
	private Integer reqeustId;
	private Date requestTime;
	private String requestContent;
	private Integer customerId;
	
	public CustomerRequest(){}
	
	public CustomerRequest(Date requestTime,
			String requestContent, Integer customerId) {
		super();
		this.requestTime = requestTime;
		this.requestContent = requestContent;
		this.customerId = customerId;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getReqeustId() {
		return reqeustId;
	}

	public void setReqeustId(Integer reqeustId) {
		this.reqeustId = reqeustId;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	
	
}
