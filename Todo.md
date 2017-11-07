# 2311 SpaceInvaders 

Play example: http://www.pacxon4u.com/space-invaders/

Requirements

ToDo:   
Aliens can fire back at the spaceship.  
**Done**:Increase the number of shots by one when a UFO is defeated.  
4 Shelters each with 5hp, block both player and alien shots.  
More wave.  

**Increase the number of shots by one when a UFO is defeated.**   
Setting up array of lasers with:    
private Laser[] lasers = new Laser[5];  //max 5 shot    
for(0-4)lasers[i] = new Laser();    //need to call it for every element**   

Extra:    
[Give up]Rework Alien class to arraylist.  
Show Hitbox.  

Done:   
At least one wave 5x11 alien.  
Create a UFO that will fly across the screen at every 5 seconds.  
Provide menu options to start, pause and replay the game. 
Drive the spaceship with the left and right keys, and fire with the space key.  
Score system and score UI.  
Save and show the highest score.  
Music and sound effects.  
Rework gameState class. 


