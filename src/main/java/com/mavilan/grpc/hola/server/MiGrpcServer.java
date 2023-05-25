package com.mavilan.grpc.hola.server;

import com.mavilan.grpc.hola.service.HolaImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static com.mavilan.grpc.hola.util.Constantes.PORT;

public class MiGrpcServer {

    public static void main(String[] args){

        Server server = ServerBuilder
                .forPort(PORT)
                .addService(new HolaImpl())
                .build();

        try {
            System.out.println("[SERV][INF] Servidor iniciado...");
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {

                System.out.println("[SERV][WAR] Orden de apagado recibida...");
                server.shutdown();
                System.out.println("[SERV][WAR] Servidor apagado... Adios!!");
            }));

            server.awaitTermination();
        } catch (IOException e){
            System.out.println("[SERV][ERR] No se pudo inciar el servidor...: " + e.getMessage());
        } catch (InterruptedException e){
            System.out.println("[SERV][ERR] No se pudo mantener encendido el servidor...: " + e.getMessage());
        }
    }
}
