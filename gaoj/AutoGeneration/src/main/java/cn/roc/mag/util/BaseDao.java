package cn.roc.mag.util;

import java.util.List;
import java.util.Map;

/**
 * @Author Roc Chen
 * @Description
 * @Date:Created Administrator in 17:38 2017/12/21 0021
 * @Modified By:
 */
public interface BaseDao<T> {

    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(T record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(T record);

    /**
     *
     * @mbggenerated
     */
    T selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(T record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(T record);

    List<T> getList(T t);

    // 获取数量
    int getCountSelective(T t);

    /**
     *
     * @Title: findPage
     * @Description: TODO()
     * @param page
     *            分页参数
     * @param sql
     *            mybatis sql语句
     * @param values
     *            命名参数,按名称绑定
     * @return 分页查询结果, 附带结果列表及所有查询时的参数.
     * @author YangChao
     * @date 2016年9月7日 下午5:30:28
     */
//    public PageView<T> findPage(final PageView<T> page, final String sql, final Map<String, Object> values);

}
