import java.sql.*;
import org.apache.derby.jdbc.ClientDriver;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Menu
{


  public static void main(String[] args)
  {

    int choice;
    Connection conn = null;
    ResultSet rs;
    Statement stmt;
    try
    {
      Driver d = new ClientDriver();
      String url = "jdbc:derby://localhost:1111/plant_db_new;create=false";
      conn = d.connect(url, null);
			Scanner keyboardScanner = new Scanner(System.in);
      choice = PrintMenuAndGetResponse();
      while (choice != 11){
       
        switch(choice)
        {
          case 1:
            //average growth time AGG
            stmt = conn.createStatement();
            String query_string = "SELECT AVG(average_growth_time) AS average_time " +
              						 "FROM PLANT";
            rs = stmt.executeQuery(query_string);
            while (rs.next()){
              String avg = rs.getString(1);             // Retrieve only the first column value
              System.out.println("Average Grow Time of all Plants  is: " + avg);
            }
            break;
            //System.out.format("%-20s %n", average_time);
          case 2:
             //list all plants in system
             query_string = "SELECT plant_id, Scientific_name, common_name " +
                            "FROM PLANT";
    
             Statement st = conn.createStatement();
             rs = st.executeQuery(query_string);
             ResultSetMetaData metadata = rs.getMetaData();
             int columns= metadata.getColumnCount();
             System.out.println("All Plants");
             System.out.println("================================");
             while(rs.next())
             {
               String s_name = rs.getString(1);
               String c_name = rs.getString(2);
		String plant_id = rs.getString(3);
               System.out.format("%-20s", s_name);
               System.out.format("%-20s", c_name);
		System.out.format("%-10s%n",plant_id);
             }
             break;
          case 3:
            //list stages of plant
            //GET PLANT
            System.out.println("Please enter the plant id to check stages: ");
            int plant_id = keyboardScanner.nextInt();
            query_string = "SELECT scientific_name, stage_name " +
                                  "FROM Stage s, plant p " + 
                                  "WHERE s.plant_id = p.plant_id " +
                                  "AND s.plant_id =" + plant_id; 
            
            st = conn.createStatement();
            rs = st.executeQuery(query_string);
            metadata = rs.getMetaData();
            columns= metadata.getColumnCount();
            
            while(rs.next())
            {
              for(int i = 1; i <= columns; i++)
              {
                System.out.print(rs.getString(i) + " ");              
              }
              System.out.println();
            }
            break;
            
          case 4:
          	query_string = "SELECT MAX(average_growth_time) AS maximum_average_time " + 
              						        "FROM PLANT";
            st = conn.createStatement();
            rs = st.executeQuery(query_string);
            rs.next();
            String max_gro = rs.getString(1);
            //System.out.println("Max grow time = " + col1);
            query_string = "SELECT Scientific_name, average_growth_time from plant where average_growth_time= " + max_gro;
            st = conn.createStatement();
            rs = st.executeQuery(query_string);
            
            while(rs.next())
            {
              String long_name = rs.getString(1);
              System.out.println(long_name+ " has the longest average growth time: " + max_gro+ " days");
            }
            
          	break;
          case 5:
            
            query_string = "SELECT MIN(average_growth_time) AS minimum_average_time " + 
             							        "FROM PLANT";
            st = conn.createStatement();
            rs = st.executeQuery(query_string);
            rs.next();
            String min_gro = rs.getString(1);
            //System.out.println("Max grow time = " + col1);
            query_string = "SELECT Scientific_name, average_growth_time from plant where average_growth_time= " + min_gro;
            st = conn.createStatement();
            rs = st.executeQuery(query_string);
            
            while(rs.next())
            {
              String long_name = rs.getString(1);
              System.out.println(long_name+ " has the fastest average growth time: " + min_gro+ " days");
            }
            
          	break;
        case 6:
          //how much water AGG - group by aggregation query
           query_string = "SELECT plant_id, SUM(water_volume) AS total_water  from watering group by plant_id";
          Statement statement = conn.createStatement();
          rs = statement.executeQuery(query_string);
          
          while(rs.next())
          {
            String id = rs.getString(1);
            String total_water = rs.getString(2);
            System.out.println("");
            System.out.println("Plant_ID: " + id + " needs a total of: " + total_water + "ml per watering period.");
            System.out.println("");
          }
          
          break;
          
        case 7:
          //add plant

          //get plant common name, scientific name, growth time
          System.out.println("Please enter the common name of the plant: ");
          String common_name= keyboardScanner.nextLine();
          
          System.out.println("\nPlease enter the scientific name of the plant");
          String scientific_name= keyboardScanner.nextLine();
          
          System.out.println("\nPlease enter the average growth time for this plant");
          int average_growth_time= keyboardScanner.nextInt();
          
          //query_string = "INSERT INTO PLANT(scientific_name,common_name,average_growth_time) VALUES(" + scientific_name + "," + common_name + "," + average_growth_time + ")";
          String insertTableSQL = "INSERT INTO PLANT" + "(scientific_name, common_name, average_growth_time) VALUES" + "(?,?,?)";
          PreparedStatement pstmt = conn.prepareStatement(insertTableSQL);
          pstmt.setString(1, scientific_name);
          pstmt.setString(2, common_name);
    			pstmt.setInt(3, average_growth_time);
          pstmt.executeUpdate();
          System.out.println("Successfully created the plant.");
          
          //get new plant id
          query_string = "SELECT plant_id FROM PLANT WHERE scientific_name=" + "'" + scientific_name + "'";
          st = conn.createStatement();
          rs = st.executeQuery(query_string);
          int new_plant_id;
          rs.next();
          new_plant_id = rs.getInt("plant_id");
          
          
          //add stages 
          System.out.println("How many stages are in this plant's growth?:");
          int i= keyboardScanner.nextInt();
          
          for(int c = 0; c < i; c++)
          {
            System.out.println("What is the name of stage " + c + "?:");
            String stage_name= keyboardScanner.nextLine();
            stage_name= keyboardScanner.nextLine();
            System.out.println("How long is this stage?:");
            int days= keyboardScanner.nextInt();
            
            System.out.println("Which nutrient phase does this stage use?(1-):");
            System.out.println("1. Seedling");
            System.out.println("2. Mild Veg");
            System.out.println("3. Aggressive Veg");
            System.out.println("4. Bloom:");
            System.out.println("5. Ripe");
						int nuts_phase_choice = keyboardScanner.nextInt();
            String nut_phase;
              switch(nuts_phase_choice)
              {
                case 1:
                  nut_phase = "Seedling";
                  break;
                case 2:
                  nut_phase = "Mild Veg";
                  break;
                case 3:
                  nut_phase = "Aggressive Veg";
                  break;           
                case 4:
                  nut_phase = "Bloom";
                  break;            
                case 5:
                  nut_phase = "Ripe";
                  break;
                default:
                  System.out.println("Invalid Choice Using (1).");
                  nut_phase ="Seedling";
                  break;
              }
            insertTableSQL = "INSERT INTO STAGE" + "(plant_id, stage_id, stage_name,days,nut_phase) VALUES" + "(?,?,?,?,?)";
            PreparedStatement apstmt = conn.prepareStatement(insertTableSQL);
            apstmt.setInt(1, new_plant_id);
            apstmt.setInt(2, c+1);
            apstmt.setString(3, stage_name);
            apstmt.setInt(4, days);
    				apstmt.setString(5, nut_phase);
					  apstmt.executeUpdate();
            
            System.out.println("How much water does this stage need in watering?(ml):");
            int water_volume= keyboardScanner.nextInt();
            
            System.out.println("How long is this watering cycle?(hours):");
            int watering_hours= keyboardScanner.nextInt();
            
            String aquery = "INSERT INTO WATERING(plant_id,stage_id,water_volume,watering_period) VALUES" + "(?,?,?,?)";
            PreparedStatement bpstmt = conn.prepareStatement(aquery);
            bpstmt.setInt(1, new_plant_id);
    			  bpstmt.setInt(2, (c+1));
    		   	bpstmt.setInt(3, water_volume);
            bpstmt.setInt(4, watering_hours);
            bpstmt.executeUpdate();
            
            System.out.println("What shall the optimal ph be during this stage?:");
            double ph= keyboardScanner.nextDouble();
            
            System.out.println("What is the optimal temperature in this phase?:");
            int optimal_temp= keyboardScanner.nextInt();
            
            System.out.println("What is the optimal humidity in this phase?:");
            double humidity= keyboardScanner.nextDouble();
            
            System.out.println("What are the daylight hours?:");
            double hours= keyboardScanner.nextDouble();
            
            String climate_query = "INSERT INTO CLIMATE(plant_id,stage_id,ph,optimal_temperature,humidity,daylight_hours) VALUES" + "(?,?,?,?,?,?)";
    				PreparedStatement cpstmt = conn.prepareStatement(climate_query);
            cpstmt.setInt(1, new_plant_id);
    			  cpstmt.setInt(2, (c+1));
    		   	cpstmt.setDouble(3, ph);
            cpstmt.setDouble(4, optimal_temp);
            cpstmt.setDouble(5, humidity);
            cpstmt.setDouble(6, hours);
            cpstmt.executeUpdate();
            
            
          }
         
					break;
          
        case 8:
          //delete plant and show stages deleted as well
          
          System.out.println("Enter the id of the plant you wish to delete:");
          int number= keyboardScanner.nextInt();
          
          String zquery = "DELETE FROM PLANT WHERE PLANT_ID = ?";
          
          PreparedStatement tpstmt = conn.prepareStatement(zquery);
          tpstmt.setInt(1, number);
          tpstmt.executeUpdate();

          

					break;
        case 9:
              System.out.println("Please enter the plant id:");
              plant_id = keyboardScanner.nextInt();
              System.out.println("What would you like to update?: ");
              System.out.println("1. Common name");
              System.out.println("2. Scientific name");
              System.out.println("3. Average Growth Time");
              int plant_choice= keyboardScanner.nextInt();
              
              switch (plant_choice)
              {
                  case 1:
                  		System.out.println("Please enter the new common name:");
                  		common_name = keyboardScanner.nextLine();
                 		  common_name = keyboardScanner.nextLine();
                  		String squery = "UPDATE PLANT SET common_name = ? WHERE plant_id= ?";
                  		//execute
                  		PreparedStatement dpstmt = conn.prepareStatement(squery);
                      dpstmt.setString(1, common_name);
    			  					dpstmt.setInt(2, plant_id);
                  	  dpstmt.executeUpdate();

                  		break;
                  
                  case 2:
                  		System.out.println("Please enter the new scientific name:");
                  		scientific_name= keyboardScanner.nextLine();
                  		scientific_name= keyboardScanner.nextLine();
											String rquery = "UPDATE PLANT SET scientific_name= ? WHERE plant_id= ?";
                			//execute
                  		PreparedStatement epstmt = conn.prepareStatement(rquery);
                      epstmt.setString(1, scientific_name);
    			  					epstmt.setInt(2, plant_id);
                  		epstmt.executeUpdate();
                  		break; 
                  
               		case 3:
                  		System.out.println("Please enter the new Average Growth Time:");
                  		average_growth_time= keyboardScanner.nextInt();
                  		
                  		String gquery = "UPDATE PLANT SET average_growth_time= ? WHERE plant_id= ?";
                  		//execute
                  		PreparedStatement fpstmt = conn.prepareStatement(gquery);
                      fpstmt.setInt(1, average_growth_time);
    			  					fpstmt.setInt(2, plant_id);
                  		fpstmt.executeUpdate();
                  		break;
              }
          break;
          		
        case 10:
          query_string = "SELECT SCIENTIFIC_NAME, COUNT(STAGE_NAME) FROM PLANT P, STAGE S WHERE P.PLANT_ID = S.PLANT_ID GROUP BY SCIENTIFIC_NAME";
          st = conn.createStatement();
          rs = st.executeQuery(query_string);
          System.out.println("Plants             |    Count");
          System.out.println("================================");
          while(rs.next())
             {
               String s_name = rs.getString(1);
               String count = rs.getString(2);
               System.out.format("%-20s", s_name);
               System.out.format("%-20s %n", count);
             }
          break;
          case 11:
            System.out.println("Goodbye!");
          default:
            System.out.println("Illegal choice");
            break;
          
        }
        if (choice != 11)
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Press Enter To Continue...");
            new java.util.Scanner(System.in).nextLine();
            choice = PrintMenuAndGetResponse();
      }
      System.out.println("");
      System.out.println("");
      System.out.println("");
      System.out.println("Goodbye!");
    }
    catch(SQLException e)
    {
    e.printStackTrace();
    }
    finally
    {
      // Step 4: Disconnect from the server
      try
      {
        if(conn != null)
        conn.close();
      }
      catch(SQLException e)
      {
      	e.printStackTrace();
      }
    }

  }

  public static int PrintMenuAndGetResponse()
  {
    Scanner keyboardScanner = new Scanner(System.in);
    int response;
    System.out.println("Choose from one of the following options:");
    System.out.println(" 1. Calculate the average growing time of all plants");
    System.out.println(" 2. List all the plants in the database");
    System.out.println(" 3. List all the stages of a plant");
    System.out.println(" 4. Find the fastest growing plant");
    System.out.println(" 5. find the slowest growing plant");
    System.out.println(" 6. Find total water each plant takes");
    System.out.println(" 7. Add a plant  and all required tables");
    System.out.println(" 8. Delete a particular plant");
    System.out.println(" 9. Edit a tuple of a plant");
    System.out.println(" 10. List the total number of stages for every plant");
    System.out.println(" 11. Quit the program\n");
    System.out.print("Your choice ==> ");
    response = keyboardScanner.nextInt();
    System.out.println( );
    return response;
  }  

}
