package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.Blog;
import com.interchange.entities.MainProject;
import com.interchange.entities.Quotation;
import com.interchange.repository.MainProjectRepository;
import com.interchange.service.MainProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class MainProjectServiceImpl extends BaseResponse implements MainProjectService {

    @Autowired
    private MainProjectRepository mainProjectRepository;

    //    @Override
//    public ResponseEntity<?> getMainProjectList(int status, LocalDate requestTime) {
//        HashSet<String> strings;
//        HashSet<String> strings2 = mainProjectRepository.getMainProjectWasApproved();
//        List<Integer> mainProjectId = new ArrayList<>();
//        if(status==0) {
//            strings = mainProjectRepository.getMainProjectWasApproved();
//            for (String s : strings) {
//                int value = Integer.parseInt(s);
//                if (!mainProjectId.contains(value)) {
//                    mainProjectId.add(value);
//                }
//            }
//        }
//        if(status==2) {
//            List<Integer> mainProjectIdAllInt = new ArrayList<>();
//            HashSet<String> mainProjectIdAllString = mainProjectRepository.getMainProjectWasApproved();
//            for (String s : mainProjectIdAllString) {
//                int value = Integer.parseInt(s);
//                if (!mainProjectIdAllInt.contains(value)) {
//                    mainProjectIdAllInt.add(value);
//                }
//            }
//            List<Integer> mainProjectIdAllStatus3Int = new ArrayList<>();
//            HashSet<String> mainProjectIdAllStatus3String = mainProjectRepository.getMainProjectWasApprovedByStatusIs3();
//            for (String s : mainProjectIdAllStatus3String) {
//                int value = Integer.parseInt(s);
//                if (!mainProjectIdAllStatus3Int.contains(value)) {
//                    mainProjectIdAllStatus3Int.add(value);
//                }
//            }
//            // Chuyển mảng B thành một List để dễ kiểm tra
//            List<Integer> listB = new ArrayList<>();
//            for (int value : mainProjectIdAllStatus3Int) {
//                listB.add(value);
//            }
//            // Tạo mảng kết quả
//            List<Integer> result = new ArrayList<>();
//            // Duyệt qua từng phần tử trong mảng A
//            for (int value : mainProjectIdAllInt) {
//                // Kiểm tra xem phần tử có tồn tại trong mảng B không
//                if (!listB.contains(value)) {
//                    result.add(value);
//                }
//            }
//            mainProjectId = result;
//        }
//
//        if(status==3) {
//            strings = mainProjectRepository.getMainProjectWasApprovedByStatusIs3();
//            for (String s : strings) {
//                int value = Integer.parseInt(s);
//                if (!mainProjectId.contains(value)) {
//                    mainProjectId.add(value);
//                }
//            }
//        }
//
//        List<MainProject> mainProjects = new ArrayList<>();
//        for (int id : mainProjectId) {
//            MainProject mainProject = mainProjectRepository.findById(id).get();
//            Quotation earliestQuotation = getQuotationWithEarliestRequestTime(mainProject);
//            java.util.Date date = new java.util.Date(earliestQuotation.getRequestTime().getTime());
//            Instant instant = date.toInstant();
//            LocalDate earliestQuotationDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//            if (earliestQuotationDate.equals(requestTime)) {
//                mainProjects.add(mainProjectRepository.findById(id).get());
//            }
//        }
//        return getResponseEntity(mainProjects);
//    }
//    public Quotation getQuotationWithEarliestRequestTime(MainProject mainProject) {
//        return mainProject.getQuotations().stream()
//                .min(Comparator.comparing(Quotation::getRequestTime))
//                .orElse(null);
//    }
    @Override
    public ResponseEntity<?> getMainProjectList(int status, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "main_project_id"));
        Page<Map<String, Object>> mainProjectPage;
        if(status==0)
            mainProjectPage = mainProjectRepository.findAllByPage(pageable);
        else
            mainProjectPage = mainProjectRepository.findByStatus(status, pageable);
        return getResponseEntity(mainProjectPage);
    }

    @Override
    public ResponseEntity<?> findMainProjectById(int id) {
        return getResponseEntity(mainProjectRepository.findById(id).get());
    }

    @Override
    public ResponseEntity<?> getLastQuotationOfMainProject(int mainProjectId) {
        MainProject mainProject = mainProjectRepository.findById(mainProjectId).get();
        List<Quotation> quotationList = mainProject.getQuotations();
        return getResponseEntity(quotationList.get(quotationList.size() - 2));
    }

}
