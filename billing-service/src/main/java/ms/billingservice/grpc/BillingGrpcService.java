package ms.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseStreamObserver){

        log.info("createBillingAccount req received: {}", billingRequest.toString());

        //Business logic-> save to db, ......

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("123")
                .setStatus("ACTIVE").build();

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();

    }
}
