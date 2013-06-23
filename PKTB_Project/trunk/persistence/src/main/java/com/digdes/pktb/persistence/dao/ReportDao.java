package com.digdes.pktb.persistence.dao;

import com.digdes.pktb.persistence.model.Report;
import com.digdes.pktb.persistence.model.User;

import java.util.List;

/**
 * User: Kozlov.D
 * Date: 12.09.12
 * Time: 14:31
 */
public interface ReportDao {
    public Report getById(Long id);

    public Report load(Long id);

    public List<Report> getByParentReportId(Long id);

    public Long saveOrUpdate(Report report);

    public void deleteById(Long id);

    public List<Object> debug(String query);

    public List<Report> getReportByName(String name);
}
