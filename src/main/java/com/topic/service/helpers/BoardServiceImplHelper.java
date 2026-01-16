package com.topic.service.helpers;

import com.topic.entity.main.Board;
import com.topic.entity.main.Publication;
import com.topic.service.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class BoardServiceImplHelper {

    public static BoardDto mapToBoardDto(Board data) {
        return new BoardDto(
                data.getId(),
                data.getTitle(),
                data.getAuthor(),
                data.getParent(),
                data.getCreatedAt()
        );
    }

    public static PaginatedBoardDto mapToPaginatedBoardDto(Page<Board> data, int page, int pageSize) {
        List<BoardDto> boardDto = data.stream()
                .map(el -> new BoardDto(
                        el.getId(), el.getTitle(), el.getAuthor(), el.getParent(), el.getCreatedAt())
                ).collect(Collectors.toList());

        // TODO Заглушка
        int totalPages = 10;

        return new PaginatedBoardDto(page, totalPages, pageSize, boardDto);
    }

    public static PublicationsListDto mapToPublicationsListDto(List<Publication> publicationList){
        List<PublicationDto> publications = publicationList.stream().map(BoardServiceImplHelper::mapToPublicationDto).toList();
        return new PublicationsListDto(publications);
    }

    public static PublicationDto mapToPublicationDto(Publication publication){
        return new PublicationDto(publication.getId(), publication.getAuthor().getUsername(), publication.getContent());
    }

    public static BoardWithAllPublicationsDto mapToBoardWithAllPublicationsDto(Board board, List<Publication> publicationList){

        BoardDto boardDto = BoardServiceImplHelper.mapToBoardDto(board);
        PublicationsListDto publicationsListDto = BoardServiceImplHelper.mapToPublicationsListDto(publicationList);

        return new BoardWithAllPublicationsDto(
                boardDto, publicationsListDto
        );
    }
}
