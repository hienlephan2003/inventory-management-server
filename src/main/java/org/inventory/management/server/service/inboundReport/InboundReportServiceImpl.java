package org.inventory.management.server.service.inboundReport;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.model.inboundReport.InboundReportModelRes;
import org.inventory.management.server.model.inboundReport.UpsertInboundReportModel;
import org.inventory.management.server.repository.InboundReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class InboundReportServiceImpl implements InboundReportService {
    private final InboundReportRepository InboundReportRepository;
    private final ModelMapper modelMapper;
    @Override
    public InboundReportModelRes getInboundReportById(long id) {
        InboundReport InboundReport = InboundReportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found InboundReport with id"+ id));
       return modelMapper.map(InboundReport, InboundReportModelRes.class);
    }

    @Override
    public InboundReportModelRes upsertInboundReport(UpsertInboundReportModel inboundReportModel) {
        InboundReport inboundReport = modelMapper.map(inboundReportModel, InboundReport.class);
        inboundReport.setId(inboundReportModel.getId());
        InboundReportRepository.save(inboundReport);
        return modelMapper.map(inboundReport, InboundReportModelRes.class);
    }

    @Override
    public InboundReportModelRes deleteInboundReport(long id) {
        return null;
    }
}
