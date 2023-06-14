package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Job;
import com.zjj.entity.Message;
import com.zjj.mapper.MessageMapper;
import com.zjj.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public IPage pageCC(Page<Message> page, Wrapper wrapper) {
        return messageMapper.pageCC(page, wrapper);
    }

    @Override
    public IPage studentMessage(Page<Message> page,Integer sid, Wrapper wrapper) {
        return messageMapper.studentMessage(page, sid, wrapper);
    }
}
