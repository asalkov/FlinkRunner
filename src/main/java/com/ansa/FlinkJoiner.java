package com.ansa;

import com.datastax.driver.core.Cluster;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.batch.connectors.cassandra.CassandraPojoInputFormat;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

import java.time.LocalTime;
import java.util.ArrayList;

public class FlinkJoiner {
    private static final ArrayList<Message> messages = new ArrayList<>(20);

    static {
        for (long i = 0; i < 20; i++) {
            messages.add(new Message("cassandra-" + i, LocalTime.now().toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Message> input = env.fromCollection(messages);

        ClusterBuilder clusterBuilder = new ClusterBuilder() {

            @Override
            public Cluster buildCluster(Cluster.Builder builder) {
                return builder.addContactPoint("127.0.0.1").build();
            }
        };


        InputFormat inputFormat =
                new CassandraPojoInputFormat("select * from test.message", clusterBuilder, Message.class);

        DataStreamSource<Message> messages = env.createInput(inputFormat, TypeInformation.of(Message.class));

        messages.print();


        env.execute();
    }
}

