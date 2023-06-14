package com.zjj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Checks;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
public interface ChecksService extends IService<Checks> {
    IPage pageCC(Page<Checks> page, String starttime, String  endtime, Wrapper wrapper);

    List weekCheck(Integer studentid);

    List monthCheck(Integer studentid);
}
