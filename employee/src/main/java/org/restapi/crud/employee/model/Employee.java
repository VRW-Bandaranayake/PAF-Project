package org.restapi.crud.employee.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class Employee {
	
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return co;
	}

	
	public String readEmployees()
	{
			String output = "";
			
			try
			{
					Connection con = connect();
					
					if (con == null)
					{
							return "Error while connecting to the database for reading.";
					}
					
					// Prepare the html table to be displayed
					output = "<table class=\'table table-bordered\' border='1'><tr><th>ID</th>"
							+ "<th>Name</th>"
							+ "<th>Gender</th>"
							+ "<th>Dob</th>"
							+ "<th>Age</th>"
							+ "<th>Salary</th>"
							+ "<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from employee"; // table name employee
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					// iterate through the rows in the result set
					while (rs.next())
					{
							String docId = rs.getString("docId");
							String docName = rs.getString("docName");
							String docGender = rs.getString("docGender");
							String docDob = rs.getString("docDob");
							String docAge = Integer.toString(rs.getInt("docAge"));
							String docSalary = rs.getString("docSalary");
							
							// Add into the html table
							output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden' value='" + docId + "'>" + docId + "</td>";
							output += "<td>" + docName + "</td>";
							output += "<td>" + docGender + "</td>";
							output += "<td>" + docDob + "</td>";
							output += "<td>" + docAge+ "</td>";
							output += "<td>" + docSalary + "</td>";
							
							// buttons
							output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-success'></td>"
								   + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ docId + "'></td></tr>";
					}
					con.close();
					// Complete the html table
					output += "</table>";
			}
			catch (Exception e)
			{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
			}
			return output;
	}
	
	
public String insertEmployees(String docId, String docName, String docGender,String docDob, String docAge,String docSalary  ) {
		
		String output = "";
		
		try {
				Connection con = connect();

				if (con == null) {
					
					return "Error while connecting to the database";
					
				}

			// create a prepared statement
			String query = " insert into employee " + "(`docId`,`docName`,`docGender`,`docDob`,`docAge`,`docSalary`)"
					+ " values (?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, docId);
			preparedStmt.setString(2, docName);
			preparedStmt.setString(3, docGender);
			preparedStmt.setString(4, docDob);
			preparedStmt.setInt(5,Integer.parseInt(docAge) );
			preparedStmt.setString(6, docSalary);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newdoc = readEmployees();
			output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";

		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Employee Deatils.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

public String UpdateEmployees(String docId,String docGender, String docDob,String docAge,String docSalary ) {
	String output = "";
	try {
		Connection con = connect();

		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = "update employee set docGender = ?,docDob = ?,docAge = ?,docSalary = ? where docId = ?";

		PreparedStatement preparedStmt = con.prepareStatement(query);

		// binding values

		preparedStmt.setString(1, docGender);
		preparedStmt.setString(2, docDob);
		preparedStmt.setInt(3,Integer.parseInt(docAge) );
		preparedStmt.setString(4, docSalary);
		preparedStmt.setString(5, docId);
		

		// execute the statement
		preparedStmt.execute();
		con.close();

		String newdoc = readEmployees();
		output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";

	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\":\"Error while updating the Doctor Details.\"}";
		System.err.println(e.getMessage());
	}
	return output;
}
	

public String deleteEmployees(String docId) {
	
	String output = "";

	try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from Employee where docId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, docId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newdoc = readEmployees();
			output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";
	}		
	catch (Exception e)
	{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting a Doctor.\"}";
			System.err.println(e.getMessage());
	}
	
	return output;
}
	
	
	
	
	
}
