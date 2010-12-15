/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

/**
 * Utility class to convert between grid coordinates and
 * screen pixel coordinates.
 * @author Andrew
 */
public class ScreenUtil {

    private int gridWidth;
    private int gridHeight;
    private int screenPixelWidth;
    private int screenPixelHeight;

    public ScreenUtil(int gridWidth, int gridHeight,
                      int screenPixelWidth, int screenPixelHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.screenPixelWidth = screenPixelWidth;
        this.screenPixelHeight = screenPixelHeight;
    }

    public int[] gridToScreen(int gridX, int gridY) {
        return new int[] { gridX * screenPixelWidth / gridWidth,
                           gridY * screenPixelHeight / gridHeight };
    }

    public int[] screenToGrid(int screenX, int screenY) {
        return new int[] { screenX * gridWidth / screenPixelWidth,
                           screenY * gridHeight / screenPixelHeight };
    }
}
