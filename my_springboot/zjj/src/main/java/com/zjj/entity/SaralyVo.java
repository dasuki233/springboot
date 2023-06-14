package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @auther zjj
 * @date 2023/3/10 4:54
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SaralyVo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "saralyId", type = IdType.AUTO)
    private Integer saralyid;

    @ApiModelProperty(value = "岗位表外键")
    @TableField("jobId")
    private Integer jobid;

    @ApiModelProperty(value = "学生学号外键")
    private Integer sid;

    @ApiModelProperty(value = "发放工资时间")
    private LocalDateTime sdate;

    @ApiModelProperty(value = "工资")
    private Double money;


}
