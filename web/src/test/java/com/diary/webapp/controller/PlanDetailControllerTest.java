package com.diary.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PlanDetailControllerTest extends BaseControllerTestCase {

	@Autowired
	private PlanDetailController controller;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		InternalResourceViewResolver viewresolver = new InternalResourceViewResolver();
		viewresolver.setPrefix("/WEB-INF/pages/");
		viewresolver.setSuffix(".jsp");
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewresolver).build();
	}
	
	@Test
	public void testDetail() throws Exception {
		log.debug("Testing detail..");
		mockMvc.perform(get("/plan/detail")
				.param("id","-1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("goalList"))
				.andExpect(view().name("plandetail"));
	}
}
