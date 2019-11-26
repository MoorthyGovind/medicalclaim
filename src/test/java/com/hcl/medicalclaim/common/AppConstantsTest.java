package com.hcl.medicalclaim.common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AppConstantsTest {

	@Before
	public void init() {
	}
	
	@Test
	public void testAppConstants() {
	    assertEquals("SUCCESS", AppConstants.SUCCESS);
	}
}
