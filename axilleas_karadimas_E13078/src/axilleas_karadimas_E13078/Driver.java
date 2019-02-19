package axilleas_karadimas_E13078;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Date;
public class Driver {
   public static void main(String[] args) throws SQLException, ParseException {
					
		DBConnection db = new DBConnection(); // Connection me thn basi dedomenwn mydatabase
		boolean isDone = true; // tsekarei ean pathithike to 0
		Scanner s = new Scanner(System.in);

		int myInt = 0;
		
		do {//Emfanizei ena epanalambanomeno mhnuma opou stamataei otan dwsoume tin timi 0
		if(isDone) {
		System.out.println("[1] All Cars ");
		System.out.println("[2] Rented Cars");
		System.out.println("[3] Search");
		System.out.println("[4] Update");
		System.out.println("[5] Delete");
		System.out.println("[0] Exit");
		myInt = s.nextInt();
		}
		
		if(myInt == 1) {   //Emfanizei ola ta amaksia
			 String sql = "select * from car";
		     Statement stmt = db.conn.prepareStatement(sql);
		     ResultSet rs = stmt.executeQuery(sql);
		     while(rs.next()) {
		    	 System.out.println(rs.getInt("car_id") +" "+ rs.getString("type") +" "+ rs.getString("doors") +"porto "+ rs.getInt("price")+" eurw");
		    	 if(rs.getInt("symb") == 1) {
		    		 System.out.println("Symbatiko");
		    	 }else {
		    		 System.out.println("Oxi Symbatiko");
		    	 }
		    	 if(rs.getInt("benzini") == 1) {
		    		 System.out.println("Benzinokinito");
		    	 }else {
		    		 System.out.println("Petrelaiokinito");
		    	 }
		     }
		     System.out.println("Patiste enan arithmo gia na sunexisete");
		     
			}else if(myInt == 2) {//Briskei poia amaksia einai enoikiasmena auth ti stigmi
				String sql = "select * from rent";
			     Statement stmt = db.conn.prepareStatement(sql);
			     ResultSet rs = stmt.executeQuery(sql);
			     SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			     Date now = new Date();
			
				
				while(rs.next()) {
					if(now.compareTo(rs.getDate("dateone")) > 0 && now.compareTo(rs.getDate("datetwo")) < 0) {
					   System.out.println("Amksi me ID: " + rs.getString("car_id"));
					}
				   }
			}else if(myInt == 3) {// kanei anazitisi kai enoikiasi ean to epileksei o pelatis
				 Boolean check = true;
				 String sql = "select * from car";
				 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			     PreparedStatement stmt = db.conn.prepareStatement(sql);
			     ResultSet rs = stmt.executeQuery(sql);
			    
			     
			     
				System.out.println("Topos Paralabis");
				String placea = s.next();
				
				System.out.println("Hmera Paralavis me tupo (yyyy-mm-dd) ");
				String datea = s.next();
				java.util.Date dateaa = sdf1.parse(datea);
				java.sql.Date dateaaa = new java.sql.Date(dateaa.getTime());
				
				   
				
				System.out.println("Topos Epistrofis (Ean einai o topos paralavis grapse same)");
				
				 String placeb = s.next();
				if(placeb.equals("same")) {
					placeb = placea;
				}
				System.out.println("Hmera Epistrofis me tupo (yyyy-mm-dd)");
				String dateb = s.next();
				java.util.Date datebb = sdf1.parse(dateb); 
				java.sql.Date datebbb = new java.sql.Date(datebb.getTime());
				long diff = datebb.getTime() - dateaa.getTime(); // briskei tin diafora twn hmerwn gia na broume thn sunoliki timi
				long days = TimeUnit.MILLISECONDS.toDays(diff); 
				
				
				System.out.println("Typos amaksiou? (mikro, mesaio, megalo)");
				String typos = s.next();
				
				
				
				PreparedStatement stmt2 = db.conn.prepareStatement("select * from car" );
				PreparedStatement stmt3 = db.conn.prepareStatement("select * from rent" );
				
				ResultSet rs3 = stmt2.executeQuery("select * from rent ");
				ResultSet rs2 = stmt2.executeQuery("select * from car ");
				while(rs2.next()) {
					System.out.println( "Amaksi me ID :" + rs.getString("car_id"));
				}
				
				System.out.println("Thelete na kanete kratisi (y/n)");
				String apantisi = s.next();
				if(apantisi.equals("y")) {
					System.out.println("Exete Hdh logariasmo? (y/n)");
					String logariasmos = s.next();
					if(logariasmos.equals("y")) {
						System.out.println("Dwste mou ID sas");
						int cID = s.nextInt();
						System.out.println("Dwse mou  ID tou amaksiou pou thelete na enoikiasete");
						int carID = s.nextInt();
						while(rs3.next()) {
						if (carID == rs3.getInt("car_id") && (   (dateaaa.compareTo(rs3.getDate("dateone")) < 0 && datebbb.compareTo(rs3.getDate("dateone")) > 0) ||  (datebbb.compareTo(rs3.getDate("datetwo")) < 0 && dateaaa.compareTo(rs3.getDate("datetwo")) > 0)  || (dateaaa.compareTo(rs3.getDate("dateone")) > 0 && datebbb.compareTo(rs3.getDate("datetwo")) < 0 )    ) ) {// Tsekarei ean to amaksi pou dialekse o pelatis einai enoikiasmeno tis meres pou epelekse      
							System.out.println("To amaksi einai enoikiasmeno. Patiste ena koubi gia na sunexisete");
							check = false;
						}
						}
						if(check) {
						String sql3 = "INSERT INTO rent (customer_id, car_id, placeone, dateone, placetwo, datetwo, type) VALUES (?, ?, ?, ?, ?, ?, ?);";
						PreparedStatement stmt6 = db.conn.prepareStatement(sql3);
						String sql5 = "select price from car where car_id = " + carID;
						PreparedStatement stmt5 = db.conn.prepareStatement(sql5);
						ResultSet rs5 = stmt5.executeQuery(sql5);
						int rs6 = stmt6.executeUpdate("INSERT INTO rent (customer_id, car_id, placeone, dateone, placetwo, datetwo, type) VALUES ("+cID+", "+carID+"," + "\"" + placea + "\"" + ","+"\"" + datea +"\"" +"," + "\"" + placeb + "\"" + ","+"\""+ dateb + "\""+"," + "\"" + typos + "\"" + ");");
						String sql4 = "select * from rent where customer_id = ";
						PreparedStatement stmt4 = db.conn.prepareStatement(sql4);
						ResultSet rs4 = stmt4.executeQuery("select * from rent where customer_id = "+ cID);
						while(rs4.next()) {
						System.out.println("Plhrofories paragelias");
						System.out.println("");
						System.out.println("ID amaksiou: " + rs4.getString("car_id"));
						System.out.println("Topothesia Paralabis: " + rs4.getString("placeone"));
						System.out.println("hmeromhnia paralabis: " + rs4.getDate("dateone"));
						System.out.println("topothesia epistrofis: " + rs4.getString("placetwo"));
						System.out.println("hmerominia epistrofis: " + rs4.getDate("datetwo"));
						while(rs5.next()) {
						System.out.println("Timh: " + days * rs5.getInt("price") );
						    }
						  }
						}
						
					}else {
						System.out.println("Dhmiourgiste enan logariasmo apo thn epilogi Update");
					}
				}else {
					System.out.println("Patiste enan arithmo gia na sunexisete (ektos tou 0)");
				}
				
			}else if(myInt == 4) {// Kanei dhmiourgia kai Update stous customers kai sta amaksia
				
				System.out.println("[1] Dhmiourgia Logariasmou");
				System.out.println("[2] Enhmerwsh Logariasmou");
				System.out.println("[3] Enhmerwsh Amaksiou");
				int myIntb = s.nextInt();
				if(myIntb == 1) {
					System.out.println("Onoma");
					String namea = s.next();
					
					System.out.println("Eponumo");
					String surname = s.next();
					
					System.out.println("Email");
					String email = s.next();
					
					System.out.println("Thlefwno");
					String thlefwno = s.next();
					
					String sql = "INSERT INTO customer (name, surname, email, phone) VALUES (?, ?, ?, ?);";
					PreparedStatement stmt = db.conn.prepareStatement(sql);
					int rs3 = stmt.executeUpdate("INSERT INTO customer (name, surname, email, phone) VALUES ("+"\""+ namea +"\""+","+"\"" + surname +"\"" +"," + "\"" + email + "\"" + ","+"\""+ thlefwno + "\""+");");
					System.out.println("Patiste enan arithmo gia na sunexisete (ektos tou 0)");
					
					
				}else if(myIntb == 2) {
					System.out.println("Dwste mou to ID sas");
					String cID = s.next();
					
					System.out.println("Dwste mou to onoma sas");
					String name = s.next();
							
					System.out.println("Dwste mou to eponumo sas");
					String surname = s.next();
					
					System.out.println("Dwste mou to email sas");
					String email = s.next();
					
					System.out.println("Dwste mou to thlefwno sas");
					String phone = s.next();
					
					String sql = "UPDATE customer SET name = "+ "\""+ name +"\"" + ", surname = " +"\""+surname+"\"" + ", email = " +"\""+email+"\"" + ", phone = " +"\""+phone+"\"" + "WHERE customer_id = "+"\""+cID+"\"";
					PreparedStatement stmt = db.conn.prepareStatement(sql);
					int rs = stmt.executeUpdate(sql);
					
				}else if(myIntb == 3) {
					System.out.println("Dwste mou to ID tou amaksiou");
					String carID = s.next();
					
					System.out.println("Dwste mou ton typo tou amksiou");
					String type = s.next();
							
					System.out.println("Dwste mou ton arithmo twn portwn");
					String portes = s.next();
					
					System.out.println("To amaksi einai Symbatiko? (1=yes 0=no)");
					String symb = s.next();
					
					System.out.println("Benzinokinito h Petrelaiokinito? (1=Benzinokinito 2=Petrelaiokinito)");
					String benzini = s.next();
					
					String sql = "UPDATE car SET type = "+ "\""+ type +"\"" + ", doors = " +"\""+portes+"\"" + ", symb = " +"\""+symb+"\"" + ", benzini = " +"\""+benzini+"\"" + "WHERE car_id = "+"\""+carID+"\"";
					PreparedStatement stmt = db.conn.prepareStatement(sql);
					int rs = stmt.executeUpdate(sql);
				}
				
			}else if(myInt == 5) {// Diagrafei amaksia kai paragellies
				System.out.println("[1] Diagrafi amaksiou");
				System.out.println("[2] Diagrafi mias palias paragelias");
				int myIntb = s.nextInt();
				
				if(myIntb == 1) {
					System.out.println("Dwse mou ID amaksiou gia thn diagrafi tou");
					int diagrafia = s.nextInt();
					
					String sql = "DELETE FROM car WHERE car_id = " + diagrafia;
					PreparedStatement stmt = db.conn.prepareStatement(sql);
					int rs = stmt.executeUpdate(sql);
					
				}else {
					System.out.println("Dwse mou ID amaksiou gia thn diagrafi ths paragellias");
					int diagrafia = s.nextInt();
					
					String sql = "DELETE FROM rent WHERE car_id = " + diagrafia;
					PreparedStatement stmt = db.conn.prepareStatement(sql);
					int rs = stmt.executeUpdate(sql);
				}
				
			}
			
		
		
	
		
		
		
		myInt = s.nextInt();
		}while (myInt != 0);
	

   

	

  }
}
