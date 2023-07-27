import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.math.BigDecimal;

import java.util.Scanner;

public class PostgreSQLJDBC 
{
	public static void main(String args[]) 
	{
		Connection c = null;
		try
		{
			// Load Postgresql Driver class
			Class.forName("org.postgresql.Driver");
			// Using Driver class connect to databased on localhost, port=5432, database=postgres, user=postgres, password=postgres. If cannot connect then exception will be generated (try-catch block)
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "M@na130702");
			Statement st = c.createStatement();
            System.out.println("Opened database successfully");
			
			// Create instance of this class to call other methods
			PostgreSQLJDBC p = new PostgreSQLJDBC();
			p.setSearchPath(c);
			
			// Call method createCompanyTable to Create Company Table
			//c.setAutoCommit(false);
			//p.createCompanyTable(c);
			//p.insertCompanyTable(c);
			//c.setAutoCommit(true);
			
			// Call method queryEmployeeTable to Create Run SELECT Query in Employee Table
			//p.queryEmployeeTable(c);

			// Call method updEmployeeTable to Create Run UPDATE Query in Employee Table
			//p.updEmployeeTable(c);
			
			// Call method is_same_dept to call function (stored procedure) and read return value
			p.is_same_dept(c);
			
			c.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	void setSearchPath(Connection c)
	{
		Statement stmt = null;
		try
		{
			stmt = c.createStatement();
			String sql = "SET search_path TO company;";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Changed Search Path successfully");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	void insertTable(Connection c)
	{
		PreparedStatement stmt = null;
		String sql = "INSERT INTO vms1.voter_table VALUES (1, 2, 3, 4, 5)";
		try
		{
			Scanner in = new Scanner(System.in);

			stmt = c.prepareStatement(sql);
			stmt.setInt(1, 10);

			System.out.println("Enter company name:");
			String s = in.nextLine();
			System.out.println("You entered string "+s);
			stmt.setString(2, s);
			stmt.setInt(3, 5);
			stmt.setString(4, "My Address");
			stmt.setBigDecimal(5, new BigDecimal(500000));
			
			//int a = in.nextInt();
			//System.out.println("You entered integer "+a);
		
			int affectedRows = stmt.executeUpdate();
			stmt.close();
			System.out.println("Table Inserted successfully: Rows Affected: " + affectedRows);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	void queryEmployeeTable(Connection c)
	{
		Statement stmt = null;
		try
		{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Vote_Given as party,COUNT(VoterID) as No_of_votes FROM Voting_day GROUP BY
            Vote_Given;");
			
			while(rs.next())
			{
				// Datatype mapping and functions to use with JDBC is found at https://www.tutorialspoint.com/jdbc/jdbc-data-types.htm
				String election_type, vote_given,;
				BigDecimal voterid, slotid;
				// Employee Infor
				election_type = rs.getString("election_type");
				vote_given = rs.getString("vote_given");
				voterid = rs.getBigDecimal("voterid");
				// Supervisor Infor
				//sssn = rs.getString("sssn");
				//sfname = rs.getString("sfname");
				//ssalary = rs.getBigDecimal("ssalary");
				sssn = rs.getString(4);
				sfname = rs.getString(5);
				ssalary = rs.getBigDecimal(6);
				
				System.out.println("EmployeeInfo: ID-" + essn + ", Name-" + efname + ", Salary:" + esalary + " SupervisorInfo: ID-" + sssn + ", Name-" + sfname + ", Salary:" + ssalary);
			}
			
			stmt.close();
			System.out.println("Table Queried successfully");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}

   }

   	
