package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.SnakeGame;
import knight.arkham.objects.Snake;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

    private final SnakeGame game = SnakeGame.INSTANCE;

    //private final OrthographicCamera camera;

    private final Texture snakeFoodTexture;

    private final Rectangle snakeFoodBody;

    private final Snake snake;

    private final World world;


    public GameScreen(OrthographicCamera globalCamera) {

        //I won't be using the camera for now
       // camera = globalCamera;

        snakeFoodTexture = new Texture("white.png");
        snakeFoodBody = new Rectangle(400,400,32,32);

        world = new World(new Vector2(0,0),false);

        //poner siempre los objetos al final cuando se desee enviar un this, para asi asegurarnos que todos los elementos
        //de nuestra pantalla esten instanciados
        snake = new Snake(this);
    }

    @Override
    public void show() {

    }

    private void update(){

//        Aqui defino que mi world correra a 60 fps
        world.step(1/60f, 6, 2);

        snake.update();

        if(snake.getSnakeFakeBody().overlaps(snakeFoodBody))
            snakeFoodRandomPositionGenerator();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){

            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,0);

        game.batch.begin();

        snake.render(game.batch);
        game.batch.draw(snakeFoodTexture,snakeFoodBody.x,snakeFoodBody.y,snakeFoodBody.width,snakeFoodBody.height);

        game.batch.end();

        update();
    }


    private void snakeFoodRandomPositionGenerator(){

        Random randomPosition = new Random();

        //the max position is the difference between widthScreen or heightScreen and the width and height
        int minPositionX = 10;
        int maxPositionX = 928;

        int minPositionY = 10;
        int maxPositionY = 608;

        snakeFoodBody.x = randomPosition.nextInt(maxPositionX-minPositionX) + minPositionX;
        snakeFoodBody.y = randomPosition.nextInt(maxPositionY-minPositionY) + minPositionY;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        snake.getSnakeTexture().dispose();
        snakeFoodTexture.dispose();
    }


    public World getWorld() { return world; }
}
