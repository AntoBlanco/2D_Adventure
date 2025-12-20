package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
        this.getTileImage();
        this.loadMap("/maps/map01.txt");
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/water.png")));
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath))))) {
            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split(" ");
                if (rowData.length == 16) {
                    mapTileNum[row] = Arrays.stream(rowData)
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    row++;
                } else {
                    throw new RuntimeException("Error: El mapa debe tener 16 caracteres de ancho. Encontrado: " + Math.ceil((double) line.length()/2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){
        int col = 0, row = 0, x = 0, y = 0;
        while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow){
            int tileNum = mapTileNum[row][col];
            graphics2D.drawImage(tile[tileNum].image,x, y, gamePanel.tileSize,gamePanel.tileSize,null);
            col++;
            x+=gamePanel.tileSize;
            if(col == gamePanel.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y+=gamePanel.tileSize;
            }
        }
    }
}
