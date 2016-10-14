/*
 * This sample shows how to insert into the WIU_Faculty table, and then
 * display all of its rows.
 *
 * This program was originally adapted from one of the samples supplied
 * with Oracle; however, it has been substantially rewritten.
 *
 * Author: Martin Maskarinec
 *
 */

// You need to import the java.sql package to use JDBC
import java.sql.*;
import java.io.*;
import java.util.*;

class lab4 {

	static BufferedReader keyboard; // Needed for keyboard I/O.
	static Connection conn; // A connection to the DB must be established
							// before requests can be handled. You should
							// have only one connection.
	static Statement stmt; // Requests are sent via Statements. You need
							// one statement for every request you have
							// open at the same time.

	// "main" is where the connection to the database is made, and
	// where I/O is presented to allow the user to direct requests to
	// the methods that actually do the work.

	public static void main(String args[]) throws IOException {
		String username = "dra108", password = "dw23eJUk";
		String ename;
		int original_empno = 0;
		int empno;

		keyboard = new BufferedReader(new InputStreamReader(System.in));

		try { // Errors will throw a "SQLException" (caught below)

			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Registered the driver...");

			// Connect to the database. The first argument is the
			// connection string, the second is your username, the third is
			// your password.
			conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.wiu.edu:1521/toolman.wiu.edu", username,
					password);

			conn.setAutoCommit(false);

			System.out.println("logged into oracle as " + username);

			// Create a Statement; again, you may have/need more than one.
			stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery("select TNAME from TAB");

			ArrayList<String> tables = new ArrayList<String>();
			while (rset.next()) {
				tables.add(rset.getString(1));
			}
			rset.close();
			int status = 1;
			while (status == 1) {
				System.out.println(
						"Enter 1 to create MyCategories,\n2 to find the ingredients used in a recipe,\n3 to classify recipes by number of ingredients,\n4 to print the author report,\n5 to exit.");
				int selection = Integer.parseInt(keyboard.readLine());

				if (selection == 1) {
					myMethod(tables);
				} else if (selection == 2) {
					myMethod2();
				} else if (selection == 3) {
					myMethod3();
				} else if (selection == 4) {
					myMethod4();
				} else if (selection == 5) {
					status = 0;
				}
			}
		} catch (SQLException e) {
			System.out.println("Caught SQL Exception: \n     " + e);
		}
	}

	public static void myMethod(ArrayList<String> tables) throws SQLException {
		int i = 0;
		try {
			// -------------------- PART A---------------------------------
			while (i < tables.size()) {
				if (tables.contains("MYCATEGORIES")) {
					Statement stmt1 = conn.createStatement();
					stmt1.executeQuery("drop table MYCATEGORIES");
					conn.commit();
					break;
				}
				i++;

			}
			// --------------------- PART B-----------------------------------
			Statement stmt4 = conn.createStatement();
			stmt4.executeQuery("create table MYCATEGORIES (categoryname varchar(15))");
			conn.commit();
			// -----------------------PART C------------------------------
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery("select * from labdatas16.categories");
			conn.commit();
			while (rs.next()) {
				String s = "insert into MYCATEGORIES values ('" + rs.getString(1) + "')";

				Statement stmt3 = conn.createStatement();
				stmt3.executeQuery(s);
				conn.commit();
			}
			// ------------------------PART D---------------------------
			Statement stmt5 = conn.createStatement();
			ResultSet cp = stmt5.executeQuery("select * from MYCATEGORIES");
			conn.commit();
			System.out.println("\nCategoryName");
			System.out.println("------------");
			while (cp.next()) {
				System.out.println(cp.getString(1));
			}

			System.out.println("\nPart D displayed\n\n");
			// ------------------PART E--------------------------------
			Statement stmt6 = conn.createStatement();
			stmt6.executeQuery("delete from MYCATEGORIES");
			conn.commit();
			// ----------------PART F-------------------------
			Statement stmt7 = conn.createStatement();
			ResultSet rb = stmt7.executeQuery("select * from MYCATEGORIES");
			conn.commit();
			System.out.println("\nCategoryName");
			System.out.println("------------");

			while (rb.next()) {
				System.out.println(rb.getString(1));
			}

			System.out.println("\nPart F displayed\n\n");
			// ----------------PART G-------------------------------
			Statement stmt8 = conn.createStatement();
			ResultSet rs2 = stmt8.executeQuery("select * from labdatas16.categories");
			conn.commit();
			while (rs2.next()) {
				String s2 = "insert into MYCATEGORIES values ('" + rs2.getString(1) + "')";

				Statement stmt9 = conn.createStatement();
				stmt9.executeQuery(s2);
				conn.commit();
			}
			// ----------------PART H------------------------------------
			Statement stmt10 = conn.createStatement();
			ResultSet cp2 = stmt10.executeQuery("select * from MYCATEGORIES");
			conn.commit();
			System.out.println("\nCategoryName");
                        System.out.println("------------");

			while (cp2.next()) {
				System.out.println(cp2.getString(1));
			}
			System.out.println("\nPart H displayed\n\n");
			// --------------PART I----------------------------------
			conn.commit();
			// --------------PART J--------------------------------
			Statement stmt11 = conn.createStatement();
			ResultSet cp3 = stmt11.executeQuery("select * from MYCATEGORIES");
			conn.commit();
			System.out.println("\nCategoryName");
                        System.out.println("------------");

			while (cp3.next()) {
				System.out.println(cp3.getString(1));
			}
			System.out.println("\nPart J displayed\n\n");

		}

		catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void myMethod2() throws SQLException, IOException {
		// Prompts the user to enter a recipe and with that recipe string str is
		// completed
		// and statement stm1 is created and str is used along with stm1 to
		// execute query
		// the result is stored in resultset and printed using while loop
		// else if (selection==2){
		System.out.println("Enter a recipe name: ");
		String n = keyboard.readLine();
		String str = " Select ingredientname,unitname,quantity " + " from labdatas16.ingredientlist "
				+ " join labdatas16.recipes " + " on labdatas16.ingredientlist.recipeid = labdatas16.recipes.recipeid "
				+ " where recipename = '" + n + "' ";
		Statement stm1 = conn.createStatement();
		ResultSet q2 = stm1.executeQuery(str);
		System.out.println("INGREDIENTNAME              UNITNAME                QUANTITY");
		System.out.println("------------------------------------------------------------");
		conn.commit();
		while (q2.next()) {
			System.out.println(q2.getString(1) + "         " + q2.getString(2) + "             " + q2.getString(3));
		}
		// }

	}

	public static void myMethod3() throws SQLException, IOException {
		// String n3 is filled with query
		// and statement stm2 is created and str is used along with stm1 to
		// execute query
		// the result is stored in resultset and printed using while loop and if
		// cases are used in place of
		// decode function
		String n3 = " SELECT recipename, Count(*) AS count " + " FROM   labdatas16.recipes "
				+ " JOIN labdatas16.ingredientlist "
				+ " ON labdatas16.recipes.recipeid = labdatas16.ingredientlist.recipeid " + "  GROUP  BY recipename ";

		Statement stm2 = conn.createStatement();
		ResultSet q5 = stm2.executeQuery(n3);
		conn.commit();
		System.out.println("recipename          # of ingredients                Difficulty ");
		System.out.println("---------------------------------------------------------------");
		while (q5.next()) {
			if (Integer.parseInt(q5.getString(2)) < 5) {
				System.out.println(q5.getString(1) + "           " + q5.getInt("count") + "          EASY");
			} else if (Integer.parseInt(q5.getString(2)) < 10) {
				System.out.println(q5.getString(1) + "           " + q5.getInt("count") + "          MEDIUM");
			} else {
				System.out.println(q5.getString(1) + "           " + q5.getInt("count") + "          HARD");
			}

		}

	}

	public static void myMethod4() throws SQLException, IOException {
		// Prompts user to input author name and displays the all the recipes written by him while displaying Recipe name and number of servings
		//string is completed by author name input and query is executed from a statement 

		System.out.println("Enter author name: ");
		String n2 = keyboard.readLine();
		Statement stm3 = conn.createStatement();
		String str2 = " select recipename,numberofservings " + " from labdatas16.recipes join labdatas16.author "
				+ " on labdatas16.recipes.username = labdatas16.author.username " + " where name = '" + n2 + "' ";
		ResultSet q3 = stm3.executeQuery(str2);
		conn.commit();
		while (q3.next()) {
		System.out.println("\n\n");
		System.out.println("		RECIPENAME          # OF SERVINGS");
                System.out.println("		---------------------------------");
		System.out.println("		"+q3.getString(1) + "                " + q3.getString(2));
		String str3 = " select iname, itype,unitname,quantity " + " from labdatas16.ingredients "
                                + " join labdatas16.ingredientlist " + " on iname = ingredientname "
                                + " join labdatas16.recipes on labdatas16.recipes.recipeid = labdatas16.ingredientlist.recipeid "
                                + " where recipename = '"+q3.getString(1)+"' ";
                Statement stm4 = conn.createStatement();
                conn.commit();
                ResultSet q4 = stm4.executeQuery(str3);
                System.out.println("INGREDIENT NAME             INGREDIENT TYPE         UNITNAME                QUANTITY");
                System.out.println("------------------------------------------------------------------------------------");
                while (q4.next()) {
                        System.out.println(q4.getString(1) + "                " + q4.getString(2) + "             "
                                        + q4.getString(3) + "             " + q4.getString(4));
                }


		}
	}

}

