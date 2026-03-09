package com.backendBegins.paymentapi.Service;

import com.backendBegins.paymentapi.DTO.PaymentRequest;
import com.backendBegins.paymentapi.DTO.PaymentResponse;
import com.backendBegins.paymentapi.Entity.PaymentEntity;
import com.backendBegins.paymentapi.Repository.PaymentRepository;
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
