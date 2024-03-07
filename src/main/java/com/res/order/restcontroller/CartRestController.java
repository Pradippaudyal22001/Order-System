package com.res.order.restcontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.res.order.db.dbConnector;

import jakarta.servlet.http.HttpSession;



@RestController
public class CartRestController {
	@Autowired
	HttpSession httpSession;
	@GetMapping("/restCart")
	public void addCart(@RequestParam("count") int count, @RequestParam("itemname") String itemname,@RequestParam("itemprice") int Itemprice) {
		try {
			int tableId =  (int) httpSession.getAttribute("TableID");
			int costid = (int)httpSession.getAttribute("costId");
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn.prepareStatement(
					"insert into orders (order_quantity,order_item_name,table_id,costumer_id,order_item_price) values(?,?,?,?,?)");
			pstm.setInt(1, count);
			pstm.setString(2, itemname);
			pstm.setInt(3, tableId);
			pstm.setInt(4,costid );
			pstm.setInt(5,Itemprice);
			pstm.executeUpdate();
			
			
			
			System.out.println(tableId);
			System.out.println(costid);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
