## 商家后台说明

## 环境
1. OS: CentOS
2. JDK: >= java 1.8
3. MySQL: 参考配置文件，视运行环境不同而不同！
4. Nginx: 请咨询运维组

## 接口文档
1. 开发环境： `http://adminapi.dev.sanduspace.com/swagger-ui.html`
2. 测试环境： `http://adminapi.test.sanduspace.com/swagger-ui.html`
3. 集成环境： `http://adminapi.ci.sanduspace.com/swagger-ui.html`
4. 预发布环境： `http://adminapi.sanduspace.com/swagger-ui.html`
5. 正式环境：  无

> 接口请求与返回规范
1. 请求方法
    - 获取单个或查询列表 `GET`
    - 新建 `POST`
    - 更新 `PUT`
    - 删除 `DELETE`
2. 返回格式 `{"code": 200, "data": {}, "list": [ {} ], "message": "", "total": 0}`
3. 代码含义
    - `200` 获取，更新，删除成功
    - `201` 新建成功
    - `204` 获取成功，但无内容
    - `400` 传入参数有误
    - `401` 未授权请求，一般指未登录
    - `403` 权限不够
    - `500` 服务器内部错误
    - `502` 网关出错，一般指nginx

## 数据库更新脚本
SVN： `https://svns.sanduspace.cn/svn/nork/decorate/server/databaseScript/merchant/release_v0.1.3_20180420`

## 代码
   - 前端  
      `https://svns.sanduspace.cn/svn/nork/webproject/brand2c-admin/tags/release/v0.1.1`
   - 后端
     - API `http://git.3du-inc.net/sandu/wangwang-web-merchant-manage.git`
     - 运行包：`target/wangwang-web-merchant-manager.jar`
       - 需用当前环境的 Redis配置替换`src\main\resources\sso-redis.properties`中配置！

     - Product `http://git.3du-inc.net/sandu/wangwang-product.git`
       - 运行包：wangwang-provider-product/target/wangwang-provider-product.jar

     - Solution `http://git.3du-inc.net/sandu/wangwang-solution.git`
       - 运行包：wangwang-provider-solution/target/wangwang-provider-solution.jar

     - Storage `http://git.3du-inc.net/sandu/wangwang-storage.git`
       - 运行包：wangwang-storage-app/target/wangwang-storage-app.jar

   > 注： 
   - git 项目全部是拉取 `master` 分支，每次会拉取最新代码！
   - 运行参数： `--spring.cloud.config.profile=online` (测试`test`, 集成 `ci`, 正式`online`)
   - 完整运行命令： `java -jar wangwang-proivder-product.jar --spring.cloud.config.profile=ci >wangwang-provider-product.log 2>&1 &`
       
   - 后端配置中心
     - 配置中心jar应用位于`10.10.20.130`，运行命令： `/opt/apps/runner.sh wangwang-config-server`
     - 配置文件SVN： `https://svns.sanduspace.cn/svn/sandudep/config/wangwang_config`
              
## 部署
1. 开发，测试环境通过gitlab部署，在每次`push`代码到`test`分支时，会自动部署
2. 集成、预发布，正式环境请在Jenkins 执行部署

