package cn.jason.controller;

import cn.jason.storage.StorageFileNotFoundException;
import cn.jason.storage.StorageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String listUploadedFiles(Model model) throws IOException{

        model.addAttribute("files",storageService
                .loadAll()
                .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class,"serveFile",path.getFileName().toString())
                        .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @RequestMapping(value = "/files/{filename:.+}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+file.getFilename() + "\"")
                .body(file);
    }

    @ApiOperation(value = "处理文件")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String handelFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", " you successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
}