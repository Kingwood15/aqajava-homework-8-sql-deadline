package ru.netology.domain.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.mode.User;

import java.sql.DriverManager;

public class DataHelper {

    private static final String passEncrypted = "$2a$10$CNrT4S.Oo27u9x/iujHEvuqwsMTeYAn7xa5H0qAq/dhe1P1Yttr7m";
    private static final String pass = "qwerty123";

    private DataHelper() {
    }

    public static User getAuthInfo() {
        //return new AuthInfo("vasya", "qwerty123");
        return requestUser();
    }

    public static String getVerificationCodeFor(User authInfo) {
        //return new VerificationCode("12345");
        return requestCode(authInfo);
    }

    //запрос пользователя из БД
    @SneakyThrows
    private static User requestUser() {
        var runner = new QueryRunner();
        var sqlRequestUser = "SELECT * FROM users WHERE login = ?;";
        String vasyaId = "vasya";

        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass")) {
            return runner.query(conn, sqlRequestUser, vasyaId, new BeanHandler<>(User.class));
        }
    }

    //запрос кода верификации из БД
    @SneakyThrows
    private static String requestCode(User user) {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();
        var sqlRequestSortByTime = "SELECT code FROM auth_codes ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestSortByTime, new ScalarHandler<>());
        }
    }

    //вычистка данных за SUT
    @SneakyThrows
    public static void clearSUTData() {
        var runner = new QueryRunner();
        var sqlCheckUsers = "SELECT * FROM users WHERE login = 'petya' OR login = 'vasya';";
        var sqlDeleteCards = "DELETE FROM cards WHERE user_id = ?;";
        var sqlDeleteUsers = "DELETE FROM users WHERE id = ? OR id = ?;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            var allUsers = runner.query(conn,
                    sqlCheckUsers, new BeanListHandler<>(User.class));
            //Проверка наличия в базе заданных пользователей:
            if (allUsers.size() > 0) {
                runner.update(conn, sqlDeleteCards,
                        //Передача id Васи, для удаления карт:
                        allUsers.get(1).getId());
                runner.update(conn, sqlDeleteUsers,
                        //Передача id Пети, для удаления пользователя:
                        allUsers.get(0).getId(),
                        //Передача id Васи, для удаления пользователя:
                        allUsers.get(1).getId());
            }
        }
    }

    //создание пользователя
    @SneakyThrows
    public static void requestCreateUser() {
        var runner = new QueryRunner();
        var sqlAddUser = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            runner.update(conn, sqlAddUser,
                    getId(),
                    getLogin(),
                    //Пароль qwerty123:
                    passEncrypted);
        }
    }

    private static String getId() {
        Faker faker = new Faker();
        return faker.internet().uuid();
    }

    private static String getLogin() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String getValidPass() {
        return pass;
    }
}