<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:forEach items="${vo.productsCostTypeList}" var="cost">
    <li class="treeview ">
        <a href="#">
            <span class="fa_title">${cost.costTypeName}：</span>
            <span class="fa_content">￥<fmt:formatNumber type="number" value="${cost.totalPrice}" pattern="0.00" maxFractionDigits="2"/>${cost.salePriceValueName}</span>
            <i class="fa_icon "></i>
            <i class="fa fa-angle-left pull-right"></i>
        </a>
        <ul class="treeview-menu">
            <c:forEach items="${cost.detailList}" var="productCost">
                <li>
                    <a href="#">
                        <span class="fa_title">${productCost.costTypeName}：</span>
                        <span class="fa_content">￥<fmt:formatNumber type="number" value="${productCost.totalPrice}" pattern="0.00" maxFractionDigits="2"/>${salePriceValueName}</span>
                        <i class="fa_icon "></i>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <c:forEach items="${productCost.detailList}" var="productCostDetail">
                            <dl class="list_menu">
                                <dt><img src="${vo.resourceUrl}${productCostDetail.productPicPath}" alt="" class="" ></dt>
                                <dd><p class="menu_left">品名：</p><p class="menu_right" name="productName">${productCostDetail.productName}</p></dd></br>
                                <dd><p class="menu_left">品牌：</p><p class="menu_right" name="brandName">${productCostDetail.brandName}</p></dd></br>
                                <dd><p class="menu_left">单价：</p><p class="menu_right" name="unitPrice"><fmt:formatNumber type="number" value="${productCostDetail.unitPrice}" pattern="0.00" maxFractionDigits="2"/>${productCostDetail.productUnit}</p></dd></br>
                                <dd><p class="menu_left">数量：</p><p class="menu_right" name="count">${productCostDetail.count}</p></dd></br>
                                <dd><p class="menu_left">金额：</p><p class="menu_right" name="totalPrice"><fmt:formatNumber type="number" value="${productCostDetail.totalPrice}" pattern="0.00" maxFractionDigits="2"/>元</p></dd>
                                <input type="hidden" name="productModelNumber" value="${productCostDetail.productModelNumber}"/>
                                <input type="hidden" name="productSpec" value="${productCostDetail.productSpec}"/>
                                <input type="hidden" name="productDesc" value="${productCostDetail.productDesc}"/>
                                <input type="hidden" name="productOriginalPicPath" value="${vo.resourceUrl}${productCostDetail.productOriginalPicPath}"/>
                            </dl><div class="clear"></div>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </li>
</c:forEach>