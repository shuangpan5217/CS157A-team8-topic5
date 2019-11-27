
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLLoader
{
	public static String USERNAME = new String("ren-2");
    public static String PASSWORD = new String("7sM}c+FG");
	public static String DB_URL = new String("jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=UTC");
	public static String JDBC_DRIVER = new String("com.mysql.cj.jdbc.Driver");
	

	public static void
	main(String[] args) throws ClassNotFoundException
	{
		
		// Load the Driver
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Class.forName(JDBC_DRIVER);

		try
		{
		          // Get a connection from the connection factory
			Connection con = DriverManager.getConnection(
			DB_URL,
			  //"jdbc:oracle:thin:@dbaprod1:1521:SHR1_PRD",
			USERNAME, PASSWORD);
	
			// Show some database/driver metadata
			SQLUtil.printDriverInfo(con);

			// Create a Statement object so we can submit SQL statements to the driver
			Statement stmt = con.createStatement();

			// Submit the statement
			for (int i=0; i<InsertRows.length; ++i)
			{
				System.out.print(InsertRows[i] + "...");
				int rowsAffected = stmt.executeUpdate(InsertRows[i]);
				if (rowsAffected == 1)
					System.out.println("OK");
			}

			// Close the statement
			stmt.close();

			// Close the connection
			con.close();
		}
		catch (SQLException e)
		{
          	 	SQLUtil.printSQLExceptions(e);		
          	 }
	}	

	static String[]	InsertRows = {


			"insert into PATIENT values (1,'testone','firsttest',2)",
			"insert into PATIENT values (2,'testtwo','secondtest',12)",
			"insert into PATIENT values (3,'testthree','thirdtest',4)",

			"insert into INSTRUMENT_MODEL values (9,'model1','onemodeltest')",
			"insert into INSTRUMENT_MODEL values (12,'model2','twomodeltest')",
			"insert into INSTRUMENT_MODEL values (11,'model3','threemodeltest')",
			"insert into INSTRUMENT_MODEL values (13,'model4','fourmodeltest')",


			"insert into INSTRUMENT_CATEGORY values (8,'ca','onemcategory test')",
			"insert into INSTRUMENT_CATEGORY values (7,'ab','two category test')",
			"insert into INSTRUMENT_CATEGORY values (6,'cd','three category test')",



			"insert into INSTRUMENT_TYPES values (4,'ca','this is onemcategory test', 8)",
			"insert into INSTRUMENT_TYPES values (5,'ab','this is two category test', 7)",
			"insert into INSTRUMENT_TYPES values (6,'cd','this is three category test', 6)",



			"insert into VISIT values (13, 1, '2019-05-09 11:30:10','this is the first visit of patient testone', 1,'no')",
			"insert into VISIT values (12, 2, '2019-05-10 12:30:10','patient testwo visit', 2, 'no')",
			"insert into VISIT values (1, 3, '2019-05-10 19:30:10','patient testthree visit', 3,'yes')",


			"insert into COUNSELING values(123, '1', 'patient testone counsel', 13)",
			"insert into COUNSELING values(234, '2', 'patient test two counsel', 12)",
			"insert into COUNSELING values(345, '3', 'patient test three counsel', 1)",



			"insert into INSTRUMENT values(1,'this is an instrument for patient testonee', 8, 9, 4 , 13, 1, 'ear piece')",
			"insert into INSTRUMENT values(2, 'this is an instrument for patient testtwo ',  7, 11 ,5 , 12, 2 , 'hearing aid')",
			"insert into INSTRUMENT values(3, 'this is an instrument for patient testthree ', 7, 12 , 6 , 1, 3, 'hearing aid v2')",

			"insert into REM values(987, 'this is testone REM', 1.1 ,2.1 ,3.1,4.1 ,5.1 ,6.1 ,7.1, 8.1 ,9.1 ,10.1 ,11.1 ,12.1, 13.1 , 14.1 ,15.1 ,16.1 , 1, 13)",
			"insert into REM values(98, 'this is testtwo REM', 1.2 ,2.9 ,3.8 ,4.6 ,5.1 ,6.1 ,7,8,11 ,10,11 ,12, 13, 14,15,16, 2, 12)",
			"insert into REM values(96, 'this is testthree REM', 1.3 ,2.5 ,3.6 ,4,5,6,7,8,9,10,11 ,12, 13, 14,15,16, 3, 1)",



	};
}
