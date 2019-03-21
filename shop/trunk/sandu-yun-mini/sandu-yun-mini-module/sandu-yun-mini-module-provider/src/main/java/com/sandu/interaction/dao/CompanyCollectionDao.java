package com.sandu.interaction.dao;

import com.sandu.common.persistence.CrudDao;
import com.sandu.interaction.model.Collection;
import com.sandu.interaction.model.query.CollectionQuery;
import com.sandu.interaction.model.vo.CollectionVo;

public interface CompanyCollectionDao  extends CrudDao<Collection,CollectionQuery,CollectionVo>{
	int count(CollectionQuery query);
}
