package test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.jaherrera.takebread.user.vo.BreadTypeEnum;
import com.jaherrera.takebread.user.vo.UserVo;
import com.jayway.restassured.RestAssured;

public class TestBread {
	
	private String basePath;
	private static UserVo user;

	@Before
    public void setUp(){
		
		basePath = "http://localhost:8080/TakeBread";
		user = new UserVo();
    }
	
	@AfterClass
	public static void resetUser(){
		user = new UserVo();
	}
	
	@Test
	public void testWantNormalBread() {

		user.setUserCode(4365);
		user.setIsBread(true);
		user.setBreadType(BreadTypeEnum.NORMAL);

        try {
        	given().
        	header("accept", "application/json").body(user).contentType(JSON)
            .expect().statusCode(200)
            .when().put(basePath+"/user");
        	
        	given().
        	header("accept", "application/json").body(user).contentType(JSON)
            .expect().statusCode(200).body(containsString("1 normal bread and 0 wholemeal bread")).contentType(JSON)
            .when().get(basePath+"/user/resume");
        	
        } finally {
            RestAssured.reset();
        }
	}
	
	@Test
	public void testWantWholemealBread() {

		user.setUserCode(4365);
		user.setIsBread(true);
		user.setBreadType(BreadTypeEnum.WHOLEMEAL);

        try {
        	given().
        	header("accept", "application/json").body(user).contentType(JSON)
            .expect().statusCode(200)
            .when().put(basePath+"/user");
        	
        	given().
        	header("accept", "application/json").body(user).contentType(JSON)
            .expect().statusCode(200).body(containsString("0 normal bread and 1 wholemeal bread")).contentType(JSON)
            .when().get(basePath+"/user/resume");
        	
        } finally {
            RestAssured.reset();
        }
	}
}
