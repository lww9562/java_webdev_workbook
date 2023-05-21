package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
	private TodoDAO todoDAO;

	@BeforeEach
	public void ready() {
		todoDAO = new TodoDAO();
	}

	@Test
	public void testTime() throws Exception {
		System.out.println(todoDAO.getTime());
	}

	@Test
	public void testTime2() throws Exception {
		System.out.println(todoDAO.getTime2());
	}

	@Test
	public void testInsert() throws Exception {
		TodoVO todoVO = TodoVO.builder()
				.title("Sample Title...")
				.dueDate(LocalDate.of(2022,12,31))
				.build();

		todoDAO.insert(todoVO);
	}

	@Test
	public void testList() throws Exception {
		List<TodoVO> list = todoDAO.selectAll();

		list.forEach(System.out::println);
	}

	@Test
	public void testSelectOne() throws Exception {
		long tno = 1L;

		TodoVO vo = todoDAO.selectOne(tno);

		System.out.println(vo);
	}

	@Test
	public void testUpdateOne() throws Exception {
		TodoVO todoVO = TodoVO.builder()
				.tno(1L)
				.title("Sample Title...")
				.dueDate(LocalDate.of(2022,12,31))
				.finished(true)
				.build();

		todoDAO.updateOne(todoVO);
	}
}
