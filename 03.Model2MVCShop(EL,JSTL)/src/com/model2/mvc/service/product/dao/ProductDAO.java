package com.model2.mvc.service.product.dao;

import java.sql.*;
import java.util.*;


import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.domain.*;

public class ProductDAO {

	// ����Ʈ ������
	public ProductDAO() {
	}

	// ��ǰ��� �޼���
	public void insertProduct(ProductVO productVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values(seq_product_prod_no.nextval,?,?,to_date(?, 'YYYY/MM/DD'),?,?,sysdate)";
		System.out.println("::: insertProduct ::: ");
		PreparedStatement stmt = con.prepareStatement(sql);
		// stmt.setInt(1, productVO.getProdNo());//��ǰ��ȣ(������ ���)
		stmt.setString(1, productVO.getProdName());// ��ǰ�̸�
		stmt.setString(2, productVO.getProdDetail());// ������
		stmt.setString(3, productVO.getManuDate());// ��������
		stmt.setInt(4, productVO.getPrice());// ��ǰ����
		stmt.setString(5, productVO.getFileName());// ��ǰ �̹��������̸�
		// stmt.setDate(7,productVO.getRegDate());//��ǰ������� sysdate�̿�??
		stmt.executeQuery();

		con.close();
	}

	// ��ǰ������ ��ȸ �޼���
	public ProductVO findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select  prod_no, prod_name, prod_detail, image_file,"
				+ " Manufacture_Day, Price, Reg_date from product  where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		//System.out.println("DAO"+prodNo);
		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = new ProductVO();

		while (rs.next()) {
			//System.out.println("Prod_NO");
			productVO.setProdNo(rs.getInt("prod_no"));// ��ǰ��ȣ
			productVO.setProdName(rs.getString("prod_name"));// ��ǰ��
			productVO.setFileName(rs.getString("image_file"));// �̹�������
			productVO.setProdDetail(rs.getString("Prod_Detail"));
			productVO.setManuDate(rs.getString("Manufacture_Day"));
			productVO.setPrice(rs.getInt("Price"));
			productVO.setRegDate(rs.getDate("Reg_Date"));

		}
		
		con.close();
		
		return productVO;
	}

	// ��ǰ �����ȸ �޼���
	public Map<String, Object> getProductList(Search search) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

		String sql = "select distinct p.prod_no, p.prod_name, p.price, p.reg_date, t.tran_status_code "
				+ " from product p, transaction t"
				+ " where p.prod_no=t.prod_no(+)";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE Prod_No LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE Prod_Name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if(search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")){
				sql +=" WHERE Price LIKE '%"+search.getSearchKeyword() +"%'";
			}
		}
		sql += " ORDER BY prod_NO";
		System.out.println("ProductDAO :: SQL  : "+sql);
		
		//��ü �Խù��� 
		int totalCount= this.getTotalCount(sql);
		
		//���� �������� �Խù� �޵��� ���� �ٽ�
		sql=makeCurrentPageSql(sql,search);
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println("���� ��ġ==> "+search);
		
		
		List<ProductVO> list=new ArrayList<ProductVO>();
		
		while(rs.next()) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));// ��ǰ��ȣ
				vo.setProdName(rs.getString("PROD_NAME"));// ��ǰ��
				vo.setPrice(rs.getInt("PRICE"));// ����
				vo.setRegDate(rs.getDate("REG_DATE"));// �����
				vo.setProTranCode(rs.getString("tran_status_code"));//��ǰ�����ڵ�
				list.add(vo);
				
			}
		//totalCount ���� ����
		map.put("list", list);
		
		//current page��������
		map.put("totalCount", totalCount);
		System.out.println("list.size() : " + list.size());
		System.out.println("totalCount: "+totalCount);
		
		
		rs.close();
		stmt.close();
		con.close();

		return map;
	}

	private int getTotalCount(String sql)throws Exception {
		
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

	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + " FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ " WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}

	// ��ǰ���� ���� �޼���
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con=DBUtil.getConnection();
		System.out.println("����� ������Ʈ �޼ҵ�");
		String sql ="update product set prod_name=?, prod_detail=?, "
				+ "manufacture_day=?, price=?, image_file=? where prod_no=?";
		
		PreparedStatement stmt=con.prepareStatement(sql);
		
		
		
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		stmt.executeUpdate();
		con.close();
	}

}
