package shure.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import shure.utilities.CredentialsAuthenticator;

@WebMvcTest(CredentialsController.class)
class CredentialsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CredentialsAuthenticator authenticator;

    private String apiBaseUrl = "/api/v1/";
    private String hash = "{ \"hash\":\"pw123\" }";

    @Test
    void testVerify_Success() throws Exception {
        
    	when(authenticator.verifyCredentials(anyString())).thenReturn(true);
        this.mockMvc.perform(post(apiBaseUrl + "verifyCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hash))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    void testVerify_Failure() throws Exception {

    	when(authenticator.verifyCredentials(anyString())).thenReturn(false);
        this.mockMvc.perform(post(apiBaseUrl + "verifyCredentials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hash))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
    
}
