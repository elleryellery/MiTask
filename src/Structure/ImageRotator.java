package Structure;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

public class ImageRotator {
    private ExecutorService executorService;
    private List<ImageIcon> icons;  // Store your icons here
    public BufferedImage buffer;

    public ImageRotator() {
        // Initialize the thread pool with an appropriate number of threads
        int numThreads = Runtime.getRuntime().availableProcessors(); // Adjust as needed
        executorService = Executors.newFixedThreadPool(numThreads);

        // Populate the 'icons' list with your ImageIcons
        icons = new ArrayList<>();
        buffer = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        // Add your icons to the 'icons' list
    }

    public ImageIcon rotate(ImageIcon icon, double angle, Graphics2D graphics) {
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();
    
        // Set the initial background to be transparent
        buffer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        graphics = buffer.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setComposite(AlphaComposite.SrcOver);
    
        // Rotate the image
        graphics.rotate(Math.toRadians(angle), imageWidth / 2, imageHeight / 2);
    
        // Draw the original ImageIcon onto the rotated BufferedImage
        icon.paintIcon(null, graphics, 0, 0);
    
        graphics.dispose();
        return new ImageIcon(buffer);
    }

    public Image rotate(Image _icon, double angle, Graphics2D graphics) {
        ImageIcon icon = new ImageIcon(_icon);
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();
        int xOffset = 0, yOffset = 0;
        if(imageWidth > imageHeight){
            yOffset = (imageWidth-imageHeight)/2;
            imageHeight = imageWidth;
        } else {
            xOffset = (imageHeight-imageWidth)/2;
            imageWidth = imageHeight;
        }
    
        // Set the initial background to be transparent
        BufferedImage buffer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        graphics = buffer.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setComposite(AlphaComposite.SrcOver);
    
        // Translate graphics context to the center of the image
        graphics.translate(imageWidth / 2, imageHeight / 2);
        
        // Rotate the image around the center
        graphics.rotate(Math.toRadians(angle));
    
        // Draw the original ImageIcon, but offset it back by half its width and height
        icon.paintIcon(null, graphics, -imageWidth / 2 + xOffset, -imageHeight / 2 + yOffset);
        
        graphics.dispose();
        return buffer;
    }
    
    

    public List<BufferedImage> rotateAllImages(double angle) {
        List<BufferedImage> rotatedImages = new ArrayList<>();

        // Create a list of Callable tasks to rotate all icons concurrently
        List<Callable<BufferedImage>> tasks = new ArrayList<>();
        for (ImageIcon icon : icons) {
            tasks.add(() -> rotatebuff(icon, angle));
        }

        try {
            // Invoke all tasks and collect the results
            rotatedImages = executorService.invokeAll(tasks)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .toList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rotatedImages;
    }

    public BufferedImage rotatebuff(ImageIcon icon, double angle) {
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        // Your rotation and rendering code here
        // Rotate the image and paint it onto 'bufferedImage'

        graphics.dispose();
        return bufferedImage;
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
