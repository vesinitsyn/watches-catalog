package com.vesinitsyn.watches_catalog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WatchCatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkout_WhenCalledWithInvalidBody_ShouldReturnBadRequest() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform(post("/checkout")
                .content("invalid_body")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkout_WhenCalledWithUnknownWatch_ShouldReturnNotFound() throws Exception {
        // Given
        String expectedMessage =
                "Not all watches found in catalog: ( foundWatchIds: [], expectedWatchIds: [unknown_watch_id]) ";

        // When
        // Then
        mockMvc.perform(post("/checkout")
                .content(asJson(List.of("unknown_watch_id")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void checkout_WhenCalledWithDifferentSetsOfWatches_ShouldReturnExpectedPrice(
            // Given
            List<String> purchasedWatchIds,
            String price
    ) throws Exception {
        // When
        // Then
        mockMvc.perform(post("/checkout")
                .content(asJson(purchasedWatchIds))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(price));
    }

    public static Object[][] provider() {
        return new Object[][]{
                {
                        List.of(),
                        "0.0"
                },
                {
                        List.of(
                                "001"
                        ),
                        "100.0"
                },
                {
                        List.of(
                                "001",
                                "002",
                                "003",
                                "004"
                        ),
                        "260.0"
                },
                {
                        List.of(
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001"
                        ),
                        "600.0"
                },
                {
                        List.of(
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "002",
                                "002",
                                "002",
                                "002"
                        ),
                        "840.0"
                },
                {
                        List.of(
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "001",
                                "002",
                                "002",
                                "002",
                                "002",
                                "003",
                                "004"
                        ),
                        "920.0"
                },
                {
                        List.of(
                                "001",
                                "002",
                                "001",
                                "004",
                                "003"
                        ),
                        "360.0"
                }
        };
    }

    private String asJson(List<String> purchasedWatchIds) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(purchasedWatchIds);
    }
}
