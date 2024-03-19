package kr.happyjob.study.scm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import kr.happyjob.study.scm.model.CategoryInfoModel;
import kr.happyjob.study.scm.model.GetWarehouseModel;
import kr.happyjob.study.scm.model.MainProductInfoModel;
import kr.happyjob.study.scm.model.MainProductModalModel;
import kr.happyjob.study.scm.model.SupplierInfoModel;
import kr.happyjob.study.scm.service.MainProductInfoService;

@Controller
@RequestMapping("/scm")
public class MainProductInfoController {
	
  @Autowired
  MainProductInfoService mainProductInfoService;
  
  private static final Logger logger = LoggerFactory.getLogger(MainProductInfoController.class);
  private final String className = this.getClass().toString();
  
  @RequestMapping("mainProductInfo.do")
  public String initMainProductInfo(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession Session) throws Exception {
    
	  logger.info("+ Start " + className + ".initMainProductInfo");
	  logger.info("   - paramMap : " + paramMap);    
	  logger.info("+ End " + className + ".initMainProductInfo");
    
    return "scm/mainProductInfo";
  }  
  
  @RequestMapping("listMainProduct.do")
  @ResponseBody
  public Map<String, Object> productList(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
	  
	   	logger.info("+ Start " + className + ".listMainProduct");
	    logger.info("   - paramMap : " + paramMap);
	    
	    int currentPage = Integer.parseInt((String) paramMap.get("currentPage")) ; // 현재 페이지 번호
	    int pageSize = Integer.parseInt((String) paramMap.get("pageSize")); // 페이지 사이즈
	    int pageIndex = (currentPage - 1) * pageSize; // 페이지 시작 row 번호
	    
	    paramMap.put("pageIndex", pageIndex);
	    paramMap.put("pageSize", pageSize);
	    
	    // 제품 목록 조회
	    List<MainProductInfoModel> listMainProductModel = mainProductInfoService.listMainProduct(paramMap);
	    model.addAttribute("listMainProductModel", listMainProductModel);
	    
	    //창고 정보 조회
	    //GetWarehouseModel warehouseInfo = mainProductInfoService.getWarehouseInfo(paramMap);
	    
	    // 제품 목록 카운트 조회
	    int totalCount = mainProductInfoService.totalCntMainProduct(paramMap);
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    
	    resultMap.put("productList", listMainProductModel);
	    //resultMap.put("warehouseInfo", warehouseInfo);
	    resultMap.put("totalCount", totalCount);
	    resultMap.put("currentPage", currentPage);
	    resultMap.put("pageSize", pageSize);
	    
	    logger.info("+ End " + className + ".listMainProduct");	  
	  
	  return resultMap;
  }
  
  // 제품 조회
  /*@RequestMapping("searchList.do")
  @ResponseBody
  public Map<String, Object> searchMainProduct(Model model, @RequestBody Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    
    logger.info("+ Start " + className + ".searchProductList");
    logger.info("   - paramMap : " + paramMap);
    
    System.out.println(paramMap.toString());
    
    int currentPage = (int) paramMap.get("currentPage"); // 현재 페이지 번호
    int pageSize = (int) paramMap.get("pageSize"); // 페이지 사이즈
    int pageIndex = (currentPage - 1) * pageSize; // 페이지 시작 row 번호
    
    paramMap.put("pageIndex", pageIndex);
    paramMap.put("pageSize", pageSize);
    
    // 제품 목록 조회
    List<MainProductInfoModel> searchMainProduct = mainProductInfoService.searchMainProduct(paramMap);
    model.addAttribute("searchMainProduct", searchMainProduct);
    
    int totalCount = mainProductInfoService.totalCntMainProduct(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    
    resultMap.put("searchList", searchMainProduct);
    resultMap.put("totalCount", totalCount);
    resultMap.put("currentPage", currentPage);
    resultMap.put("pageSize", pageSize);
    resultMap.put("pageIndex", pageIndex);
    
    logger.info("+ End " + className + ".searchProductList");
    
    return resultMap;
    
  }  */
  
