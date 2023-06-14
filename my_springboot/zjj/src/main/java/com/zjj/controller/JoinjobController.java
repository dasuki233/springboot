package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.JoinJobVO;
import com.zjj.entity.Joinjob;
import com.zjj.entity.User;
import com.zjj.service.JoinjobService;
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
@RequestMapping("/joinjob")
public class JoinjobController {

    @Autowired
    private JoinjobService joinjobService;


    //    教师查找学生 - 模糊查询
    @PostMapping("/joinjobList")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        Integer tId = (Integer) param.get("tId");
        String jobname = (String) param.get("jobname"); //岗位名称
        String name = (String) param.get("name");  //姓名
        if (tId == null)
            return Result.fail();

        Page<JoinJobVO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = joinjobService.pageCC(page, tId, jobname, name);
        System.out.println("totol===" + result.getTotal());
        return Result.suc(result.getRecords(), result.getTotal());
    }

    //删除学生的申请
    @PostMapping("/delJoinJob")
    public Result delJoinJob(@RequestBody Joinjob joinjob) {
        return joinjobService.removeById(joinjob)?Result.suc():Result.fail();
    }

//    审核学生的申请
    @PostMapping("/updateState")
    public Result updateState(@RequestBody Joinjob joinjob){
        return joinjobService.updateById(joinjob)?Result.suc():Result.fail();
    }

    //模糊查询
    @PostMapping("/StudentJobList")
    public Result TeacherJob(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String jobname = (String) param.get("jobname");
        Page<Job> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(jobname) && !"null".equals(jobname)) {
            lambdaQueryWrapper.like(Job::getJobname, jobname);
        }

        //根据老师id查发布的工作


        IPage result = joinjobService.pageCC1(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //学生岗位申请
    @PostMapping("/studentJob")
    public Result studentJob(@RequestBody Joinjob joinjob){
        return Result.suc(joinjobService.save(joinjob));
    }

    //学生查看自己的申请
    @PostMapping("/joinjobStudent")
    public Result joinjobStudent(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String jobname = (String) param.get("jobname");
        Integer sid = (Integer) param.get("sid");
        Page<JoinJobVO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = joinjobService.joinjobStudent(page, jobname, sid);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
