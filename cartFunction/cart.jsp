<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="./css/mars.css">
<link rel="stylesheet" href="./css/cart.css">
<title>カート</title>
<script>

function checkValue(check){
	var checkList = document.getElementsByClassName("checkList");
	var checkFlag = 0;
	for(var i = 0; i < checkList.length; i++){
		if(checkFlag == 0){
			if(checkList[i].checked){
				checkFlag = 1;
				break;
			}
		}
	}
	/* チェックボックスにチェックが無かったときは削除できないようにする処理 */
	if(checkFlag == 1){
		document.getElementById('delete_btn').disabled="";
	}else{
		document.getElementById('delete_btn').disabled="true";
	}
}


</script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="main">
 <div class="page-wrapper">
	<h1>カート画面</h1>

	<s:if test="#session.cartInfoDtoList.size()>0">
		<s:form id="form" action="SettlementConfirmAction">
			<table class="horizontal-list-table">
				<thead>
					<tr>
						<th><s:label value="#"/></th>
						<th><s:label value="商品名"/></th>
						<th><s:label value="商品名ふりがな"/></th>
						<th><s:label value="商品画像"/></th>
						<th><s:label value="値段"/></th>
						<th><s:label value="発売会社名"/></th>
						<th><s:label value="発売年月日"/></th>
						<th><s:label value="購入個数"/></th>
						<th><s:label value="合計金額"/></th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="#session.cartInfoDtoList">
					<tr>
						<td><s:checkbox name="checkList" class="checkList" value="checked" fieldValue="%{productId}" onchange="checkValue(this)"/></td>
						<s:hidden name="productId" value="%{productId}"/>
						<td><s:property value="productName"/></td>
						<td><s:property value="productNameKana"/></td>
						<td><img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>' width="50px" height="50px" /></td>
						<td><s:property value="price"/>円</td>
						<td><s:property value="releaseCompany"/></td>
						<td><s:property value="releaseDate"/></td>
						<td><s:property value="productCount"/></td>
						<td><s:property value="subtotal"/>円</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>

			<!-- レスポンス対応画面 -->
			<table class="active">
			<s:iterator value="#session.cartInfoDtoList">
				<tbody class="tbody">
				    <tr>
						<th><s:label value="#"/></th><td><s:checkbox name="checkList" class="checkList" value="checked" fieldValue="%{productId}" onchange="checkValue(this)"/></td>
						</tr>
					<tr>
						<th><s:label value="商品名"/></th><s:hidden name="productId" value="%{productId}"/><td><s:property value="productName"/></td>
					</tr>
					<tr>
						<th><s:label value="商品名ふりがな"/></th><td><s:property value="productNameKana"/></td>
					</tr>
					<tr>
					    <th><s:label value="商品画像"/></th><td><img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>' width="80%"  /></td>
					</tr>
					<tr>
						<th><s:label value="値段"/></th><td><s:property value="price"/>円</td>
					</tr>
					<tr>
						<th><s:label value="発売会社名"/></th><td><s:property value="releaseCompany"/></td>
					</tr>
					<tr>
						<th><s:label value="発売年月日"/></th><td><s:property value="releaseDate"/></td>
					</tr>
					<tr>
						<th><s:label value="購入個数"/></th><td><s:property value="productCount"/></td>
					</tr>
					<tr>
						<th><s:label value="合計金額"/></th><td><s:property value="subtotal"/>円</td>
					</tr>
				</tbody>
			</s:iterator>
			</table>


				<h2><s:label value="カート合計金額："/><s:property value="#session.totalPrice"/>円</h2><br>
			<div class="btn-wrapper">
				<div class="submit_btn_box">
				<s:submit value="決済" class="cart-btn"/>
				</div>

				<div class="submit_btn_box">
					<s:submit value="削除" id="delete_btn" class="cart-btn" onclick="this.form.action='DeleteCartAction';" disabled="true"/>
				</div>
			</div>
		</s:form>
	</s:if>
	<!-- もしカートに何もない場合の表示画面 submit_btn -->
	<s:else>
	<div class="info">
	カート情報がありません。
	</div>
	</s:else>
  </div>
</div>
</body>
</html>