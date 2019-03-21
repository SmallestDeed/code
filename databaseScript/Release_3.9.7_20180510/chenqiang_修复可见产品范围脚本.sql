-- 创建备份临时表
CREATE TABLE company_category_rel_20180510 LIKE company_category_rel;
INSERT INTO  company_category_rel_20180510 SELECT * FROM company_category_rel;

-- 删除数据
TRUNCATE FROM company_category_rel;

-- 创建存储过程
CREATE PROCEDURE p_category_reform()
BEGIN

	DECLARE v_company_id               					INT ; -- 公司id

	DECLARE done INT DEFAULT FALSE; -- 是否继续游标循环
	
	-- 查询非经销商企业，未被删除、可见产品范围不为空的企业数据
	DECLARE My_Cursor cursor for  SELECT id FROM base_company WHERE business_type <> 2 AND is_deleted = 0 AND product_visibility_range IS NOT NULL AND product_visibility_range <> '' ORDER BY id ASC;

	-- 打开游标
	open My_Cursor;
	 
	-- 开始循环
	read_loop: LOOP
		FETCH My_Cursor INTO v_company_id;
		IF done THEN -- 判断是否继续循环 
		
			LEAVE read_loop; -- 结束循环
			
		ELSE
			
			-- 新增数据
			INSERT INTO `company_category_rel`( `company_id`, `category_id`, `display_status`, `sys_code`, `creator`, `gmt_create`, `modifier`, `gmt_modified`, `is_deleted`, `att1`, `att2`, `numa1`, `numa2`, `remark`) 
			SELECT v_company_id,bc.category_id,NULL,CONCAT(DATE_FORMAT(NOW(),'%Y%m%d%H%i%S'),'_',FLOOR(RAND()*500000 + 500000)),'13554889912',NOW(),'13554889912',NOW(),0,NULL, NULL, NULL, NULL,'[reform:根据产品可见范围修复]' 
			FROM (
				-- 三级分类
				SELECT
					substring_index( substring_index( bc.product_visibility_range, ',', b.help_topic_id + 1 ), ',', - 1 ) AS category_id
				FROM
					base_company bc JOIN mysql.help_topic b ON b.help_topic_id < ( LENGTH( bc.product_visibility_range ) - LENGTH( REPLACE ( bc.product_visibility_range, ',', '' ) ) + 1 )
				WHERE bc.id= v_company_id
				UNION	-- 二级分类
				SELECT pid as category_id FROM pro_category WHERE id in(
				SELECT
					substring_index( substring_index( bc.product_visibility_range, ',', b.help_topic_id + 1 ), ',', - 1 ) AS category_id
				FROM
					base_company bc JOIN mysql.help_topic b ON b.help_topic_id < ( LENGTH( bc.product_visibility_range ) - LENGTH( REPLACE ( bc.product_visibility_range, ',', '' ) ) + 1 )
				WHERE bc.id= v_company_id)
				UNION	-- 一级级分类
				SELECT pid as category_id FROM pro_category WHERE id in(
				SELECT pid FROM pro_category WHERE id in(
				SELECT
					substring_index( substring_index( bc.product_visibility_range, ',', b.help_topic_id + 1 ), ',', - 1 ) AS category_id
				FROM
					base_company bc JOIN mysql.help_topic b ON b.help_topic_id < ( LENGTH( bc.product_visibility_range ) - LENGTH( REPLACE ( bc.product_visibility_range, ',', '' ) ) + 1 )
				WHERE bc.id= v_company_id))
			) bc;
	
		END IF;

	END LOOP read_loop;	-- 结束自定义循环体 
	CLOSE My_Cursor; -- 关闭游标  
END;

-- 执行存储过程
CALL p_category_reform();

-- 回滚脚本
-- TRUNCATE TABLE company_category_rel;
-- INSERT INTO company_category_rel SELECT * FROM company_category_rel_20180510;