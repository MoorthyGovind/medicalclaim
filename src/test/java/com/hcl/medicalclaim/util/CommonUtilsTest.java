package com.hcl.medicalclaim.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CommonUtilsTest {

	@Before
	public void init() {
	}
	
	@Test
	public void getClaimAmount() {
		CommonUtils util = new CommonUtils();
		double result = util.getClaimAmount(20000, 80);
		assertEquals(16000.0, result);

	}
	
}
