package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.Joinjob;
import com.zjj.entity.User;
import com.zjj.service.JobService;
import com.zjj.service.JoinjobService;
import com.zjj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    //    修改岗位状态,是否结束
    @PostMapping("/f5")
    public void F5() {
        jobService.F5Time();
    }

    //文件上传
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
//    public String uploadFile(MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
//            return "";
            return Result.fail("-1");
        }
        // 获取传过来的文件名字
        String OriginalFilename = file.getOriginalFilename();
        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName = System.currentTimeMillis() + "." + OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1);
        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String currentPath = System.getProperty("user.dir");
        String path = currentPath.replace("zjj", "") + "//my_springboot//zjj-web/src/assets/files/";
        System.out.println("path:" + path);
        File dest = new File(path + fileName);
        // 判断文件是否存在
        if (!dest.getParentFile().exists()) {
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        try {
            // 后台上传
            file.transferTo(dest);
//            return fileName;
            return Result.suc(fileName);
        } catch (Exception e) {
            e.printStackTrace();
//            return "";
            return Result.fail("-1");
        }
    }

    //新增岗位
    @PostMapping("/saveForm")
    public Result save(@RequestBody Job job) {
        return jobService.saveForm(job) ? Result.suc(job) : Result.fail();
    }

    //新增岗位
    @PostMapping("/updateForm")
    public Result updateForm(@RequestBody Job job) {
        return jobService.updateById(job) ? Result.suc(job) : Result.fail();
    }

    //模糊查询
    @PostMapping("/jobList")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String jobname = (String) param.get("jobname");

        Page<Job> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(jobname) && !"null".equals(jobname)) {
            lambdaQueryWrapper.like(Job::getJobname, jobname);
        }

        IPage result = jobService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //审核通过
    @PostMapping("/mod")
    public Result mod(@RequestBody Job job) {
        job.setStates("进行中");
        return jobService.updateById(job) ? Result.suc() : Result.fail();
    }

    //审核不通过
    @PostMapping("/modNotSuccess")
    public Result modNotSuccess(@RequestBody Job job) {
        job.setStates("未通过审核");
        return jobService.updateById(job) ? Result.suc() : Result.fail();
    }

    //删除岗位
    @PostMapping("/delJob")
    public Result delJobById(@RequestBody Job job) {
        return jobService.removeById(job.getJobid()) ? Result.suc() : Result.fail();
    }

    //下载附件
    @RequestMapping("/download")
    public Result download(String path, HttpServletResponse response) {
        String currentPath = System.getProperty("user.dir");
        path = currentPath.replace("zjj", "") + "//my_springboot//zjj-web/src/assets/files/" + path;
//        path = "D:\\IDEA_SprintBoot\\springboot-vue\\my_springboot\\zjj-web\\src\\assets\\files\\1.txt";
        try {
// path是指想要下载的文件的路径
            File file = new File(path);
            System.out.println(file.getPath());
// 获取文件名
            String filename = file.getName();
// 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            System.out.println("文件后缀名：" + ext);

// 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

// 清空response
            response.reset();
// 设置response的Header
            response.setCharacterEncoding("UTF-8");
//Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
// filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
// 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
            response.setContentType("application/json;charset=utf-8");
            outputStream.write(buffer);
            outputStream.flush();
            return Result.suc();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Result.fail();
        }
    }

    //模糊查询
    @PostMapping("/TeacherJob")
    public Result TeacherJob(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String jobname = (String) param.get("jobname");
        String tid = (String)param.get("tid");
        Page<Job> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(jobname) && !"null".equals(jobname)) {
            lambdaQueryWrapper.like(Job::getJobname, jobname);
        }

        //根据老师id查发布的工作

        lambdaQueryWrapper.eq(Job::getTid, tid);

        IPage result = jobService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }


}
