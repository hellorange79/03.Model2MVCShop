
package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.*;

public class PurchaseDAO {

	// ����Ʈ ������
	public PurchaseDAO() {

	}

	//
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction " + " (tran_no, prod_no, buyer_id, payment_option, "
				+ " receiver_name, receiver_phone, dlvy_addr," 
				+ " dlvy_request, dlvy_date, "
				+ " Tran_status_code, order_date) "
				+ "VALUES(seq_transaction_tran_no.nextval,?," 
				+ " ?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'),?,sysdate)";

		PreparedStatement stmt = con.prepareStatement(sql);
		//System.out.println("PurchaseDAO ::" + sql);

		// tran_no �������� �޾ƿ�
		stmt.setString(2, purchaseVO.getBuyer().getUserId());//
		//System.out.println("�� ���̵�" + purchaseVO.getBuyer().getUserId());
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		//System.out.println("�� �ѹ�" + purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getDivyDate());
		stmt.setString(9, purchaseVO.getTranCode());
		// �ֹ��� sysdate ���
		stmt.executeQuery();

		con.close();

	}// end of addPurchase
	
	// ���Ż����� ��û
	public PurchaseVO findPurcahse(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, " + 
		" RECEIVER_NAME, RECEIVER_PHONE, DLVY_ADDR, "
				+ " DLVY_REQUEST, DLVY_DATE, ORDER_DATE "
				+ " FROM transaction WHERE PROD_NO=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct(prodNo);

		UserService userservice = new UserServiceImpl();
		
		
		PurchaseVO purchaseVO = new PurchaseVO();

		while (rs.next()) {
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));

			System.out.println("PurchaseDAO getPurchase :" + purchaseVO);

		}
		con.close();

		return purchaseVO;
	}// end of getPurchase
	
	public PurchaseVO findPurcahse2(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, " + 
		" RECEIVER_NAME, RECEIVER_PHONE, DLVY_ADDR, "
				+ " DLVY_REQUEST, DLVY_DATE, ORDER_DATE "
				+ " FROM transaction WHERE TRAN_NO=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductService productService = new ProductServiceImpl();
		

		UserService userservice = new UserServiceImpl();
		
		
		PurchaseVO purchaseVO = new PurchaseVO();

		while (rs.next()) {
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATE"));
			System.out.println("PurchaseDAO getPurchase :" + purchaseVO);

		}
		con.close();

		return purchaseVO;
	}// end of getPurchase
	
	
	//���Ÿ�� ��ȸ No, ȸ��ID, ȸ����, ��ȭ��ȣ, �����Ȳ, ��������
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		
		String sql="select  tran_no, prod_no, buyer_id, receiver_name, receiver_phone, tran_status_code "
				+ " from transaction where buyer_id = '"+buyerId+"'";
		
		System.out.println("buyerId:::::"+buyerId);
		//��ü �Խù��� 
		int totalCount= this.getTotalCount(sql);
		System.out.println("buyerId22:::::"+buyerId);
		//���� �������� �Խù� �޵��� ���� �ٽ�
		sql=makeCurrentPageSql(sql,search);
		
		PreparedStatement stmt=con.prepareStatement(sql);
				
		ResultSet rs = stmt.executeQuery();
		System.out.println("���� ��ġ==> "+search);
		
		
		List<PurchaseVO> list=new ArrayList<PurchaseVO>();
		
		ProductService productservice = new ProductServiceImpl();
		UserService userservice= new UserServiceImpl();
		
		
		while(rs.next()) {
			
			PurchaseVO purchaseVO= new PurchaseVO();
			
			purchaseVO.setTranNo(rs.getInt("tran_No"));
			purchaseVO.setPurchaseProd(productservice.getProduct(rs.getInt("prod_No")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("buyer_Id")));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			list.add(purchaseVO);
			//System.out.println("purchaseVO==>"+purchaseVO);
		}
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
	}// end of getPurchaseList

	private String makeCurrentPageSql(String sql, Search search) throws Exception{
		
		sql = "SELECT * " + " FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ " WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("purchaseDAO :: make SQL :: " + sql);

		return sql;
	}

	private int getTotalCount(String sql) throws Exception{
		//total ī��Ʈ ���� ���� ����
				sql ="SELECT COUNT(*) "+"FROM ( " + sql + " ) countable ";
				
				
				Connection con =DBUtil.getConnection();
				PreparedStatement Stmt = con.prepareStatement(sql);
				ResultSet rs = Stmt.executeQuery();

				int totalCount = 0;
				if (rs.next()) {
					totalCount = rs.getInt(1);
				}

				Stmt.close();
				con.close();
				rs.close();
				
				return totalCount;
	}
	
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con=DBUtil.getConnection();
		System.out.println("����� purchase������Ʈ �޼ҵ�");
		
		String sql="update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?,"
				+ " RECEIVER_PHONE=?, DLVY_ADDR=?, DLVY_REQUEST=?, DLVY_DATE=? "
				+ " WHERE tran_no=?";
		System.out.println("PurchaseDAO Update ==>"+purchaseVO);
		PreparedStatement stmt= con.prepareStatement(sql);
		
		
		//stmt.setString(1, purchaseVO.getBuyer().getUserId());
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7,purchaseVO.getTranNo());
		
		stmt.executeUpdate();
		
		
		con.close();
	}

	public void updateTranCode(PurchaseVO purchaseVO)throws Exception{

		Connection con=DBUtil.getConnection();
		System.out.println("TranCode �޼ҵ�");
		
		String sql="update transaction set tran_status_code=?"
				+ "WHERE prod_no=?";
				
		
		System.out.println("PurchaseDAO TranCode ==>"+purchaseVO);
		PreparedStatement stmt= con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getPurchaseProd().getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
		
	}
	

	
}// end of class