package com.example.Fashion_Shop.configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.context.annotation.Bean;

public class QdrantConfig {
    // Khai báo đối tượng kết nối đến cơ sở dữ liệu vector
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient.Builder grpClientBuilder = QdrantGrpcClient
                .newBuilder("5d6c3839-262a-4ae8-a7e0-95a67a8aba3d.europe-west3-0.gcp.cloud.qdrant.io", 6334, true); //
        grpClientBuilder.withApiKey("Kw_c52-Rc813YktGmNWFSDNWbN9URox6v18GNGnSr8ZMQavp3V-XTQ"); // API KEY
        return new QdrantClient(grpClientBuilder.build());
    }

    // Khai báo database được sử dụng trong cơ sở dữ liệu
    @Bean
    public QdrantVectorStore vectorStore(EmbeddingModel embeddingModel, QdrantClient qdrantClient) {
        return new QdrantVectorStore(qdrantClient, "ChauNguyenPhat", embeddingModel, true);
    }

}
