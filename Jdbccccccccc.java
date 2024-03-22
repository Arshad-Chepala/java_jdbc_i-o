import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;


public class Jdbccccccccc {
    private static  String url = null;
    private static  String user = null;
    private static  String password = null;
    static int c;
    static String date = null;

    public static void main(String[] args) throws Exception {

        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\hp\\Desktop\\javaioooo\\javaio1\\out\\application.properties");
            properties.load(fileInputStream);
            String name = properties.getProperty("name");
            System.out.println(name);


            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("loaded");


                 FileWriter fw = null;

             url =properties.getProperty("url");
             user =properties.getProperty("user");
             password = properties.getProperty("password");
                    Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("connection successed");
            Statement stmt = con.createStatement();
            String query1 = "select paramvalue  from params where paramname='USER_DATE'";

            ResultSet resultSet = stmt.executeQuery(query1);
            while (resultSet.next()) {
                date = resultSet.getString("paramvalue");
            }
            System.out.println(date);
            String query2 = properties.getProperty("query2");
            String query3 = properties.getProperty("query3");
            String query4 = properties.getProperty("query4");
            PreparedStatement ps = con.prepareStatement(query3);
            PreparedStatement ps1=con.prepareStatement(query4);
              ResultSet rs1 = ps1.executeQuery();
            int total_record=0;
              while (rs1.next()){
                   total_record=rs1.getInt("count(*)");
              }
                // ps.setString(1, date);
            ResultSet resultSet1 = ps.executeQuery();
            ResultSet resulsetrecords=resultSet1;
            int fileCount = 1;
            System.out.println("successfully accesswed");
            fw = new FileWriter("C:/Users/hp/Desktop/javaioooo/iofiles/" + fileCount + ".txt");
            int noOfRecords = 0;
            int counter=0;
            //int total_record=0;
            int noofrecordsperfile =80;
            int counter_attack=0;
            if(total_record%noofrecordsperfile!=0){
                counter_attack=total_record/noofrecordsperfile;
            }
            else{
                counter_attack= (total_record/noofrecordsperfile)-1;
            }
            while (resultSet1.next()) {
                String a = resultSet1.getString("uuid");
                String b = resultSet1.getString("first_name");
                String c = resultSet1.getString("last_name");
                Date d = resultSet1.getDate("birthday");
                Date e = resultSet1.getDate("dateofrecords");
              System.out.println(a + " " + b + " " + c + " " + d + " " + e + " ," + noOfRecords);
                fw.write(a + "," + b + "," + c + "," + d + "," + e + " ," + noOfRecords);
                fw.write("\n");
                noOfRecords++;
               //total_record++;
                if (noOfRecords %noofrecordsperfile == 0) {
//                    System.out.println("inside every 100 records");
//                    System.out.println(noOfRecords);
                    fw.write(String.valueOf(noOfRecords));
                    fw.flush();
                    fw.close();
                    fileCount++;

                    if(counter<counter_attack) {
                      fw = new FileWriter("C:/Users/hp/Desktop/javaioooo/iofiles/" + fileCount + ".txt");
                  }
                    counter++;
                    noOfRecords = 0;
                }
            }
           if(total_record%noofrecordsperfile!=0){
                fw.write(String.valueOf(noOfRecords));
                fw.flush();
              fw.close();
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}