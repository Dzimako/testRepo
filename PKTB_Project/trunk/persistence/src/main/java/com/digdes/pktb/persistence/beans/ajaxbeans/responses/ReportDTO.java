package com.digdes.pktb.persistence.beans.ajaxbeans.responses;

import com.digdes.pktb.persistence.model.ReportLevel;
import com.digdes.pktb.persistence.model.UserGroup;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Panfilov.V
 * Date: 26.09.12
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class ReportDTO {

    private Long id;
    private String name;
    private Boolean folder;
    private Boolean available;
    private String treePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFolder() {
        return folder;
    }

    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ReportDTO reportDTO = (ReportDTO) o;

        return !(id != null ? !id.equals(reportDTO.id) : reportDTO.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ReportDTO");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", folder=").append(folder);
        sb.append(", available=").append(available);
        sb.append('}');
        return sb.toString();
    }
}
