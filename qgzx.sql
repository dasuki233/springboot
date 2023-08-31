/*
 Navicat Premium Data Transfer

 Source Server         : springboot
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : qgzx

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 01/09/2023 04:15:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '123', '管理员', '123123', 0);
INSERT INTO `admin` VALUES (2, 'admin', '管理员', 'admin', 0);

-- ----------------------------
-- Table structure for checks
-- ----------------------------
DROP TABLE IF EXISTS `checks`;
CREATE TABLE `checks`  (
  `checkId` int NOT NULL AUTO_INCREMENT,
  `jobId` int NOT NULL COMMENT 'job工作表外键',
  `jdate` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '考勤时间',
  `sId` int NOT NULL COMMENT '学生学号外键',
  `checkes` double(255, 0) NULL DEFAULT NULL COMMENT '上班时间',
  `leavess` double(255, 0) NULL DEFAULT NULL COMMENT '请假/迟到\r\n',
  PRIMARY KEY (`checkId`, `jobId`, `sId`) USING BTREE,
  INDEX `check_job`(`jobId`) USING BTREE,
  INDEX `check_sno`(`sId`) USING BTREE,
  CONSTRAINT `check_job` FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `check_sno` FOREIGN KEY (`sId`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of checks
-- ----------------------------
INSERT INTO `checks` VALUES (10, 11, '2023-03-14 00:17:38', 24, 3, 1);
INSERT INTO `checks` VALUES (14, 10, '2023-03-15 00:17:39', 24, 3, 0);
INSERT INTO `checks` VALUES (15, 10, '2023-03-13 08:02:37', 14, 2, 1);
INSERT INTO `checks` VALUES (16, 106, '2023-03-13 08:02:37', 14, 2, 1);
INSERT INTO `checks` VALUES (17, 107, '2023-03-12 08:02:37', 14, 2, 1);
INSERT INTO `checks` VALUES (18, 11, '2023-03-21 04:28:03', 14, 3, 1);
INSERT INTO `checks` VALUES (19, 106, '2023-03-20 08:02:37', 14, 2, 1);
INSERT INTO `checks` VALUES (20, 11, '2023-03-16 00:17:40', 24, 2, 1);
INSERT INTO `checks` VALUES (21, 11, '2023-03-17 07:15:43', 24, 1, 1);
INSERT INTO `checks` VALUES (22, 11, '2023-03-18 00:17:44', 24, 2, 1);
INSERT INTO `checks` VALUES (23, 106, '2023-03-21 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (24, 106, '2023-03-21 08:02:37', 14, 14, 1);
INSERT INTO `checks` VALUES (25, 106, '2023-03-22 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (26, 106, '2023-03-22 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (27, 106, '2023-03-23 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (28, 106, '2023-03-24 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (29, 106, '2023-03-25 08:02:37', 14, 3, 1);
INSERT INTO `checks` VALUES (30, 106, '2023-03-20 13:18:04', 14, 22, 1);
INSERT INTO `checks` VALUES (32, 124, '2023-05-11 18:26:20', 12, 3, 0);
INSERT INTO `checks` VALUES (33, 117, '2023-05-11 18:30:49', 12, 5, 0);
INSERT INTO `checks` VALUES (34, 117, '2023-05-11 18:31:18', 12, 2, 0);
INSERT INTO `checks` VALUES (35, 124, '2023-05-11 18:31:46', 12, 3, 1);
INSERT INTO `checks` VALUES (36, 117, '2023-05-08 00:00:00', 12, 3, 1);
INSERT INTO `checks` VALUES (37, 117, '2023-05-10 00:00:00', 12, 2, 0);

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `clzId` int NOT NULL AUTO_INCREMENT COMMENT 'clazzId',
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级',
  `cId` int NULL DEFAULT NULL COMMENT '院系外键',
  PRIMARY KEY (`clzId`) USING BTREE,
  INDEX `coId`(`cId`) USING BTREE,
  CONSTRAINT `coId` FOREIGN KEY (`cId`) REFERENCES `colleges` (`collegesId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (1, '英语(经贸翻译方向)', 4);
INSERT INTO `clazz` VALUES (2, '英语(经贸翻译方向)', 4);
INSERT INTO `clazz` VALUES (3, '英语(经贸翻译方向)', 4);
INSERT INTO `clazz` VALUES (4, '英语(经贸翻译方向)', 4);
INSERT INTO `clazz` VALUES (5, '市场营销(专升本)', 5);
INSERT INTO `clazz` VALUES (6, '市场营销', 5);
INSERT INTO `clazz` VALUES (7, '会展经济与管理', 5);
INSERT INTO `clazz` VALUES (8, '工商管理(专升本)', 5);
INSERT INTO `clazz` VALUES (9, '工商管理', 5);
INSERT INTO `clazz` VALUES (10, '旅游管理（专升本）', 5);
INSERT INTO `clazz` VALUES (11, '旅游管理', 5);
INSERT INTO `clazz` VALUES (12, '人力资源管理（专升本）', 5);
INSERT INTO `clazz` VALUES (13, '人力资源管理', 5);
INSERT INTO `clazz` VALUES (15, '经济学（拔尖人才）', 1);
INSERT INTO `clazz` VALUES (16, '农林经济管理（专升本）', 1);
INSERT INTO `clazz` VALUES (17, '土地资源管理（专升本）', 1);
INSERT INTO `clazz` VALUES (18, '土地资源管理', 1);
INSERT INTO `clazz` VALUES (19, '商务经济学', 1);
INSERT INTO `clazz` VALUES (20, '国际经济与贸易(专升本)', 1);
INSERT INTO `clazz` VALUES (21, '国际经济与贸易', 1);
INSERT INTO `clazz` VALUES (22, '国际商务(专升本)', 1);
INSERT INTO `clazz` VALUES (23, '国际商务', 1);
INSERT INTO `clazz` VALUES (24, '数字经济', 1);
INSERT INTO `clazz` VALUES (25, '财政学（专升本）', 2);
INSERT INTO `clazz` VALUES (26, '财政学', 2);
INSERT INTO `clazz` VALUES (27, '税收学（专升本）', 2);
INSERT INTO `clazz` VALUES (28, '税收学', 2);
INSERT INTO `clazz` VALUES (29, '税收学(航信创新)', 2);
INSERT INTO `clazz` VALUES (30, '劳动与社会保障（专升本）', 2);
INSERT INTO `clazz` VALUES (31, '劳动与社会保障', 2);
INSERT INTO `clazz` VALUES (32, '公共事业管理（专升本）', 2);
INSERT INTO `clazz` VALUES (33, '公共事业管理（专升本）（公共危机管理）', 2);
INSERT INTO `clazz` VALUES (34, '公共事业管理', 2);
INSERT INTO `clazz` VALUES (35, '金融学', 3);
INSERT INTO `clazz` VALUES (36, '投资学（专升本）', 3);
INSERT INTO `clazz` VALUES (37, '投资学', 3);
INSERT INTO `clazz` VALUES (38, '保险学（专升本）', 3);
INSERT INTO `clazz` VALUES (39, '保险学', 3);
INSERT INTO `clazz` VALUES (40, '金融工程', 3);
INSERT INTO `clazz` VALUES (41, '金融科技', 3);
INSERT INTO `clazz` VALUES (42, '会计学(专升本)', 6);
INSERT INTO `clazz` VALUES (43, '会计学', 6);
INSERT INTO `clazz` VALUES (44, '审计学', 6);
INSERT INTO `clazz` VALUES (45, '财务管理（专升本）', 6);
INSERT INTO `clazz` VALUES (46, '财务管理', 6);
INSERT INTO `clazz` VALUES (47, '资产评估（专升本）', 6);
INSERT INTO `clazz` VALUES (48, '资产评估', 6);
INSERT INTO `clazz` VALUES (49, '会计学(东盟国际会计)', 6);
INSERT INTO `clazz` VALUES (50, '广告学（专升本）', 8);
INSERT INTO `clazz` VALUES (51, '广告学', 8);
INSERT INTO `clazz` VALUES (52, '视觉传达设计', 8);
INSERT INTO `clazz` VALUES (53, '环境设计', 8);
INSERT INTO `clazz` VALUES (54, '新闻学', 8);
INSERT INTO `clazz` VALUES (55, '网络与新媒体', 8);
INSERT INTO `clazz` VALUES (56, '法学', 10);
INSERT INTO `clazz` VALUES (57, '法学(东盟民商法)', 10);
INSERT INTO `clazz` VALUES (58, '法学(法务会计)', 10);
INSERT INTO `clazz` VALUES (59, '法学(金融法)', 10);
INSERT INTO `clazz` VALUES (60, '体育经济与管理（专升本）', 11);
INSERT INTO `clazz` VALUES (61, '体育经济与管理', 11);
INSERT INTO `clazz` VALUES (62, '国际商务（中美）', 12);
INSERT INTO `clazz` VALUES (63, '会计学(中澳)', 12);
INSERT INTO `clazz` VALUES (64, '工程管理(专升本)', 13);
INSERT INTO `clazz` VALUES (65, '工程管理', 13);
INSERT INTO `clazz` VALUES (66, '物流管理(专升本)', 13);
INSERT INTO `clazz` VALUES (67, '物流管理', 13);
INSERT INTO `clazz` VALUES (68, '房地产开发与管理（专升本）', 13);
INSERT INTO `clazz` VALUES (69, '房地产开发与管理', 13);
INSERT INTO `clazz` VALUES (70, '人文地理与城乡规划', 13);
INSERT INTO `clazz` VALUES (71, '工程造价（专升本）', 13);
INSERT INTO `clazz` VALUES (72, '工程造价', 13);
INSERT INTO `clazz` VALUES (73, '统计学', 17);
INSERT INTO `clazz` VALUES (74, '经济统计学', 17);
INSERT INTO `clazz` VALUES (75, '应用统计学', 17);
INSERT INTO `clazz` VALUES (76, '电子商务(专升本)', 14);
INSERT INTO `clazz` VALUES (77, '电子商务', 14);
INSERT INTO `clazz` VALUES (78, '信息管理与信息系统（专升本）', 14);
INSERT INTO `clazz` VALUES (79, '信息管理与信息系统', 14);
INSERT INTO `clazz` VALUES (80, '计算机科学与技术（专升本）', 14);
INSERT INTO `clazz` VALUES (81, '计算机科学与技术', 14);
INSERT INTO `clazz` VALUES (82, '数字媒体技术（专升本）', 14);
INSERT INTO `clazz` VALUES (83, '数字媒体技术', 14);
INSERT INTO `clazz` VALUES (84, '数据科学与大数据技术（专升本）', 14);
INSERT INTO `clazz` VALUES (85, '数据科学与大数据技术', 14);
INSERT INTO `clazz` VALUES (86, '数字媒体技术（中外合作办学）', 14);
INSERT INTO `clazz` VALUES (87, '人工智能', 14);
INSERT INTO `clazz` VALUES (88, '信息与计算科学', 15);
INSERT INTO `clazz` VALUES (89, '金融数学', 15);
INSERT INTO `clazz` VALUES (90, '数学与应用数学', 15);
INSERT INTO `clazz` VALUES (91, '金融数学（中外合作办学）', 15);

-- ----------------------------
-- Table structure for colleges
-- ----------------------------
DROP TABLE IF EXISTS `colleges`;
CREATE TABLE `colleges`  (
  `collegesId` int NOT NULL AUTO_INCREMENT COMMENT '学院外键',
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院名字',
  PRIMARY KEY (`collegesId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of colleges
-- ----------------------------
INSERT INTO `colleges` VALUES (1, '经济与贸易学院');
INSERT INTO `colleges` VALUES (2, '财政与公共管理学院');
INSERT INTO `colleges` VALUES (3, '金融与保险学院');
INSERT INTO `colleges` VALUES (4, '商务外国语学院');
INSERT INTO `colleges` VALUES (5, '工商管理学院');
INSERT INTO `colleges` VALUES (6, '会计与审计学院');
INSERT INTO `colleges` VALUES (7, '马克思主义学院');
INSERT INTO `colleges` VALUES (8, '新闻与文化传播学院');
INSERT INTO `colleges` VALUES (9, '信息与统计学院');
INSERT INTO `colleges` VALUES (10, '法学院');
INSERT INTO `colleges` VALUES (11, '体育经济与管理学院');
INSERT INTO `colleges` VALUES (12, '国际教育学院');
INSERT INTO `colleges` VALUES (13, '管理科学与工程学院');
INSERT INTO `colleges` VALUES (14, '大数据与人工智能学院');
INSERT INTO `colleges` VALUES (15, '数学与数量经济学院');
INSERT INTO `colleges` VALUES (17, '中国-东盟统计学院');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `jobId` int NOT NULL AUTO_INCREMENT COMMENT '岗位编号',
  `jobname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `counts` int NULL DEFAULT NULL COMMENT '招聘人数',
  `applicants` int NULL DEFAULT NULL COMMENT '申请人数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位介绍',
  `states` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未审核' COMMENT '岗位状态',
  `tId` int NULL DEFAULT NULL COMMENT '教师id',
  `jdate` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '岗位发布时间',
  `overTime` datetime(0) NULL DEFAULT NULL COMMENT '截止时间',
  `adminId` int NULL DEFAULT NULL COMMENT '管理id',
  `files` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件',
  PRIMARY KEY (`jobId`, `jobname`) USING BTREE,
  INDEX `id`(`jobId`) USING BTREE,
  INDEX `teacher_id`(`tId`) USING BTREE,
  INDEX `admin_id`(`adminId`) USING BTREE,
  CONSTRAINT `admin_id` FOREIGN KEY (`adminId`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teacher_id` FOREIGN KEY (`tId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (10, '图书馆1', 2, NULL, '图书馆上班,工作轻松', '进行中', NULL, '2023-03-07 18:19:54', '2023-11-11 00:00:00', 1, NULL);
INSERT INTO `job` VALUES (11, '图书馆2', 2, NULL, '图书馆上班,工作轻松', '未通过审核', NULL, '2023-03-07 18:23:29', '2023-11-11 00:00:00', 1, '1.png');
INSERT INTO `job` VALUES (106, '食堂1', 1, NULL, '主要负责饭点帮助点单', '进行中', NULL, '2023-03-09 02:49:50', '2023-03-15 16:00:00', 1, NULL);
INSERT INTO `job` VALUES (107, '食堂2', 2, NULL, '主要负责饭点帮助点单', '进行中', NULL, '2023-03-09 02:50:28', '2023-03-30 16:00:00', 1, '1.png');
INSERT INTO `job` VALUES (110, '办公室1', 1, NULL, '帮助老师整理文档,完成表格统计之类', '进行中', NULL, '2023-03-09 02:52:06', '2023-03-01 16:00:00', 1, '1.png');
INSERT INTO `job` VALUES (112, '办公室2', 3, 1, '帮助老师整理文档,完成表格统计之类', '进行中', NULL, '2023-03-09 21:24:41', NULL, NULL, NULL);
INSERT INTO `job` VALUES (114, '办公室3', 2, NULL, '帮助老师整理文档,完成表格统计之类', '进行中', NULL, '2023-03-09 21:46:36', '2023-03-30 16:00:00', 1, '1678369862203.txt');
INSERT INTO `job` VALUES (115, '办公室4', 2, NULL, '帮助老师整理文档,完成表格统计之类', '进行中', NULL, '2023-03-09 21:48:04', '2023-03-01 16:00:00', 1, '1678369862203.txt');
INSERT INTO `job` VALUES (116, '办公室5', 1, NULL, '帮助老师整理文档,完成表格统计之类', '未审核', NULL, '2023-03-09 21:51:02', '2023-03-01 16:00:00', 1, '1678369862203.txt');
INSERT INTO `job` VALUES (117, '办公室6', 2, NULL, '帮助老师整理文档,完成表格统计之类', '未审核', 16, '2023-03-10 00:20:09', '2023-03-02 00:00:00', 1, '');
INSERT INTO `job` VALUES (118, '办公室7', 2, NULL, '帮助老师整理文档,完成表格统计之类', '未审核', NULL, '2023-03-10 00:30:04', '2023-03-10 00:00:00', 1, '1678379404030.txt');
INSERT INTO `job` VALUES (119, '图书馆5', 2, NULL, '图书馆上班,工作轻松', '未通过审核', 75, '2023-03-11 02:02:17', '2023-03-11 04:02:08', NULL, '1678481380945.txt');
INSERT INTO `job` VALUES (122, '图书馆3', 2, NULL, '图书馆上班,工作轻松', '未审核', NULL, '2023-03-11 04:17:46', '2023-03-11 02:02:08', NULL, NULL);
INSERT INTO `job` VALUES (124, '图书馆6', 2, NULL, '图书馆上班,工作轻松', '进行中', 16, '2023-03-11 04:20:16', '2023-03-11 02:02:08', NULL, NULL);
INSERT INTO `job` VALUES (125, '图书馆7', 2, NULL, '图书馆上班,工作轻松', '未审核', 75, '2023-03-11 04:40:09', '2023-03-24 00:00:00', NULL, '1678480809940.txt');
INSERT INTO `job` VALUES (127, '图书馆8', 2, NULL, '图书馆上班,工作轻松', '未审核', 75, '2023-03-11 04:43:16', '2023-03-09 00:00:00', NULL, '1678480996610.txt');

-- ----------------------------
-- Table structure for joinjob
-- ----------------------------
DROP TABLE IF EXISTS `joinjob`;
CREATE TABLE `joinjob`  (
  `joinjobId` int NOT NULL AUTO_INCREMENT COMMENT '岗位申请编号',
  `jobid` int NOT NULL COMMENT '工作表id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `jobDate` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '申请日期',
  `sid` int NOT NULL COMMENT '学生外键',
  `states` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '未审核' COMMENT '申请状态',
  PRIMARY KEY (`joinjobId`) USING BTREE,
  INDEX `job_id`(`jobid`) USING BTREE,
  INDEX `job_stu_id`(`sid`) USING BTREE,
  CONSTRAINT `job_id` FOREIGN KEY (`jobid`) REFERENCES `job` (`jobId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `job_stu_id` FOREIGN KEY (`sid`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of joinjob
-- ----------------------------
INSERT INTO `joinjob` VALUES (3, 124, '希望能获得这份工作', '2023-03-22 05:17:46', 15, '审核已通过');
INSERT INTO `joinjob` VALUES (4, 124, '希望能获得这份工作', '2023-05-11 18:23:53', 15, '审核已通过');
INSERT INTO `joinjob` VALUES (5, 124, '希望能获得这份工作', '2023-05-11 20:28:49', 15, '审核已通过');
INSERT INTO `joinjob` VALUES (6, 124, '希望能获得这份工作', '2023-03-22 05:17:50', 15, '未审核');
INSERT INTO `joinjob` VALUES (7, 124, '希望能获得这份工作', '2023-03-22 05:17:50', 15, '未审核');
INSERT INTO `joinjob` VALUES (8, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (9, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (10, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (11, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (12, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (13, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (14, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (15, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (16, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (17, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (18, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (19, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (20, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (21, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (22, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (23, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (24, 124, '希望能获得这份工作', '2023-03-22 05:17:52', 15, '未审核');
INSERT INTO `joinjob` VALUES (25, 106, '我有能力应对工作中的各种困难', '2023-03-22 05:17:16', 16, '未审核');
INSERT INTO `joinjob` VALUES (26, 106, '我有能力应对工作中的各种困难', '2023-03-22 05:17:20', 19, '未审核');
INSERT INTO `joinjob` VALUES (27, 106, '我有能力应对工作中的各种困难', '2023-03-22 05:17:20', 14, '未审核');
INSERT INTO `joinjob` VALUES (28, 107, '一定能完成所有工作', '2023-03-22 05:17:33', 14, '未审核');
INSERT INTO `joinjob` VALUES (29, 114, '我想申请这个岗位', '2023-03-22 05:18:21', 14, '审核已通过');
INSERT INTO `joinjob` VALUES (30, 124, '我想申请这个岗位', '2023-03-22 01:34:21', 14, '审核已通过');
INSERT INTO `joinjob` VALUES (31, 115, '我想申请这个岗位', '2023-03-22 05:18:22', 14, '未审核');
INSERT INTO `joinjob` VALUES (32, 124, '我想加入', '2023-05-11 18:24:46', 12, '审核已通过');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单级别0-只有1级-1一级',
  `menuParentCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限 0管理员，1表示教师，2表示学生，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '无所谓',
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'icon',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '岗位管理', '1', NULL, 'Admin', '0', NULL, 'el-icon-s-cooperation');
INSERT INTO `menu` VALUES (2, '001', '新增岗位', NULL, '1', 'AddPosition', '0', 'admin/positionManagement/AddPosition', 'el-icon-user-solid');
INSERT INTO `menu` VALUES (3, '001', '岗位审查', NULL, '1', 'PostReview', '0', 'admin/positionManagement/PostReview', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (4, '002', '新闻公告管理', '2', NULL, '', '0', '', 'el-icon-message-solid');
INSERT INTO `menu` VALUES (5, '002', '发布公告', NULL, '2', 'News', '0', 'admin/newsManagement/News', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (6, '002', '公告管理', NULL, '2', 'NewsManagement', '0', 'admin/newsManagement/NewsManagement', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (7, '003', '岗位工资管理', '3', NULL, NULL, '0', '', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (8, '003', '发放工资', NULL, '3', 'Payoff', '0', 'admin/salaryAdministration/Payoff', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (9, '004', '工资发放情况', NULL, '3', 'SararyUpdate', '0', 'admin/salaryAdministration/SalaryUpdate', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (10, '004', '人员管理', '4', NULL, NULL, '0', NULL, 'el-icon-s-custom');
INSERT INTO `menu` VALUES (11, '004', '教师管理', NULL, '4', 'TeacherManagement', '0', 'admin/peopleManagement/TeacherManagement', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (12, '004', '学生管理', NULL, '4', 'StudentManagement', '0', 'admin/peopleManagement/StudentManagement', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (13, '013', '班级管理', '10', NULL, 'ClazzManagement', '0', 'admin/clazzManagement/ClazzManagement', 'el-icon-s-management');
INSERT INTO `menu` VALUES (14, '014', '考勤管理', '11', NULL, 'CheckManagement', '0', 'admin/checkManagement/CheckManagement', 'el-icon-circle-check');
INSERT INTO `menu` VALUES (15, '015', '留言管理', '12', NULL, 'Msg', '0', 'admin/msg/Msg', 'el-icon-s-comment');
INSERT INTO `menu` VALUES (16, '016', '新闻公告', '66', NULL, 'News', '1', 'Teacher/news/News', 'el-icon-message-solid');
INSERT INTO `menu` VALUES (17, '005', '岗位管理', '1', NULL, NULL, '1', '', 'el-icon-s-cooperation');
INSERT INTO `menu` VALUES (18, '005', '新增岗位', NULL, '1', 'AddPosition', '1', 'Teacher/positionManagement/AddPosition', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (19, '005', '我发布的岗位', NULL, '1', 'MyPosition', '1', 'Teacher/positionManagement/MyPosition', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (20, '005', '查看学生申请岗位', NULL, '1', 'CheckPosition', '1', 'Teacher/positionManagement/CheckPosition', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (21, '021', '考勤管理', '13', NULL, 'CheckManagement', '1', 'Teacher/checkManagement/CheckManagement', 'el-icon-circle-check');
INSERT INTO `menu` VALUES (22, '022', '修改个人资料', '14', NULL, 'EditInformation', '1', 'Teacher/edit/EditInformation', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (23, '023', '新闻公告', '15', NULL, 'News', '2', 'Student/news/News', 'el-icon-message-solid');
INSERT INTO `menu` VALUES (24, '024', '考勤情况', '16', NULL, 'Check', '2', 'Student/check/Check', 'el-icon-circle-check');
INSERT INTO `menu` VALUES (25, '025', '岗位申请', NULL, '2', 'JoinJob', '2', 'Student/job/JoinJob', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (26, '026', '我的工资', '18', NULL, 'Sarary', '2', 'Student/sarary/Sarary', 'el-icon-s-ticket');
INSERT INTO `menu` VALUES (27, '006', '在线留言', '1', NULL, NULL, '2', '', 'el-icon-s-comment');
INSERT INTO `menu` VALUES (28, '006', '留言', NULL, '1', 'Msg', '2', 'Student/msg/Msg', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (29, '006', '我的留言', NULL, '1', NULL, '2', 'Student/msg/MyMsg', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (30, '028', '我的申请', NULL, '2', 'JoinJob', '2', 'Student/job/MyJoinJob', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (31, '007', '岗位信息', '2', NULL, NULL, '2', NULL, 'el-icon-s-goods');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留言标题',
  `text` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `answer` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `sid` int NOT NULL COMMENT '留言学生学号',
  `msgDate` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `answerDate` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`, `sid`) USING BTREE,
  INDEX `stu_id_msg`(`sid`) USING BTREE,
  CONSTRAINT `stu_id_msg` FOREIGN KEY (`sid`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, ' 关于工资', '是一个月450元吗', '是根据实际工作时间来决定的s', 12, '2023-03-11 00:02:54', '2023-03-22 01:43:16');
INSERT INTO `message` VALUES (3, 'test1', 'test', '可以的哦', 14, '2023-03-22 03:14:46', '2023-03-22 03:44:18');
INSERT INTO `message` VALUES (5, 'test', 'test', NULL, 14, '2023-03-22 03:43:59', NULL);
INSERT INTO `message` VALUES (6, '图书馆', '123', NULL, 12, '2023-05-11 20:30:17', NULL);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `newsId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻标题',
  `mains` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻内容',
  `file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件',
  `newsdate` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  PRIMARY KEY (`newsId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, '关于组织我校2023届毕业生参加春季线下双选会的通知', '各教学院：\n\n为了给毕业生提供更多就业岗位，促进毕业生更高质量、更充分就业，我校将举办2023届毕业生春季线下双选会，届时将有80余家用人单位参会（详细名单见附件），请各教学院积极组织2023届毕业生参加，具体安排如下：\n\n一、时间\n\n2023年3月11日9：00—12:00（星期六）。\n\n二、地点\n\n广西财经学院相思湖校区图书馆广场（正大门对面）。\n\n三、注意事项\n\n（一）宣传发动：请各教学院把双选会信息传达给全体学生。\n\n（二）高度重视：请各教学院积极组织本学院毕业生参会。\n\n（三）安全第一：请各教学院务必提醒毕业生牢固树立“安全第一”的理念，做好个人健康监测，遵守会场秩序，注意求职期间的财产安全和人身安全，并做好相关疫情防控措施。\n\n欢迎广大师生参加！\n\n', '1678383039007.txt', '2023-02-16 18:35:06');
INSERT INTO `news` VALUES (4, '新的通知', '新内容', '1678383039007.txt', '2023-03-10 01:25:40');
INSERT INTO `news` VALUES (5, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 01:25:45');
INSERT INTO `news` VALUES (10, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:02:42');
INSERT INTO `news` VALUES (11, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:02:45');
INSERT INTO `news` VALUES (12, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:02:51');
INSERT INTO `news` VALUES (13, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:02:54');
INSERT INTO `news` VALUES (14, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:01');
INSERT INTO `news` VALUES (15, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:01');
INSERT INTO `news` VALUES (16, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:01');
INSERT INTO `news` VALUES (17, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:01');
INSERT INTO `news` VALUES (18, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (19, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (20, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (21, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (22, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (23, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (24, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:02');
INSERT INTO `news` VALUES (25, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (26, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (27, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (28, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (29, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (30, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:03');
INSERT INTO `news` VALUES (31, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:04');
INSERT INTO `news` VALUES (32, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:04');
INSERT INTO `news` VALUES (33, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:04');
INSERT INTO `news` VALUES (34, '新的通知', '新内容111', '1678383039007.txt', '2023-03-10 03:03:04');

-- ----------------------------
-- Table structure for saraly
-- ----------------------------
DROP TABLE IF EXISTS `saraly`;
CREATE TABLE `saraly`  (
  `saralyId` int NOT NULL AUTO_INCREMENT,
  `jobId` int NOT NULL COMMENT '岗位表外键',
  `sid` int NOT NULL COMMENT '学生学号外键',
  `sdate` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发放工资时间',
  `money` double NULL DEFAULT 0 COMMENT '工资',
  PRIMARY KEY (`saralyId`, `jobId`, `sid`) USING BTREE,
  INDEX `money_stu_id`(`sid`) USING BTREE,
  INDEX `job_id_stu`(`jobId`) USING BTREE,
  CONSTRAINT `job_id_stu` FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `money_stu_id` FOREIGN KEY (`sid`) REFERENCES `student` (`sId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of saraly
-- ----------------------------
INSERT INTO `saraly` VALUES (2, 10, 12, '2023-05-11 18:38:23', 1200);
INSERT INTO `saraly` VALUES (3, 106, 19, '2023-03-22 01:37:44', 1897);
INSERT INTO `saraly` VALUES (6, 124, 15, '2023-03-22 02:15:49', 22);
INSERT INTO `saraly` VALUES (7, 114, 14, '2023-03-22 02:16:14', 222);
INSERT INTO `saraly` VALUES (8, 114, 14, '2023-03-22 02:46:54', 22);
INSERT INTO `saraly` VALUES (9, 114, 14, '2023-03-22 02:46:56', 33);
INSERT INTO `saraly` VALUES (10, 114, 14, '2023-03-22 02:46:57', 444);
INSERT INTO `saraly` VALUES (11, 124, 14, '2023-03-24 02:46:59', 222);
INSERT INTO `saraly` VALUES (12, 124, 15, '2023-03-22 03:57:07', 22);
INSERT INTO `saraly` VALUES (13, 124, 14, '2023-03-22 03:57:20', 2222);
INSERT INTO `saraly` VALUES (14, 114, 14, '2023-03-22 03:57:37', 1111);
INSERT INTO `saraly` VALUES (15, 124, 15, '2023-05-11 17:56:25', 450);
INSERT INTO `saraly` VALUES (16, 124, 15, '2023-05-11 20:26:34', 456);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sId` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `age` int NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` int NULL DEFAULT 2 COMMENT '角色 0管理员，1教师，2学生',
  `isValid` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否有效，Y有效，其他无效',
  `clazzId` int NULL DEFAULT NULL COMMENT '班级',
  PRIMARY KEY (`sId`) USING BTREE,
  INDEX `no`(`no`) USING BTREE,
  INDEX `clz_id`(`clazzId`) USING BTREE,
  CONSTRAINT `clz_id` FOREIGN KEY (`clazzId`) REFERENCES `clazz` (`clzId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (12, '210235712', '林宥嘉', '123123123', 19, 1, '13423515435', 2, 'Y', 51);
INSERT INTO `student` VALUES (14, '11235123', '林芝', 'qq23ww23', 22, 1, '13412341234', 2, 'Y', 24);
INSERT INTO `student` VALUES (15, '256132623', '小红', '112312414123', 32, 0, '13412341231', 2, 'Y', 80);
INSERT INTO `student` VALUES (16, '12341241', '小绿', '1231231231', 22, 0, '13412341231', 2, 'Y', 33);
INSERT INTO `student` VALUES (19, '235673456', '小紫', '123123123', 19, 0, '13412341234', 2, 'Y', 49);
INSERT INTO `student` VALUES (21, '151356166', '小黄', '123321321', 21, 1, '13412341234', 2, 'Y', 41);
INSERT INTO `student` VALUES (22, '351367125', '小蓝', '123123123', 20, 0, '13412341234', 2, 'Y', 5);
INSERT INTO `student` VALUES (24, '1', 'q', 'q', 2, 1, NULL, 2, 'Y', NULL);
INSERT INTO `student` VALUES (25, 'admin', '小明', '1', 11, 1, NULL, 1, 'Y', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` int NULL DEFAULT 1 COMMENT '角色 0超级管理员，1管理员，2普通账号',
  `isValid` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否有效，Y有效，其他无效',
  `academyId` int NOT NULL COMMENT '学院',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cid`(`academyId`) USING BTREE,
  CONSTRAINT `cid` FOREIGN KEY (`academyId`) REFERENCES `colleges` (`collegesId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (16, '512671323', '林丹', '123123123', 35, 1, '13412341234', 1, 'Y', 17);
INSERT INTO `user` VALUES (17, '265123743', '周星驰', '12312312', 46, 1, '13456124634', 1, 'Y', 2);
INSERT INTO `user` VALUES (19, '635623831', '周杰伦', '12312312', 45, 1, '15012616837', 1, 'Y', 2);
INSERT INTO `user` VALUES (20, '234723487', '周冬雨', '16342213qq', 47, 1, '18725782467', 1, 'Y', 2);
INSERT INTO `user` VALUES (22, 'admin5', '薛之谦', '123123', 11, 1, '123123', 1, 'Y', 2);
INSERT INTO `user` VALUES (23, 'admin6', '吴亦凡', '123123', 11, 1, '123123', 1, 'Y', 2);
INSERT INTO `user` VALUES (24, '123', '吴彦祖', '123123', 11, 1, '123123', 1, 'Y', 3);
INSERT INTO `user` VALUES (68, '3156123', '林志炫', '1231231231', 34, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (69, '124551', '林志', '1231231231', 55, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (70, '164323', '小明', '1231231231', 45, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (71, '127674', '小红', '1231231231', 123, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (72, '123673', '小白', '1231231231', 123, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (73, '127235', '小绿', '1231231231', 123, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (74, '12635775', '小紫', '1231231231', 123, 1, '13412341234', 1, 'Y', 7);
INSERT INTO `user` VALUES (75, 'q', '123', 'q', 123, 1, '123', 1, 'Y', 5);
INSERT INTO `user` VALUES (76, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (77, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (78, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (79, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (80, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (81, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (82, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (83, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (84, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (85, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (86, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (87, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);
INSERT INTO `user` VALUES (88, '1231111', '1', '111111', 22, 0, '12312312311', 1, 'Y', 2);

-- ----------------------------
-- Event structure for reset_money
-- ----------------------------
DROP EVENT IF EXISTS `reset_money`;
delimiter ;;
CREATE EVENT `reset_money`
ON SCHEDULE
EVERY '30' DAY STARTS '2023-03-10 06:33:33'
DO UPDATE salary SET money = 0
;;
delimiter ;

-- ----------------------------
-- Event structure for update_mycolumn
-- ----------------------------
DROP EVENT IF EXISTS `update_mycolumn`;
delimiter ;;
CREATE EVENT `update_mycolumn`
ON SCHEDULE
EVERY '1' DAY STARTS '2023-03-10 06:23:33'
DO BEGIN
		UPDATE `saraly` 
		SET `money` = '0' 
	WHERE
		`create_date` <= DATE_SUB( NOW(), INTERVAL 30 DAY );

END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table saraly
-- ----------------------------
DROP TRIGGER IF EXISTS `mytable_insert`;
delimiter ;;
CREATE TRIGGER `mytable_insert` BEFORE INSERT ON `saraly` FOR EACH ROW SET NEW.`sdate` = NOW()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
