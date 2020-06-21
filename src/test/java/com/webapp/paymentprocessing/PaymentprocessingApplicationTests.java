package com.webapp.paymentprocessing;

import com.jayway.jsonpath.JsonPath;
import com.webapp.paymentprocessing.controller.PaymentController;
import com.webapp.paymentprocessing.model.Currency;
import com.webapp.paymentprocessing.model.Payment;
import com.webapp.paymentprocessing.model.Type;
import com.webapp.paymentprocessing.service.PaymentService;
import net.minidev.json.JSONObject;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(PaymentController.class)
class PaymentprocessingApplicationTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    PaymentService paymentService;

    Payment payment;
    List<Payment> payments;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
        payment.setId(1L);
        payment.setAmount(25L);
        payment.setType(Type.TYPE1);
        payment.setCurrency(Currency.EUR);
        payment.setCreditorIban("SE35 5000 0000");
        payment.setActive(true);
        payment.setDebtorIban("SE35 5000 0000 0549 1000 0003");
        payment.setCreationDate(LocalDateTime.now().minusHours(1));
        payment.setFee(3.0);
        payment.setDetails("details");
    }

    @Test
    public void calculateFeeTest() {
        PaymentController pc = new PaymentController();
        Double fee = pc.calculateFee(payment);
        assertTrue(fee > 0);
    }

    @Test
    public void getPaymentsTest() throws Exception {
        when(paymentService.getAll()).thenReturn(Arrays.asList(payment));
        mvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].amount",is(25)))
                .andExpect(jsonPath("$[0].debtorIban",is("SE35 5000 0000 0549 1000 0003")))
                .andExpect(jsonPath("$[0].creditorIban",is("SE35 5000 0000")))
                .andExpect(jsonPath("$[0].type",is("TYPE1")))
                .andExpect(jsonPath("$[0].currency",is("EUR")))
                .andExpect(jsonPath("$[0].active",is(true)))
                .andExpect(jsonPath("$[0].fee",is(3.0)))
                .andExpect(jsonPath("$[0].details",is("details")));
    }

    @Test
    public void getSpecificPaymentTest() throws Exception {
        when(paymentService.getOne(anyLong())).thenReturn(payment);
        mvc.perform(get("/api/getPayment/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fee", is(0.05)));
    }

}

