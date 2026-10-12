import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Match the parameters from the Android URL
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Database details - CHANGE THESE TO YOUR ACTUAL DB CREDENTIALS
        String dbUrl = "jdbc:mysql://localhost:3306/Udon?allowPublicKeyRetrieval=true&useSSL=false";
        String dbUser = "myuser"; 
        String dbPass = "xxxx";   

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

            // Query the users table
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                out.print("Success");
            } else {
                out.print("Fail");
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("Database Error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}