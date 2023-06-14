package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Colleges对象", description="")
public class Colleges implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学院外键")
    @TableId(value = "collegesId", type = IdType.AUTO)
    private Integer collegesid;

    @ApiModelProperty(value = "学院名字")
    private String cname;


}
