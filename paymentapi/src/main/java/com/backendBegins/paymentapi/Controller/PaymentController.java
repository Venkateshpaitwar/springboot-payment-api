package com.backendBegins.paymentapi.Controller;

import com.backendBegins.paymentapi.DTO.*;
import com.backendBegins.paymentapi.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backendBegins.paymentapi.util.ApiResponseUtil;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id){

        PaymentRequest internalRequestObj = new PaymentRequest();
        internalRequestObj.setPaymentId(id);

        PaymentResponse payment = paymentService.getPaymentDetailsById(internalRequestObj);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment fetched successfully", payment)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@Valid @RequestBody PaymentRequest request){

        PaymentResponse payment = paymentService.createPayment(request);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment created successfully", payment));
    }
}