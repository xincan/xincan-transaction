package com.xincan.transaction.oauth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("role")
public class Role implements GrantedAuthority {

	// 此处使用snowflake分布式主键算法
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;

	@TableField("name")
	private String name;

	@Override
	public String getAuthority() {
		return name;
	}

}
