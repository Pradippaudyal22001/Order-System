package com.res.order.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.res.order.dao.CTable;
import com.res.order.dao.Menu;
import com.res.order.db.dbConnector;

@Controller
public class pageController {
	@GetMapping("/")
	public String login(Model model) {
		List<CTable> ctbl = new ArrayList<>();
		
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm= conn.prepareStatement("select * from  all_tables");
			
			ResultSet rs=  pstm.executeQuery();
			
			while (rs.next()) {
				CTable tableobj = new CTable();
				tableobj.setTableId(rs.getInt("table_id"));
				tableobj.setTableStatus(rs.getInt("table_status"));
				ctbl.add(tableobj);
				
			}
			
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		model.addAttribute("tableList",ctbl);
		
		
		return "index";
	}

	@GetMapping("/adminHome")
	public String adminHome() {
		return "admin_home";
	}

	@GetMapping("/addMenu")
	public String addMenu(Model model) {
		model.addAttribute("menuobj", new Menu());
		return "add_menu_item";
	}
	

	

	

};
