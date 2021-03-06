package com.csu.carefree.Service.impl;

import com.csu.carefree.Model.ProductDT.*;
import com.csu.carefree.Model.TraverAsk.TraverNote;
import com.csu.carefree.Model.TraverMsg.CityMsg;
import com.csu.carefree.Model.TraverMsg.ProvinceMsg;
import com.csu.carefree.Model.TraverMsg.ScenicMsg;
import com.csu.carefree.Persistence.*;
import com.csu.carefree.Service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *   Great by WLX
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private HotelMsgMapper hotelMsgMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CityMsgMapper cityMsgMapper;

    @Autowired
    private ScenicMsgMapper scenicMsgMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ProductCityMsgMapper productCityMsgMapper;

    @Autowired
    private ProductScenicMsgMapper productscenicMsgMapper;

    @Autowired
    private ProvinceMsgMapper provinceMsgMapper;

    @Autowired
    private StrategyMsgMapper strategyMsgMapper;

    @Autowired
    private TicketMsgMapper ticketMsgMapper;

    @Autowired
    private TraverNoteMapper traverNoteMapper;

    /*******************根据行程推荐景点、酒店、攻略、游记等**********/

    @Override
    public ArrayList<ScenicMsg> getRecommendScenicList(HttpSession session) {
        List<ScenicMsg> scenicList = this.getScenicMsgListByCityName((String) session.getAttribute("location") + "市");
        //对景点进行排序
        sortScenicList(scenicList);
        ArrayList<ScenicMsg> recommendScenicList = new ArrayList<>();
        if (scenicList.size() > 18) {
            recommendScenicList.addAll(scenicList.subList(0, 18));
        } else {
            recommendScenicList.addAll(scenicList);
        }
        return recommendScenicList;
    }

    @Override
    public ArrayList<HotelMsg> getRecommendHotelList(HttpSession session) {
        List<HotelMsg> hotelList = this.getHotelListByDestination((String) session.getAttribute("location") + "市");
        //根据酒店的评分对酒店进行排序
        sortHotelList(hotelList);
        ArrayList<HotelMsg> recommendHotelList = new ArrayList<>();
        if (hotelList.size() > 18) {
            recommendHotelList.addAll(hotelList.subList(0, 18));
        } else {
            recommendHotelList.addAll(hotelList);
        }
        return recommendHotelList;
    }

    @Override
    public ArrayList<StrategyMsg> getRecommendStrategyList(HttpSession session) {
        //首先通过城市信息找到相应的景点，在通过景点找到相应的攻略
//        List<ScenicMsg> recommendScenicList = this.getRecommendScenicList(session);
        ArrayList<StrategyMsg> strategyList = new ArrayList<>();
//        for (ScenicMsg s: recommendScenicList)
//            strategyList.add(this.getStrategyByScenicName(s.getName()));
        strategyList.addAll(this.getStrategyList());
        return strategyList;
    }

    @Override
    public ArrayList<TraverNote> getRecommendTraverNoteList(HttpSession session) {
        //先根据城市名得到ID
        String cityId = this.searchCityMsgByName((String) session.getAttribute("location") + "市").get(0).getId();
        System.out.println("cityID" + cityId);
        List<TraverNote> traverNoteList = this.getTraverNoteListByCityName(cityId);
        ArrayList<TraverNote> recommendTraverNoteList = new ArrayList<>();
        if (traverNoteList.size() > 18) {
            recommendTraverNoteList.addAll(traverNoteList.subList(0, 18));
        } else {
            recommendTraverNoteList.addAll(traverNoteList);
        }
        return recommendTraverNoteList;
    }

    @Override
    public void sortScenicList(List<ScenicMsg> sortScenicList) {
        //这里根据景点的热度进行排序，通过Collections提供的排序方法
        for (ScenicMsg scenicMsg : sortScenicList) {
            System.out.print(" " + scenicMsg.getName() + "：" + scenicMsg.getPopular_level() + "；");
        }
        sortScenicList.sort((o1, o2) -> {
            int i = (int) (10 * (Double.parseDouble(o2.getPopular_level()) - Double.parseDouble(o1.getPopular_level())));
            if (i == 0) {
                return 0;
            }
            return i;
        });
        System.out.println("**********************************");
        for (ScenicMsg scenicMsg : sortScenicList) {
            System.out.print(" " + scenicMsg.getName() + "：" + scenicMsg.getPopular_level() + "；");
        }

    }

    @Override
    public void sortHotelList(List<HotelMsg> sortHotelList) {
        for (HotelMsg hotel : sortHotelList) {
            System.out.print(" " + hotel.getName() + "：" + hotel.getScore() + "；");
        }
        sortHotelList.sort((o1, o2) -> {
            int i = (int) (10 * (Double.parseDouble(o2.getScore()) - Double.parseDouble(o1.getScore())));
            if (i == 0) {
                return 0;
            }
            return i;
        });
        System.out.println("**********************************");
        for (HotelMsg hotel : sortHotelList) {
            System.out.print(" " + hotel.getName() + "：" + hotel.getScore() + "；");
        }

    }

    @Override
    public void sortStrategyList(List<StrategyMsg> recommendStrategyList) {

    }

    @Override
    public void sortTraverNoteList(List<TraverNote> recommendTraverNoteList) {

    }


    /*******************获取热门信息**********/
    @Override
    public ArrayList<FullProductInfo> getHotProductList(HttpSession session) {
        List<ProductMsg> productList = this.getProductList();
        ArrayList<FullProductInfo> hotProductList = new ArrayList<>();
        //热门产品推荐指标
        for (int i = 0; i < productList.size(); i++) {
            break;
        }
        hotProductList.add(new FullProductInfo(productList.get(0)));
        hotProductList.add(new FullProductInfo(productList.get(1)));
        return hotProductList;
    }

    @Override
    public ArrayList<HotelMsg> getHotHotelList() {
        List<HotelMsg> hotelList = this.getHotelMsgList();
        ArrayList<HotelMsg> hotHotelList = new ArrayList<>();
        hotHotelList.add(hotelList.get(0));
        hotHotelList.add(hotelList.get(1));
        hotHotelList.add(hotelList.get(2));
        hotHotelList.add(hotelList.get(3));
        return hotHotelList;
    }

    @Override
    public ArrayList<TraverNote> getHotTraverNoteList() {
        List<TraverNote> TraverNoteList = this.getTraverNoteList();
        System.out.println("TraverNoteList包含个数：" + TraverNoteList.size());
        ArrayList<TraverNote> hotTraverNoteList = new ArrayList<>();
        hotTraverNoteList.add(TraverNoteList.get(0));
        //hotTraverNoteList.add(TraverNoteList.get(1));
        //hotTraverNoteList.add(TraverNoteList.get(2));
        return hotTraverNoteList;
    }

    /*******************酒店信息**********/
    @Override
    public List<HotelMsg> getHotelMsgList() {
        return hotelMsgMapper.getHotelMsgList();
    }

    @Override
    public HotelMsg getHotelMsg(String id) {
        return hotelMsgMapper.getHotelMsg(id);
    }

    @Override
    public List<HotelMsg> searchHotelMsgList(String keyword) {
        return searchHotelMsgList("%"+keyword+"%");
    }

    @Override
    public List<HotelMsg> getHotelListByDestination(String destination) {
        return hotelMsgMapper.getHotelListByDestination(destination);
    }

    @Override
    public List<ProductMsg> getProductList() {
        return productMapper.getProductList();
    }

    @Override
    public ProductMsg getProductById(String id) {
        return productMapper.getProductById(id);
    }

    @Override
    public List<ProductMsg> getProductListByTraverdays(String traverDays) {
        return productMapper.getProductListByTraverdays(traverDays);
    }

    @Override
    public List<ProductMsg> getProductListByProductType(String productType) {
        return productMapper.getProductListByProductType(productType);
    }

    @Override
    public List<ProductMsg> getProductListBySupplierId(String supplierId) {
        return productMapper.getProductListBySupplierId(supplierId);
    }

    @Override
    public List<ProductMsg> getProductListByName(String productName) {
        return productMapper.getProductListByName(productName);
    }

    @Override
    public List<ProductMsg> getProductListByThree(String traverDays, String productType, String supplierId) {
        return productMapper.getProductListByThree(traverDays, productType, supplierId);
    }

    @Override
    public List<Supplier> getSupplierList() {
        return supplierMapper.getSupplierList();
    }

    @Override
    public Supplier getSupplierByName(String name) {
        return supplierMapper.getSupplierByName(name);
    }

    @Override
    public ProvinceMsg getProvinceMsgById(String provinceId) {
        return provinceMsgMapper.getProvinceMsgById(provinceId);
    }

    @Override
    public CityMsg getCityMsgById(String cityId) {
        return cityMsgMapper.getCityMsgById(cityId);
    }

    @Override
    public List<CityMsg> searchCityMsgByName(String cityName) {
        return cityMsgMapper.searchCityMsgByName("%"+cityName+"%");
    }

    @Override
    public List<ScenicMsg> getScenicMsgListByCityId(String cityId) {
        return cityMsgMapper.getScenicMsgListById(cityId);
    }

    @Override
    public List<ScenicMsg> getScenicMsgListByCityName(String cityName) {
        return scenicMsgMapper.getScenicMsgListByCityName(cityName);
    }

    @Override
    public ScenicMsg getScenicMsgById(String scenicId) {
        return scenicMsgMapper.getScenicMsgById(scenicId);
    }

    @Override
    public List<ScenicMsg> getScenicMsgListByName(String name) {
        return scenicMsgMapper.getScenicMsgListByName(name);
    }

    @Override
    public List<ProductCityMsg> getProductCityList() {
        return productCityMsgMapper.getProductCityList();
    }

    @Override
    public List<ProductCityMsg> getAllDepartCityListByProductId(String productId) {
        return productCityMsgMapper.getAllDepartCityListByProductId(productId);
    }

    @Override
    public ProductCityMsg getDepartCityPrice(String productId, String cityName) {
        return productCityMsgMapper.getDepartCityPriceByProductId(productId, cityName);
    }

    @Override
    public List<ProductScenicMsg> getProductIdsByScenicName(String scenicName) {
        return productscenicMsgMapper.getProductIdsByScenicName(scenicName);
    }

    @Override
    public List<ProductScenicMsg> getScenicNamesByProductId(String productId) {
        return productscenicMsgMapper.getScenicNamesByProductId(productId);
    }

    /*******************攻略信息**********/

    @Override
    public List<StrategyMsg> getStrategyList() {
        return strategyMsgMapper.getStrategyList();
    }

    @Override
    public StrategyMsg getStrategyByScenicName(String scenicName) {
        return strategyMsgMapper.getStrategyByScenicName(scenicName);
    }

    /*******************门票信息**********/

    @Override
    public List<TicketMsg> getTicketList() {
        return ticketMsgMapper.getTicketList();
    }

    @Override
    public List<TicketMsg> getTicketListByScenicName(String scenicName) {
        return ticketMsgMapper.getTicketListByScenicName(scenicName);
    }

    @Override
    public List<TicketMsg> getTicketListByCityName(String cityId) {
        return ticketMsgMapper.getTicketListByCityName(cityId);
    }

    /*******************游记信息**********/

    @Override
    public List<TraverNote> getTraverNoteList() {
        return traverNoteMapper.getTraverNoteList();
    }

    @Override
    public List<TraverNote> searchTraverNoteList(String keyword) {
        return traverNoteMapper.searchTraverNoteList("%"+keyword+"%");
    }

    @Override
    public TraverNote getTraverNoteById(String traverNoteId) {
        return traverNoteMapper.getTraverNoteById(traverNoteId);
    }

    @Override
    public List<TraverNote> getTraverNoteListByCityName(String cityId) {
        return traverNoteMapper.getTraverNodeListbyCityName(cityId);
    }
}
