package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.services.ImageService;
import com.webbuilders.webgym.services.ProductService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;

    public ImageController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    @GetMapping("product/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("product", productService.findCommandById(Long.valueOf(id)));

        return "product/imageform";
    }

    @PostMapping("product/{id}/image")
    public String handleImagePost(@PathVariable String id,
                                  @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/product/" + id + "/show";
    }

    @GetMapping("product/{id}/imageOfProduct")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        ProductCommand productCommand = productService.findCommandById(Long.valueOf(id));

        if (productCommand.getImage() != null) {
            byte[] byteArray = new byte[productCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : productCommand.getImage()){
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
