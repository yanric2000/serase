package com.startup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import com.startup.domain.user.UserRole;
import com.startup.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StartupApiTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private UserRepository users;

  @Test
  void personSavesADrinkLearnsTheRecipeAndFindsANearbyEvent() throws Exception {
    mockMvc
        .perform(get("/drinks"))
        .andExpect(status().isUnauthorized());

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ana@startup.dev\",\"password\":\"senha-segura\"}"))
        .andExpect(status().isCreated());

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"ana@startup.dev\",\"password\":\"senha-segura\"}"))
        .andExpect(status().isConflict());

    var login =
        mockMvc
            .perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"ana@startup.dev\",\"password\":\"senha-segura\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(jsonPath("$.userId").isNotEmpty())
            .andReturn();

    String body = login.getResponse().getContentAsString();
    String token = JsonPath.read(body, "$.token");
    String userId = JsonPath.read(body, "$.userId");

    mockMvc
        .perform(get("/auth/session").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(userId));

    mockMvc
        .perform(
            post("/drinks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\":\"Caipirinha\",\"description\":\"Limao, cachaca e gelo\",\"category\":\"cocktail\"}"))
        .andExpect(status().isForbidden());

    var user = users.findByEmail("ana@startup.dev").orElseThrow();
    user.changeRole(UserRole.ADMIN);
    users.saveAndFlush(user);

    var drink =
        mockMvc
            .perform(
                post("/drinks")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\"name\":\"Caipirinha\",\"description\":\"Limao, cachaca e gelo\",\"category\":\"cocktail\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Caipirinha"))
            .andReturn();
    String drinkId = JsonPath.read(drink.getResponse().getContentAsString(), "$.id");

    mockMvc
        .perform(
            post("/recipes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"drinkId\":\""
                        + drinkId
                        + "\",\"title\":\"Caipirinha classica\",\"ingredients\":\"limao, acucar, cachaca, gelo\",\"steps\":\"macere o limao com acucar e complete com cachaca\"}"))
        .andExpect(status().isCreated());

    mockMvc
        .perform(get("/recipes").param("drinkId", drinkId).header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Caipirinha classica"));

    mockMvc
        .perform(
            post("/events")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\":\"Noite de caipirinha\",\"description\":\"Degustacao no centro\",\"venue\":\"Bar do Centro\",\"city\":\"Sao Paulo\",\"latitude\":-23.5505,\"longitude\":-46.6333,\"startsAt\":\"2026-11-01T20:00:00\"}"))
        .andExpect(status().isCreated());

    mockMvc
        .perform(
            post("/events")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\":\"Festival na floresta\",\"description\":\"Longe de Sao Paulo\",\"venue\":\"Espaco Manaus\",\"city\":\"Manaus\",\"latitude\":-3.119,\"longitude\":-60.0217,\"startsAt\":\"2026-12-01T18:00:00\"}"))
        .andExpect(status().isCreated());

    mockMvc
        .perform(
            get("/events")
                .param("latitude", "-23.55")
                .param("longitude", "-46.63")
                .param("radiusKm", "10")
                .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("Noite de caipirinha"))
        .andExpect(jsonPath("$[0].distanceKm").isNumber());

    var saved =
        mockMvc
            .perform(
                post("/saved-drinks")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"drinkId\":\"" + drinkId + "\",\"note\":\"a que eu mais gostei\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.drinkName").value("Caipirinha"))
            .andReturn();
    String savedId = JsonPath.read(saved.getResponse().getContentAsString(), "$.id");

    mockMvc
        .perform(
            post("/saved-drinks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"drinkId\":\"" + drinkId + "\"}"))
        .andExpect(status().isConflict());

    mockMvc
        .perform(get("/saved-drinks").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));

    mockMvc
        .perform(delete("/saved-drinks/" + savedId).header("Authorization", "Bearer " + token))
        .andExpect(status().isNoContent());
  }
}
