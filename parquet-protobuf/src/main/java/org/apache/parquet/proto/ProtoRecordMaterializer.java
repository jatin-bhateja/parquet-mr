/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.parquet.proto;

import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.conf.HadoopParquetConfiguration;
import org.apache.parquet.conf.ParquetConfiguration;
import org.apache.parquet.io.api.GroupConverter;
import org.apache.parquet.io.api.RecordMaterializer;
import org.apache.parquet.schema.MessageType;

import java.util.Map;

class ProtoRecordMaterializer<T extends MessageOrBuilder> extends RecordMaterializer<T> {

  private final ProtoRecordConverter<T> root;

  public ProtoRecordMaterializer(Configuration conf, MessageType requestedSchema, Class<? extends Message> protobufClass, Map<String, String> metadata) {
    this(new HadoopParquetConfiguration(conf), requestedSchema, protobufClass, metadata);
  }

  public ProtoRecordMaterializer(ParquetConfiguration conf, MessageType requestedSchema, Class<? extends Message> protobufClass, Map<String, String> metadata) {
    this.root = new ProtoRecordConverter<T>(conf, protobufClass, requestedSchema, metadata);
  }

  @Override
  public T getCurrentRecord() {
    return root.getCurrentRecord();
  }

  @Override
  public GroupConverter getRootConverter() {
    return root;
  }
}
