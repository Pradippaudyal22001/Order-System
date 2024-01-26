package com.res.order.controller;

import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.db.DBaccess;

@Controller
public class LoginController {
	@GetMapping("/adminLoginAction")
	public String adminLoginAction(@RequestParam("user_id") String id, @RequestParam("user_pwd") String pwd) {
		try {

			ResultSet rs = DBaccess.loginByUserinfo(id, pwd, 0);
			if (rs.next() & rs != null) {
				return "redirect:adminHome";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";

	}
	@GetMapping("/CostumerloginAction")
	public String CostumerloginAction(@RequestParam("user_id") String id, @RequestParam("user_pwd") String pwd) {
		try {

			ResultSet rs = DBaccess.loginByUserinfo(id, pwd, 1);
			if (rs.next() & rs != null) {
			
				return "redirect:CostumerAllMenu";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";

	}
	

}
