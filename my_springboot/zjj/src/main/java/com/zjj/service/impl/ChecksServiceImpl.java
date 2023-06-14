package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Checks;
import com.zjj.mapper.ChecksMapper;
import com.zjj.service.ChecksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class ChecksServiceImpl extends ServiceImpl<ChecksMapper, Checks> implements ChecksService {
    @Autowired
    private ChecksMapper checksMapper;


    @Override
    public IPage pageCC(Page<Checks> page, String starttime, String  endtime, Wrapper wrapper) {
        return checksMapper.pageCC(page, starttime, endtime,wrapper);
    }

    @Override
    public List weekCheck(Integer studentid) {
        return checksMapper.weekCheck(studentid);
    }
    @Override
    public List monthCheck(Integer studentid) {
        return checksMapper.monthCheck(studentid);
    }


}
