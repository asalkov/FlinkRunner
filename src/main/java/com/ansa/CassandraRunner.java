package com.ansa;


import com.datastax.driver.core.ResultSet;

public class CassandraRunner {
    public static void main(String[] args) {
        CassandraConnector connector = new CassandraConnector();

        connector.connect("127.0.0.1", 9042);


        ResultSet result =
                connector.getSession().execute("SELECT * FROM cycling.record;");

        result.all().forEach(item -> {
            System.out.println(item);
        });

    }
}
