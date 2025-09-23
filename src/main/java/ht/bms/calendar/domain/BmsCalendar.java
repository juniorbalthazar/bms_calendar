package ht.bms.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the "BMS_CALENDAR" database table.
 * 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_calendar")
@NamedQuery(name="BmsCalendar.findAll", query="SELECT b FROM BmsCalendar b")
public class BmsCalendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="calendar_date")
	private Date calendarDate;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	private BigDecimal dayofmonth;


	@Column(name="holiday_name")
	private String holidayName;

	@Column(name="holiday_note")
	private String holidayNote;

	private BigDecimal isbusinessday;

	private BigDecimal isdavailable;

	private BigDecimal ishalfday;

	private BigDecimal isholiday;
	
	@Column(name="CALENDAR_STR")
	private String dateStr;
	
	@Column(name="CALENDAR_LON")
	private BigDecimal dateLong;

	private BigDecimal year;
	

	//bi-directional many-to-one association to BmsDayofweek
	@ManyToOne
	@JoinColumn(name="dayofweek_id")
	private BmsDayofweek bmsDayofweek;

	//bi-directional many-to-one association to BmsMonthofyear
	@ManyToOne
	@JoinColumn(name="monthofyear_id")
	private BmsMonthofyear bmsMonthofyear;


}