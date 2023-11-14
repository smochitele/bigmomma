package com.healinghaven.bigmomma.controller;

import com.healinghaven.bigmomma.entity.Vendor;
import com.healinghaven.bigmomma.enums.LocationSearchCriteria;
import com.healinghaven.bigmomma.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendorController {
    @Autowired
    private VendorService service;

    @GetMapping("/api/getvendors")
    public List<Vendor> getVendors() {
        return service.getVendors();
    }

    @GetMapping("/api/getownersvendor/{ownerEmail}")
    public Vendor getOwnersVendor(@PathVariable String ownerEmail) {
        return service.getOwnersVendor(ownerEmail);
    }

    @GetMapping("/api/getvendorbyid/{vendorId}")
    public Vendor getVendorById(@PathVariable String vendorId) {
        return service.getVendorById(vendorId);
    }

    @GetMapping("/api/getvendorbylocationcriteria/{criteria}/{value}")
    public List<Vendor> getVendorByLocationCriteria(@PathVariable int criteria, @PathVariable String value) {
        return service.getVendorByLocationCriteria(LocationSearchCriteria.geLocationSearchCriteria(criteria), value);
    }

    @PostMapping("/api/addvendor")
    public Vendor addVendor(@RequestBody Vendor vendor) {
        return service.addVendor(vendor);
    }

    @PutMapping("/api/updatevendor")
    public Vendor updateVendor(@RequestBody Vendor vendor) {
        return service.updateVendor(vendor);
    }
}
