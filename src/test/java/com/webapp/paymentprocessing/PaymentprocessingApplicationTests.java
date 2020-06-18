package com.webapp.paymentprocessing;

import com.webapp.paymentprocessing.controller.PaymentController;
import com.webapp.paymentprocessing.model.Currency;
import com.webapp.paymentprocessing.model.Payment;
import com.webapp.paymentprocessing.model.Type;
import com.webapp.paymentprocessing.service.PaymentService;
import net.minidev.json.JSONObject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class PaymentprocessingApplicationTests {

	@Test
	void testCreationOfPayment() throws Exception {

		String expetedResult="{id: 1,amount: 1500.0,debtorIban: SE35 5000 0000 0549 1000 0003," +
				"creditorIban:CH93 0076 2011 6238 5295 7, type: TYPE1,currency: EUR";


	}

}
