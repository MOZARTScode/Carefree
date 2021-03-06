package com.csu.carefree.Persistence;

import com.csu.carefree.Model.ProductDT.ProductMsg;

import java.util.List;

public interface ProductMapper {

    /*****************通过筛选条件获取产品*****************/


    List<ProductMsg> getProductList();

    ProductMsg getProductById(String id);

    //筛选条件：traver_days, product_type, supplier_id

    //行程天数
    List<ProductMsg> getProductListByTraverdays(String traverDays);

    //产品类型
    List<ProductMsg> getProductListByProductType(String productType);

    //供应商
    List<ProductMsg> getProductListBySupplierId(String supplierId);

    //产品名称
    List<ProductMsg> getProductListByName(String productName);

    //三个筛选条件都有
    List<ProductMsg> getProductListByThree(String traverDays, String productType, String supplierId);

    //*****************通过推荐条件来排序、推荐*****************/
    //注意：推荐条件是在筛选条件的基础上进行的，所以不能直接对数据库进行查找
    //推荐（排序）条件：score, sell_num, comments_num, product_grade
}
