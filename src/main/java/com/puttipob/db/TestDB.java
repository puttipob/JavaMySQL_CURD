package com.puttipob.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDB {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String dbURL = "jdbc:mysql://localhost/blueshop?characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(dbURL, "root", "");

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRICE > ?");
			pstmt.setInt(1, 700);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("pname"));
				System.out.println(rs.getInt("price"));
			}
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("สวัสดี");

	}

}
