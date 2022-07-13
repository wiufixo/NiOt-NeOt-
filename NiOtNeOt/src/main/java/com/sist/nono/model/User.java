package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment
	private int cu_no;
	
//	@Column(nullable = false, length = 100, unique = true)
	@NotEmpty(message = "*필수")
	@Size(min=2, max=5, message = "*아이디는 2~15자 사이로 입력해주세요.")
	private String cu_id;
	
//	@Column(nullable = false, length = 100)
//	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
	@NotEmpty(message = "*필수")
	private String cu_pwd;
	
//	@Column(nullable = false, length = 50)
	@NotEmpty(message = "*필수")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "*이메일 형식에 맞지 않습니다.")
	private String cu_email;
	
	@Enumerated(EnumType.STRING) //db에는 enumtype 자료형이 없으므로 string으로 변환해준다
	private RoleType role;
	
	@CreationTimestamp //비워놔도 자동으로 등록한 현재시간을 입력해준다
	private Timestamp cu_created;
//	
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
//	@JsonIgnoreProperties("user")
//	private List<Board> board;
	
	
}
