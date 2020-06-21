package com.webapp.paymentprocessing.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.webapp.paymentprocessing.model.Payment;
import com.webapp.paymentprocessing.search.PaymentSearch;
import com.webapp.paymentprocessing.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PaymentController {

    // having search item to use as filters
    // active and amount
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

    @PatchMapping(value="/cancelPayment",produces = MediaType.APPLICATION_JSON_VALUE)
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
        // calculating hour difference
        double difference = LocalDateTime.now().getHour() - payment.getCreationDate().getHour();
        //each type is multiplied by its coefficient
        if (payment.getType().getType().equals("TYPE1")) {
            fee = difference * 0.05;
        } else if (payment.getType().getType().equals("TYPE2")) {
            fee = difference * 0.1;
        } else {
            fee = difference * 0.15;
        }
        return fee;
    }

    @GetMapping("/doFilter")
    public List<Payment> doFilter(PaymentSearch search) {
        /*search parameters has to be set
         *should have been made to work via api
         *or at least consume RequestBody of PaymentSearch
         *but required logic is done in service
         */
        return paymentService.getFilters(search);
    }

    @GetMapping("/getPayment/{id}")
    public JSONObject getSpecificPayment(@PathVariable Long id) {
        JSONObject jo = new JSONObject();
        Payment payment = paymentService.getOne(id);
        jo.put("id", payment.getId());
        Double fee = calculateFee(payment);
        jo.put("fee", fee);
        return jo;
    }

    //using GeoLite2 free version to determine the country of orgin
    @GetMapping("/lookupId/{string}")
    public Country getLocation(@PathVariable String string) throws IOException, GeoIp2Exception {
        File database = new File("GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(string);
        CityResponse response = reader.city(ipAddress);
        Country country = response.getCountry();
        writeToFile(country.toString());
        return country;
    }

    public void writeToFile(String text) throws IOException {
        File file = new File("log.txt");
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(text);
        br.write("\n");
        br.close();
        fr.close();
    }


}
