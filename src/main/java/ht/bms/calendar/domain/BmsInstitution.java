package ht.bms.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the "BMS_INSTITUTION" database table.
 * 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bms_institution")
@NamedQuery(name="BmsInstitution.findAll", query="SELECT b FROM BmsInstitution b")
public class BmsInstitution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institution_seq_generator")
	@SequenceGenerator(name="institution_seq_generator", sequenceName = "INSTITUTION_SEQ",initialValue = 2, allocationSize=1)
	@Column(name="institution_id")
	private BigDecimal institutionId;

	private String email;

	@Column(name="fulle_name")
	private String fulleName;

	private String phone;

	@Column(name="short_name")
	private String shortName;
	
	@Column(name="is_default")
	private BigDecimal isDefault;
	

}