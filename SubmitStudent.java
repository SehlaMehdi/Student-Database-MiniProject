import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class SubmitStudent extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String usn = req.getParameter("usn");
        String name = req.getParameter("name");
        String branch = req.getParameter("branch");
        int sem = Integer.parseInt(req.getParameter("sem"));

        int sub1 = Integer.parseInt(req.getParameter("sub1"));
        int sub2 = Integer.parseInt(req.getParameter("sub2"));
        int sub3 = Integer.parseInt(req.getParameter("sub3"));
        int sub4 = Integer.parseInt(req.getParameter("sub4"));
        int sub5 = Integer.parseInt(req.getParameter("sub5"));

        int total = sub1 + sub2 + sub3 + sub4 + sub5;
        double avg = total / 5.0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "admin");

            PreparedStatement ps = con.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, usn);
            ps.setString(2, name);
            ps.setString(3, branch);
            ps.setInt(4, sem);
            ps.setInt(5, sub1);
            ps.setInt(6, sub2);
            ps.setInt(7, sub3);
            ps.setInt(8, sub4);
            ps.setInt(9, sub5);
            ps.setInt(10, total);
            ps.setDouble(11, avg);
            ps.setString(12, (avg >= 50) ? "Pass" : "Fail");

            int i = ps.executeUpdate();

            out.println("<html><body bgcolor='white'><center>");
            if (i > 0)
                out.println("<h2>Record Inserted Successfully</h2>");
            else
                out.println("<h2>Failed to Insert Record</h2>");

            // Fetch and display updated student records
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student");

            out.println("<h2>Student Records</h2>");
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>USN</th><th>Name</th><th>Branch</th><th>Sem</th><th>Sub1</th><th>Sub2</th><th>Sub3</th><th>Sub4</th><th>Sub5</th><th>Total</th><th>Avg</th><th>Result</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                for (int j = 1; j <= 12; j++) {
                    out.println("<td>" + rs.getString(j) + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='index.html'>Go Back</a>");
            out.println("</center></body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "admin");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student");

            out.println("<html><body bgcolor='lightyellow'><center>");
            out.println("<h2>Student Records</h2>");
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>USN</th><th>Name</th><th>Branch</th><th>Sem</th><th>Sub1</th><th>Sub2</th><th>Sub3</th><th>Sub4</th><th>Sub5</th><th>Total</th><th>Avg</th><th>Result</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= 12; i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='index.html'>Go Back</a>");
            out.println("</center></body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}