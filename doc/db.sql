CREATE TABLE `dream`.`dream_channel` (
  `CODE` INT NOT NULL COMMENT '编码',
  `P_CODE` INT NULL COMMENT '父编码',
  `NAME` VARCHAR(80) NULL COMMENT '名称',
  PRIMARY KEY (`CODE`))
COMMENT = '栏目';


CREATE TABLE `dream`.`dream_article` (
  `ID` INT NOT NULL COMMENT '主键',
  `TITLE` VARCHAR(200) NULL COMMENT '标题',
  `CONTENT` TEXT NULL COMMENT '内容',
  `CHAN_ID` INT NULL COMMENT '栏目ID',
  `ATIME` VARCHAR(20) NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`))
COMMENT = '文章';


CREATE TABLE `dream`.`dream_task` (
  `ID` INT NOT NULL,
  `TITLE` VARCHAR(200) NULL COMMENT '标题',
  `DESCP` VARCHAR(400) NULL COMMENT '说明',
  `TTYPE` DECIMAL(1) NULL COMMENT '类型 (未完成/已完成)',
  `START` VARCHAR(20) NULL COMMENT '开始时间',
  `ENDTIME` VARCHAR(20) NULL COMMENT '结束时间',
  PRIMARY KEY (`ID`))
COMMENT = '任务';

ALTER TABLE `dream`.`dream_task` 
CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `dream`.`dream_channel` 
CHANGE COLUMN `CODE` `CODE` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编码' ;

ALTER TABLE `dream`.`dream_article` 
CHANGE COLUMN `ID` `ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键' ;

ALTER TABLE `dream`.`dream_article` 
ADD COLUMN `SUMMARY` VARCHAR(300) NULL COMMENT '摘要' AFTER `ATIME`,
ADD COLUMN `LOCALURL` VARCHAR(100) NULL COMMENT '本地HTML路径' AFTER `SUMMERY`;


CREATE TABLE `dream`.`dream_file` (
  `ID` VARCHAR(40) NOT NULL COMMENT '主键',
  `NAME` VARCHAR(200) NULL COMMENT '文件名',
  `DISNAME` VARCHAR(200) NULL COMMENT '显示名',
  `MTYPE` VARCHAR(40) NULL COMMENT '类型',
  `FSIZE` VARCHAR(45) NULL COMMENT '大小',
  `PATH` VARCHAR(100) NULL COMMENT '路径',
  `DATAID` VARCHAR(40) NULL,
  `ATIME` VARCHAR(23) NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC));

  
CREATE TABLE `dream`.`dream_config` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CONFKEY` VARCHAR(45) NULL COMMENT 'KEY',
  `CONFVALUE` VARCHAR(400) NULL COMMENT '配置值',
  PRIMARY KEY (`ID`))
COMMENT = '配置表';  

ALTER TABLE `dream`.`dream_article` 
ADD COLUMN `IMGIDS` VARCHAR(400) NULL AFTER `LOCALURL`;



CREATE TABLE `dream`.`dream_act_log` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `model_type` VARCHAR(45) NULL COMMENT '所属模块',
  `act_type` VARCHAR(10) NULL COMMENT '操作类型',
  `dataid` VARCHAR(45) NULL COMMENT '数据主键',
  `atime` VARCHAR(23) NULL COMMENT '操作时间',
  PRIMARY KEY (`ID`))
COMMENT = '操作记录';



ALTER TABLE `dream`.`dream_article` 
ADD COLUMN `SORTNUM` INT NULL COMMENT '排序' AFTER `IMGIDS`;

ALTER TABLE `dream`.`dream_article` 
CHANGE COLUMN `CHAN_ID` `CHANID` INT(11) NULL DEFAULT NULL COMMENT '栏目ID' ;

ALTER TABLE `dream`.`dream_article` 
ADD COLUMN `CHANNAME` VARCHAR(45) NULL COMMENT '栏目' AFTER `SORTNUM`;

INSERT INTO `dream`.`dream_config` (`ID`, `CONFKEY`, `CONFVALUE`) VALUES ('11', 'INDEX_URL', 'http://localhost:8080/solr/collection1');


CREATE TABLE `dream`.`dream_sicker` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` VARCHAR(45) NULL COMMENT '姓名',
  `AGE` INT NULL COMMENT '年龄',
  `JIGUAN` VARCHAR(45) NULL COMMENT '籍贯',
  `INTIME` VARCHAR(23) NULL COMMENT '入院时间',
  `OUTTIME` VARCHAR(23) NULL COMMENT '出院时间',
  `KESHI` VARCHAR(45) NULL COMMENT '科室',
  `BEDNUM` VARCHAR(45) NULL COMMENT '床位号',
  `INNUM` VARCHAR(45) NULL COMMENT '入院号',
  `SICKDESC` TEXT NULL COMMENT '说明',
  `MEMO` VARCHAR(2000) NULL COMMENT '备注',
  PRIMARY KEY (`ID`));
  
  
  
