package com.ansa;

import com.datastax.driver.core.Cluster;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.batch.connectors.cassandra.CassandraInputFormat;
import org.apache.flink.batch.connectors.cassandra.CassandraPojoInputFormat;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

public class CassandraReader {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ClusterBuilder clusterBuilder = new ClusterBuilder() {

            @Override
            public Cluster buildCluster(Cluster.Builder builder) {
                return builder.addContactPoint("127.0.0.1").build();
            }
        };

        InputFormat inputFormat =
                new CassandraPojoInputFormat("select * from test.message", clusterBuilder, Message.class);

        DataStream<Message> messages = env.createInput(inputFormat, GenericTypeInfo.of(Message.class));

        messages.print();


        env.execute();
    }
}
