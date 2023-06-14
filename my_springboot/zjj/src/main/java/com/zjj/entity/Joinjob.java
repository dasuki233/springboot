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
@ApiModel(value="Joinjob对象", description="")
public class Joinjob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位申请编号")
    @TableId(value = "joinjobId", type = IdType.AUTO)
    private Integer joinjobid;

    @ApiModelProperty(value = "工作表id")
    private Integer jobid;

    @ApiModelProperty(value = "申请理由")
    private String remark;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("jobDate")
    private LocalDateTime jobdate;

    @ApiModelProperty(value = "学生外键")
    private Integer sid;


    @ApiModelProperty(value = "申请状态")
    private String states;


}
