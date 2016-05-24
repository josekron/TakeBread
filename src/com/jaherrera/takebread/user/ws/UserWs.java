package com.jaherrera.takebread.user.ws;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jaherrera.takebread.user.exception.UserNotFoundException;
import com.jaherrera.takebread.user.service.UserService;
import com.jaherrera.takebread.user.vo.UserVo;

@Path("/user")
public class UserWs {

	/** The Constant log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWs.class.getName());
	
	@Inject
	UserService userService;
	
	@GET
	@Path("/resume")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadResume(){
		
		LOGGER.info("[UserWs - loadResume] - init");
		long currentSystemTime = System.currentTimeMillis();
		
		String json = null;
		Gson gson = new Gson();

		try {
			
			String resume = userService.resumeBreads();
			
			json = gson.toJson(resume);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
			
		}catch(Exception ex){
			LOGGER.error("[UserWs - loadResume] - Error: "+ex);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
			
		}finally{
			LOGGER.info("[UserWs - loadResume] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(UserVo userVo){
		
		LOGGER.info("[UserWs - createUser] - init");
		long currentSystemTime = System.currentTimeMillis();

		try{
			if(userVo == null)
				throw new IllegalArgumentException("userVo cannot be null");
			
			userService.createUser(userVo);
			return Response.ok().build();
			
		}catch (IllegalArgumentException ex) {
			LOGGER.error("[UserWs - createUser] - Error: "+ex);
			return Response.serverError().status(Status.BAD_REQUEST).build();
			
		}catch (Exception ex) {
			LOGGER.error("[UserWs - createUser] - Error: "+ex);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
			
		}finally{
			LOGGER.info("[UserWs - createUser] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBread(UserVo userVo){
		
		LOGGER.info("[UserWs - updateBread] - init");
		long currentSystemTime = System.currentTimeMillis();

		try{
			if(userVo == null)
				throw new IllegalArgumentException("userVo cannot be null");
			if(userVo.getIsBread() == null)
				throw new IllegalArgumentException("userVo.getIsBread() cannot be null");
			if(userVo.getBreadType() == null)
				throw new IllegalArgumentException("userVo.getBreadType() cannot be null");
			
			userService.updateBread(userVo.getUserCode(), userVo.getIsBread(), userVo.getBreadType());
			return Response.ok().build();
			
		}catch (IllegalArgumentException ex) {
			LOGGER.error("[UserWs - updateBread] - Error: "+ex);
			return Response.serverError().status(Status.BAD_REQUEST).build();
			
		}catch (UserNotFoundException ex) {
			LOGGER.error("[UserWs - updateBread] - Error: "+ex);
			return Response.serverError().status(Status.NOT_FOUND).build();
			
		}catch (Exception ex) {
			LOGGER.error("[UserWs - updateBread] - Error: "+ex);
			return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
			
		}finally{
			LOGGER.info("[UserWs - updateBread] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
}
