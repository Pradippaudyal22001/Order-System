package com.res.order.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBaccess {
	public static ResultSet loginByUserinfo(String userId, String userPwd, int userRole) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = dbConnector.getConnection();
			PreparedStatement pstm = conn.prepareStatement("select * from account where user_id = ? and user_pwd=? and user_role =?");
			pstm.setString(1, userId);
			pstm.setString(2, userPwd);
			pstm.setInt(3, userRole);

			return pstm.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
