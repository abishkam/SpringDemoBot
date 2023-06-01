package org.example.tgbd.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemorizationRepository extends JpaRepository<Memorization, Long> {


    @Query("select case when count(t)>0 then true else false end" +
            " from Memorization t " +
            "where t.user.chatId = :chatId and t.messageId = :messageId")
    boolean existsByUserChatId(
            @Param("chatId") Long chatId,
            @Param("messageId") Long messageId
    );

    @Modifying
    @Query("delete from Memorization m where m.messageId=:messageId")
    void deleteById(
            @Param("messageId") Long messageId
    );

}