CREATE TABLE `dream`.`dream_dict` (
  `CODE` VARCHAR(45) NOT NULL COMMENT '主键',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  PRIMARY KEY (`CODE`))
ENGINE = InnoDB
COMMENT = '字典';

CREATE TABLE `dream`.`dream_dict_entry` (
  `CODE` VARCHAR(45) NOT NULL COMMENT '编号',
  `PCODE` VARCHAR(45) NULL COMMENT '父编号',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  `ESORT` VARCHAR(45) NULL COMMENT '排序',
  `DICTID` VARCHAR(45) NULL COMMENT '字典编码'
COMMENT = '字典项';


ALTER TABLE `dream`.`dream_dict_entry` 
ADD COLUMN `ID` INT NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (`ID`);


ALTER TABLE `dream`.`dream_dict_entry` 
CHANGE COLUMN `ESORT` `ESORT` INT NULL DEFAULT NULL COMMENT '排序' ;


ALTER TABLE `dream`.`dream_dict_entry` 
ADD COLUMN `DLEVEL` INT NULL COMMENT '层级' AFTER `DICTID`;


CREATE TABLE `dream`.`dream_menu` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '名称',
  `url` VARCHAR(45) NULL COMMENT '地址',
  `mtype` VARCHAR(10) NULL COMMENT '类型',
  `mclass` VARCHAR(100) NULL COMMENT '样式',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '菜单';

ALTER TABLE `dream`.`dream_menu` 
ADD COLUMN `msort` INT NULL COMMENT '排序' AFTER `mclass`;


ALTER TABLE `dream`.`dream_menu` 
ADD COLUMN `pid` INT NULL COMMENT '父ID' AFTER `msort`;


ALTER TABLE `dream`.`dream_menu` 
ADD COLUMN `mlevel` INT NULL COMMENT '层级' AFTER `pid`,
ADD COLUMN `mpath` VARCHAR(100) NULL COMMENT '路径' AFTER `mlevel`;



CREATE TABLE `dream`.`tb_dept` (
  `code` VARCHAR(45) NOT NULL COMMENT '编码',
  `pcode` VARCHAR(45) NULL COMMENT '父编码',
  `name` VARCHAR(50) NULL COMMENT '名称',
  `dlevel` INT NULL COMMENT '层级',
  `dpath` VARCHAR(200) NULL COMMENT '路径',
  `dsort` INT NULL COMMENT '排序',
  PRIMARY KEY (`code`))
COMMENT = '部门';


ALTER TABLE `dream`.`tb_user` 
ADD COLUMN `deptcode` VARCHAR(45) NULL COMMENT '部门' AFTER `sex`,
ADD COLUMN `usort` INT NULL COMMENT '排序' AFTER `deptcode`;

ALTER TABLE `dream`.`tb_user` 
CHANGE COLUMN `id` `id` VARCHAR(45) NOT NULL COMMENT '主键' ;

ALTER TABLE `dream`.`tb_user` 
ADD COLUMN `loginname` VARCHAR(45) NULL COMMENT '登录名' AFTER `id`;


ALTER TABLE `dream`.`dream_sicker` 
ADD COLUMN `SEX` INT NULL COMMENT '性别' AFTER `AGE`;


CREATE TABLE `dream`.`dream_sicker_record` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SICKERID` VARCHAR(45) NULL COMMENT '病人ID',
  `INTIME` VARCHAR(23) NULL COMMENT '开始时间',
  `OUTTIME` VARCHAR(23) NULL COMMENT '结束时间',
  `DURATION` VARCHAR(20) NULL COMMENT '持续时间',
  `MEMO` VARCHAR(4000) NULL COMMENT '说明',
  `RESULT` VARCHAR(4000) NULL COMMENT '结果',
  `PARAM1` VARCHAR(45) NULL COMMENT '参数1',
  `PARAM2` VARCHAR(45) NULL COMMENT '参数2',
  `PARAM3` VARCHAR(45) NULL COMMENT '参数3',
  PRIMARY KEY (`ID`))
COMMENT = '病历';

CREATE TABLE `dream`.`dream_sicker_log` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `RECORDID` VARCHAR(45) NULL COMMENT '批次',
  `PARAM` VARCHAR(45) NULL COMMENT '参数',
  `XAXIS` VARCHAR(45) NULL COMMENT 'X轴',
  `XVALUE` VARCHAR(45) NULL COMMENT 'X值',
  `YAXIS` VARCHAR(45) NULL COMMENT 'Y轴',
  `YVALUE` VARCHAR(45) NULL COMMENT 'Y值',
  PRIMARY KEY (`ID`))
COMMENT = '日志记录';


CREATE TABLE `dream`.`tb_seat` (
  `seatid` VARCHAR(45) NOT NULL COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '名称',
  `sdesc` VARCHAR(45) NULL COMMENT '说明',
  `userid` VARCHAR(45) NULL COMMENT '人员',
  PRIMARY KEY (`seatid`))
COMMENT = '座位';


CREATE TABLE `dream`.`dream_todo` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(400) NULL COMMENT '标题',
  `userid` VARCHAR(45) NULL COMMENT '待办人',
  `fromuser` VARCHAR(45) NULL COMMENT '来自',
  `url` VARCHAR(400) NULL COMMENT 'url',
  `tdtime` VARCHAR(23) NULL COMMENT '时间',
  `tdname` VARCHAR(40) NULL COMMENT '模块名称',
  `tdtype` INT NULL COMMENT '是否完成',
  PRIMARY KEY (`ID`))
COMMENT = '待办';


CREATE TABLE `dream`.`dream_wf_def` (
  `CODE` VARCHAR(80) NOT NULL COMMENT '编码',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  `jsondef` TEXT NULL COMMENT '定义json文件',
  PRIMARY KEY (`CODE`))
COMMENT = '流程定义';


CREATE TABLE `dream`.`dream_wf_node` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `jsonstr` TEXT NULL COMMENT '定义json',
  `wfcode` VARCHAR(80) NULL COMMENT '流程编码',
  PRIMARY KEY (`ID`))
COMMENT = '节点定义';



CREATE TABLE `dream`.`dream_wf_line` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `jsonstr` TEXT NULL COMMENT '定义信息',
  `wfcode` VARCHAR(80) NULL COMMENT '流程编码',
  PRIMARY KEY (`ID`))
COMMENT = '连线定义';


CREATE TABLE `dream`.`dream_wf_inst` (
  `ID` INT NOT NULL COMMENT '主键',
  `wfcode` VARCHAR(45) NULL COMMENT '流程编码',
  `dataid` VARCHAR(45) NULL COMMENT '数据ID',
  `complete` INT NULL COMMENT '是否办结',
  `btime` VARCHAR(23) NULL COMMENT '开始时间',
  `etime` VARCHAR(23) NULL COMMENT '结束时间',
  PRIMARY KEY (`ID`))
COMMENT = '流程实例';

CREATE TABLE `dream`.`dream_wf_node_inst` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `wfid` VARCHAR(45) NULL COMMENT '流程实例ID',
  `nodeid` VARCHAR(45) NULL COMMENT '节点ID',
  `nodename` VARCHAR(45) NULL COMMENT '节点名称',
  `totime` VARCHAR(23) NULL COMMENT '送交时间',
  `touser` VARCHAR(45) NULL COMMENT '接收人',
  `tousername` VARCHAR(45) NULL COMMENT '接收人',
  `donetime` VARCHAR(23) NULL COMMENT '办理时间',
  `doneuser` VARCHAR(45) NULL COMMENT '办理人',
  `doneusername` VARCHAR(45) NULL COMMENT '办理人名字',
  `fromuser` VARCHAR(45) NULL COMMENT '送交人',
  `fromusername` VARCHAR(45) NULL COMMENT '送交人',
  `nodestate` INT NULL COMMENT '节点是否正在办理',
  PRIMARY KEY (`ID`))
COMMENT = '节点实例';


CREATE TABLE `dream`.`dream_dynamic` (
  `ID` VARCHAR(45) NOT NULL,
  `position` VARCHAR(100) NULL COMMENT '地点',
  `atime` VARCHAR(23) NULL COMMENT '添加时间',
  `geopoint` VARCHAR(100) NULL COMMENT '地理信息',
  `imgIds` VARCHAR(45) NULL COMMENT '文件ID',
  `memo` VARCHAR(450) NULL COMMENT '说明',
  `itemtype` INT NULL COMMENT '类型',
  PRIMARY KEY (`ID`))
COMMENT = '动态';


ALTER TABLE `dream`.`dream_file` 
ADD COLUMN `model` VARCHAR(150) NULL COMMENT '所属模块' AFTER `ATIME`;




CREATE TABLE `dream`.`dream_ins_def` (
  `ID` VARCHAR(45) NOT NULL COMMENT '主键',
  `CODE` VARCHAR(45) NULL COMMENT '编码',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  `MODEL` VARCHAR(100) NULL COMMENT '模板路径',
  `CHANNUM` INT NULL COMMENT '通道数',
  `COMMAND` VARCHAR(400) NULL COMMENT '值改变的命令',
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


CREATE TABLE `dream`.`dream_ins_btn` (
  `ID` VARCHAR(45) NOT NULL COMMENT '主键',
  `CODE` VARCHAR(45) NULL COMMENT '编码',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  `COMMAND` VARCHAR(400) NULL COMMENT '命令',
  `SORTNUM` INT NULL COMMENT '排序',
  `CSSPLUS` VARCHAR(100) NULL COMMENT '样式',
  `INSID` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = '按钮定义';


CREATE TABLE `dream`.`dream_ins_field` (
  `ID` VARCHAR(45) NOT NULL COMMENT '主键',
  `CODE` VARCHAR(45) NULL COMMENT '编码',
  `NAME` VARCHAR(45) NULL COMMENT '名称',
  `ITEMTYPE` VARCHAR(45) NULL COMMENT '类型',
  `RELDICT` VARCHAR(45) NULL COMMENT '关联字典',
  `DEFAULTVAL` VARCHAR(45) NULL COMMENT '默认值',
  `SORTNUM` INT NULL COMMENT '排序',
  `INSID` VARCHAR(45) NULL COMMENT '关联定义',
  `COMMAND` VARCHAR(400) NULL COMMENT '命令',
  `CSSPLUS` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = '字段设置';



ALTER TABLE `dream`.`dream_act_log` 
ADD COLUMN `memo` VARCHAR(400) NULL COMMENT '备注' AFTER `atime`,
ADD COLUMN `descp` VARCHAR(200) NULL COMMENT '说明' AFTER `memo`;

ALTER TABLE `dream`.`dream_act_log` 
ADD COLUMN `reluser` VARCHAR(45) NULL COMMENT '关联的人员' AFTER `descp`,
ADD COLUMN `actname` VARCHAR(45) NULL COMMENT '按钮名称' AFTER `reluser`,
ADD COLUMN `result` VARCHAR(200) NULL COMMENT '结果' AFTER `actname`,
ADD COLUMN `portnum` VARCHAR(45) NULL COMMENT '通道序号' AFTER `result`;

ALTER TABLE `dream`.`dream_ins_def` 
ADD COLUMN `VALIDATEREQ` VARCHAR(100) NULL COMMENT '验证请求' AFTER `COMMAND`,
ADD COLUMN `VALIDATERES` VARCHAR(100) NULL COMMENT '验证返回值' AFTER `VALIDATEREQ`;

ALTER TABLE `dream`.`dream_sicker_log` 
CHANGE COLUMN `RECORDID` `RECORDID` VARCHAR(45) NULL DEFAULT NULL COMMENT '批次' ,
ADD COLUMN `USERID` VARCHAR(45) NULL COMMENT '关联用户' AFTER `YVALUE`;




CREATE TABLE `dream`.`dream_comment` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PID` INT NULL COMMENT '父ID',
  `DEPTH` INT NULL COMMENT '深度',
  `DATAID` VARCHAR(45) NULL COMMENT '关联ID',
  `UCODE` VARCHAR(45) NULL COMMENT '用户编码',
  `UNAME` VARCHAR(45) NULL COMMENT '名字',
  `IP` VARCHAR(45) NULL COMMENT 'IP',
  `CONTENT` VARCHAR(400) NULL COMMENT '评论内容',
  `ATIME` VARCHAR(23) NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`))
COMMENT = '评论';



