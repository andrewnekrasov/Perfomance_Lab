package ru.nekrasov.spring_task.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usr")
@SuppressWarnings("serial")
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "user_name")
	private String name;

	private String email;

	@Column(name = "hash_password")
	private String hashPass;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashPass() {
		return hashPass;
	}

	public void setHashPass(String hashPass) {
		this.hashPass = hashPass;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public String getPassword() {
		return getHashPass();
	}

	@Override
	public String getUsername() {
		return getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
