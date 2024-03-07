package com.res.order.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.db.dbConnector;

@Controller
public class CheckinController {
	@GetMapping("registerCheckIn")
	public String registerCheckIn(@RequestParam("customerName") String costumerName,
			@RequestParam("noOfPeople") int peopleCount, @RequestParam("tableNo") int tableId) {
		Connection conn = dbConnector.getConnection();
		try {

			PreparedStatement pstm = conn.prepareStatement("update all_tables set table_status = 1 where table_id = ?");
			pstm.setInt(1, tableId);
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement pstm = conn
					.prepareStatement("insert into customer_info(costumer_name,total_people,table_id,checkin_time)values (?,?,?,sysdate())");

			pstm.setString(1, costumerName);
			pstm.setInt(2, peopleCount);
			pstm.setInt(3, tableId);
			
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:showAllTable";
	}

}
