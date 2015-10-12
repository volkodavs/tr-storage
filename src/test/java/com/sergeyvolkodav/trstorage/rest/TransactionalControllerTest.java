package com.sergeyvolkodav.trstorage.rest;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.sergeyvolkodav.trstorage.test.TrTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;


public class TransactionalControllerTest extends TrTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeClass
    public static void setupURL() {
        RestAssuredMockMvc.basePath = "/rest/v1/transactionservice";
    }

    @Before
    public void initializeMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testSaveTransactionSuccessful() {

        String newTransaction = "{ \"amount\":44.35,\"type\":\"bank\"}";
        given().
                contentType("application/json").
                body(newTransaction).
                when().
                put("/transaction/991").
                then().
                statusCode(HttpServletResponse.SC_OK).
                contentType("application/json").
                body("status", equalTo("ok"));
    }

    @Test
    public void testGetTransactionSuccessful() {
        given().
                contentType("application/json").
                when().
                get("/transaction/1").
                then().
                statusCode(HttpServletResponse.SC_OK).
                contentType("application/json").
                body("type", equalTo("car"));
    }

    @Test
    public void testGetTransactionSum() {
        given().
                contentType("application/json").
                when().
                get("/sum/1").
                then().
                statusCode(HttpServletResponse.SC_OK).
                contentType("application/json");
    }

    @Test
    public void testGetListTransactionsByType() {
        given().
                contentType("application/json").
                when().
                get("/types/bank").
                then().
                statusCode(HttpServletResponse.SC_OK).
                contentType("application/json");
    }

    @Test
    public void testInvalidSum() {
        given().
                contentType("application/json").
                when().
                get("/sum/101").
                then().
                statusCode(HttpServletResponse.SC_BAD_REQUEST).
                contentType("application/json").
                body("status", equalTo("error"));
    }

    @Test
    public void testInvalidType() {
        given().
                contentType("application/json").
                when().
                get("/transaction/101").
                then().
                statusCode(HttpServletResponse.SC_BAD_REQUEST).
                contentType("application/json").
                body("status", equalTo("error"));
    }

    @Test
    public void testInvalidSaveTransaction() {
        String newTransaction = "{ \"amount\":44.35}";

        given().
                contentType("application/json").
                body(newTransaction).
                when().
                put("/transaction/99").
                then().
                statusCode(HttpServletResponse.SC_BAD_REQUEST).
                contentType("application/json").
                body("status", equalTo("error"));
    }
}