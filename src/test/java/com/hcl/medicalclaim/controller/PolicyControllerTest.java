package com.hcl.medicalclaim.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.medicalclaim.common.AppConstants;
import com.hcl.medicalclaim.dto.ClaimAppoveDto;
import com.hcl.medicalclaim.dto.PolicyClaimDto;
import com.hcl.medicalclaim.dto.PolicyDto;
import com.hcl.medicalclaim.dto.UserIdDto;
import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.response.ApiResponse;
import com.hcl.medicalclaim.response.EntityResponse;
import com.hcl.medicalclaim.service.PolicyService;

@RunWith(SpringJUnit4ClassRunner.class)
public class PolicyControllerTest{

    @InjectMocks
    PolicyController policyController;
    
    @Mock
    PolicyService policyService;
   
    private MockMvc mockMvc;
    @Mock
    private TestRestTemplate restTemplate;
    @Before 
    public void init() {
    	mockMvc = MockMvcBuilders.standaloneSetup(policyController).build();
    	restTemplate = new TestRestTemplate();
    }
    
    @Test
    public void testGetPolicyById() throws Exception {
    	PolicyDto policyDto = new PolicyDto();
    	policyDto.setPolicyNo("P00001");
		
    	when(policyService.getPolicyById(policyDto)).thenReturn(new Policy());
    	@SuppressWarnings("rawtypes")
		ResponseEntity<EntityResponse> response = policyController.getPolicyById(policyDto);
    	assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
    
    }
    
    @Test
    public void testGetPolicyByIdNotFound() throws Exception {
    	PolicyDto policyDto = new PolicyDto();
    	policyDto.setPolicyNo("P001");
		
    	when(policyService.getPolicyById(policyDto)).thenReturn(null);
    	@SuppressWarnings("rawtypes")
		ResponseEntity<EntityResponse> response = policyController.getPolicyById(policyDto);
    	assertEquals(AppConstants.NO_RECORD_FOUND, response.getBody().getMessage());
    	assertEquals(AppConstants.FAILURE, response.getBody().getStatus());
    	assertEquals(null, response.getBody().getData());
    }
    
    @Test
	public void testClaimListByUser() throws Exception{
		UserIdDto userIdDto = new UserIdDto();
		userIdDto.setUserId("moorthy127@gmail.com");

		//MvcResult for mockmvc performed
		mockMvc.perform(MockMvcRequestBuilders.post("/claims")
                .content(asJsonString(userIdDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	
	}
    
    @Test
  	public void testCreatePolicyClaim() throws Exception{
    	PolicyClaimDto policyClaimDto = new PolicyClaimDto();
    	policyClaimDto.setHospitalId(1L);
    	policyClaimDto.setPolicyNo("P0001");
    	policyClaimDto.setReason("Policy Created");
    	ApiResponse apiResponse = new ApiResponse(AppConstants.SUCCESS, 200, AppConstants.SUCCESS_MESSAGE);

    	when(policyService.createPolicyClaim(policyClaimDto)).thenReturn(apiResponse);
		ResponseEntity<ApiResponse> response = policyController.createPolicyClaim(policyClaimDto);
		assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
  	}
    
    @Test
  	public void testClaimApprovedByUser() throws Exception{
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(1L);
    	approveDto.setPolicyId(1);
    	approveDto.setRequestId(1);
    	approveDto.setComments("User Approved");
    	ApiResponse apiResponse = new ApiResponse(AppConstants.SUCCESS, 200, AppConstants.SUCCESS_MESSAGE);

		when(policyService.claimApprove(approveDto)).thenReturn(apiResponse);
		ResponseEntity<ApiResponse> response = policyController.claimApprovedByUser(approveDto);
		assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
  	}
    
    @Test
  	public void testClaimRejectedByUser() throws Exception{
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(1L);
    	approveDto.setPolicyId(1);
    	approveDto.setRequestId(1);
    	approveDto.setComments("User Rejected");
    	ApiResponse apiResponse = new ApiResponse(AppConstants.SUCCESS, 200, AppConstants.SUCCESS_MESSAGE);

		when(policyService.claimReject(approveDto)).thenReturn(apiResponse);
		ResponseEntity<ApiResponse> response = policyController.claimRejectedByUser(approveDto);
		assertEquals(AppConstants.SUCCESS, response.getBody().getStatus());
  	}
    
    @Test
	public void testGetPolicyStatus() throws Exception{
    	PolicyDto policyDto = new PolicyDto();
    	policyDto.setPolicyNo("P3888333");
    	
		//MvcResult for mockmvc performed
		mockMvc.perform(MockMvcRequestBuilders.post("/check/policy-status")
                .content(asJsonString(policyDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}
    
    /**
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) throws Exception{
            return new ObjectMapper().writeValueAsString(obj);
    }
}
