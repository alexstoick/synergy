package synergy.tasks;

import com.bric.image.jpeg.JPEGMetaData;

import org.imgscalr.Scalr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import synergy.models.Photo;
import synergy.utilities.ImagePadder;
import synergy.utilities.WritableImageCreator;
import synergy.views.PhotoGrid;

/**
 * This is the task used to add the thumbnails of photos to the grid.
 */
public class ThumbnailLoaderTask extends Task {
    private List<Photo> photosToDisplay;
    private ObservableList<Image> displayedImagesList;
    private HashMap<Photo, Image> displayedImagesMap;
    private Thread parentThread;

    public ThumbnailLoaderTask(List<Photo> photosToDisplay) {
        this.photosToDisplay = photosToDisplay;
        this.displayedImagesList = PhotoGrid.getDisplayedImagesList();
        this.displayedImagesMap = PhotoGrid.getDisplayedImagesMap();
    }

    public void setParentThread(Thread parentThread) {
        this.parentThread = parentThread;
    }

    @Override
    protected Object call() throws Exception {
        ArrayList<Photo> toEliminate = new ArrayList<>();
        for (Photo photo : photosToDisplay) {
            BufferedImage initialThumbnail = null;
            try {
                initialThumbnail = JPEGMetaData.getThumbnail(new File(photo.getPath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (initialThumbnail == null) {
                toEliminate.add(photo);
                initialThumbnail = ImageIO.read(new File(photo.getPath()));
                initialThumbnail = Scalr.resize(initialThumbnail, 250);
            }

            final WritableImage finalWi = WritableImageCreator.fromBufferedImage
                    (ImagePadder.padToSize(initialThumbnail, 250, 250, new Color(29, 30, 30)));
            initialThumbnail.flush();

            if (!parentThread.isInterrupted()) {
                Platform.runLater(() -> {
                    displayedImagesList.add(finalWi);
                    displayedImagesMap.put(photo, finalWi);
                });
            } else {
                return null;
            }
            System.out.println("Loaded thumbnail for: " + photo.getPath());
        }
        photosToDisplay.removeAll(toEliminate);
        FullResolutionPhotoLoaderTask qualityLoaderTask = new FullResolutionPhotoLoaderTask
                (photosToDisplay);
        Thread qualityLoaderThread = new Thread(qualityLoaderTask);
        qualityLoaderTask.setParentThread(qualityLoaderThread);
        PhotoGrid.getThreads().add(qualityLoaderThread);
        qualityLoaderThread.setDaemon(true);
        qualityLoaderThread.start();
        return null;
    }
}