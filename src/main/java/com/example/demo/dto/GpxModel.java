package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "gpx")
public class GpxModel {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String xmlns;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String creator;

    @XmlElement
    private Metadata metadata;

    private List<Wpt> wpt;

    @XmlElement
    private Trk trk;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Wpt> getWpt() {
        return wpt;
    }

    public void setWpt(List<Wpt> wpt) {
        this.wpt = wpt;
    }

    public Trk getTrk() {
        return trk;
    }

    public void setTrk(Trk trk) {
        this.trk = trk;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
