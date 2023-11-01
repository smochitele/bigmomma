package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private static final Logger LOG = LoggerFactory.getLogger(VendorService.class);
    @Autowired
    private VendorRepository repository;


}
