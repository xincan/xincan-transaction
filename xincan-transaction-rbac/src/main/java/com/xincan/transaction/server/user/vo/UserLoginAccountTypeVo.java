package com.xincan.transaction.server.user.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.server.user.vo
 * @ClassName: UserLoginAccountTypeVo
 * @author: wangmingshuai
 * @Description: description
 * @Date: 2019/12/17 17:11
 * @Version: 1.0
 */
@ApiModel(value = "UserLoginAccountTypeVo", description = "用户登陆账号类型")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
public class UserLoginAccountTypeVo {

    @ApiModelProperty(value="登陆类型", dataType = "String", example = "UUID")
    @NotBlank(message = "登陆类型不能为空")
    private String type;

    @ApiModelProperty(value="登陆账号", dataType = "String", example = "UUID")
    @NotBlank(message = "登陆账号不能为空")
    private String username;
}
