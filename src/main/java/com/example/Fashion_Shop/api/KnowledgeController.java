package com.example.Fashion_Shop.api;

import org.jsoup.Jsoup;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class KnowledgeController {
    private final QdrantVectorStore qdrantVectorStore;

    public KnowledgeController(QdrantVectorStore qdrantVectorStore) {
        this.qdrantVectorStore = qdrantVectorStore;
    }

    // Thu thập kiến thức cho LLM
    @GetMapping("insert-from-url")
    public void insertFromUrl(@RequestParam(value = "url") String url) {
        // Khai báo tài liệu Jsoup và StringBuilder
        org.jsoup.nodes.Document jsoupDocument;
        Document vectorDocument;
        StringBuilder contentBuilder = new StringBuilder();

        try {
            // Kết nối và lấy nội dung từ URL
            jsoupDocument = Jsoup.connect(url).get();

            // Xây dựng nội dung
            contentBuilder.append(jsoupDocument.body().text()).append(" Đây là web nguồn tham khảo của nội dung ")
                    .append(jsoupDocument.title()).append(". Trang web (").append(url).append(" )\n");

            // Gán nội dung vào đối tượng Document để xử lý tiếp
            vectorDocument = new Document(contentBuilder.toString());

            // Chia nhỏ nội dung thành các token và thêm vào VectorStore
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            qdrantVectorStore.add(tokenTextSplitter.split(vectorDocument));
            System.out.println("Lưu thành công!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("insert-manual")
    public void insertManual(@RequestBody Map<String, String> requestData) {
        String content = requestData.get("content");
        String metadata = requestData.get("metadata"); // Thông tin phụ kèm theo, nếu cần

        try {
            // Gán nội dung vào đối tượng Document để xử lý
            Document vectorDocument = new Document(content);

            // Chia nhỏ nội dung thành các token và thêm vào VectorStore
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            qdrantVectorStore.add(tokenTextSplitter.split(vectorDocument));

            System.out.println("Lưu thành công nội dung thủ công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
