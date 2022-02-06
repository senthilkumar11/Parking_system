package com.zoho.parking_system.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	@NotBlank
	@Size(max = 20)
	private String username;

	@Column
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Column
	@NotBlank
	@Size(max = 120)
	private String password;

	@Column
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	private Set<Role> roles=new HashSet<Role>();

	@Column
	@NotBlank
	@Size(min = 3, max = 20)
	private String lastname;

	@Column
	@NotBlank
	@Size(min = 3, max = 20)
	private String firstname;
	@Column
	@NotBlank
	@Size(min = 3, max = 20)
	private String phoneNumber;

	public User() {
	}

	public User(String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, @NotBlank @Size(min = 3, max = 20) String lastname,
			@NotBlank @Size(min = 3, max = 20) String firstname,
			@NotBlank @Size(min = 3, max = 20) String phoneNumber,Set<Role> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phoneNumber = phoneNumber;
		this.roles.addAll(roles);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
