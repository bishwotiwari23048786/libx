package com.Libx.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class CoverImageUtil {

    // Folder inside webapp where images will be saved
    private static final String IMAGE_FOLDER = "cover-images";

    // The absolute path to save the image on your system
    private static final String BASE_PATH = "C:\\Users\\drbis\\eclipse-workspace\\LibraryManagementSystem\\src\\main\\webapp\\";

    public static String saveCoverImage(Part part, ServletContext context) throws IOException {
        if (part == null || part.getSize() == 0) {
            System.out.println("No file uploaded or file size is zero.");
            return null;
        }

        // Generate a unique filename to avoid overwriting
        String fileName = UUID.randomUUID().toString() + "_" +
                          Paths.get(part.getSubmittedFileName()).getFileName().toString();

        // Get absolute path to the folder where images should be stored inside webapp (not the tmp directory)
        String uploadPath = BASE_PATH + IMAGE_FOLDER;

        // Save the file to the directory
        String fullImagePath = uploadPath + File.separator + fileName;
        try {
            part.write(fullImagePath);
            System.out.println("File saved to: " + fullImagePath);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        // Return the relative path to store in the database
        return IMAGE_FOLDER + "/" + fileName;
    }
}
