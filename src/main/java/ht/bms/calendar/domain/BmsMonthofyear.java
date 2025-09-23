package ht.bms.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "BMS_MONTHOFYEAR" database table.
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_monthofyear")
@NamedQuery(name="BmsMonthofyear.findAll", query="SELECT b FROM BmsMonthofyear b")
public class BmsMonthofyear implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="monthofyear_id")
	private BigDecimal monthofyearId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="monthofyear_name")
	private String monthofyearName;

	@Column(name="short_name")
	private String shortName;
	
	@Column(name="SHORT_NAME_EN")
	private String shortNameEn;
	
	
	
	//bi-directional many-to-one association to BmsCalendar
	@OneToMany(mappedBy="bmsMonthofyear")
	private List<BmsCalendar> bmsCalendars;

}