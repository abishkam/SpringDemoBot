package org.example.tgbd.kafkasender;

import org.example.tgbd.dto.DtoKeeper;

public interface KafkaSender {

    void send(DtoKeeper dtoKeeper);
}
