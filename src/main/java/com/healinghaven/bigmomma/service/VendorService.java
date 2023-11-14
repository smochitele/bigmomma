package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.Vendor;
import com.healinghaven.bigmomma.enums.LocationSearchCriteria;
import com.healinghaven.bigmomma.repository.VendorRepository;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    private static final Logger LOG = LoggerFactory.getLogger(VendorService.class);
    @Autowired
    private VendorRepository repository;

    public Vendor addVendor(Vendor vendor) {
        if (vendor != null) {
            try {
                LOG.info("Attempting to add vendor[" + vendor + "]");
                return repository.addVendor(vendor);
            } catch (Exception e) {
                LOG.error("Failed to add vendor[" + vendor + "]", e);
                return null;
            }
        } else {
            LOG.error("Null vendor passed");
            return null;
        }
    }

    public Vendor getOwnersVendor(String ownerEmail) {
        if (StringUtils.isNotBlank(ownerEmail)) {
            try {
                LOG.info("Attempting to get vendor belonging to[" + ownerEmail + "]");
                return repository.getOwnersVendor(ownerEmail);
            } catch (Exception e) {
                LOG.error("Failed to get vendor belonging to[" + ownerEmail + "]", e);
                return null;
            }
        } else {
            LOG.error("Cannot get vendor's owner - Null owner email passed");
            return null;
        }
    }

    public List<Vendor> getVendors() {
        try {
            LOG.info("Attempting to get a list of vendors");
            return repository.getVendors();
        } catch (Exception e) {
            LOG.error("Failed to get a list of all vendors", e);
            return null;
        }
    }

    public Vendor getVendorById(String vendorId) {
        if (StringUtils.isNotBlank(vendorId)) {
            try {
                LOG.info("Attempting to get vendor with id[" + vendorId + "]");
                return repository.getVendorById(vendorId, true);
            } catch (Exception e) {
                LOG.error("Failed to get vendor with id[" + vendorId + "]", e);
                return null;
            }
        } else {
            LOG.error("Cannot get vendor by id - Null vendor ID passed");
            return null;
        }
    }

    public Vendor updateVendor(Vendor vendor) {
        if (vendor != null) {
            try {
                LOG.info("Attempting to update vendor[" + vendor + "]");
                return repository.updateVendor(vendor);
            } catch (Exception e) {
                LOG.error("Failed to update vendor[" + vendor + "]", e);
                return null;
            }
        } else {
            LOG.error("Cannot update vendor - Null vendor passed");
            return null;
        }
    }
    public List<Vendor> getVendorByLocationCriteria(LocationSearchCriteria criteria, String value) {
        if (criteria != null && StringUtils.isNotBlank(value)) {
            try {
                LOG.info("Attempting to get vendors with criteria[" + criteria + "] and value[" + value + "]");
                return repository.getVendorByLocationCriteria(criteria, value);
            } catch (Exception e) {
                LOG.error("Cannot get vendor by location criteria[" + criteria + "] with value[" + value + "]", e);
                return null;
            }
        } else {
            LOG.error("Cannot get vendor by location criteria[" + criteria + "] with value[" + value + "]");
            return null;
        }
    }
}
