package com.hcl.medicalclaim;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MedicalclaimApplicationTests {

	@Test
    public void shouldLoadApplicationContext() {
    }

    @Test
    public void applicationTest() {
    	MedicalclaimApplication.main(new String[] {});
    }

}
