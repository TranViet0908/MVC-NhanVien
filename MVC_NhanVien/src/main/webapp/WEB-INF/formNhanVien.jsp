<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.NhanVien" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Nhân Viên</title>
</head>
<body>
    <h2><%= request.getAttribute("nhanVien") == null ? "Thêm Nhân Viên" : "Cập nhật thông tin" %></h2>
    <form action="NhanVienServlet" method="post">
        <input type="hidden" name="maNV" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getMaNV() : "" %>">
        
        Họ: <input type="text" name="ho" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getHo() : "" %>"><br>
        
        Tên: <input type="text" name="ten" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getTen() : "" %>"><br>
        
        Giới tính: <input type="text" name="gioitinh" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getGioitinh() : "" %>"><br>
        
        Ngày sinh: <input type="date" name="ngaysinh" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getNgaysinh() : "" %>"><br>
        
        Địa chỉ: <input type="text" name="diachi" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getDiachi() : "" %>"><br>
        
        Lương: <input type="number" name="luong" step="0.01" value="<%= request.getAttribute("nhanVien") != null ? ((NhanVien) request.getAttribute("nhanVien")).getLuong() : "" %>"><br>
        
        <input type="submit" value="<%= request.getAttribute("nhanVien") == null ? "Lưu" : "Cập nhật" %>">
        <input type="hidden" name="action" value="<%= request.getAttribute("nhanVien") == null ? "insert" : "update" %>">
    </form>
</body>
</html>
