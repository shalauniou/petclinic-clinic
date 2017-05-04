package com.epam.petclinic.clinic.controller;

import com.epam.petclinic.clinic.model.Clinic;
import com.epam.petclinic.clinic.repository.ClinicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests {@link ClinicController}.
 *
 * Date: 4/29/2017
 *
 * @author Stanislau Halauniou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class ClinicControllerIntegrationTest {

    private static final String CLINIC_NAME = "Clinic Name";
    private static final String CLINIC_ADDRESS = "Clinic Address";

    /**
     * Rest documentation.
     */
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testGetClinic() throws Exception {
        Clinic clinic = clinicRepository.save(getClinic("Clinic Name", "Clinic Address"));
        document = MockMvcRestDocumentation.document("clinic-list", responseFields(
                fieldWithPath("id").description("The clinic ID"),
                fieldWithPath("name").description("The clinic name"),
                fieldWithPath("address").description("The clinic address"),
                fieldWithPath("offers").description("The clinic offers"))
        );
        mockMvc.perform(get("/clinics/" + clinic.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document)
                .andExpect(content().json(objectMapper.writer().writeValueAsString(clinic)));
    }

    @Test
    public void testSaveClinic() throws Exception {
        Clinic clinic = getClinic(CLINIC_NAME, CLINIC_ADDRESS);
        document = MockMvcRestDocumentation.document("clinic-create", requestFields(
                fieldWithPath("id").description("The clinic ID"),
                fieldWithPath("name").description("The clinic name"),
                fieldWithPath("address").description("The clinic address"),
                fieldWithPath("offers").description("The clinic offers"))
        );
        MvcResult result = mockMvc.perform(post("/clinics/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clinic)))
                .andDo(document)
                .andExpect(jsonPath("name").value(CLINIC_NAME))
                .andExpect(jsonPath("address").value(CLINIC_ADDRESS))
                .andReturn();
        String jsonResult = result.getResponse().getContentAsString();
        String id =  objectMapper.reader().readTree(jsonResult).get("id").asText();
        clinic.setId(id);
        assertEquals(clinic, clinicRepository.findOne(id));
    }

    private Clinic getClinic(String name, String address) {
        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setAddress(address);
        return clinic;
    }
}
