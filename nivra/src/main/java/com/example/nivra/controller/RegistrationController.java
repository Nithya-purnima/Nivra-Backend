package com.example.nivra.controller;

import com.example.nivra.entity.*;
import com.example.nivra.repository.*;
import com.example.nivra.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired private ConsumerRepository consumerRepo;
    @Autowired private NGORepository ngoRepo;
    @Autowired private SellerRepository sellerRepo;
    @Autowired private FileStorageService fileService;

    @PostMapping("/consumer")
    public String registerConsumer(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam MultipartFile certificate
    ) throws Exception {
        Consumer c = new Consumer();
        c.setName(name);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(address);
        c.setPassword(password);
        c.setCertificatePath(fileService.saveFile(certificate));
        consumerRepo.save(c);
        return "Consumer registered";
    }
//    @PostMapping("/register/ngo")
//    public ResponseEntity<String> registerNGO(
//            @RequestParam String orgName,
//            @RequestParam String email,
//            @RequestParam String phone,
//            @RequestParam String address,
//            @RequestParam String password,
//            @RequestParam MultipartFile certificate
//    ) {
//        try {
//            NGO ngo = new NGO();
//            ngo.setOrgName(orgName);
//            ngo.setEmail(email);
//            ngo.setPhone(phone);
//            ngo.setAddress(address);
//            ngo.setPassword(password);
//            ngo.setCertificatePath(fileService.saveFile(certificate));
//
//            ngoRepo.save(ngo);
//
//            return ResponseEntity.ok("NGO registered successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
//        }
//    }
//
@PostMapping("/ngo")
public ResponseEntity<String> registerNGO(
        @RequestParam String orgName,
        @RequestParam String email,
        @RequestParam String phone,
        @RequestParam String address,
        @RequestParam String password,
        @RequestParam MultipartFile certificate
) {
    try {
        NGO existingNGO = ngoRepo.findByEmail(email);
        if (existingNGO != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        NGO ngo = new NGO();
        ngo.setOrgName(orgName);
        ngo.setEmail(email);
        ngo.setPhone(phone);
        ngo.setAddress(address);
        ngo.setPassword(password);
        ngo.setCertificatePath(fileService.saveFile(certificate));

        ngoRepo.save(ngo);
        return ResponseEntity.ok("NGO registered successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
    }
}
    @PostMapping("/seller")
    public String registerSeller(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password
    ) {
        Seller s = new Seller();
        s.setName(name);
        s.setEmail(email);
        s.setPhone(phone);
        s.setPassword(password);
        sellerRepo.save(s);
        return "Seller registered";
    }
}

