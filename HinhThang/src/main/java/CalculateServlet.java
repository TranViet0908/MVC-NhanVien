import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculate")
public class CalculateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        double base1 = Double.parseDouble(request.getParameter("base1"));
        double base2 = Double.parseDouble(request.getParameter("base2"));
        double height = Double.parseDouble(request.getParameter("height"));
        
        // Tính diện tích hình thang
        double area = ((base1 + base2) * height) / 2;
        
        // Gửi kết quả về lại trang JSP
        request.setAttribute("area", area);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
