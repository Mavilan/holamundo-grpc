package com.mavilan.grpc.hola.client;

import com.mavilan.grpc.hola.GenerarSaludoGrpc;
import com.mavilan.grpc.hola.Hola;
import com.mavilan.grpc.hola.HolaResponse;
import com.mavilan.grpc.hola.HolaResques;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.mavilan.grpc.hola.util.Constantes.HOSTNAME;
import static com.mavilan.grpc.hola.util.Constantes.PORT;

public class HolaClient {

    public static void main(String[] args) {

        System.out.println("[CLIE][DEB] Se inicia cliente...");
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(HOSTNAME, PORT)
                .usePlaintext()
                .build();

        GenerarSaludoGrpc.GenerarSaludoBlockingStub blockingStub = GenerarSaludoGrpc.newBlockingStub(managedChannel);
        HolaResponse holaResponse = blockingStub.saluda(
                HolaResques.newBuilder()
                        .setHola(
                                Hola.newBuilder()
                                        .setNombre("Marco")
                                        .build())
                        .build()
        );
        System.out.println("[CLIE][INF] Se recibe la repuesta del servicio: " + holaResponse);

        managedChannel.shutdown();
        System.out.println("[CLIE][WAR] canal cerrado...");
    }
}
