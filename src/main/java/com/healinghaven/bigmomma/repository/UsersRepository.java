package com.healinghaven.bigmomma.repository;

import com.healinghaven.bigmomma.datasource.db.ConnectionFactory;
import com.healinghaven.bigmomma.entity.User;
import com.healinghaven.bigmomma.enums.UserType;
import com.healinghaven.bigmomma.utils.DatabaseUtil;
import com.healinghaven.bigmomma.utils.DateUtil;
import com.healinghaven.bigmomma.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                    preparedStatement.setString(2, EncryptionUtil.getHashedSHA256String(user.getAccessKey().getEmail()));

                    resultSet = preparedStatement.executeQuery();

                    if(resultSet.next() && resultSet.getBoolean("is_active")) {
                        LOG.info("Valid username and password entered, now going to authenticate user");
                        user.getAccessKey().setLastLogonDateTime(DateUtil.getHistoryDateFormat(String.valueOf(System.currentTimeMillis())));

                        user.setUserId(String.valueOf(resultSet.getInt("user_id")));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmailAddress(resultSet.getString("email_address"));
                        user.setUserType(UserType.getUserType(String.valueOf(resultSet.getInt("user_type"))));
                        user.setCellphoneNumber("cellphone_number");

                        updateLastLogon(user.getEmailAddress(), connection, preparedStatement, resultSet);
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

    private void updateLastLogon(String emailAddress, Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        LOG.info("Updating last logon for user[" + emailAddress + "]");
        //TODO: update last logon for user
    }
}
