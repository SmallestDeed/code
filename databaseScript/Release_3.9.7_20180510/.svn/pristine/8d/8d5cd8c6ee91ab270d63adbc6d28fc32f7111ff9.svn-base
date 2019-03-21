#备份数据
create table his.sys_user_20180503 like sys_user;
insert into his.sys_user_20180503 select * from sys_user;

CREATE PROCEDURE `p_process_designer`()
BEGIN
	
	declare v_user_id 															INT; -- 用户ID
	declare v_company_id 														INT; -- 企业ID
	declare v_authorized_code_type									INT; -- 序列号使用类型（0正式， 1试用）
	declare v_start_time														timestamp; -- 序列号绑定时间
	declare v_valid_time														timestamp; -- 序列号到期时间

	declare done int default false;

	declare cur1 cursor for select a.user_id,a.company_id,a.authorized_code_type,a.start_time,a.valid_time from sys_user s
														left join authorized_config a on a.user_id = s.id
														where s.user_type = 4 and s.business_administration_id is null and s.is_deleted = 0 and a.valid_time > now() and a.is_deleted = 0;


	-- declare continue handler for not found SET done=true;

	-- 打开游标
	open cur1;
		read_loop: LOOP

			fetch cur1 into v_user_id,v_company_id,v_authorized_code_type,v_start_time,v_valid_time;

			if done then 
			
				leave read_loop;
		
			else
				
				-- 用户与序列号企业关联
				update sys_user set business_administration_id = v_company_id where is_deleted = 0 and id = v_user_id;
				
				-- 如果是正式序列号，用户使用类型改为正式，首次登录时间改为首次绑定序列号时间，有效时长按月来统计
				if v_authorized_code_type = 0 THEN

					select TIMESTAMPDIFF(month,v_start_time,v_valid_time) into @v_user_valid_time;
					update sys_user set use_type = 1 , first_login_time = v_start_time , valid_time = @v_user_valid_time , failure_time = v_valid_time where is_deleted = 0 and id = v_user_id;

				end if;

				-- 如果是试用序列号，用户使用类型改为试用，首次登录时间改为序列号绑定时间，有效时长按天数来统计
				if v_authorized_code_type = 1 THEN

					select TIMESTAMPDIFF(day,v_start_time,v_valid_time) into @v_user_valid_time;
					update sys_user set use_type = 0 , first_login_time = v_start_time , valid_time = @v_user_valid_time , failure_time = v_valid_time where is_deleted = 0 and id = v_user_id;

				end if;

				-- 修改设计师关联的企业类型为设计公司
				update base_company set business_type = 4 where id = v_company_id;

			end if;

		end LOOP read_loop;
	close cur1;

END;

call p_process_designer();

-- 回滚脚本
-- TRUNCATE TABLE sys_user;
-- INSERT INTO sys_user SELECT * FROM his.sys_user_20180503;