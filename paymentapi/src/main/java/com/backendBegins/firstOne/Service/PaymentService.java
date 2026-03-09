package com.backendBegins.firstOne.Service;

import com.backendBegins.firstOne.DTO.PaymentRequest;
import com.backendBegins.firstOne.DTO.PaymentResponse;
import com.backendBegins.firstOne.Entity.PaymentEntity;
import com.backendBegins.firstOne.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponse getPaymentDetailsById(PaymentRequest internalRequestObject) {

        PaymentEntity paymentModel = paymentRepository.getPaymentById(internalRequestObject);

        PaymentResponse paymentResponse = mapModelToResponseDTO(paymentModel);
        return paymentResponse;
    }

    private PaymentResponse mapModelToResponseDTO(PaymentEntity paymentEntity){

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(paymentEntity.getId());
        response.setAmount(paymentEntity.getPaymentAmount());
        response.setCurrency(paymentEntity.getPaymentCurrency());
        return response;
    }
}
