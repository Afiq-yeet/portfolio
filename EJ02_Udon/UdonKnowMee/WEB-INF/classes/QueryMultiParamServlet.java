import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/querymp")
public class QueryMultiParamServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Udon Shop - Results</title></head><body>");
        
        out.println("<style>");
        out.println("body { font-family: 'Arial', sans-serif; padding: 40px; background-color: #f4f4f4; }");
        out.println(".center-text { text-align: center; width: 100%; }");
        out.println("table { width: 100%; margin: 20px 0; background-color: white; border-collapse: collapse; }");
        
        out.println("td, th { text-align: center; vertical-align: middle; padding: 15px; border: 1px solid #ddd;}");
        out.println("th { background-color: #facf60; color: #68330d; font-size: 1.1em; }"); 
        
        out.println("img { max-width: 120px; height: auto; border-radius: 4px; }");
        
        out.println("#total-bar { position: fixed; bottom: 20px; right: 20px; background: #68330d; color: #facf60; "
          + "padding: 20px; border-radius: 10px; font-weight: bold; font-size: 1.2em; box-shadow: 0 4px 15px rgba(0,0,0,0.3); }");

        out.println("</style>");
        out.println("</head><body>");

        try (
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/UdonKnowMee?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "xxxx");
            Statement stmt = conn.createStatement();
        ) {
            // parameters
            String[] brands = request.getParameterValues("brand");

            // Null check 
            if (brands == null) {
                out.println("<h2>Please select at least one brand!</h2>");
                out.println("<p><a href='queryudonmp.html'>Back to Search</a></p></body></html>");
                return;
            }

            
            StringBuilder brandList = new StringBuilder();
            for (int i = 0; i < brands.length; i++) {
                brandList.append("'").append(brands[i]).append("'");
                if (i < brands.length - 1) brandList.append(",");
            }

            String sqlStr = "SELECT * FROM noodles WHERE brand IN (" + brandList.toString() + ") "
                          + " AND qty > 0 ORDER BY brand ASC, flavour ASC";

            out.println("<div class='center-text'>");
            out.println("<h3>Thank you for your query.</h3>");
            
            System.out.println("SQL Query: " + sqlStr); 
            
            out.println("</div>");

            ResultSet rset = stmt.executeQuery(sqlStr);

            
            out.println("<form method='get' action='eshoporder'>");
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr><th>Select</th><th>PHOTO</th><th>BRAND</th><th>FLAVOUR</th><th>PRICE</th><th>INVENTORY</th></tr>");

            int count = 0;
            while (rset.next()) {
                out.println("<tr>");
                out.println("<td><input type='checkbox' name='id' value='" + rset.getString("id") + "' /></td>");
                
                
                out.println("<td><img src='Images/" + rset.getString("id") + ".jpg' width='100' alt='noodle'></td>");

                out.println("<td>" + rset.getString("brand") + "</td>");
                out.println("<td>" + rset.getString("flavour") + "</td>");
                out.println("<td>$" + rset.getString("price") + "</td>");
                out.println("<td>" + rset.getString("qty") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<p>==== " + count + " records found =====</p>");

            // customer details 
            if (count > 0) {
                out.println("<p>Enter your Name: <input type='text' name='cust_name' required /></p>");
                out.println("<p>Enter your Email: <input type='email' name='cust_email' required /></p>");
                out.println("<p>Enter your Phone Number: <input type='text' name='cust_phone' required /></p>");
                out.println("<input type='submit' value='ORDER' />");
            } else {
                out.println("<h3>No noodles match your search.</h3>");
            }

            out.println("<div id='total-bar'>Current Total: $<span id='live-total'>0.00</span></div>");
            out.println("</form>");
            out.println("<p><a href='queryudonmp.html'>New Search</a></p>");

        } catch (Exception ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            ex.printStackTrace();
        }

        out.println("<script>");
        out.println("function calculateTotal() {");
        out.println("  let total = 0;");
        out.println("  const checkboxes = document.querySelectorAll('input[name=\"id\"]:checked');");
        out.println("  checkboxes.forEach((cb) => {");
        
        // find price in the 5th column of the same row as the checkbox
        out.println("    let priceText = cb.closest('tr').cells[4].innerText;");
        out.println("    let price = parseFloat(priceText.replace('$', ''));");
        out.println("    total += price;");
        out.println("  });");
        out.println("  document.getElementById('live-total').innerText = total.toFixed(2);");
        out.println("}");

        // Attach the function to every checkbox
        out.println("document.querySelectorAll('input[name=\"id\"]').forEach(cb => {");
        out.println("  cb.addEventListener('change', calculateTotal);");
        out.println("});");
        out.println("</script>");

        out.println("</body></html>");
    }
}
