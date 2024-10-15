package org.novi.languagelearner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private ObjectMapper objectMapper;

    private GroupRequestDTO groupRequestDTO;
    private GroupResponseDTO groupResponseDTO;

    @BeforeEach
    void setUp() {
        groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setGroupName("Test Group");
        groupRequestDTO.setUserName("testUser");

        groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setGroupName("Test Group");
        groupResponseDTO.setUserName("testUser");
    }

    @Test
    @WithMockUser(username = "testUser")
    void createGroup_ShouldReturnGroupResponseDTO() throws Exception {

        Mockito.when(groupService.createGroup(Mockito.any(GroupRequestDTO.class))).thenReturn(groupResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/groups/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupRequestDTO)))
                .andExpect(jsonPath("$.groupName").value("Test Group"))
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "testUser")
    void getGroupById_ShouldReturnGroupResponseDTO() throws Exception {
        Mockito.when(groupService.getGroupById(Mockito.anyLong())).thenReturn(groupResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/groups/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("Test Group"))
                .andExpect(jsonPath("$.userName").value("testUser"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void getAllGroupsOfUser_ShouldReturnListOfGroupResponseDTO() throws Exception {
        Mockito.when(groupService.getAllGroups(Mockito.anyString())).thenReturn(Collections.singletonList(groupResponseDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/groups/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupName").value("Test Group"))
                .andExpect(jsonPath("$[0].userName").value("testUser"));
    }
}