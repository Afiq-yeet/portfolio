import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
    
    private String dbURL = "jdbc:mysql://localhost:3306/Udon?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private String dbUser = "root"; 
    private String dbPass = "xxxx"; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Capture Choice & Comment from Android URL
        String userChoice = request.getParameter("choice");
        String userComment = request.getParameter("comment");
        int questionNum = 1; 

        // Prepare the response
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        //Prevents Null from Entering
        if (userChoice == null || userChoice.trim().isEmpty()) {
            out.print("Error: No choice selected"); 
            return; // CRITICAL: This stops the rest of the code from running
        }
        
        // SQL Insert
        String sql = "INSERT INTO responses (questionNo, choice, comment) VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setInt(1, questionNum);
                stmt.setString(2, userChoice);
                stmt.setString(3, userComment);
                
                stmt.executeUpdate();
                
                // 4. Send plain text back to Android
                out.print("VoteSubmitted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("Error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}