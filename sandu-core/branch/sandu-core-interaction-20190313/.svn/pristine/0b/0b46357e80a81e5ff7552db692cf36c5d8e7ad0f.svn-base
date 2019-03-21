package com.sandu.service.base.dao;

import com.sandu.api.base.input.InteractiveZoneReplyQuery;
import com.sandu.api.base.model.CompanyShopArticle;
import com.sandu.api.base.model.InteractiveZoneReply;
import com.sandu.api.base.model.ProjectCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository

public interface InteractiveZoneReplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InteractiveZoneReply record);

    int insertSelective(InteractiveZoneReply record);

    InteractiveZoneReply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InteractiveZoneReply record);

    int updateByPrimaryKeyWithBLOBs(InteractiveZoneReply record);

    int updateByPrimaryKey(InteractiveZoneReply record);

	int countByTopicId(@Param("id") Long id);

    List<InteractiveZoneReply> queryBiz(InteractiveZoneReplyQuery query);

    List<CompanyShopArticle> findCompanyShopArticleListByArticleIds(@Param("articleIdList") Set<Integer> articleIdList);

    List<ProjectCase> findProjectCaseListByArticleIds(@Param("projectCaseIds")Set<Integer> projectCaseIds);

}