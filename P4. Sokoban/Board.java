/*
* Object Oriented Programming
 * Author: Miguel Marines
 * Activity: Sokoban
 * 
 * Program with the charasteristics and properties of the board.
*/

// Package Name
package gui.s;

// Libraries
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

// Class Board
public class Board extends JPanel
{
	// Variables
	private static final long serialVersionUID = 1L;
	private final int OFFSET = 30;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Wall> walls;
    private ArrayList<FireBall> baggs;
    private ArrayList<RadioactiveArea> areas;
    
    private LightBall soko;
    private int w = 0;
    private int h = 0;
    
    private boolean isCompleted = false;

    private String level
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##   # ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ##     #########\n"
            + "    ########\n";

    // Constructor
    public Board()
    {

        initBoard();
    }
    
    // Methods to establish and get the characteristics of the board.
    private void initBoard()
    {

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth()
    {
        return this.w;
    }

    public int getBoardHeight()
    {
        return this.h;
    }

    private void initWorld()
    {
        
        walls = new ArrayList<>();
        baggs = new ArrayList<>();
        areas = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        FireBall b;
        RadioactiveArea a;

        for (int i = 0; i < level.length(); i++)
        {

            char item = level.charAt(i);

            switch (item)
            {

                case '\n':
                    y += SPACE;

                    if (this.w < x)
                    {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case '#':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    b = new FireBall(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new RadioactiveArea(x, y);
                    areas.add(a);
                    x += SPACE;
                    break;

                case '@':
                    soko = new LightBall(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }

            h = y;
        }
    }

    private void buildWorld(Graphics g)
    {

        g.setColor(new Color(0, 0, 128));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Ball> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(areas);
        world.addAll(baggs);
        world.add(soko);

        for (int i = 0; i < world.size(); i++) {

            Ball item = world.get(i);

            if (item instanceof LightBall || item instanceof FireBall) {
                
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted)
            {
                
                g.setColor(new Color(230, 230, 0));
                g.drawString("Sokoban Completed!", 290, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter
    {

        @Override
        public void keyPressed(KeyEvent e)
        {

            if (isCompleted)
            {
                return;
            }

            int key = e.getKeyCode();

            switch (key)
            {
                
                case KeyEvent.VK_LEFT:
                    
                    if (checkWallCollision(soko,
                            LEFT_COLLISION))
                    {
                        return;
                    }
                    
                    if (checkBagCollision(LEFT_COLLISION))
                    {
                        return;
                    }
                    
                    soko.move(-SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION))
                    {
                        return;
                    }
                    
                    if (checkBagCollision(RIGHT_COLLISION))
                    {
                        return;
                    }
                    
                    soko.move(SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    if (checkWallCollision(soko, TOP_COLLISION))
                    {
                        return;
                    }
                    
                    if (checkBagCollision(TOP_COLLISION))
                    {
                        return;
                    }
                    
                    soko.move(0, -SPACE);
                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION))
                    {
                        return;
                    }
                    
                    if (checkBagCollision(BOTTOM_COLLISION))
                    {
                        return;
                    }
                    
                    soko.move(0, SPACE);
                    
                    break;
                    
                case KeyEvent.VK_R:
                    
                    restartLevel();
                    
                    break;
                    
                default:
                    break;
            }

            repaint();
        }
    }

    private boolean checkWallCollision(Ball actor, int type)
    {

        switch (type)
        {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < walls.size(); i++)
                {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isLeftCollision(wall))
                    {
                        
                        return true;
                    }
                }
                
                return false;
                
            case RIGHT_COLLISION:
                
                for (int i = 0; i < walls.size(); i++)
                {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isRightCollision(wall))
                    {
                        return true;
                    }
                }
                
                return false;
                
            case TOP_COLLISION:
                
                for (int i = 0; i < walls.size(); i++)
                {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isTopCollision(wall))
                    {
                        
                        return true;
                    }
                }
                
                return false;
                
            case BOTTOM_COLLISION:
                
                for (int i = 0; i < walls.size(); i++)
                {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isBottomCollision(wall))
                    {
                        
                        return true;
                    }
                }
                
                return false;
                
            default:
                break;
        }
        
        return false;
    }

    private boolean checkBagCollision(int type)
    {

        switch (type)
        {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++)
                {

                    FireBall bag = baggs.get(i);

                    if (soko.isLeftCollision(bag))
                    {

                        for (int j = 0; j < baggs.size(); j++)
                        {
                            
                            FireBall item = baggs.get(j);
                            
                            if (!bag.equals(item))
                            {
                                
                                if (bag.isLeftCollision(item))
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, LEFT_COLLISION))
                            {
                                return true;
                            }
                        }
                        
                        bag.move(-SPACE, 0);
                        isCompleted();
                    }
                }
                
                return false;
                
            case RIGHT_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++)
                {

                    FireBall bag = baggs.get(i);
                    
                    if (soko.isRightCollision(bag))
                    {
                        
                        for (int j = 0; j < baggs.size(); j++)
                        {

                        	FireBall item = baggs.get(j);
                            
                            if (!bag.equals(item))
                            {
                                
                                if (bag.isRightCollision(item))
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, RIGHT_COLLISION))
                            {
                                return true;
                            }
                        }
                        
                        bag.move(SPACE, 0);
                        isCompleted();
                    }
                }
                return false;
                
            case TOP_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++)
                {

                	FireBall bag = baggs.get(i);
                    
                    if (soko.isTopCollision(bag))
                    {
                        
                        for (int j = 0; j < baggs.size(); j++)
                        {

                        	FireBall item = baggs.get(j);

                            if (!bag.equals(item))
                            {
                                
                                if (bag.isTopCollision(item))
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, TOP_COLLISION))
                            {
                                return true;
                            }
                        }
                        
                        bag.move(0, -SPACE);
                        isCompleted();
                    }
                }

                return false;
                
            case BOTTOM_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++)
                {

                	FireBall bag = baggs.get(i);
                    
                    if (soko.isBottomCollision(bag))
                    {
                        
                        for (int j = 0; j < baggs.size(); j++)
                        {

                        	FireBall item = baggs.get(j);
                            
                            if (!bag.equals(item))
                            {
                                
                                if (bag.isBottomCollision(item))
                                {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag,BOTTOM_COLLISION))
                            {
                                
                                return true;
                            }
                        }
                        
                        bag.move(0, SPACE);
                        isCompleted();
                    }
                }
                
                break;
                
            default:
                break;
        }

        return false;
    }

    public void isCompleted()
    {

        int nOfBags = baggs.size();
        int finishedBags = 0;

        for (int i = 0; i < nOfBags; i++)
        {
            
        	FireBall bag = baggs.get(i);
            
            for (int j = 0; j < nOfBags; j++)
            {
                
                RadioactiveArea area =  areas.get(j);
                
                if (bag.x() == area.x() && bag.y() == area.y())
                {
                    
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == nOfBags)
        {
            
            isCompleted = true;
            repaint();
        }
    }

    private void restartLevel()
    {

        areas.clear();
        baggs.clear();
        walls.clear();

        initWorld();

        if (isCompleted)
        {
            isCompleted = false;
        }
    }
}
