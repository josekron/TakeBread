package com.jaherrera.takebread.user.service;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaherrera.takebread.listener.MongoClientManagerFactory;
import com.jaherrera.takebread.user.dao.UserDAO;
import com.jaherrera.takebread.user.exception.UserNotFoundException;
import com.jaherrera.takebread.user.exception.UserServiceException;
import com.jaherrera.takebread.user.vo.BreadTypeEnum;
import com.jaherrera.takebread.user.vo.UserVo;
import com.mongodb.MongoClient;

public class UserService implements Serializable{

	private static final long serialVersionUID = -6883378201749038834L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());
	
	private MongoClient mongoClient;
	private Morphia morphia;
	private UserDAO userDAO;

	public UserService(){
		super();
		LOGGER.info("[UserService - Constructor] - init");
		
		mongoClient = MongoClientManagerFactory.getMongoClientManager();
		morphia = new Morphia();
		morphia.map(UserVo.class);
		userDAO = new UserDAO(mongoClient, morphia, MongoClientManagerFactory.getDatabase());
	}
	
	public UserVo loadByUserCode(Integer userCode)  throws UserNotFoundException{
		LOGGER.info("[UserService - loadByUserCode] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		if(userCode == null){
			LOGGER.error("[UserService - loadByUserCode] - id cannot be null");
			throw new IllegalArgumentException();
		}
		UserVo geoProfileVo = userDAO.findByUserCode(userCode);
		
		if(geoProfileVo == null){
			LOGGER.error("[UserService - loadByUserCode] - geoProfileVo not found");
			throw new UserNotFoundException();
		}
		
		LOGGER.debug("[UserService - loadByUserCode] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		return geoProfileVo;
	}
	
	public void createUser(UserVo userVo){
		LOGGER.info("[UserService - createUser] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		if(userVo == null){
			LOGGER.error("[UserService - createUser] - userVo cannot be null");
			throw new IllegalArgumentException();
		}
		
		userDAO.saveUser(userVo);
		
		LOGGER.debug("[UserService - createUser] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
	}
	
	public String resumeBreads(){
		LOGGER.info("[UserService - resumeBreads] - init");
		long currentSystemTime=System.currentTimeMillis();
		String resume = "";
		try{
			List<UserVo> users = userDAO.findAll();
			
			if(users == null || users.isEmpty()){
				resume = "0 normal bread and 0 wholemeal bread";
			}
			else{
				LOGGER.debug("[UserService - resumeBreads] - user list size:{}",users.size());
				
				Integer normal = 0;
				Integer wholemeal = 0;
				
				for(UserVo user : users){
					if(user.getIsBread()){
						if(user.getBreadType().equals(BreadTypeEnum.NORMAL))
							normal++;
						else
							wholemeal++;
					}
				}
				
				resume = normal+" normal bread and "+wholemeal+" wholemeal bread";
			}
			LOGGER.debug("[UserService - resumeBreads] - resume:{}",resume);
			return resume;
			
		}finally{
			LOGGER.debug("[UserService - resumeBreads] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
	
	public void resetAllUsers(){
		LOGGER.info("[UserService - resetAllUsers] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		try{
			List<UserVo> users = userDAO.findAll();
			LOGGER.debug("[UserService - resetAllUsers] - Loaded user list size:{}",users.size());
			
			for(UserVo user: users){
				
				if(user.getIsBread()){
					
					if(user.getTotalBread()==null)
						user.setTotalBread(0);
					
					user.setTotalBread(user.getTotalBread()+1);
				}
				
				user.setIsBread(false);	
				updateUser(user, false);
			}
			
		}catch(UserNotFoundException ex){
			throw new UserServiceException();
			
		}catch(UserServiceException ex){
			throw ex;	
		}finally{
			LOGGER.debug("[UserService - resetAllUsers] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
	
	public void updateBread(Integer userCode, Boolean isBread, BreadTypeEnum breadType) throws UserNotFoundException{
		LOGGER.info("[UserService - updateBread] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		if(userCode==null)
			throw new IllegalArgumentException("[UserService - updateBread] - userCode cannot be null");
		if(isBread==null)
			throw new IllegalArgumentException("[UserService - updateBread] - isBread cannot be null");
		if(breadType==null)
			throw new IllegalArgumentException("[UserService - typeBread] - breadType cannot be null");
		
		try{
			LOGGER.debug("[UserService - updateBread] - load user by userCode");
			UserVo userVo = userDAO.findByUserCode(userCode);
			
			if(userVo == null)
				throw new UserNotFoundException("[UserService - updateBread] - userCode: "+userCode+"not found");
			
			userVo.setIsBread(isBread);
			userVo.setBreadType(breadType);
			
			updateUser(userVo, false);
			
		}catch(UserNotFoundException ex){
			throw ex;		
		}catch(UserServiceException ex){
			throw ex;	
		}finally{
			LOGGER.debug("[UserService - updateBread] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
	}
	
	private void updateUser(UserVo userVo, Boolean copyIfNull) throws UserNotFoundException, UserServiceException{
		LOGGER.info("[UserService - updateUser] - init");
		long currentSystemTime=System.currentTimeMillis();
		
		if(userVo == null){
			LOGGER.error("[UserService - updateUser] - userVo cannot be null");
			throw new IllegalArgumentException();
		}
		if(userVo.getId() == null){
			LOGGER.error("[UserService - updateUser] - objectId cannot be null");
			throw new IllegalArgumentException();
		}
		if(copyIfNull == null){
			LOGGER.error("[UserService - updateUser] - copyIfNull cannot be null");
			throw new IllegalArgumentException();
		}
		
		UserVo user = userDAO.findById(userVo.getId());
		if(user==null){
			LOGGER.error("[UserService - updateUser] - userVo not found in BD");
			throw new UserNotFoundException();
		}
		
		LOGGER.info("[UserService - updateUser] - updating userVo id:{}",userVo.getId());
		userDAO.updateUser(userVo, copyIfNull);
		
		LOGGER.debug("[UserService - updateUser] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		
	}
}
