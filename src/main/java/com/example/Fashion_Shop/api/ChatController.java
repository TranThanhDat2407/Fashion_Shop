package com.example.Fashion_Shop.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ChatController {
    // Đối tượng để tương tác với LLM(OpenAi)
    private final ChatClient chatClient;

    // Đối tượng sử dụng để truy vấn thông tin
    private final QdrantVectorStore qdrantVectorStore;
    String context = "";

    public ChatController(ChatClient chatClient, QdrantVectorStore draQdrantVectorStore) {
        this.chatClient = chatClient;
        this.qdrantVectorStore = draQdrantVectorStore;
    }

    @GetMapping("chat-ai")
    public String doChat(@RequestParam String message) {
        // B1. Truy vấn từ cơ sở kiến thức với từ khóa là câu hỏi của nguời dùng
        // "message"
        List<Document> contexts = qdrantVectorStore.similaritySearch(SearchRequest.query(message).withTopK(10));

        // B2. Các thông tin có thể sử dụng để trả lời câu hỏi
        for (Document document : contexts) {
            context += document.getContent();
        }

        return chatClient.prompt().system(sp -> sp.param("context", context)) // Cung cấp nội dung có thể sử dụng để trả
                // lời cho LLM
                .user(message).call().content(); // Thực hiện gọi đến LLM để nhận câu trả lời và trả về cho client
    }
}
