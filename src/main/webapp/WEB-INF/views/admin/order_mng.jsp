<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/admin/order_mng.css?ver=3">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <title>Document</title>
</head>

<body>
    <div class="background">

        <!-- 메뉴바 -->
        <header>
             <%@ include file= "../header/header.jsp" %>
        </header>

        <div class="main">
            <div class="aside">
            	<%@ include file="admin_aside.jsp" %>
            </div>

            <div class="mainbox">
                <div class="purchase">
                    <h4>주문 현황</h4>
                    <hr style="border: solid black 2px;">
                </div>
                <div class="page_name">
                <form action="order_mng" method="post">
                    <input type="date" name="logDate" class="log_date">

                    <select class="category" name="mainSelect" id="category" onchange="itemChange()">
                        <option value="">카테고리</option>                        
                        <option value="1">배송상태</option>
                        <option value="2">취소상태</option>
                        <option value="3">아이디</option>
                        <option value="4">주문번호</option>
                      </select>
                    <select class="subcategory" name="subSelect" id="subcategory" required >
                    <option value="">분류선택</option>
                      </select>
                    <input type="text" name="searchWord"  id="searchWord">
                    <input type="submit" class="btn" value="검색">
                    </form>
                </div>
                <div class="log_table">
                    <hr>
                    <table>
                        <tr>
                            <th>결재일자</th>
                            <th>주문번호</th>
                            <th>상품번호</th>                           
                            <th>회원아이디</th>
                            <th>주문상태</th>
                        </tr>
                        <c:forEach items="${list}"  var="list">
                        <form action="sts_update" method="post" onsubmit="return checksts()" name="stsform">
                        <tr>
                            <td>
                               <fmt:formatDate value="${list.orderDate}" pattern="yy-MM-dd"/>
                            </td>
                            <td><input type="hidden" value="${list.orderNo}" name="orderNo"/> ${list.orderNo}</td>
                            <td>${list.productNo}</td>
                            <td>${list.id}</td>
                            <td>
                            <input class="stsinput" type="text" value="${list.stsDelivery}" class="stsDelivery" readonly/>
                            <select class="category" name="stsDelivery" class="stsDeliverySel">
                            <c:choose>
                            <c:when test="${list.stsDelivery eq '배송준비'}">
                                <option value="배송준비">배송준비</option>                        
                                <option value="배송중">배송중</option>
                                <option value="배송완료">배송완료</option>
                            </c:when>
                            <c:when test="${list.stsDelivery eq '배송중'}">
                                <option value="배송중">배송중</option>
                                <option value="배송완료">배송완료</option>
                            </c:when>
                            <c:when test="${list.stsDelivery eq '배송완료'}">
                                <option value="배송완료">배송완료</option>
                            </c:when>

							</c:choose>
							
							
                              </select>
                              <input class="stsCancel" type="text" value="${list.stsCancel}" class="stsCancel" readonly/>
                              

                              <select class="stsCancelSel" name="stsCancel" class="stsCancelSel">     
                             <c:choose>
                                 <c:when test="${list.stsCancel eq '취소대기'}">                       		 
                           		 <option value="취소대기">취소대기</option>
                           		 <option value="취소완료">취소완료</option>
                           	  </c:when>
                           		 
                            <c:when test="${list.stsCancel eq '취소'}">  
                           		  <option value="취소">취소</option>
                          	  </c:when>
                          	  <c:otherwise>
                           		  <option value="취소완료">취소완료</option>
                            </c:otherwise>
                            </c:choose>							      
                              </select>
                              <input type="button" class="btn" id="stsbtn" value="변경">
                            </td>
                        </tr>
                        </form>
                       </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="/js/order_mng.js"></script>
<script type="text/javascript">

stsSet();
function stsSet(){
	console.log(${searchList.mainSelect});
	console.log('${searchList.subSelect}');
	console.log('${searchList.searchWord}');
	$("#category").val(${searchList.mainSelect}).attr("selected","selected");
//	$("#subcategory").val(${searchList.subSelect}).attr("selected","selected");
	$("#subcategory > option[@value=${searchList.subSelect}]").attr("selected","selected");
	$("#searchWord").val(${searchList.searchWord}).attr("selected","selected");
	}

$("#stsbtn").click(function(){
	
	var frm = document.stsform;
	frm.method ="post";
	frm.action ="sts_update";
	frm.append("mainSelect",$("#category").val());
	frm.append("subSelect",$("#subcategory").val());
	frm.append("searchWord",$("#searchWord").val());
	
	frm.submit();	
});

</script>



<script type="text/javascript" src="/js/search.js"></script>

</html>