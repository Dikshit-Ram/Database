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

class project {

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
			
		
			System.out.println("\tUsers enter 1\n\tAdminstrator enter 2\nTo exit enter 3\n	Enter your choice: ");
			int selection = Integer.parseInt(keyboard.readLine());
			while(selection!=3){
				if(selection == 1)
				{
					users();
				}
				else if (selection==2)
				{
					admins();
				}
				else 
					System.out.println("Please enter a valid input");
			System.out.println("\tTo continue, users enter 1\n\tTo continue, admins enter 2\n\tIf you want to exit please enter 3 ");
                        selection = Integer.parseInt(keyboard.readLine());
			}

			} catch (SQLException e) {
			System.out.println("Caught SQL Exception: \n     " + e);
			}
		
		}


// provides users with a set of options which involves searching movies, serials etc

	public static void users() throws IOException,SQLException
	{
		System.out.println("\tEnter 1 to search Netflix for movies or serials\n\tEnter 2 to view plan or change plan\n\tEnter 9 to exit\n\n\t\tEnter the choice:");
		int category = Integer.parseInt(keyboard.readLine());
		int selection =0;
		if(category==1){
		while(selection!=9)
		{
			System.out.println("\n\tTo search all Movies ordered by rating enter 1\n\tTo search all Serials ordered by rating enter 2\n\tTo search a movie enter 3\n\tTo search a serial enter 4\n\tTo search movies by Actor enter 5\n\tTo search movies by Director enter 6\n\tTo search Serials by Actor enter 7\n\tTo search Serials by Director enter 8\n\tTo exit enter 9\n\t\tEnter your choice:");
	                        selection = Integer.parseInt(keyboard.readLine());

//ORDER BY advanced sql function is used to order movies by rating
				if(selection ==1)
				{
					Statement stmt1 = conn.createStatement();
			
                        		ResultSet rset1 = stmt1.executeQuery(" select * from movies ORDER BY(RATING) DESC ");
                       			System.out.println("MOVIE ID            MOVIE NAME  	               LANGUAGE    	          YEAR 			       RATING");
					System.out.println("----------------------------------------------------------------------------------------------------------------------------");

					 while (rset1.next()) 
					{
		            		System.out.println(rset1.getString(1)+"		"+rset1.getString(2)+"		"+rset1.getString(3)+"		"+rset1.getString(4)+"		"+rset1.getString(5));
                        		}
					System.out.println("\n\n");

					conn.commit();
				}

//advanced sql function ORDER BY is used to order serials by rating
				else if (selection == 2)
				{
					 Statement stmt2 = conn.createStatement();

                                        ResultSet rset2 = stmt2.executeQuery(" select * from serials ORDER BY(RATING) DESC ");
                                        System.out.println("SERIAL ID            SERIAL NAME              LANGUAGE		NUMBER OF EPISODES                YEAR            RATING");
					System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                                         while (rset2.next())
                                        {
                                                System.out.println(rset2.getString(1)+"         "+rset2.getString(2)+"          "+rset2.getString(3)+"          "+rset2.getString(4)+"          "+rset2.getString(5)+"		"+rset2.getString(6));
                                        }
					System.out.println("\n\n");
                                        conn.commit();

				}

//a created view "movielist" is used

				if(selection ==3)
				{
					Statement stmt3 = conn.createStatement();
					System.out.println("Enter movie name [ex: Inception]");
					String mname = keyboard.readLine();
					
                        		ResultSet rset3 = stmt3.executeQuery(" select * from movielist where movie_name ='"+mname+"' ");
                       			System.out.println("MOVIE NAME  	               		ACTOR NAME    	          	DIRECTOR NAME");
					System.out.println("---------------------------------------------------------------------------------------------------------");

					 if (rset3.next()) {
 					   do {
		            		System.out.println(rset3.getString(1)+"		"+rset3.getString(2)+"		"+rset3.getString(3));
                        		} while(rset3.next());
					} else {
   						System.out.println("No such movie found");
					}					
					System.out.println("\n\n");

					conn.commit();
				}

//a created view "seriallist" is used
				else if (selection == 4)
				{
					 Statement stmt4 = conn.createStatement();
					System.out.println("Enter serial name [ex: Sherlock]");
					String sname = keyboard.readLine();

                        		ResultSet rset4 = stmt4.executeQuery(" select * from seriallist where serial_name ='"+sname+"' ");

                       			System.out.println("SERIAL NAME  	               		ACTOR NAME    	          	DIRECTOR NAME");
					System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                                         if (rset4.next()) {
 					   do {
                                                System.out.println(rset4.getString(1)+"         "+rset4.getString(2)+"          "+rset4.getString(3));
                                        } while(rset4.next());
					} else {
   						System.out.println("No such serial found");
					}					
					System.out.println("\n\n");
                                        conn.commit();

				}
		
//join is used here
				else if(selection == 5)
				{
					System.out.println("Enter the name of Actor [ex: Tom Cruise]");
					String actor = keyboard.readLine();
					Statement stmt5 = conn.createStatement();
					String query5 = " select actor_name,movie_name from movies "+
							" join movies_acted on movies.movie_id = movies_acted.movie_id "+
							" join actor on actor.actor_id = movies_acted.actor_id "+
							" where actor.actor_name =  '"+actor+"' ";
                                        ResultSet rset5 = stmt5.executeQuery(query5);
                                        System.out.println("ACTOR NAME            		MOVIE NAME");
					System.out.println("----------------------------------------------");
					if (rset5.next()) {
 					   do {
     						System.out.println(rset5.getString(1)+"         "+rset5.getString(2));
 						   } while(rset5.next());
					} else {
   						System.out.println("No such Actor found");
					}					
                               

					System.out.println("\n\n");

                                        conn.commit();
					
				}
//join is used
				else if(selection == 6)
                                {
                                        System.out.println("Enter the name of Director [ex: Steven Spielberg]");
                                        String director = keyboard.readLine();
                                        Statement stmt6 = conn.createStatement();
                                        String query6 = " select director_name,movie_name from movies "+
                                                        " join movies_directed on movies.movie_id = movies_directed.movie_id "+
                                                        " join director on director.director_id = movies_directed.director_id "+
                                                        " where director.director_name =  '"+director+"' ";
                                        ResultSet rset6 = stmt6.executeQuery(query6);
                                        System.out.println("DIRECTOR NAME            		MOVIE NAME");
                                        System.out.println("----------------------------------------------");
					 if (rset6.next()) {
 					   do {
     						System.out.println(rset6.getString(1)+"         "+rset6.getString(2));
 						   } while(rset6.next());
					} else {
   						System.out.println("No such Director found");
					}					
                               
					System.out.println("\n\n");

                                        conn.commit();
					
                                }
//simple search
				else if(selection == 7)
				{
					System.out.println("Enter the name of Actor [ex: Bennedict Cumberbatch]");
                                        String sactor = keyboard.readLine();
                                        Statement stmt7 = conn.createStatement();

					String query7 = " select actor_name,serial_name from serials "+
                                                        " join serials_acted on serials.serial_id = serials_acted.serial_id "+
                                                        " join actor on actor.actor_id = serials_acted.actor_id "+
                                                        " where actor.actor_name =  '"+sactor+"' ";
                                        ResultSet rset7 = stmt7.executeQuery(query7);
                                        System.out.println("ACTOR NAME            		SERIAL NAME");
                                        System.out.println("-----------------------------------------------");
					if (rset7.next()) {
 					   do {
     						System.out.println(rset7.getString(1)+"         "+rset7.getString(2));
 						   } while(rset7.next());
					} else {
   						System.out.println("No such Actor found");
					}					
                               
                                        System.out.println("\n\n");

                                        conn.commit();
					
				}
//SIMPLE SEARCH	
				else if(selection == 8)
                                {
				        System.out.println("Enter the name of Director [ex: Steven Spielberg]");
                                        String sdirector = keyboard.readLine();
                                        Statement stmt8 = conn.createStatement();
                                        String query8 = " select director_name,serial_name from serials "+
                                                        " join serials_directed on serials.serial_id = serials_directed.serial_id "+
                                                        " join director on director.director_id = serials_directed.director_id "+
                                                        " where director.director_name =  '"+sdirector+"' ";
                                        ResultSet rset8 = stmt8.executeQuery(query8);
                                        System.out.println("DIRECTOR NAME            		SERIAL NAME");
                                        System.out.println("-----------------------------------------------");
					if (rset8.next()) {
 					   do {
     						System.out.println(rset8.getString(1)+"         "+rset8.getString(2));
 						   } while(rset8.next());
					} else {
   						System.out.println("No such Director found");
					}					
                               
                                        System.out.println("\n\n");

                                        conn.commit();
					
                                }
				else if(selection != 9)
				{
					System.out.println("Invalid input!");
				}

		}
		
		}
		else if(category==2){
			selection = 0;
			while(selection!=9)
			{
			System.out.println("\n\tTo view plans offered by Netflix enter 1\n\tTo change your subscription enter 2\n\tTo update your info enter 3\n\t\tEnter 9 for previous menu\n\tEnter choice:");
                        selection = Integer.parseInt(keyboard.readLine());
			
//shows plan by netfilx
			if(selection==1)
			{
				Statement stmt9 = conn.createStatement();
                                ResultSet rset9 = stmt9.executeQuery("select * from plan");
				System.out.println("PLAN NAME		AMOUNT		DURATION		DESCRIPTION");
				System.out.println("-----------------------------------------------------------------------");
				while (rset9.next()) 
					{
		            		System.out.println(rset9.getString(1)+"			"+rset9.getString(2)+"			"+rset9.getString(3)+"			"+rset9.getString(4));
                        		}
					System.out.println("\n\n");


                                        conn.commit();
			}
//creates a subscription
			else if(selection==2)
			{
				int check = 0;
				String plan;
				do{
				System.out.println("\n\tEnter the plan you want to subscribe for \n\tBasic\n\tStandard\n\tPremium\n\t\nEnter choice plan:");
                        	plan = keyboard.readLine();
				if(plan.equals("Basic")|| plan.equals("Standard")|| plan.equals("Premium"))
				{
					check = 1;
				}
				}while(check!=1);


				System.out.println("\n\tEnter your ID [ex: 501]:");
				int id = Integer.parseInt(keyboard.readLine());

				System.out.println("\n\tEnter Start Date [ex: 14-may-2016]:");
				String date = keyboard.readLine();

				System.out.println("\n\tEnter your card number [ex 1234567812345678]");
				double card = Double.parseDouble(keyboard.readLine());


				Statement stmt10 = conn.createStatement();
				String query10 = " insert into subscription (Customer_ID,plan_name,Start_date,card_used) "
						+" values ("+id+",'"+plan+"','"+date+"',"+card+") ";
                                stmt10.executeQuery(query10);
				System.out.println("\nSubscription Completed");
				conn.commit();
			}

//updates the info of customer
			else if (selection == 3)
			{
				System.out.println("Enter your Customer ID [ex: 501] :");
				int cid = Integer.parseInt(keyboard.readLine());
				
				Statement stmt110 = conn.createStatement();
                                stmt110.executeQuery(" select * from customers where customer_id = "+cid+" ");
				conn.commit();
				
				System.out.println("\tEnter 1 to update ADDRESS\n\tEnter 2 to update PHONE NUMBER\n\tEnter any other number for previous menu :\n\t\t");
				int sel = Integer.parseInt(keyboard.readLine());

				if(sel==1)
				{
				System.out.println("\n\tEnter new ADDRESS [ex: 212 Stipes]:");
				String add = keyboard.readLine();
				Statement stmt121 = conn.createStatement();
                                stmt121.executeQuery(" update customers set address = '"+add+"' where customer_id ="+cid+" ");
				conn.commit();
				}
				else if (sel == 2)
				{
				System.out.println("\n\tEnter new PHONE NUMBER [ex: 3096215147]:");
				String num = keyboard.readLine();
				Statement stmt122 = conn.createStatement();
                                stmt122.executeQuery(" update customers set phone_number = "+num+" where customer_id ="+cid+" ");
				conn.commit();
				
				}
				System.out.println("\t\t**Update sucessful**");
			}
			else
			{
				System.out.println("Invalid Input!");
			}
			}
		}
}
	

