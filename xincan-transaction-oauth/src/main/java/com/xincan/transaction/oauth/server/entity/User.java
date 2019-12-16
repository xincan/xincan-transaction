package com.xincan.transaction.oauth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@Builder
public class User implements UserDetails, Serializable {

	// 此处使用snowflake分布式主键算法
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;

	@TableField("name")
	private String username;

	@TableField("password")
	private String password;

	@TableField("phone")
	private String phone;

	@TableField(exist = false)
	@JsonIgnore
	private List<Role> authorities;

	@TableField(exist = false)
	@JsonIgnore
	private List<Tenant> tenants;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
