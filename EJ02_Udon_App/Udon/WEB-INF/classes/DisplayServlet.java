import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {

    private String DB_URL = "jdbc:mysql://localhost:3306/Udon?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private String USER = "root";
    private String PASS = "xxxx";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><style>");
        out.println("body { font-family: Arial; background:#FF9800; text-align:center; }");
        out.println(".container { background:white; padding:20px; margin:auto; width:85%; border-radius:20px; }");
        out.println(".stats { display:flex; justify-content:center; gap:20px; margin:20px; }");
        out.println(".card { padding:15px; border:2px solid orange; border-radius:10px; width:120px; }");
        
        // Bar Graph
        out.println(".chart-section { margin: 30px auto; width: 80%; text-align: left; }");
        out.println(".bar-label { font-weight: bold; margin-bottom: 5px; }");
        out.println(".bar-container { background: #eee; border-radius: 10px; margin-bottom: 15px; width: 100%; }");
        out.println(".bar-fill { height: 25px; background: #e65100; border-radius: 10px; color: white; padding-left: 10px; line-height: 25px; font-size: 14px; transition: width 1s; }");
        
        out.println("table { width:100%; margin-top:20px; border-collapse: collapse; }");
        out.println("th, td { padding:10px; border-bottom:1px solid #ddd; text-align: center; }");
        out.println("th { background:#e65100; color:white; }");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h1>🍜 Udon Know Mee Dashboard</h1>");
        out.println("<h2 class='survey-question'>Pick your favourite Brand:</h2>");

        int a=0, b=0, c=0, d=0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Get Counts
            String sql = "SELECT choice, COUNT(*) as total FROM responses GROUP BY choice";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                String choice = rs.getString("choice");
                int total = rs.getInt("total");
                switch (choice) {
                    case "a": a = total; break;
                    case "b": b = total; break;
                    case "c": c = total; break;
                    case "d": d = total; break;
                }
            }

            // Show stats
            out.println("<div class='stats'>");
            out.println(card("Samyang", a));
            out.println(card("Maggi", b));
            out.println(card("Indomie", c));
            out.println(card("Shin Ramyun", d));
            out.println("</div>");

            // Bar Graph Section
            int grandTotal = a + b + c + d;
            out.println("<div class='chart-section'>");
            out.println("<h3>Visual Popularity</h3>");
            out.println(bar("Samyang", a, grandTotal));
            out.println(bar("Maggi", b, grandTotal));
            out.println(bar("Indomie", c, grandTotal));
            out.println(bar("Shin Ramyun", d, grandTotal));
            out.println("</div>");

            // Show Comments 
            out.println("<h3>Responses</h3>");
            out.println("<table><tr><th>Brand</th><th>Comment</th></tr>");

            ResultSet rs2 = conn.createStatement()
                    .executeQuery("SELECT choice, comment FROM responses ORDER BY id DESC");

            while (rs2.next()) {
                String choice = rs2.getString("choice");
                String comment = rs2.getString("comment");
                if (comment == null || comment.isEmpty()) comment = "<i>No comment</i>";

                out.println("<tr><td>" + map(choice) + "</td><td>" + comment + "</td></tr>");
            }

            out.println("</table>");
            conn.close();

        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("</div></body></html>");
    }

    // Helper for Cards
    private String card(String name, int count) {
        return "<div class='card'><b>" + name + "</b><br><h2>" + count + "</h2>votes</div>";
    }

    // Helper for Bar 
    private String bar(String name, int count, int total) {
        // Calculate percentage
        int percent = (total > 0) ? (int) (((double) count / total) * 100) : 0;
        
        return "<div class='bar-label'>" + name + " (" + count + ")</div>" +
               "<div class='bar-container'>" +
               "  <div class='bar-fill' style='width: " + percent + "%;'>" + percent + "%</div>" +
               "</div>";
    }

    private String map(String c) {
        switch (c) {
            case "a": return "Samyang";
            case "b": return "Maggi";
            case "c": return "Indomie";
            case "d": return "Shin Ramyun";
            default: return c;
        }
    }
}