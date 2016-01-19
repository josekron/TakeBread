package com.jaherrera.takebread.user.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaherrera.takebread.user.exception.UserServiceException;
import com.jaherrera.takebread.user.vo.UserVo;
import com.mongodb.MongoClient;

public class UserDAO extends BasicDAO<UserVo, ObjectId>{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class.getName());
	
	public UserDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
		super(mongoClient, morphia, dbName);
	}

	public UserVo findByUserCode (Integer userCode){
		LOGGER.info("[UserDAO - findByUserCode] - init");
		
		return getDs().find(UserVo.class).field("userCode").equal(userCode).disableValidation().get();
	}
	
	public UserVo findById (String id){
		LOGGER.info("[UserDAO - findById] - init");
		
		ObjectId oid = new ObjectId(id);
		return getDs().find(UserVo.class).field("id").equal(oid).disableValidation().get();
	}
	
	public void saveUser(UserVo userVo){
		LOGGER.info("[UserDAO - saveUser] - init");
		
		getDs().save(userVo);
	}

	public List<UserVo> findAll (){
		LOGGER.info("[UserDAO - findAll] - init");

		return getDs().find(UserVo.class).asList();
	}
	
	public void updateUser(UserVo userVo, Boolean copyIfNull){
		LOGGER.info("[UserDAO - updateUser] - init");
		
		ObjectId oid = new ObjectId(userVo.getId());
		
		UpdateOperations<UserVo> ops = getOpsUpdate(userVo, copyIfNull);
		Query<UserVo> updateQuery = getDs().createQuery(UserVo.class).field("id").equal(oid);
		updateQuery.disableValidation();
		UpdateResults ur = getDs().update(updateQuery, ops);
		
		if(!ur.getUpdatedExisting()){
			LOGGER.error("[UserDAO - updateUser] - UserServiceException - userVo: "+userVo.getId()+" not updated");
			throw new UserServiceException();
		}
	}
	
	private UpdateOperations<UserVo> getOpsUpdate(UserVo source, Boolean copyIfNull){
		LOGGER.info("[UserDAO - getOpsUpdate] - init");
		
		UpdateOperations<UserVo> ops = getDs().createUpdateOperations(UserVo.class);
		
		if(copyIfNull || source.getName()!=null)
			ops.set("name", source.getName());
		if(copyIfNull || source.getIsBread()!=null)
			ops.set("isBread", source.getIsBread());
		if(copyIfNull || source.getBreadType()!=null)
			ops.set("breadType", source.getBreadType());
		if(copyIfNull || source.getUserCode()!=null)
			ops.set("userCode", source.getUserCode());
		if(copyIfNull || source.getTotalBread()!=null)
			ops.set("totalBread", source.getTotalBread());

		return ops;
	}
}
