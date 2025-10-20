package ht.bms.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the "BMS_OFFICE_SERVICE" database table.
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_office_service")
@NamedQuery(name="BmsOfficeService.findAll", query="SELECT b FROM BmsOfficeService b")
public class BmsOfficeService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_service_seq_generator")
	@SequenceGenerator(name="office_service_seq_generator", sequenceName = "OFFICE_SERVICE_SEQ",initialValue = 2, allocationSize=1)
	private BigDecimal id;

	//bi-directional many-to-one association to BmsOffice
	@ManyToOne
	@JoinColumn(name="office_id")
	private BmsOffice bmsOffice;

	//bi-directional many-to-one association to BmsService
	@ManyToOne
	@JoinColumn(name="service_id")
	private BmsService bmsService;

}