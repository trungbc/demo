package com.example.demo.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GpxServiceImpl implements GpxService {

    @Autowired
    Mapper mapper;

    @Autowired
    private GpxRepository gpxRepository;

    @Autowired
    TrkptRepository trkptRepository;

    @Override
    public GpxModel create(GpxModel gpx) throws JsonProcessingException {
        Gpx entity = gpxRepository.save(mapper.convertToEntity(gpx));
        return mapper.convertToModel(entity);

    }

    @Override
    public ListResponse getLatestTracks(Pageable pageable) {
        List<TrkptEntity> trkpts = trkptRepository.findAll(pageable);
        Page page = new PageImpl(trkpts, pageable, trkptRepository.count());
        List<Trkpt> result = new ArrayList<>();
        trkpts.forEach(e -> {
            result.add(mapper.convertToTrkptModel(e));
        });
        ListResponse response = new ListResponse();
        response.setData(result);
        response.setTotalPage(page.getTotalPages());

        return response;
    }

    @Override
    public GpxModel getGpxById(Long id) throws NotFoundException, JsonProcessingException {
        Optional<Gpx> gpxOptional = gpxRepository.findById(id);
         Gpx gpx = gpxOptional.orElseThrow(() ->new NotFoundException(String.format("CANNOT FIND GPX WITH ID %s", id)));

        return mapper.convertToModel(gpx);
    }


}
