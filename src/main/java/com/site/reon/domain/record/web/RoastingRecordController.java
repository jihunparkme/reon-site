package com.site.reon.domain.record.web;

import com.site.reon.domain.record.dto.RoastingRecordRequest;
import com.site.reon.domain.record.entity.RoastingRecord;
import com.site.reon.domain.record.service.RoastingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.site.reon.global.common.constant.Result.FAIL;
import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RoastingRecordController {

    private final RoastingRecordService recordService;

    @GetMapping
    public String list(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size,
            Model model) {

        // TODO: 로그인 정보 확인해서 관리자면 전체 조회, 일반 회원이면 회원 정보만 조회

        // TODO: 회원번호, 날짜로 검색

        Page<RoastingRecord> roastingRecordListPage = recordService.findAllSortByIdDescPaging(page, size);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", page);

        return "record/record-list";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody RoastingRecordRequest request){
        try {
            recordService.upload(request);
            return ResponseEntity.ok(SUCCESS);
        } catch (Exception e) {
            log.error("RoastingRecordController.uploadFile Exception: ", e);
            return new ResponseEntity<>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * TODO: View 로 보여줄 때는 json 을 잘 풀어서 보여줄 수 있도록..
     * chart.js로 차트 그려주기.
     * https://www.chartjs.org/
     *
     * tabulator 테이블 그리드
     * https://tabulator.info/
     * https://blog.naver.com/sacroo/222285981374
     */
}
