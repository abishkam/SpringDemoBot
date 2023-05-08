package org.example.tgbd.kafkacontroller;

import org.example.tgbd.dto.DtoKeeper;

public interface KafkaController {

    void request(DtoKeeper dtoKeeper);
}
