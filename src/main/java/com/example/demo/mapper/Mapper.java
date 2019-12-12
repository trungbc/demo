package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.Gpx;
import com.example.demo.entity.TrkptEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Mapper {

    @Autowired
    private ObjectMapper mapper;

    public Gpx convertToEntity(GpxModel model) throws JsonProcessingException {
        Gpx gpx = new Gpx();
        gpx.setMetadata(mapper.writeValueAsString(model.getMetadata()));
        gpx.setWpt(mapper.writeValueAsString(model.getWpt()));
        model.getTrk().getTrkseg().getTrkpt().forEach(e -> {
            TrkptEntity trkpt = convertToTrkptEntity(e, gpx);
            gpx.getTrkpts().add(trkpt);
        });

        return gpx;
    }

    public TrkptEntity convertToTrkptEntity(Trkpt e, Gpx gpx) {
        TrkptEntity trkpt = new TrkptEntity();
        trkpt.setLat(Double.parseDouble(e.getLat()));
        trkpt.setLon(Double.parseDouble(e.getLon()));
        trkpt.setEle(Double.parseDouble(e.getEle()));
        trkpt.setTime(ZonedDateTime.parse(e.getTime()));
        trkpt.setGpx(gpx);

        return trkpt;
    }

    public GpxModel convertToModel(Gpx entity) throws JsonProcessingException {
        GpxModel model = new GpxModel();
        model.setMetadata(mapper.readValue(entity.getMetadata(), Metadata.class));
        model.setWpt(mapper.readValue(entity.getWpt(), List.class));
        Trk trk = new Trk();
        Trkseg trkseg = new Trkseg();
        entity.getTrkpts().forEach(e -> {
            Trkpt trkptModel = convertToTrkptModel(e);
            trkseg.getTrkpt().add(trkptModel);
        });
        trk.setTrkseg(trkseg);
        model.setTrk(trk);
        model.setId(entity.getId());

        return model;
    }

    public Trkpt convertToTrkptModel(TrkptEntity e) {
        Trkpt trkptModel = new Trkpt();
        trkptModel.setLat(e.getLat().toString());
        trkptModel.setLon(e.getLon().toString());
        trkptModel.setEle(e.getEle().toString());
        trkptModel.setTime(e.getTime().format(DateTimeFormatter.ISO_DATE_TIME));
        trkptModel.setId(e.getId());
        return trkptModel;
    }
}
