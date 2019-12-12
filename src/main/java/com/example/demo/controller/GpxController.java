package com.example.demo.controller;

import com.example.demo.configuration.Response;
import com.example.demo.configuration.XmlConfiguration;
import com.example.demo.dto.GpxModel;
import com.example.demo.dto.ListResponse;
import com.example.demo.dto.Trkpt;
import com.example.demo.service.GpxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.NotSupportedException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping(value = "/gpx")
public class GpxController {

    @Autowired
    GpxService gpxService;

    @Autowired
    XmlConfiguration xmlConfiguration;

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) throws IOException, NotSupportedException {
        File gpxFile = xmlConfiguration.convertFromMultiPartToFile(file);
        GpxModel data = xmlConfiguration.readXml(gpxFile.getAbsolutePath(), GpxModel.class);
        GpxModel gpx = gpxService.create(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(gpx.getId()).toUri();

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED);
        response.setReturnMessage(HttpStatus.CREATED.name());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public GpxModel getGpxById(@PathVariable Long id) throws NotFoundException, JsonProcessingException {
        return gpxService.getGpxById(id);
    }

    @GetMapping("/track")
    public ListResponse getLatestTracks(@PageableDefault(sort = "time", direction = Sort.Direction.DESC) Pageable pageable) {
        return gpxService.getLatestTracks(pageable);
    }

}
