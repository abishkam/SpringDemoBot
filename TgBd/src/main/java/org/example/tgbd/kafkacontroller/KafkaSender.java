package org.example.tgbd.kafkacontroller;

import org.example.tgbd.dto.DtoKeeper;

public interface KafkaSender {

    void request(DtoKeeper dtoKeeper);
}
