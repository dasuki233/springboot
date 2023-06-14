package com.zjj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sun.reflect.generics.tree.Tree;

/**
 * <p>
 *
 * </p>
 *
 * @author zjj
 * @since 2023-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单编码")
    @TableField("menuCode")
    private String menucode;

    @ApiModelProperty(value = "菜单名字")
    @TableField("menuName")
    private String menuname;

    @ApiModelProperty(value = "菜单级别0-只有1级-1一级")
    @TableField("menuLevel")
    private String menulevel;

    @ApiModelProperty(value = "菜单的父code")
    @TableField("menuParentCode")
    private String menuparentcode;

    @ApiModelProperty(value = "点击触发的函数")
    @TableField("menuClick")
    private String menuclick;

    @ApiModelProperty(value = "权限 0管理员，1表示教师，2表示学生，可以用逗号组合使用")
    @TableField("menuRight")
    private String menuright;

    @TableField("menuComponent")
    private String menucomponent;

    @TableField("menuIcon")
    private String menuicon;

    @TableField(exist = false)
    private List childListsss;


}
