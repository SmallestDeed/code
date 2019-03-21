-- 创建备份临时表
CREATE TABLE sys_user_20180510 LIKE sys_user;
INSERT INTO  sys_user_20180510 SELECT * FROM sys_user;

-- 更改昵称中包含有试用两字的未登录用户类型为试用，有效时间为10天
UPDATE 
	sys_user 
SET
	use_type = 0,
	valid_time = 10,
	remark = CONCAT(IFNULL(remark,''),'[reform:更改未登录用户为试用用户]')
WHERE use_type = 1
	AND is_deleted = 0 
	AND is_login_before = 0
	AND user_name LIKE '%试用%';


-- 更改昵称中包含有试用两字的登录用户类型为试用，在当天基础上加10天
UPDATE 
	sys_user 
SET
	use_type = 0,
	failure_time = DATE_ADD(NOW(), INTERVAL 10 DAY),
	remark = CONCAT(IFNULL(remark,''),'[reform:更改登录用户为试用用户]')
WHERE use_type = 1
	AND is_deleted = 0 
	AND is_login_before = 1
	AND user_name LIKE '%试用%';
	
	
-- 修复未登录超级管理员没有用户类型改为正式、12月
UPDATE 
	sys_user 
SET 
	use_type = 1,
	valid_time = 12,
	remark = CONCAT( IFNULL( remark, '' ), '[reform:管理员使用类型修复]' ) 
WHERE nick_name LIKE 'adminC%' 
	AND use_type IS NULL
	AND is_deleted = 0
	AND is_login_before = 0;
	
	
-- 回滚脚本
-- TRUNCATE TABLE sys_user;
-- INSERT INTO sys_user SELECT * FROM sys_user_20180510;
