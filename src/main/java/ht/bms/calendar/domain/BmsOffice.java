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
 * The persistent class for the "BMS_OFFICE" database table.
 * 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_office")
@NamedQuery(name="BmsOffice.findAll", query="SELECT b FROM BmsOffice b")
public class BmsOffice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_seq_generator")
	@SequenceGenerator(name="office_seq_generator", sequenceName = "OFFICE_SEQ",initialValue = 2, allocationSize=1)
	@Column(name="office_id")
	private BigDecimal officeId;

	private BigDecimal active;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="INSTITUTION_ID")
	private BigDecimal institutionId;

	@Column(name="is_central")
	private BigDecimal isCentral;

	@Column(name="office_name")
	private String officeName;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to BmsAccount
	//@ManyToOne
	@Column(name="created_by")
	private BigDecimal createdBy;

	//bi-directional many-to-one association to BmsOfficeService
	@OneToMany(mappedBy="bmsOffice")
	private List<BmsOfficeService> bmsOfficeServices;


}