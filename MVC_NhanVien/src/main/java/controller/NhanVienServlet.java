package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DatabaseConnection;
import model.NhanVien;

@WebServlet("/NhanVienServlet")
public class NhanVienServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        try {
            // Switch based on the action parameter
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertNhanVien(request, response);
                    break;
                case "delete":
                    deleteNhanVien(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateNhanVien(request, response);
                    break;
                default:
                    listNhanVien(request, response);
                    break;
            }
        } catch (SQLException | ServletException | IOException e) {
            // Handle errors gracefully and log them
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing the request: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    // List all employees
    private void listNhanVien(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<NhanVien> listNhanVien = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM nhanvien";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int maNV = resultSet.getInt("MaNV");
                String ho = resultSet.getString("HoDem");
                String ten = resultSet.getString("Ten");
                String gioitinh = resultSet.getString("GioiTinh");
                String ngaysinh = resultSet.getString("NgaySinh");
                String diachi = resultSet.getString("Diachi");
                double luong = resultSet.getDouble("Luong");
                listNhanVien.add(new NhanVien(maNV, ho, ten, gioitinh, ngaysinh, diachi, luong));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving employee data.", e);
        }

        // Set the employee list to be used in JSP
        request.setAttribute("listNhanVien", listNhanVien);
        request.getRequestDispatcher("/WEB-INF/listNhanVien.jsp").forward(request, response);  // Correct path to JSP
    }

    // Show form for creating new employee
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/formNhanVien.jsp").forward(request, response);  // Correct path to JSP
    }

    // Insert new employee into the database
    private void insertNhanVien(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String ho = request.getParameter("ho");
        String ten = request.getParameter("ten");
        String gioitinh = request.getParameter("gioitinh");
        String ngaysinh = request.getParameter("ngaysinh");
        String diachi = request.getParameter("diachi");
        double luong = Double.parseDouble(request.getParameter("luong"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO nhanvien (HoDem, Ten, GioiTinh, NgaySinh, DiaChi, Luong) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ho);
            statement.setString(2, ten);
            statement.setString(3, gioitinh);
            statement.setString(4, ngaysinh);
            statement.setString(5, diachi);
            statement.setDouble(6, luong);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting new employee.", e);
        }

        response.sendRedirect("NhanVienServlet?action=list");  // Redirect to the list page
    }

    // Delete employee from the database
    private void deleteNhanVien(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int maNV = Integer.parseInt(request.getParameter("maNV"));
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM nhanvien WHERE MaNV = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, maNV);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting employee.", e);
        }

        response.sendRedirect("NhanVienServlet?action=list");  // Redirect to the list page
    }

    // Show form to edit employee details
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int maNV = Integer.parseInt(request.getParameter("maNV"));
        NhanVien existingNhanVien = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM nhanvien WHERE MaNV = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, maNV);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ho = resultSet.getString("HoDem");
                String ten = resultSet.getString("Ten");
                String gioitinh = resultSet.getString("GioiTinh");
                String ngaysinh = resultSet.getString("NgaySinh");
                String diachi = resultSet.getString("DiaChi");
                double luong = resultSet.getDouble("Luong");
                existingNhanVien = new NhanVien(maNV, ho, ten, gioitinh, ngaysinh, diachi, luong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving employee data for editing.", e);
        }

        request.setAttribute("nhanVien", existingNhanVien);
        request.getRequestDispatcher("/WEB-INF/formNhanVien.jsp").forward(request, response);  // Correct path to JSP
    }

    // Update existing employee in the database
    private void updateNhanVien(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int maNV = Integer.parseInt(request.getParameter("maNV"));
        String ho = request.getParameter("ho");
        String ten = request.getParameter("ten");
        String gioitinh = request.getParameter("gioitinh");
        String ngaysinh = request.getParameter("ngaysinh");
        String diachi = request.getParameter("diachi");
        double luong = Double.parseDouble(request.getParameter("luong"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE nhanvien SET HoDem = ?, Ten = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Luong = ? WHERE MaNV = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ho);
            statement.setString(2, ten);
            statement.setString(3, gioitinh);
            statement.setString(4, ngaysinh);
            statement.setString(5, diachi);
            statement.setDouble(6, luong);
            statement.setInt(7, maNV);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating employee data.", e);
        }

        response.sendRedirect("NhanVienServlet?action=list");  // Redirect to the list page
    }

    // Handle POST requests (same as GET for most actions)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        try {
            // Switch based on the action parameter
            switch (action) {
                case "insert":
                    insertNhanVien(request, response);
                    break;
                case "update":
                    updateNhanVien(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listNhanVien(request, response);
                    break;
            }
        } catch (SQLException | ServletException | IOException e) {
            // Handle errors gracefully and log them
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing the request: " + e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
