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
         String url = "jdbc:postgresql://db1.immuta.io:5432/immuta?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
         Properties props = new Properties();
         props.setProperty("user","infocepts");
         props.setProperty("password","sgf5q!$wQklMXt6$");
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