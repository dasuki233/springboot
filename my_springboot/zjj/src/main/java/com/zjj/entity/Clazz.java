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
@ApiModel(value="Clazz对象", description="")
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "clazzId")
    @TableId(value = "clzId", type = IdType.AUTO)
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
    private String cname;


}
