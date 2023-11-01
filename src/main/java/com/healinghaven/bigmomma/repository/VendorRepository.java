package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.Location;
import com.healinghaven.bigmomma.entity.Vendor;
import com.healinghaven.bigmomma.enums.LocationSearchCriteria;
import com.healinghaven.bigmomma.enums.UserSearchCriteria;
import com.healinghaven.bigmomma.service.LocationService;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorRepository {

    private static final Logger LOG = LoggerFactory.getLogger(VendorRepository.class);
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Autowired
    LocationService locationService;

    public Vendor addVendor(Vendor vendor) {
        if (vendor != null) {
            try {
                final String SQL = "INSERT INTO momma_db.vendors (vendor_name, vendor_description, email_address, cellphone_number, vendor_owner) " +
                                   "VALUES(?,?,?,?,?)";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, vendor.getName());
                preparedStatement.setString(2, vendor.getDescription());
                preparedStatement.setString(3, vendor.getEmailAddress());
                preparedStatement.setString(4, vendor.getCellphoneNumber());
                preparedStatement.setString(5, vendor.getOwner().getEmailAddress());

                LOG.info("Saving vendor[" + vendor + " to DB");

                preparedStatement.execute();

                LOG.info("Successfully added vendor[" + vendor + "] owned by user[" + vendor.getOwner() + "]");

                if (vendor.getLocation() != null) {
                    try {
                        preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()", Statement.RETURN_GENERATED_KEYS);
                        ResultSet rs = preparedStatement.executeQuery();
                        if(rs.next()) {
                            int vendorId = rs.getInt("LAST_INSERT_ID()");
                            vendor.setLocation(locationService.setEntityLocation(vendor.getLocation(), vendorId));
                        }
                    } catch (Exception e) {
                        LOG.error("Failed to set vendor location", e);
                    }
                }

                return vendor;
            } catch (Exception e) {
                LOG.error("Failed to add vendor[" + vendor + "]");
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.warn("Null vendor in the method input[addVendor(Vendor vendor)]");
            return null;
        }
    }

    //Gets a vendor belonging to an owner
    public Vendor getOwnersVendor(String ownerEmail) {
        if(StringUtils.isNotBlank(ownerEmail)) {
            try {
                final String SQL = "SELECT * FROM momma_db.vendors WHERE vendor_owner = ?";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, ownerEmail);
                LOG.info("Executing query[" + SQL + "]");
                resultSet = preparedStatement.executeQuery();
                Vendor vendor = null;
                if(resultSet.next() && resultSet.getBoolean("is_active")) {
                    vendor = new Vendor();

                    vendor.setId(resultSet.getString("vendor_id"));
                    vendor.setName(resultSet.getString("vendor_name"));
                    vendor.setEmailAddress(resultSet.getString("email_address"));
                    vendor.setCellphoneNumber(resultSet.getString("cellphone_number"));
                    vendor.setOwner(new UsersRepository().getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, resultSet.getString("email_address")));
                    vendor.setLocation(locationService.getEntityLocation(Integer.parseInt(vendor.getId())));
                    vendor.setDescription(resultSet.getString("vendor_description"));
                }
                LOG.info("Returning vendor[" + vendor + "]");
                return vendor;
            } catch (Exception e) {
                LOG.error("Failed to get the owner's vendor");
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.error("Invalid input for[public Vendor getOwnersVendor(String ownerEmail)]");
            return null;
        }
    }

    public List<Vendor> getVendors() {
        ArrayList<Vendor> vendors = null;
        try {
            vendors = new ArrayList<>();
            final String SQL = "SELECT * FROM momma_db.vendors";

            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            LOG.info("Executing query[" + SQL + "]");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next() && resultSet.getBoolean("is_active")) {
                Vendor vendor = new Vendor();

                vendor.setId(resultSet.getString("vendor_id"));
                vendor.setName(resultSet.getString("vendor_name"));
                vendor.setEmailAddress(resultSet.getString("email_address"));
                vendor.setCellphoneNumber(resultSet.getString("cellphone_number"));
                vendor.setOwner(new UsersRepository().getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, resultSet.getString("email_address")));
                vendor.setLocation(locationService.getEntityLocation(Integer.parseInt(vendor.getId())));
                vendor.setDescription(resultSet.getString("vendor_description"));

                vendors.add(vendor);
            }
            LOG.info("Returning vendors list with size[" + vendors.size() + "]");
            return vendors;
        } catch (Exception e) {
            LOG.error("Failed to get vendors", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public Vendor getVendorById(String vendorId) {
        if(StringUtils.isNotBlank(vendorId)) {
            try {
                final String SQL = "SELECT * FROM momma_db.vendors WHERE vendor_id = ?";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, vendorId);
                LOG.info("Executing query[" + SQL + "]");
                resultSet = preparedStatement.executeQuery();
                Vendor vendor = null;
                if(resultSet.next() && resultSet.getBoolean("is_active")) {
                    vendor = new Vendor();

                    vendor.setId(resultSet.getString("vendor_id"));
                    vendor.setName(resultSet.getString("vendor_name"));
                    vendor.setEmailAddress(resultSet.getString("email_address"));
                    vendor.setCellphoneNumber(resultSet.getString("cellphone_number"));
                    vendor.setOwner(new UsersRepository().getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, resultSet.getString("email_address")));
                    vendor.setDescription(resultSet.getString("vendor_description"));
                    vendor.setLocation(locationService.getEntityLocation(Integer.parseInt(vendor.getId())));
                }
                LOG.info("Returning vendor[" + vendor + "]");
                return vendor;
            } catch (Exception e) {
                LOG.error("Failed to get the owner's vendor");
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.error("Invalid input for[public Vendor getOwnersVendor(String ownerEmail)]");
            return null;
        }
    }

    public Vendor updateVendor(Vendor vendor) {
        if (vendor != null) {
            try {
                final String SQL = "UPDATE momma_db.vendors SET " +
                                   "vendor_name = ?, " +
                                   "vendor_description = ?, " +
                                   "email_address = ?, " +
                                   "cellphone_number = ?, " +
                                   "last_updated = ? " +
                                   "WHERE vendor_id = ?";

                connection = ConnectionFactory.getConnection();

                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, vendor.getName());
                preparedStatement.setString(2, vendor.getDescription());
                preparedStatement.setString(3, vendor.getEmailAddress());
                preparedStatement.setString(4, vendor.getCellphoneNumber());
                preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                preparedStatement.setString(6, vendor.getId());

                LOG.info("Executing query[" + SQL + "]");

                preparedStatement.execute();

                vendor.setLocation(locationService.updateEntityLocation(vendor.getLocation(), Integer.parseInt(vendor.getId())));
                return vendor;
            } catch (Exception e) {
                LOG.error("Failed to update vendor[" + vendor + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement);
            }
        } else {
            LOG.error("Null vendor passed in method[public Vendor updateVendor(Vendor vendor) ]");
            return null;
        }
    }

    public List<Vendor> getVendorByLocationCriteria(LocationSearchCriteria criteria, String value) {
        if (criteria != null) {
            try {
                List<Location> locations = locationService.getLocationsByCriteria(criteria, value);
                ArrayList<Vendor> vendors = new ArrayList<>();
                for (Location l : locations) {
                    vendors.add(getVendorById(l.getEntityId()));
                }
                return vendors;
            } catch (Exception e) {
                LOG.error("Failed to get vendors by criteria[" + criteria + "]", e);
                return null;
            }
        } else {
            LOG.error("Null value returned in method[public List<Vendor> getVendorByCriteria(VendorSearchCriteria criteria)]");
            return null;
        }
    }
}
