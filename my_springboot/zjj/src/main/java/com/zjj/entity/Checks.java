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
@ApiModel(value="Check对象", description="")
public class Checks implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "checkId", type = IdType.AUTO)
    private Integer checkid;

    @ApiModelProperty(value = "job工作表外键")
    @TableField("jobId")
    private Integer jobid;

    @ApiModelProperty(value = "考勤时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jdate;

    @ApiModelProperty(value = "学生学号外键")
    @TableField("sId")
    private Integer sid;

    @ApiModelProperty(value = "班级")
    private String clazz;

    @ApiModelProperty(value = "上班时间")
    private String checkes;

    @ApiModelProperty(value = "请假/迟到时间")
    private String leavess;

    @ApiModelProperty(value = "岗位名称")
    private String jobname;

    @ApiModelProperty(value = "账号")
    private String no;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime starttime;

    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

}
