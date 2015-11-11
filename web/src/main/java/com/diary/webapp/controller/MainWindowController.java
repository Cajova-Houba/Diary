package com.diary.webapp.controller;

import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller will display the main page of diary application.
 * @author Zdenda
 *
 */
@Controller
@RequestMapping("/diary")
public class MainWindowController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {
		return new ModelAndView("diary");
	}
}
