package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Message;
import com.zjj.entity.User;
import com.zjj.service.MenuService;
import com.zjj.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    //查询(模糊,匹配) 管理员查看留言
    @PostMapping("/msgList")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String no = (String) param.get("no");
        String title = (String) param.get("title");
        System.out.println("name==" + (String) param.get("name"));
        System.out.println("no==" + (String) param.get("no"));

        Page<Message> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Message::getName, name);
        }
        if (StringUtils.isNotBlank(no) && !"null".equals(no)) {
            lambdaQueryWrapper.like(Message::getNo, no);
        }
        if (StringUtils.isNotBlank(title) && !"null".equals(title)) {
            lambdaQueryWrapper.like(Message::getTitle, title);
        }

        IPage result = messageService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //学生查看自己的留言
    @PostMapping("/stuLookMsg")
    public Result stuLookMsg(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String title = (String) param.get("title");
        Integer sid = (Integer) param.get("sid");

        Page<Message> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(title) && !"null".equals(title)) {
            lambdaQueryWrapper.like(Message::getTitle, title);
        }


        IPage result = messageService.studentMessage(page, sid, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //回复留言
    @PostMapping("/answer")
    public Result answer(@RequestBody Message message){
        return messageService.updateById(message)?Result.suc():Result.fail();
    }

    //删除留言
    @PostMapping("/delete")
    public Result del(@RequestBody Message message){
        return messageService.removeById(message)?Result.suc():Result.fail();
    }

    //学生留言
    @PostMapping("/studentMsg")
    public Result studentMsg(@RequestBody Message message){
        return messageService.save(message)?Result.suc():Result.fail();
    }
}
