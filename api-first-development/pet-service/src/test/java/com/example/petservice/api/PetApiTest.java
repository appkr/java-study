package com.example.petservice.api;

import com.example.petservice.PetServiceApplication;
import com.example.petservice.api.model.PetDto;
import com.example.petservice.api.model.PetTypeDto;
import com.example.petservice.domain.Pet;
import com.example.petservice.domain.repository.PetRepository;
import com.example.petservice.service.mapper.PetMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PetServiceApplication.class})
public class PetApiTest {

    private MockMvc mvc;
    @Autowired
    private PetApiDelegateImpl apiDelegate;
    @Autowired private ApiExceptionHandler exceptionHandler;
    @Autowired private PetMapper mapper;
    @Autowired private PetRepository repository;

    @Test
    @Transactional
    public void createPet() throws Exception {
        ResultActions res = mvc.perform(
            post("/api/pets")
                .contentType(MediaType.valueOf(APPLICATION_JSON_VALUE))
                .content(convertObjectToJsonBytes(createDto()))
        ).andDo(print());

        res.andExpect(status().is2xxSuccessful())
            .andExpect(header().exists("Location"));
    }

    @Test
    @Transactional
    public void listPets() throws Exception {
        final PetDto dto = createDto();
        final Pet entity = mapper.toEntity(dto);
        repository.save(entity);

        ResultActions res = mvc.perform(
            get("/api/pets")
                .accept(MediaType.valueOf(APPLICATION_JSON_VALUE))
                .param("name", "Foo")
        ).andDo(print());

        res.andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void getPet() throws Exception {
        final PetDto dto = createDto();
        final Pet entity = mapper.toEntity(dto);
        repository.save(entity);

        ResultActions res = mvc.perform(
            get("/api/pets/{petId}", entity.getId())
                .accept(MediaType.valueOf(APPLICATION_JSON_VALUE))
        ).andDo(print());

        res.andExpect(status().is2xxSuccessful());
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetApiController controller = new PetApiController(apiDelegate);
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(exceptionHandler)
            .build();
    }

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        final ObjectMapper om = new ObjectMapper();
        return om.writeValueAsBytes(object);
    }

    private PetDto createDto() {
        return new PetDto()
            .name("Foo")
            .petType(PetTypeDto.CAT);
    }
}