package avaliev.portfolio.quotesservice.api;

import avaliev.portfolio.quotesservice.service.QuotesService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@WebMvcTest
public class QuoteControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuotesService quotesService;

    @Test
    @DisplayName("should call quoteservice.addQuotes when put /quotes request executed")
    @SneakyThrows
    void addQuotesCall() {
        String requestBody = readStringFromResource("json/quotesList.json");
        var quotesCaptor = ArgumentCaptor.forClass(List.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/quotes")
                        .content(requestBody)
                        .header("content-type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(quotesService).addQuotes(quotesCaptor.capture());

        List values = quotesCaptor.getValue();
        Assertions.assertEquals(3, values.size());
    }

    String readStringFromResource(String fileName) throws IOException {
        var resource = new ClassPathResource("json/quotesList.json");
        return Files.readString(Path.of(resource.getURI()));
    }


}
