package com.site.reon.aggregate.workshop.query.service;

import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import com.site.reon.aggregate.workshop.command.domain.Workshop;
import com.site.reon.aggregate.workshop.command.domain.WorkshopRepository;
import com.site.reon.aggregate.workshop.query.dto.WorkshopResponse;
import com.site.reon.aggregate.workshop.query.dto.WorkshopSearchRequestParam;
import com.site.reon.aggregate.workshop.query.dto.WorkshopsResponse;
import com.site.reon.global.security.exception.NotFoundWorkshopException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkshopFindServiceImpl implements WorkshopFindService {

    private final WorkshopRepository workshopRepository;
    private final RoastingRecordFindService roastingRecordFindService;

    @Override
    public WorkshopResponse findWorkshop(final Long id) {
        final Optional<Workshop> workshopOpt = workshopRepository.findById(id);
        if (workshopOpt.isEmpty()) {
            throw new NotFoundWorkshopException();
        }

        final Workshop workshop = workshopOpt.get();
        final RoastingRecordResponse roastingRecord = roastingRecordFindService.findRoastingRecordBy(workshop.getRecordId());
        return WorkshopResponse.of(workshop, roastingRecord);
    }

    @Override
    public boolean isSubscribed(final Long recordId, final Long memberId) {
        return roastingRecordFindService.isSubscribed(recordId, memberId);
    }

    @Override
    public Page<WorkshopsResponse> findAllByFilter(final WorkshopSearchRequestParam param) {
        return workshopRepository.findAllByFilter(param);
    }
}
