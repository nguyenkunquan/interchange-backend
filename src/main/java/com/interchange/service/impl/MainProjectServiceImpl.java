package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.*;
import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import com.interchange.entities.DTO.MainProjectDTO.ProductDetailDTO;
import com.interchange.entities.DTO.MainProjectDTO.RoomDTO;
import com.interchange.repository.*;
import com.interchange.service.MainProjectService;
import com.interchange.service.QuotationService;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuotationRepository quotationRepository;
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
    @Autowired
    private SupplierProductRepository supplierProductRepository;

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
        if(status==-1)
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

    @Override
    public ResponseEntity<?> getPreQuotation(int mainProjectId, int preQuotationId) {
        return getResponseEntity(quotationRepository.findById(preQuotationId).get());
    }

    @Override
    public ResponseEntity<?> createMainProject(MainProjectDTO mainProjectDTO) {
        MainProject mainProject = new MainProject();
        mainProject.setCustomer(userRepository.findById(mainProjectDTO.getCustomerId()).get());
        mainProject.setStaff(userRepository.findById(mainProjectDTO.getStaffId()).get());
        mainProject.setCreateTime(new Date());
        mainProject.setStatus(1);
        MainProject newMainProject = mainProjectRepository.save(mainProject);

        Quotation firstQuotation = new Quotation();
        firstQuotation.setRequestTime(new Date());
        firstQuotation.setStatus(1);
        firstQuotation.setContentRequestQuotation(mainProjectDTO.getQuotations().get(0).getContentRequestQuotation());
        firstQuotation.setMainProject(newMainProject);
        return getResponseEntity(quotationRepository.save(firstQuotation));
    }

    @Override
    public ResponseEntity<?> getMainProjectListByCusId(String cusId) {
        return getResponseEntity(mainProjectRepository.getMainProjectListByCusId(cusId));
    }

    @Override
    public ResponseEntity<?> hasRequestIsWaiting(int mainProjectId) {
        MainProject mainProject = mainProjectRepository.findById(mainProjectId).get();
        for (Quotation quotation : mainProject.getQuotations()) {
            if (quotation.getStatus() == 1 || quotation.getStatus() == 2 || quotation.getStatus() == 3) {
                return getResponseEntity(true);
            }
        }
        return getResponseEntity(false);
    }

    @Override
    public ResponseEntity<?> getFinalQuotation(int mainProjectId) {
        MainProject mainProject = mainProjectRepository.findById(mainProjectId).get();
        for (Quotation quotation : mainProject.getQuotations()) {
            if (quotation.getStatus() >= 5 || quotation.getStatus() == 0) {
                return getResponseEntity(quotation.getQuotationId());
            }
        }
        return getResponseEntity(null);
    }

    @Override
    public ResponseEntity<?> saveMainProjectWithFirstQuotationHasInformation(MainProjectDTO mainProjectDTO) {
        MainProject mainProject = new MainProject();
        mainProject.setCustomer(userRepository.findById(mainProjectDTO.getCustomerId()).get());
        mainProject.setStaff(userRepository.findById(mainProjectDTO.getStaffId()).get());
        mainProject.setCreateTime(new Date());
        mainProject.setStatus(1);
        MainProject newMainProject = mainProjectRepository.save(mainProject);

        Quotation firstQuotation = new Quotation();
        firstQuotation.setRequestTime(new Date());
        firstQuotation.setStatus(1);
        firstQuotation.setPreQuotationId(0);
        firstQuotation.setContentRequestQuotation(mainProjectDTO.getQuotations().get(0).getContentRequestQuotation());
        firstQuotation.setMainProject(newMainProject);
        Quotation newQuotation = quotationRepository.save(firstQuotation);

        Project firstProject = new Project();
        String projName = mainProjectDTO.getQuotations().get(0).getProject().getProjName();
        int projectCategoryId = mainProjectDTO.getQuotations().get(0).getProject().getProjectCategoryId();
        firstProject.setProjName(projName);
        firstProject.setCategoryProject(categoryProjectRepository.findById(projectCategoryId).get());
        firstProject.setQuotation(newQuotation);
        Project newProject = projectRepository.save(firstProject);
        int supId = mainProjectDTO.getQuotations().get(0).getProject().getSupplierId();
        for (RoomDTO roomDTO : mainProjectDTO.getQuotations().get(0).getProject().getRooms()) {
            Room room = saveRoom(newProject, roomDTO);
            for (ProductDetailDTO productDetailDTO: roomDTO.getProductDetailList()) {
                ProductDetail productDetail = saveProductDetail(supId, productDetailDTO);
                saveRoomProduct(room, productDetail, productDetailDTO.getQuantity(), productDetailDTO.getTotalPrice());
            }
        }
        return getResponseEntity("Save new quotation successfully");
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

}
