package com.res.order.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.dao.Menu;

@Controller
public class MenuController {
	@GetMapping("/adminAllMenu")
	public String adminAllMenu(Model model) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

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

		return "admin_all_menu";
	}

	@PostMapping("/AddMenuAction")
	public String AddMenuAction(@ModelAttribute Menu menu) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement(
					"insert into all_menu (menu_name,menu_price,menu_category,menu_details,menu_photo) values (?,?,?,?,?)");
			pstm.setString(1, menu.getMenuName());
			pstm.setString(2, menu.getMenuPrice());
			pstm.setInt(3, menu.getMenuCategory());
			pstm.setString(4, menu.getMenuDetails());
			pstm.setBytes(5, menu.getMenuPhoto().getBytes());

			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/adminHome";
	}

	@GetMapping("/removeAction")
	public String removeAction(@RequestParam("idkey") int id) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement("delete from all_menu where menu_id = ? ");
			pstm.setInt(1, id);
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/adminAllMenu";

	}

	@GetMapping("/editMenuItem")
	public String editMenuItem(@RequestParam("idkey") int id, Model model) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement("select * from all_menu where menu_id=? ");
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			Menu menu = new Menu();
			if (rs.next()) {

				menu.setMenuId(rs.getInt("menu_id"));
				menu.setMenuName(rs.getString("menu_name"));
				menu.setMenuPrice(rs.getString("menu_price"));
				menu.setMenuCategory(rs.getInt("menu_category"));
				menu.setMenuDetails(rs.getString("menu_details"));

				menu.setPhotoBase64String(Base64.encodeBase64String(rs.getBytes("menu_photo")));

			}
			model.addAttribute("menuobj", menu);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit_menu_item";

	}

	@PostMapping("/updateMenuAction")
	public String updateMenuAction(@ModelAttribute Menu menu) {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement(
					"update all_menu set menu_name = ? ,menu_price = ? ,menu_category = ? , menu_details = ?,menu_photo=? where menu_id = ? ");
			pstm.setString(1, menu.getMenuName());
			pstm.setString(2, menu.getMenuPrice());
			pstm.setInt(3, menu.getMenuCategory());
			pstm.setString(4, menu.getMenuDetails());
			pstm.setBytes(5, menu.getMenuPhoto().getBytes());
			pstm.setInt(6, menu.getMenuId());

			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:adminAllMenu";
	}

	@GetMapping("changeStockStatusAction")
	public String changeStockStatus() {

		return "redirect:adminAllMenu";
	}

	@GetMapping("/fillStockAction")
	public String FillstatusOfStock(@RequestParam("idkey") int id) {
		System.out.println("Fill");
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement("update all_menu set status_of_stock = 1 where menu_id= ? ");
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:adminAllMenu";

	}

	@GetMapping("/emptyStockAction")
	public String EmptystatusOfStock(@RequestParam("idkey") int id) {
		System.out.println("Calling Me");
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://mysql-3b8e1b6b-personal-2024.a.aivencloud.com:25679/res_order_app?sslmode=require",
					"avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29");

			PreparedStatement pstm = conn.prepareStatement("update all_menu set status_of_stock = 0 where menu_id= ? ");
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:adminAllMenu";

	}

}
