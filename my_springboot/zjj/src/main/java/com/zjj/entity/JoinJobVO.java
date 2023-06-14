package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @auther zjj
 * @date 2023/3/13 2:13
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Joinjob对象", description="")
public class JoinJobVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位申请编号")
    @TableId(value = "joinjobId", type = IdType.AUTO)
    private Integer joinjobid;

    @ApiModelProperty(value = "工作表id")
    private Integer jobid;

    @ApiModelProperty(value = "申请理由")
    private String remark;

    @ApiModelProperty(value = "日期")
    @TableField("jobDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jobdate;

    @ApiModelProperty(value = "学生外键")
    private Integer sid;

    @ApiModelProperty(value = "申请状态")
    private String states;

    @ApiModelProperty(value = "岗位名称")
    private String jobname;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "教师id")
    @TableField("tId")
    private Integer tId;

    @ApiModelProperty(value = "学号")
    private Integer no;

}
