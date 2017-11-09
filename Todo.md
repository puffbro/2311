# 2311 SpaceInvaders 

Play example: http://www.pacxon4u.com/space-invaders/

Working on:   


Recent Updates:    
Added stage screen.
2nd Stage faster, shots remains.    
Shield tweaked.

Requirements:

ToDo:   
Aliens can fire back at the spaceship.    
Increase the number of shots by one when a UFO is defeated.**Done**  
4 Shelters each with 5hp, block both player and alien shots.    **Done**
More wave.  **Done**


Extra:    
Rework Alien class to array.**Done**    
Show Hitbox.(press H to show)**Done**    

**Detasils on increase no. of shots**   
Setting up array of lasers with:    
private Laser[] lasers = new Laser[5];  //max 5 shot    
for(0-4)lasers[i] = new Laser();    //need to call it for every element!   

Done:   
At least one wave 5x11 alien.  
Create a UFO that will fly across the screen at every 5 seconds.  
Provide menu options to start, pause and replay the game. 
Drive the spaceship with the left and right keys, and fire with the space key.  
Score system and score UI.  
Save and show the highest score.  
Music and sound effects.  
Rework gameState class. 


