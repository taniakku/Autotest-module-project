package data;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;

public class PostgreSQLHelper {

    @Step("Запрос в БД данных о записанной в неё карте")
    public static String getCardId() {
        var cardId = "SELECT credit_id FROM order_entity WHERE created = (SELECT max(created) FROM order_entity);";
        return getValue(cardId);
    }

    @Step("Запрос в БД информации о платеже")
    public static String getPaymentStatus() {
        var paymentStatus = "SELECT status FROM payment_entity WHERE created = (SELECT max(created) FROM payment_entity);";
        return getValue(paymentStatus);
    }

    @SneakyThrows
    public static String getValue(String request) {
        var runner = new QueryRunner();
        var value = new String();
        try (var conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "admin");
        ) {
            var result = runner.query(conn, request, new ScalarHandler<>());
            value = String.valueOf(result);
        }
        return value;
    }
}
