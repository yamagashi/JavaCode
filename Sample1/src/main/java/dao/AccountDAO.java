package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Login;

public class AccountDAO {
//データベース接続使用する情報
	private final String JDBC_URL = "jdbc:mariadb://localhost:3306/sampledb";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";

	public Account findByLogin(Login login) {
		Account account = null;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql = "SERECT USER_ID,PASS,MAIL,NAME,AGE FROM ACCOUNT WHERE USER_ID = ? AND PASS = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,login.getUserId());
			pStmt.setString(2, login.getPass());

			//SERECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合
			//そのユーザーを表すAccountインスタンスを生成
			if (rs.next()) {
				//結果表からデータを取得
				String userId = rs.getString("USER_ID");
				String pass = rs.getString("PASS");
				String mail = rs.getString("MAIL");
				String name = rs.getString("NAME");
				int age = rs.getInt("age");
				account = new Account(userId,pass,mail,name,age);
			}
		} catch (SQLException e) {
		  e.printStackTrace();
		  return null;
		}
		//見つかったユーザーまたはnullを返す
		return account;
	}

}
