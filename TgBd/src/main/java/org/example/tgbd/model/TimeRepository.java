package org.example.tgbd.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {

    @Query("SELECT t FROM Time t WHERE t.memorization.user.chatId= :chatId and t.amount=0 ")
    Time findTimeByMemorizationMessageIdAndFlag(
            @Param("chatId") Long chatId
    );

    @Modifying
    @Query("delete from Time t where t.id=:id")
    void deleteById(
            @Param("id") Long id
    );

    @Modifying
    @Query("delete from Time t where t.memorization.messageId=:messageId")
    void deleteAllByMessageId(
            @Param("messageId") Long messageId
    );

    @Query("SELECT t FROM Time t WHERE t.memorization.messageId= :messageId ")
    List<Time> findAllByMemorizationMessageId(
            @Param("messageId") Long messageId
    );

    List<Time> findAllByDateToReturnBetween(Date dateToReturn, Date dateToReturn2);

}
