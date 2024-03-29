package com.res.order.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.res.order.dao.Menu;
import com.res.order.db.dbConnector;

import jakarta.servlet.http.HttpSession;

@Controller

public class CostumerAllMenu {
	@Autowired
	HttpSession httpSession;

	@GetMapping("/CostumerAllMenu")
	public String adminAllMenu(Model model) {
		try {
			int tableId =  (int) httpSession.getAttribute("TableID");
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn
					.prepareStatement("select costumer_id from customer_info where check_status = 0 and table_id =?");
			pstm.setInt(1, tableId);
			System.out.println(pstm.toString());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				
				httpSession.setAttribute("costId", rs.getInt("costumer_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = dbConnector.getConnection();

			PreparedStatement pstm = conn.prepareStatement("select * from all_menu");

			ResultSet rs = pstm.executeQuery();
			List<Menu> setMenu = new ArrayList<>();
			List<Menu> TanpinMenu = new ArrayList<>();
			List<Menu> DesertMenu = new ArrayList<>();
			List<Menu> SoftDrinkMenu = new ArrayList<>();
			List<Menu> WhiskeyMenu = new ArrayList<>();
			List<Menu> BeerMenu = new ArrayList<>();
			List<Menu> NonAlcoholMenu = new ArrayList<>();

			while (rs.next()) {

				Menu menuObj = new Menu();
				menuObj.setMenuId(rs.getInt("menu_id"));
				menuObj.setMenuName(rs.getString("menu_name"));
				menuObj.setMenuPrice(rs.getString("menu_price"));
				menuObj.setMenuCategory(rs.getInt("menu_category"));
				menuObj.setMenuDetails(rs.getString("menu_details"));
				menuObj.setStatusOfStock(rs.getInt("status_of_stock"));

				rs.getBytes("menu_photo");
				menuObj.setPhotoBase64String(Base64.encodeBase64String(rs.getBytes("menu_photo")));

				switch (menuObj.getMenuCategory()) {
				case 0:
					setMenu.add(menuObj);
					break;
				case 1:
					TanpinMenu.add(menuObj);
					break;
				case 2:
					DesertMenu.add(menuObj);
					break;
				case 3:
					SoftDrinkMenu.add(menuObj);
					break;
				case 4:
					WhiskeyMenu.add(menuObj);
					break;
				case 5:
					BeerMenu.add(menuObj);
					break;
				case 6:
					NonAlcoholMenu.add(menuObj);
					break;
				}

			}
			model.addAttribute("setmenus", setMenu);
			model.addAttribute("tanpinmenus", TanpinMenu);
			model.addAttribute("desertmenus", DesertMenu);
			model.addAttribute("softDrinkmenus", SoftDrinkMenu);
			model.addAttribute("beermenus", BeerMenu);
			model.addAttribute("Whiskeymenus", WhiskeyMenu);
			model.addAttribute("NonAlcoholmenus", NonAlcoholMenu);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Costumer/costumerMenu";
	}

}
