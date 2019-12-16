package com.xincan.transaction.server.demo.entity;

import com.xincan.transaction.server.demo.annotations.HatechIdCard;
import com.xincan.transaction.server.demo.interfaces.CreateValidation;
import com.xincan.transaction.server.demo.interfaces.UpdateValidation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;


/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.server.demo.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 用户实体类
 * @Date: 2019/12/12 13:57
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank(message = "用户ID不能为空",groups = UpdateValidation.class)
    @NotNull(message = "用户ID不能为空",groups = UpdateValidation.class)
    @ApiModelProperty(value="ID", dataType = "String", example = "UUID")
    private String id;

    @NotBlank(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", dataType = "String", example = "张三")
    private String name;

    @NotBlank(message = "账号不能为空")
    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号", dataType = "String", example = "account")
    private String account;

    @NotBlank(message = "密码不能为空",groups = CreateValidation.class)
    @NotNull(message = "密码不能为空",groups = CreateValidation.class)
    @ApiModelProperty(value = "密码", dataType = "String", example = "password")
    private String password;

    @NotEmpty(message = "角色ID不能为空（集合）")
    @ApiModelProperty(value = "角色ID集合", dataType = "List", example = "['111','222']")
    private List<String> roleIdList;

    @NotEmpty(message = "角色ID不能为空（数组）")
    @ApiModelProperty(value = "角色ID数组", dataType = "Array", example = "['111','222']")
    private String [] roleIdArr;

    @NotBlank(message = "邮箱不能为空")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱", dataType = "String", example = "11@qq.com")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp ="^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @ApiModelProperty(value = "手机号", dataType = "String", example = "17099999999")
    private String mobile;

    @HatechIdCard(message = "身份证号码不合法！")
    @ApiModelProperty(value = "身份证", dataType = "String", example = "4111111111111111")
    private String idCard;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "用户状态", dataType = "Integer", example = "1")
    private Integer status;

    @NotNull(message = "出生日期不能为空")
    @ApiModelProperty(value = "出生日期", dataType = "Integer", example = "1992/09/20")
    private Date birthday;
}
