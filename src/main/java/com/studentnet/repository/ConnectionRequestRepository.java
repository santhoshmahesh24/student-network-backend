package com.studentnet.repository;

import com.studentnet.model.ConnectionRequest;
import com.studentnet.model.ConnectionRequest.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    List<ConnectionRequest> findByReceiverIdAndStatus(Long receiverId, Status status);

    List<ConnectionRequest> findBySenderIdAndStatus(Long senderId, Status status);

    Optional<ConnectionRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE " +
           "(cr.sender.id = :userId OR cr.receiver.id = :userId) AND cr.status = :status")
    List<ConnectionRequest> findAllByUserAndStatus(@Param("userId") Long userId,
                                                    @Param("status") Status status);

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
