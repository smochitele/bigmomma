package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Location;
import com.healinghaven.bigmomma.enums.LocationSearchCriteria;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationRepository {
    private static final Logger LOG = LoggerFactory.getLogger(LocationRepository.class);

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Location getEntityLocation(int entityId) {
        try {
            Location location = null;
            String SQL = "SELECT * FROM momma_db.locations WHERE entity_id = ?";

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, entityId);
            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                location = getLocationFromResultSet(resultSet);
            }

            return location;
        } catch (Exception e) {
            LOG.error("Failed to get entity location[" + entityId + "]", e);
            return null;
        } finally {
            LOG.info("Closing connection");
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public Location setEntityLocation(Location location, int entityId) {
        if (location != null) {
            try {
                final String SQL = "INSERT INTO momma_db.locations (city, province, suburb, street_number, longitude, latitude, entity_id) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, location.getCity());
                preparedStatement.setString(2, location.getProvince());
                preparedStatement.setString(3, location.getSuburb());
                preparedStatement.setString(4, location.getStreetNumber());
                preparedStatement.setString(5, location.getLongitude());
                preparedStatement.setString(6, location.getLatitude());
                preparedStatement.setInt(7, entityId);

                LOG.info("Executing query[" + SQL + "]");

                preparedStatement.execute();
                LOG.info("Successfully inserted location[" + location + "] to DB");
                return location;
            } catch (Exception e) {
                LOG.error("Failed to set location[" + location + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement);
            }
        } else {
            LOG.warn("Null location passed in the method");
            return null;
        }
    }

    public Location updateEntityLocation(Location location, int entityId) {
        try {
            final String SQL = "UPDATE momma_db.locations SET " +
                               "city = ?, " +
                               "province = ?, " +
                               "suburb = ?, " +
                               "street_number = ?, " +
                               "longitude = ?, " +
                               "latitude = ? " +
                               "WHERE entity_id = ?";

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, location.getCity());
            preparedStatement.setString(2, location.getProvince());
            preparedStatement.setString(3, location.getSuburb());
            preparedStatement.setString(4, location.getStreetNumber());
            preparedStatement.setString(5, location.getLongitude());
            preparedStatement.setString(6, location.getLatitude());
            preparedStatement.setInt(7, entityId);

            LOG.info("Executing query[" + SQL + "]");

            preparedStatement.execute();
            LOG.info("Successfully updated location");
            return location;
        } catch (Exception e) {
            LOG.error("Failed to update entity[" + entityId + "]'s location", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement);
        }
    }

    public List<Location> getLocationsByCriteria(LocationSearchCriteria criteria, String value) {
        if(criteria != null && StringUtils.isNotBlank(value)) {
            ArrayList<Location> locations = new ArrayList<>();
            try {
                String SQL = "SELECT * FROM momma_db.locations WHERE ";
                switch (criteria) {
                    case CITY ->
                        SQL += "city = ?";
                    case PROVINCE ->
                        SQL += "province = ?";
                    case SUBURB ->
                        SQL += "suburb = ?";
                    default ->
                        SQL += "street_number = ?";

                }

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setString(1, value);
                LOG.info("Executing query[" + SQL + "]");
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Location location = getLocationFromResultSet(resultSet);
                    locations.add(location);
                }
                LOG.info("Returning locations with size[" + locations.size() + "]");
                return locations;
            } catch (Exception e) {
                LOG.error("Failed to get location by criteria[" + criteria + "] with value[" + value + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.warn("Null value in method[public List<Location> getLocationsByCriteria(LocationSearchCriteria criteria, String value)]");
            return null;
        }
    }

    private Location getLocationFromResultSet(ResultSet resultSet) {
        try {
            Location location = new Location();

            location.setId(String.valueOf(resultSet.getInt("location_id")));
            location.setCity(resultSet.getString("city"));
            location.setProvince(resultSet.getString("province"));
            location.setSuburb(resultSet.getString("suburb"));
            location.setStreetNumber(resultSet.getString("street_number"));
            location.setLongitude(resultSet.getString("longitude"));
            location.setLatitude(resultSet.getString("latitude"));
            location.setEntityId(resultSet.getString("entity_id"));

            return location;
        } catch (Exception e) {
            LOG.error("Failed to get location from resultSet[" + resultSet + "]", e);
            return null;
        }
    }
}
