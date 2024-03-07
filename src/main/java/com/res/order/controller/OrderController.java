package com.res.order.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.dao.order;
import com.res.order.db.dbConnector;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	@Autowired
	HttpSession httpSession;

	@GetMapping("/Tocart")
	public String Tocart(Model model) {
		List<order> Orders = new ArrayList<>();
		int CostId = (int) httpSession.getAttribute("costId");
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn
					.prepareStatement("select * from orders where costumer_id = ? and order_status =0");
			pstm.setInt(1, CostId);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				order object = new order();
				object.setOrderId(rs.getInt("order_id"));
				object.setOrderItemName(rs.getString("order_item_name"));
				object.setOrderItemPrice(rs.getInt("order_item_price"));
				object.setOrderQuantity(rs.getInt("order_quantity"));

				Orders.add(object);

			}
			model.addAttribute("order", Orders);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Costumer/cart";
	}

	@GetMapping("/DeleteOrder")
	public String DeleteOrder(@RequestParam("orderId") int oId) {
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn.prepareStatement("delete from orders where order_id = ?");
			pstm.setInt(1, oId);
			pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:Tocart";
	}

	@GetMapping("/Placeorder")
	public String Placeorder(@RequestParam("orderId") int Oid) {
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn.prepareStatement("update orders set order_status = 1 where order_id = ?");
			pstm.setInt(1, Oid);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:Tocart";
	}

	@GetMapping("/ToOrderHistory")
	public String ToOrderHistory(Model model) {
		List<order> Orders = new ArrayList<>();
		int CostId = (int) httpSession.getAttribute("costId");
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn
					.prepareStatement("select * from orders where costumer_id = ? and order_status =1");
			pstm.setInt(1, CostId);
			ResultSet rs = pstm.executeQuery();
			int subTotal = 0;
			while (rs.next()) {
				order object = new order();
				object.setOrderId(rs.getInt("order_id"));
				object.setOrderItemName(rs.getString("order_item_name"));
				object.setOrderItemPrice(rs.getInt("order_item_price"));
				object.setOrderQuantity(rs.getInt("order_quantity"));

				subTotal += object.getOrderItemPrice() * object.getOrderQuantity();

				Orders.add(object);

			}
			model.addAttribute("order", Orders);
			model.addAttribute("SubTotal", subTotal);
			model.addAttribute("tax", (subTotal * 0.1));
			model.addAttribute("total", subTotal + (subTotal * 0.1));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Costumer/OrderHistory";
	}
}
