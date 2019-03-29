package com.internousdev.mars.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.mars.dao.CartInfoDAO;
import com.internousdev.mars.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCartAction extends ActionSupport implements SessionAware {

	private Collection<String>checkList;
	private String productId;
	private Map<String, Object> session;

	public String execute(){
		//セッションタイムアウト確認
		if(session.isEmpty()){
			return "sessionError";
		}
		String result=ERROR;
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		int count = 0;
		String userId = null;
		if(session.containsKey("userId")){
			userId = String.valueOf(session.get("userId"));
			}else if(session.containsKey("tempUserId")){
			userId = String.valueOf(session.get("tempUserId"));
		}
try{
	for(String productId:checkList) {
		System.out.println(productId);
		System.out.println(userId);
		count += cartInfoDAO.delete(productId, userId);
	}
	if(count<= 0){
		return ERROR;
	}else{
		List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();
		cartInfoDtoList = cartInfoDAO.getCartInfoDtoList(userId);
		Iterator<CartInfoDTO> iterator = cartInfoDtoList.iterator();
		if(!(iterator.hasNext())) {
			cartInfoDtoList = null;
		}
		session.put("cartInfoDtoList", cartInfoDtoList);

		int totalPrice = Integer.parseInt(String.valueOf(cartInfoDAO.getTotalPrice(userId)));
		session.put("totalPrice", totalPrice);

		result=SUCCESS;
	}
	}catch(Exception e){
		e.printStackTrace();
			return ERROR;

	}
	return result;
	}

	public Collection<String> getCheckList() {
		return checkList;
	}
	public void setCheckList(Collection<String> checkList) {
		this.checkList = checkList;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
