package com.greenThumb.service;

import com.greenThumb.domain.File;
import com.greenThumb.dto.FileDto;
import com.greenThumb.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    public FileDto getFile(Long id){
        File file = fileRepository.findById(id).get();  // 후에 null 에러 리팩토링

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();

        return fileDto;

    }

}
