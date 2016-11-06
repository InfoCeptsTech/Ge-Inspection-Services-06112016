package com.ge.inspection.ir;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class PostgreSQLJDBC {
   public static void main(String args[]) {
      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         /*
         c = DriverManager
            .getConnection("jdbc:postgresql://db1.immuta.io:5432/immuta?sslmode=require&sslfactory=org.postgresql.ssl.NonValidatingFactory",
            "infocepts", "dv7hQRJmBCNqYxha");
         */
         String url = "jdbc:postgresql://db1.immuta.io:5432/immuta?user=infocepts&password=dv7hQRJmBCNqYxha&ssl=require";
         Properties props = new Properties();
         props.setProperty("user","infocepts");
         props.setProperty("password","dv7hQRJmBCNqYxha");
         props.setProperty("ssl","require");
         c = DriverManager.getConnection(url, props);

         
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
}