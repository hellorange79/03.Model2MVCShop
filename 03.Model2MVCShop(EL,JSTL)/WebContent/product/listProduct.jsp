<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=euc-kr" %>

<%--@page import="com.model2.mvc.common.util.CommonUtil"--%>
<%--@page import="com.model2.mvc.view.product.ListProductAction"--%>
<%--@page import="com.model2.mvc.service.product.vo.*"--%>
<%--@page import="com.model2.mvc.common.*"--%>
<%--@page import="java.util.*"--%>
<%--@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%--
	List<ProductVO> list =(List<ProductVO>)request.getAttribute("list");
	String menu = request.getParameter("menu");
	Page resultPage=(Page)request.getAttribute("resultPage");
	Search search=(Search)request.getAttribute("search");
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	
	--%>
	

<html>
<head>


<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetUserList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listProduct.do?menu=${param.menu}"
			method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">
								<c:if test="${param.menu eq 'manage'}" > ��ǰ������ȸ</td></c:if>
								<c:if test="${param.menu eq 'search'}"> ��ǰ�����ȸ </td></c:if>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>

					<td align="right"><select name="searchCondition"
						class="ct_input_g" style="width: 80px">

							<option value="0" ${! empty searchCondition.equals && searchCondition.equals==0 ? "selected" : "" }>��ǰ��ȣ</option>
							<option value="1" ${! empty searchCondition.equals && searchCondition.equals==1? "selected" : "" }>��ǰ��</option>
							<option value="2" ${! empty searchCondition.equals && searchCondition.equals==2 ? "selected" : "" }>��ǰ����</option>
					</select> <input type="text" name="searchKeyword"
						value="${! empty search.searchKeyword ? search.searchKeyword : "" }" class="ct_input_g"
						style="width: 200px; height: 19px" /></td>

					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">

							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>

								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;"><a
									href="javascript:fncGetUserList('1');">�˻�</a>
								</td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�,	���� ${resultPage.currentPage} ������
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��ǰ��</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				<%--
					int no = list.size();
					for (int i = 0; i < list.size(); i++) {
						ProductVO vo = (ProductVO) list.get(i);
				
				<tr class="ct_list_pop">
					<td align="center"><%=i+1%></td>
					<td></td>
					<td align="left">
						<%
							if ("manage".equals(menu)) {
						%> <a
						href="/updateProductView.do?menu=<%=menu%>&prodNo=<%=vo.getProdNo()%>"><%=vo.getProdName()%></a>
					</td>

					<%
						} else if("search".equals(menu)) {
					%>
					<a href="/getProduct.do?menu=<%=menu%>&prodNo=<%=vo.getProdNo()%>"><%=vo.getProdName()%></a>
					</td>
					<%
						}
					--%>
					<c:set var="i" value="0"/>
					<c:forEach var="productVO" items="${list}">
					<c:set var="i" value="${i+1}"/>
					<tr class="ct_list_pop">
					<td align="center">${ i }</td>
					<td></td>
					<td align="left">
					<c:if test="${param.menu eq 'null'}" >
					<a href="/getProduct.do?menu=${param.menu}&prodNo=${productVO.prodNo}">${productVO.prodName}</a>
					</c:if>
					<c:if test="${param.menu eq 'manage'}" >
					<a href="/updateProductView.do?menu=${param.menu}&prodNo=${productVO.prodNo}">${productVO.prodName}</a>
					</c:if>
					<c:if test="${param.menu eq 'search'}">
					<a href="/getProduct.do?menu=${param.menu}&prodNo=${productVO.prodNo}">${productVO.prodName}</a></c:if></td>
					
					<td></td>
					<td align="left">${productVO.price}</td>
					<td></td>

					<td align="left">${productVO.regDate}</td>
					<td></td>
					
					<td> 
					
					<c:if test="${productVO.proTranCode.trim() == null && param.menu =='search'}">�Ǹ���</c:if>
					<c:if test="${productVO.proTranCode.trim() == null && param.menu =='manage'}">�Ǹ���</c:if>
					
					<c:if test="${productVO.proTranCode.trim() == '1' && user.role =='user'}">�������</c:if>
					<c:if test="${productVO.proTranCode.trim() == '1' && user.role =='admin'}">���ſϷ�
					<a href="/updateTranCodeByProd.do?proTranCode=2&prodNo=${productVO.prodNo}">&nbsp;����ϱ�</a></c:if>
					
					<c:if test="${productVO.proTranCode.trim() =='2' && user.role =='admin'}">�����</c:if>
					<c:if test="${productVO.proTranCode.trim() =='2' && user.role =='user'}">�������</c:if>
									
					<c:if test="${productVO.proTranCode.trim() =='3' }">��ۿϷ�</c:if>
					
					
					</td>
					
				</tr>
				
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center">
					 <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<%-- if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ 
					�� ����
			<% }else{ %>
					<a href="javascript:fncgetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncgetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncgetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } --%>
			<jsp:include page="../common/pageNavigator.jsp"/>
					</td>
				</tr>
			</table>
			<!--  ������ Navigator �� -->

		</form>

	</div>
</body>
</html>