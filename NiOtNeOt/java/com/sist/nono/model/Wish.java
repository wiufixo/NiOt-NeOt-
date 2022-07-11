package com.sist.nono.model;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wish")
public class Wish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ws_no;

	@ManyToOne(fetch = FetchType.EAGER) //기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name="cu_no")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER) //기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name="pr_no")
	private Product product;
}
