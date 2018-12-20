package com.ansa;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.mapping.Mapper;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

import java.util.ArrayList;

public class FlinkPojoRunner {
    private static final ArrayList<Message> messages = new ArrayList<>(20);

    static {
        for (long i = 0; i < 20; i++) {
            messages.add(new Message("cassandra-" + i));
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Message> source = env.fromCollection(messages);



        CassandraSink.addSink(source)
                .setClusterBuilder(new ClusterBuilder() {
                    @Override
                    protected Cluster buildCluster(Cluster.Builder builder) {
                        return builder.addContactPoint("127.0.0.1").build();
                    }
                })
                .setMapperOptions(() -> new Mapper.Option[]{Mapper.Option.saveNullFields(true)})
                .build();

        env.execute("Cassandra Sink example");
    }
}
