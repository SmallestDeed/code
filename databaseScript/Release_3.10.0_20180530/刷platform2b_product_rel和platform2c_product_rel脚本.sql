-- 备份platform2c_product_rel表数据
CREATE TABLE platform2c_product_rel_bak_20180531 AS SELECT * FROM platform2c_product_rel;
-- 备份platform2b_product_rel表数据
CREATE TABLE platform2b_product_rel_bak_20180531 AS SELECT * FROM platform2b_product_rel;

-- 修复 platform2b_product_rel表中style_id为null的数据  
UPDATE platform2b_product_rel a,base_product b
SET a.style_id = REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(b.product_style_id_info, '"isLeaf_0":"', -1),'"',1),'{','')
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.product_style_id_info) > 0
AND a.style_id IS NULL
AND LENGTH(REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(b.product_style_id_info, '"isLeaf_0":"', -1),'"',1),'{',''))>0;

-- 修复 platform2c_product_rel表中style_id为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.style_id = REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(b.product_style_id_info, '"isLeaf_0":"', -1),'"',1),'{','')
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.product_style_id_info) > 0
AND a.style_id IS NULL
AND LENGTH(REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(b.product_style_id_info, '"isLeaf_0":"', -1),'"',1),'{',''))>0;

-- 修复 platform2b_product_rel表中sale_price为null的数据
UPDATE platform2b_product_rel a,base_product b
SET a.sale_price = b.sale_price
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.sale_price) > 0  AND b.sale_price > 0
AND (a.sale_price IS NULL OR a.sale_price = 0);

-- 修复 platform2c_product_rel表中sale_price为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.sale_price = b.sale_price
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.sale_price) > 0 AND b.sale_price > 0
AND (a.sale_price IS NULL OR a.sale_price = 0);

-- 修复 platform2b_product_rel表中advice_price为null的数据
UPDATE platform2b_product_rel a,base_product b
SET a.advice_price = b.advice_price
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.advice_price) > 0 AND b.advice_price > 0
AND (a.advice_price IS NULL OR a.advice_price = 0);

-- 修复 platform2c_product_rel表中advice_price为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.advice_price = b.advice_price
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND LENGTH(b.advice_price) > 0 AND b.advice_price > 0
AND (a.advice_price IS NULL OR a.advice_price = 0);

-- 修复 platform2b_product_rel表中pic_id为null的数据
UPDATE platform2b_product_rel a,base_product b
SET a.pic_id = b.pic_id
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND b.pic_id IS NOT NULL AND b.pic_id !=''
AND (a.pic_id IS NULL OR a.pic_id ='');

-- 修复 platform2c_product_rel表中pic_id为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.pic_id = b.pic_id
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND  b.pic_id IS NOT NULL AND b.pic_id !=''
AND (a.pic_id IS NULL OR a.pic_id ='');

-- 修复 platform2b_product_rel表中pic_ids为null的数据
UPDATE platform2b_product_rel a,base_product b
SET a.pic_ids = b.pic_ids
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND  b.pic_ids IS NOT NULL AND b.pic_ids !=''
AND (a.pic_ids IS NULL OR a.pic_ids ='');

-- 修复 platform2c_product_rel表中pic_ids为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.pic_ids = b.pic_ids
WHERE a.product_id = b.id 
AND b.is_deleted = 0  AND  b.pic_ids IS NOT NULL AND b.pic_ids !=''
AND (a.pic_ids IS NULL OR a.pic_ids ='');

-- 修复 platform2b_product_rel表中remark为null的数据
UPDATE platform2b_product_rel a,base_product b
SET a.description = b.product_desc
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND  b.product_desc IS NOT NULL AND b.product_desc !=''
AND (a.description IS NULL OR a.description ='');

-- 修复 platform2c_product_rel表中remark为null的数据
UPDATE platform2c_product_rel a,base_product b
SET a.description = b.product_desc
WHERE a.product_id = b.id 
AND b.is_deleted = 0 AND  b.product_desc IS NOT NULL AND b.product_desc !=''
AND (a.description IS NULL OR a.description ='');


