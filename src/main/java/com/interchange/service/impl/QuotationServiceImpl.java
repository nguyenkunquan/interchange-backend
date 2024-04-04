package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.*;
import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import com.interchange.entities.DTO.MainProjectDTO.ProductDetailDTO;
import com.interchange.entities.DTO.MainProjectDTO.ProjectDTO;
import com.interchange.entities.DTO.MainProjectDTO.RoomDTO;
import com.interchange.repository.*;
import com.interchange.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class QuotationServiceImpl extends BaseResponse implements QuotationService {

    @Autowired
    MainProjectRepository mainProjectRepository;
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private SupplierProductRepository supplierProductRepository;
    @Autowired
    private CategoryProjectRepository categoryProjectRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryRoomRepository categoryRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private RoomProductRepository roomProductRepository;

    @Override
    public ResponseEntity<?> findAllPendingQuotationByTime(LocalDate requestTime) {
        return getResponseEntity(quotationRepository.findAllPendingQuotationByTime(requestTime));
    }

    @Override
    public ResponseEntity<?> findQuotationById(int quotationId) {
        return getResponseEntity(quotationRepository.findQuotationById(quotationId));
    }

    @Override
    public ResponseEntity<?> findQuotationEagerById(int quotationId) {
        return getResponseEntity(quotationRepository.findById(quotationId));
    }

    @Override
    public ResponseEntity<?> findQuotationListByStatus(int status, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "quotation_id"));
        Page<Map<String, Object>> quotationPage;
        quotationPage = quotationRepository.findQuotationListByStatus(status, pageable);
        return getResponseEntity(quotationPage);
    }

    @Override
    public ResponseEntity<?> updateQuotationStatus(int quotationId, int newStatus, String contentResponse) {
        Quotation quotation = quotationRepository.findById(quotationId).get();
        quotation.setStatus(newStatus);
        if(!(contentResponse == "")) quotation.setContentResponse(contentResponse);
        quotationRepository.save(quotation);
        MainProject mainProject = mainProjectRepository.findById(quotation.getMainProject().getMainProjectId()).get();
        mainProject.setStatus(newStatus);
        mainProjectRepository.save(mainProject);
        return getResponseEntity("Update quotation status successfully!");
    }

    @Override
    public ResponseEntity<?> createRequestQuotation(MainProjectDTO mainProjectDTO) {
        Quotation newRequestQuotation = new Quotation();
        newRequestQuotation.setRequestTime(new Date());
        newRequestQuotation.setStatus(1);
        newRequestQuotation.setContentRequestQuotation(mainProjectDTO.getQuotations().get(0).getContentRequestQuotation());
        newRequestQuotation.setPreQuotationId(mainProjectDTO.getQuotations().get(0).getPreQuotationId());
        newRequestQuotation.setMainProject(mainProjectRepository.findById(mainProjectDTO.getMainProjectId()).get());
        return getResponseEntity(quotationRepository.save(newRequestQuotation));
    }

    //Giai đoạn 3 - Kiểm duyệt báo giá
    @Override
    public ResponseEntity<?> saveQuotation(MainProjectDTO mainProjectDTO) {
        Quotation quotation = quotationRepository.findById(mainProjectDTO.getQuotations().get(0).getQuotationId()).get();
        quotation.setStatus(mainProjectDTO.getQuotations().get(0).getStatus());
        quotationRepository.save(quotation);
        MainProject mainProject = mainProjectRepository.findById(mainProjectDTO.getMainProjectId()).get();
        mainProject.setStatus(mainProjectDTO.getStatus());
        mainProjectRepository.save(mainProject);
        Project project = saveProject(mainProjectDTO);
        int supId = mainProjectDTO.getQuotations().get(0).getProject().getSupplierId();
        for (RoomDTO roomDTO : mainProjectDTO.getQuotations().get(0).getProject().getRooms()) {
            Room room = saveRoom(project, roomDTO);
            for (ProductDetailDTO productDetailDTO: roomDTO.getProductDetailList()) {
                ProductDetail productDetail = saveProductDetail(supId, productDetailDTO);
                saveRoomProduct(room, productDetail, productDetailDTO.getQuantity(), productDetailDTO.getTotalPrice());
            }
        }
        return getResponseEntity("Save new quotation successfully");
    }

    public Project saveProject(MainProjectDTO mainProjectDTO) {
        ProjectDTO projectDTO = mainProjectDTO.getQuotations().get(0).getProject();
        int projId = projectDTO.getProjId();
        String projName = projectDTO.getProjName();
        int projectCategoryId = projectDTO.getProjectCategoryId();
        int quotationId = projectDTO.getQuotationId();

        Project project = new Project();
        if(projId!=0) {
            project.setProjId(projId);
        }
        project.setProjName(projName);
        project.setCategoryProject(categoryProjectRepository.findById(projectCategoryId).get());
        project.setQuotation(quotationRepository.findById(quotationId).get());
        return projectRepository.save(project);
    }

    public Room saveRoom(Project project, RoomDTO roomDTO) {
        Room room = new Room();
        if(roomDTO.getRoomId()!=0) room.setRoomId(roomDTO.getRoomId());
        room.setRoomName(roomDTO.getRoomName());
        room.setCategoryRoom(categoryRoomRepository.findById(roomDTO.getRoomCategoryId()).get());
        room.setProject(project);
        return roomRepository.save(room);
    }

    public ProductDetail saveProductDetail(int supId, ProductDetailDTO productDetailDTO) {
        int quantity = productDetailDTO.getQuantity();
        double totalPrice = productDetailDTO.getTotalPrice();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProLength(productDetailDTO.getProLength());
        productDetail.setProWidth(productDetailDTO.getProWidth());
        productDetail.setProHeight(productDetailDTO.getProHeight());
        productDetail.setProPrice(totalPrice/quantity);
        productDetail.setSupplierProduct(supplierProductRepository.findBySupplier_SupIdAndProduct_ProId(supId, productDetailDTO.getProId()));
        return productDetailRepository.save(productDetail);
    }

    public void saveRoomProduct(Room room, ProductDetail productDetail, int quantity, double totalPrice) {
        RoomProduct roomProduct = new RoomProduct();
        roomProduct.setRoom(room);
        roomProduct.setProductDetail(productDetail);
        roomProduct.setQuantity(quantity);
        roomProduct.setTotalPrice(totalPrice);
        roomProductRepository.save(roomProduct);
    }

    //Giai đoạn 2 - Cap nhat báo giá
    @Override
    public ResponseEntity<?> updateQuotation(MainProjectDTO mainProjectDTO) {
        Quotation quotation = quotationRepository.findById(mainProjectDTO.getQuotations().get(0).getQuotationId()).get();
        int curProjectId = quotation.getProject().getProjId();
        quotation.setStatus(mainProjectDTO.getQuotations().get(0).getStatus());
        quotationRepository.save(quotation);
        MainProject mainProject = mainProjectRepository.findById(mainProjectDTO.getMainProjectId()).get();
        mainProject.setStatus(mainProjectDTO.getStatus());
        mainProjectRepository.save(mainProject);
        Quotation updateQuotation = new Quotation();
        updateQuotation.setQuotationId(quotation.getQuotationId());
        updateQuotation.setRequestTime(quotation.getRequestTime());
        updateQuotation.setStatus(quotation.getStatus());
        updateQuotation.setContentRequestQuotation(quotation.getContentRequestQuotation());
        updateQuotation.setContentResponse(quotation.getContentResponse());
        updateQuotation.setMainProject(quotation.getMainProject());
        quotationRepository.save(updateQuotation);
        projectRepository.deleteById(curProjectId);
        Project project = saveProject(mainProjectDTO);
        int supId = mainProjectDTO.getQuotations().get(0).getProject().getSupplierId();
        for (RoomDTO roomDTO : mainProjectDTO.getQuotations().get(0).getProject().getRooms()) {
            Room room = saveRoom(project, roomDTO);
            for (ProductDetailDTO productDetailDTO: roomDTO.getProductDetailList()) {
                ProductDetail productDetail = saveProductDetail(supId, productDetailDTO);
                saveRoomProduct(room, productDetail, productDetailDTO.getQuantity(), productDetailDTO.getTotalPrice());
            }
        }
        return getResponseEntity("Update quotation successfully");
    }

    @Override
    public ResponseEntity<?> findQuotationByStatusAndProjectCategory(int status, int projectCategoryId) {
        List<Map<String, Object>> quotationList = quotationRepository.findQuotationByStatusAndProjectCategory(status, projectCategoryId);
        List<Map<String, Object>> uniqueQuotationList = getUniqueQuotationList(quotationList);
        return getResponseEntity(uniqueQuotationList);
    }

    public List<Map<String, Object>> getUniqueQuotationList(List<Map<String, Object>> quotationList) {
        List<Map<String, Object>> uniqueQuotationList = new ArrayList<>();
        Set<String> quotationIds = new HashSet<>();

        for (Map<String, Object> quotationMap : quotationList) {
            String quotationId = (String) quotationMap.get("quotationId");
            if (!quotationIds.contains(quotationId)) {
                uniqueQuotationList.add(quotationMap);
                quotationIds.add(quotationId);
            }
        }

        return uniqueQuotationList;
    }
    @Override
    public ResponseEntity<?> countQuotationByStatus() {
        int countDangChoPheDuyet = quotationRepository.countQuotationByStatus(1);
        int countDangXulyYeuCau = quotationRepository.countQuotationByStatus(2) +
                quotationRepository.countQuotationByStatus(3) +
                quotationRepository.countQuotationByStatus(4) +
                quotationRepository.countQuotationByStatus(5);
        int countDaHoanThanh = quotationRepository.countQuotationByStatus(6);
        int countDaHuy = quotationRepository.countQuotationByStatus(0);
        List<Integer> count = new ArrayList<>();
        count.add(countDangChoPheDuyet);
        count.add(countDangXulyYeuCau);
        count.add(countDaHoanThanh);
        count.add(countDaHuy);

        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

}
