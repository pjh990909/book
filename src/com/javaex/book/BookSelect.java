package com.javaex.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSelect {

	public static void main(String[] args) {

		List<BookVo> bookList = new ArrayList<BookVo>();

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select b.book_id,";
			query += " 		  b.title,";
			query += "  	  b.pubs,";
			query += "  	  b.pub_date,";
			query += "  	  b.author_id,";
			query += "  	  a.author_name,";
			query += "  	  a.author_desc";
			query += " from book b";
			query += " left join author a";
			query += " 	      on b.author_id = a.author_id";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			int bookId;
			String title;
			String pubs;
			String pubDate;
			int authorId;
			String authorName;
			String authorDesc;

			while (rs.next()) {

				bookId = rs.getInt("book_id");
				title = rs.getString("title");
				pubs = rs.getString("pubs");
				pubDate = rs.getString("pub_date");
				authorId = rs.getInt("author_id");
				authorName = rs.getString("author_name");
				authorDesc = rs.getString("author_desc");

				BookVo v0 = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
				bookList.add(v0);
			}

			for (int i = 0; i < bookList.size(); i++) {
				bookList.get(i).toString();

			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	}

}
