package com.backendBegins.paymentapi.Service;

import com.backendBegins.paymentapi.DTO.PaymentRequest;
import com.backendBegins.paymentapi.DTO.PaymentResponse;
import com.backendBegins.paymentapi.Entity.PaymentEntity;
import com.backendBegins.paymentapi.Exception.PaymentNotFoundException;
import com.backendBegins.paymentapi.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    PaymentRepository paymentRepository;


    public PaymentResponse getPaymentDetailsById(Long id) {

        logger.info("Fetching payment with id: {}", id);

        PaymentEntity paymentModel = paymentRepository
                .findById(id)
                .orElseThrow(() -> {
                    logger.error("Payment not found with id: {}", id);
                    return new PaymentNotFoundException("Payment not found");
                });

        return mapModelToResponseDTO(paymentModel);
    }


    private PaymentResponse mapModelToResponseDTO(PaymentEntity paymentEntity){

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(paymentEntity.getId());
        response.setAmount(paymentEntity.getPaymentAmount());
        response.setCurrency(paymentEntity.getPaymentCurrency());
        response.setUserEmail(paymentEntity.getUserEmail());

        return response;
    }


    public PaymentResponse createPayment(PaymentRequest paymentRequest) {

        logger.info("Creating payment for user: {}", paymentRequest.getUserEmail());

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentAmount(paymentRequest.getAmount());
        payment.setPaymentCurrency(paymentRequest.getCurrency());
        payment.setUserEmail(paymentRequest.getUserEmail());

        PaymentEntity savedPayment = paymentRepository.save(payment);

        logger.info("Payment created successfully with id: {}", savedPayment.getId());

        return mapModelToResponseDTO(savedPayment);
    }


    public List<PaymentResponse> getAllPayments(int page, int size) {

        logger.info("Fetching all payments with page {} and size {}", page, size);

        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(page, size);

        org.springframework.data.domain.Page<PaymentEntity> paymentPage =
                paymentRepository.findAll(pageable);

        List<PaymentResponse> responseList = new ArrayList<>();

        for(PaymentEntity payment : paymentPage.getContent()){
            responseList.add(mapModelToResponseDTO(payment));
        }

        return responseList;
    }


    public void deletePayment(Long id) {

        logger.info("Deleting payment with id: {}", id);

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Payment not found with id: {}", id);
                    return new PaymentNotFoundException("Payment not found");
                });

        paymentRepository.delete(payment);

        logger.info("Payment deleted successfully with id: {}", id);
    }


    public PaymentResponse updatePayment(Long id, PaymentRequest request) {

        logger.info("Updating payment with id: {}", id);

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Payment not found with id: {}", id);
                    return new PaymentNotFoundException("Payment not found");
                });

        payment.setPaymentAmount(request.getAmount());
        payment.setPaymentCurrency(request.getCurrency());
        payment.setUserEmail(request.getUserEmail());

        PaymentEntity updated = paymentRepository.save(payment);

        logger.info("Payment updated successfully with id: {}", updated.getId());

        return mapModelToResponseDTO(updated);
    }
}