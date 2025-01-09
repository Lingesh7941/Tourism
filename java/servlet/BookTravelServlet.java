package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookTravelServlet")
public class BookTravelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root"; // Update this with your database username
    private static final String DB_PASSWORD = "dhanush"; // Update this with your database password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String state = request.getParameter("state");
        String district = request.getParameter("district");
        String[] activities = request.getParameterValues("activities");

        int noOfPeople = Integer.parseInt(request.getParameter("noOfPeople"));

        // Use SimpleDateFormat to parse dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date travelDate = null;
        Date returnDate = null;
        try {
            travelDate = sdf.parse(request.getParameter("travelDate"));
            returnDate = sdf.parse(request.getParameter("returnDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>Error: Invalid date format.</h3>");
            out.println("<a href='index.html'>Go Back to Booking Form</a>");
            out.println("</body></html>");
            return;
        }

        // Calculate the number of days between dates
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(travelDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(returnDate);

        int noOfDays = 0;
        while (startCal.before(endCal)) {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            noOfDays++;
        }

        if (noOfDays <= 0) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>Error: Return date must be after the travel date.</h3>");
            out.println("<a href='index.html'>Go Back to Booking Form</a>");
            out.println("</body></html>");
            return;
        }

        // Price calculation
        double pricePerPerson = 5000.00; // Default price per person per day
        double totalPrice = noOfPeople * pricePerPerson * noOfDays;

        // Join activities into a single string
        StringBuilder activitiesStr = new StringBuilder();
        if (activities != null) {
            for (String activity : activities) {
                if (activitiesStr.length() > 0) {
                    activitiesStr.append(", ");
                }
                activitiesStr.append(activity);
            }
        }

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Adjust driver class name as needed

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO booking (full_name, email, phone, state, district, no_of_people, travel_date, return_date, total_price, activities) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                // Prepare the statement
                stmt.setString(1, fullName);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, state);
                stmt.setString(5, district);
                stmt.setInt(6, noOfPeople);
                stmt.setDate(7, new java.sql.Date(travelDate.getTime())); // Convert Date to java.sql.Date
                stmt.setDate(8, new java.sql.Date(returnDate.getTime())); // Convert Date to java.sql.Date
                stmt.setDouble(9, totalPrice);
                stmt.setString(10, activitiesStr.toString());

                // Execute the statement
                stmt.executeUpdate();

                // Show success message
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                
                out.println("<html><body>");
                out.println("<h2>Booking Successful!</h2>");
                out.println("<p>Thank you, " + fullName + ". Your trip is booked.</p>");
                out.println("<p class=\"text-center\">Thank you for booking with us. <br> We look forward to providing you with a great experience.</p>");
                out.println("<p>Total Price: RS." + totalPrice + "</p>");
                out.println("<a href='qwe.html' class='btn btn-primary'>Go Back to Home</a>");
                out.println("</body></html>");

            } catch (SQLException e) {
                e.printStackTrace();
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h3 style='color:red;'>Error: Booking failed due to a database error.</h3>");
                out.println("<p>" + e.getMessage() + "</p>");
                out.println("<a href='index.html'>Go Back to Booking Form</a>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>Error: Unable to load database driver.</h3>");
            out.println("<a href='index.html'>Go Back to Booking Form</a>");
            out.println("</body></html>");
            return;
        }
    }
}
