package com.puttipob.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDB {

	public static void main(String[] args) {
		try {
			// 1. โหลด JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. กำหนด URL สำหรับติดต่อกับฐานข้อมูล
			String dbURL = "jdbc:mysql://localhost/registration?characterEncoding=utf-8";

			// 3. เชื่อมต่อกับฐานข้อมูล
			Connection con = DriverManager.getConnection(dbURL, "root", "");

			// 4. เตรียมคำสั่ง SQL ที่จะประมวลผล
			PreparedStatement pStatement = con.prepareStatement("SELECT * FROM teacher");
			// 4.2 INSERT
			// PreparedStatement pStatement = con.prepareStatement("INSERT INTO teacher
			// (tname, status) VALUES (?, ?)");
			// pStatement.setString(1, "คม");
			// pStatement.setString(2, "m");

			// 5. ส่งคำสั่ง SQL ไปยังฐานข้อมูล ResultSet resultSet =
			ResultSet resultSet = pStatement.executeQuery();
			// 5.2 INSERT
			// pStatement.executeUpdate();

			// 6. อ่านผลลัพธ์ที่ฐานข้อมูลส่งกลับ
			while (resultSet.next()) {
				int tid = resultSet.getInt("tid");
				String tname = resultSet.getString("tname");
				String status = resultSet.getString("status");
				if (status.equals("s")) {
					status = "โสด";
				} else if (status.equals("m")) {
					status = "แต่งงาน";
				}
				System.out.println(tid + "," + tname + "," + status);
			}

			// 7. ปิดการเชื่อมต่อ
			con.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		} catch (SQLException e) {
			System.err.println("Error database connection: " + e);
		}
	}
}
