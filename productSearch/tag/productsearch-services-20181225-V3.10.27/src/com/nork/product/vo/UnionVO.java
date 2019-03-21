package com.nork.product.vo;




import com.nork.product.model.BaseCompany;
import com.nork.product.model.CompanyUnion;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:21 2017/12/2 0002
 * @Modified By:
 */
public class UnionVO extends CompanyUnion implements Serializable {

    /**
     * 企业信息集合
     */
    private List<BaseCompany> companys;

    public List<BaseCompany> getCompanys() {
        return companys;
    }

    public void setCompanys(List<BaseCompany> companys) {
        this.companys = companys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UnionVO unionVO = (UnionVO) o;

        return companys != null ? companys.equals(unionVO.companys) : unionVO.companys == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (companys != null ? companys.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UnionVO{" +
                "companys=" + companys +
                '}';
    }
}
