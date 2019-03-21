# 方案涉及的表
design_plan_recommended 主要的表 
 recommended_type 1 普通方案 2 智能方案

design_plan_recommended_products 方案产品表

res_render_pic 效果图表

design_plan_copy_log 交付和使用记录表

design_plan_2b_platform 方案的2b分配上架表
design_plan_2c_platform 方案的2c分配上架表

共享方案
上架 没有上架上架到一键方案栏目的方案
使用共享方案 就是把那条方案复制一份 把公司id改成自己的id



# 方案功能实现

## 1、分配

### 分配渠道
1. 先判断这个方案是不是已经在design_plan_2b_platform中有数据了,
如果有了就修改allot_state = 1 否则的话 在design_plan_2b_platform中写入数据 
并把allot_state=1 每个平台都需要写入(每个平台id 通过base_platform)

### 分配线上
1. 先判断这个方案是不是已经在design_plan_2c_platform中有数据了,
如果有了就修改allot_state = 1 否则的话 在design_plan_2c_platform中写入数据 
并把allot_state=1 每个平台都需要写入

    
## 2、上架
### 渠道上架:

1. 前端传递 方案和上架到的菜单 一键方案/公开方案 样板方案/
2. 在design_plan_recommended 中写入上架的类型 shelf_status字段
3. 同时在上架表 design_plan_2b_platform该方案的所有平台 修改pushaway字段为 1 
4.上架成功
     
### 线上上架:
       
1. 前端传递 方案和上架到的平台 如 小程序 2c品牌网站 
2. 同时在上架表 design_plan_2c_platform该方案的对应平台的数据 修改pushaway字段为 1 
3. 上架成功

## 3、方案交付

1. 前端选择需要交付的方案和交付给的企业
2. 后端获取到该方案 并设置上架状态(shelf_status)为'' 方案来源plan_source设置为deliver company_id 
设置为交付给的企业 复制(insert)一份方案到交付给的企业 
3. 在design_plan_brand 中写入一条数据 对应交付给的公司 (兼容以前的逻辑)
4. 复制该方案的 产品(design_plan_recommended_products) 和缩略图 (res_render_pic),多点效果图 (design_render_roam),
 视频图 (res_render_video) 并把对应的方案id改为复制后的那条方案的id
5. 交付完成

## 5、查看共享方案&使用共享方案

### 方案列表
1. 共享方案(design_plan_recommended)的查询规则 
(1) 该方案已上架除了一键方案菜单的方案 
(2) 该方案的来源(plan_source)是内部的 (plan_source 为 null ,'',diy,huxing都属于内部

### 使用方案
1.  传入方案和当前的企业
2. 后端获取到该方案 并设置上架状态(shelf_status)为'' 方案来源(plan_source)设置为share company_id 
设置为分享给的企业 复制(insert into design_plan_recommended)一份方案到给的企业
3. 在design_plan_brand 中写入一条数据 对应交付给的公司 (兼容以前的逻辑)
4. 复制该方案的 产品(design_plan_recommended_products) 和缩略图 (res_render_pic) 并把对应的方案id改为复制后的那条方案的id
 

## 6、


## 定时任务
1. 自动分配、上架
获取在design_paln_2b_platfrom里面完全没有分配和上架过的方案
也就是在design_plan_recommended里面有方案 而在design_paln_2b_platfrom没有数据的方案进行分配上架

2. 为公司自动使用分享方案
先获取一个没有方案的公司
然后获取指定的方案
调用 '使用'copySharePlan2Company 的接口为这个公司使用这批方案


## 设计方案
1. 开始设计：根据提供样板设计方案（白膜方案匹配产品），创建方案配置文件
2. 保存设计方案配置文件到 res_design设计方案资源表
3. 装修完成，生成草图 design_plan设计方案表
4. 完成渲染，生成效果图方案




