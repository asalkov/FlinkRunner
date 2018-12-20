package com.ansa;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.mapping.Mapper;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

import java.util.ArrayList;

public class FlinkRunner {

    private static final String INSERT = "INSERT INTO cycling.record (record_id, ticker) VALUES (?, ?)";
    private static final ArrayList<Tuple2<String, String>> collection = new ArrayList<>(20);

    static {
        for (int i = 0; i < 20; i++) {
            collection.add(new Tuple2<>("cassandra-" + i, "" + i));
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        DataStream<Record> data = env.fromElements(
//                new Record("AAA", "B", 1000L),
//                new Record("BBB", "B", 100L),
//                new Record("AAA", "S", 400L)
//        );

        DataStreamSource<Tuple2<String, String>> source = env.fromCollection(collection);

//        DataStream toSave = source.
//                map(item -> {
//                    if ("S".equals(item.getSide())) {
//                        return new Record(item.getTicker(), item.getSide(), item.getAmount() * -1);
//                    } else return item;
//                });

        CassandraSink
                .addSink(source)
                .setQuery(INSERT)
                .setClusterBuilder(new ClusterBuilder() {
                    @Override
                    protected Cluster buildCluster(Cluster.Builder builder) {
                        return builder.addContactPoint("127.0.0.1").build();
                    }
                })
                .setMapperOptions(() -> new Mapper.Option[]{Mapper.Option.saveNullFields(true)})
                .build();


        //toSave.print();
        env.execute();


    }
}
