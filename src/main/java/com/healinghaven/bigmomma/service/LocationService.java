package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.Location;
import com.healinghaven.bigmomma.enums.LocationSearchCriteria;
import com.healinghaven.bigmomma.repository.LocationRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    LocationRepository repository;

    public Location getEntityLocation(int entityId) {
        try {
            LOG.info("Attempting to get location belonging to entity[" + entityId + "]");
            return repository.getEntityLocation(entityId);
        } catch (Exception e) {
            LOG.error("Failed to get entity location", e);
            return null;
        }
    }

    public Location setEntityLocation(Location location, int entityId) {
        try {
            LOG.info("Attempting to set location[" + location + "] to entity[" + entityId + "]");
            return repository.setEntityLocation(location, entityId);
        } catch (Exception e) {
            LOG.error("Failed to set location[" + location + "]  to entity[" + entityId + "]", e);
            return null;
        }
    }

    public Location updateEntityLocation(Location location, int entityId) {
        try {
            LOG.info("Attempting to set location[" + location + "] to entity [" + entityId + "]");
            return repository.updateEntityLocation(location, entityId);
        } catch (Exception e) {
            LOG.error("Failed to update location[" + location + "] to entity[" + entityId + "]", e);
            return null;
        }
    }

    public List<Location> getLocationsByCriteria(LocationSearchCriteria criteria, String value) {
        if (criteria != null && StringUtils.isNotBlank(value)) {
            try {
                LOG.info("Attempting to get locations by criteria[" + criteria + "] with value[" + value + "]");
                return repository.getLocationsByCriteria(criteria, value);
            } catch (Exception e) {
                LOG.error("Failed to get locations by criteria[" + criteria + "]");
                return null;
            }
        } else {
            LOG.warn("Null value in method[public List<Location> getLocationsByCriteria(LocationSearchCriteria criteria[" + criteria  +"], String value[" + value + "] ) ]");
            return null;
        }
    }
}
