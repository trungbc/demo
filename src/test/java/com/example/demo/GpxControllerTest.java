package com.example.demo;

import com.example.demo.configuration.XmlConfiguration;
import com.example.demo.dto.GpxModel;
import com.example.demo.dto.ListResponse;
import com.example.demo.dto.Trkpt;
import com.example.demo.service.GpxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GpxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GpxService gpxService;

    @MockBean
    XmlConfiguration xmlConfiguration;

    @Autowired
    ObjectMapper mapper;


    @Test
    public void testGetGpxById() throws Exception {
        final long id = 1;
        GpxModel gpx = new GpxModel();
        gpx.setId(id);
        Mockito.when(gpxService.getGpxById(id))
                .thenReturn(gpx);
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/gpx/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GpxModel result = mapper.readValue(response, GpxModel.class);
        Assert.assertEquals(1l, result.getId().longValue());
    }

    @Test
    public void testUpload() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "sample.gpx", "text/plain", "test data".getBytes());
        GpxModel result = new GpxModel();
        result.setId(1l);
        Mockito.when(xmlConfiguration.convertFromMultiPartToFile(mockMultipartFile))
                .thenReturn(new File("sample.gpx"))
        ;
        Mockito.when(xmlConfiguration.readXml("", GpxModel.class))
                .thenReturn(result)
        ;
        Mockito.when(gpxService.create(null))
                .thenReturn(result)
        ;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/gpx/upload")
                        .file(mockMultipartFile);
        this.mockMvc.perform(builder).andExpect(status().isCreated());
    }

    @Test
    public void testGetLatestTracks() throws Exception {
        PodamFactory podamFactory = new PodamFactoryImpl();
        Trkpt entity1 = podamFactory.manufacturePojoWithFullData(Trkpt.class);
        Trkpt entity2 = podamFactory.manufacturePojoWithFullData(Trkpt.class);
        List<Trkpt> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);
        ListResponse result = new ListResponse();
        result.setData(list);

        Mockito.when(gpxService.getLatestTracks(Mockito.any()))
                .thenReturn(result)
        ;

        String response = mockMvc.perform(MockMvcRequestBuilders.get("/gpx/track"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ListResponse data = mapper.readValue(response, ListResponse.class);
        Assert.assertEquals(2, data.getData().size());
    }

}
