/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.cassandra.transforms.type.deserializer;

import java.nio.ByteBuffer;

import org.apache.cassandra.cql3.Duration;
import org.apache.cassandra.db.marshal.AbstractType;

import io.debezium.connector.cassandra.transforms.CassandraTypeKafkaSchemaBuilders;
import io.debezium.time.NanoDuration;

public class DurationTypeDeserializer extends BasicTypeDeserializer {
    /*
     * Cassandra Duration type is serialized into micro seconds in double.
     */

    public DurationTypeDeserializer() {
        super(CassandraTypeKafkaSchemaBuilders.DURATION_TYPE);
    }

    @Override
    public Object deserialize(AbstractType<?> abstractType, ByteBuffer bb) {
        Duration duration = (Duration) super.deserialize(abstractType, bb);
        int months = duration.getMonths();
        int days = duration.getDays();
        long nanoSec = duration.getNanoseconds();
        return NanoDuration.durationNanos(0, months, days, 0, 0, 0, nanoSec);
    }
}
