package com.backendBegins.paymentapi.Controller;

import com.backendBegins.paymentapi.DTO.PaymentRequest;
import com.backendBegins.paymentapi.DTO.PaymentResponse;
import com.backendBegins.paymentapi.DTO.ApiResponse;
import com.backendBegins.paymentapi.Entity.PaymentStatus;
import com.backendBegins.paymentapi.Service.PaymentService;
import com.backendBegins.paymentapi.util.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment API", description = "Operations related to payment processing")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get payment by ID", description = "Fetch a payment using its unique ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @Parameter(description = "ID of the payment") @PathVariable Long id){

        PaymentResponse payment = paymentService.getPaymentDetailsById(id);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment fetched successfully", payment)
        );
    }


    @Operation(summary = "Create a new payment", description = "Creates a new payment record")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid payment request")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request){

        PaymentResponse payment = paymentService.createPayment(request);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment created successfully", payment)
        );
    }
    @PostMapping("/{id}/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@PathVariable Long id) {

        PaymentResponse response = paymentService.processPayment(id);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment processed successfully", response)
        );
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByUser(
            @PathVariable String email) {

        List<PaymentResponse> payments = paymentService.getPaymentsByUser(email);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payments fetched successfully", payments)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByStatus(
            @PathVariable PaymentStatus status){
        List<PaymentResponse> payments = paymentService.getPaymentsByStatus(status);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payments fetched successfully", payments)
        );
    }


    @Operation(summary = "Get all payments", description = "Retrieve a paginated list of payments")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments(
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page")
            @RequestParam(defaultValue = "5") int size) {

        List<PaymentResponse> payments = paymentService.getAllPayments(page, size);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payments fetched successfully", payments)
        );
    }


    @Operation(summary = "Delete payment", description = "Delete a payment using its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(
            @Parameter(description = "ID of the payment to delete") @PathVariable Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment deleted successfully", null)
        );
    }


    @Operation(summary = "Update payment", description = "Update payment details using payment ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @Parameter(description = "ID of the payment to update") @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse updatedPayment = paymentService.updatePayment(id, request);

        return ResponseEntity.ok(
                ApiResponseUtil.success("Payment updated successfully", updatedPayment)
        );
    }
}