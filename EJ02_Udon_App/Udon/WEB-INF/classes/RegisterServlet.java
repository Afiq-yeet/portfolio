import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Matched parameter names to Android Volley request
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        String f = request.getParameter("fullname");

        // 2. Updated URL to match typical setup
        String url = "jdbc:mysql://localhost:3306/udon?allowPublicKeyRetrieval=true&useSSL=false";
        String dbUser = "root"; 
        String dbPass = "xxxx"; // MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
                
                // Check if user already exists
                String checkSql = "SELECT username FROM users WHERE username = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, u);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    response.getWriter().write("Exists");
                    return;
                }

                // Insert new user
                String sql = "INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, u);
                pstmt.setString(2, p);
                pstmt.setString(3, f);
                pstmt.executeUpdate();

                // 3. Send plain text back so Android knows to move to MainActivity
                response.getWriter().write("Success");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error");
        }
    }
}