  /*// 제품정보 관리 조회
  @RequestMapping("selectMainProduct.do")
  @ResponseBody
  public Map<String, Object> selectMainProduct(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    logger.info("+ Start " + className + ".selectMainProduct");
    logger.info("   - paramMap : " + paramMap);
    
    String result = "SUCCESS";
    String resultMsg = "조회 되었습니다.";
    
    MainProductInfoModel mainProductInfoModel = mainProductInfoService.selectMainProduct(paramMap);
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    resultMap.put("mainProductInfoModel", mainProductInfoModel);
    
    logger.info("+ End " + className + ".selectMainProduct");
    
    System.out.println(resultMap);
    return resultMap;
  }
  */
  @RequestMapping("saveMainProduct.do")
  @ResponseBody
  public Map<String, Object> saveMainProduct(Model model, MultipartFile file, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    logger.info("+ Start " + className + ".saveMainProduct");
    logger.info("   - paramMap : " + paramMap);
    
   // String fileName = file.getOriginalFilename();
    
    String action = (String) paramMap.get("action");
    
    String result = "SUCCESS";
    String resultMsg = "";
    
    // 등록
    int saveResult = mainProductInfoService.insertMainProduct(paramMap, request);
    
    if (saveResult == 0) {
    	
    	result = "FAIL";
      	resultMsg = "중복된 코드입니다.";
      	
    } else{
    	
    	resultMsg = "등록 완료"; 
    	
    }
    
    
    
    /*if ("I".equals(action)) {
      // 등록
      int saveResult = mainProductInfoService.insertMainProduct(paramMap, request);
      if (saveResult == 0) {
        result = "FAIL";
        resultMsg = "중복된 코드입니다.";
      } else{
      resultMsg = "등록 완료"; }
    } else if ("U".equals(action)) {
      // 수정
      mainProductInfoService.updateMainProduct(paramMap, request);
      resultMsg = "수정 완료";
    } else if ("D".equals(action)) {
      // 삭제
      mainProductInfoService.deleteMainProduct(paramMap);
      resultMsg = "삭제 완료";
    } else {
      result = "FALSE";
      resultMsg = "저장 실패";
    }*/
    
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("result", result);
    resultMap.put("resultMsg", resultMsg);
    
    logger.info("+ End " + className + ".saveMainProduct");
    
    return resultMap;
  } 
  
 //창고정보 조회
  @RequestMapping("getWarehouseInfo.do")
  @ResponseBody
  public Map<String, Object> getWarehouseInfo(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
    
	  logger.info("+ Start " + className + ".getWarehouseInfo");
	  logger.info("   - paramMap : " + paramMap);
	  
    /*GetWarehouseModel warehouseInfo = mainProductInfoService.getWarehouseInfo(paramMap);*/
	  //List<GetWarehouseModel> warehouseInfo = mainProductInfoService.getWarehouseInfo();
	  List<CategoryInfoModel> categoryInfo = mainProductInfoService.getCategoryInfo();
	  List<SupplierInfoModel> supplierInfo = mainProductInfoService.getSupplierInfo();
	  
    //model.addAttribute("warehouseInfo",warehouseInfo);
 
    Map<String, Object> resultMap = new HashMap<String, Object>();
    //resultMap.put("warehouseInfo", warehouseInfo);
    resultMap.put("categoryInfo", categoryInfo);
    resultMap.put("supplierInfo", supplierInfo);
    
    logger.info("+ End " + className + ".getWarehouseInfo");
    
    return resultMap;
  }
  
  // 제품 상세정보 조회
  @RequestMapping("mainProductModal.do")
  @ResponseBody
  public Map<String, Object> mainProductModal(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
	  
	  logger.info("+ Start " + className + ".mainProductModal");
	  logger.info("   - paramMap : " + paramMap);
    
	  String result = "SUCCESS";
	  String resultMsg = "조회 되었습니다.";
    
	  MainProductModalModel mainProductModalModel = mainProductInfoService.mainProductModal(paramMap);
	  
	  System.out.println(mainProductModalModel.toString());
	  System.out.println();
	  
	  Map<String, Object> resultMap = new HashMap<String, Object>();
	  resultMap.put("result", result);
	  resultMap.put("resultMsg", resultMsg);
	  resultMap.put("mainProductModalModel", mainProductModalModel);
    
	  logger.info("+ End " + className + ".mainProductModal");
    
	  System.out.println(resultMap);
	  return resultMap;
	  
  }
  
  //제품 정보 수정
  @RequestMapping("modifyProduct.do")
  @ResponseBody
  public Map<String, Object> modifyProduct(@RequestParam Map<String , Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
	  logger.info("+ Start " + className + ".modifyProduct");
	  logger.info("  - paramMap: " + paramMap);
	  
	  String result = "S	UCCESS";
	  String resultMsg = "수정 되었습니다.";
	  
	  int modifyProduct = mainProductInfoService.updateMainProduct(paramMap, request);
	  
	  System.out.println(modifyProduct);
	  
	  Map<String, Object> resultMap = new HashMap<String, Object>();
	  resultMap.put("result",result);
	  resultMap.put("resultMsg", resultMsg);
	  resultMap.put("deleteProduct", modifyProduct);
	  
	  return resultMap;
	  
  }
  
  
  //제품 정보 삭제
  @RequestMapping("deleteProduct.do")
  @ResponseBody
  public Map<String, Object> deleteProduct(@RequestParam Map<String , Object> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
	  
	  logger.info("+ Start " + className + ".deleteProduct");
	  logger.info("  - paramMap: " + paramMap);
	  
	  String result = "S	UCCESS";
	  String resultMsg = "삭제 되었습니다.";
	  
	  int deleteProduct = mainProductInfoService.deleteMainProduct(paramMap);
	  
	  Map<String, Object> resultMap = new HashMap<String, Object>();
	  resultMap.put("result",result);
	  resultMap.put("resultMsg", resultMsg);
	  resultMap.put("deleteProduct", deleteProduct);
	  
	  return resultMap;
  }
  

}
