package game.ship;

import game.player.ShipType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

public class SpritesLoader {
    private static final EnumMap<ShipType, BufferedImage> SPRITES_CACHE = new EnumMap<>(ShipType.class);
    private static final EnumMap<ShipType, String> SPRITE_MAPPING = new EnumMap<>(ShipType.class);

    public static BufferedImage getShipSprite(ShipType type) {
        return SPRITES_CACHE.get(type);
    }

    static {
        SPRITE_MAPPING.put(ShipType.CARRIER, "carrier");
        SPRITE_MAPPING.put(ShipType.BATTLESHIP, "battleship");
        SPRITE_MAPPING.put(ShipType.FRIGATE, "frigate");
        SPRITE_MAPPING.put(ShipType.DESTROYER, "destroyer");
        SPRITE_MAPPING.put(ShipType.SUBMARINE, "submarine");

        System.out.println("loading sprites, amount: " + SPRITE_MAPPING.size());

        for (var kvPair : SPRITE_MAPPING.entrySet()) {

            try {
                final var resource = SpritesLoader.class.getResource("/ships/" + kvPair.getValue() + ".png");

                if (resource == null) {
                    throw new NullPointerException("resource for ship type: " + kvPair.getKey() + " with name " + kvPair.getValue() + " is null");
                }

                final var sprite = ImageIO.read(resource);

                SPRITES_CACHE.put(kvPair.getKey(), sprite);
            } catch (IOException | NullPointerException e) {
                System.err.println("couldn't load sprite for ship type: " + kvPair.getKey() + " with name " + kvPair.getValue());

                throw new RuntimeException(e);
            }
        }

        System.out.println("finished loading sprites");
    }
}
