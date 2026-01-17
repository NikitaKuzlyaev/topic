package com.topic.controller.helpers;

import com.topic.dto.api.request.BoardCreateRequest;
import com.topic.dto.api.response.*;
import com.topic.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class BoardControllerHelper {
    public static CreateBoardDto mapToCreateBoardDTO(BoardCreateRequest data, Long userId) {
        return new CreateBoardDto(data.title(), userId);
    }

    public static BoardPaginatedResponse mapToBoardPaginatedResponse(PaginatedBoardDto data){
        List<BoardMainInfoResponse> res = new ArrayList<>();
        for (var el: data.threads()){
            res.add(new BoardMainInfoResponse(el.id(), el.title()));
        }
        return new BoardPaginatedResponse(
                new PageResponse(data.currentPage(), data.pageSize(), data.totalPages()), res);
    }

    public static BoardFullInfoResponse mapToBoardFullInfoResponse(BoardWithAllPublicationsDto data){
        return new BoardFullInfoResponse(
                new BoardMainInfoResponse(data.board().id(), data.board().title()),
                data.publications().publications().stream().map(BoardControllerHelper::mapToPublicationInfoResponse).toList()
        );
    }

    public static PublicationInfoResponse mapToPublicationInfoResponse(PublicationDto data){
        return new PublicationInfoResponse(data.id(), data.author(), data.content());
    }

    public static DeleteBoardDto mapToDeleteBoardDTO(Long boardId) {
        return new DeleteBoardDto(boardId);
    }
}
