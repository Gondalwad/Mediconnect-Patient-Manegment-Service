package com.pm.billingservice.grpc;

import billing.BillingResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase{

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);


    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver){

        log.info("Create Billing Account Request Received{}",billingRequest.toString());

        BillingResponse response = billing.BillingResponse.newBuilder()
                .setAccountId(billingRequest.getEmail())
                .setStatus("Active")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
