package ht.bms.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the "BMS_SERVICES" database table.
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_services")
@NamedQuery(name="BmsService.findAll", query="SELECT b FROM BmsService b")
public class BmsService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_seq_generator")
	@SequenceGenerator(name="service_seq_generator", sequenceName = "SERVICE_SEQ",initialValue = 2, allocationSize=1)
	@Column(name="service_id")
	private BigDecimal osId;

	@Column(name="is_service_available")
	private String isServiceAvailable;

	@Column(name="service_fullName")
	private String serviceFullName;

	@Column(name="serviceShort_name")
	private String serviceShortName;

	//bi-directional many-to-one association to BmsOfficeService
	@OneToMany(mappedBy="bmsService")
	private List<BmsOfficeService> bmsOfficeServices;

}