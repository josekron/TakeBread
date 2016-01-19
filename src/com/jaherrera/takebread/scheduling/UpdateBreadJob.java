package com.jaherrera.takebread.scheduling;

import java.io.Serializable;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaherrera.takebread.user.exception.UserServiceException;
import com.jaherrera.takebread.user.service.UserService;

@Stateless
public class UpdateBreadJob implements Serializable{

	private static final long serialVersionUID = -220227848287657824L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBreadJob.class.getName());
	
	@Inject
	UserService userService;
	
	@Schedule(dayOfWeek = "*", hour = "9", minute = "01", second = "10")
	public void doExecute()
	{  
		LOGGER.info("[UpdateBreadJob - execute] - init");
		long T1=System.currentTimeMillis();
		
		try {
			
			LOGGER.debug("[UpdateBreadJob - execute] - Reset bread users");
			userService.resetAllUsers();
		
		}catch(UserServiceException ex){
			LOGGER.error("[UpdateBreadJob - execute] - Error: "+ex);
			throw ex;
		}finally{
			LOGGER.debug("[UpdateBreadJob - execute] >> Finish Timing:{}",(System.currentTimeMillis()-T1));
		}

	} 
}
