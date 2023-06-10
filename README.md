# jangl-falling-sand
A falling sand simulation using JANGL, my graphics library.

![image](https://github.com/AlexanderJCS/JANGL/assets/98898166/22e0aede-44e6-4f2c-8299-a8788b7e84e7)

## Running

If you receive an error when running this program on Mac, make sure to include the following VM argument:
```
-XstartOnFirstThread
```

## My Window is Too Large!

Your window may be too large (or small) on your display. To fix this, modify the [WindowOptions](src/main/java/WindowOptions.java) class. Set the `WINDOW_HEIGHT` value to whatever feels comfortable for your monitor's resolution. I have noticed that a value of 1200 pixels is good for 1440p monitors and a value of 900 pixels is good for 1080p monitors.

## How to Play

Below are all the controls for the game. They are also explained on the top bar of the window.

| Key   | Action                                                      |
|-------|-------------------------------------------------------------|
| S     | Select the sand particle.                                   |
| W     | Select the water particle.                                  |
| Z     | Select the stone particle.                                  |
| B     | Select the barrier particle.                                |
| A     | Select the air particle. It deletes particles you click on. |
| Click | Create the currently selected particle.                     |
