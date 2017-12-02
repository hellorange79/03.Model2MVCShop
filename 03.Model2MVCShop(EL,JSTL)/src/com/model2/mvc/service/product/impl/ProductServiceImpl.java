package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO(); 
	}
	
	@Override
	//��ǰ���
	public void addProduct(ProductVO productVO) throws Exception {
		
		productDAO.insertProduct(productVO);
		
	}

	@Override
	//������
	public ProductVO getProduct(int prodNo) throws Exception {
		//System.out.println(prodNo);
		return productDAO.findProduct(prodNo);
	}

	@Override
	//��ǰ���
	public HashMap<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return (HashMap<String, Object>)productDAO.getProductList(search);
	}

	@Override
	//��ǰ����
	public void updateProduct(ProductVO productVO) throws Exception {
		System.out.println(productVO);
		productDAO.updateProduct(productVO);
		
	}
	
	

}
