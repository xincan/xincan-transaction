package com.xincan.transaction.server.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 文件实体类
 * @Date: 2019/12/17 13:06
 * @Version: 1.0
 */
@ApiModel(value = "File", description = "文件实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "file")   // 指定该类映射数据库表名
public class File {

    @ApiModelProperty(value="文件ID", dataType = "String", example = "UUID")
    @NotBlank(message = "文件ID不能为空")
    @Length(message = "文件ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="文件名称", dataType = "String", required = true, example = "123.doc")
    @NotBlank(message = "文件名称不能为空")
    @Length(message = "文件名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value="文件路径", dataType = "String", required = true, example = "/a/b/123.doc")
    @NotBlank(message = "文件路径不能为空")
    @Length(message = "文件路径长度应该在{min}~{max}之间", min = 1, max = 100)
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty(value="编辑人员ID", dataType = "String", example = "UUID")
    @TableField("edit_user_id")
    private String editUserId;

    @ApiModelProperty(value="操作时间，格式：yyyy-MM-dd HH:mm:ss", dataType="String", notes="格式：yyyy-MM-dd HH:mm:ss", example = "2019-09-09 09:09:09")
    @DateTimeFormat
    @TableField(value = "edit_time")
    private LocalDateTime editTime;
}
