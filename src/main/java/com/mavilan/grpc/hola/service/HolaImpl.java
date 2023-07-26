package com.mavilan.grpc.hola.service;

import com.mavilan.grpc.hola.GenerarSaludoGrpc;
import com.mavilan.grpc.hola.Hola;
import com.mavilan.grpc.hola.HolaRequest;
import com.mavilan.grpc.hola.HolaResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

// implementacion de la clase abstracta del servicio grpc
public class HolaImpl extends GenerarSaludoGrpc.GenerarSaludoImplBase {

    public HolaImpl(){
        System.out.println("[IMPL][DEB] Servicio HolaImpl llamado...");
    }
    @Override
    public void saluda(HolaRequest request, StreamObserver<HolaResponse> responseObserver) {

        if("".equals(request.getHola().getNombre())){
            System.out.println("[IMPL][ERR] Se ha producido un error");
            responseObserver.onError(  //usar la respuesta de error que nos da el responseObserver
                    Status.INVALID_ARGUMENT
                            .augmentDescription("nombre no debe ser vacio")
                            .asRuntimeException()
            );
        }

        responseObserver.onNext(  //usar la respuesta que nos da el responseObserver
                HolaResponse.newBuilder()
                        .setHola(Hola.newBuilder()
                                .setNombre("Hola " + request.getHola().getNombre())
                                .build())
                        .build()
        );
        System.out.println("[IMPL][INF] Respuesta creada");
        responseObserver.onCompleted();  //terminar la comunicacion de esta accion
    }
}
