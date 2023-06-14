package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.Joinjob;
import com.zjj.entity.Saraly;
import com.zjj.entity.User;
import com.zjj.service.SaralyService;
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
@RequestMapping("/saraly")
public class SaralyController {
    @Autowired
    private SaralyService saralyService;

    //查询(模糊,匹配) 工资发放情况
    @PostMapping("/saralyList")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name"); //学生姓名
        System.out.println("name==" + (String) param.get("name"));
        String no = (String) param.get("no"); //学生姓名
        System.out.println("no==" + (String) param.get("no"));
        String jobname = (String) param.get("jobname"); //岗位名称
        System.out.println("jobname==" + (String) param.get("jobname"));
        String starttime = (String) param.get("starttime");
        String endtime = (String) param.get("endtime");


        Page<Saraly> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Saraly> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Saraly::getName, name);
        }
        if (StringUtils.isNotBlank(no)) {
            lambdaQueryWrapper.like(Saraly::getNo, no);
        }
        if (StringUtils.isNotBlank(jobname)) {
            lambdaQueryWrapper.like(Saraly::getJobname, jobname);
        }
        if (StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)){
            lambdaQueryWrapper.between(Saraly::getSdate, starttime, endtime);
        }


        IPage result = saralyService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //查询(模糊,匹配) 发放工资界面用
    @PostMapping("/paySaralyList")
    public Result paySaralyList(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name"); //学生姓名
        System.out.println("name==" + (String) param.get("name"));
        String no = (String) param.get("no"); //学生姓名
        System.out.println("no==" + (String) param.get("no"));
        String jobname = (String) param.get("jobname"); //岗位名称
        System.out.println("jobname==" + (String) param.get("jobname"));

        Page<Saraly> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Saraly> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Saraly::getName, name);
        }
        if (StringUtils.isNotBlank(no)) {
            lambdaQueryWrapper.like(Saraly::getNo, no);
        }
        if (StringUtils.isNotBlank(jobname)) {
            lambdaQueryWrapper.like(Saraly::getJobname, jobname);
        }


        IPage result = saralyService.PaysalaryList(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //发放工资
    @PostMapping("/pay")
    public Result pay(@RequestBody Saraly saraly) {
        if (saraly.getMoney() <= 0 || saraly.getMoney() == null){
            return Result.fail();
        }
        return saralyService.save(saraly)?Result.suc():Result.fail();
//        return saralyService.pay(saraly.getMoney(), saraly.getSaralyid())?Result.suc():Result.fail();
    }

    //学生查看自己工资情况
    @PostMapping("/studentSalary")
    public Result studentSalary(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String jobname = (String) param.get("jobname"); //岗位名称
        String starttime = (String) param.get("starttime");
        String endtime = (String) param.get("endtime");
        Integer sid = (Integer) param.get("sid");


        Page<Saraly> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Saraly> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(jobname)) {
            lambdaQueryWrapper.like(Saraly::getJobname, jobname);
        }
        if (StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)){
            lambdaQueryWrapper.between(Saraly::getSdate, starttime, endtime);
        }




        IPage result = saralyService.studentSalary(page, sid,lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

//    @PostMapping("/pay")
//    public Result pay(@RequestBody Saraly saraly) {
//        if (saraly.getMoney() <= 0 || saraly.getMoney() == null){
//            return Result.fail();
//        }
//        return saralyService.pay(saraly.getMoney(), saraly.getSaralyid())?Result.suc():Result.fail();
//    }
}
