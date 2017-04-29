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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testGetClinic() throws Exception {
        Clinic clinic = clinicRepository.save(getClinic("Clinic Name", "Clinic Address"));
        RestDocumentationResultHandler document = MockMvcRestDocumentation.document("clinic-list", responseFields(
                fieldWithPath("id").description("The clinic ID"),
                fieldWithPath("name").description("The clinic name"),
                fieldWithPath("address").description("The clinic address"),
                fieldWithPath("offers").description("The clinic offers"))
        );
        mockMvc.perform(get("/clinics/" + clinic.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document).andExpect(content()
                .json(objectMapper.writer().writeValueAsString(clinic)));
    }

    private Clinic getClinic(String name, String address) {
        Clinic clinic = new Clinic();
        clinic.setName(name);
        clinic.setAddress(address);
        return clinic;
    }
}
