package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private static Faker faker = new Faker();

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static String generateRandomLogin() {
        return faker.name().username();
    }

    public static AuthInfo generateRandomUser() {
        return  new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public  static  VerificationCode generateRandomVerificationCode() {
        return new  VerificationCode(faker.numerify("######"));
    }

    public static String getInvalidAuthCode() {
        return faker.number().digits(6);
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
    @Value
    public  static class VerificationCode {
        String code;
    }


    public static AuthInfo getAuthInfo() {return new AuthInfo("vasya", "qwerty123");}

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class AuthCode {
        private String id;
        private String user_id;
        private String code;
        private String created;
    }
}