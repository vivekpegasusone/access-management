package com.drishti.accessmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.drishti.accessmanagement.config.ApplicationTestConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationTestConfiguration.class})
class AccessManagementApplicationTests {

	@Test
	void contextLoads() {
	}
}
