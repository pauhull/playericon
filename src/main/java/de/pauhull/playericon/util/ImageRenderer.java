package de.pauhull.playericon.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageRenderer {

    public static BufferedImage createIcon(IconPlayer player, BufferedImage background, BufferedImage overlay, boolean is3D, double scaleFactor) throws Exception {

        URL url;
        if (is3D) {
            url = new URL("https://crafatar.com/renders/head/" + player.getUuid().toString() + ".png");
        } else {
            url = new URL("https://crafatar.com/avatars/" + player.getUuid().toString() + "?size=64.png");
        }

        BufferedImage playerImage = ImageIO.read(url);

        BufferedImage result = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = result.createGraphics();

        if (background != null)
            graphics.drawImage(background, 0, 0, 64, 64, null);

        int newPos = (int) (64.0 * (1.0 - scaleFactor) * 0.5);
        graphics.drawImage(playerImage, newPos, newPos, (int) (64.0 * scaleFactor), (int) (64.0 * scaleFactor), null);

        if (overlay != null)
            graphics.drawImage(overlay, 0, 0, 64, 64, null);

        graphics.dispose();

        return result;
    }

}
