import java.awt.*;
import java.awt.image.BufferedImage;

class ImageOperations {

    /**
     * zeros out the red color
     *
     * @param img the image provided
     * @return returns a buffered image without red.
     */
    static BufferedImage zeroRed(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i, j);
                int red = 0;
                int green = ColorOperations.getGreen(rgb);
                int blue = ColorOperations.getBlue(rgb);
                Color color = new Color(red, green, blue);
                newImg.setRGB(i, j, color.getRGB());

            }
        }
        return newImg;
    }

    /**
     * turns the image into greyScale
     *
     * @param img image provided
     * @return returns a new instance of the image which is in greyscale.
     */
    static BufferedImage grayscale(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i, j);
                int red = ColorOperations.getRed(rgb);
                int green = ColorOperations.getGreen(rgb);
                int blue = ColorOperations.getBlue(rgb);
                int grey = (red + green + blue) / 3;
                Color color = new Color(grey,grey,grey);
                newImg.setRGB(i, j, color.getRGB());

            }
        }
        return newImg;
    }

    /**
     * inverts the image
     * @param img image provided
     * @return returns the image inverted (color)
     */
    static BufferedImage invert(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i, j);
                int red = ColorOperations.getRed(rgb);
                int green = ColorOperations.getGreen(rgb);
                int blue = ColorOperations.getBlue(rgb);
                Color color = new Color(255 - red, 255 - green, 255- blue);
                newImg.setRGB(i, j, color.getRGB());

            }
        }
        return newImg;
    }

    /**
     * mirrors the image
     *
     * @param img img
     * @param dir dir dir determines if it's horizontal or vertical
     * @return returns a new instance of the mirrored image.
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (dir == MirrorMenuItem.MirrorDirection.VERTICAL) {
            for(int i = 0; i < img.getHeight(); i++){
                for(int j = 0, k = img.getWidth() - 1; j < img.getWidth(); j++, k--){
                    int r = img.getRGB(k,i);
                    newImg.setRGB(j,i,r);
                    newImg.setRGB(k,i,r);

                }
            }
        } else {
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0, k = img.getHeight() - 1; j < img.getHeight(); j++, k--) {
                    int r = img.getRGB(i, k);
                    newImg.setRGB(i, j, r);
                    newImg.setRGB(i, k, r);

                }
            }
        }
        return newImg;
    }

    /**
     * rotates the image either clockwise or counterclockwise
     *
     * @param img img
     * @param dir dir determines if it's horizontal or vertical
     * @return returns a new instance of a counterclockwise or clockwise image
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);

        if (dir == RotateMenuItem.RotateDirection.CLOCKWISE) {
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int rgb = img.getRGB(x, y);
                    newImg.setRGB(img.getHeight() - 1 - y, x, rgb);
                }
            }
        } else {
            for (int y = 0; y < img.getWidth(); y++) {
                for (int x = 0; x < img.getHeight(); x++) {
                    int rgb = img.getRGB(y, x);
                    newImg.setRGB(x, img.getWidth() - 1 - y, rgb);
                }
            }
        }

        return newImg;
    }



    /**
     * repeats the image horizontally or vertically
     *
     * @param img image
     * @param n  no of times we want the image to repeat
     * @param dir dir determines if it's horizontal or vertical
     * @return repeats the number
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        BufferedImage newImg;
        if (dir == RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(img.getWidth()*n, img.getHeight(), BufferedImage.TYPE_INT_RGB);
            for(int y= 0; y < img.getHeight(); y++){
                for(int x = 0; x < img.getWidth(); x++){
                    Color c = new Color(img.getRGB(x,y));
                    Color newC = new Color(c.getRed(),c.getGreen(),c.getBlue());
                    for(int k =0; k < n; k++){
                        newImg.setRGB(x+(img.getHeight() * k), y, newC.getRGB());
                    }
                }
            }

        } else {
            newImg = new BufferedImage(img.getWidth(), img.getHeight()*n, BufferedImage.TYPE_INT_RGB);
            for(int y= 0; y < img.getWidth(); y++){
                for(int x = 0; x < img.getHeight(); x++){
                    Color c = new Color(img.getRGB(x,y));
                    Color newC = new Color(c.getRed(),c.getGreen(),c.getBlue());
                    for(int k =0; k < n; k++){
                        newImg.setRGB(x, y+(img.getWidth() * k), newC.getRGB());
                    }
                }
            }
        }
        return newImg;
    }



    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}
