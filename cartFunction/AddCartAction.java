package com.internousdev.mars.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.mars.dao.CartInfoDAO;
import com.internousdev.mars.dao.ProductInfoDAO;
import com.internousdev.mars.dto.CartInfoDTO;
import com.internousdev.mars.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {

	private int productId;
	private int price;
	private String productCount;
	private Map<String, Object> session;

	public String execute(){
		//セッションタイムアウト確認
		if(session.isEmpty()){
			return "sessionError";
		}
		String result = ERROR;
		String userId = null;
		String tempUserId = null;

		session.remove("checkListErrorMessageList");

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		ProductInfoDTO productInfoDTO = productInfoDAO.getProductInfo((Integer)session.get("productId"));

		if(productInfoDTO.getProductName()==null){
			return ERROR;
		}

		if((int)session.get("loginFlg")==1){
			userId = String.valueOf(session.get("userId"));
		}else{
			userId = String.valueOf(session.get("tempUserId"));
			tempUserId = String.valueOf(session.get("tempUserId"));
		}

		/*数値フォーマットに文字列が入った場合はエラーを出す処理*/
		int intProductCount = 0;

		try {
			intProductCount = Integer.parseInt(productCount);
		} catch (NumberFormatException e) {
			return ERROR;
		}

		/*想定外の数値が入った場合はエラーを出す処理*/
		if(intProductCount>5 || intProductCount<0){
            result=ERROR;
            return result;
        }

		CartInfoDAO cartInfoDao = new CartInfoDAO();
//		int count = cartInfoDao.regist(userId,tempUserId,productId,productCount,price);
		// 以下1の処理をする為のDAOを取得する
		// 1. カートに商品を新規追加or情報を更新する
		int count = 0;
		if(cartInfoDao.isExistsCartInfo(userId, productId)){
			count = cartInfoDao.updateProductCount(userId, productId, intProductCount);
		}else{
			count = cartInfoDao.regist(userId, tempUserId, productId, intProductCount, price);
		}

		if(count > 0){
			result=SUCCESS;
		}
		List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();
		cartInfoDtoList = cartInfoDao.getCartInfoDtoList(userId);
		Iterator<CartInfoDTO>iterator = cartInfoDtoList.iterator();
		if(!(iterator.hasNext())){
			cartInfoDtoList = null;
		}
		session.put("cartInfoDtoList", cartInfoDtoList);
		int totalPrice = Integer.parseInt(String.valueOf(cartInfoDao.getTotalPrice(userId)));
		session.put("totalPrice", totalPrice);
		return result;
	}

	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
