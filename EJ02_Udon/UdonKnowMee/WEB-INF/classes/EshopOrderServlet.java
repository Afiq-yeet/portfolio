import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/eshoporder")
public class EshopOrderServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
   
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   
   //CSS
   out.println("<html><head><title>Order Confirmed</title>");
   out.println("<style>");
   out.println("body { font-family: sans-serif; background: #f4f4f4; display: flex; justify-content: center; padding-top: 10px; margin: 0; }");
   out.println(".container { background: white; padding: 15px 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 400px; text-align: center; }");
   out.println(".details { text-align: left; background: #f9f9f9; padding: 10px; border-radius: 5px; margin: 10px 0; font-size: 0.9em; }");
   out.println("hr { border: 1px dashed #bbb; margin: 10px 0; }");
   
   
   out.println(".thankyou-img { width: 250px; margin: 15px auto; display: block; border-radius: 15px; }");
   
   
   out.println("button { display: block; margin: 20px auto; padding: 10px 20px; cursor: pointer; }");
   
   out.println("h2, h3 { margin: 10px 0; }"); 
   out.println("</style></head><body>");
   
   out.println("<div class='container'>");

   try (
      Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/UdonKnowMee?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "myuser", "xxxx"); 
      Statement stmt = conn.createStatement();
   ) {
      String[] ids = request.getParameterValues("id");
      String cust_name = request.getParameter("cust_name");
      String cust_email = request.getParameter("cust_email");
      String cust_phone = request.getParameter("cust_phone");

      if (ids != null) {
         String sqlStr;
         int count;
         double totalOrderPrice = 0.0;

         out.println("<h2 style='color: #2c3e50;'>Order Confirmed!</h2>");

         for (int i = 0; i < ids.length; ++i) {
            // Fetch Price
            sqlStr = "SELECT price FROM noodles WHERE id = " + ids[i];
            ResultSet rset = stmt.executeQuery(sqlStr);
            if (rset.next()) {
                double price = rset.getDouble("price");
                totalOrderPrice += price;
            }

            // Update qty
            sqlStr = "UPDATE noodles SET qty = qty - 1 WHERE id = " + ids[i];
            System.out.println(sqlStr); 
            count = stmt.executeUpdate(sqlStr);

            // Create record
            sqlStr = "INSERT INTO order_records (id, qty_ordered, cust_name, cust_email, cust_phone) VALUES ("
                  + ids[i] + ", 1, '" + cust_name + "', '" + cust_email + "', '" + cust_phone + "')";
            System.out.println(sqlStr); 
            count = stmt.executeUpdate(sqlStr);

            out.println("<p>Item <strong>#" + ids[i] + "</strong> has been reserved.</p>");
         }

         out.println("<hr>");
         out.println("<h3>Order Summary</h3>");
         
         out.println("<div class='details'>");
         out.println("<strong>Customer:</strong> " + cust_name + "<br>");
         out.println("<strong>Email:</strong> " + cust_email + "<br>");
         out.println("<strong>Phone:</strong> " + cust_phone);
         out.println("</div>");

         out.println("<h2 style='color: #e67e22;'>Total Price: $" + String.format("%.2f", totalOrderPrice) + "</h2>");
         out.println("<p>Thank you for your purchase!</p>"); 
         
         
         out.println("<button onclick=\"window.location.href='UdonKnowMee.html'\">Back to Shop</button>");
         
         
         out.println("<img src='Images/Thankyou.png' alt='Thank You' class='thankyou-img'>");
         
      } else { 
         out.println("<h3>Please go back and select a noodle...</h3>");
      }

   } catch (SQLException ex) {
      out.println("<p style='color:red;'>Error: " + ex.getMessage() + "</p>");
      ex.printStackTrace();
   }
   out.println("</div>"); 
   out.println("</body></html>");
}
}
