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
 * The persistent class for the "BMS_CALENDAR_HOLIDAY_FIXED" database table.
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_calendar_holiday_fixed")
@NamedQuery(name="BmsCalendarHolidayFixed.findAll", query="SELECT b FROM BmsCalendarHolidayFixed b")
public class BmsCalendarHolidayFixed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="holiday_id")
	private Date holidayId;

	@Column(name="holiday_name")
	private String holidayName;
	
	@Column(name="HOLIDAY_STR")
	private String holidayStr;
	
	@Column(name="IS_AVAILABLE")
	private BigDecimal isAvailable;

	
}