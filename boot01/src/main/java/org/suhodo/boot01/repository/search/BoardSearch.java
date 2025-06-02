package org.suhodo.boot01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suhodo.boot01.domain.Board;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);
}
