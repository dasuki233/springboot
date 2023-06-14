package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Checks;
import com.zjj.entity.Job;
import com.zjj.entity.Student;
import com.zjj.entity.User;
import com.zjj.service.ChecksService;
import com.zjj.service.JobService;
import com.zjj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/check")
public class ChecksController {
    @Autowired
    private ChecksService checksService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JobService jobService;

    //模糊查询
    @PostMapping("/checkList")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name"); //学生姓名
        System.out.println("name==" + (String) param.get("name"));
        String no = (String) param.get("no"); //学生姓名
        System.out.println("no==" + (String) param.get("no"));
        String jobname = (String) param.get("jobname"); //岗位名称
        System.out.println("jobname==" + (String) param.get("jobname"));
        String starttime = (String ) param.get("starttime");
        String  endtime = (String ) param.get("endtime");
        System.out.println("starttime==" + (String) param.get("starttime"));
        System.out.println("endtime==" + (String) param.get("endtime"));

        Page<Checks> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Checks> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Checks::getName, name);
//            lambdaQueryWrapper.between(Checks::getJdate, starttime, endtime);
        }
        if (StringUtils.isNotBlank(no)) {
            lambdaQueryWrapper.like(Checks::getNo, no);
        }
        if (StringUtils.isNotBlank(jobname)) {
            lambdaQueryWrapper.like(Checks::getJobname, jobname);
        }


        IPage result = checksService.pageCC(page, starttime, endtime, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //检查是否有这个学生和岗位 通过学号和
    @PostMapping("/checkNo")
    public Result checkUser(@RequestBody Checks checks) {
        String no = checks.getNo();
        List stuNo = studentService.lambdaQuery().select(Student::getNo).eq(Student::getNo, no).list();
        if (stuNo.size() == 0) {
            return Result.fail();
        }
        return Result.suc();
    }


    @PostMapping("/save")
    public Result saveCheck(@RequestBody Checks checks) {
        if (checks.getSid() == null && checks.getJobid() == null)
            return Result.fail();
        String no = checks.getNo();
        Student student = studentService.lambdaQuery().select(Student::getSid).eq(Student::getNo, no).one();
        Checks checks1 = new Checks();
        checks1.setSid(student.getSid());
        checks1.setJobid(checks.getJobid());

        if (StringUtils.isNotBlank(checks.getLeavess()))
            checks1.setLeavess(checks.getLeavess());

        if (StringUtils.isNotBlank(checks.getCheckes()))
            checks1.setCheckes(checks.getCheckes());

        if (checks.getJdate() != null)
            checks1.setJdate(checks.getJdate());
        return checksService.save(checks1) ? Result.suc() : Result.fail();
    }

    //工作岗位groupby,用于前端下拉框
    @GetMapping("/findAllJob")
    public Result findAllJob() {
        List list = jobService.lambdaQuery().select(Job::getJobid, Job::getJobname).groupBy(Job::getJobname).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Checks checks) {
        if (checks.getSid() == null && checks.getJobid() == null)
            return Result.fail();
        String no = checks.getNo();
        Student student = studentService.lambdaQuery().select(Student::getSid).eq(Student::getNo, no).one();
        Checks checks1 = new Checks();
        checks1.setSid(student.getSid());
        checks1.setJobid(checks.getJobid());
        if (StringUtils.isNotBlank(checks.getLeavess()))
            checks1.setLeavess(checks.getLeavess());

        if (StringUtils.isNotBlank(checks.getCheckes()))
            checks1.setCheckes(checks.getCheckes());

        if (checks.getCheckid() != null)
            checks1.setCheckid(checks.getCheckid());
        return checksService.updateById(checks1) ? Result.suc() : Result.fail();
    }

    //删除
    @PostMapping("/delete")
    public Result delete(@RequestBody Checks checks) {
        return checksService.removeById(checks) ? Result.suc() : Result.fail();
    }

    //本周上班时间
    @PostMapping("/weekCheck")
    public Result weekCheck(@RequestBody Checks checks) {
        return Result.suc(checksService.weekCheck(checks.getSid()));
    }


    //本月上班时间
    @PostMapping("/monthCheck")
    public Result monthCheck(@RequestBody Checks checks) {
        return Result.suc(checksService.monthCheck(checks.getSid()));
    }
}
