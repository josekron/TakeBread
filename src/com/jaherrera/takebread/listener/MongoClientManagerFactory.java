package com.jaherrera.takebread.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaherrera.takebread.maintenance.MongoClientConfigVo;
import com.jaherrera.takebread.maintenance.ReadXmlFile;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

/**
 * A factory for creating MongoClientManager objects.
 */
@WebListener
public class MongoClientManagerFactory {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoClientManagerFactory.class.getName());
	
	/** The mongo client. */
	private static MongoClient mongoClient;
	
	/** The database. */
	private static String database;

	/**
	 * Instantiates a new mongo client manager factory.
	 */
	public MongoClientManagerFactory(){
		LOGGER.debug("[MongoClientManagerFactory] >> Constructor");
	}
	
	/**
	 * Context destroyed.
	 *
	 * @param arg0 the arg0
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		mongoClient.close();
		LOGGER.debug("[MongoClientManagerFactory] >> contextDestroyed. Close createMongoClientManager");	
	}
	
	/**
	 * Context initialized.
	 *
	 * @param arg0 the arg0
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.debug("[GeolocationDaoManagerFactory.contextInitialized] >> Init");
    	long currentSystemTime=System.currentTimeMillis();

    	try{
    		
    		mongoClient = getMongoClientManager();
    		
    		LOGGER.debug("[MongoClientManagerFactory.contextInitialized] >> Initializes getMongoClientManager");

    	}catch(Exception e){
    		LOGGER.error("[MongoClientManagerFactory.contextInitialized] >> Exception",e);

    	}finally{
    		LOGGER.debug("[MongoClientManagerFactory.contextInitialized] >> Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
    	}
		
	}
	
	/**
	 * Gets the mongo client manager.
	 *
	 * @return the mongo client manager
	 */
	public static MongoClient getMongoClientManager()
    {  
    	long currentSystemTime=System.currentTimeMillis();
    	
    	if(mongoClient==null){
    		try{    			
    			LOGGER.debug("[MongoClientManagerFactory.contextInitialized] >> load mongoClientConfigVo");

    			MongoClientConfigVo mongoClientConfigVo = ReadXmlFile.getMongoClientConfig();
    			
    			database = mongoClientConfigVo.getDatabase();
    			
    			MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(mongoClientConfigVo.getConnectionsPerHost()).build();
    			
    			String mongoClientURI = mongoClientConfigVo.getHost()+":"+mongoClientConfigVo.getPort();
        		mongoClient = new MongoClient(mongoClientURI, options);
        		
        		LOGGER.debug("[MongoClientManagerFactory.contextInitialized] >> Initializes mongoClient");

        	}catch(Exception e){
        		LOGGER.error("[MongoClientManagerFactory.contextInitialized] >> Exception",e);

        	}finally{
        		LOGGER.debug("[MongoClientManagerFactory.contextInitialized] >> Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
        	}
    	}
    	
    	if (mongoClient == null)  
    		throw new IllegalStateException("Context is not initialized yet.");

    	LOGGER.debug("[MongoClientManagerFactory.createMongoClientManager] >> Creating Mongo Client Manager. Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
    	return mongoClient;
    }  
	
	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public static String getDatabase(){
		return database;
	}
	
}
