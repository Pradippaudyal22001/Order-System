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

import com.res.order.dao.CTable;
import com.res.order.dao.order;
import com.res.order.db.dbConnector;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
	@Autowired
	HttpSession httpSession;

	@GetMapping("/Payment")
	public String Payment(Model model) {

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

				object.setOrderItemPrice(rs.getInt("order_item_price"));
				object.setOrderQuantity(rs.getInt("order_quantity"));

				subTotal += object.getOrderItemPrice() * object.getOrderQuantity();
			}

			PreparedStatement pstm1 = conn
					.prepareStatement("select total_people from customer_info where costumer_id = ?");
			pstm1.setInt(1, CostId);
			ResultSet rs1 = pstm1.executeQuery();
			model.addAttribute("total", subTotal + (subTotal * 0.1));
			if (rs1.next()) {
				model.addAttribute("totalPeople", rs1.getInt("total_people"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Payment";
	}

	@GetMapping("/finishPayment")
	public String finishPayment() {
		int CostId = (int) httpSession.getAttribute("costId");
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm1 = conn
					.prepareStatement("update customer_info set check_status = 1 where costumer_id = ? ");
			pstm1.setInt(1, CostId);
			pstm1.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FinishPayment";
	}

	@GetMapping("/WaitingForPayment")
	public String WaitingForPayment(Model model) {
		List<CTable> Paymentinfo = new ArrayList<>();
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm1 = conn.prepareStatement("select * from  customer_info where check_status = 1");
			ResultSet rs = pstm1.executeQuery();

			while (rs.next()) {
				CTable obj = new CTable();
				obj.setTableId(rs.getInt("table_id"));
				obj.setCustomerid(rs.getInt("costumer_id"));
				Paymentinfo.add(obj);

			}
			model.addAttribute("Paymentinfo", Paymentinfo);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "khaikeMachi";
	}

	@GetMapping("/ForReceipt")
	public String ForReceipt(Model model, @RequestParam("cId") int CostId, @RequestParam("Tid") int TblId,@RequestParam("page")int pageid ) {
		List<order> Orders = new ArrayList<>();
		System.out.println("pageid =" +pageid);

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

			model.addAttribute("cId", CostId);
			model.addAttribute("tId", TblId);
			model.addAttribute("pageid", pageid);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Receipt";
	}

	@GetMapping("/FinalPaymentinfo")
	public String FinishPayment(Model model, @RequestParam("cusId") int abc, @RequestParam("tbId") int def) {
		System.out.println(abc);
		System.out.println(abc);
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm1 = conn
					.prepareStatement("update customer_info set check_status = 2 , checkout_time = sysdate() where costumer_id = ?");
			pstm1.setInt(1, abc);
			 pstm1.executeUpdate();
			
			PreparedStatement pstm2 = conn
					.prepareStatement("update all_tables  set table_status = 0 where table_id = ?");
			pstm2.setInt(1, def);
			pstm2.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:adminHome";
	}
	@GetMapping("/Forjournal")
	public String Forjournal(Model model) {
		List<CTable> Paymentinfo = new ArrayList<>();
		try {
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm1 = conn.prepareStatement("select * from  customer_info where check_status = 2");
			ResultSet rs = pstm1.executeQuery();

			while (rs.next()) {
				CTable obj = new CTable();
				obj.setTableId(rs.getInt("table_id"));
				obj.setCustomerid(rs.getInt("costumer_id"));
				obj.setCheckinTime(rs.getString("checkin_time"));
				obj.setCheckOutTime(rs.getString("checkout_time"));
				
				Paymentinfo.add(obj);

			}
			model.addAttribute("Paymentinfo", Paymentinfo);

		} catch (Exception e) {
			e.printStackTrace();
		}

			
		
		
		return"Journal";
	}
	
	@GetMapping("/BackToTop")
	public String BackToTop() {
		return "redirect:/";
	}

}
