package com.res.order.dao;

import org.springframework.web.multipart.MultipartFile;

public class Menu {

	private String menuName;
	private String menuPrice;
	private int menuCategory;
	private String menuDetails;
	private int menuId;
	private int statusOfStock;
	private MultipartFile menuPhoto;

	public MultipartFile getMenuPhoto() {
		return menuPhoto;
	}

	public void setMenuPhoto(MultipartFile menuPhoto) {
		this.menuPhoto = menuPhoto;
	}

	private String photoBase64String;

	public String getPhotoBase64String() {
		return photoBase64String;
	}

	public void setPhotoBase64String(String photoBase64String) {
		this.photoBase64String = photoBase64String;
	}

	public int getStatusOfStock() {
		return statusOfStock;
	}

	public void setStatusOfStock(int statusOfStock) {
		this.statusOfStock = statusOfStock;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(String menuPrice) {
		this.menuPrice = menuPrice;
	}

	public int getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(int menuCategory) {
		this.menuCategory = menuCategory;
	}

	public String getMenuDetails() {
		return menuDetails;
	}

	public void setMenuDetails(String menuDetails) {
		this.menuDetails = menuDetails;
	}
}
