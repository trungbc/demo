package com.example.demo;

import com.example.demo.dto.GpxModel;
import com.example.demo.dto.ListResponse;
import com.example.demo.dto.Trkpt;
import com.example.demo.entity.Gpx;
import com.example.demo.entity.TrkptEntity;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.GpxRepository;
import com.example.demo.repository.TrkptRepository;
import com.example.demo.service.GpxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpxServiceTest {

    @Autowired
    Mapper mapper;

    @MockBean
    private GpxRepository gpxRepository;

    @MockBean
    TrkptRepository trkptRepository;

    @Autowired
    GpxService gpxService;

    PodamFactory podamFactory = new PodamFactoryImpl();

    @Test
    public void testCreate() throws JsonProcessingException {
        GpxModel gpx = podamFactory.manufacturePojoWithFullData(GpxModel.class);
        gpx.getTrk().getTrkseg().getTrkpt().forEach(e-> {
            e.setLon("10.0");
            e.setLat("10.0");
            e.setEle("10.0");
            e.setTime("2017-10-22T09:41:33Z");
        });
        Gpx entity = mapper.convertToEntity(gpx);
        entity.setId(1l);
        Mockito.when(gpxRepository.save(Mockito.any(Gpx.class)))
                .thenReturn(entity);

        GpxModel result = gpxService.create(gpx);
        Assert.assertEquals(entity.getId(), result.getId());
    }

    @Test
    public void testGetLatestTracks() throws JsonProcessingException {
        List<TrkptEntity> entities = new ArrayList<>();
        TrkptEntity entity1 = podamFactory.manufacturePojoWithFullData(TrkptEntity.class);
        TrkptEntity entity2 = podamFactory.manufacturePojoWithFullData(TrkptEntity.class);
        entities.add(entity1);
        entities.add(entity2);

        Mockito.when(trkptRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(entities);

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));

        ListResponse  result = gpxService.getLatestTracks(pageable);
        Assert.assertEquals(entities.size(), result.getData().size());
    }

    @Test
    public void testGetGpxById() throws NotFoundException, JsonProcessingException {
        Gpx gpx = podamFactory.manufacturePojoWithFullData(Gpx.class);
        gpx.setMetadata("{}");
        gpx.setWpt("[]");
        Mockito.when(gpxRepository.findById(1l))
                .thenReturn(Optional.of(gpx));

        GpxModel result = gpxService.getGpxById(1l);
        Assert.assertEquals(gpx.getId(), result.getId());
    }
}
