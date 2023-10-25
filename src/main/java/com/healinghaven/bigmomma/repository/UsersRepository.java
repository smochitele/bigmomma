package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.AccessKey;
import com.healinghaven.bigmomma.entity.User;
import com.healinghaven.bigmomma.enums.UserSearchCriteria;
import com.healinghaven.bigmomma.enums.UserStatus;
import com.healinghaven.bigmomma.enums.UserType;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import com.healinghaven.bigmomma.utils.EncryptionUtil;
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
public class UsersRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UsersRepository.class);

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    public User getUser(User user) {
        if(user != null && user.getAccessKey() != null && user.getEmailAddress() != null) {
            try {
                final String checkUserSQL = "SELECT * FROM momma_db.users_auth WHERE user_id = ?";
                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(checkUserSQL);
                preparedStatement.setString(1, EncryptionUtil.getHashedSHA256String(user.getEmailAddress()));

                resultSet = preparedStatement.executeQuery();
                LOG.info("Executing query to lookup user[" + checkUserSQL + "]");
                if(resultSet.next()) {
                    LOG.info("User[" + user + "] exists, now looking up password");

                    final String validateUserSQL = "SELECT * FROM momma_db.users_auth WHERE user_id = ? AND password = ?";
                    preparedStatement = connection.prepareStatement(validateUserSQL);
                    preparedStatement.setString(1, EncryptionUtil.getHashedSHA256String(user.getEmailAddress()));
                    preparedStatement.setString(2, EncryptionUtil.getHashedSHA256String(user.getAccessKey().getPassword()));

                    resultSet = preparedStatement.executeQuery();

                    if(resultSet.next()) {
                        LOG.info("Valid username and password entered, now going to authenticate user");
                        //Getting the last logon time from DB
                        AccessKey lastAccessKey = getAccessKey(user.getEmailAddress());
                        //Getting the user from the DB
                        user = getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, user.getEmailAddress());
                        //Updating the last logon to the latest values
                        updateLastLogon(user, connection, preparedStatement, resultSet);
                        //Setting previous logon details to current login
                        user.setAccessKey(lastAccessKey);
                        LOG.info("Returning user[" + user + "] after successful login");
                        return user;
                    } else {
                        LOG.warn("Incorrect password entered, not going to validate user");
                        return new User();
                    }
                } else {
                    LOG.warn("Unable to find user on the auth table");
                    return null;
                }
            } catch (Exception e) {
                LOG.error("Failed to retrieve user", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.warn("This user[" + user + "] does not exist");
            return null;
        }
    }

    public List<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            final String SQL = "SELECT * FROM momma_db.users";
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            LOG.info("Executing query[" + SQL + "]");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setUserId(String.valueOf(resultSet.getInt("user_id")));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmailAddress(resultSet.getString("email_address"));
                user.setUserType(UserType.getUserType(String.valueOf(resultSet.getInt("user_type"))));
                user.setCellphoneNumber(resultSet.getString("cellphone_number"));
                user.setActive(resultSet.getBoolean("is_active"));
                user.setAccessKey(getAccessKey(user.getEmailAddress()));

                users.add(user);
            }

            if(users.isEmpty())
                LOG.warn("The users table is empty");
            else
                LOG.info("Returning a list of users with size[" + users.size() + "]");

            return users;
        } catch (Exception e) {
            LOG.error("Failed to get users", e);
            return null;
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public User getUserByCategory(UserSearchCriteria searchCriteria, String criteriaValue) {
        if(searchCriteria != null && StringUtils.isNotBlank(criteriaValue)) {
            try {
                String searchSQL = "";
                switch (searchCriteria) {
                    case ID ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE user_id = '" + criteriaValue + "'";
                    case EMAIL_ADDRESS ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE email_address = '" + criteriaValue + "'";
                    case CELLPHONE_NUMBER ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE cellphone_number = '" + criteriaValue + "'";
                    case FIRST_NAME ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE first_name = '" + criteriaValue + "'";
                    case LAST_NAME ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE last_name = '" + criteriaValue + "'";
                    case USER_TYPE ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE user_type = '" + criteriaValue + "'";
                    default -> {
                        LOG.warn("No search criteria found, going to return[NULL]");
                        return null;
                    }
                }

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(searchSQL);
                LOG.info("Executing query[" + searchSQL + "]");
                resultSet = preparedStatement.executeQuery();

                User user = null;

                if(resultSet.next() && resultSet.getBoolean("is_active")) {
                    user = new User();

                    user.setUserId(String.valueOf(resultSet.getInt("user_id")));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmailAddress(resultSet.getString("email_address"));
                    user.setUserType(UserType.getUserType(String.valueOf(resultSet.getInt("user_type"))));
                    user.setCellphoneNumber(resultSet.getString("cellphone_number"));
                    user.setUserStatus(UserStatus.getUserStatus(resultSet.getInt("is_active")));
                }
                return user;
            } catch (Exception e) {
                assert LOG != null;
                LOG.error("Error while searching for user", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.error("Null inputs found! searchCriteria[" + searchCriteria + "] criteriaValue[" + criteriaValue + "]");
            return null;
        }
    }

    public List<User> getUsersByCategory(UserSearchCriteria searchCriteria, String criteriaValue) {
        if(searchCriteria != null && StringUtils.isNotBlank(criteriaValue)) {
            ArrayList<User> users = new ArrayList<>();
            try {
                String searchSQL = "";
                switch (searchCriteria) {
                    case ID ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE user_id = " + criteriaValue;
                    case EMAIL_ADDRESS ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE email_address = " + criteriaValue;
                    case CELLPHONE_NUMBER ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE cellphone_number = " + criteriaValue;
                    case FIRST_NAME ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE first_name = " + criteriaValue;
                    case LAST_NAME ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE last_name = " + criteriaValue;
                    case USER_TYPE ->
                            searchSQL = "SELECT * FROM momma_db.users WHERE user_type = " + criteriaValue;
                    default -> {
                        LOG.warn("No search criteria found, going to return[NULL]");
                        return null;
                    }
                }

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(searchSQL);
                LOG.info("Executing query[" + searchSQL + "]");
                resultSet = preparedStatement.executeQuery();

                User user = null;

                while(resultSet.next() && resultSet.getBoolean("is_active")) {
                    user = new User();

                    user.setUserId(String.valueOf(resultSet.getInt("user_id")));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmailAddress(resultSet.getString("email_address"));
                    user.setUserType(UserType.getUserType(String.valueOf(resultSet.getInt("user_type"))));
                    user.setCellphoneNumber(resultSet.getString("cellphone_number"));
                    user.setUserStatus(UserStatus.getUserStatus(resultSet.getInt("is_active")));

                    users.add(user);
                }
                return users;
            } catch (Exception e) {
                assert LOG != null;
                LOG.error("Error while searching for user", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.error("Null inputs found! searchCriteria[" + searchCriteria + "] criteriaValue[" + criteriaValue + "]");
            return null;
        }
    }

    private void updateLastLogon(User user, Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            LOG.info("Updating last logon for user[" + user + "]");
            final String SQL = "UPDATE momma_db.users_auth " +
                               "SET last_logon_time = ?," +
                               "last_logon_device = ? " +
                               "WHERE user_id = ?";


            connection = ConnectionFactory.getConnection();
            LOG.info("Executing query[" + SQL + "]");

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getAccessKey().getLastLogonDateTime());
            preparedStatement.setString(2, user.getAccessKey().getLastLogonDevice());
            preparedStatement.setString(3, EncryptionUtil.getHashedSHA256String(user.getEmailAddress()));

            preparedStatement.execute();
        } catch (Exception e) {
            LOG.error("Failed to update last logon", e);
        } finally {
            DatabaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    private AccessKey getAccessKey(String emailAddress) {
        if(StringUtils.isNotBlank(emailAddress)) {
            try {
                AccessKey accessKey = null;
                final String SQL = "SELECT * FROM momma_db.users_auth WHERE user_id = '" + EncryptionUtil.getHashedSHA256String(emailAddress) +"'";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);
                LOG.info("Executing query[" + SQL + "]");
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    accessKey = new AccessKey();

                    accessKey.setLastLogonDateTime(resultSet.getString("last_logon_date"));
                    accessKey.setLastLogonDevice(resultSet.getString("last_logon_device"));
                }
                return accessKey;
            } catch (Exception e) {
                LOG.error("Failed to get access key", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement, resultSet);
            }
        } else {
            LOG.warn("Empty string entered emailAddress[" + emailAddress + "]");
            return null;
        }
    }

    public User addUser(User user) {
        if (user != null) {
            try {
                final String SQL = "INSERT INTO momma_db.users (first_name, last_name, email_address, user_type, date_added, cellphone_number) " +
                                   "VALUES(?,?,?,?,?,?)";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmailAddress());
                preparedStatement.setInt(4, user.getUserType().ordinal());
                preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                preparedStatement.setString(6, user.getCellphoneNumber());

                LOG.info("Executing query[" + SQL + "]");

                preparedStatement.execute();

                setUserAccessKey(user);

                LOG.info("Successfully added user[" + user + "]");
                return user;
            } catch (Exception e) {
                LOG.error("Failed to add user[" + user + "]", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement);
            }
        } else {
            LOG.warn("User is null[" + user + "]");
            return user;
        }
    }

    public User updateUser(User user) {
        if (user != null) {
            if(getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, user.getEmailAddress()) != null) {
                try {
                    final String SQL = "UPDATE momma_db.users SET " +
                                       "first_name = ?, " +
                                       "last_name = ?, " +
                                       "user_type = ?, " +
                                       "cellphone_number = ?, " +
                                       "last_updated = ? " +
                                       "WHERE email_address = ?";

                    connection = ConnectionFactory.getConnection();
                    preparedStatement = connection.prepareStatement(SQL);

                    preparedStatement.setString(1, user.getFirstName());
                    preparedStatement.setString(2, user.getLastName());
                    preparedStatement.setInt(3, user.getUserType().ordinal());
                    preparedStatement.setString(4, user.getCellphoneNumber());
                    preparedStatement.setString(5, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                    preparedStatement.setString(6, user.getEmailAddress());

                    LOG.info("Executing query[" + SQL + "]");
                    preparedStatement.execute();

                    User updatedUser = getUserByCategory(UserSearchCriteria.EMAIL_ADDRESS, user.getEmailAddress());
                    user.setAccessKey(getAccessKey(user.getEmailAddress()));
                    LOG.info("Successfully updated user from [" + user + "] to user[" + updatedUser + "]");
                    return updatedUser;
                } catch (Exception e) {
                    LOG.error("Failed to update user[" + user + "]");
                    return null;
                } finally {
                    DatabaseUtil.close(connection, preparedStatement);
                }
            } else {
                LOG.warn("Cannot update a none existing user[" + user + "]");
                return null;
            }
        } else {
            LOG.warn("User is null[" + user + "]");
            return user;
        }
    }

    public AccessKey changePassword(AccessKey accessKey) {
        if (accessKey != null) {
            try {
                final String SQL = "UPDATE momma_db.users_auth SET " +
                                   "password = ?, " +
                                   "last_updated = ? " +
                                   "WHERE user_id = ?";

                connection = ConnectionFactory.getConnection();
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, EncryptionUtil.getHashedSHA256String(accessKey.getPassword()));
                preparedStatement.setString(2, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));
                preparedStatement.setString(3, EncryptionUtil.getHashedSHA256String(accessKey.getEmail()));

                LOG.info("Executing query[" + SQL + "]");
                preparedStatement.execute();
                return accessKey;
            } catch (Exception e) {
                LOG.info("Failed to update password", e);
                return null;
            } finally {
                DatabaseUtil.close(connection, preparedStatement);
            }
        } else {
            return null;
        }
    }

    public void deleteUser(String emailAddress) {
        if (StringUtils.isNotBlank(emailAddress)) {
            try {
                final String SQL = "UPDATE momma_db.users SET " +
                                   "is_active = 0 " +
                                   "WHERE email_address = " + emailAddress;

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");
                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.execute();
            } catch (Exception e) {
                LOG.error("Failed to delete user with email[" + emailAddress + "]");
            }
        } else {
            LOG.warn("User's email address is blank");
        }
    }

    private void setUserAccessKey(User user) {
        if(user != null) {
            try {
                final String SQL = "INSERT INTO momma_db.users_auth (user_id, password, last_logon_device) VALUES (?,?,?)";

                connection = ConnectionFactory.getConnection();
                LOG.info("Executing query[" + SQL + "]");

                preparedStatement = connection.prepareStatement(SQL);

                preparedStatement.setString(1, EncryptionUtil.getHashedSHA256String(user.getAccessKey().getEmail()));
                preparedStatement.setString(2, EncryptionUtil.getHashedSHA256String(user.getAccessKey().getPassword()));
                preparedStatement.setString(3, DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));

                preparedStatement.execute();

                LOG.info("Successfully added access key for user[" + user + "]");
            } catch (Exception e) {
                LOG.error("Failed to update user access key for user[" + user + "]", e);
            } finally {
                DatabaseUtil.close(connection, preparedStatement);
            }
        }
    }
}
