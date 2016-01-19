package com.jaherrera.takebread.maintenance;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




/**
 * The Class ReadXmlFile.
 */
public class ReadXmlFile {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadXmlFile.class.getName());
	
	/** The path config. */
	private static String PATH_CONFIG = "/opt/share/takebread/config/config.xml";
	
	/**
	 * Instantiates a new read xml file.
	 */
	ReadXmlFile(){
		super();
	}
	
	/**
	 * Gets the mongo client config.
	 *
	 * @return the mongo client config
	 */
	public static MongoClientConfigVo getMongoClientConfig(){
		LOGGER.debug("[ReadXmlFile - readXmlFileMongoClientConfig] >> Init");
		long currentSystemTime = System.currentTimeMillis();
		MongoClientConfigVo mongoClientConfigVo = new MongoClientConfigVo();
		
		try {
			
			LOGGER.debug("[ReadXmlFile - readXmlFileMongoClientConfig] >> Location file: /opt/share/cityquest/config/config.xml");
			File fXmlFile = new File(PATH_CONFIG);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			LOGGER.debug("[ReadXmlFile - readXmlFileMongoClientConfig] >> Root Element: "+ doc.getDocumentElement().getNodeName());
			
			NodeList  nListMongoClient  =  doc.getElementsByTagName("mongoClient");	
			Node nMongoClient = nListMongoClient.item(0);
			
			LOGGER.debug("[ReadXmlFile - readXmlFileMongoClientConfig] >> Child mongoClient: "+ nMongoClient.getNodeName());
			
			Element eElement = (Element) nMongoClient;
			
			mongoClientConfigVo.setHost(eElement.getElementsByTagName("host").item(0).getTextContent());
			mongoClientConfigVo.setPort(eElement.getElementsByTagName("port").item(0).getTextContent());
			mongoClientConfigVo.setConnectionsPerHost(Integer.parseInt(eElement.getElementsByTagName("connectionsPerHost").item(0).getTextContent()));
			mongoClientConfigVo.setDatabase(eElement.getElementsByTagName("database").item(0).getTextContent());

			LOGGER.debug("[ReadXmlFile - readXmlFileMongoClientConfig] >> mongoClientConfigVo: "+mongoClientConfigVo.toString());
			
		} catch (ParserConfigurationException | SAXException |  IOException e) {
			LOGGER.error("[ReadXmlFile - readXmlFileMongoClientConfig] - ERROR Config not read - Load Default values");
			mongoClientConfigVo.setHost("localhost");
			mongoClientConfigVo.setPort("27017");
			mongoClientConfigVo.setConnectionsPerHost(200);
			mongoClientConfigVo.setDatabase("takebread_db");
			
		} finally{
			LOGGER.info("[ReadXmlFile - readXmlFileMongoClientConfig] - Finish Timing:"+(System.currentTimeMillis()-currentSystemTime));
		}
		
		return mongoClientConfigVo;
	}
	

}
