package com.backendBegins.paymentapi.Service;

import com.backendBegins.paymentapi.DTO.PaymentRequest;
import com.backendBegins.paymentapi.DTO.PaymentResponse;
import com.backendBegins.paymentapi.Entity.PaymentEntity;
import com.backendBegins.paymentapi.Exception.PaymentNotFoundException;
import com.backendBegins.paymentapi.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponse getPaymentDetailsById(PaymentRequest internalRequestObject) {

        PaymentEntity paymentModel = paymentRepository
                .findById(internalRequestObject.getPaymentId())
                .orElse(null);

        if(paymentModel == null){
            throw new PaymentNotFoundException("Payment not found");
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

    public List<PaymentResponse> getAllPayments(int page, int size) {

        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(page, size);

        org.springframework.data.domain.Page<PaymentEntity> paymentPage =
                paymentRepository.findAll(pageable);

        List<PaymentResponse> responseList = new java.util.ArrayList<>();

        for(PaymentEntity payment : paymentPage.getContent()){
            responseList.add(mapModelToResponseDTO(payment));
        }

        return responseList;
    }
    public void deletePayment(Long id) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        paymentRepository.delete(payment);
    }
}