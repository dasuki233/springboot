package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value="Saraly对象", description="")
public class Saraly implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "saralyId", type = IdType.AUTO)
    private Integer saralyid;

    @ApiModelProperty(value = "岗位表外键")
    @TableField("jobId")
    private Integer jobid;

    @ApiModelProperty(value = "学生学号外键")
    private Integer sid;

    @ApiModelProperty(value = "发放工资时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sdate;

    @ApiModelProperty(value = "工资")
    private Double money;

    @ApiModelProperty(value = "账号")
    private String no;

    @ApiModelProperty(value = "名字")
    private String name;


    @ApiModelProperty(value = "岗位名称")
    private String jobname;

    @ApiModelProperty(value = "班级")
    private String clazz;


}
