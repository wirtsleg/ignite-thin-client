package test;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.client.SslMode;
import org.apache.ignite.configuration.ClientConfiguration;

public class Main {
    public static void main(String[] args) {
        ClientConfiguration cfg = new ClientConfiguration()
            .setAddresses(
                "385da3d5-567a-470d-bff8-d3224301df60.gridgain-nebula-test.com:10800")
            .setUserName("сс_login")
            .setUserPassword("сс_password")
            .setSslMode(SslMode.REQUIRED);

        try (IgniteClient client = Ignition.startClient(cfg)) {
            client.query(new SqlFieldsQuery("" +
                "CREATE TABLE DemoPersonCache(" +
                " id INTEGER PRIMARY KEY," +
                " name VARCHAR" +
                ") WITH \"cache_name=DemoPersonCache\";"));

            ClientCache<Integer, String> cache = client.cache("DemoPersonCache");
            cache.put(1, "Mike");
            cache.put(2, "John");

            System.out.println("Done!");
        } catch (Exception ex) {
            System.out.println("Could not connected");
        }
    }
}
