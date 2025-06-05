package org.suhodo.boot01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.suhodo.boot01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

}
