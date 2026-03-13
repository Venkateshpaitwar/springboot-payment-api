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

        PaymentEntity paymentModel = paymentRepository
                .findById(internalRequestObject.getPaymentId())
                .orElse(null);

        if(paymentModel == null){
            return null;
        }

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
    public PaymentResponse createPayment(PaymentRequest request){

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentAmount(request.getAmount());
        paymentEntity.setPaymentCurrency(request.getCurrency());
        paymentEntity.setUserEmail(request.getUserEmail());

        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);

        return mapModelToResponseDTO(savedPayment);
    }
}