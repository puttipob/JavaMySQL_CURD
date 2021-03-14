package com.puttipob.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAO {

	private Connection con;

	public TeacherDAO() throws ClassNotFoundException, SQLException {
		// 1. โหลด JDBC Driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2. กำหนด URL สำหรับติดต่อกับฐานข้อมูล
		String dbURL = "jdbc:mysql://localhost/registration?characterEncoding=utf-8";

		// 3. เชื่อมต่อกับฐานข้อมูล
		con = DriverManager.getConnection(dbURL, "root", "");
	}

	public Teacher mapping(ResultSet resultSet) throws SQLException {
		return new Teacher(resultSet.getString("tid"), resultSet.getString("tname"), resultSet.getString("status"));
	}

	public Teacher findOne(String id) throws SQLException {
		PreparedStatement pStatement = con.prepareStatement("SELECT * FROM teacher WHERE tid = ?");
		pStatement.setString(1, id);
		ResultSet resultSet = pStatement.executeQuery();

		Teacher t = null;
		if (resultSet.next()) {
			t = mapping(resultSet);
		}

		return t;
	}

	public ArrayList<Teacher> findAll() throws SQLException {
		PreparedStatement pStatement = con.prepareStatement("SELECT * FROM teacher");
		ResultSet resultSet = pStatement.executeQuery();

		ArrayList<Teacher> list = new ArrayList<Teacher>();
		while (resultSet.next()) {
			Teacher t = mapping(resultSet);
			list.add(t);
		}

		return list;
	}

	public void save(Teacher t) throws SQLException {
		PreparedStatement pStatement = con.prepareStatement("INSERT INTO teacher (tname, status) VALUES (?, ?)");
		pStatement.setString(1, t.getTeacherName());
		pStatement.setString(2, t.getStatus());
		pStatement.executeUpdate();
	}

	public void save(Teacher t, String id) throws SQLException {
		PreparedStatement pStatement = con.prepareStatement("UPDATE teacher SET tname = ?, status = ? WHERE tid = ?");
		pStatement.setString(1, t.getTeacherName());
		pStatement.setString(2, t.getStatus());
		pStatement.setString(3, id);
		pStatement.executeUpdate();
	}

	public void delete(String string) throws SQLException {
		PreparedStatement pStatement = con.prepareStatement("DELETE FROM teacherWHERE tid = ?");
		pStatement.setString(1, string);
		pStatement.executeUpdate();
	}

	public void closeConnect() throws SQLException {
		con.close();
	}

	public static void main(String[] args) {
		try {
			TeacherDAO dao = new TeacherDAO();

			// SELECT INSERT UPDATE

			Teacher ryu = new Teacher("", "ริว", "s");
			dao.save(ryu);

			Teacher Alen = new Teacher("", "อเลน", "m");
			dao.save(Alen, "4");

			Teacher t = dao.findOne("2");
			System.out.println(t.getTeacherName() + "" + t.getStatus());

			ArrayList<Teacher> list = dao.findAll();
			for (Teacher teacher : list) {

				System.out.println(teacher.getTeacherName());
			}

			// DELETE
			// dao.delete("7");

			dao.closeConnect();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
