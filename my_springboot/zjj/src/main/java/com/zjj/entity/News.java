package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@ApiModel(value="News对象", description="")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "newsId", type = IdType.AUTO)
    private Integer newsid;

    @ApiModelProperty(value = "新闻标题")
    private String title;

    @ApiModelProperty(value = "新闻内容")
    private String mains;

    @ApiModelProperty(value = "附件")
    private String file;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime newsdate;


}
