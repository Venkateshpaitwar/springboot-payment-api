package com.backendBegins.paymentapi.Service;

import com.backendBegins.paymentapi.DTO.PaymentRequest;
import com.backendBegins.paymentapi.DTO.PaymentResponse;
import com.backendBegins.paymentapi.Entity.PaymentEntity;
import com.backendBegins.paymentapi.Entity.PaymentStatus;
import com.backendBegins.paymentapi.Exception.PaymentNotFoundException;
import com.backendBegins.paymentapi.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        payment.setStatus(PaymentStatus.PENDING);

        PaymentEntity savedPayment = paymentRepository.save(payment);

        logger.info("Payment created successfully with id: {}", savedPayment.getId());

        return mapModelToResponseDTO(savedPayment);
    }


    public List<PaymentResponse> getAllPayments(int page, int size) {

        logger.info("Fetching all payments with page {} and size {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<PaymentEntity> paymentPage = paymentRepository.findAll(pageable);

        return paymentPage.getContent()
                .stream()
                .map(this::mapModelToResponseDTO)
                .toList();
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
    public Page<PaymentEntity> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
    private PaymentResponse mapToResponse(PaymentEntity entity) {

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(entity.getId());
        response.setAmount(entity.getPaymentAmount());
        response.setCurrency(entity.getPaymentCurrency());
        response.setUserEmail(entity.getUserEmail());
        response.setStatus(
                entity.getStatus() != null ? entity.getStatus().name() : "PENDING"
        );
        return response;
    }
}