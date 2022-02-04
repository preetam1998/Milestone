package com.example.second.models;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class User extends Timestamps {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String userName;

	@Column(nullable = false)
	private String firstName;

	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String mobileNumber;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String cAddress;

	private String pAddress;

	@Column(nullable = false)
	private boolean isActive;

	@Column(nullable = false)
	private String roles;

//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name="wallet_id", referencedColumnName = "id")
//	private UserWallet wallet;

}