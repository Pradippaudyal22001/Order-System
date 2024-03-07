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
import com.res.order.db.dbConnector;

@Controller
public class TableController {
	@GetMapping("showAllTable")
	public String showAllTable(Model model) {
		List<CTable> tables = new ArrayList<>();
		try {
			Connection conn = dbConnector.getConnection();

			PreparedStatement pstm = conn.prepareStatement(
					"select * from all_tables left join (select * from customer_info where check_status = 0) As checkIncustomer on all_tables.table_id = checkIncustomer.table_id");
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				CTable obj = new CTable();
				obj.setTableId(rs.getInt("table_id"));
				obj.setTableStatus(rs.getInt("table_status"));
				obj.setTaplecapacity(rs.getInt("table_capacity"));
				obj.setTotalPeople(rs.getInt("total_people"));
				obj.setCostumerName(rs.getString("costumer_name"));
				tables.add(obj);

			}
			model.addAttribute("TBL", tables);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "tableLists";
	}

}
