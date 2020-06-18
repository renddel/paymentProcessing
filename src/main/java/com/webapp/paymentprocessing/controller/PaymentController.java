package com.webapp.paymentprocessing.controller;

import com.maxmind.geoip2.WebServiceClient;
import com.webapp.paymentprocessing.model.Payment;
import com.webapp.paymentprocessing.search.PaymentSearch;
import com.webapp.paymentprocessing.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PaymentController {

    PaymentSearch search;

    @Autowired
    PaymentService paymentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Payment createPayment(@RequestBody final Payment payment) {
        paymentService.create(payment);
        return paymentService.getOne(payment.getId());
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Payment cancelPayment(Long id) {
        Payment payment = paymentService.getOne(id);
        if (LocalDate.now().isEqual(payment.getCreationDate().toLocalDate())) {
            payment.setFee(calculateFee(payment));
            payment.setActive(false);
            paymentService.update(payment);
        }
        return payment;
    }

    public Double calculateFee(Payment payment) {
        double fee;
        double difference = LocalDateTime.now().getHour() - payment.getCreationDate().getHour();
        if (payment.getType().getType().equals("TYPE1")) {
            fee = difference * 0.05;
        } else if (payment.getType().getType().equals("TYPE2")) {
            fee = difference * 0.1;
        } else {
            fee = difference * 0.15;
        }
        return fee;
    }

//    //or you can do this with specifications but since there is no front end no point
//    @GetMapping("isActive/{active}/filterByAmount/{amount}")
//    public List<Payment> getAllActiveAndFilterByAmount(@PathVariable boolean active,@PathVariable Long amount) {
//        return paymentService.getFilters(amount,active);
//    }

    @GetMapping("/testing/")
    public List<Payment> doFilter(PaymentSearch search) {
        return paymentService.getFilters(search);
    }

    @GetMapping("/getPayment/{id}")
    public JSONObject getSpecificPayment(@PathVariable Long id){
        JSONObject jo = new JSONObject();
        Payment payment = paymentService.getOne(id);
        jo.put("id",payment.getId());
        Double fee = calculateFee(payment);
        jo.put("fee",fee);
        return jo;
    }

    public String getLocation(){
        WebServiceClient.Builder builder
                = new WebServiceClient.Builder(MyConstants.MY_USER_ID, MyConstants.MY_LICENSE_KEY);
    }

}
