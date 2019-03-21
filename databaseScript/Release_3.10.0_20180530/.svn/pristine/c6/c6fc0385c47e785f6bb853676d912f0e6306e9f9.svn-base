-- 删除platform2c_product_rel修改过的数据
delete from platform2c_product_rel where gmt_modified <= (
	SELECT MAX(gmt_modified) FROM platform2c_product_rel_bak_20180531
);
-- 重新从platform2c_product_rel_bak_20180531插入
insert into platform2c_product_rel select * from platform2c_product_rel_bak_20180531;
-- 删除platform2c_product_rel修改过的数据
delete from platform2b_product_rel where gmt_modified <= (
	SELECT MAX(gmt_modified) FROM platform2b_product_rel_bak_20180531
);
-- 重新从platform2b_product_rel_bak_20180531插入
insert into platform2b_product_rel select * from platform2b_product_rel_bak_20180531;
