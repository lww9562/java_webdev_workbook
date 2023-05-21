package org.zerock.jdbcex.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.zerock.jdbcex.dto.TodoDTO;
import org.zerock.jdbcex.service.TodoService;

import java.io.IOException;

@WebServlet(name = "todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {
	private TodoService todoService = TodoService.INSTANCE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long tno = Long.parseLong(req.getParameter("tno"));

			TodoDTO todoDTO = todoService.get(tno);

			//데이터 담기
			req.setAttribute("dto", todoDTO);

			req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);

		} catch(Exception e ){
			log.error(e.getMessage());
			throw new ServletException("read error!");
		}
	}
}
