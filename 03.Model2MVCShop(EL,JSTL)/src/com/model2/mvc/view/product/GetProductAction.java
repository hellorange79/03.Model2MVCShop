package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action{
	
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		
		String menu=request.getParameter("menu");

		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		ProductVO productVO= service.getProduct(prodNo);
		
		request.setAttribute("productVO", productVO);
		
		
		if("manage".equals(menu)) {
		
			return "forward:/product/updateProductView.jsp?menu="+menu;
		}
		return "forward:/product/getProduct.jsp?menu="+menu;
	}

}