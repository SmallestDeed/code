package com.sandu.supplydemand.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseSupplyDemandExample implements Serializable{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BaseSupplyDemandExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Integer value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Integer value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Integer value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Integer value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Integer> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Integer> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Integer value1, Integer value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueIsNull() {
            addCriterion("creator_type_value is null");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueIsNotNull() {
            addCriterion("creator_type_value is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueEqualTo(Integer value) {
            addCriterion("creator_type_value =", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueNotEqualTo(Integer value) {
            addCriterion("creator_type_value <>", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueGreaterThan(Integer value) {
            addCriterion("creator_type_value >", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator_type_value >=", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueLessThan(Integer value) {
            addCriterion("creator_type_value <", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueLessThanOrEqualTo(Integer value) {
            addCriterion("creator_type_value <=", value, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueIn(List<Integer> values) {
            addCriterion("creator_type_value in", values, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueNotIn(List<Integer> values) {
            addCriterion("creator_type_value not in", values, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueBetween(Integer value1, Integer value2) {
            addCriterion("creator_type_value between", value1, value2, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCreatorTypeValueNotBetween(Integer value1, Integer value2) {
            addCriterion("creator_type_value not between", value1, value2, "creatorTypeValue");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(Integer value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(Integer value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(Integer value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(Integer value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<Integer> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<Integer> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(Integer value1, Integer value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNull() {
            addCriterion("district is null");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNotNull() {
            addCriterion("district is not null");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualTo(String value) {
            addCriterion("district =", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualTo(String value) {
            addCriterion("district <>", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThan(String value) {
            addCriterion("district >", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("district >=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThan(String value) {
            addCriterion("district <", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualTo(String value) {
            addCriterion("district <=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLike(String value) {
            addCriterion("district like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotLike(String value) {
            addCriterion("district not like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictIn(List<String> values) {
            addCriterion("district in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotIn(List<String> values) {
            addCriterion("district not in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictBetween(String value1, String value2) {
            addCriterion("district between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotBetween(String value1, String value2) {
            addCriterion("district not between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdIsNull() {
            addCriterion("cover_pic_id is null");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdIsNotNull() {
            addCriterion("cover_pic_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdEqualTo(Integer value) {
            addCriterion("cover_pic_id =", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdNotEqualTo(Integer value) {
            addCriterion("cover_pic_id <>", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdGreaterThan(Integer value) {
            addCriterion("cover_pic_id >", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cover_pic_id >=", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdLessThan(Integer value) {
            addCriterion("cover_pic_id <", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdLessThanOrEqualTo(Integer value) {
            addCriterion("cover_pic_id <=", value, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdIn(List<Integer> values) {
            addCriterion("cover_pic_id in", values, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdNotIn(List<Integer> values) {
            addCriterion("cover_pic_id not in", values, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdBetween(Integer value1, Integer value2) {
            addCriterion("cover_pic_id between", value1, value2, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andCoverPicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cover_pic_id not between", value1, value2, "coverPicId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdIsNull() {
            addCriterion("description_pic_id is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdIsNotNull() {
            addCriterion("description_pic_id is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdEqualTo(String value) {
            addCriterion("description_pic_id =", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdNotEqualTo(String value) {
            addCriterion("description_pic_id <>", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdGreaterThan(String value) {
            addCriterion("description_pic_id >", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdGreaterThanOrEqualTo(String value) {
            addCriterion("description_pic_id >=", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdLessThan(String value) {
            addCriterion("description_pic_id <", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdLessThanOrEqualTo(String value) {
            addCriterion("description_pic_id <=", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdLike(String value) {
            addCriterion("description_pic_id like", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdNotLike(String value) {
            addCriterion("description_pic_id not like", value, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdIn(List<String> values) {
            addCriterion("description_pic_id in", values, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdNotIn(List<String> values) {
            addCriterion("description_pic_id not in", values, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdBetween(String value1, String value2) {
            addCriterion("description_pic_id between", value1, value2, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDescriptionPicIdNotBetween(String value1, String value2) {
            addCriterion("description_pic_id not between", value1, value2, "descriptionPicId");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyIsNull() {
            addCriterion("decoration_company is null");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyIsNotNull() {
            addCriterion("decoration_company is not null");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyEqualTo(Integer value) {
            addCriterion("decoration_company =", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyNotEqualTo(Integer value) {
            addCriterion("decoration_company <>", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyGreaterThan(Integer value) {
            addCriterion("decoration_company >", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyGreaterThanOrEqualTo(Integer value) {
            addCriterion("decoration_company >=", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyLessThan(Integer value) {
            addCriterion("decoration_company <", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyLessThanOrEqualTo(Integer value) {
            addCriterion("decoration_company <=", value, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyIn(List<Integer> values) {
            addCriterion("decoration_company in", values, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyNotIn(List<Integer> values) {
            addCriterion("decoration_company not in", values, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyBetween(Integer value1, Integer value2) {
            addCriterion("decoration_company between", value1, value2, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDecorationCompanyNotBetween(Integer value1, Integer value2) {
            addCriterion("decoration_company not between", value1, value2, "decorationCompany");
            return (Criteria) this;
        }

        public Criteria andDesignerIsNull() {
            addCriterion("designer is null");
            return (Criteria) this;
        }

        public Criteria andDesignerIsNotNull() {
            addCriterion("designer is not null");
            return (Criteria) this;
        }

        public Criteria andDesignerEqualTo(Integer value) {
            addCriterion("designer =", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotEqualTo(Integer value) {
            addCriterion("designer <>", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerGreaterThan(Integer value) {
            addCriterion("designer >", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerGreaterThanOrEqualTo(Integer value) {
            addCriterion("designer >=", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerLessThan(Integer value) {
            addCriterion("designer <", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerLessThanOrEqualTo(Integer value) {
            addCriterion("designer <=", value, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerIn(List<Integer> values) {
            addCriterion("designer in", values, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotIn(List<Integer> values) {
            addCriterion("designer not in", values, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerBetween(Integer value1, Integer value2) {
            addCriterion("designer between", value1, value2, "designer");
            return (Criteria) this;
        }

        public Criteria andDesignerNotBetween(Integer value1, Integer value2) {
            addCriterion("designer not between", value1, value2, "designer");
            return (Criteria) this;
        }

        public Criteria andMaterialShopIsNull() {
            addCriterion("material_shop is null");
            return (Criteria) this;
        }

        public Criteria andMaterialShopIsNotNull() {
            addCriterion("material_shop is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialShopEqualTo(Integer value) {
            addCriterion("material_shop =", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopNotEqualTo(Integer value) {
            addCriterion("material_shop <>", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopGreaterThan(Integer value) {
            addCriterion("material_shop >", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopGreaterThanOrEqualTo(Integer value) {
            addCriterion("material_shop >=", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopLessThan(Integer value) {
            addCriterion("material_shop <", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopLessThanOrEqualTo(Integer value) {
            addCriterion("material_shop <=", value, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopIn(List<Integer> values) {
            addCriterion("material_shop in", values, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopNotIn(List<Integer> values) {
            addCriterion("material_shop not in", values, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopBetween(Integer value1, Integer value2) {
            addCriterion("material_shop between", value1, value2, "materialShop");
            return (Criteria) this;
        }

        public Criteria andMaterialShopNotBetween(Integer value1, Integer value2) {
            addCriterion("material_shop not between", value1, value2, "materialShop");
            return (Criteria) this;
        }

        public Criteria andProprietorIsNull() {
            addCriterion("proprietor is null");
            return (Criteria) this;
        }

        public Criteria andProprietorIsNotNull() {
            addCriterion("proprietor is not null");
            return (Criteria) this;
        }

        public Criteria andProprietorEqualTo(Integer value) {
            addCriterion("proprietor =", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorNotEqualTo(Integer value) {
            addCriterion("proprietor <>", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorGreaterThan(Integer value) {
            addCriterion("proprietor >", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorGreaterThanOrEqualTo(Integer value) {
            addCriterion("proprietor >=", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorLessThan(Integer value) {
            addCriterion("proprietor <", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorLessThanOrEqualTo(Integer value) {
            addCriterion("proprietor <=", value, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorIn(List<Integer> values) {
            addCriterion("proprietor in", values, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorNotIn(List<Integer> values) {
            addCriterion("proprietor not in", values, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorBetween(Integer value1, Integer value2) {
            addCriterion("proprietor between", value1, value2, "proprietor");
            return (Criteria) this;
        }

        public Criteria andProprietorNotBetween(Integer value1, Integer value2) {
            addCriterion("proprietor not between", value1, value2, "proprietor");
            return (Criteria) this;
        }

        public Criteria andBuilderIsNull() {
            addCriterion("builder is null");
            return (Criteria) this;
        }

        public Criteria andBuilderIsNotNull() {
            addCriterion("builder is not null");
            return (Criteria) this;
        }

        public Criteria andBuilderEqualTo(Integer value) {
            addCriterion("builder =", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderNotEqualTo(Integer value) {
            addCriterion("builder <>", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderGreaterThan(Integer value) {
            addCriterion("builder >", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderGreaterThanOrEqualTo(Integer value) {
            addCriterion("builder >=", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderLessThan(Integer value) {
            addCriterion("builder <", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderLessThanOrEqualTo(Integer value) {
            addCriterion("builder <=", value, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderIn(List<Integer> values) {
            addCriterion("builder in", values, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderNotIn(List<Integer> values) {
            addCriterion("builder not in", values, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderBetween(Integer value1, Integer value2) {
            addCriterion("builder between", value1, value2, "builder");
            return (Criteria) this;
        }

        public Criteria andBuilderNotBetween(Integer value1, Integer value2) {
            addCriterion("builder not between", value1, value2, "builder");
            return (Criteria) this;
        }

        public Criteria andPushStatusIsNull() {
            addCriterion("push_status is null");
            return (Criteria) this;
        }

        public Criteria andPushStatusIsNotNull() {
            addCriterion("push_status is not null");
            return (Criteria) this;
        }

        public Criteria andPushStatusEqualTo(Integer value) {
            addCriterion("push_status =", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusNotEqualTo(Integer value) {
            addCriterion("push_status <>", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusGreaterThan(Integer value) {
            addCriterion("push_status >", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("push_status >=", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusLessThan(Integer value) {
            addCriterion("push_status <", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusLessThanOrEqualTo(Integer value) {
            addCriterion("push_status <=", value, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusIn(List<Integer> values) {
            addCriterion("push_status in", values, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusNotIn(List<Integer> values) {
            addCriterion("push_status not in", values, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusBetween(Integer value1, Integer value2) {
            addCriterion("push_status between", value1, value2, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andPushStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("push_status not between", value1, value2, "pushStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusIsNull() {
            addCriterion("\" business_status\" is null");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusIsNotNull() {
            addCriterion("\" business_status\" is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusEqualTo(Integer value) {
            addCriterion("\" business_status\" =", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusNotEqualTo(Integer value) {
            addCriterion("\" business_status\" <>", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusGreaterThan(Integer value) {
            addCriterion("\" business_status\" >", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("\" business_status\" >=", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusLessThan(Integer value) {
            addCriterion("\" business_status\" <", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusLessThanOrEqualTo(Integer value) {
            addCriterion("\" business_status\" <=", value, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusIn(List<Integer> values) {
            addCriterion("\" business_status\" in", values, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusNotIn(List<Integer> values) {
            addCriterion("\" business_status\" not in", values, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusBetween(Integer value1, Integer value2) {
            addCriterion("\" business_status\" between", value1, value2, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("\" business_status\" not between", value1, value2, "businessStatus");
            return (Criteria) this;
        }

        public Criteria andGmtPublishIsNull() {
            addCriterion("gmt_publish is null");
            return (Criteria) this;
        }

        public Criteria andGmtPublishIsNotNull() {
            addCriterion("gmt_publish is not null");
            return (Criteria) this;
        }

        public Criteria andGmtPublishEqualTo(Date value) {
            addCriterion("gmt_publish =", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishNotEqualTo(Date value) {
            addCriterion("gmt_publish <>", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishGreaterThan(Date value) {
            addCriterion("gmt_publish >", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_publish >=", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishLessThan(Date value) {
            addCriterion("gmt_publish <", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishLessThanOrEqualTo(Date value) {
            addCriterion("gmt_publish <=", value, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishIn(List<Date> values) {
            addCriterion("gmt_publish in", values, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishNotIn(List<Date> values) {
            addCriterion("gmt_publish not in", values, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishBetween(Date value1, Date value2) {
            addCriterion("gmt_publish between", value1, value2, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andGmtPublishNotBetween(Date value1, Date value2) {
            addCriterion("gmt_publish not between", value1, value2, "gmtPublish");
            return (Criteria) this;
        }

        public Criteria andSysCodeIsNull() {
            addCriterion("sys_code is null");
            return (Criteria) this;
        }

        public Criteria andSysCodeIsNotNull() {
            addCriterion("sys_code is not null");
            return (Criteria) this;
        }

        public Criteria andSysCodeEqualTo(String value) {
            addCriterion("sys_code =", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotEqualTo(String value) {
            addCriterion("sys_code <>", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeGreaterThan(String value) {
            addCriterion("sys_code >", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_code >=", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLessThan(String value) {
            addCriterion("sys_code <", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLessThanOrEqualTo(String value) {
            addCriterion("sys_code <=", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLike(String value) {
            addCriterion("sys_code like", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotLike(String value) {
            addCriterion("sys_code not like", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeIn(List<String> values) {
            addCriterion("sys_code in", values, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotIn(List<String> values) {
            addCriterion("sys_code not in", values, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeBetween(String value1, String value2) {
            addCriterion("sys_code between", value1, value2, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotBetween(String value1, String value2) {
            addCriterion("sys_code not between", value1, value2, "sysCode");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}