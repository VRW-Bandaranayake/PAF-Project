package org.restapi.crud.crud.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.restapi.crud.crud.model.crudmodel;

public class crudservice {
	
 Connection con;
	 
	 public crudservice(){
			
			try {
				String url =String.format("jdbc:mysql://localhost:3306/users");
				String uname ="root";
				String pwd = "";
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url,uname,pwd);
				
			} catch(Exception e) {
				System.out.println(e +"data insert unsuccess.");
			}
		}
	 
	 public crudmodel insertUser(crudmodel user) {
			String insert = "insert into person(name, gender, dob, age, salary) values(?,?,?,?,?) ";
			
			try {
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setString(1, user.getName());
				ps.setString(2, user.getGender());
				ps.setString(3, user.getDob());
				ps.setLong(4, user.getAge());
				ps.setString(5, user.getSalary());
				
				ps.execute();
			}catch(Exception e) {
				System.out.println(e +"data insert unsuccess.");
			}
			
			return user;
			
		}
	 
	 public ArrayList<crudmodel> getUser() throws SQLException{
			
			ArrayList<crudmodel> data = new ArrayList<crudmodel>();
			
			String select = "select * from person";
			PreparedStatement ps = con.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				crudmodel model = new crudmodel();
				
				model.setName(rs.getString("name"));// column name
				model.setGender(rs.getString("gender"));
				model.setDob(rs.getString("dob"));
				model.setAge(rs.getInt("age"));	
				model.setSalary(rs.getString("salary"));
				
				data.add(model);
				
			}
			
			return data;
			
		}
	 
	 public ArrayList<crudmodel> getUserById(int id) throws SQLException{
			
			ArrayList<crudmodel> data = new ArrayList<crudmodel>();
			String select = "select * from person where id =?";
			PreparedStatement ps = con.prepareStatement(select);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				crudmodel model = new crudmodel();
				
				model.setName(rs.getString("name"));// column name
				model.setGender(rs.getString("gender"));
				model.setDob(rs.getString("dob"));
				model.setAge(rs.getInt("age"));	
				model.setSalary(rs.getString("salary"));
				data.add(model);		
			}		
			return data;	
		}
		
	 public crudmodel updatetUser(crudmodel user) {
		 
		 String insert = "update person  set name=? , gender=?, dob=?, age=?, salary=? where id =?";
			
			try {
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setString(1, user.getName());
				ps.setString(2, user.getGender());
				ps.setString(3, user.getDob());
				ps.setLong(4, user.getAge());
				ps.setString(5, user.getSalary());
				ps.setInt(6, user.getId());
				
				ps.executeUpdate();
			}catch(Exception e) {
				System.out.println(e +"data insert unsuccess.");	
			}
			
			return user;
			
		}
	 
	 
	 public int deletetUser(int id) {
			String insert = "delete from person where id =?";
			
			try {
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setInt(1,id);
				
				ps.executeUpdate();
			}catch(Exception e) {
				System.out.println(e +"data insert unsuccess.");
			}
			
			return id;
			
		}	

}
