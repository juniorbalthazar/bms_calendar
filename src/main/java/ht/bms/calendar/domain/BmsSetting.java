package ht.bms.calendar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the "BMS_ACCOUNTS" database table.
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_setting")
public class BmsSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="id")
	private BigDecimal Id;
	
	@Column(name="nbre_transaction_applicant")
	private BigDecimal nbreTransPerApplicant;
	
	@Column(name="nbre_transaction_office")
	private BigDecimal officeCapacity;
	
	@Column(name="start_hours")
	private String startHours;

	@Column(name="end_hours")
	private String endHours;
	
	@Column(name="expiration_certificate")
	private BigDecimal expirationCertificat;
	
	@Column(name="SEND_EMAIL_TRANSACTION")
	private BigDecimal sendEmailTransaction;
	
	@Column(name="MAX_TIME_TX_BEFORE_PAY")
	private BigDecimal maxTimeTxBeforePay;
	
	@Column(name="SMS_MESSAGE_BODY")
	private String smsMesageBody;
	
	@Column(name="institution_id")
	private BigDecimal institutionId;

}	
	

