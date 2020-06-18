package com.webapp.paymentprocessing.service;

import com.webapp.paymentprocessing.model.Payment;
import com.webapp.paymentprocessing.repository.PaymentRepository;
import com.webapp.paymentprocessing.search.PaymentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public void create(Payment payment){
        payment.setActive(true);
        payment.setCreationDate(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    public List<Payment> getAll(){
        return paymentRepository.findAll();
    }

    public Payment getOne(Long id){
        return paymentRepository.findById(id).get();
    }

    public void update(Payment payment){
        paymentRepository.save(payment);
    }

    public List<Payment> getFilters(PaymentSearch search){
        return paymentRepository.findAll(new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if(search.getAmount()!=null){
                    p = cb.and(p,cb.equal(root.get("amount"),search.getAmount()));
                }
                if(search.isActive()){
                    p = cb.and(p,cb.equal(root.get("active"),search.isActive()));
                }
                return p;
            }
        });
    }
}
