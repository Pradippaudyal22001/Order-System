package com.res.order.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class dbConnector {
	private static Connection connection;

	public static Connection getConnection() {
		return connection == null ? createConnection() : connection;
	}

	private static Connection createConnection() {
		try {
			ExecutorService threadpool = Executors.newCachedThreadPool();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Future<Connection> futureTask = threadpool.submit(() ->DriverManager.getConnection(
					"jdbc:mysql://" + "mysql-3b8e1b6b-personal-2024.a.aivencloud.com" + ":" + "25679" + "/" + "res_order_app" + "?sslmode=require", "avnadmin", "AVNS_rD4L1U0rBrjUFaLLs29") );

			while (!futureTask.isDone()) {
			    System.out.println("connecting to database..."); 
			} 

			Connection conn = futureTask.get();
			connection = conn;
			threadpool.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
