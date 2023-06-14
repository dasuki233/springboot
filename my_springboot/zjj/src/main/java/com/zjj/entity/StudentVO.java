package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Student对象", description="")
public class StudentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "sId", type = IdType.AUTO)
    private Integer sid;

    @ApiModelProperty(value = "账号")
    private String no;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    private Integer age;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "角色 0管理员，1教师，2学生")
    private Integer roleId;

    @ApiModelProperty(value = "是否有效，Y有效，其他无效")
    @TableField("isValid")
    private String isvalid;

    @ApiModelProperty(value = "班级")
    @TableField("clazzId")
    private Integer clazzid;

    @ApiModelProperty(value = "clazzId")
    @TableField(value = "clzId")
    private Integer clzid;

    @ApiModelProperty(value = "班级")
    private String clazz;

    @ApiModelProperty(value = "院系外键")
    @TableField("cId")
    private Integer cid;

    @ApiModelProperty(value = "学院外键")
    @TableField(value = "collegesId")
    private Integer collegesid;

    @ApiModelProperty(value = "学院名字")
    @TableField("cname")
    private String cname;


}
