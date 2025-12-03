package Pacifica.tgbotek.service;
import Pacifica.tgbotek.entity.File;
import Pacifica.tgbotek.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getAllFiles(){
        return fileRepository.findAll();
    }

    public String  formatFilesList(List<File> files){
        if (files.isEmpty()){
            return ("список файлов пуст");
        }

        StringBuilder sb = new StringBuilder("Список файлов:\n\n");
        int counter = 1;
        for (File file : files){
            sb.append(String.format("%d ID файла %d\n",counter++,file.getId()));
            sb.append(String.format("Путь к файлу: %s\n",file.getFilePath()!= null ? file.getFilePath() : "не указан"));
            sb.append(String.format("ID Решения: %s\n",file.getDecisionId() != null ? file.getDecisionId() : "-"));
            sb.append(String.format("Загрузил: %s\n", file.getUploadedBy() != null ? file.getUploadedBy(): "неизвестно"));
            sb.append(String.format("Дата: %s\n",file.getUploadedAt() != null ? file.getUploadedAt():"неизвестно"));
            sb.append("\n");
        }
        return sb.toString();
    }

}
