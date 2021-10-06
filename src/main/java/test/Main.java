package test;

import java.util.List;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

public class Main {
    public static void main(String[] args) {
        ClientConfiguration cfg = new ClientConfiguration()
            .setAddresses(
                "c42af0de-9ea5-4abd-9182-26b0ac7c2bd3.gridgain-nebula-test.com:10800")
            .setUserName("сс_login")
            .setUserPassword("сс_password");

        try (IgniteClient client = Ignition.startClient(cfg)) {
            executeSql(client, "" +
                "CREATE TABLE IF NOT EXISTS DemoPersonCache(" +
                " id INTEGER PRIMARY KEY," +
                " name VARCHAR" +
                ") WITH \"cache_name=DemoPersonCache\";");

            executeSql(client, "DELETE FROM DemoPersonCache");
            executeSql(client, "INSERT INTO DemoPersonCache (id, name) VALUES (1, 'Mike')");
            executeSql(client, "INSERT INTO DemoPersonCache (id, name) VALUES (2, 'John')");

            System.out.println("Done!");
        } catch (Exception ex) {
            System.out.println("Could not connected");
            ex.printStackTrace();
        }
    }

    private static void executeSql(IgniteClient client, String qry) {
        SqlFieldsQuery query = new SqlFieldsQuery(qry);

        try (QueryCursor<List<?>> cur = client.query(query)) {
            cur.forEach(System.out::println);
        }
    }
}
