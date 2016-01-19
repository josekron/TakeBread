package com.jaherrera.takebread.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Servlet implementation class CurrentDayServlet
 */
@WebServlet("/CurrentDayServlet")
public class CurrentDayServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Integer HOUR_UPDATE = 9;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentDayServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentDayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doService(request, response);
	}
	
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("[CurrentDayServlet - doService] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		try{
			
			Calendar cal = Calendar.getInstance();
			Integer hour = cal.get(Calendar.HOUR_OF_DAY);
			
			if(hour>=HOUR_UPDATE){
				cal.add(Calendar.DATE, 1);
			}
			
			String res = getNameDayWeek(cal.get(Calendar.DAY_OF_WEEK))+", "+cal.get(Calendar.DAY_OF_MONTH)+" "+getNameMonth(cal.get(Calendar.MONTH));
			
			String json = new Gson().toJson(res);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
		    
			return;
			
		}finally{
			LOGGER.debug("[CurrentDayServlet - doService] - Finish Timing:{}",(System.currentTimeMillis()-currentSystemTime));	
		}
	}

	private static String getNameMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
	
	private static String getNameDayWeek(int day){
	    String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	    return dayNames[day];
	}
}
