package com.example.demo.service;

import com.example.demo.dto.GpxModel;
import com.example.demo.dto.ListResponse;
import com.example.demo.dto.Trkpt;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GpxService {

    GpxModel create(GpxModel gpx) throws JsonProcessingException;

    ListResponse getLatestTracks(Pageable pageable);

    GpxModel getGpxById(Long id) throws NotFoundException, JsonProcessingException;
}
