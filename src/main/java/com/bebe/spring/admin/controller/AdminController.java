package com.bebe.spring.admin.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bebe.spring.admin.service.AdminService;
import com.bebe.spring.utils.UploadFileUtils;
import com.bebe.spring.vo.OptionsVO;
import com.bebe.spring.vo.OrdersSearchVO;
import com.bebe.spring.vo.OrdersVO;
import com.bebe.spring.vo.ProductOptionsVO;
import com.bebe.spring.vo.ProductSearchVO;
import com.bebe.spring.vo.UsersVO;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

	@Resource(name = "adminService")
	private AdminService adminService;

	@Resource(name = "uploadPath")
	private String uploadPath;

	// -------------------------------------------- 관리자 정보 페이지
	// --------------------------------------------
	// 관리자 정보 페이지 첫 화면
	@RequestMapping(value = "/admin_info", method = RequestMethod.GET)
	public String adminInfoGet(HttpServletRequest req) {
		System.out.println("관리자 인포 GET 진입");

		HttpSession session = req.getSession();
		session.setAttribute("id", "admin123");

		return "/admin/admin_info";
	}

	// 관리자 정보 페이지에서 비밀번호 변경 눌렀을때
	@RequestMapping(value = "/admin_info", method = RequestMethod.POST)
	public ModelAndView adminInfoPost(String passwd) {
		System.out.println("관리자 인포 POST 진입");

		adminService.updatePasswd(passwd);
		ModelAndView mv = new ModelAndView("/admin/admin_info");
		if (adminService.updatePasswd(passwd) > 0)
			mv.addObject("success", "1");
		else
			mv.addObject("success", "0");

		return mv;
	}

	// -------------------------------------------- 주문 관리 페이지
	// --------------------------------------------
	@RequestMapping(value = "/order_mng", method = RequestMethod.GET)
	public String adminOrdermngGet() {
		System.out.println("관리자 주문관리 GET 진입");
		return "/admin/order_mng";
	}

	// 주문 관리 페이지에서 검색버튼 눌렀을때
	@RequestMapping(value = "/order_mng", method = RequestMethod.POST)
	public ModelAndView adminOrdermngPost(OrdersSearchVO osVo) {
		System.out.println("관리자 주문관리 POST 진입");

		List<OrdersVO> oVo = adminService.selectOrdersList(osVo);

		ModelAndView mv = new ModelAndView("/admin/order_mng");
		mv.addObject("list", oVo);

		return mv;
	}

	// 주문 관리 페이지에서 배송 수정버튼 눌렀을때
	@RequestMapping(value = "/sts_update", method = RequestMethod.POST)
	public ModelAndView adminOrderStsPost(OrdersVO orderVo) {
		System.out.println("관리자 주문관리 POST 진입");

		adminService.updateOrderSts(orderVo);

		ModelAndView mav = new ModelAndView("/admin/order_mng");
		return mav;
	}

	// -------------------------------------------- 상품 추가 페이지
	// --------------------------------------------
	// 상품 추가 페이지 첫 화면
	@RequestMapping(value = "/product_add", method = RequestMethod.GET)
	public String adminProductaddGet() {
		System.out.println("관리자 상품추가 GET 진입");
		return "/admin/product_add";
	}

	// 상품 추가 페이지에서 추가버튼 눌렀을 때
	@RequestMapping(value = "/product_add", method = RequestMethod.POST)
	public String adminProductaddPost(ProductOptionsVO productOpVo, MultipartFile[] file) throws Exception {
		System.out.println("관리자 상품관리 POST 진입");

		// 파일 업로드 처리 로직
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
//		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;


		for (int i = 0; i < file.length; i++) {
			if (file[i].getOriginalFilename() != null && file[i].getOriginalFilename() != "") {
				fileName = UploadFileUtils.fileUpload(imgUploadPath, file[i].getOriginalFilename(), file[i].getBytes());
			} else {
				fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
			}
			System.out.println("여기까지 성공");
			if (i == 0)
				productOpVo.setProductImg1(File.separator + "imgUpload" +  File.separator + fileName);
			else if (i == 1)
				productOpVo.setProductImg2(File.separator + "imgUpload" +  File.separator + fileName);
			else if (i == 2)
				productOpVo.setProductImg3(File.separator + "imgUpload" + File.separator + fileName);
			else if (i == 3)
				productOpVo.setProductImg4(File.separator + "imgUpload" +  File.separator + fileName);
		}

		System.out.println(productOpVo.toString());
		adminService.insertProduct(productOpVo);

		return "/admin/product_add";
	}

	// -------------------------------------------- 재고 관리 페이지
	// --------------------------------------------
	// 재고 관리 페이지 첫 화면
	@RequestMapping(value = "/product_mng", method = RequestMethod.GET)
	public String adminPoductmngGet() {
		System.out.println("관리자 재고관리 GET 진입");
		return "/admin/product_mng";
	}

	@RequestMapping(value = "/product_mng", method = RequestMethod.POST)
	public String adminPoductmngPost() {
		System.out.println("관리자 재고관리 POST 진입");
		return "/admin/product_mng";
	}

	// 재고관리 페이지에서 재고 검색했을때

	@RequestMapping(value = "/searchproduct", method = RequestMethod.POST)
	public ModelAndView adminPoductmngSearchPost(ProductSearchVO psVo) {
		System.out.println("관리자 재고관리 POST 진입");
		Map<String, Object> map = new HashMap<>();
		map.put("productlist", adminService.selectProductList(psVo));
		map.put("optionlist", adminService.selectOptions());
		map.put("psVo", psVo);

		System.out.println("관리자 재고관리 POST 진입3");
		ModelAndView mv = new ModelAndView("/admin/product_mng");
		mv.addObject("map", map);

		return mv;
	}

	// 재고 관리 페이지 - 삭제 버튼 눌렀을 때
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public ModelAndView adminPoductmngDeletePost(int[] checkproductno, ProductSearchVO psVo) {
		System.out.println(checkproductno);
		adminService.deleteProduct(checkproductno);
		adminService.deleteOptions(checkproductno);

		HashMap<String, Object> map = new HashMap<>();
		map.put("productlist", adminService.selectProductList(psVo));
		map.put("optionlist", adminService.selectOptions());

		ModelAndView mv = new ModelAndView("/admin/product_mng");
		mv.addObject("map", map);
		return mv;
	}

	// 재고관리 페이지 - 옵션추가 버튼 눌렀을때
	@RequestMapping(value = "/productMngOption", method = RequestMethod.GET)
	public ModelAndView popupGet(@RequestParam(value = "pno") int pNo, @RequestParam(value = "pname") String pName) {
		System.out.println("옵션추가버튼 매핑완료");
		ModelAndView mav = new ModelAndView("/admin/product_mng_option");
		mav.addObject("pNo", pNo);
		mav.addObject("pName", pName);
		return mav;
	}

	// 옵션 추가 팝업 - 추가 버튼 눌렀을때
	@RequestMapping(value = "/productMngOptionAdd", method = RequestMethod.POST)
	public String popupPost(OptionsVO optionsVo, HttpServletRequest request) {
		System.out.println("옵션추가진행");
		adminService.insertOptions(optionsVo);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	// -------------------------------------------- 유저 관리 페이지
	// --------------------------------------------
	// 유저 관리 페이지 첫 화면
	@RequestMapping(value = "/user_mng", method = RequestMethod.GET)
	public ModelAndView adminUsermngGet() {
		System.out.println("관리자 유저 관리 GET 진입");
		List<UsersVO> userlist = adminService.selectUsers();
		ModelAndView mv = new ModelAndView("/admin/user_mng");
		mv.addObject("userlist", userlist);
		return mv;
	}

	// 유저 관리 페이지 - 삭제 버튼 눌렀을 때
	@RequestMapping(value = "/user_mng", method = RequestMethod.POST)
	public ModelAndView adminUsermngPost(String id) {
		System.out.println("관리자 유저관리 POST 진입");
		adminService.deleteUser(id);
		List<UsersVO> userlist = adminService.selectUsers();
		ModelAndView mv = new ModelAndView("/admin/user_mng");
		mv.addObject("userlist", userlist);
		return mv;
	}

}
