package com.example.nivra.controller;


import com.example.nivra.entity.*;
import com.example.nivra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired private ConsumerRepository consumerRepo;
    @Autowired private NGORepository ngoRepo;
    @Autowired private SellerRepository sellerRepo;

    @PostMapping("/buyer")
    public Map<String, String> loginBuyer(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        // check consumer first
        Consumer c = consumerRepo.findByEmail(email);
        if (c != null && c.getPassword().equals(password)) {
            Map<String,String> res = new HashMap<>();
            res.put("token", "dummy-token"); // can replace with JWT later
            res.put("userType", "consumer");
            res.put("userId", c.getId().toString()); // ✅ add this
            return res;
        }

        // check NGO
        NGO ngo = ngoRepo.findByEmail(email);
        if (ngo != null && ngo.getPassword().equals(password)) {
            Map<String,String> res = new HashMap<>();
            res.put("token", "dummy-token");
            res.put("userType", "ngo");
            res.put("userId", ngo.getId().toString()); // optional if needed
            return res;
        }

        throw new RuntimeException("Invalid credentials");
    }


    @PostMapping("/seller")
    public Map<String,String> loginSeller(@RequestBody Map<String,String> req) {
        String email = req.get("email");
        String password = req.get("password");

        Seller s = sellerRepo.findByEmail(email);
        if(s!=null && s.getPassword().equals(password)){
            Map<String,String> res = new HashMap<>();
            res.put("token","dummy-token");
            res.put("userType","seller");
            res.put("userId", s.getId().toString()); // Add this line
            return res;
        }
        throw new RuntimeException("Invalid credentials");
    }
}

