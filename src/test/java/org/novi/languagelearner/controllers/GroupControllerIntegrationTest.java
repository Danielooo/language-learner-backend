package org.novi.languagelearner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GroupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String requestJson;

    @BeforeEach
    public void setUp() {
        requestJson = """                      
                {
                    "groupName" : "Test Group",
                        "userName" : "testUser",
                        "exercises" : [
                            {
                                "question" : "Question 1",
                                    "answer" : "Answer 1"
                            },
                            {
                                "question": "Question 2",
                                    "answer" : "Answer 2"
                            }
                        ]
                }""";

        System.out.println(requestJson);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void createGroup() throws Exception {
        mockMvc.perform(post("/groups/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("Test Group"))
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.exercises[0].question").value("Question 1"))
                .andExpect(jsonPath("$.exercises[0].answer").value("Answer 1"))
                .andExpect(jsonPath("$.exercises[1].question").value("Question 2"))
                .andExpect(jsonPath("$.exercises[1].answer").value("Answer 2"));
    }


    @Test
    @WithMockUser(username = "testUser")
    public void addExercisesToGroup() throws Exception {
        String requestJson = """
            {
                "id": 1,
                "groupName": "Initial Test Group",
                "userName": "testUser",
                "exercises": [
                    {
                        "question": "New Question 1",
                        "answer": "New Answer 1"
                    },
                    {
                        "question": "New Question 2",
                        "answer": "New Answer 2"
                    }
                ]
            }""";

        mockMvc.perform(patch("/groups/user/add-exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("Initial Test Group 1"))
                .andExpect(jsonPath("$.userName").value("testUser"))
                .andExpect(jsonPath("$.exercises[2].question").value("New Question 1"))
                .andExpect(jsonPath("$.exercises[2].answer").value("New Answer 1"))
                .andExpect(jsonPath("$.exercises[3].question").value("New Question 2"))
                .andExpect(jsonPath("$.exercises[3].answer").value("New Answer 2"));
    }
}



