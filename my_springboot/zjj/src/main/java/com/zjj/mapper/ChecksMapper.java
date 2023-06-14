package com.zjj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Checks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Mapper
public interface ChecksMapper extends BaseMapper<Checks> {

    IPage pageCC(Page<Checks> page, String starttime, String  endtime, @Param(Constants.WRAPPER) Wrapper wrapper);

    List weekCheck(Integer studentid);

    List monthCheck(Integer studentid);
}
