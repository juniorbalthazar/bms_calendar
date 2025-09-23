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
 * The persistent class for the "BMS_DAYOFWEEK" database table.
 * 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_dayofweek")
@NamedQuery(name="BmsDayofweek.findAll", query="SELECT b FROM BmsDayofweek b")
public class BmsDayofweek implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dayofweek_id")
	private BigDecimal dayofweekId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="dayofweek_name")
	private String dayofweekName;
	
	@Column(name="short_name")
	private String shortName;

	//bi-directional many-to-one association to BmsCalendar
	@OneToMany(mappedBy="bmsDayofweek")
	private List<BmsCalendar> bmsCalendars;


}