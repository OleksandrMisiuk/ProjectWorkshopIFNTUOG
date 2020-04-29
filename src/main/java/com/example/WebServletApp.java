package com.example;

import com.example.dao.TodoDao;
import com.example.domain.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/DemoServlet")
public class WebServletApp extends HttpServlet {
    private TodoDao todoDao = new TodoDao(Persistence.createEntityManagerFactory("rules-it").createEntityManager());
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Todo> todos = todoDao.findAll();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>MIsiuk Lab #3</h1>");
        out.println("<h2>Todo List: </h2>");
        todos.forEach(todo -> {
            out.println("<h3>" + todo.toString() + "</h3>");
            out.println();
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }
        Todo result = objectMapper.readValue(jb.toString(), Todo.class);
        resp.setContentType("text/html");
        resp.setStatus(201);
        PrintWriter out = resp.getWriter();
        todoDao.create(result);
        out.println(todoDao.update(result).toString());
    }

}