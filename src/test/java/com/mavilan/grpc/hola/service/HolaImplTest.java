package com.mavilan.grpc.hola.service;

import com.mavilan.grpc.hola.GenerarSaludoGrpc;
import com.mavilan.grpc.hola.Hola;
import com.mavilan.grpc.hola.HolaRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HolaImplTest {

    private Server server;
    private GenerarSaludoGrpc.GenerarSaludoBlockingStub blockingStub;

    @Before
    public void antes() throws IOException {
        server = ServerBuilder.forPort(50050).addService(new HolaImpl()).build();
        server.start();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50050).usePlaintext().build();
        blockingStub = GenerarSaludoGrpc.newBlockingStub(channel);
    }

    @After
    public void despues(){
        server.shutdownNow();
    }
    @Test
    public void executeService(){
        Assert.assertEquals("Hola marco", blockingStub.saluda(HolaRequest.newBuilder()
                .setHola(Hola.newBuilder().setNombre("marco").build())
                .build())
                .getHola()
                .getNombre());
    }

    @Test
    public void executeServiceEmpty(){
        try {
            blockingStub.saluda(HolaRequest.newBuilder().setHola(Hola.getDefaultInstance()).build());
        } catch (StatusRuntimeException sre){
            Assert.assertEquals("nombre no debe ser vacio", sre.getStatus().getDescription());
            Assert.assertEquals("INVALID_ARGUMENT", sre.getStatus().getCode().name());
        }
    }
}
