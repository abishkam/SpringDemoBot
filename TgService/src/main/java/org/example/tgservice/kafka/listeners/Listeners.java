package org.example.tgservice.kafka.listeners;

import org.example.tgbd.dto.DtoKeeper;

public interface Listeners {

    boolean support(String string);

    void send(DtoKeeper dtoKeeper);
}
