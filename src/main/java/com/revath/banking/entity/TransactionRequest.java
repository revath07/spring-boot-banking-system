package com.revath.banking.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="transaction_requests")
@Getter
@Setter
public class TransactionRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String requestId;
	
	private String status;
	
	public void setRequestId(String requestId)
	{
		this.requestId=requestId;
	}
	
	public String getRequestId()
	{
		return requestId;
	}
	
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatus(String status)
	{
		return status;
	}
	
	
	

}