//admin method

public static void admins() throws IOException, SQLException
{	
	int selection = 100;
	int tabsel ;
	while(selection != 0)
	{
		tabsel = 0;
		System.out.println("\n\tTo insert, update, delete data for following tables choose the option beside it,\n\tFor Movies table enter 1\n\tFor Serials table enter 2\n\tFor Actors table enter 3\n\tFor Directors table enter 4\n\tFor Episodes enter 5\n\tFor Plan table enter 6\n\tTo delete old subscriptions enter 7\n\tEnter 0 for exit\n\tEnter choice:");
               	selection = Integer.parseInt(keyboard.readLine());
		
//To do dml with movies table
		if(selection==1 || selection == 2)
		{
			while(tabsel !=4)
			{
			System.out.println("\n\tTo insert data, enter 1\n\tTo delete data, enter 2\n\tFor previous menu, enter 4\n\tEnter choice:\n");
			tabsel = Integer.parseInt(keyboard.readLine());
				
			
			if(tabsel == 1 )
			{
				System.out.println("Enter movie/serial id [ex: 1111]:");
				int id1 = Integer.parseInt(keyboard.readLine());
				System.out.println("Enter movie/serial name [ex: martin mystery]:");
				String name1 = keyboard.readLine();

				System.out.println("Enter movie/serial language[ex: English]:");	
				String lang1 = keyboard.readLine();


				System.out.println("Enter movie/serial year[ex: 1999]:");
				int year1 = Integer.parseInt(keyboard.readLine());
				
				System.out.println("Enter movie/serial rating:");
					int rating1 = Integer.parseInt(keyboard.readLine());

					System.out.println("Enter movie/serial episodes count [for movie enter 0]:");
					int count = Integer.parseInt(keyboard.readLine());
					
					if(selection == 1 )
					{
					Statement stmt111 = conn.createStatement();
					String query111 = " insert into movies "
							+" values ("+id1+",'"+name1+"','"+lang1+"',"+year1+","+rating1+") ";
                                	stmt111.executeQuery(query111);
					conn.commit();
					}

					if(selection == 2 )
					{
					Statement stmt112 = conn.createStatement();

					String query112 = " insert into serials "
							+" values ("+id1+",'"+name1+"','"+lang1+"',"+count+","+year1+","+rating1+") ";
                                	stmt112.executeQuery(query112);
					conn.commit();

					}

					System.out.println("\nData insertion Completed");
					

			}
			else if(tabsel==2)
			{
					
					if(selection ==1)
					{
						Statement stmt121 = conn.createStatement();
						ResultSet rset121 = stmt121.executeQuery(" select * from movies ");
						conn.commit();
                                        	System.out.println("MOVIE NAME  	               LANGUAGE    	          	YEAR 			       RATING");
						System.out.println("---------------------------------------------------------------------------------------------------------");
						 if (rset121.next()) {
 					   do {
     						System.out.println(rset121.getString(1)+"		"+rset121.getString(2)+"		"+rset121.getString(3)+"		"+rset121.getString(4)+"		"+rset121.getString(5));
                        			}while(rset121.next());
						} else {
   							System.out.println("No such table found");
						}
						System.out.println("\nEnter the id you want to delete");
						int row11 = Integer.parseInt(keyboard.readLine());

						Statement stmt125 = conn.createStatement();
						ResultSet rset125 = stmt125.executeQuery(" delete from movies where movie_id = "+row11+" ");
						conn.commit();
					}
					else if(selection ==2)
					{
						
						Statement stmt132 = conn.createStatement();

                                        	ResultSet rset132 = stmt132.executeQuery("select * from serials");
                                        	conn.commit();
						System.out.println("SERIAL ID            SERIAL NAME              LANGUAGE		NUMBER OF EPISODES                YEAR            RATING");
						System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                                        	if (rset132.next()) {
 
						do {
     						System.out.println(rset132.getString(1)+"		"+rset132.getString(2)+"		"+rset132.getString(3)+"		"+rset132.getString(4)+"		"+rset132.getString(5));
                        			}while(rset132.next());
						} else {
   							System.out.println("No such table found");
						}
						System.out.println("\nEnter the id you want to delete");
						int row12 = Integer.parseInt(keyboard.readLine());

						Statement stmt133 = conn.createStatement();
						ResultSet rset133 = stmt133.executeQuery(" delete from serials where serial_id = "+row12+" ");
						conn.commit();	
					}	
							
			}
			else{
					System.out.println("Invalid input");
			}
				
			}
		}
		else if(selection ==3 || selection == 4)
		{
				tabsel = 0;
			while(tabsel !=4)
			{
					System.out.println("\n\tTo insert data, enter 1\n\tTo delete data, enter 2\n\tFor previous menu, enter 4\n\tEnter choice:\n");
					tabsel = Integer.parseInt(keyboard.readLine());		
				if(tabsel == 1)	
				{
					
					System.out.println("Enter actor/director id [ex: 999]:");
					int id21 = Integer.parseInt(keyboard.readLine());
					System.out.println("Enter actor/director  name [ex: Will Smith]:");
					String name21 = keyboard.readLine();
					System.out.println("Enter actor/director Date of Birth [ex: 11-jan-2016]:");
					String dob21 = keyboard.readLine();
					System.out.println("Enter actor/director age [ex: 54]:");
					int age21 = Integer.parseInt(keyboard.readLine());				
					if(selection == 3 )
					{	
						String query31 = " insert into actor values("+id21+",'"+name21+"','"+dob21+"',"+age21+") ";
						Statement stmt31 = conn.createStatement();
						stmt31.executeQuery(query31);
						conn.commit();
					}
					if(selection == 4 )
					{
						Statement stmt32 = conn.createStatement();
						String query32 = " insert into director "
							+" values("+id21+",'"+name21+"','"+dob21+"',"+age21+") ";
                                		stmt32.executeQuery(query32);
						conn.commit();
					}
					System.out.println("\nData insertion Completed");		
				}
				else if(tabsel==2)
				{
					
					if(selection ==3)
					{
						Statement stmt321 = conn.createStatement();
						ResultSet rset321 = stmt321.executeQuery(" select * from actor ");
                                        	System.out.println("ACTOR ID  	          ACTOR NAME    	          DATE OF BIRTH 			       AGE");
						System.out.println("---------------------------------------------------------------------------------------------------------");
						while(rset321.next())	
						{
						System.out.println(rset321.getString(1)+"		"+rset321.getString(2)+"		"+rset321.getString(3)+"		"+rset321.getString(4));
                        			}
						System.out.println("\nEnter the id you want to delete");
						int row21 = Integer.parseInt(keyboard.readLine());
	
						Statement stmt322 = conn.createStatement();
						ResultSet rset322 = stmt322.executeQuery(" delete from actor where actor_id = "+row21+" ");
						conn.commit();
						System.out.println("Row deleted");

					}
					else if(selection ==4)
					{
						
						Statement stmt323 = conn.createStatement();
                                        	ResultSet rset323 = stmt323.executeQuery("select * from director");
                                        	System.out.println("DIRECTOR ID  	               DIRECTOR NAME    	          	DATE OF BIRTH 			       AGE");
						System.out.println("----------------------------------------------------------------------------------------------------------------------------");
						while(rset323.next())	
						{
						System.out.println(rset323.getString(1)+"		"+rset323.getString(2)+"		"+rset323.getString(3)+"		"+rset323.getString(4));
                        			}

                                		System.out.println("\nEnter the id you want to delete");
						int row22 = Integer.parseInt(keyboard.readLine());
						Statement stmt324 = conn.createStatement();
						ResultSet rset324 = stmt324.executeQuery(" delete from director where director_id = "+row22+" ");
						conn.commit();
					}				
	
				}
				else
					System.out.println("Invalid input");
				
			} 

			}

		else if(selection == 5)
		{
			tabsel =0;
			
			int epicount = 0;
			while(tabsel !=4)
			{
					System.out.println("\n\tTo insert data, enter 1\n\tTo delete data, enter 2\n\tFor previous menu, enter 4\n\tEnter choice:\n");
					tabsel = Integer.parseInt(keyboard.readLine());		
				if(tabsel == 1)	
				{
					
					System.out.println("Enter serial id whose episode you are inserting  [ex: 404]:");
					int id51 = Integer.parseInt(keyboard.readLine());

					System.out.println("Enter name of episode [ex: project]:");
					String name51 = keyboard.readLine();

					System.out.println("Enter date of airing [ex: 14-may-2015]:");
					String doa51 = keyboard.readLine();

					System.out.println("Enter rating [ex: 7]:");
					int rating51 = Integer.parseInt(keyboard.readLine());
				

					String query51 = " insert into episodes values("+id51+",'"+name51 +"','"+doa51+"',"+rating51+") ";
					Statement stmt51 = conn.createStatement();
					stmt51.executeQuery(query51);
					conn.commit();
					String query52 = " select count(*) from episodes where serial_id ="+id51+" ";
					Statement stmt52 = conn.createStatement();
					ResultSet rset51 = stmt52.executeQuery(query52);
					while (rset51.next())
                                        {
						epicount = Integer.parseInt(rset51.getString(1));

                                        }
					

					String query53 = " update serials set number_of_episodes = "+epicount+" where serial_id = "+id51+" ";
					Statement stmt53 = conn.createStatement();
					stmt53.executeQuery(query53);
					conn.commit();
				}

				else if (tabsel == 2)
				{
					Statement stmt3233 = conn.createStatement();
                                        	ResultSet rset3233 = stmt3233.executeQuery("select * from episodes");
                                        	System.out.println("SERIAL_ID  	               NAME    	          	DATE OF AIRING 			       RATING");
						System.out.println("----------------------------------------------------------------------------------------------------------------------------");
						while(rset3233.next())	
						{
						System.out.println(rset3233.getString(1)+"		"+rset3233.getString(2)+"		"+rset3233.getString(3)+"		"+rset3233.getString(4));
                        			}

					conn.commit();
					System.out.println("Enter serial id whose episode you are deleting  [ex: 404]:");
					int id52 = Integer.parseInt(keyboard.readLine());

					System.out.println("Enter name of episode [ex: Episode 2]:");
					String name52 = keyboard.readLine();
					
					String query54 = " delete from episodes where serial_id = "+id52+" and name = '"+name52+"' ";
					Statement stmt54 = conn.createStatement();
					ResultSet rset54 = stmt54.executeQuery(query54);

					conn.commit();
					String query55 = " select count(*) from episodes where serial_id ="+id52+" ";
					Statement stmt55 = conn.createStatement();
					ResultSet rset55 = stmt55.executeQuery(query55);
					conn.commit();
					while(rset55.next()){
					epicount = Integer.parseInt(rset55.getString(1));
					}
					

					String query56 = " update serials set number_of_episodes = "+epicount+" where serial_id = "+id52+" ";
					Statement stmt56 = conn.createStatement();
					stmt56.executeQuery(query56);
					conn.commit();

				}
				else
				{
					System.out.println("Invalid input!");
				}
			}
		}
		
		else if(selection == 6)
		{
			tabsel =0;
			while(tabsel !=4)
			{
					System.out.println("\n\tTo insert data, enter 1\n\tTo update data, enter 2\n\tTo delete data, enter 3\n\tFor previous menu, enter 4\n\tEnter choice:\n");
					tabsel = Integer.parseInt(keyboard.readLine());		
				if(tabsel == 1)	
				{
					
					System.out.println("Enter plan name  [ex: hd]:");
					String name61 = keyboard.readLine();

					System.out.println("Enter amount [ex: 11]:");
					int amount61 = Integer.parseInt(keyboard.readLine());

					System.out.println("Enter duration days of plan [ex: 30]:");
					int d61 = Integer.parseInt(keyboard.readLine());

					System.out.println("Enter description [ex: 2 screens at a time]:");
					String de61 = keyboard.readLine();				

					String query61 = " insert into plan values('"+name61+"',"+amount61+","+d61+",'"+de61+"') ";
					Statement stmt61 = conn.createStatement();
					stmt61.executeQuery(query61);
					conn.commit();
				}
				else if(tabsel == 2)	
				{
					
					System.out.println("Enter plan name where you want to update amount [ex: Basic]:");
					String name62 = keyboard.readLine();

					System.out.println("Enter amount [ex: 12]:");
					int amount62 = Integer.parseInt(keyboard.readLine());
					String query62 = " update plan set amount = '"+amount62+"' where plan_name ='"+name62+"' ";
					Statement stmt62 = conn.createStatement();
					stmt62.executeQuery(query62);
					conn.commit();
				}
				else if (tabsel == 3)
				{
						Statement stmt63 = conn.createStatement();
                                        	ResultSet rset63 = stmt63.executeQuery("select * from plan");
						conn.commit();

                                        	System.out.println("PLAN NAME  	               AMOUNT    	          DURATION 			       DESCRIPTION");
						System.out.println("----------------------------------------------------------------------------------------------------------------------------");
						while(rset63.next())	
						{
						System.out.println(rset63.getString(1)+"			"+rset63.getString(2)+"			"+rset63.getString(3)+"			"+rset63.getString(4));
                        			}

                                		System.out.println("\nEnter the plan name you want to delete [ex: hd]");
						String row62 = keyboard.readLine();
						Statement stmt64 = conn.createStatement();
						ResultSet rset64 = stmt64.executeQuery(" delete from plan where plan_name = '"+row62+"' ");

				}

			}

		}
		if(selection == 7)
		{
			Statement stmt71 = conn.createStatement();
                        ResultSet rset71 = stmt71.executeQuery("select * from SUBSCRIPTION");
			conn.commit();

                        System.out.println("CUSTOMER ID					PLAN NAME  	              		 START DATE    	          	CARD USED");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
			while(rset71.next())	
			{
			System.out.println(rset71.getString(1)+"			"+rset71.getString(2)+"		"+rset71.getString(3)+"			******************");
                        }
			
                      	
			System.out.println("\nBefore what date you want to delete the subscriptions: ");
			String row73 = keyboard.readLine();

			Statement stmt74 = conn.createStatement();
			ResultSet rset74 = stmt74.executeQuery(" DELETE FROM subscription WHERE ( start_date <= '"+row73+"' ) ");

		}
			
	}
}	
}
