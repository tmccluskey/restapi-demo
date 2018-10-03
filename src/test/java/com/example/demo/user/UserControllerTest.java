package com.example.demo.user;

import com.example.demo.GlobalControllerExceptionHandler;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class UserControllerTest {

    @InjectMocks
    UserController controller;

    @Mock
    UserService mockService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller, new GlobalControllerExceptionHandler())
                .build();
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUserWithError() throws Exception {
        doThrow(new BadRequestException()).when(mockService).createUser(any());

        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException() instanceof BadRequestException);
    }

    @Test
    public void testGetUser() throws Exception {
        when(mockService.getUser(any())).thenReturn(new User());

        mockMvc.perform(get("/users/{username}", "mockUsername"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserWithError() throws Exception {
        doThrow(new BadRequestException()).when(mockService).getUser(any());

        MvcResult result = mockMvc.perform(get("/users/{username}", "mockUsername"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException() instanceof BadRequestException);
    }

    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(delete("/users/{username}", "mockUsername"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserWithError() throws Exception {
        doThrow(new BadRequestException()).when(mockService).deleteUser(any());

        MvcResult result = mockMvc.perform(delete("/users/{username}", "mockUsername"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException() instanceof BadRequestException);
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(put("/users/{username}", "mockUsername")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserWithError() throws Exception {
        doThrow(new BadRequestException()).when(mockService).updateUser(any(), any());

        MvcResult result = mockMvc.perform(put("/users/{username}", "mockUsername")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new User())))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException() instanceof BadRequestException);
    }
}

