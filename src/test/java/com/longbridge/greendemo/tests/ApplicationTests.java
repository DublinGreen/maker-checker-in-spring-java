package com.longbridge.greendemo.tests;

import com.longbridge.greendemo.Application;
//import com.longbridge.greendemo.model.User;
import com.longbridge.greendemo.jwt.DAOUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		DAOUser user = restTemplate.getForObject(getRootUrl() + "/users/1", DAOUser.class);
		System.out.println(user.getUsername());
		Assert.assertNotNull(user);
	}

//	@Test
//	public void testCreateUser() {
//		DAOUser user = new DAOUser();
//		user.setEmail("admin@gmail.com");
//		user.setUsername("admin");
//		user.setLastName("admin");
//		user.setCreatedBy("admin");
//		user.setUpdatedBy("admin");
//
//		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
//		Assert.assertNotNull(postResponse);
//		Assert.assertNotNull(postResponse.getBody());
//	}

//	@Test
//	public void testUpdatePost() {
//		int id = 1;
//		User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		user.setFirstName("admin1");
//		user.setLastName("admin2");
//
//		restTemplate.put(getRootUrl() + "/users/" + id, user);
//
//		User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		Assert.assertNotNull(updatedUser);
//	}
//
	@Test
	public void testDeletePost() {
		int id = 2;
		DAOUser user = restTemplate.getForObject(getRootUrl() + "/users/" + id, DAOUser.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/users/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/users/" + id, DAOUser.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
