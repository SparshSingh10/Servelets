package com.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = { "/LoginServlet" },
        initParams = {
                @WebInitParam(name = "user", value = "sparsh"),
                @WebInitParam(name = "password", value = "Passw0rdd"),
        }
)

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        // Username validation: Must start with a capital letter and be at least 3 characters long
        String usernameRegex = "^[A-Z][A-Za-z]{2,}$";
        Pattern usernamePattern = Pattern.compile(usernameRegex);
        Matcher usernameMatcher = usernamePattern.matcher(user);

        // Updated password validation regex
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=[^!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/]*[!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/][^!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/]*$).{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(pwd);


        String userId = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");

        if (!usernameMatcher.matches()) {
            out.println("<font color=red>Username must start with a capital letter and have at least 3 characters</font>");
            rd.include(request, response);
        } else if (!passwordMatcher.matches()) {
            out.println("<font color=red>Password must be at least 8 characters long, have at least 1 uppercase letter, 1 number, and exactly 1 special character</font>");
            rd.include(request, response);
        } else if (userId.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
        } else {
            out.println("<font color=red>Either username or password is incorrect</font>");
            rd.include(request, response);
        }
    }
}
