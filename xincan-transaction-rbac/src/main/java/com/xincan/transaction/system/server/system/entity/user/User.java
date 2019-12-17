package com.xincan.transaction.system.server.system.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xincan.transaction.system.server.system.entity.tenant.Tenant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User implements Serializable {

	// 此处使用snowflake分布式主键算法
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;

	@TableField("name")
	private String username;

	@JsonIgnore
	@TableField("password")
	private String password;

	@TableField("age")
	private String age;

	@TableField("sex")
	private Integer sex;

	@TableField("phone")
	private String phone;

	@TableField("email")
	private String email;

	@TableField("is_admin")
	private Integer isAdmin;

	@TableField("area_id")
	private String areaId;

	@TableField("organization_id")
	private String organizationId;

	@TableField("create_time")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(exist = false)
	private List<Role> authorities;

	@TableField(exist = false)
	private List<Tenant> tenants;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
