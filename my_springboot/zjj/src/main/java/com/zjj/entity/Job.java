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
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位编号")
    @TableId(value = "jobId", type = IdType.AUTO)
    private Integer jobid;

    @ApiModelProperty(value = "岗位名称")
    private String jobname;

    @ApiModelProperty(value = "招聘人数")
    private Integer counts;

    @ApiModelProperty(value = "申请人数")
    private Integer applicants;

    @ApiModelProperty(value = "岗位介绍")
    private String description;

    @ApiModelProperty(value = "岗位状态")
    private String states;

    @ApiModelProperty(value = "教师id")
    @TableField("tId")
    private Integer tid;

    @ApiModelProperty(value = "岗位发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jdate;

    @ApiModelProperty(value = "结束时间")
    @TableField(value = "overTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime overTime;

    @ApiModelProperty(value = "管理id")
    @TableField(value = "adminId"  )
    private Integer adminid;

    @ApiModelProperty(value = "附件")
    private String files;

}
