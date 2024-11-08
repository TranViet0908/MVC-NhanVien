<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Nhân Viên</title>
</head>
<body>
    <h1>Danh Sách Nhân Viên</h1>
    <a href="NhanVienServlet?action=new">Thêm Nhân Viên</a>
    <table border="1">
        <thead>
            <tr>
                <th>Mã NV</th>
                <th>Họ</th>
                <th>Tên</th>
                <th>Giới Tính</th>
                <th>Ngày Sinh</th>
                <th>Địa Chỉ</th>
                <th>Lương</th>
                <th>Thao Tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="nhanVien" items="${listNhanVien}">
                <tr>
                    <td>${nhanVien.maNV}</td>
                    <td>${nhanVien.ho}</td>
                    <td>${nhanVien.ten}</td>
                    <td>${nhanVien.gioitinh}</td>
                    <td>${nhanVien.ngaysinh}</td>
                    <td>${nhanVien.diachi}</td>
                    <td>${nhanVien.luong}</td>
                    <td>
                        <a href="NhanVienServlet?action=edit&maNV=${nhanVien.maNV}">Sửa</a> |
                        <a href="NhanVienServlet?action=delete&maNV=${nhanVien.maNV}" onclick="return confirm('Bạn có chắc chắn muốn xóa nhân viên này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
